CREATE DATABASE  IF NOT EXISTS `tlink` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tlink`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: tlink
-- ------------------------------------------------------
-- Server version	5.6.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Access`
--

DROP TABLE IF EXISTS `Access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Access` (
  `vehicleNumber` int(11) NOT NULL,
  `cid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`vehicleNumber`,`cid`,`pid`),
  KEY `accessCid_idx` (`cid`,`pid`),
  CONSTRAINT `accessVehicleNumber` FOREIGN KEY (`vehicleNumber`) REFERENCES `Vehicle` (`vehicleNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `accessCid` FOREIGN KEY (`cid`, `pid`) REFERENCES `Owns_Pass` (`cid`, `pid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Access`
--

LOCK TABLES `Access` WRITE;
/*!40000 ALTER TABLE `Access` DISABLE KEYS */;
/*!40000 ALTER TABLE `Access` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Customer`
--

DROP TABLE IF EXISTS `Customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Customer` (
  `cid` int(11) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Customer`
--

LOCK TABLES `Customer` WRITE;
/*!40000 ALTER TABLE `Customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `Customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Driveable`
--

DROP TABLE IF EXISTS `Driveable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Driveable` (
  `vehicleNumber` int(11) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`vehicleNumber`),
  CONSTRAINT `vehicleNumber` FOREIGN KEY (`vehicleNumber`) REFERENCES `Vehicle` (`vehicleNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Driveable`
--

LOCK TABLES `Driveable` WRITE;
/*!40000 ALTER TABLE `Driveable` DISABLE KEYS */;
/*!40000 ALTER TABLE `Driveable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Driven_By`
--

DROP TABLE IF EXISTS `Driven_By`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Driven_By` (
  `vehicleNumber` int(11) NOT NULL,
  `from` datetime NOT NULL,
  `to` datetime NOT NULL,
  `empId` int(11) NOT NULL,
  PRIMARY KEY (`vehicleNumber`,`from`,`to`,`empId`),
  KEY `drivenDuration_idx` (`from`,`to`),
  KEY `drivenEmp_idx` (`empId`),
  CONSTRAINT `drivenVehicle` FOREIGN KEY (`vehicleNumber`) REFERENCES `Vehicle` (`vehicleNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `drivenDuration` FOREIGN KEY (`from`, `to`) REFERENCES `Duration` (`from`, `to`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `drivenEmp` FOREIGN KEY (`empId`) REFERENCES `Driver` (`empId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Driven_By`
--

LOCK TABLES `Driven_By` WRITE;
/*!40000 ALTER TABLE `Driven_By` DISABLE KEYS */;
/*!40000 ALTER TABLE `Driven_By` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Driver`
--

DROP TABLE IF EXISTS `Driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Driver` (
  `empId` int(11) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `address` varchar(60) DEFAULT NULL,
  `phoneNumber` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Driver`
--

LOCK TABLES `Driver` WRITE;
/*!40000 ALTER TABLE `Driver` DISABLE KEYS */;
/*!40000 ALTER TABLE `Driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Driverless`
--

DROP TABLE IF EXISTS `Driverless`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Driverless` (
  `vehicleNumber` int(11) NOT NULL,
  PRIMARY KEY (`vehicleNumber`),
  CONSTRAINT `driverless` FOREIGN KEY (`vehicleNumber`) REFERENCES `Vehicle` (`vehicleNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Driverless`
--

LOCK TABLES `Driverless` WRITE;
/*!40000 ALTER TABLE `Driverless` DISABLE KEYS */;
/*!40000 ALTER TABLE `Driverless` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Duration`
--

DROP TABLE IF EXISTS `Duration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Duration` (
  `from` datetime NOT NULL,
  `to` datetime NOT NULL,
  PRIMARY KEY (`from`,`to`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Duration`
--

LOCK TABLES `Duration` WRITE;
/*!40000 ALTER TABLE `Duration` DISABLE KEYS */;
/*!40000 ALTER TABLE `Duration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Follows`
--

DROP TABLE IF EXISTS `Follows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Follows` (
  `vehicleNumber` int(11) NOT NULL,
  `routeNumber` int(11) NOT NULL,
  `from` datetime NOT NULL,
  `to` datetime NOT NULL,
  PRIMARY KEY (`vehicleNumber`,`routeNumber`,`from`,`to`),
  KEY `followsRoute_idx` (`routeNumber`),
  KEY `followsDuration_idx` (`from`,`to`),
  CONSTRAINT `followsVehicle` FOREIGN KEY (`vehicleNumber`) REFERENCES `Vehicle` (`vehicleNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `followsRoute` FOREIGN KEY (`routeNumber`) REFERENCES `Route` (`routeNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `followsDuration` FOREIGN KEY (`from`, `to`) REFERENCES `Duration` (`from`, `to`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Follows`
--

LOCK TABLES `Follows` WRITE;
/*!40000 ALTER TABLE `Follows` DISABLE KEYS */;
/*!40000 ALTER TABLE `Follows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Has`
--

DROP TABLE IF EXISTS `Has`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Has` (
  `routeNumber` int(11) NOT NULL,
  `stopNumber` int(11) NOT NULL,
  PRIMARY KEY (`routeNumber`,`stopNumber`),
  KEY `hasStop_idx` (`stopNumber`),
  CONSTRAINT `hasRoute` FOREIGN KEY (`routeNumber`) REFERENCES `Route` (`routeNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hasStop` FOREIGN KEY (`stopNumber`) REFERENCES `Stop` (`stopNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Has`
--

LOCK TABLES `Has` WRITE;
/*!40000 ALTER TABLE `Has` DISABLE KEYS */;
/*!40000 ALTER TABLE `Has` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Owns_Pass`
--

DROP TABLE IF EXISTS `Owns_Pass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Owns_Pass` (
  `pid` int(11) NOT NULL,
  `balance` int(11) DEFAULT NULL,
  `cid` int(11) NOT NULL,
  PRIMARY KEY (`pid`,`cid`),
  KEY `ownspass_idx` (`cid`),
  CONSTRAINT `ownspass` FOREIGN KEY (`cid`) REFERENCES `Customer` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Owns_Pass`
--

LOCK TABLES `Owns_Pass` WRITE;
/*!40000 ALTER TABLE `Owns_Pass` DISABLE KEYS */;
/*!40000 ALTER TABLE `Owns_Pass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Route`
--

DROP TABLE IF EXISTS `Route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Route` (
  `routeNumber` int(11) NOT NULL,
  `routeName` varchar(45) DEFAULT NULL,
  `stopTime` time DEFAULT NULL,
  `startTime` time DEFAULT NULL,
  PRIMARY KEY (`routeNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Route`
--

LOCK TABLES `Route` WRITE;
/*!40000 ALTER TABLE `Route` DISABLE KEYS */;
/*!40000 ALTER TABLE `Route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Stop`
--

DROP TABLE IF EXISTS `Stop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Stop` (
  `stopNumber` int(11) NOT NULL,
  `stopName` varchar(40) DEFAULT NULL,
  `location` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`stopNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Stop`
--

LOCK TABLES `Stop` WRITE;
/*!40000 ALTER TABLE `Stop` DISABLE KEYS */;
/*!40000 ALTER TABLE `Stop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Vehicle`
--

DROP TABLE IF EXISTS `Vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Vehicle` (
  `vehicleNumber` int(11) NOT NULL,
  `age` int(11) NOT NULL,
  `capacity` int(11) DEFAULT NULL,
  PRIMARY KEY (`vehicleNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='		';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Vehicle`
--

LOCK TABLES `Vehicle` WRITE;
/*!40000 ALTER TABLE `Vehicle` DISABLE KEYS */;
/*!40000 ALTER TABLE `Vehicle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-19 15:35:19
