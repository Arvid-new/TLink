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
-- Table structure for table `access`
--

DROP TABLE IF EXISTS `access`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `access` (
  `vehicleNumber` int(11) NOT NULL,
  `cid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`vehicleNumber`,`cid`,`pid`),
  KEY `accessCid_idx` (`cid`,`pid`),
  CONSTRAINT `accessCid` FOREIGN KEY (`cid`, `pid`) REFERENCES `owns_pass` (`cid`, `pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `accessVehicleNumber` FOREIGN KEY (`vehicleNumber`) REFERENCES `vehicle` (`vehicleNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `access`
--

LOCK TABLES `access` WRITE;
/*!40000 ALTER TABLE `access` DISABLE KEYS */;
INSERT INTO `access` VALUES (512,111111,222333),(400,123456,654321),(664,222222,111222),(965,333333,333221),(133,444444,105234);
/*!40000 ALTER TABLE `access` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `cid` int(11) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (111111,'Jane Henry'),(123456,'John Henry'),(222222,'James Smith'),(333333,'Sally Adams'),(444444,'Jack Jackson');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driveable`
--

DROP TABLE IF EXISTS `driveable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driveable` (
  `vehicleNumber` int(11) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`vehicleNumber`),
  CONSTRAINT `drivableNumber` FOREIGN KEY (`vehicleNumber`) REFERENCES `vehicle` (`vehicleNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driveable`
--

LOCK TABLES `driveable` WRITE;
/*!40000 ALTER TABLE `driveable` DISABLE KEYS */;
INSERT INTO `driveable` VALUES (101,'ferry'),(133,'bus'),(200,'ferry'),(664,'bus'),(965,'bus');
/*!40000 ALTER TABLE `driveable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driven_by`
--

DROP TABLE IF EXISTS `driven_by`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driven_by` (
  `vehicleNumber` int(11) NOT NULL,
  `from` datetime NOT NULL,
  `to` datetime NOT NULL,
  `empId` int(11) NOT NULL,
  PRIMARY KEY (`vehicleNumber`,`from`,`to`,`empId`),
  KEY `drivenDuration_idx` (`from`,`to`),
  KEY `drivenEmp_idx` (`empId`),
  CONSTRAINT `drivenDuration` FOREIGN KEY (`from`, `to`) REFERENCES `duration` (`from`, `to`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `drivenEmp` FOREIGN KEY (`empId`) REFERENCES `driver` (`empId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `drivenVehicle` FOREIGN KEY (`vehicleNumber`) REFERENCES `vehicle` (`vehicleNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driven_by`
--

LOCK TABLES `driven_by` WRITE;
/*!40000 ALTER TABLE `driven_by` DISABLE KEYS */;
INSERT INTO `driven_by` VALUES (101,'2015-01-02 08:00:00','2015-01-02 22:00:00',111111),(200,'2015-01-05 08:00:00','2015-01-05 22:00:00',123123),(965,'2015-02-05 10:00:00','2015-02-05 19:00:00',123456),(133,'2015-02-05 10:00:00','2015-02-05 16:00:00',222222);
/*!40000 ALTER TABLE `driven_by` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driver` (
  `empId` int(11) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `address` varchar(60) DEFAULT NULL,
  `phoneNumber` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES (111111,'Sally Adams','3575 Granview St.','6042313248'),(123123,'Jane Smith','3819 Victoria Dr.','6047987522'),(123456,'John Smith','3761 Main St.','6045554568'),(222222,'Dave Davidson','521 Renfrew St.','6045465454'),(456456,'Bob Johnson','3802 Hastings St.','6047987985');
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driverless`
--

DROP TABLE IF EXISTS `driverless`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driverless` (
  `vehicleNumber` int(11) NOT NULL,
  PRIMARY KEY (`vehicleNumber`),
  CONSTRAINT `driverless` FOREIGN KEY (`vehicleNumber`) REFERENCES `vehicle` (`vehicleNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driverless`
--

LOCK TABLES `driverless` WRITE;
/*!40000 ALTER TABLE `driverless` DISABLE KEYS */;
INSERT INTO `driverless` VALUES (348),(400),(512);
/*!40000 ALTER TABLE `driverless` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `duration`
--

DROP TABLE IF EXISTS `duration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `duration` (
  `from` datetime NOT NULL,
  `to` datetime NOT NULL,
  PRIMARY KEY (`from`,`to`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `duration`
--

LOCK TABLES `duration` WRITE;
/*!40000 ALTER TABLE `duration` DISABLE KEYS */;
INSERT INTO `duration` VALUES ('2015-01-02 08:00:00','2015-01-02 22:00:00'),('2015-01-05 08:00:00','2015-01-05 22:00:00'),('2015-02-05 10:00:00','2015-02-05 16:00:00'),('2015-02-05 10:00:00','2015-02-05 19:00:00');
/*!40000 ALTER TABLE `duration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follows`
--

DROP TABLE IF EXISTS `follows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `follows` (
  `vehicleNumber` int(11) NOT NULL,
  `routeNumber` int(11) NOT NULL,
  `from` datetime NOT NULL,
  `to` datetime NOT NULL,
  PRIMARY KEY (`vehicleNumber`,`routeNumber`,`from`,`to`),
  KEY `followsRoute_idx` (`routeNumber`),
  KEY `followsDuration_idx` (`from`,`to`),
  CONSTRAINT `followsDuration` FOREIGN KEY (`from`, `to`) REFERENCES `duration` (`from`, `to`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `followsRoute` FOREIGN KEY (`routeNumber`) REFERENCES `route` (`routeNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `followsVehicle` FOREIGN KEY (`vehicleNumber`) REFERENCES `vehicle` (`vehicleNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follows`
--

LOCK TABLES `follows` WRITE;
/*!40000 ALTER TABLE `follows` DISABLE KEYS */;
INSERT INTO `follows` VALUES (664,22,'2015-01-05 08:00:00','2015-01-05 22:00:00'),(133,55,'2015-02-05 10:00:00','2015-02-05 16:00:00'),(965,66,'2015-02-05 10:00:00','2015-02-05 19:00:00'),(965,88,'2015-01-02 08:00:00','2015-01-02 22:00:00'),(133,99,'2015-01-02 08:00:00','2015-01-02 22:00:00');
/*!40000 ALTER TABLE `follows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `has`
--

DROP TABLE IF EXISTS `has`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `has` (
  `routeNumber` int(11) NOT NULL,
  `stopNumber` int(11) NOT NULL,
  PRIMARY KEY (`routeNumber`,`stopNumber`),
  KEY `hasStop_idx` (`stopNumber`),
  CONSTRAINT `hasRoute` FOREIGN KEY (`routeNumber`) REFERENCES `route` (`routeNumber`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hasStop` FOREIGN KEY (`stopNumber`) REFERENCES `stop` (`stopNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `has`
--

LOCK TABLES `has` WRITE;
/*!40000 ALTER TABLE `has` DISABLE KEYS */;
INSERT INTO `has` VALUES (99,1000),(22,2000),(66,3000),(55,4000),(99,5000);
/*!40000 ALTER TABLE `has` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owns_pass`
--

DROP TABLE IF EXISTS `owns_pass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `owns_pass` (
  `pid` int(11) NOT NULL,
  `balance` int(11) DEFAULT NULL,
  `cid` int(11) NOT NULL,
  PRIMARY KEY (`pid`,`cid`),
  KEY `ownspass_idx` (`cid`),
  CONSTRAINT `ownspass` FOREIGN KEY (`cid`) REFERENCES `customer` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owns_pass`
--

LOCK TABLES `owns_pass` WRITE;
/*!40000 ALTER TABLE `owns_pass` DISABLE KEYS */;
INSERT INTO `owns_pass` VALUES (105234,50,444444),(111222,7,222222),(222333,200,111111),(333221,0,333333),(654321,100,123456);
/*!40000 ALTER TABLE `owns_pass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route` (
  `routeNumber` int(11) NOT NULL,
  `routeName` varchar(45) DEFAULT NULL,
  `stopTime` time DEFAULT NULL,
  `startTime` time DEFAULT NULL,
  PRIMARY KEY (`routeNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (22,'Downtown Express','23:59:00','06:00:00'),(55,'Burnaby Express','23:59:00','06:30:00'),(66,'North Van Shuttle','23:00:00','07:00:00'),(88,'Richmond Express','23:00:00','06:00:00'),(99,'UBC Express','23:59:00','06:00:00');
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stop`
--

DROP TABLE IF EXISTS `stop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stop` (
  `stopNumber` int(11) NOT NULL,
  `stopName` varchar(40) DEFAULT NULL,
  `location` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`stopNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stop`
--

LOCK TABLES `stop` WRITE;
/*!40000 ALTER TABLE `stop` DISABLE KEYS */;
INSERT INTO `stop` VALUES (1000,'UBC Bus Loop','UBC Campus'),(2000,'Waterfront Station','Downtown'),(3000,'Capilano University','North Vancouver'),(4000,'SFU Bus Loop','Burnaby'),(5000,'Richmond Center','Richmond');
/*!40000 ALTER TABLE `stop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicle`
--

DROP TABLE IF EXISTS `vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicle` (
  `vehicleNumber` int(11) NOT NULL,
  `age` int(11) NOT NULL,
  `capacity` int(11) DEFAULT NULL,
  PRIMARY KEY (`vehicleNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='		';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicle`
--

LOCK TABLES `vehicle` WRITE;
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
INSERT INTO `vehicle` VALUES (101,10,100),(133,2,50),(200,2,100),(348,5,50),(400,15,100),(512,9,50),(664,12,75),(965,4,100);
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-21 12:52:58
