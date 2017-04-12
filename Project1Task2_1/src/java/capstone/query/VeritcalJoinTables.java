package capstone.query;

import capstone.connection.DBConnectionManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anshu
 */
public class VeritcalJoinTables {

    DBConnectionManager DBManager = null;
    Connection connection = null;
    Statement statement = null, stmt = null,stmt2=null;

    //initialize connections
    public VeritcalJoinTables() {
        DBManager = DBConnectionManager.getInstance();
        connection = DBManager.getConnection();
    }

    //vertical join tables method
    String joinTables(String[] tables, String startDate, String startTime, String endDate, String endTime, String flagInclude) throws SQLException {
        try {
            statement = connection.createStatement();
            stmt = connection.createStatement();
            stmt2 = connection.createStatement();
            //String[] columnAlias;
            //String[] columnName;

            //if (tables.length == 1) {

            //    return tables[0];
            //}
            
            String baseTable = "";
            StringBuilder nonBaseTables = new StringBuilder();
            String nonBaseTablesString = "";
            String[] nonBaseTablesArray;
            String[] tableNames = tables;
            for (int i = 0; i < tableNames.length; i++) {
                if (isBaseTable(tableNames[i])) {
                    baseTable = tableNames[i];
                } else {
                    nonBaseTables.append(tableNames[i]).append(",");
                }
            }
            nonBaseTablesString = nonBaseTables.toString().replaceAll(",$", "");

            System.out.println("Base Table: " + baseTable);
            System.out.println("Nonbase Table: " + nonBaseTablesString);

            nonBaseTablesArray = nonBaseTablesString.split(",");
            ResultSet rsBaseTable = statement.executeQuery("SELECT * FROM `" + baseTable + "`");
            ResultSetMetaData rsmdBaseTable = rsBaseTable.getMetaData();
            int columnCountBaseTable = rsmdBaseTable.getColumnCount();
            String[] columnNamesBaseTable = new String[columnCountBaseTable];

            //get aliases
            String sqlAliases = "SELECT ColumnNames FROM MappingTable where tablename='" + baseTable + "'";
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

            StringBuilder baseTableColumns = new StringBuilder();
            for (int i = 1; i <= columnCountBaseTable; i++) {
                columnNamesBaseTable[i - 1] = rsmdBaseTable.getColumnName(i);
                String colName="";
                if (hmapAlias.containsKey(rsmdBaseTable.getColumnName(i)) && !hmapAlias.get(rsmdBaseTable.getColumnName(i)).equals(" ") )          
                    colName = hmapAlias.get(rsmdBaseTable.getColumnName(i));
                else
                    colName = rsmdBaseTable.getColumnName(i);
                if (i != columnCountBaseTable) {
                    baseTableColumns.append(colName).append(",");
                } else {
                    baseTableColumns.append(colName);
                }
            }
            String combinedColumnNames = baseTableColumns.toString();
            System.out.println("combinedColumnNames: "+ combinedColumnNames);
            String combinedColumnNamesUpdated = combinedColumnNames.replace(",", "`,`");
            String createTable = combinedColumnNames.replace(",", "` varchar(600),`");
            String[] nonBaseTableSelects = new String[nonBaseTablesArray.length];

            for (int i = 0; i < nonBaseTablesArray.length; i++) {
                HashMap<String, String> hmap = new HashMap();
                ResultSet rsMappingColumnsNonBaseTable = statement.executeQuery("SELECT Source_ColumnName,Dest_ColumnName FROM `MappingColumn` where Source_TableName='" + baseTable + "' and Dest_TableName='" + nonBaseTablesArray[i] + "'");
                while (rsMappingColumnsNonBaseTable.next()) {
                    hmap.put(rsMappingColumnsNonBaseTable.getString(1), rsMappingColumnsNonBaseTable.getString(2));
                }
                hmap.put("isItFlagged", "isItFlagged");
                hmap.put("ConvertedTimeStamp", "ConvertedTimeStamp");
                hmap.put("Year", "Year");
                hmap.put("yDay", "yDay");
                hmap.put("Month", "Month");
                hmap.put("mDay", "mDay");
                hmap.put("Hour", "Hour");
                hmap.put("Minute", "Minute");
                hmap.put("Second", "Second");
                hmap.put("Source_Filename", "Source_Filename");
                StringBuilder nonBaseSelectStmt = new StringBuilder();
                nonBaseSelectStmt.append("Select ");
                for (int j = 0; j < columnNamesBaseTable.length; j++) {
                    if (hmap.containsKey(columnNamesBaseTable[j])) {
                        nonBaseSelectStmt.append("`").append(hmap.get(columnNamesBaseTable[j])).append("`").append(",");
                    } else {
                        nonBaseSelectStmt.append("null").append(",");
                    }
                }
                String remainderClauseNonBase;
                if (flagInclude.equals("no")) {
                    remainderClauseNonBase = nonBaseSelectStmt.toString().replaceAll(",$", "") + " from `" + nonBaseTablesArray[i] + "` where ConvertedTimestamp between '" + startDate + " " + startTime + "' and '" + endDate + " " + endTime + "' and isItFlagged<>1";
                } else {
                    remainderClauseNonBase = nonBaseSelectStmt.toString().replaceAll(",$", "") + " from `" + nonBaseTablesArray[i] + "` where ConvertedTimestamp between '" + startDate + " " + startTime + "' and '" + endDate + " " + endTime + "'";
                }

                nonBaseTableSelects[i] = remainderClauseNonBase;
            }
            StringBuilder baseSelectStmt = new StringBuilder();
            baseSelectStmt.append("Select ");
            for (int i = 0; i < columnNamesBaseTable.length; i++) {
                if (i != columnNamesBaseTable.length - 1) {
                    baseSelectStmt.append("`").append(columnNamesBaseTable[i]).append("`").append(",");
                } else {
                    baseSelectStmt.append("`").append(columnNamesBaseTable[i]).append("`");
                }
            }
            String remainderClauseBase;
            if (flagInclude.equals("no")) {
                remainderClauseBase = " from `" + baseTable + "` where ConvertedTimestamp between '" + startDate + " " + startTime + "' and '" + endDate + " " + endTime + "' and isItFlagged<>1";
                baseSelectStmt.append(remainderClauseBase);
            } else {
                remainderClauseBase = " from `" + baseTable + "` where ConvertedTimestamp between '" + startDate + " " + startTime + "' and '" + endDate + " " + endTime + "'";
                baseSelectStmt.append(remainderClauseBase);
            }
            for (int i = 0; i < nonBaseTableSelects.length; i++) {
                baseSelectStmt.append(" union ").append(nonBaseTableSelects[i]);
            }
            Random rand = new Random();
            int value = rand.nextInt(100);
            //System.out.println(value);
            String tempTable = "VTempTableVert" + value;
            System.out.println("temptable: " + tempTable);
            String sql = "CREATE TABLE " + tempTable + "(`" + createTable + "` varchar(600))";
            statement.executeUpdate(sql);

            System.out.println("Final select state: " + baseSelectStmt.toString());
            
            String finalSelect="";
            if(tables.length==1)
                finalSelect=baseSelectStmt.toString().split("union")[0];
            else
                finalSelect=baseSelectStmt.toString();
            ResultSet verticalJoin = statement.executeQuery(finalSelect);
            int batchCounter = 0;
            while (verticalJoin.next()) {
                if (batchCounter != 0 && batchCounter % 5 == 0) {
                    stmt.executeBatch();
                }
                StringBuilder insertQueryValues = new StringBuilder();
                String insertQuery;
                for (int i = 1; i <= columnCountBaseTable; i++) {
                    if (i != columnCountBaseTable) {
                        insertQueryValues.append(verticalJoin.getObject(i)).append(",");
                    } else {
                        insertQueryValues.append(verticalJoin.getObject(i));
                    }
                }
                insertQuery = "insert into " + tempTable + "(`" + combinedColumnNamesUpdated + "`) values('" + insertQueryValues.toString().replace(",", "','") + "')";
                stmt.addBatch(insertQuery.replace("'null'", "null"));
                batchCounter++;
            }
            stmt.executeBatch();
            return tempTable;
        } catch (SQLException ex) {
            Logger.getLogger(HorizontalJoinTables.class.getName()).log(Level.SEVERE, null, ex);
            return "fail";
        }
    }

    // find if a table is basetable
    boolean isBaseTable(String table) throws SQLException {
        String query = "Select TypeSensor_BaseTable from MappingTable where tablename='" + table + "'";
        ResultSet isBase = statement.executeQuery(query);
        while (isBase.next()) {
            if (isBase.getString(1).equals("TRUE")) {
                return true;
            }
        }
        return false;
    }

    

    public static void main(String[] args) throws SQLException {
        VeritcalJoinTables veritcalJoin = new VeritcalJoinTables();
        //String[] tableNames = "Ryan_BRCR01_Phizer_7392-960779_DataTable,Ryan_BRCR01_Phizer_7392-960779_DataTable1,Ryan_BRCR01_Phizer_7392-960779_DataTable2".split(",");
        String[] tableNames = "Ryan1_BRCR01_Phizer_7392-960779_DataTable,".split(",");
        System.out.println(veritcalJoin.joinTables(tableNames, "2015-11-01", "12:00", "2015-12-31", "12:00", "yes"));
    }
}
