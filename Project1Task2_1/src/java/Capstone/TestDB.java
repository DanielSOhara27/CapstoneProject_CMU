/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capstone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author LP
 */
public class TestDB {

    DBConnectionManager manager = DBConnectionManager.getInstance();

    public void dbOps() throws SQLException {
        Statement stmt = null;
        Connection connection = null;
        
         String siteID ="BRCR01";
    String modelID ="Phizer";
    String sensorID ="7392-960779";
        connection = manager.getConnection();
        stmt = connection.createStatement();
        connection.setAutoCommit(false);
        String sql;
        sql = "SELECT TableName, RowsToSkip, FileDelimiter, ColumnNamesDataTypes FROM MappingTable where siteid=\"" + siteID+ "\"and modelid=\""+modelID +"\"and sensorid=\""+sensorID+"\"";
         ResultSet rs = stmt.executeQuery(sql);
        //STEP 5: Extract data from result set
        while (rs.next()) {
            //Retrieve by column name
            String tableName = rs.getString("TableName");
            int rowsToSkip = rs.getInt("RowsToSkip");
            String fileDelimiter = rs.getString("FileDelimiter");
            String columnNamesDataTypes = rs.getString("ColumnNamesDataTypes");
            
            //Display values
            System.out.println("TableName: " + tableName);
            System.out.println("RowsToSkip: " + rowsToSkip);
            System.out.println("FileDelimiter: " + fileDelimiter);
            System.out.println("ColumnNamesDataTypes: " + columnNamesDataTypes);
        }
        rs.close();
       
        
        /*String query = "insert into t1 values (?,?)";   
        PreparedStatement pStatement = manager.getConnection().prepareStatement(query);
        String name = "Anshu111";
        pStatement.setInt(1, 1);
        pStatement.setString(2,name);
        pStatement.addBatch();
        pStatement.setInt(1, 2);
        pStatement.setString(2,"Anshu2");
        pStatement.addBatch();
        pStatement.executeBatch();*/
        
       
        stmt.addBatch("insert into `t1` values(1,\"Anshu123\")");
        stmt.addBatch("insert into `t1` values(2,\"Anshu456\")");
        stmt.executeBatch();
        connection.commit();
        stmt.close();
    }

    public static void main(String[] a) throws SQLException {
            TestDB testDB = new TestDB();
            testDB.dbOps();
    }

}
