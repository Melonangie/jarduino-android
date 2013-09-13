-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: db4free.net:3306
-- Generation Time: Jul 24, 2013 at 06:57 AM
-- Server version: 5.6.12-log
-- PHP Version: 5.3.10-1ubuntu3.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `jarduino`
--
CREATE DATABASE IF NOT EXISTS `jarduino` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `jarduino`;

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `deleteConfiguration`(id int)
DELETE FROM configuration WHERE config_id = id$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `deleteError`(id int)
DELETE FROM error WHERE error_id = id$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `deleteLog`(id int)
DELETE FROM logs WHERE log_id = id$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `deletePackage`(id int)
DELETE FROM package WHERE paquete_id=id$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `deletePlant`(id int)
DELETE FROM plant WHERE planta_id=id$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `deleteUser`(id int)
DELETE FROM user where user_id=id$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `insertConfiguration`(idPaquete int,idPlanta int)
INSERT INTO configuration(paquete_id,planta_id,config_date)VALUES(idPaquete,idPlanta,NOW())$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `insertError`(name varchar(50), info varchar(180))
INSERT INTO error(error_name,error_date,error_info) VALUES(name,NOW(),info)$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `insertHistory`(temp int,hum int, uv int, light int)
INSERT INTO history(his_tem,his_hum,his_uv,his_light,his_date)VALUES(temp,hum,uv,light,NOW())$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `insertLogs`(idUser int,evento varchar(50))
INSERT INTO logs(user_id,log_event,log_date) VALUES(idUSer,evento,NOW())$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `insertPackage`(name varchar(50))
INSERT INTO package(paquete_nombre,paquete_date)VALUES(name,NOW())$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `insertPlant`(IN `name` VARCHAR(50), IN `temp` INT, IN `hum` INT, IN `uv` INT, IN `light` INT)
INSERT INTO plant(plant_name,plant_temp,plant_hum,plant_uv,plant_light,plant_date)
		VALUES(name,temp,hum,uv,light,now())$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `insertUser`(IN `name` VARCHAR(250), IN `pasword` VARCHAR(250), IN `nombre` VARCHAR(150), IN `estatus` INT, IN `email` VARCHAR(250))
INSERT INTO user(user_name,user_password,user_nombre,user_subscribe_date,user_status,user_email)
		VALUES(name,pasword,nombre,now(),estatus,email)$$

CREATE DEFINER=`jarduinouser`@`%` PROCEDURE `updatePackage`(name varchar(50),id int)
UPDATE package
	set paquete_nombre=name,
		paquete_date=NOW()
	where paquete_id=id$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `error`
--

CREATE TABLE `error` (
  `error_id` int(11) NOT NULL AUTO_INCREMENT,
  `error_name` varchar(50) DEFAULT NULL,
  `error_date` datetime NOT NULL,
  `error_info` varchar(180) DEFAULT NULL,
  PRIMARY KEY (`error_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Table structure for table `history`
--

CREATE TABLE `history` (
  `history_id` int(11) NOT NULL AUTO_INCREMENT,
  `his_tem` int(11) NOT NULL,
  `his_hum` int(11) NOT NULL,
  `his_uv` int(11) NOT NULL,
  `his_light` int(11) NOT NULL,
  `his_date` datetime DEFAULT NULL,
  PRIMARY KEY (`history_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=23 ;

--
-- Dumping data for table `history`
--

INSERT INTO `history` (`history_id`, `his_tem`, `his_hum`, `his_uv`, `his_light`, `his_date`) VALUES
(1, 25, 25, 45, 36, '2013-07-03 00:00:00'),
(2, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(3, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(4, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(5, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(6, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(7, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(8, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(9, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(10, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(11, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(12, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(13, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(14, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(15, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(16, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(17, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(18, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(19, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(20, 56, 85, 96, 24, '2013-07-03 00:00:00'),
(21, 56, 85, 96, 24, '2013-07-03 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `log_event` varchar(50) NOT NULL,
  `log_date` datetime NOT NULL,
  PRIMARY KEY (`log_id`,`user_id`),
  KEY `Refuser5` (`user_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `logs`
--

INSERT INTO `logs` (`log_id`, `user_id`, `log_event`, `log_date`) VALUES
(1, 1, 'hiso insert', '2013-07-10 11:04:28'),
(2, 2, 'inserto esto', '2013-07-10 23:00:30'),
(3, 3, 'inserto esto', '2013-07-10 23:02:06');

-- --------------------------------------------------------

--
-- Table structure for table `package`
--

CREATE TABLE `package` (
  `paquete_id` int(11) NOT NULL AUTO_INCREMENT,
  `paquete_nombre` varchar(50) NOT NULL,
  `paquete_date` date NOT NULL,
  PRIMARY KEY (`paquete_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `package`
--

INSERT INTO `package` (`paquete_id`, `paquete_nombre`, `paquete_date`) VALUES
(1, 'Jardin Botanico', '2013-07-10');

-- --------------------------------------------------------

--
-- Table structure for table `plant`
--

CREATE TABLE `plant` (
  `planta_id` int(11) NOT NULL AUTO_INCREMENT,
  `plant_name` varchar(50) NOT NULL,
  `plant_temp` int(11) NOT NULL,
  `plant_hum` int(11) NOT NULL,
  `plant_uv` int(11) NOT NULL,
  `plant_light` int(11) NOT NULL,
  `plant_date` date NOT NULL,
  PRIMARY KEY (`planta_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `plant`
--

INSERT INTO `plant` (`planta_id`, `plant_name`, `plant_temp`, `plant_hum`, `plant_uv`, `plant_light`, `plant_date`) VALUES
(1, 'tomatoes', 1, 1, 1, 1, '0000-00-00'),
(2, 'cebolloes', 1, 1, 1, 1, '0000-00-00'),
(3, 'rabanoes', 1, 1, 1, 1, '2013-07-10');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(250) NOT NULL,
  `user_password` varchar(250) NOT NULL,
  `user_nombre` varchar(150) DEFAULT NULL,
  `user_subscribe_date` datetime NOT NULL,
  `user_status` int(11) NOT NULL,
  `user_email` varchar(250) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `user_password`, `user_nombre`, `user_subscribe_date`, `user_status`, `user_email`) VALUES
(1, 'bhernandez', '202cb962ac59075b964b07152d234b70', 'Braulio Hernandez', '2013-06-21 00:00:00', 1, 'braulio.hernandz@gmail.com');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
