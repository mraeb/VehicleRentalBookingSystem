package com.service;

import com.management.VehicleManagement;
import com.model.Vehicle;
import com.util.ApplicationUtil;
import java.util.*;
import java.util.UUID;

public class VehicleService {
    private VehicleManagement dao = new VehicleManagement();

    public List<Vehicle> buildVehicleList(String input) { return ApplicationUtil.parseVehicleList(input); }
    public String generateVehicleId() { return "V" + UUID.randomUUID().toString().substring(0,6).toUpperCase(); }

    public boolean addVehicle(Vehicle v) {
        if (v == null) return false;
        if (!ApplicationUtil.isValidLicense(v.getLicensePlate())) { System.out.println("⚠ Invalid license plate"); return false; }
        if (v.getVehicleId() == null || v.getVehicleId().trim().isEmpty()) v.setVehicleId(generateVehicleId());
        if (dao.isVehicleExists(v.getVehicleId())) { System.out.println("⚠ VehicleId exists"); return false; }
        return dao.insertVehicle(v);
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> list = dao.getAvailableVehicles();
        if (list.isEmpty()) System.out.println("⚠ No vehicles available right now.");
        return list;
    }

    public double getVehicleRateById(String vehicleId) {
        try {
            Vehicle v = dao.getVehicleById(vehicleId);
            if (v != null) return v.getDailyRate();
            else { System.out.println("⚠ Vehicle not found for ID: " + vehicleId); return 0.0; }
        } catch (Exception e) {
            System.out.println("⚠ getVehicleRateById Error: " + e.getMessage());
            return 0.0;
        }
    }

    public boolean addMultipleVehicles(List<Vehicle> list) {
        if (list == null || list.isEmpty()) { System.out.println("⚠ No vehicles to add"); return false; }
        for (Vehicle v : list) {
            if (v.getVehicleId() == null || v.getVehicleId().trim().isEmpty()) v.setVehicleId(generateVehicleId());
        }
        return dao.insertVehicleBatch(list);
    }
    public boolean updateVehicleStatus(String vehicleId, String newStatus) { return dao.updateVehicleStatus(vehicleId, newStatus); }
    public boolean deleteVehicle(String vehicleId) { return dao.deleteVehicle(vehicleId); }
    public Vehicle getVehicleById(String id) { return dao.getVehicleById(id); }
    public List<Vehicle> getVehiclesByType(String type) { return dao.getVehiclesByType(type.toUpperCase()); }
    public List<Vehicle> listAvailableVehicles() { return dao.getAvailableVehicles(); }
    public List<Vehicle> listAllVehicles() { return dao.getAllVehicles(); }
}
