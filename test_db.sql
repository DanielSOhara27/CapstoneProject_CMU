CREATE DATABASE `test`;


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
) ENGINE=InnoDB;

CREATE TABLE `Mapping_ETL_Rules` (
  `ETL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Source_TableName` varchar(10000) NOT NULL,
  `Source_ColumnName` varchar(10000) NOT NULL,
  `Rule_Type` varchar(10000) NOT NULL,
  `Rule` varchar(10000) NOT NULL,
  `FlagType` varchar(50) NOT NULL,
  PRIMARY KEY (`ETL_ID`)
) ENGINE=InnoDB;

CREATE TABLE `MappingFacultyStudent` (
  `FSMap_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Faculty_Username` varchar(50) NOT NULL,
  `Student_Username` varchar(50) NOT NULL,
  `ExpirationDate` datetime NOT NULL,
  `ActiveFlag` varchar(10) NOT NULL DEFAULT 'False',
  PRIMARY KEY (`FSMap_ID`)
) ENGINE=InnoDB;

CREATE TABLE `MappingTable` (
  `MappingTable_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SiteID` varchar(10000) NOT NULL DEFAULT 'foo',
  `ModelID` varchar(10000) NOT NULL DEFAULT 'foo',
  `SensorID` varchar(10000) NOT NULL DEFAULT 'foo',
  `Username` varchar(45) NOT NULL DEFAULT 'foo',
  `SiteName` varchar(1000) NOT NULL DEFAULT 'No Default Sitename provided',
  `SensorType` varchar(10000) NOT NULL DEFAULT 'foo',
  `Location` varchar(1000) DEFAULT NULL,
  `NumColumn` int(11) NOT NULL DEFAULT '-1',
  `Delimiter` varchar(50) NOT NULL DEFAULT 'foo',
  `RangeBetweenReadings` varchar(50) NOT NULL DEFAULT 'foo',
  `AcceptableRange` varchar(50) DEFAULT NULL,
  `TypeSensor_BaseTable` varchar(10) DEFAULT 'foo',
  `Site_BaseTable` varchar(10) DEFAULT 'foo',
  `Public` varchar(10) NOT NULL DEFAULT 'foo',
  `TableName` varchar(10000) NOT NULL DEFAULT 'foo',
  `ColumnNames` varchar(10000) NOT NULL DEFAULT 'foo',
  `Row_To_Skip` int(11) NOT NULL DEFAULT '-1',
  `Comment_Delimiter` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`MappingTable_ID`)
) ENGINE=InnoDB;

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
) ENGINE=InnoDB;