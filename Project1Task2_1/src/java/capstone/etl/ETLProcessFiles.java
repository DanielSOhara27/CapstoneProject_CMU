package capstone.etl;

import capstone.connection.DBConnectionManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anshu
 */
public class ETLProcessFiles {

    DBConnectionManager manager = DBConnectionManager.getInstance();
    Statement stmt = null, stmtMissingValue = null, stmtRangeValue = null, stmtFileExists = null;
    Connection connection = null;
    private static final String BASE_RECEIVE_DIRECTORY = "C:/TestLoad/";
    String siteID = "";
    String modelID = "";
    String sensorID = "";
    String uploadfilesAbsoluteDirectory = "";
    String tableName = "";
    int rowsToSkip = 0;
    String commentSignifier = "";
    String fileDelimiter = "";
    String columnNamesDataTypes = "";
    String[] columnName;
    String[] columnDataType;
    String[] columnFormat;
    String[] columnAlias;

    //Setup connections
    public ETLProcessFiles() {
        try {
            connection = manager.getConnection();
            stmt = connection.createStatement();
            stmtMissingValue = connection.createStatement();
            stmtRangeValue = connection.createStatement();
            stmtFileExists = connection.createStatement();
            //connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(ETLProcessFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //entry point for ETL work
    public void startETL(String site, String model, String sensor, String folderName) throws IOException, SQLException {
        //set the params
        siteID = site;
        modelID = model;
        sensorID = sensor;
        uploadfilesAbsoluteDirectory = BASE_RECEIVE_DIRECTORY + folderName;
        //get & set the TableName,RowsToSkip,FileDelimiter,ColumnNamesDataTypes 
        setMappingInfo();
        //System.out.println("StrtETL: "+  columnFormat[0]);
        //start file processing
        startFileProcessing();
    }

    //process all files in the folder and load data in the specified table
    public void startFileProcessing() throws FileNotFoundException, IOException, SQLException {
        //get file object to the file diectory
        File directory = new File(uploadfilesAbsoluteDirectory);
        //get fileList
        File fileList[] = directory.listFiles();
        //loop thru each file and process it
        BufferedReader br = null;
        for (File file : fileList) {
            String currentFile = file.getName();
            //System.out.println("Current File: " + currentFile);
            //check for redundant files
            if (!checkIfExists(currentFile)) {
                br = new BufferedReader(new FileReader(file));
                String line = null;
                int skipLinesCtr = 0;
                int batchCounter = 0;
                //loop through each line of current file
                while ((line = br.readLine()) != null) {
                    int isItFlagged = 0;
                    long unixTime;
                    if (skipLinesCtr != rowsToSkip + 1) {
                        skipLinesCtr++;
                        continue;
                    }
                    //System.out.println("Current Line: " + line);
                    if (batchCounter != 0 && batchCounter % 20 == 0) {
                        stmt.executeBatch();
                    }
                    if (!commentSignifier.equals("") && line.startsWith(commentSignifier)) {
                        continue;
                    }
                    String[] lineTokens = line.split(fileDelimiter);
                    int missingValueFlag = 0;
                    for (int j = 0; j < lineTokens.length; j++) {
                        String missingValueFlagCheck = missingValueCheck(columnName[j]);
                        if (missingValueFlagCheck.equalsIgnoreCase("red") && lineTokens[j].trim().equals("")) {
                            missingValueFlag = 1;
                            break;
                        }
                        if (missingValueFlagCheck.equalsIgnoreCase("yellow") && lineTokens[j].equals("")) {
                            isItFlagged = 1;
                            //System.out.println(isItFlagged);
                            break;
                        }
                    }
                    if (missingValueFlag == 1) {
                        continue;
                    }
                    int rangeValueFlag = 0;
                    for (int j = 0; j < lineTokens.length; j++) {
                        if (!columnDataType[j].toLowerCase().contains("datetime") && !columnDataType[j].toLowerCase().contains("date") && !columnDataType[j].toLowerCase().contains("time")) {
                            //!columnDataType[j].equalsIgnoreCase("varchar") && 
                            String rangeFlag = "";
                            if ((columnDataType[j].toLowerCase().contains("varchar") && (lineTokens[j].charAt(0) == '+' || lineTokens[j].charAt(0) == '-')) || columnDataType[j].toLowerCase().contains("double")) {
                                rangeFlag = rangeValueCheck(columnName[j], lineTokens[j].trim().substring(1, lineTokens[j].trim().length()));
                            }
                            if (rangeFlag.equalsIgnoreCase("red")) {
                                rangeValueFlag = 1;
                                break;
                            }
                            if (rangeFlag.equalsIgnoreCase("yellow")) {
                                isItFlagged = 1;
                                break;
                            }
                        }
                    }
                    if (rangeValueFlag == 1) {
                        continue;
                    }
                    StringBuilder insertColumnValues = new StringBuilder();
                    StringBuilder insertColumnNames = new StringBuilder();
                    int indexDateTime = -1;
                    int indexDate = -1;
                    int indexTime = -1;
                    int indexUnixTime = -1;
                    for (int j = 0; j < lineTokens.length; j++) {
                        insertColumnNames.append("`").append(columnName[j]).append("`").append(",");
                        String token = "";
                        if (columnDataType[j].toLowerCase().contains("varchar") || columnDataType[j].toLowerCase().contains("datetime") || columnDataType[j].toLowerCase().contains("date") || columnDataType[j].toLowerCase().contains("time")) {
                            token = "'" + lineTokens[j] + "'";
                            if (columnDataType[j].toLowerCase().contains("datetime") && columnFormat[j].toLowerCase().contains("unixtime")) {
                                indexUnixTime = j;
                                token = "'" + lineTokens[j] + "'";
                                insertColumnValues.append(token).append(",");
                                continue;
                            }
                            if (columnDataType[j].toLowerCase().contains("datetime") && !columnFormat[j].toLowerCase().contains("unixtime")) {
                                indexDateTime = j;
                                String dateTimeFormat = columnFormat[indexDateTime];
                                SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
                                String dateInString = lineTokens[indexDateTime];
                                Date date = null;
                                System.out.println("date in string:"+ dateInString +":"+dateTimeFormat);
                                try {
                                    date = formatter.parse(dateInString);
                                } catch (ParseException ex) {
                                    Logger.getLogger(ETLProcessFiles.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                insertColumnValues.append("'").append(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)).append("'").append(",");
                                continue;
                            }
                            if (columnDataType[j].toLowerCase().contains("date")) {
                                indexDate = j;
                                insertColumnValues.append(token).append(",");
                                continue;
                            }
                            if (columnDataType[j].toLowerCase().contains("time")) {
                                indexTime = j;
                                insertColumnValues.append(token).append(",");
                                continue;
                            }
                        } else {
                            token = lineTokens[j];
                            //System.out.println(token);
                        }
                        //System.out.println(token);
                        insertColumnValues.append(token).append(",");
                    }
                    insertColumnNames.append("ConvertedTimeStamp,Year,month,mDay,hour,minute,yDay,Source_Filename,isItFlagged,second");
                    int year = 0;
                    int month = 0;
                    int day = 0;
                    int dayOfYear = 0;
                    int hour = 0;
                    int minute = 0;
                    int seconds = 0;
                    String convertedTimestamp = "";
                    Calendar cal = Calendar.getInstance();
                    //we have epoch time in the file
                    if (indexUnixTime != -1) {
                        System.out.println("indexunixtime: " + indexUnixTime);
                        System.out.println(lineTokens[indexUnixTime]);
                        unixTime = Long.parseLong(lineTokens[indexUnixTime]);
                        //System.out.println("Unix Time: "+ unixTime);
                        String dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(unixTime * 1000));
                        String[] dateSplit = dateTime.split(" ");
                        year = Integer.parseInt(dateSplit[0].split("-")[0]);
                        month = Integer.parseInt(dateSplit[0].split("-")[1]);
                        day = Integer.parseInt(dateSplit[0].split("-")[2]);
                        dayOfYear = dayOfYear(year, month, day);
                        hour = Integer.parseInt(dateSplit[1].split(":")[0]);
                        minute = Integer.parseInt(dateSplit[1].split(":")[1]);
                        seconds = Integer.parseInt(dateSplit[1].split(":")[2]);
                        convertedTimestamp = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + seconds;
                    }
                    SimpleDateFormat formatter;
                    if (indexDateTime != -1) {
                        String dateTimeFormat = columnFormat[indexDateTime];
                        formatter = new SimpleDateFormat(dateTimeFormat);
                        String dateInString = lineTokens[indexDateTime];
                        Date date = null;
                        try {
                            date = formatter.parse(dateInString);
                        } catch (ParseException ex) {
                            Logger.getLogger(ETLProcessFiles.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        cal.setTime(date);
                        year = cal.get(Calendar.YEAR);
                        month = cal.get(Calendar.MONTH) + 1;
                        day = cal.get(Calendar.DAY_OF_MONTH);
                        dayOfYear = dayOfYear(year, month, day);
                        hour = cal.get(Calendar.HOUR_OF_DAY);
                        minute = cal.get(Calendar.MINUTE);
                        seconds = cal.get(Calendar.SECOND);
                        convertedTimestamp = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + seconds;
                    }
                    insertColumnValues.append("'").append(convertedTimestamp).append("'").append(",").append(year).append(",").append(month).append(",").append(day).append(",").append(hour).append(",").append(minute).append(",").append(dayOfYear).append(",").append("'").append(currentFile).append("'").append(",").append(isItFlagged).append(",").append(seconds);
                    String insertQuery = "insert into `" + tableName + "`(" + insertColumnNames.toString() + ") values(" + insertColumnValues.toString() + ")";
                    System.out.println("Insert Query: " + insertQuery);
                    stmt.addBatch(insertQuery);
                    batchCounter++;
                    //break;
                }
                stmt.executeBatch();
                br.close();
            }
        }
        stmt.close();
    }

    //get info from MappingTable and set them
    public void setMappingInfo() {
        try {
            String sql = "SELECT TableName, Row_To_Skip, Delimiter,Comment_Delimiter, ColumnNames FROM MappingTable where siteid=\"" + siteID + "\"and modelid=\"" + modelID + "\"and sensorid=\"" + sensorID + "\"";
            ResultSet rs = stmt.executeQuery(sql);
            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                tableName = rs.getString("TableName");
                rowsToSkip = rs.getInt("Row_To_Skip");
                fileDelimiter = rs.getString("Delimiter");
                columnNamesDataTypes = rs.getString("ColumnNames");
                commentSignifier = rs.getString("Comment_Delimiter");
            }
            rs.close();
            //stmt.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        String[] columnNamesDataTypesSplits = columnNamesDataTypes.split(",");
        int columnNamesDataTypesLength = columnNamesDataTypesSplits.length;
        columnName = new String[columnNamesDataTypesLength];
        columnDataType = new String[columnNamesDataTypesLength];
        columnFormat = new String[columnNamesDataTypesLength];
        columnAlias = new String[columnNamesDataTypesLength];
        for (int i = 0; i < columnNamesDataTypesLength; i++) {
            columnName[i] = columnNamesDataTypesSplits[i].split("\\|")[1];
            columnDataType[i] = columnNamesDataTypesSplits[i].split("\\|")[2];
            columnFormat[i] = columnNamesDataTypesSplits[i].split("\\|")[3];
            //System.out.println("unix format: " + columnFormat[0]);
            columnAlias[i] = columnNamesDataTypesSplits[i].split("\\|")[4];
        }
    }

    // 0<month<=12
    public int dayOfYear(int year, int month, int day) {
        short[] monthDays = new short[]{0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        int yearDay = monthDays[month - 1] + day;
        if (((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) && month > 2) {
            yearDay++;
        }
        return yearDay;
    }

    //check for missing value(red flag/yellow flag)- MappingColumn Table
    String missingValueCheck(String colName) throws SQLException {
        String queryMissingValue = "SELECT lower(Rule),lower(flagtype) FROM `Mapping_ETL_Rules` where source_tablename='" + tableName + "' and source_columnname='" + colName + "' and lower(Rule_type)='missing'";
        System.out.println("Query Missing Value: " + queryMissingValue);
        ResultSet rs = stmtMissingValue.executeQuery(queryMissingValue);
        String flagType = "";
        if (rs.next()) {
            if (rs.getString(1).equalsIgnoreCase("missing value") && rs.getString(2).equalsIgnoreCase("exclude")) {
                flagType = "red";
            }
            if (rs.getString(1).equalsIgnoreCase("missing value") && rs.getString(2).equalsIgnoreCase("flag")) {
                flagType = "yellow";
            }
        }
        //System.out.println(flagType);
        return flagType;
    }

    //check for any value falling in range(red flag/yellow flag)- MappingColumn Table 
    String rangeValueCheck(String colName, String tokenValue) throws SQLException {
        String queryRangeValue = "SELECT lower(rule),flagtype FROM `Mapping_ETL_Rules` where source_tablename='" + tableName + "' and source_columnname='" + colName + "' and lower(Rule_Type)='range'";
        //System.out.println("Query Range: " + queryRangeValue);
        ResultSet rs = stmtRangeValue.executeQuery(queryRangeValue);
        String rangeRule = "";
        String flagType = "";
        String flag = "";
        //while (rs.next()) {
        while (rs.next()) {
            rangeRule = rs.getString(1);
            flagType = rs.getString(2);
            String[] rangeRuleArray = rangeRule.split(":");
            /*if (rangeRuleArray[1].equalsIgnoreCase("AND")) {
                if (flagType.equalsIgnoreCase("exclude") && (Double.parseDouble(tokenValue) < Double.parseDouble(rangeRuleArray[0]) && Double.parseDouble(tokenValue) > Double.parseDouble(rangeRuleArray[2]))) {
                    flag = "red";
                }
                if (flagType.equalsIgnoreCase("flag") && (Double.parseDouble(tokenValue) < Double.parseDouble(rangeRuleArray[0]) && Double.parseDouble(tokenValue) > Double.parseDouble(rangeRuleArray[2]))) {
                    flag = "yellow";
                }
            }
            if (rangeRuleArray[1].equalsIgnoreCase("OR")) {
                if (flagType.equalsIgnoreCase("exclude") && (Double.parseDouble(tokenValue) < Double.parseDouble(rangeRuleArray[0]) || Double.parseDouble(tokenValue) > Double.parseDouble(rangeRuleArray[2]))) {
                    flag = "red";
                }
                if (flagType.equalsIgnoreCase("flag") && (Double.parseDouble(tokenValue) < Double.parseDouble(rangeRuleArray[0]) || Double.parseDouble(tokenValue) > Double.parseDouble(rangeRuleArray[2]))) {
                    flag = "yellow";
                }
            }*/
            if (flagType.equalsIgnoreCase("exclude")) {
                if (rangeRuleArray[1].equalsIgnoreCase("AND") && (Double.parseDouble(tokenValue) < Double.parseDouble(rangeRuleArray[0]) && Double.parseDouble(tokenValue) > Double.parseDouble(rangeRuleArray[2]))) {
                    flag = "red";
                    break;
                }
                if (rangeRuleArray[1].equalsIgnoreCase("OR") && (Double.parseDouble(tokenValue) < Double.parseDouble(rangeRuleArray[0]) || Double.parseDouble(tokenValue) > Double.parseDouble(rangeRuleArray[2]))) {
                    flag = "red";
                    break;
                }
            }
            if (flagType.equalsIgnoreCase("flag")) {
                if (rangeRuleArray[1].equalsIgnoreCase("AND") && (Double.parseDouble(tokenValue) < Double.parseDouble(rangeRuleArray[0]) && Double.parseDouble(tokenValue) > Double.parseDouble(rangeRuleArray[2]))) {
                    flag = "yellow";
                }
                if (rangeRuleArray[1].equalsIgnoreCase("OR") && (Double.parseDouble(tokenValue) < Double.parseDouble(rangeRuleArray[0]) || Double.parseDouble(tokenValue) > Double.parseDouble(rangeRuleArray[2]))) {
                    flag = "yellow";
                }
            }

        }
        return flag;
    }

    boolean checkIfExists(String fileName) throws SQLException {
        ResultSet fileExists = stmtFileExists.executeQuery("Select count(1) from `" + tableName + "` where source_filename='" + fileName + "'");
        int count = 0;
        while (fileExists.next()) {
            count = Integer.parseInt(fileExists.getString(1));
        }
        if (count == 0) {
            return false;
        }
        return true;
    }

    //main method to start ETL. The upload service should create and object and call startETL method as it is done in this method 
    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
        ETLProcessFiles processFiles1 = new ETLProcessFiles();
        //processFiles1.startETL("BRCR01", "Phizer", "7392-960779", "BRCR01 1-26-16");

        //horizontal
        //processFiles1.startETL("DRCR01", "Phizer", "7392-960779", "BRCR01 1-26-16");
        processFiles1.startETL("DRCR01", "Phizer", "8392-960779", "BRCR01 1-26-16");
    }
}
