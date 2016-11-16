package Capstone;
//check Ellie
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Anshu Agrawal
 */
public class ProcessFiles {

    DBConnectionManager manager = DBConnectionManager.getInstance();
    Statement stmt = null;
    Connection connection = null;
    private static final String BASE_RECEIVE_DIRECTORY = "C:/TestLoad/";
    //final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String siteID = "";
    String modelID = "";
    String sensorID = "";
    String uploadfilesAbsoluteDirectory = "";
    String tableName = "";
    int rowsToSkip = 0;
    String fileDelimiter = "";
    String columnNamesDataTypes = "";
    String[] columnDataType;
    String[] columnName;

    //Setup connections
    public ProcessFiles(){
        try {
            connection = manager.getConnection();
            stmt = connection.createStatement();
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(ProcessFiles.class.getName()).log(Level.SEVERE, null, ex);
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
        //start file processing
        startFileProcessing();
    }
    //assume 1st column be time
    public void startFileProcessing() throws FileNotFoundException, IOException, SQLException {
        //get file object to the file diectory
        File directory = new File(uploadfilesAbsoluteDirectory);
        //get fileList
        File fileList[] = directory.listFiles();
        //loop thru each file and process it
        BufferedReader br = null;
        for (File file : fileList) {
            System.out.println(file.getName());
            br = new BufferedReader(new FileReader(file));
            String line = null;
            //skip lines before we hit the actual data
            int skipLinesCtr = 0;
            int batchCounter = 0;
            while ((line = br.readLine()) != null) {
                long unixTime;
                if (skipLinesCtr != rowsToSkip) {
                    skipLinesCtr++;
                    continue;
                }
                System.out.println(line);
                if(batchCounter!=0 && batchCounter%20 ==0 ){
                    stmt.executeBatch();
                    connection.commit();
                }
                // need to know what is the type of delimiter it is
                //assumption: 1st field in the file is unixtime
                String[] lineTokens = line.split(fileDelimiter);
                StringBuilder lineTokensInsertString = new StringBuilder();
                for (int j = 1; j < lineTokens.length; j++) {
                    System.out.println(lineTokens[j]);
                    String token = "";
                    if (columnDataType[j].equals("varchar") || columnDataType[j].equals("datetime")) {
                        token = "\"" + lineTokens[j] + "\"";
                    } else {
                        token = lineTokens[j];
                    }
                    /*if (j < lineTokens.length - 1) {
                        lineTokensInsertString.append(token).append(",");
                    } else {
                        lineTokensInsertString.append(token);
                    }*/
                    lineTokensInsertString.append(token).append(",");
                    
                }    
                //if(columnName[j].toLowerCase().contains("time")){
                unixTime = Long.parseLong(lineTokens[0]);
                String dateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(unixTime * 1000));
                //}
                String[] dateSplit = dateTime.split(" ");
                int year = Integer.parseInt(dateSplit[0].split("-")[0]);
                int month = Integer.parseInt(dateSplit[0].split("-")[1]);
                int day = Integer.parseInt(dateSplit[0].split("-")[2]);
                int dayOfYear = dayOfYear(year,month,day);
                int hour = Integer.parseInt(dateSplit[1].split(":")[0]);
                int minute = Integer.parseInt(dateSplit[1].split(":")[1]);
                String fileName = file.getName();
                lineTokensInsertString.append(minute).append(",").append(hour).append(",").append(day).append(",").append(month).append(",").append(year).append(",").append(dayOfYear).append(",").append("\"").append(fileName).append("\"");
                String insertQuery = "insert into `" + tableName + "` values(" +unixTime+ "," + lineTokensInsertString.toString() + ")";
                System.out.println(insertQuery);
                stmt.addBatch(insertQuery);
                batchCounter++;
                //stmt.executeBatch();
                //stmt.executeUpdate(insertQuery);
                //connection.commit();
                //break;
                // need to know what table the data will go into
            }
            //break;
        }
        br.close();
        stmt.close();
    }
    
    public void setMappingInfo() {
        try {
            String sql = "SELECT TableName, RowsToSkip, FileDelimiter, ColumnNamesDataTypes FROM MappingTable where siteid=\"" + siteID + "\"and modelid=\"" + modelID + "\"and sensorid=\"" + sensorID + "\"";
            ResultSet rs = stmt.executeQuery(sql);
            //Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                tableName = rs.getString("TableName");
                rowsToSkip = rs.getInt("RowsToSkip");
                fileDelimiter = rs.getString("FileDelimiter");
                columnNamesDataTypes = rs.getString("ColumnNamesDataTypes");
            }
            rs.close();
            //stmt.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } /*finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
        }*/
        String[] columnNamesDataTypesSplits = columnNamesDataTypes.split(",");
        int columnNamesDataTypesLength = columnNamesDataTypesSplits.length;
        columnDataType = new String[columnNamesDataTypesLength];
        columnName = new String[columnNamesDataTypesLength];
        for (int i = 0; i < columnNamesDataTypesLength; i++) {
            columnName[i] = columnNamesDataTypesSplits[i].split(":")[0];
            columnDataType[i] = columnNamesDataTypesSplits[i].split(":")[1];
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
   //main method to start ETL. The upload service should create and object and call startETL method as it is done in this method 
    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
        ProcessFiles processFiles = new ProcessFiles();
        processFiles.startETL("BRCR01", "Phizer", "7392-960779", "BRCR01 1-26-16");
    }
}
