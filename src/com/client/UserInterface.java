package com.client;

import com.model.*;
import com.service.*;
import com.util.ApplicationUtil;
import java.util.*;
import java.sql.Date;

public class UserInterface {
    private static Scanner sc = new Scanner(System.in);
    private static CustomerService customerService = new CustomerService();
    private static VehicleService vehicleService = new VehicleService();
    private static BookingService bookingService = new BookingService();
    private static BillingService billingService = new BillingService();

    public static void main(String[] args) throws Exception {
        showBanner();
        while (true) {
            System.out.println("\n\033[1;95m===== VEHICLE RENTAL BOOKING SYSTEM =====\033[0m");
            System.out.println("\033[1;94m1️⃣  Admin Login\033[0m");
            System.out.println("\033[1;94m2️⃣  Register Customer\033[0m");
            System.out.println("\033[1;94m3️⃣  Customer Login\033[0m");
            System.out.println("\033[1;94m4️⃣  Exit\033[0m");
            System.out.print("👉 Enter choice: ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1": adminLogin(); break;
                case "2": registerFlow(); break;
                case "3": customerLogin(); break;
                case "4":
                    System.out.println("\n👋 Goodbye! Visit again!");
                    return;
                default:
                    System.out.println("⚠ Invalid choice! Try again.");
            }
        }
    }

    private static void showBanner() {
        System.out.println("\n\033[1;93m==============================================\033[0m");
        System.out.println("\033[1;96m   🚘  WELCOME TO  🌟 VEHICLE RENTAL BOOKING SYSTEM (VRBS) 🌟  🚘\033[0m");
        System.out.println("\033[1;93m==============================================\033[0m\n");
    }

    // ADMIN
    private static void adminLogin() throws Exception {
        String user = ApplicationUtil.readLine("👤 Admin Username: ");
        String pass = ApplicationUtil.readLine("🔑 Password: ");
        if ("Stackers".equalsIgnoreCase(user) && "vrbs12345".equals(pass)) {
            System.out.println("\n👑 Admin login successful!");
            adminMenu();
        } else {
            System.out.println("❌ Invalid admin credentials.");
        }
    }

    private static void registerFlow() throws Exception {
        System.out.println("\n📝 REGISTER NEW CUSTOMER");

        // --- VALIDATED INPUTS ---
        String firstName = ApplicationUtil.readValidName("👤 First Name: ");
        String lastName = ApplicationUtil.readValidName("👤 Last Name: ");
        String email = ApplicationUtil.readValidEmail("📧 Email: ");
        String phone = ApplicationUtil.readValidPhone("📞 Phone (10 digits): ");
        String address = ApplicationUtil.readLine("🏠 Address: ");
        String license = ApplicationUtil.readValidLicense("🪪 License No (e.g., TN10AB1234): ");
        String password = ApplicationUtil.readValidPassword("🔑 Password (min 6 chars): ");

        // --- CREATE CUSTOMER OBJECT ---
        Customer c = new Customer();
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setEmail(email);
        c.setPhone(phone);
        c.setAddress(address);
        c.setDriverLicenseNumber(license);

        // --- REGISTER CUSTOMER ---
        String id = customerService.registerCustomer(c, password);
        if (id != null)
            ApplicationUtil.notifySuccess("Registration successful! Your Customer ID: " + id);
        else
            ApplicationUtil.notifyInvalid("Registration failed. Try again.");
    }


    private static void adminMenu() throws Exception {
        while (true) {
            System.out.println("\n\033[1;92m===== ADMIN MENU =====\033[0m");
            System.out.println("1️⃣  Vehicle Management");
            System.out.println("2️⃣  Customer Management");
            System.out.println("3️⃣  Booking Management");
            System.out.println("4️⃣  Billing Management");
            System.out.println("5️⃣  Logout");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": adminVehicleMenu(); break;
                case "2": adminCustomerMenu(); break;
                case "3": adminBookingMenu(); break;
                case "4": adminBillingMenu(); break;
                case "5": return;
                default: System.out.println("⚠ Invalid choice.");
            }
        }
    }

    // Vehicle menu
    private static void adminVehicleMenu() {
        System.out.println("\n🚗 VEHICLE MENU:");
        System.out.println("1. Add Vehicle");
//        System.out.println("2. Batch Add Vehicles (comma separated entries)");
        System.out.println("2. Update Vehicle Status");
        System.out.println("3. Delete Vehicle");
        System.out.println("4. List All Available");
        System.out.println("5. List By Type");
        System.out.println("6. Back");
        String ch = sc.nextLine().trim();

        switch (ch) {
            case "1": {
                String input = ApplicationUtil.readLine("Enter details (ID:Make:Model:Year:Type:Plate:Rate:Status:Mileage): ");
                String[] t = input.split(":");
                if (t.length == 9) {
                    try {
                        Vehicle v = new Vehicle(t[0], t[1], t[2], Integer.parseInt(t[3]), t[4].toUpperCase(), t[5],
                                Double.parseDouble(t[6]), t[7].toUpperCase(), Integer.parseInt(t[8]));
                        boolean ok = vehicleService.addVehicle(v);
                        if (ok) ApplicationUtil.notifySuccess("Vehicle added: " + v.getVehicleId());
                    } catch (Exception e) { System.out.println("⚠ Invalid values: " + e.getMessage()); }
                } else System.out.println("⚠ Invalid format.");
                break;
            }
//            case "2": {
//            	
//                String input = ApplicationUtil.readLine("Enter multiple vehicles (comma separated): ");
//                List<Vehicle> list = ApplicationUtil.parseVehicleList(input);
//                vehicleService.addMultipleVehicles(list);
//                break;
//                
//            }
            case "2": {
                String id = ApplicationUtil.readLine("Vehicle ID: ").trim();

                // Validate vehicle existence
                Vehicle existing = vehicleService.getVehicleById(id);
                if (existing == null) {
                    System.out.println("⚠ Invalid Vehicle ID!");
                    break;
                }

                // Prompt for new status
                String ns = ApplicationUtil.readLine("New Status (AVAILABLE/BOOKED/IN_SERVICE): ").trim().toUpperCase();

                // Validate status input
                if (!ns.equals("AVAILABLE") && !ns.equals("BOOKED") && !ns.equals("IN_SERVICE")) {
                    System.out.println("⚠ Invalid status! Allowed: AVAILABLE, BOOKED, IN_SERVICE.");
                    break;
                }

                // Proceed with update
                boolean updated = vehicleService.updateVehicleStatus(id, ns);

                if (updated)
                    System.out.println("✅ Vehicle updated successfully!");
                else
                    System.out.println("❌ Vehicle status update failed. Please try again.");

                break;
            }

            case "3": {
                String id = ApplicationUtil.readLine("Vehicle ID to delete: ");
                vehicleService.deleteVehicle(id);
                System.out.println("Vehicle "+id+" deleted successfully!");
                break;
            }
            case "4":
                vehicleService.listAvailableVehicles().forEach(System.out::println);
                break;
            case "5": {
                String type = ApplicationUtil.readLine("Type (CAR/BIKE/VAN): ");
                vehicleService.getVehiclesByType(type).forEach(System.out::println);
                break;
            }
            case "6": return;
            default: System.out.println("⚠ Invalid choice.");
        }
    }

    // Customer menu (admin)
    private static void adminCustomerMenu() {
        System.out.println("\n👥 CUSTOMER MENU:");
        System.out.println("1. List All");
        System.out.println("2. Retrieve by ID");
        System.out.println("3. Update Info");
        System.out.println("4. Delete");
        System.out.println("5. Back");
        String ch = sc.nextLine().trim();

        switch (ch) {
            case "1":
                customerService.listAllCustomers().forEach(System.out::println);
                break;
            case "2": {
                String id = ApplicationUtil.readLine("Customer ID: ");
                Customer c = customerService.getCustomerById(id);
                System.out.println(c != null ? c : "⚠ Customer not found.");
                break;
            }
            case "3": {
            	String id = ApplicationUtil.readLine("Customer ID: ");
            	Customer existing = customerService.getCustomerById(id);
            	if (existing == null) { 
            	    System.out.println("⚠ Invalid customer Id"); 
            	    break; 
            	}

            	String email = ApplicationUtil.readUpdatedEmail("📧 New Email (press enter to keep): ", existing.getEmail());
            	String phone = ApplicationUtil.readUpdatedPhone("📞 New Phone (press enter to keep): ", existing.getPhone());
            	String addr = ApplicationUtil.readUpdatedAddress("🏠 New Address (press enter to keep): ", existing.getAddress());

            	existing.setEmail(email);
            	existing.setPhone(phone);
            	existing.setAddress(addr);

            	customerService.updateCustomerInfo(id, email, phone, addr);
            	System.out.println("✅ Profile Updated Successfully!");

                
//                String email = ApplicationUtil.readLine("New Email (or press enter to keep): ");
//                String phone = ApplicationUtil.readLine("New Phone (or press enter to keep): ");
//                String addr = ApplicationUtil.readLine("New Address (or press enter to keep): ");
//                Customer existing = customerService.getCustomerById(id);
//                if (existing == null) { System.out.println("⚠ Invalid customer Id"); break; }
//                if (!email.isEmpty()) existing.setEmail(email);
//                if (!phone.isEmpty()) existing.setPhone(phone);
//                if (!addr.isEmpty()) existing.setAddress(addr);
//                customerService.updateCustomerInfo(id, existing.getEmail(), existing.getPhone(), existing.getAddress());
//                System.out.println("Profile updated successfully!");
                break;
            }
            case "4": {
                String id = ApplicationUtil.readLine("Customer ID to delete: ");
                customerService.deleteCustomer(id);
                System.out.println(id+" Customer Deleted Successfully!");
                break;
            }
            case "5": return;
            default: System.out.println("⚠ Invalid choice.");
        }
    }


 // Booking admin menu
    private static void adminBookingMenu() throws Exception {
        while (true) {
            System.out.println("\n🧾 BOOKING MENU:");
            System.out.println("1. Retrieve Booking Details");
            System.out.println("2. Update Booking Status");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Back");

            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1": {
                    String bid = ApplicationUtil.readLine("🔍 Enter Booking ID: ");
                    Booking booking = bookingService.getBookingById(bid);

                    if (booking == null) {
                        System.out.println("⚠ Invalid Booking ID or Customer not found!");
                    } else {
                        System.out.println(booking);
                    }
                    break;
                }

                case "2": {
                    String bid = ApplicationUtil.readLine("🆔 Enter Booking ID: ");
                    Booking booking = bookingService.getBookingById(bid);

                    if (booking == null) {
                        System.out.println("⚠ Cannot update — Booking not found!");
                        break;
                    }

                    // Get and validate new status
                    String newStatus = ApplicationUtil.readLine("✏ Enter New Status (PENDING / CONFIRMED / CANCELLED): ").trim();

                    // Call service and check result
                    boolean updated = bookingService.updateBookingStatus(bid, newStatus);

                    if (updated)
                        System.out.println("✅ Booking status updated successfully!");
                    else
                        System.out.println("❌ Update failed! Invalid or unaccepted status.");

                    break;
                }


                case "3": {
                    String bid = ApplicationUtil.readLine("🗑 Enter Booking ID to cancel: ");
                    Booking booking = bookingService.getBookingById(bid);

                    if (booking == null) {
                        System.out.println("⚠ Cannot cancel — Booking not found!");
                        break;
                    }

                    bookingService.cancelBooking(bid);
                    System.out.println("❌ Booking cancelled successfully!");
                    break;
                }

                case "4":
                    return;

                default:
                    System.out.println("⚠ Invalid option. Try again.");
            }
        }
    }


    // Billing admin menu
    private static void adminBillingMenu() {
        System.out.println("\n💰 BILLING MENU:");
        System.out.println("1. Create Invoice (admin)");
        System.out.println("2. Retrieve Invoice by Invoice ID");
        System.out.println("3. List All Invoices");
        System.out.println("4. Back");
        String ch = sc.nextLine().trim();

        switch (ch) {
            case "1": {
                String bookingId = ApplicationUtil.readLine("Booking ID: ");
                double rate = Double.parseDouble(ApplicationUtil.readLine("Daily Rate: "));
                String mode = ApplicationUtil.readLine("Payment Mode: ");
                billingService.createInvoice(bookingId, rate, mode);
                break;
            }
            case "2": {
                String id = ApplicationUtil.readLine("Invoice ID: ");
                Billing b = billingService.getBillingByInvoiceId(id);
                System.out.println(b != null ? b : "⚠ Invoice not found.");
                break;
            }
            case "3":
                billingService.getAllInvoices().forEach(System.out::println);
                break;
            case "4": return;
            default: System.out.println("⚠ Invalid choice.");
        }
    }

    // CUSTOMER flows
    private static void customerLogin() {
        String email = ApplicationUtil.readLine("📧 Email: ");
        String password = ApplicationUtil.readLine("🔑 Password: ");
        Customer c = customerService.login(email, password);
        if (c != null) {

            customerMenu(c);
        } else {
            System.out.println("❌ Invalid credentials or user not found.");
        }
    }
   //CUSTOMER MENU
    private static void customerMenu(final Customer c) {
        while (true) {
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. Create Booking");
            System.out.println("2. My Bookings");
            System.out.println("3. Cancel Booking");
            System.out.println("4. List Vehicles by Type");
            System.out.println("5. Update Profile");
            System.out.println("6. View My Invoice");
            System.out.println("7. Logout");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1": {
                    System.out.println("\n--- Available Vehicles ---");
                    vehicleService.listAvailableVehicles().forEach(System.out::println);

                    String veh = ApplicationUtil.readLine("🚗 Vehicle ID: ");
                    Date pickup = ApplicationUtil.toSqlDate(ApplicationUtil.readLine("📅 Pickup date (dd-MM-yyyy): "));
                    Date ret = ApplicationUtil.toSqlDate(ApplicationUtil.readLine("📅 Return date (dd-MM-yyyy): "));
                    

                    if (!ApplicationUtil.validateBookingDates(pickup, ret)) {
                        System.out.println(" Returning to menu...");
                        break;
                    }

                    if (!pickup.before(ret)) { System.out.println("⚠ Pickup date must be before return date"); break; }

                    Vehicle v = vehicleService.getVehicleById(veh);
                    if (v == null) { System.out.println("⚠ Invalid vehicle Id"); break; }

                    Booking b = new Booking(bookingService.generateBookingId(), c, v, new Date(System.currentTimeMillis()), pickup, ret, "CONFIRMED");
                    boolean booked = bookingService.createBooking(b);
                    if (!booked) { System.out.println("❌ Booking failed. Try again."); break; }

                    double rate = v.getDailyRate();
                    double amount = billingService.calculateBillAmount(pickup, ret, rate);

                    System.out.println("\n💳 Payment Details:");
                    System.out.println("Booking ID: " + b.getBookingId());
                    System.out.println("Vehicle ID: " + veh);
                    System.out.println("Pickup: " + ApplicationUtil.formatDate(pickup) + "  Return: " + ApplicationUtil.formatDate(ret));
                    System.out.println("Daily Rate: ₹" + rate);
                    System.out.println("Total Payable: ₹" + amount);

                    String confirm = ApplicationUtil.readLine("Proceed to payment? (yes/no): ");
                    if (!confirm.equalsIgnoreCase("yes")) {
                        System.out.println("❌ Booking cancelled before payment.");
                        bookingService.cancelBooking(b.getBookingId());
                        break;
                    }

                    String mode = ApplicationUtil.readLine("Payment Mode (CARD/UPI/CASH): ");
                    Billing invoice = billingService.createInvoice(b.getBookingId(), rate, mode);
                    if (invoice != null)
                        System.out.println("✅ Booking confirmed & invoice generated successfully!");
                    else
                        System.out.println("⚠ Booking confirmed but invoice failed!");
                    break;
                }

                case "2": {
                    List<Booking> myBookings = bookingService.getCustomerBookings(c.getCustomerId());
                    if (myBookings.isEmpty()) {
                        System.out.println("⚠ No bookings yet.");
                    } else myBookings.forEach(System.out::println);
                    break;
                }

                case "3": {
                    String bid = ApplicationUtil.readLine("Booking ID to cancel: ");
                    bookingService.cancelBooking(bid);
                    break;
                }

                case "4": {
                    String type = ApplicationUtil.readLine("Type (CAR/BIKE/VAN): ");
                    vehicleService.getVehiclesByType(type).forEach(System.out::println);
                    break;
                }

                case "5": {
                    Customer existing = customerService.getCustomerById(c.getCustomerId());
                    if (existing == null) { 
                        System.out.println("⚠ Invalid customer Id"); 
                        break; 
                    }

                    System.out.println("\n===== UPDATE PROFILE =====");

                    // Email
                    String email = ApplicationUtil.readLine("📧 New Email (press enter to keep): ");
                    if (!email.trim().isEmpty()) {
                        while (!ApplicationUtil.isValidEmail(email)) {
                            System.out.println("⚠ Invalid email format! Please enter a valid one (e.g., name@example.com)");
                            email = ApplicationUtil.readLine("📧 New Email (press enter to keep): ");
                            if (email.trim().isEmpty()) {
                                email = existing.getEmail(); // if user presses enter mid-way
                                break;
                            }
                        }
                        existing.setEmail(email);
                    }

                    // Phone
                    String phone = ApplicationUtil.readLine("📞 New Phone (press enter to keep): ");
                    if (!phone.trim().isEmpty()) {
                        while (!ApplicationUtil.isValidPhone(phone)) {
                            System.out.println("⚠ Invalid phone number! It must be 10 digits starting with 6-9.");
                            phone = ApplicationUtil.readLine("📞 New Phone (press enter to keep): ");
                            if (phone.trim().isEmpty()) {
                                phone = existing.getPhone();
                                break;
                            }
                        }
                        existing.setPhone(phone);
                    }

                    // Address (no strict validation)
                    String addr = ApplicationUtil.readLine("🏠 New Address (press enter to keep): ");
                    if (!addr.trim().isEmpty()) {
                        existing.setAddress(addr);
                    }

                    // Now update DB
                    customerService.updateCustomerInfo(c.getCustomerId(), existing.getEmail(), existing.getPhone(), existing.getAddress());
                    System.out.println("✅ Profile Updated Successfully!");
                    break;
                }


                case "6": {
                    String bookingId = ApplicationUtil.readLine("Enter Booking ID to view invoice: ");
                    Billing bill = billingService.getBillingByBookingId(bookingId);
                    System.out.println(bill != null ? bill : "⚠ No invoice found for booking.");
                    break;
                }

                case "7": System.out.println("👋 Logged out!"); return;
                default: System.out.println("⚠ Invalid choice.");
            }
        }
    }
}
