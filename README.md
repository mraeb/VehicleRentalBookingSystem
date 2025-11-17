<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>

<body>

  <div class="logo">
    <h1>🚗 Vehicle Rental Booking System (VRBS)</h1>
  </div>

  <div class="section">
    <p>
      A Java-based full-stack console application designed to manage the complete workflow
      of a vehicle rental service — including customers, vehicles, bookings, billing, and payments.
      The system integrates MySQL using JDBC and is structured using modular, clean coding practices.
    </p>
  </div>

  <hr />

  <div class="section">
    <h2>✨ Features</h2>
    <h3>✔ Vehicle Management</h3>
    <ul>
      <li>Add, update, delete, and view vehicles</li>
      <li>Track availability (Available / Booked / In Service)</li>
      <li>Supports Cars, Bikes, and Vans</li>
    </ul>
    <h3>✔ Customer Management</h3>
    <ul>
      <li>Store and update customer information</li>
      <li>Manage phone, address, and license details</li>
      <li>Retrieve customer by ID</li>
    </ul>
    <h3>✔ Booking Management</h3>
    <ul>
      <li>Real-time availability checking</li>
      <li>Create, cancel, update bookings</li>
      <li>Prevents overlapping reservations</li>
      <li>Auto calculates rental duration</li>
    </ul>
    <h3>✔ Billing & Payment</h3>
    <ul>
      <li>Auto-generated invoice</li>
      <li>Calculates charges based on daily rate</li>
      <li>Supports Cash, Card, and Online payments</li>
    </ul>
  </div>

  <hr />

  <div class="section">
    <h2>🛠 Tech Stack</h2>
    <ul>
      <li><strong>Languages:</strong> Java, SQL</li>
      <li><strong>Tools:</strong> JDBC, MySQL, XAMPP, Eclipse IDE</li>
      <li><strong>Concepts:</strong> OOP, Exception Handling, File Handling, Modular Design</li>
    </ul>
  </div>

  <hr />

  <div class="section">
    <h2>📂 Project Modules</h2>
    <ul>
      <li><strong>Customer Module</strong> – CRUD operations</li>
      <li><strong>Vehicle Module</strong> – Manage types, rates, availability</li>
      <li><strong>Booking Module</strong> – Create & validate reservations</li>
      <li><strong>Billing Module</strong> – Generate invoices & payments</li>
      <li><strong>DB Connection Manager</strong> – Central JDBC handler</li>
      <li><strong>Exception Module</strong> – Custom validations</li>
      <li><strong>Utility Module</strong> – Date formatter, parsers</li>
    </ul>
  </div>

  <hr />

  <div class="section">
    <h2>🧾 Database Tables</h2>
    <h3>Vehicle Table</h3>
    <code>vehicleId, make, model, year, vehicleType, licensePlate, dailyRate, status, mileage</code>
    <h3>Customer Table</h3>
    <code>customerId, firstName, lastName, email, phone, address, license</code>
    <h3>Booking Table</h3>
    <code>bookingId, customerId, vehicleId, bookingDate, pickupDate, returnDate, status</code>
    <h3>Billing Table</h3>
    <code>invoiceId, bookingId, totalDays, totalAmount, paymentDate, paymentMode</code>
  </div>

  <hr />

  <div class="section">
    <h2>▶️ How to Run</h2>
    <ul>
      <li>Install <strong>Eclipse IDE</strong></li>
      <li>Install <strong>XAMPP</strong> and start MySQL</li>
      <li>Import SQL tables into MySQL</li>
      <li>Add <strong>MySQL Connector JAR</strong> to project build path</li>
      <li>Clone/download the project</li>
      <li>Run the main Java file</li>
    </ul>
  </div>

  <hr />

  <div class="section">
    <h2>🎯 Purpose of the Project</h2>
    <p>
      This system automates vehicle rental operations and demonstrates core Java + MySQL skills.
    </p>
  </div>

  <hr />

  <div class="section">
    <h2>👨‍💻 Developer</h2>
    <p><strong>Balaji S</strong><br />
      Aspiring Software Engineer<br />
      Portfolio: <a href="https://www.mraeb.in">https://www.mraeb.in</a><br />
      Email: <a href="mailto:mraebs@gmail.com">mraebs@gmail.com</a><br />
      GitHub: <a href="https://github.com/mraeb">https://github.com/mraeb</a>
    </p>
  </div>

</body>
</html>
