package com.model;

public class Vehicle {
    private String vehicleId;
    private String make;
    private String model;
    private int year;
    private String vehicleType; // CAR/BIKE/VAN
    private String licensePlate;
    private double dailyRate;
    private String status; // AVAILABLE, BOOKED, IN_SERVICE
    private int mileage;

    public Vehicle() {}

    public Vehicle(String vehicleId, String make, String model, int year, String vehicleType, String licensePlate,
                   double dailyRate, String status, int mileage) {
        this.vehicleId = vehicleId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.vehicleType = vehicleType;
        this.licensePlate = licensePlate;
        this.dailyRate = dailyRate;
        this.status = status;
        this.mileage = mileage;
    }

    // getters/setters...
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public double getDailyRate() { return dailyRate; }
    public void setDailyRate(double dailyRate) { this.dailyRate = dailyRate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }

    @Override
    public String toString() {
        return String.format("%s | %s %s (%d) | %s | ₹%.2f/day | %s | %dkm",
                vehicleId, make, model, year, vehicleType, dailyRate, status, mileage);
    }
}
