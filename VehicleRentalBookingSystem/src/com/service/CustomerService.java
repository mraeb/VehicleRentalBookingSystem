package com.service;

import com.management.CustomerManagement;
import com.model.Customer;
import com.util.ApplicationUtil;
import java.util.List;

public class CustomerService {
    private CustomerManagement dao = new CustomerManagement();

    public List<Customer> buildCustomerList(String input) { return ApplicationUtil.parseCustomerList(input); }

    public String registerCustomer(Customer c, String plainPassword) throws Exception {
        if (c == null) return null;
        if (!ApplicationUtil.isValidEmail(c.getEmail())) { ApplicationUtil.notifyInvalid("Invalid email"); return null; }
        if (dao.isCustomerExistsByEmail(c.getEmail())) { System.out.println("⚠ Email already exists"); return null; }
        c.setPassword(plainPassword);
        return dao.insertCustomer(c); // returns String id now
    }

    public Customer login(String email, String password) {
        if (email == null || email.isEmpty()) return null;
        Customer stored = dao.getCustomerByEmail(email);
        if (stored == null) return null;
        if (password != null && password.equals(stored.getPassword())) {
            System.out.println("✅ Login successful. Welcome " + stored.getFirstName());
            return stored;
        } else return null;
    }

    public boolean updateCustomerInfo(String id, String email, String phone, String address) { return dao.updateCustomerInfo(id, email, phone, address); }
    public boolean deleteCustomer(String id) { return dao.deleteCustomer(id); }
    public Customer getCustomerById(String id) { return dao.getCustomerById(id); }
    public List<Customer> listAllCustomers() { return dao.getAllCustomers(); }
}
