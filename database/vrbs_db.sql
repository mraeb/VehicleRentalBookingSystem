-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 28, 2026 at 11:26 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vrbs_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `billing`
--

CREATE TABLE `billing` (
  `invoiceId` varchar(10) NOT NULL,
  `bookingId` varchar(10) DEFAULT NULL,
  `totalDays` int(11) DEFAULT NULL,
  `totalAmount` double DEFAULT NULL,
  `paymentDate` date DEFAULT NULL,
  `paymentMode` varchar(20) DEFAULT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `billing`
--

INSERT INTO `billing` (`invoiceId`, `bookingId`, `totalDays`, `totalAmount`, `paymentDate`, `paymentMode`, `createdAt`) VALUES
('INV6A6163', 'B308F22', 12, 18000, '2026-01-28', 'UPI', '2026-01-28 09:15:07');

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `bookingId` varchar(10) NOT NULL,
  `customerId` varchar(10) DEFAULT NULL,
  `vehicleId` varchar(10) DEFAULT NULL,
  `bookingDate` date DEFAULT NULL,
  `pickupDate` date DEFAULT NULL,
  `returnDate` date DEFAULT NULL,
  `bookingStatus` varchar(20) DEFAULT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `updatedAt` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`bookingId`, `customerId`, `vehicleId`, `bookingDate`, `pickupDate`, `returnDate`, `bookingStatus`, `createdAt`, `updatedAt`) VALUES
('B308F22', 'CUST001', 'VEH001', '2026-01-28', '2026-01-29', '2026-02-10', 'CONFIRMED', '2026-01-28 09:13:06', '2026-01-28 09:13:06');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customerId` varchar(10) NOT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `driverLicenseNumber` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `updatedAt` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customerId`, `firstName`, `lastName`, `email`, `phone`, `address`, `driverLicenseNumber`, `password`, `createdAt`, `updatedAt`) VALUES
('CUST001', 'balaji', 's', 'mraebs@gmail.com', '9843170602', 'adm', 'TN98BD9874', 'mraeb6', '2026-01-28 07:48:50', '2026-01-28 07:48:50');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `vehicleId` varchar(10) NOT NULL,
  `make` varchar(30) DEFAULT NULL,
  `model` varchar(50) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `vehicleType` varchar(20) DEFAULT NULL,
  `licensePlate` varchar(30) DEFAULT NULL,
  `dailyRate` double DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `mileage` int(11) DEFAULT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `updatedAt` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`vehicleId`, `make`, `model`, `year`, `vehicleType`, `licensePlate`, `dailyRate`, `status`, `mileage`, `createdAt`, `updatedAt`) VALUES
('VEH001', 'Hyundai', 'i20', 2022, 'CAR', 'TN10AB1234', 1500, 'BOOKED', 18, '2026-01-28 08:21:42', '2026-01-28 09:13:06'),
('VEH002', 'Honda', 'City', 2021, 'SEDAN', 'TN09AB9876', 1800, 'AVAILABLE', 16, '2026-01-28 08:21:55', '2026-01-28 08:21:55'),
('VEH003', 'Yamaha', 'FZ', 2021, 'BIKE', 'TN09CD5678', 500, 'AVAILABLE', 45, '2026-01-28 08:21:15', '2026-01-28 08:21:15'),
('VEH004', 'Tata', 'Nexon', 2023, 'SUV', 'TN11EF9012', 2200, 'AVAILABLE', 17, '2026-01-28 08:22:24', '2026-01-28 08:22:24'),
('VEH005', 'Yamaha', 'FZ', 2021, 'BIKE', 'TN09CD5678', 500, 'AVAILABLE', 45, '2026-01-28 08:22:36', '2026-01-28 08:22:36'),
('VEH006', 'RoyalEnfield', 'Classic350', 2022, 'BIKE', 'TN10GH4321', 650, 'AVAILABLE', 35, '2026-01-28 08:22:47', '2026-01-28 08:22:47'),
('VEH007', 'Honda', 'City', 2021, 'SEDAN', 'TN09AB9876', 1800, 'AVAILABLE', 16, '2026-01-28 07:47:04', '2026-01-28 07:47:04'),
('VEH008', 'Hyundai', 'Venue', 2022, 'SUV', 'TN10AA8008', 1900, 'AVAILABLE', 18, '2026-01-28 08:24:13', '2026-01-28 08:24:13'),
('VEH009', 'Maruti', 'Baleno', 2021, 'CAR', 'TN09BB9009', 1500, 'AVAILABLE', 20, '2026-01-28 08:24:22', '2026-01-28 08:24:22'),
('VEH010', 'Honda', 'Amaze', 2020, 'SEDAN', 'TN07CC1010', 1600, 'AVAILABLE', 17, '2026-01-28 08:24:37', '2026-01-28 08:24:37'),
('VEH011', 'Tata', 'Altroz', 2022, 'CAR', 'TN11DD1111', 1700, 'AVAILABLE', 19, '2026-01-28 08:25:01', '2026-01-28 08:25:01'),
('VEH012', 'Toyota', 'Innova', 2023, 'SUV', 'TN12EE1212', 2500, 'AVAILABLE', 14, '2026-01-28 08:25:13', '2026-01-28 08:25:13'),
('VEH013', 'Kia', 'Seltos', 2022, 'SUV', 'TN08FF1313', 2300, 'AVAILABLE', 16, '2026-01-28 08:25:31', '2026-01-28 08:25:31'),
('VEH014', 'Ford', 'Ecosport', 2021, 'SUV', 'TN06GG1414', 2000, 'AVAILABLE', 15, '2026-01-28 08:25:58', '2026-01-28 08:25:58'),
('VEH015', 'Hyundai', 'Verna', 2021, 'SEDAN', 'TN05HH1515', 1800, 'AVAILABLE', 16, '2026-01-28 08:26:09', '2026-01-28 08:26:09'),
('VEH016', 'RoyalEnfield', 'Classic350', 2022, 'BIKE', 'TN04JJ1616', 650, 'AVAILABLE', 35, '2026-01-28 08:26:18', '2026-01-28 08:26:18'),
('VEH017', 'Yamaha', 'R15', 2023, 'BIKE', 'TN03KK1717', 700, 'AVAILABLE', 40, '2026-01-28 08:26:26', '2026-01-28 08:26:26'),
('VEH018', 'TVS', 'ApacheRTR', 2022, 'BIKE', 'TN02LL1818', 600, 'AVAILABLE', 38, '2026-01-28 08:26:53', '2026-01-28 08:26:53'),
('VEH019', 'Bajaj', 'PulsarNS200', 2021, 'BIKE', 'TN01MM1919', 550, 'AVAILABLE', 42, '2026-01-28 08:27:02', '2026-01-28 08:27:02'),
('VEH020', 'Tata', 'Punch', 2023, 'CAR', 'TN13NN2020', 1600, 'AVAILABLE', 20, '2026-01-28 08:27:13', '2026-01-28 08:27:13'),
('VEH021', 'Hyundai', 'Exter', 2023, 'SUV', 'TN14AA2121', 1850, 'AVAILABLE', 19, '2026-01-28 08:28:51', '2026-01-28 08:28:51'),
('VEH022', 'Skoda', 'Slavia', 2022, 'SEDAN', 'TN15BB2222', 2000, 'AVAILABLE', 17, '2026-01-28 08:29:00', '2026-01-28 08:29:00'),
('VEH023', 'Volkswagen', 'Virtus', 2022, 'SEDAN', 'TN16CC2323', 2100, 'AVAILABLE', 16, '2026-01-28 08:29:08', '2026-01-28 08:29:08'),
('VEH024', 'Mahindra', 'XUV300', 2023, 'SUV', 'TN17DD2424', 2300, 'AVAILABLE', 15, '2026-01-28 08:29:15', '2026-01-28 08:29:15'),
('VEH025', 'Hero', 'Xpulse200', 2022, 'BIKE', 'TN18EE2525', 650, 'AVAILABLE', 35, '2026-01-28 08:29:23', '2026-01-28 08:29:23');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `billing`
--
ALTER TABLE `billing`
  ADD PRIMARY KEY (`invoiceId`),
  ADD KEY `bookingId` (`bookingId`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`bookingId`),
  ADD KEY `customerId` (`customerId`),
  ADD KEY `vehicleId` (`vehicleId`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customerId`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`vehicleId`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `billing`
--
ALTER TABLE `billing`
  ADD CONSTRAINT `billing_ibfk_1` FOREIGN KEY (`bookingId`) REFERENCES `booking` (`bookingId`);

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`),
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`vehicleId`) REFERENCES `vehicle` (`vehicleId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
