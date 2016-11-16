CREATE DATABASE `test` /*!40100 DEFAULT CHARACTER SET latin1 */;

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

CREATE TABLE `Mapping_ETL_Rules` (
  `ETL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Source_TableName` varchar(10000) NOT NULL,
  `Source_ColumnName` varchar(10000) NOT NULL,
  `Rule_Type` varchar(10000) NOT NULL,
  `Rule` varchar(10000) NOT NULL,
  `FlagType` varchar(50) NOT NULL,
  PRIMARY KEY (`ETL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `MappingFacultyStudent` (
  `FSMap_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Faculty_Username` varchar(50) NOT NULL,
  `Student_Username` varchar(50) NOT NULL,
  `ExpirationDate` datetime NOT NULL,
  `ActiveFlag` varchar(10) NOT NULL DEFAULT 'False',
  PRIMARY KEY (`FSMap_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `MappingTable` (
  `MappingTable_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SiteID` varchar(10000) NOT NULL,
  `ModelID` varchar(10000) NOT NULL,
  `SensorID` varchar(10000) NOT NULL,
  `Username` varchar(45) NOT NULL,
  `SiteName` varchar(1000) NOT NULL DEFAULT 'No Default Sitename provided',
  `SensorType` varchar(10000) NOT NULL,
  `Location` varchar(1000) DEFAULT NULL,
  `NumColumn` int(11) NOT NULL,
  `Delimiter` varchar(50) NOT NULL,
  `RangeBetweenReadings` varchar(50) NOT NULL,
  `AcceptableRange` varchar(50) DEFAULT NULL,
  `TypeSensor_BaseTable` varchar(10) NOT NULL,
  `Site_BaseTable` varchar(10) NOT NULL,
  `Public` varchar(10) NOT NULL,
  `TableName` varchar(10000) NOT NULL,
  `ColumnNames` varchar(10000) NOT NULL,
  `Row_To_Skip` int(11) NOT NULL,
  PRIMARY KEY (`MappingTable_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

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

