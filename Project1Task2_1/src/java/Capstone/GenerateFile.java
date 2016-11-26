package Capstone;

/**
 *
 * @author Anshu Agrawall
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateFile {

    // declare final variables
    DBConnectionManager manager = DBConnectionManager.getInstance();
    Statement stmt = null;
    Connection connection = null;

    public static void main(String[] args) throws SQLException, IOException {

        String file = "Ryan_BRCR01_Phizer_7392-960779_DataTable6.csv";
        String outFile = "Ryan_BRCR01_Phizer_7392-960779_DataTable_Out.csv";

        //String workingDirectory = System.getProperty("user.dir");
        //String absoluteFilePath = "";
        //absoluteFilePath = workingDirectory + File.separator + file; System.out.print(absoluteFilePath);
        GenerateFile genDownloadFile = new GenerateFile();
        genDownloadFile.exportData(file,outFile);
    }

    public void exportData(String filename, String outFile) throws SQLException, IOException {

        connection = manager.getConnection();
        stmt = connection.createStatement();
        FileWriter fw = new FileWriter("C:/TestDownload/" + outFile);

        //For comma separated file
        String query = "SELECT * into OUTFILE  '" + filename
                + "' FIELDS TERMINATED BY ',' FROM `Ryan_BRCR01_Phizer_7392-960779_DataTable` t";
        stmt.executeQuery(query);
        String query1 = "select * from `Ryan_BRCR01_Phizer_7392-960779_DataTable`";
        ResultSet rs = stmt.executeQuery(query1);
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int numberOfColumns = rsMetaData.getColumnCount();
        //StringBuilder header = new StringBuilder("");
        for (int i = 1; i < numberOfColumns + 1; i++) {
            String columnName = rsMetaData.getColumnName(i);
            System.out.println(columnName);
            if(i!=numberOfColumns)
                fw.append(columnName).append(",");
            else 
                fw.append(columnName);
        }
        fw.append("\n");
        fw.flush();
        //System.out.println();
        fw.close();
        BufferedReader br = new BufferedReader(new FileReader("C:/ProgramData/MySQL/MySQL Server 5.7/Data/test/"+ filename));
        FileWriter fileWritter = new FileWriter("C:/TestDownload/"+ outFile,true);
    	BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	String line = null;
        while ((line = br.readLine()) != null) {
            bufferWritter.write(line);
            bufferWritter.write("\n");
        }bufferWritter.close();

	        System.out.println("Done");
        
        /*while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append('\n');
               }
            fw.flush();
            fw.close();*/

    }

}
