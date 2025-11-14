package com.management;

import com.model.Billing;
import com.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingManagement {

    public boolean insertInvoice(Billing b) {
        String sql = "INSERT INTO billing (invoiceId, bookingId, totalDays, totalAmount, paymentDate, paymentMode) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, b.getInvoiceId());
            ps.setString(2, b.getBooking().getBookingId());
            ps.setInt(3, b.getTotalDays());
            ps.setDouble(4, b.getTotalAmount());
            ps.setDate(5, b.getPaymentDate());
            ps.setString(6, b.getPaymentMode());
            return ps.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("⚠ Invoice already exists for this booking.");
            return false;
        } catch (Exception e) {
            System.out.println("⚠ insertInvoice Error: " + e.getMessage());
            return false;
        }
    }

    public Billing getBillingByInvoiceId(String invoiceId) {
        String sql = "SELECT * FROM billing WHERE invoiceId=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Billing b = new Billing();
                    b.setInvoiceId(rs.getString("invoiceId"));
                    Booking bk = new Booking(); bk.setBookingId(rs.getString("bookingId"));
                    b.setBooking(bk);
                    b.setTotalDays(rs.getInt("totalDays"));
                    b.setTotalAmount(rs.getDouble("totalAmount"));
                    b.setPaymentDate(rs.getDate("paymentDate"));
                    b.setPaymentMode(rs.getString("paymentMode"));
                    return b;
                }
            }
        } catch (Exception e) {
            System.out.println("⚠ getBillingByInvoiceId Error: " + e.getMessage());
        }
        return null;
    }

    public Billing getBillingByBookingId(String bookingId) {
        String sql = "SELECT * FROM billing WHERE bookingId=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Billing b = new Billing();
                    b.setInvoiceId(rs.getString("invoiceId"));
                    Booking bk = new Booking(); bk.setBookingId(rs.getString("bookingId"));
                    b.setBooking(bk);
                    b.setTotalDays(rs.getInt("totalDays"));
                    b.setTotalAmount(rs.getDouble("totalAmount"));
                    b.setPaymentDate(rs.getDate("paymentDate"));
                    b.setPaymentMode(rs.getString("paymentMode"));
                    return b;
                }
            }
        } catch (Exception e) {
            System.out.println("⚠ getBillingByBookingId Error: " + e.getMessage());
        }
        return null;
    }

    public List<Billing> getAllInvoices() {
        List<Billing> list = new ArrayList<>();
        String sql = "SELECT * FROM billing ORDER BY paymentDate DESC";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Billing b = new Billing();
                b.setInvoiceId(rs.getString("invoiceId"));
                Booking bk = new Booking(); bk.setBookingId(rs.getString("bookingId"));
                b.setBooking(bk);
                b.setTotalDays(rs.getInt("totalDays"));
                b.setTotalAmount(rs.getDouble("totalAmount"));
                b.setPaymentDate(rs.getDate("paymentDate"));
                b.setPaymentMode(rs.getString("paymentMode"));
                list.add(b);
            }
        } catch (Exception e) {
            System.out.println("⚠ getAllInvoices Error: " + e.getMessage());
        }
        return list;
    }

    public boolean isInvoiceExists(String invoiceId) {
        String sql = "SELECT invoiceId FROM billing WHERE invoiceId=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            System.out.println("⚠ isInvoiceExists Error: " + e.getMessage());
        }
        return false;
    }
}
