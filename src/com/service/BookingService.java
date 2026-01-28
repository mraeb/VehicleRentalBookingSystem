package com.service;

import com.management.BookingManagement;
import com.management.VehicleManagement;
import com.management.CustomerManagement;
import com.model.Booking;
import com.model.Customer;
import com.model.Vehicle;
import com.util.ApplicationUtil;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import com.management.DBConnectionManager;

public class BookingService {
    private BookingManagement bookingDAO = new BookingManagement();
    private VehicleManagement vehicleDAO = new VehicleManagement();
    private CustomerManagement customerDAO = new CustomerManagement();

    public String generateBookingId() { return "B" + UUID.randomUUID().toString().substring(0, 6).toUpperCase(); }

    // transactional createBooking
    public boolean createBooking(Booking b) {
        if (b == null || b.getCustomer() == null || b.getVehicle() == null) {
            System.out.println("⚠ Invalid booking data");
            return false;
        }

        String customerId = b.getCustomer().getCustomerId();
        String vehicleId = b.getVehicle().getVehicleId();
        if (!ApplicationUtil.validateBookingDates(b.getPickupDate(), b.getReturnDate())) {
            return false; // stop execution early
        }

        
        if (!customerDAO.isCustomerExistsById(customerId)) {
            System.out.println("⚠ Invalid customer Id");
            return false;
        }
        if (!vehicleDAO.isVehicleExists(vehicleId)) {
            System.out.println("⚠ Invalid Vehicle ID!");
            return false;
        }
        Date pickup = b.getPickupDate();
        Date ret = b.getReturnDate();
        if (pickup == null || ret == null) {
            System.out.println("⚠ Invalid dates");
            return false;
        }
        if (!pickup.before(ret)) {
            System.out.println("⚠ Pickup date must be before return date");
            return false;
        }

        // availability check
        if (!bookingDAO.isVehicleAvailable(vehicleId, pickup, ret)) {
            System.out.println("⚠ Vehicle not available for the selected dates.");
            return false;
        }

        // Transaction: insert booking and update vehicle status
        Connection con = null;
        try {
            con = DBConnectionManager.getConnection();
            con.setAutoCommit(false);

            // optional row lock: SELECT FOR UPDATE (simple implementation)
            try (java.sql.PreparedStatement lock = con.prepareStatement("SELECT status FROM vehicle WHERE vehicleId=? FOR UPDATE")) {
                lock.setString(1, vehicleId);
                java.sql.ResultSet rr = lock.executeQuery();
                if (rr.next()) {
                    String st = rr.getString(1);
                    if (!"AVAILABLE".equalsIgnoreCase(st)) {
                        con.rollback();
                        System.out.println("⚠ Vehicle currently not AVAILABLE (status=" + st + ")");
                        return false;
                    }
                } else {
                    con.rollback();
                    System.out.println("⚠ Vehicle row not found");
                    return false;
                }
            }

            // insert booking using connection
            boolean ok = bookingDAO.insertBooking(con, b);
            if (!ok) { con.rollback(); System.out.println("❌ Booking insert failed"); return false; }

            // update vehicle status
            try (java.sql.PreparedStatement upd = con.prepareStatement("UPDATE vehicle SET status='BOOKED', updatedAt=NOW() WHERE vehicleId=?")) {
                upd.setString(1, vehicleId);
                int u = upd.executeUpdate();
                if (u != 1) { con.rollback(); System.out.println("❌ Failed to update vehicle status"); return false; }
            }

            con.commit();
            System.out.println("✅ Booking confirmed with ID: " + b.getBookingId());
            return true;
        } catch (Exception e) {
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            System.out.println("⚠ createBooking Error: " + e.getMessage());
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); if (con != null) con.close(); } catch (Exception ex) {}
        }
    }

    public Booking getBookingById(String id) { return bookingDAO.getBookingById(id); }
    public List<Booking> getCustomerBookings(String string) { return bookingDAO.getBookingsByCustomerId(string); }

    public boolean cancelBooking(String bookingId) {
        Booking b = bookingDAO.getBookingById(bookingId);
        if (b == null) {
            System.out.println("⚠ Booking ID not found.");
            return false;
        }
        boolean cancelled = bookingDAO.cancelBooking(bookingId);
        if (cancelled) {
            vehicleDAO.updateVehicleStatus(b.getVehicle().getVehicleId(), "AVAILABLE");
            System.out.println("✅ Booking cancelled and vehicle released.");
        }
        return cancelled;
    }

    public boolean updateBookingStatus(String bookingId, String status) {
        if (!"PENDING".equalsIgnoreCase(status) && !"CONFIRMED".equalsIgnoreCase(status) && !"CANCELLED".equalsIgnoreCase(status)) {
            System.out.println("⚠ Invalid status. Allowed: PENDING, CONFIRMED, CANCELLED");
            return false;
        }
        return bookingDAO.updateBookingStatus(bookingId, status.toUpperCase());
    }

    public long calculateDuration(Date pickup, Date ret) { return ApplicationUtil.daysBetween(pickup, ret); }
}
