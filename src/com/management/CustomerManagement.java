package com.management;

import com.model.Customer;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;

public class CustomerManagement {
	 public String generateNextCustomerId() {
	        String sql = "SELECT customerId FROM customer ORDER BY customerId DESC LIMIT 1";
	        String prefix = "CUST";
	        int next = 1;

	        try (Connection con = DBConnectionManager.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {
	                String lastId = rs.getString("customerId");
	                if (lastId != null && lastId.startsWith(prefix)) {
	                    next = Integer.parseInt(lastId.substring(4)) + 1;
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("⚠ ID generation error: " + e.getMessage());
	        }

	        DecimalFormat df = new DecimalFormat("000");
	        return prefix + df.format(next);
	    }

	    // 🔹 Insert new customer (now includes generated customerId)
	    public String insertCustomer(Customer c) throws Exception {
	        String sql = "INSERT INTO customer(customerId, firstName, lastName, email, phone, address, driverLicenseNumber, password, createdAt, updatedAt) "
	                   + "VALUES (?,?,?,?,?,?,?,?,NOW(),NOW())";

	        String newId = generateNextCustomerId();

	        try (Connection con = DBConnectionManager.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setString(1, newId);
	            ps.setString(2, c.getFirstName());
	            ps.setString(3, c.getLastName());
	            ps.setString(4, c.getEmail());
	            ps.setString(5, c.getPhone());
	            ps.setString(6, c.getAddress());
	            ps.setString(7, c.getDriverLicenseNumber());
	            ps.setString(8, c.getPassword());

	            int rows = ps.executeUpdate();
	            if (rows > 0) return newId;

	        } catch (SQLIntegrityConstraintViolationException icv) {
	            System.out.println("⚠ Duplicate email or license number: " + icv.getMessage());
	        } catch (SQLException e) {
	            System.out.println("❌ InsertCustomerError: " + e.getMessage());
	        }
	        return null;
	    }


//    public int insertCustomer(Customer c) throws Exception {
//        String sql = "INSERT INTO customer(firstName,lastName,email,phone,address,driverLicenseNumber,password,createdAt,updatedAt) "
//                   + "VALUES (?,?,?,?,?,?,?,?,NOW())";
//        try (Connection con = DBConnectionManager.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//            ps.setString(1, c.getFirstName());
//            ps.setString(2, c.getLastName());
//            ps.setString(3, c.getEmail());
//            ps.setString(4, c.getPhone());
//            ps.setString(5, c.getAddress());
//            ps.setString(6, c.getDriverLicenseNumber());
//            ps.setString(7, c.getPassword());
//
//            int rows = ps.executeUpdate();
//            if (rows == 0) return -1;
//            try (ResultSet rs = ps.getGeneratedKeys()) {
//                if (rs.next()) return rs.getInt(1);
//            }
//        } catch (SQLIntegrityConstraintViolationException icv) {
//            System.out.println("⚠ Duplicate email or constraint violation: " + icv.getMessage());
//        } catch (SQLException e) {
//            System.out.println("❌ InsertCustomerError: " + e.getMessage());
//        }
//        return -1;
//    }

    public boolean isCustomerExistsByEmail(String email) {
        String sql = "SELECT customerId FROM customer WHERE email=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { System.out.println("⚠ CheckCustomerByEmailError: " + e.getMessage()); }
        return false;
    }

    public boolean isCustomerExistsById(String customerId) {
        String sql = "SELECT customerId FROM customer WHERE customerId=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customerId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { System.out.println("⚠ CheckCustomerByIdError: " + e.getMessage()); }
        return false;
    }

    public boolean updateCustomerInfo(String customerId, String newEmail, String newPhone, String newAddress) {
        if (!isCustomerExistsById(customerId)) {
            System.out.println("⚠ Invalid customer Id");
            return false;
        }
        String sql = "UPDATE customer SET email=?, phone=?, address=?, updatedAt=NOW() WHERE customerId=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newEmail);
            ps.setString(2, newPhone);
            ps.setString(3, newAddress);
            ps.setString(4, customerId);
            return ps.executeUpdate() == 1;
        } catch (SQLIntegrityConstraintViolationException icv) {
            System.out.println("⚠ Email already in use: " + icv.getMessage());
        } catch (Exception e) {
            System.out.println("⚠ UpdateCustomerInfoError: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteCustomer(String customerId) {
        if (!isCustomerExistsById(customerId)) {
            System.out.println("⚠ Invalid customer Id");
            return false;
        }
        String sql = "DELETE FROM customer WHERE customerId=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customerId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.out.println("⚠ DeleteCustomerError: " + e.getMessage());
            return false;
        }
    }

    public Customer getCustomerById(String customerId) {
        String sql = "SELECT * FROM customer WHERE customerId=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultToCustomer(rs);
            }
        } catch (Exception e) { System.out.println("⚠ GetCustomerByIdError: " + e.getMessage()); }
        return null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY customerId";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultToCustomer(rs));
        } catch (Exception e) { System.out.println("⚠ GetAllCustomersError: " + e.getMessage()); }
        return list;
    }

    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM customer WHERE email=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultToCustomer(rs);
            }
        } catch (Exception e) { System.out.println("⚠ GetCustomerByEmailError: " + e.getMessage()); }
        return null;
    }

    private Customer mapResultToCustomer(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setCustomerId(rs.getString("customerId"));
        c.setFirstName(rs.getString("firstName"));
        c.setLastName(rs.getString("lastName"));
        c.setEmail(rs.getString("email"));
        c.setPhone(rs.getString("phone"));
        c.setAddress(rs.getString("address"));
        c.setDriverLicenseNumber(rs.getString("driverLicenseNumber"));
        c.setPassword(rs.getString("password"));
        return c;
    }
}
