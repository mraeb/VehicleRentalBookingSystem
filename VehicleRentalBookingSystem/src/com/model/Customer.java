package com.model;

public class Customer {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;      // username
    private String phone;
    private String address;
    private String driverLicenseNumber;
    private String password;   // plain per requirement

    public Customer() {}

//    public int getCustomerId() { return customerId; }
//    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDriverLicenseNumber() { return driverLicenseNumber; }
    public void setDriverLicenseNumber(String driverLicenseNumber) { this.driverLicenseNumber = driverLicenseNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return String.format("ID:%s | %s %s | %s | %s | DL:%s",
                customerId, firstName, lastName, email, phone, driverLicenseNumber);
    }
}
