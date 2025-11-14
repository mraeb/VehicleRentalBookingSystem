package com.model;

import java.sql.Date;

public class Booking {
    private String bookingId;
    private Customer customer; // object (foreign key mapped to object)
    private Vehicle vehicle;   // object
    private Date bookingDate;
    private Date pickupDate;
    private Date returnDate;
    private String bookingStatus; // PENDING, CONFIRMED, CANCELLED

    public Booking() {}

    public Booking(String bookingId, Customer customer, Vehicle vehicle, Date bookingDate, Date pickupDate, Date returnDate, String bookingStatus) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.bookingDate = bookingDate;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.bookingStatus = bookingStatus;
    }

    // getters/setters
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    public Date getPickupDate() { return pickupDate; }
    public void setPickupDate(Date pickupDate) { this.pickupDate = pickupDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    @Override
    public String toString() {
        String cust = (customer != null) ? customer.getCustomerId()+"" : "N/A";
        String veh = (vehicle != null) ? vehicle.getVehicleId() : "N/A";
        return "BookingID: " + bookingId + " | VehicleID: " + veh +
                " | CustomerID: " + cust + " | Status: " + bookingStatus +
                " | Pickup: " + pickupDate + " | Return: " + returnDate;
    }
}
