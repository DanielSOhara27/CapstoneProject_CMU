-- MySQL dump 10.13  Distrib 5.6.20, for osx10.8 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.6.20

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
-- Table structure for table `MappingColumn`
--

DROP TABLE IF EXISTS `MappingColumn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MappingColumn` (
  `ColMap_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TypeOfAssociation` varchar(100) NOT NULL,
  `Source_DataType` varchar(100) NOT NULL,
  `Source_ColumnName` varchar(10000) NOT NULL,
  `Source_TableName` varchar(10000) NOT NULL,
  `Dest_DataType` varchar(100) NOT NULL,
  `Dest_ColumnName` varchar(10000) NOT NULL,
  `Dest_TableName` varchar(10000) NOT NULL,
  PRIMARY KEY (`ColMap_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MappingFacultyStudent`
--

DROP TABLE IF EXISTS `MappingFacultyStudent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MappingFacultyStudent` (
  `FSMap_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Faculty_Username` varchar(50) NOT NULL,
  `Student_Username` varchar(50) NOT NULL,
  `ExpirationDate` datetime NOT NULL,
  `ActiveFlag` varchar(10) NOT NULL DEFAULT 'False',
  PRIMARY KEY (`FSMap_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MappingTable`
--

DROP TABLE IF EXISTS `MappingTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MappingTable` (
  `MappingTable_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SiteID` varchar(10000) NOT NULL,
  `ModelID` varchar(10000) NOT NULL,
  `SensorID` varchar(10000) NOT NULL,
  `Username` varchar(45) NOT NULL,
  `SiteName` varchar(10000) NOT NULL DEFAULT 'No Default Sitename provided',
  `SensorType` varchar(10000) NOT NULL,
  `Location` varchar(10000) DEFAULT NULL,
  `NumColumn` int(11) NOT NULL,
  `Delimiter` varchar(50) NOT NULL,
  `RangeBetweenReadings` varchar(50) NOT NULL,
  `AcceptableRange` varchar(50) DEFAULT NULL,
  `TypeSensor_BaseTable` varchar(10) NOT NULL,
  `Site_BaseTable` varchar(10) NOT NULL,
  `Public` varchar(10) NOT NULL,
  PRIMARY KEY (`MappingTable_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Mapping_ETL_Rules`
--

DROP TABLE IF EXISTS `Mapping_ETL_Rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Mapping_ETL_Rules` (
  `ETL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Source_TableName` varchar(10000) NOT NULL,
  `Source_ColumnName` varchar(10000) NOT NULL,
  `Rule_Type` varchar(10000) NOT NULL,
  `Rule` varchar(10000) NOT NULL,
  `FlagType` varchar(50) NOT NULL,
  PRIMARY KEY (`ETL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `User_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Firstname` varchar(45) NOT NULL,
  `Lastname` varchar(45) DEFAULT NULL,
  `Department` varchar(45) DEFAULT NULL,
  `TypeUser` varchar(45) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(100) NOT NULL,
  PRIMARY KEY (`User_ID`),
  UNIQUE KEY `UserName_UNIQUE` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-08 16:28:20
