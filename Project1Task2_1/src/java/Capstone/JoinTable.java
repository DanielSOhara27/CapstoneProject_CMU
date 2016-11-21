/**
 *
 * @author NaTT
 */
package Capstone;      
import java.util.*;
import java.sql.*;
import com.sun.rowset.CachedRowSetImpl;
import Capstone.DBConnectionManager;
import com.sun.rowset.JoinRowSetImpl;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.JoinRowSet;
import static javax.sql.rowset.JoinRowSet.LEFT_OUTER_JOIN;

public class JoinTable {  
   
    public JoinRowSet joinTable(String tableName_1, String tableName_2){

        try {
            
            //Create database connection    
            DBConnectionManager DBManager = DBConnectionManager.getInstance();
            Connection connection = DBManager.getConnection();
            Statement statement;
            statement = connection.createStatement();


            // Query from TableMapping to get RangeBetweenReadings (interval) for both table
            String queryTableMapping = "SELECT `RangeBetweenReading`, `TableName` FROM MappingTable WHERE `TableName` = `"+ tableName_1 +"` OR `TableName` = `" + tableName_1+ "`";
            ResultSet intervalRS = statement.executeQuery(queryTableMapping);
            // Get the information from row 1
            intervalRS.absolute(1);
            int interval1 = intervalRS.getInt("RangeBetweenReadings");   
            String tableName1 = intervalRS.getString("TableName");
            // Get the information from row 2
            intervalRS.absolute(2);
            int interval2 = intervalRS.getInt("RangeBetweenReadings"); 
            String tableName2 = intervalRS.getString("TableName");
            
            // Assign base table and time span of base table
            String baseTableName;
            String joinedTableName;
            long span_baseTable;
            long span_joinedTable;
            if (interval1 <= interval2){
                baseTableName = tableName1;
                joinedTableName = tableName2;
                span_baseTable = (long)interval1/2;
            } else {
                baseTableName = tableName2;
                joinedTableName = tableName1;
                span_baseTable = (long)interval2/2;
            }

            // use baseTableName and joinedTableName as a SQL query
            String queryBaseTable = "SELECT * FROM `" + baseTableName+ "`";
            String queryJoinedTable = "SELECT * FROM `"+ joinedTableName + "`";
            ResultSet baseTable = statement.executeQuery(queryBaseTable);
            ResultSet joinedTable = statement.executeQuery(queryJoinedTable);
            ResultSetMetaData rsmd_baseTable = baseTable.getMetaData();
            ResultSetMetaData rsmd_joinedTable = joinedTable.getMetaData();
            
            int cursor_joinedTable = 1;
            // Run through everyline of the base table
            while (baseTable.next()) {
                Timestamp baseTimeStamp = baseTable.getTimestamp("Converted_Timestamp");
                long baseTime = baseTimeStamp.getTime(); 
                long baseTime_min = TimeUnit.MILLISECONDS.toMinutes(baseTime);             
                //Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //String baseTimeStamp_String = formatter.format(baseTimeStamp);
                //String hour = baseTimeStamp_String.substring(11,12);
                //String minute = baseTimeStamp_String.substring(14,15);

                long baseTime_lower = baseTime_min - span_baseTable; //find lower bound
                long baseTime_upper = baseTime_min + span_baseTable; //find upper bound

                joinedTable.absolute(cursor_joinedTable); // moves the cursor to the fifth row of rs
                Timestamp joinedTimeStamp = joinedTable.getTimestamp("Converted_Timestamp");
                long joinedTime = joinedTimeStamp.getTime(); 
                long joinedTime_min = TimeUnit.MILLISECONDS.toMinutes(joinedTime); 
                
                // If the time in joinedTable fall in the range of baseTable, change the time in joinedTable to be the same as baseTable
                if (baseTime_lower <= joinedTime_min & joinedTime_min < baseTime_upper){
                    joinedTable.updateTimestamp("Converted_Timestamp", baseTimeStamp); // updates time to be the same as base table
                    joinedTable.updateRow(); // updates the row in the data source
                    cursor_joinedTable++;
                } 
            }
            //ref https://docs.oracle.com/javase/tutorial/jdbc/basics/joinrowset.html
            //http://www.onjava.com/pub/a/onjava/2004/06/23/cachedrowset.html
            JoinRowSet jrs = new JoinRowSetImpl(); //A JoinRowSet object serves as the holder of a SQL JOIN
            jrs.setJoinType(LEFT_OUTER_JOIN);
            CachedRowSetImpl baseTable_crs = new CachedRowSetImpl();
            CachedRowSetImpl joinedTable_crs = new CachedRowSetImpl();
            baseTable_crs.populate(baseTable);
            joinedTable_crs.populate(joinedTable);

            jrs.addRowSet(baseTable_crs, "Converted_Timestamp");
            jrs.addRowSet(joinedTable_crs, "Converted_Timestamp");


            return jrs;
            //while (jrs.next()) {
                //save in csv format
            //}

        
        } catch (SQLException ex) {
            Logger.getLogger(JoinTable.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
        
}
    
   