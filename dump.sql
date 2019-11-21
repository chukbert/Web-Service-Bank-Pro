-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Nov 21, 2019 at 12:34 PM
-- Server version: 5.7.25
-- PHP Version: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `ws-bank`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `nama` varchar(255) NOT NULL,
  `no_rekening` int(255) NOT NULL,
  `balance` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`nama`, `no_rekening`, `balance`) VALUES
('faiz', 1, 286000),
('sekar', 2, 270000);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(10) NOT NULL,
  `id_account` int(10) NOT NULL,
  `type` varchar(10) NOT NULL,
  `amount` int(255) NOT NULL,
  `account_number` int(255) NOT NULL,
  `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `id_account`, `type`, `amount`, `account_number`, `time`) VALUES
(1, 1, 'DEBIT', 20000, 2, '2019-11-19 14:52:35'),
(2, 1, 'CREDIT', 10000, 2, '2019-11-19 14:57:16'),
(3, 2, 'DEBIT', 10000, 1, '2019-11-19 14:57:16'),
(4, 2, 'CREDIT', 20000, 1, '2019-11-19 15:56:20'),
(5, 1, 'DEBIT', 20000, 2, '2019-11-19 15:56:20'),
(6, 1, 'CREDIT', 2000, 2, '2019-11-19 20:10:07'),
(7, 2, 'DEBIT', 2000, 1, '2019-11-19 20:10:07');

-- --------------------------------------------------------

--
-- Table structure for table `virtual_account`
--

CREATE TABLE `virtual_account` (
  `no_rekening` int(255) NOT NULL,
  `no_virtual_account` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `virtual_account`
--

INSERT INTO `virtual_account` (`no_rekening`, `no_virtual_account`) VALUES
(1, 9000),
(1, 9001),
(1, 9002),
(1, 9003),
(1, 9004);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
