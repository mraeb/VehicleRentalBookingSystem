package com.service;

import com.management.BillingManagement;
import com.management.BookingManagement;
import com.model.Billing;
import com.model.Booking;
import com.util.ApplicationUtil;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class BillingService {

    private BillingManagement billingDAO = new BillingManagement();
    private BookingManagement bookingDAO = new BookingManagement();

    public String generateInvoiceId() { return "INV" + UUID.randomUUID().toString().substring(0, 6).toUpperCase(); }

    public double calculateBillAmount(Date pickup, Date ret, double rate) {
        long days = ApplicationUtil.daysBetween(pickup, ret);
        if (days <= 0) days = 1;
        return Math.round(days * rate * 100.0) / 100.0;
    }

    public Billing createInvoice(String bookingId, double dailyRate, String paymentMode) {
        Booking booking = bookingDAO.getBookingById(bookingId);
        if (booking == null) {
            System.out.println("⚠ Booking not found. Invoice cannot be generated.");
            return null;
        }

        Billing existing = billingDAO.getBillingByBookingId(bookingId);
        if (existing != null) {
            System.out.println("⚠ Invoice already exists for this booking.");
            return existing;
        }

        double totalAmount = calculateBillAmount(booking.getPickupDate(), booking.getReturnDate(), dailyRate);
        long totalDays = ApplicationUtil.daysBetween(booking.getPickupDate(), booking.getReturnDate());
        if (totalDays <= 0) totalDays = 1;

        Billing b = new Billing();
        b.setInvoiceId(generateInvoiceId());
        b.setBooking(booking);
        b.setTotalDays((int) totalDays);
        b.setTotalAmount(totalAmount);
        b.setPaymentDate(new Date(System.currentTimeMillis()));
        b.setPaymentMode(paymentMode);

        boolean ok = billingDAO.insertInvoice(b);
        if (ok) {
            System.out.println("\n✅ Invoice Created Successfully!");
            System.out.println(b.toString());
            return b;
        } else {
            System.out.println("❌ Failed to insert invoice into database.");
            return null;
        }
    }

    public Billing getBillingByInvoiceId(String invoiceId) { return billingDAO.getBillingByInvoiceId(invoiceId); }
    public Billing getBillingByBookingId(String bookingId) { return billingDAO.getBillingByBookingId(bookingId); }
    public List<Billing> getAllInvoices() { return billingDAO.getAllInvoices(); }
}
