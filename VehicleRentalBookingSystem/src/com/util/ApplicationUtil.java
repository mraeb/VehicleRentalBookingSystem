package com.util;

import com.model.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;

public class ApplicationUtil {
    private static final Scanner sc = new Scanner(System.in);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    // INPUT HELPERS
    public static String readLine(String prompt) {
        System.out.print(prompt);
        String line = sc.nextLine();
        if (line == null) return "";
        return line.trim();
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("⚠ Invalid number, try again.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("⚠ Invalid amount, try again.");
            }
        }
    }

    // DATE HELPERS
    public static Date toSqlDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        try {
            java.util.Date d = sdf.parse(dateStr.trim());
            return new Date(d.getTime());
        } catch (ParseException e) {
            System.out.println("⚠ Invalid date format! Use dd-MM-yyyy.");
            return null;
        }
    }

    public static String formatDate(Date date) {
        return (date == null) ? "N/A" : sdf.format(date);
    }

    public static long daysBetween(Date start, Date end) {
        if (start == null || end == null) return 0;
        long diff = end.getTime() - start.getTime();
        return diff / (1000 * 60 * 60 * 24);
    }

    // PARSERS
    // Vehicle input string: ID:Make:Model:Year:Type:Plate:Rate:Status:Mileage
    public static List<Vehicle> parseVehicleList(String input) {
        List<Vehicle> list = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) return list;
        String[] entries = input.split(",");
        for (String entry : entries) {
            String[] t = entry.trim().split(":");
            if (t.length == 9) {
                try {
                    Vehicle v = new Vehicle(t[0], t[1], t[2], Integer.parseInt(t[3]), t[4].toUpperCase(), t[5],
                            Double.parseDouble(t[6]), t[7].toUpperCase(), Integer.parseInt(t[8]));
                    list.add(v);
                } catch (Exception e) {
                    System.out.println("⚠ Invalid vehicle entry: " + entry);
                }
            } else {
                System.out.println("⚠ Skipped malformed vehicle: " + entry);
            }
        }
        return list;
    }

    // Customer input string: firstName:lastName:email:phone:address:driverLicense:password
    public static List<Customer> parseCustomerList(String input) {
        List<Customer> list = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) return list;
        String[] entries = input.split(",");
        for (String entry : entries) {
            String[] t = entry.trim().split(":");
            if (t.length == 7) {
                try {
                    Customer c = new Customer();
                    c.setFirstName(t[0]);
                    c.setLastName(t[1]);
                    c.setEmail(t[2]);
                    c.setPhone(t[3]);
                    c.setAddress(t[4]);
                    c.setDriverLicenseNumber(t[5]);
                    c.setPassword(t[6]);
                    list.add(c);
                } catch (Exception e) {
                    System.out.println("⚠ Invalid customer entry: " + entry);
                }
            } else {
                System.out.println("⚠ Skipped malformed customer: " + entry);
            }
        }
        return list;
    }

    // Booking parsing: bookingId:customerId:vehicleId:bookingDate:pickupDate:returnDate:status
    public static List<Booking> parseBookingList(String input) {
        List<Booking> list = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) return list;
        String[] entries = input.split(",");
        for (String entry : entries) {
            String[] t = entry.trim().split(":");
            if (t.length == 7) {
                try {
                    Booking b = new Booking();
                    b.setBookingId(t[0]);
                    Customer c = new Customer();
                    c.setCustomerId(t[1]);
                    b.setCustomer(c);
                    Vehicle v = new Vehicle();
                    v.setVehicleId(t[2]);
                    b.setVehicle(v);
                    b.setBookingDate(toSqlDate(t[3]));
                    b.setPickupDate(toSqlDate(t[4]));
                    b.setReturnDate(toSqlDate(t[5]));
                    b.setBookingStatus(t[6]);
                    list.add(b);
                } catch (Exception e) {
                    System.out.println("⚠ Invalid booking entry: " + entry);
                }
            } else {
                System.out.println("⚠ Skipped malformed booking: " + entry);
            }
        }
        return list;
    }

    // Billing parsing: invoiceId:bookingId:totalDays:totalAmount:paymentDate:paymentMode
    public static List<Billing> parseBillingList(String input) {
        List<Billing> list = new ArrayList<>();
        if (input == null || input.trim().isEmpty()) return list;
        String[] entries = input.split(",");
        for (String entry : entries) {
            String[] t = entry.trim().split(":");
            if (t.length == 6) {
                try {
                    Billing b = new Billing();
                    b.setInvoiceId(t[0]);
                    Booking bk = new Booking(); bk.setBookingId(t[1]);
                    b.setBooking(bk);
                    b.setTotalDays(Integer.parseInt(t[2]));
                    b.setTotalAmount(Double.parseDouble(t[3]));
                    b.setPaymentDate(toSqlDate(t[4]));
                    b.setPaymentMode(t[5]);
                    list.add(b);
                } catch (Exception e) {
                    System.out.println("⚠ Invalid billing entry: " + entry);
                }
            } else {
                System.out.println("⚠ Skipped malformed billing: " + entry);
            }
        }
        return list;
    }

    // VALIDATIONS
    public static boolean isValidLicense(String plate) {
        return plate != null && plate.matches("^[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}$");
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^[6-9][0-9]{9}$");
    }

    public static boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-z ]+$");
    }

    // UI helpers
    public static void notifyInvalid(String msg) {
        System.out.println("⚠ " + msg);
    }

    public static void notifySuccess(String msg) {
        System.out.println("✅ " + msg);
    }
    
 // Validate that date is not before today
    public static boolean isDateInFutureOrToday(Date date) {
        if (date == null) return false;
        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
        return !date.before(today);
    }

    public static boolean validateBookingDates(Date pickup, Date ret) {
        if (pickup == null || ret == null) {
            System.out.println("⚠ Date input missing. Booking not executed.");
            return false;
        }

        if (!isDateInFutureOrToday(pickup) || !isDateInFutureOrToday(ret)) {
            System.out.println("⚠ Date input not valid! Booking not executed.");
            return false;
        }

        if (pickup.after(ret)) {
            System.out.println("⚠ Pickup date cannot be after return date. Booking not executed.");
            return false;
        }

        return true;
    }
    
    
 // === VALIDATED INPUT HELPERS ===

 // Read and validate email input
 public static String readValidEmail(String prompt) {
     while (true) {
         String input = readLine(prompt);
         if (isValidEmail(input)) return input;
         System.out.println("⚠ Invalid email format. Please enter a valid email (e.g. name@example.com).");
     }
 }

 // Read and validate phone number input
 public static String readValidPhone(String prompt) {
     while (true) {
         String input = readLine(prompt);
         if (isValidPhone(input)) return input;
         System.out.println("⚠ Invalid phone number! Enter a 10-digit number starting with 6-9.");
     }
 }

 // Read and validate license number
 public static String readValidLicense(String prompt) {
     while (true) {
         String input = readLine(prompt);
         if (isValidLicense(input)) return input;
         System.out.println("⚠ Invalid license format! Expected format: TN10AB1234.");
     }
 }

 // Read and validate name input
 public static String readValidName(String prompt) {
     while (true) {
         String input = readLine(prompt);
         if (isValidName(input)) return input;
         System.out.println("⚠ Invalid name! Use only alphabets and spaces.");
     }
 }

 // Read and validate password (min 6 chars)
 public static String readValidPassword(String prompt) {
     while (true) {
         String input = readLine(prompt);
         if (input.length() >= 6) return input;
         System.out.println("⚠ Password must be at least 6 characters long.");
     }
 }

 // Read and validate date format
 public static Date readValidDate(String prompt) {
     while (true) {
         String input = readLine(prompt);
         Date date = toSqlDate(input);
         if (date != null) return date;
         System.out.println("⚠ Please re-enter a valid date (format: dd-MM-yyyy).");
     }
 }
 
//=== VALIDATED UPDATE INPUTS (allow empty for keeping old value) ===

//Update Email (allow keeping old if blank)
public static String readUpdatedEmail(String prompt, String currentEmail) {
  while (true) {
      String input = readLine(prompt);
      if (input.trim().isEmpty()) return currentEmail; // keep old
      if (isValidEmail(input)) return input;
      System.out.println("⚠ Invalid email format. Try again (e.g. name@example.com).");
  }
}

//Update Phone
public static String readUpdatedPhone(String prompt, String currentPhone) {
  while (true) {
      String input = readLine(prompt);
      if (input.trim().isEmpty()) return currentPhone;
      if (isValidPhone(input)) return input;
      System.out.println("⚠ Invalid phone number! Enter 10 digits starting with 6-9.");
  }
}

//Update Name
public static String readUpdatedName(String prompt, String currentName) {
  while (true) {
      String input = readLine(prompt);
      if (input.trim().isEmpty()) return currentName;
      if (isValidName(input)) return input;
      System.out.println("⚠ Invalid name! Use alphabets and spaces only.");
  }
}

//Update Address (no pattern restriction)
public static String readUpdatedAddress(String prompt, String currentAddress) {
  String input = readLine(prompt);
  return input.trim().isEmpty() ? currentAddress : input;
}

//Update License
public static String readUpdatedLicense(String prompt, String currentLicense) {
  while (true) {
      String input = readLine(prompt);
      if (input.trim().isEmpty()) return currentLicense;
      if (isValidLicense(input)) return input;
      System.out.println("⚠ Invalid license format! Expected: TN10AB1234.");
  }
}




}
