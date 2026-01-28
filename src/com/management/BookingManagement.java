package com.management;

import com.model.Booking;
import com.model.Customer;
import com.model.Vehicle;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BookingManagement {

    public boolean insertBooking(Connection con, Booking b) throws SQLException {
        String sql = "INSERT INTO booking (bookingId, customerId, vehicleId, bookingDate, pickupDate, returnDate, bookingStatus) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, b.getBookingId());
            ps.setString(2, b.getCustomer().getCustomerId());
            ps.setString(3, b.getVehicle().getVehicleId());
            ps.setDate(4, b.getBookingDate());
            ps.setDate(5, b.getPickupDate());
            ps.setDate(6, b.getReturnDate());
            ps.setString(7, b.getBookingStatus());
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    // Non-transactional convenience wrapper
    public boolean insertBooking(Booking b) {
        String sql = "INSERT INTO booking (bookingId, customerId, vehicleId, bookingDate, pickupDate, returnDate, bookingStatus) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, b.getBookingId());
            ps.setString(2, b.getCustomer().getCustomerId());
            ps.setString(3, b.getVehicle().getVehicleId());
            ps.setDate(4, b.getBookingDate());
            ps.setDate(5, b.getPickupDate());
            ps.setDate(6, b.getReturnDate());
            ps.setString(7, b.getBookingStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("⚠ insertBooking Error: " + e.getMessage());
            return false;
        }
    }

    public Booking getBookingById(String bookingId) {
        String sql = "SELECT b.*, c.*, v.* FROM booking b " +
                     "LEFT JOIN customer c ON b.customerId = c.customerId " +
                     "LEFT JOIN vehicle v ON b.vehicleId = v.vehicleId " +
                     "WHERE b.bookingId=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Booking b = new Booking();
                    b.setBookingId(rs.getString("bookingId"));
                    Customer c = new Customer();
                    c.setCustomerId(rs.getString("customerId"));
                    c.setFirstName(rs.getString("firstName"));
                    c.setLastName(rs.getString("lastName"));
                    c.setEmail(rs.getString("email"));
                    c.setPhone(rs.getString("phone"));
                    c.setAddress(rs.getString("address"));
                    c.setDriverLicenseNumber(rs.getString("driverLicenseNumber"));
                    c.setPassword(rs.getString("password"));
                    b.setCustomer(c);

                    Vehicle v = new Vehicle();
                    v.setVehicleId(rs.getString("vehicleId"));
                    v.setMake(rs.getString("make"));
                    v.setModel(rs.getString("model"));
                    v.setYear(rs.getInt("year"));
                    v.setVehicleType(rs.getString("vehicleType"));
                    v.setLicensePlate(rs.getString("licensePlate"));
                    v.setDailyRate(rs.getDouble("dailyRate"));
                    v.setStatus(rs.getString("status"));
                    v.setMileage(rs.getInt("mileage"));
                    b.setVehicle(v);

                    b.setBookingDate(rs.getDate("bookingDate"));
                    b.setPickupDate(rs.getDate("pickupDate"));
                    b.setReturnDate(rs.getDate("returnDate"));
                    b.setBookingStatus(rs.getString("bookingStatus"));
                    return b;
                }
            }
        } catch (Exception e) {
            System.out.println("⚠ getBookingById Error: " + e.getMessage());
        }
        return null;
    }

    public List<Booking> getBookingsByCustomerId(String string) {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM booking WHERE customerId=? ORDER BY bookingDate DESC";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, string);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setBookingId(rs.getString("bookingId"));
                    Customer c = new Customer(); c.setCustomerId(rs.getString("customerId"));
                    b.setCustomer(c);
                    Vehicle v = new Vehicle(); v.setVehicleId(rs.getString("vehicleId"));
                    b.setVehicle(v);
                    b.setBookingDate(rs.getDate("bookingDate"));
                    b.setPickupDate(rs.getDate("pickupDate"));
                    b.setReturnDate(rs.getDate("returnDate"));
                    b.setBookingStatus(rs.getString("bookingStatus"));
                    list.add(b);
                }
            }
        } catch (Exception e) {
            System.out.println("⚠ getBookingsByCustomerId Error: " + e.getMessage());
        }
        return list;
    }

    public boolean cancelBooking(String bookingId) {
        String sql = "UPDATE booking SET bookingStatus='CANCELLED' WHERE bookingId=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, bookingId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("⚠ cancelBooking Error: " + e.getMessage());
            return false;
        }
    }

    public boolean updateBookingStatus(String bookingId, String status) {
        String sql = "UPDATE booking SET bookingStatus=? WHERE bookingId=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, bookingId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("⚠ updateBookingStatus Error: " + e.getMessage());
            return false;
        }
    }

    public boolean isVehicleAvailable(String vehicleId, Date pickup, Date ret) {
        String sql = "SELECT 1 FROM booking WHERE vehicleId=? AND bookingStatus='CONFIRMED' AND NOT (returnDate < ? OR pickupDate > ?)";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicleId);
            ps.setDate(2, pickup);
            ps.setDate(3, ret);
            try (ResultSet rs = ps.executeQuery()) {
                return !rs.next();
            }
        } catch (Exception e) {
            System.out.println("⚠ isVehicleAvailable Error: " + e.getMessage());
            return false;
        }
    }
}
