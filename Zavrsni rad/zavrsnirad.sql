-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 10, 2024 at 06:39 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `zavrsnirad`
--

-- --------------------------------------------------------

--
-- Table structure for table `drzava`
--

DROP TABLE IF EXISTS `drzava`;
CREATE TABLE IF NOT EXISTS `drzava` (
  `drz_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `drz_drzava` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  PRIMARY KEY (`drz_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

--
-- Dumping data for table `drzava`
--

INSERT INTO `drzava` (`drz_id`, `drz_drzava`) VALUES
(1, 'srbija'),
(2, 'BIH'),
(12, 'bugarska');

-- --------------------------------------------------------

--
-- Table structure for table `grad`
--

DROP TABLE IF EXISTS `grad`;
CREATE TABLE IF NOT EXISTS `grad` (
  `gra_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gra_grad` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `drz_id` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`gra_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

--
-- Dumping data for table `grad`
--

INSERT INTO `grad` (`gra_id`, `gra_grad`, `drz_id`) VALUES
(1, 'kragujevac', 1),
(2, 'beograd', 1),
(3, 'sarajevo', 2),
(4, ' mostar', 2),
(9, 'novi sad', 1),
(10, 'Plovdiv', 12),
(11, 'asdfasdf', 12);

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
CREATE TABLE IF NOT EXISTS `korisnik` (
  `kor_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `kor_ime` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `kor_prezime` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `kor_email` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `kor_sifra` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `kor_username` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `gra_id` int(10) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`kor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`kor_id`, `kor_ime`, `kor_prezime`, `kor_email`, `kor_sifra`, `kor_username`, `gra_id`) VALUES
(21, 'mirkoo', 'petrovic', 'mirko@asgd.com', 'pass', 'mirko', 11);

-- --------------------------------------------------------

--
-- Table structure for table `kviz`
--

DROP TABLE IF EXISTS `kviz`;
CREATE TABLE IF NOT EXISTS `kviz` (
  `kvi_id` int(11) NOT NULL AUTO_INCREMENT,
  `usl_id` int(10) UNSIGNED DEFAULT NULL,
  `kvi_naziv` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `kvi_datum` date DEFAULT NULL,
  `pitanja` json DEFAULT NULL,
  PRIMARY KEY (`kvi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

--
-- Dumping data for table `kviz`
--

INSERT INTO `kviz` (`kvi_id`, `usl_id`, `kvi_naziv`, `kvi_datum`, `pitanja`) VALUES
(14, 12, '', '2024-02-02', '[{\"tacan\": null, \"pitanje\": \"\", \"odgovori\": [\"\", \"\", \"\", \"\"]}]'),
(15, 13, '', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asdf\"]}, {\"tacan\": 2, \"pitanje\": \"sdaf\", \"odgovori\": [\"asdf\", \"adsf\", \"asdf\", \"fds\"]}, {\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"adsf\", \"adsf\", \"fads\", \"fas\"]}]'),
(16, 14, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(17, 15, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(18, 16, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(19, 18, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(20, 17, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(21, 19, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(22, 20, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(23, 21, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(24, 22, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(25, 24, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(26, 25, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(27, 23, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(28, 26, 'asdf', '2024-02-02', '[{\"tacan\": 2, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"asdf\", \"asdf\", \"asfd\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"fasd\", \"sd\", \"asdaf\", \"af\"]}]'),
(29, 27, '', '2024-02-03', '[{\"tacan\": 2, \"pitanje\": \"asdfa\", \"odgovori\": [\"sadf\", \"asdf\", \"asdf\", \"asdf\"]}, {\"tacan\": 1, \"pitanje\": \"asdf\", \"odgovori\": [\"asdf\", \"df\", \"asfd\", \"asf\"]}]'),
(30, 28, 'asdfas naziv', '2024-02-03', '[{\"tacan\": 2, \"pitanje\": \"sdfa\", \"odgovori\": [\"sdf\", \"asdf\", \"sdf\", \"as\"]}, {\"tacan\": 2, \"pitanje\": \"fds\", \"odgovori\": [\"fds\", \"dfs\", \"afsd\", \"asdf\"]}]'),
(31, 29, '', '2024-02-03', '[{\"tacan\": 2, \"pitanje\": \"sdfa\", \"odgovori\": [\"sdf\", \"asdf\", \"sdf\", \"as\"]}, {\"tacan\": 2, \"pitanje\": \"fds\", \"odgovori\": [\"fds\", \"dfs\", \"afsd\", \"asdf\"]}]'),
(32, 30, '', '2024-02-03', '[{\"tacan\": 2, \"pitanje\": \"sdfa\", \"odgovori\": [\"sdf\", \"asdf\", \"sdf\", \"as\"]}, {\"tacan\": 2, \"pitanje\": \"fds\", \"odgovori\": [\"fds\", \"dfs\", \"afsd\", \"asdf\"]}]'),
(33, 32, '', '2024-02-10', '[{\"tacan\": 1, \"pitanje\": \"afasdf\", \"odgovori\": [\"asdf\", \"tacnoi\", \"asdfasdf\", \"asd\"]}, {\"tacan\": 2, \"pitanje\": \"adsfasd\", \"odgovori\": [\"fasd\", \"fasd\", \"fas\", \"dfa\"]}]');

-- --------------------------------------------------------

--
-- Table structure for table `oblast`
--

DROP TABLE IF EXISTS `oblast`;
CREATE TABLE IF NOT EXISTS `oblast` (
  `obl_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `obl_naziv` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  PRIMARY KEY (`obl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

--
-- Dumping data for table `oblast`
--

INSERT INTO `oblast` (`obl_id`, `obl_naziv`) VALUES
(1, 'matematika'),
(2, 'nauka'),
(3, 'programiranje');

-- --------------------------------------------------------

--
-- Table structure for table `prijava`
--

DROP TABLE IF EXISTS `prijava`;
CREATE TABLE IF NOT EXISTS `prijava` (
  `pri_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `kor_id` int(10) UNSIGNED DEFAULT NULL,
  `usl_id` int(10) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`pri_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

--
-- Dumping data for table `prijava`
--

INSERT INTO `prijava` (`pri_id`, `kor_id`, `usl_id`) VALUES
(8, 21, 11),
(9, 21, 32);

-- --------------------------------------------------------

--
-- Table structure for table `recenzije`
--

DROP TABLE IF EXISTS `recenzije`;
CREATE TABLE IF NOT EXISTS `recenzije` (
  `rec_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `kor_id` int(10) UNSIGNED DEFAULT NULL,
  `usl_id` int(10) UNSIGNED DEFAULT NULL,
  `rec_recenzija` longtext COLLATE utf8mb4_unicode_520_ci,
  `rec_ocena` tinyint(3) DEFAULT NULL,
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

--
-- Dumping data for table `recenzije`
--

INSERT INTO `recenzije` (`rec_id`, `kor_id`, `usl_id`, `rec_recenzija`, `rec_ocena`) VALUES
(1, 21, 11, 'asdfasdf', 5),
(2, 21, 11, 'asdf', 3),
(3, 21, 11, 'moja nova recenzija', 10),
(4, 21, 11, 'asfdasdfasdfasdfas dfasdfasd fasdf asdf asd fasd f', 6),
(5, 21, 14, 'asdf', 3),
(6, 21, 11, 'neka nova recenzija', 11),
(7, 21, 32, 'jako dobro', 8),
(8, 21, 32, 'asdfasdfa', 4);

-- --------------------------------------------------------

--
-- Table structure for table `rezultat`
--

DROP TABLE IF EXISTS `rezultat`;
CREATE TABLE IF NOT EXISTS `rezultat` (
  `kor_id` int(11) NOT NULL,
  `kviz_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `points` float NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

--
-- Dumping data for table `rezultat`
--

INSERT INTO `rezultat` (`kor_id`, `kviz_id`, `date`, `points`) VALUES
(21, 29, '2024-02-04', 50),
(21, 33, '2024-02-10', 50);

-- --------------------------------------------------------

--
-- Table structure for table `usluga`
--

DROP TABLE IF EXISTS `usluga`;
CREATE TABLE IF NOT EXISTS `usluga` (
  `usl_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `obl_id` int(10) UNSIGNED DEFAULT NULL,
  `usl_naziv` varchar(255) COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `usl_opis` longtext COLLATE utf8mb4_unicode_520_ci,
  `usl_slika` longblob,
  PRIMARY KEY (`usl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- --------------------------------------------------------

--
-- Table structure for table `vlasnik`
--

DROP TABLE IF EXISTS `vlasnik`;
CREATE TABLE IF NOT EXISTS `vlasnik` (
  `kor_id` int(11) NOT NULL,
  `usluga_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

--
-- Dumping data for table `vlasnik`
--

INSERT INTO `vlasnik` (`kor_id`, `usluga_id`) VALUES
(21, 11),
(21, 12),
(21, 13),
(21, 14),
(21, 15),
(21, 16),
(21, 18),
(21, 17),
(21, 19),
(21, 20),
(21, 21),
(21, 22),
(21, 24),
(21, 25),
(21, 23),
(21, 26),
(22, 27),
(21, 28),
(21, 29),
(21, 30),
(21, 31),
(21, 32);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
