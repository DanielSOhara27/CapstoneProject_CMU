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
   
    public JoinRowSet joinTable(String tableNam1_1, String tableName_2){

        try {
        DBConnectionManager DBManager = DBConnectionManager.getInstance();
        Connection connection = DBManager.getConnection();
        Statement statement;
        statement = connection.createStatement();
        
        //need 2 table name as input
        
        String queryTableMapping = "SELECT tableMapping where table = table1 or table2";
        ResultSet intervalRS = statement.executeQuery(queryTableMapping);
        intervalRS.absolute(1);
        int interval1 = intervalRS.getInt("RangeBetweenReadings");   
        String tableName1 = intervalRS.getString("TableName");
        intervalRS.absolute(1);
        int interval2 = intervalRS.getInt("RangeBetweenReadings"); 
        String tableName2 = intervalRS.getString("TableName");
        
        long span_baseTable;
        long span_joinedTable;
        if (interval1 <= interval2){
            String baseTableName = tableName1;
            String joinedTableName = tableName2;
            span_baseTable = (long)interval1/2;
            span_joinedTable = (long)interval2/2;
        } else {
            String baseTableName = tableName2;
            String joinedTableName = tableName1;
            span_baseTable = (long)interval2/2;
            span_joinedTable = (long)interval1/2;
        }
        
        // use baseTableName and joinedTableName as a SQL query
        
        String queryBaseTable = "SELECT TimeLX, `BV (Volts)`, `T (deg C)`,`DO (mg/l)`,`Q ()`,minute,hour,mday,mon,year,yday,`SourceFile` FROM `Ryan_BRCR01_Phizer_7392-960779_DataTable`";
        String queryJoinedTable = "SELECT TimeLX, `BV (Volts)`, `T (deg C)`,`DO (mg/l)`,`Q ()`,minute,hour,mday,mon,year,yday,`SourceFile` FROM `Ryan_BRCR01_Phizer_7392-960779_DataTable`";
        ResultSet baseTable = statement.executeQuery(queryBaseTable);
        ResultSet joinedTable = statement.executeQuery(queryJoinedTable);
        ResultSetMetaData rsmd_baseTable = baseTable.getMetaData();
        ResultSetMetaData rsmd_joinedTable = joinedTable.getMetaData();
        

               
        int cursor_joinedTable = 1;
        while (baseTable.next()) {
            Timestamp baseTimeStamp = baseTable.getTimestamp("Converted_Timestamp");
            long baseTime = baseTimeStamp.getTime(); 
            long baseTime_min = TimeUnit.MILLISECONDS.toMinutes(baseTime);             
            //Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //String baseTimeStamp_String = formatter.format(baseTimeStamp);
            //String hour = baseTimeStamp_String.substring(11,12);
            //String minute = baseTimeStamp_String.substring(14,15);
            
            long baseTime_lower = baseTime_min - span_baseTable; //find lower bound
            long baseTime_upper = baseTime_min + span_joinedTable; //find upper bound

            joinedTable.absolute(cursor_joinedTable); // moves the cursor to the fifth row of rs
            Timestamp joinedTimeStamp = joinedTable.getTimestamp("Converted_Timestamp");
            long joinedTime = joinedTimeStamp.getTime(); 
            long joinedTime_min = TimeUnit.MILLISECONDS.toMinutes(baseTime); 
            
            if (baseTime_lower <= joinedTime_min & joinedTime_min < baseTime_upper){
                joinedTable.updateString("Converted_Timestamp", "xxx"); // updates time to be the same as base table
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
    
   