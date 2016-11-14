package Capstone;

/**
 *
 * @author Anshu Agrawal
 */

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateFile {
    // declare final variables
    DBConnectionManager manager = DBConnectionManager.getInstance();
            
    public static void main(String[] args) {
        
        String file = "capstonetest122.csv";
        
        //String workingDirectory = System.getProperty("user.dir");
        
        //String absoluteFilePath = "";
        //absoluteFilePath = workingDirectory + File.separator + file; System.out.print(absoluteFilePath);
        
        GenerateFile genDownloadFile = new GenerateFile();
        genDownloadFile.exportData(file);
    }
    
    public void exportData(String filename) {
        Statement stmt;
        String query;
        try {
            stmt = manager.getConnection().createStatement();
             
            //For comma separated file
            query = "SELECT id,text,price into OUTFILE  '"+filename+
                    "' FIELDS TERMINATED BY ',' FROM testtable t";
            stmt.executeQuery(query);
             
        } catch(Exception e) {
            e.printStackTrace();
            stmt = null;
        }
    }
     
}
 
