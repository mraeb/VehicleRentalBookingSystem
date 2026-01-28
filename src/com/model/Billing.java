package com.model;

import java.sql.Date;

public class Billing {
    private String invoiceId;
    private Booking booking; // object
    private int totalDays;
    private double totalAmount;
    private Date paymentDate;
    private String paymentMode;

    public Billing() {}

    public Billing(String invoiceId, Booking booking, int totalDays, double totalAmount, Date paymentDate, String paymentMode) {
        this.invoiceId = invoiceId;
        this.booking = booking;
        this.totalDays = totalDays;
        this.totalAmount = totalAmount;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
    }

    // getters/setters
    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
    public int getTotalDays() { return totalDays; }
    public void setTotalDays(int totalDays) { this.totalDays = totalDays; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    @Override
    public String toString() {
        String bid = (booking != null) ? booking.getBookingId() : "N/A";
        return "InvoiceID: " + invoiceId + " | BookingID: " + bid +
                " | TotalDays: " + totalDays + " | TotalAmount: ₹" + totalAmount +
                " | PaymentDate: " + paymentDate + " | Mode: " + paymentMode;
    }
}
