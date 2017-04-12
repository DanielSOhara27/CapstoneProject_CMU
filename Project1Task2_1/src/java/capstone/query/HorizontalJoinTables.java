package capstone.query;

import capstone.connection.DBConnectionManager;
import java.sql.*;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Anshu
 */
public class HorizontalJoinTables {
    public static void main(String[] a) {
        HorizontalJoinTables j = new HorizontalJoinTables();
        System.out.println(j.joinTables("Ryan_DRCR01_Phizer_7392-960779_DataTable", "Ryan_DRCR01_Phizer_8392-960779_DataTable","2015-01-01", "12:00", "2016-12-31", "12:00", "yes"));
    }
    public String joinTables(String tableName_1, String tableName_2,String startDate,String startTime,String endDate, String endTime,String flagInclude) {
        try {
            //Create database connection    
            DBConnectionManager DBManager = DBConnectionManager.getInstance();
            Connection connection = DBManager.getConnection();
            Statement statement = connection.createStatement();
            Statement statement2= connection.createStatement();
            Statement stmt2= connection.createStatement();
            // Query from TableMapping to get RangeBetweenReadings (interval) for both table
            String queryTableMapping = "SELECT RangeBetweenReadings, TableName FROM `MappingTable` WHERE TableName = \"" + tableName_1 + "\" OR TableName = \"" + tableName_2 + "\"";
            ResultSet intervalRS = statement.executeQuery(queryTableMapping);
            // Get the information from row 1
            intervalRS.absolute(1);
            int interval1 = Integer.parseInt(intervalRS.getString("RangeBetweenReadings"));
            String tableName1 = intervalRS.getString("TableName");
            // Get the information from row 2
            intervalRS.absolute(2);
            int interval2 = Integer.parseInt(intervalRS.getString("RangeBetweenReadings"));
            String tableName2 = intervalRS.getString("TableName");
            // Assign base table and time span of base table
            String baseTableName;
            String joinedTableName;
            long span_baseTable;
            if (interval1 <= interval2) {
                baseTableName = tableName1;
                joinedTableName = tableName2;
                span_baseTable = (long) interval1 / 2;
            } else {
                baseTableName = tableName2;
                joinedTableName = tableName1;
                span_baseTable = (long) interval2 / 2;
            }
            // use baseTableName and joinedTableName as a SQL query
            String queryBaseTable;
            String queryJoinedTable;
            if(flagInclude.equals("no")){
                queryBaseTable = "SELECT * FROM `" + baseTableName + "` where ConvertedTimestamp between '"+startDate+ " " +startTime+"' and '"+endDate+" "+ endTime+"' and isItFlagged<>1";
                queryJoinedTable = "SELECT * FROM `" + joinedTableName + "` where ConvertedTimestamp between '"+startDate+ " " +startTime+"' and '"+endDate+" "+ endTime+"' and isItFlagged<>1";
            } else{
                queryBaseTable = "SELECT * FROM `" + baseTableName + "` where ConvertedTimestamp between '"+startDate+ " " +startTime+"' and '"+endDate+" "+ endTime+"'";
                queryJoinedTable = "SELECT * FROM `" + joinedTableName + "` where ConvertedTimestamp between '"+startDate+ " " +startTime+"' and '"+endDate+" "+ endTime+"' ";
            }
            System.out.println("Base table query: "+queryBaseTable);
            System.out.println("Join table query: "+queryJoinedTable);
            ResultSet baseTable = statement.executeQuery(queryBaseTable);
            ResultSetMetaData rsmdBaseTable = baseTable.getMetaData();
            int columnCountBaseTable = rsmdBaseTable.getColumnCount();
            StringBuilder columnNamesBaseTable = new StringBuilder();
            for (int i = 1; i <= columnCountBaseTable; i++) {
                columnNamesBaseTable.append(rsmdBaseTable.getColumnName(i)).append(",");
            }
            String columnNamesBaseTableString = columnNamesBaseTable.toString();
            StringBuilder columnNamesJoinedTable = new StringBuilder();
            ResultSet joinedTable;
            joinedTable = statement2.executeQuery(queryJoinedTable);
            ResultSetMetaData rsmdJoinedTable = joinedTable.getMetaData();
            int columnCountJoinedTable = rsmdJoinedTable.getColumnCount();
            for (int i = 1; i <= columnCountJoinedTable; i++) {
                if (i == columnCountJoinedTable) {
                    if (columnNamesBaseTableString.contains(rsmdJoinedTable.getColumnName(i))) {
                        columnNamesJoinedTable.append(rsmdJoinedTable.getColumnName(i)).append("_2");
                    } else {
                        columnNamesJoinedTable.append(rsmdJoinedTable.getColumnName(i));
                    }
                } else if (columnNamesBaseTableString.contains(rsmdJoinedTable.getColumnName(i))) {
                    columnNamesJoinedTable.append(rsmdJoinedTable.getColumnName(i)).append("_2").append(",");
                } else {
                    columnNamesJoinedTable.append(rsmdJoinedTable.getColumnName(i)).append(",");
                }
            }
            joinedTable.close();
            String columnNamesJoinedTableString = columnNamesJoinedTable.toString();
            
             //get aliases
            String sqlAliases = "SELECT ColumnNames FROM MappingTable where tablename='" + tableName_1 + "'";
            ResultSet rsAliases = stmt2.executeQuery(sqlAliases);
            String columnNamesDataTypes = "";
            while (rsAliases.next()) {
                columnNamesDataTypes = rsAliases.getString("ColumnNames");
            }
            rsAliases.close();

            String[] columnNamesDataTypesSplits = columnNamesDataTypes.split(",");
            int columnNamesDataTypesLength = columnNamesDataTypesSplits.length;
            //columnAlias = new String[columnNamesDataTypesLength];
            //columnName = new String[columnNamesDataTypesLength];
            
            HashMap<String, String> hmapAlias = new HashMap();
            
            for (int i = 0; i < columnNamesDataTypesLength; i++) {
                hmapAlias.put(columnNamesDataTypesSplits[i].split("\\|")[1], columnNamesDataTypesSplits[i].split("\\|")[4]);
            }

            
            for (String key : hmapAlias.keySet()) {
                if(columnNamesBaseTableString.contains(key) && !hmapAlias.get(key).equals(" ") ){
                    columnNamesBaseTableString = columnNamesBaseTableString.replace(key, hmapAlias.get(key));
                }
            }
            
            String sqlAliases2 = "SELECT ColumnNames FROM MappingTable where tablename='" + tableName_2 + "'";
            ResultSet rsAliases2 = stmt2.executeQuery(sqlAliases2);
            String columnNamesDataTypes2 = "";
            while (rsAliases2.next()) {
                columnNamesDataTypes2 = rsAliases2.getString("ColumnNames");
            }
            rsAliases2.close();

            String[] columnNamesDataTypesSplits2 = columnNamesDataTypes2.split(",");
            int columnNamesDataTypesLength2 = columnNamesDataTypesSplits2.length;
            //columnAlias = new String[columnNamesDataTypesLength];
            //columnName = new String[columnNamesDataTypesLength];
            
            HashMap<String, String> hmapAlias2 = new HashMap();
            
            for (int i = 0; i < columnNamesDataTypesLength2; i++) {
                hmapAlias2.put(columnNamesDataTypesSplits2[i].split("\\|")[1], columnNamesDataTypesSplits2[i].split("\\|")[4]);
            }

            
            for (String key2 : hmapAlias2.keySet()) {
                if(columnNamesJoinedTableString.contains(key2) && !hmapAlias2.get(key2).equals(" ") ){
                    columnNamesJoinedTableString = columnNamesJoinedTableString.replace(key2, hmapAlias2.get(key2));
                }
            }
            
            //String combinedColumnNames = columnNamesBaseTable + columnNamesJoinedTable.toString();
            String combinedColumnNames = columnNamesBaseTableString + columnNamesJoinedTableString;
            
            System.out.println("combinedColumnNames:"+combinedColumnNames);
            String combinedColumnNamesUpdated = combinedColumnNames.replace(",", "`,`");
            String createTable = combinedColumnNames.replace(",", "` varchar(600),`");
            Random rand = new Random(); 
            int value = rand.nextInt(100);
            //System.out.println(value);
            String tempTable= "HTempTableHori"+value;
            String sql = "CREATE TABLE "+ tempTable+ "(`" + createTable + "` varchar(600))";
            Statement stmt = connection.createStatement();
            //stmt.executeUpdate(sql);
            statement2.executeUpdate(sql);
            int batchCounter = 0;
            // Run through everyline of the base table
            while (baseTable.next()) {
                Timestamp baseTimeStamp = baseTable.getTimestamp("ConvertedTimestamp");
                long baseTime = baseTimeStamp.getTime();
                long baseTime_min = TimeUnit.MILLISECONDS.toMinutes(baseTime);
                long baseTime_lower = baseTime_min - span_baseTable; //find lower bound
                long baseTime_upper = baseTime_min + span_baseTable; //find upper bound
                ResultSet joinedTable1 = statement2.executeQuery(queryJoinedTable);
                int flag = 0;
                String baseRowData = "";
                StringBuilder rowData = new StringBuilder();
                for (int i = 1; i <= columnCountBaseTable; i++) {
                    rowData.append(baseTable.getObject(i)).append(",");
                }
                baseRowData = rowData.toString();
                String matchFound = "";
                StringBuilder noMatch = new StringBuilder();
                for (int i = 1; i <= columnCountJoinedTable; i++) {
                    if (i == columnCountJoinedTable) {
                        noMatch.append("-");
                    } else {
                        noMatch.append("-").append(",");
                    }
                }
                String matchNotFound = noMatch.toString();
                while (joinedTable1.next()) {
                    Timestamp joinedTimeStamp = joinedTable1.getTimestamp("ConvertedTimestamp");
                    long joinedTime = joinedTimeStamp.getTime();
                    long joinedTime_min = TimeUnit.MILLISECONDS.toMinutes(joinedTime);
                    if (baseTime_lower <= joinedTime_min && joinedTime_min < baseTime_upper) {
                        flag = 1;
                        StringBuilder match = new StringBuilder();
                        for (int i = 1; i <= columnCountJoinedTable; i++) {
                            if (i == columnCountJoinedTable) {
                                match.append(joinedTable1.getObject(i));
                            } else {
                                match.append(joinedTable1.getObject(i)).append(",");
                            }
                        }
                        matchFound = match.toString();
                        break;
                    }
                }
                joinedTable1.close();
                String insertQuery;
                if (flag == 0) {
                    String values = baseRowData + matchNotFound;
                    String values1 = values.replace(",", "','").replace("'null'", "null");
                    insertQuery = "insert into `" + tempTable + "` (`" + combinedColumnNamesUpdated + "`) values('" + values1 + "')";
                    stmt.addBatch(insertQuery);
                    batchCounter++;
                } else {
                    String values2 = baseRowData + matchFound;
                    String values3 = values2.replace(",", "','").replace("'null'", "null");
                    insertQuery = "insert into `" + tempTable + "` (`" + combinedColumnNamesUpdated + "`) values('" + values3 + "')";
                    stmt.addBatch(insertQuery);
                    batchCounter++;
                }
                System.out.println("Insert Query: "+insertQuery);
                if (batchCounter != 0 && batchCounter % 5 == 0) {
                    stmt.executeBatch();
                }
            }
            stmt.executeBatch();
            return tempTable;
        } catch (SQLException ex) {
            Logger.getLogger(HorizontalJoinTables.class.getName()).log(Level.SEVERE, null, ex);
            return "fail";
        }
    }
}

