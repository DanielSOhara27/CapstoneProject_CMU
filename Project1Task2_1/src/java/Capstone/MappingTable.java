/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capstone;


import Capstone.DBConnectionManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;
/**
 *
 * @author danielohara
 */
public class MappingTable{
    DBConnectionManager manager = DBConnectionManager.getInstance();
     
    
    public ResultSet InsertMetaData_MappingTable2(String createTableParameterSet1){
       Statement stmt;
       String query;
       JSONObject myJSONobject;
       try{
        System.out.println("Inside the try");
        myJSONobject = new JSONObject(createTableParameterSet1);
        String userTableName = myJSONobject.getString("Username") +"_"+ myJSONobject.getString("SiteID") +"_"+ myJSONobject.getString("ModelID") +"_"+ myJSONobject.getString("SensorID") +"_DataTable";
        stmt = manager.getConnection().createStatement();
             
            //For comma separated file
            query = "INSERT INTO MappingTable (SiteID, ModelID, SensorID, Username, SiteName, SensorType, Location, NumColumn, Delimiter, RangeBetweenReadings, AcceptableRange,TypeSensor_BaseTable, Site_BaseTable, Public, TableName, ColumnNames, Row_To_Skip) "+
                    " SELECT * FROM ( SELECT "+ 
                        "'" + myJSONobject.getString("SiteID") +"' AS SITEID_ALIAS, " +
                        "'" + myJSONobject.getString("ModelID") +"' AS MODELID_ALIAS, " +
                        "'" + myJSONobject.getString("SensorID") +"' AS SENSORID_ALIAS, " +
                        "'" + myJSONobject.getString("Username") +"' AS USERNAME_ALIAS, " +
                        "'" + myJSONobject.getString("SiteName") +"' AS SITENAME_ALIAS, " +
                        "'" + myJSONobject.getString("SensorType") + "' AS SENSORTYPE_ALIAS, " + 
                        "'" + myJSONobject.getString("Location") + "' AS LOCATION_ALIAS, " +
                        "'" + myJSONobject.getString("NumColumn") + "' AS NUMCOLUMN_ALIAS, " +
                        "'\\" + myJSONobject.getString("Delimiter") +"' AS DELIMiTER_ALIAS, " +
                        "'" + myJSONobject.getString("RangeBetweenReadings") + "' AS RANGEBETWEEN_ALIAS, " +
                        "'" + myJSONobject.getString("AcceptableRange") + "' AS ACCEPTEDRANGE_ALIAS, " +
                        "'" + myJSONobject.getString("TypeSensor_BaseTable") + "' AS TYPESENSOR_BASE_ALIAS, " +
                        "'" + myJSONobject.getString("Site_BaseTable") + "' AS SITE_BASE_ALIAS, " +
                        "'" + myJSONobject.getString("Public") + "' AS PUBLIC_ALIAS, " +
                        "'" + userTableName +"', " +
                        "'" + myJSONobject.getString("ColumnNames") + "' AS COLUMNNAMES_ALIAS, " +
                        "'" + myJSONobject.getString("Row_To_Skip") + "' AS ROWSKIP_ALIAS" +
                        " ) AS TMP " +
                    "WHERE NOT EXISTS ( " + 
                        "SELECT TableName FROM MappingTable WHERE TableName='" + userTableName + "'" +
                        " ) LIMIT 1";
                    
            System.out.println(query);        
            stmt.executeUpdate(query);
            System.out.println("after the executeUpdate");
            ResultSet res = stmt.executeQuery("SELECT * FROM MappingTable");
            return res;
       }catch(SQLException e){
            e.printStackTrace();
            stmt = null;
            myJSONobject = null;
            return null;
       } catch (JSONException ex) {
            ex.printStackTrace();
            //Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
            stmt = null;
            myJSONobject = null;
            return null;
        }
        
        
    }//InsertMetaData_MappingTable

    public ResultSet DescribeTables (String Metadata_Tablename){
       Statement stmt;
       String query;
       try{
          stmt = manager.getConnection().createStatement();
          
          query = "Describe "+ Metadata_Tablename;
          
          ResultSet res = stmt.executeQuery(query);
          return res;

       }catch(SQLException e){
            e.printStackTrace();
            stmt = null;
            return null;
       }
    }

    public ResultSet CreateUserTable(String createUserTableParameterSet, String tableName){
       Statement stmt;
       String query;
       JSONObject myJSONobject;

        
       
        try{
          myJSONobject = new JSONObject(createUserTableParameterSet);
          JSONArray myJSONArray = myJSONobject.getJSONArray("User_Columns");
          JSONObject aux;
          
          int numColumns2 = myJSONobject.getInt("numColumn");
          
          query = "CREATE TABLE " + tableName + " ";
          query = query + "( ";
          query = query + " userTable_ID INT(11) NOT NULL AUTO_INCREMENT,";
          query = query + " isItFlagged VARCHAR(10) NOT NULL,";
          query = query + " Converted_Timetamp VARCHAR(20),";
          query = query + " TimeFormat VARCHAR(50) NOT NULL,";
          query = query + " Original_Timestamp VARCHAR(50) NOT NULL,";
          query = query + " Year INT(2),";
          query = query + " Yday INT(2),";
          query = query + " Ymonth INT(1), ";
          query = query + " Mday INT(1), ";
          query = query + " SourceFile_Name VARCHAR(100) NOT NULL,";
          
          for(int x = 0; x < myJSONArray.length(); x++){
               aux = myJSONArray.getJSONObject(x);
               query = query + " " + aux.getString("nameColumn") + " " + parseDataType(aux.getString("dataType"));
               
               if(x != ( myJSONArray.length() -1) ) query = query + ",";
          }

          stmt = manager.getConnection().createStatement();
          
          stmt.executeUpdate(query);
          
          query = "DESCRIBE " + tableName;
          ResultSet res = stmt.executeQuery(query);
          return res;

       }catch(SQLException e){
            e.printStackTrace();
            stmt = null;
            return null;
       }catch (JSONException ex) {
            ex.printStackTrace();
            //Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
            stmt = null;
            myJSONobject = null;
            return null;
        }
    }

    public String PrintCreateTable(ResultSet sqlResult){
         String myTable = "<h3>How the MappingTable After creation</h3>"
                        + "<table border=\"1\">"
                        + "<tr>"
                        + "<th>MappingTable_ID</th>"
                        + "<th>SiteID</th>"
                        + "<th>ModelID</th>"
                        + "<th>SensorID</th>"
                        + "<th>Username</th>"
                        + "<th>SiteName</th>"
                        + "<th>SensorType</th>"
                        + "<th>Location</th>"
                        + "<th>NumColumn</th>"
                        + "<th>Delimiter</th>"
                        + "<th>RangeBetweenReadings</th>"
                        + "<th>AcceptableRange</th>"
                        + "<th>TypeSensor_BaseTable</th>"
                        + "<th>Site_BaseTable</th>"
                        + "<th>Public</th>"
                        + "<th>TableName</th>"
                        + "<th>ColumnNames</th>"
                        + "<th>Row_To_Skip</th>"
                        + "</tr>";
        try {
            //System.out.println(myTable);
            while(sqlResult.next()){
                myTable = myTable + "<tr>";
                myTable = myTable + "<td>" + sqlResult.getInt("MappingTable_ID") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("SiteID") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("ModelID") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("SensorID") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("Username") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("SiteName") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("SensorType") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("Location") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getInt("NumColumn") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("Delimiter") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("RangeBetweenReadings") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("AcceptableRange") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("TypeSensor_BaseTable") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("Site_BaseTable") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("Public") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("TableName") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getString("ColumnNames") + "</td>";
                myTable = myTable + "<td>" + sqlResult.getInt("Row_To_Skip") + "</td>";
                myTable = myTable + "</tr>";
            }//while
        } catch (SQLException ex) {
            Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
             myTable = "<p> SOMETHING WENT WRONG </p>";
             return myTable;
        }//catch exception
        /*If al goes well, then this is what is being returned on Success*/
        myTable = myTable + "</table><br /><br />";
        return myTable;
    }//printCreateTable

    public String parseDataType(String dataType){
        
        String res = "";
        switch(dataType.toLowerCase()){
            case "int":
                res = "INT(3)";
                break;
            case "string":
                res = "VARCHAR(5000)";
                break;
            case "date":
                res = "VARCHAR(20)";
                break;
            case "boolean":
                res = "VARCHAR(10)";
                break;
            default:
                res = "varchar(5000)";
                break;
        }
        return res;
    }

    public String getUserTableName(String mySiteID, String myModelID, String mySensorID){
       Statement stmt;
       String query;
       ResultSet res;
       String result;
       
       try{
           stmt = manager.getConnection().createStatement();
           query = "SELECT TableName FROM MappingTable WHERE"
                   + " SiteID ='" + mySiteID +"'"
                   + " AND ModelID='" + myModelID +"'"
                   + " AND SensorID='" + mySensorID +"'";
           res = stmt.executeQuery(query);
           
           result = res.getString("TableName");
           
       }catch(SQLException e){
            e.printStackTrace();
            stmt = null;
            return null;
       }
       return result;
    }

    public ResultSet ListUserTables_Keys(String myUsername){
       Statement stmt;
       String query;
       JSONObject myJSONobject;
       
       try{
           stmt = manager.getConnection().createStatement();
           
           query = "SELECT SiteID, ModelID, SensorID, TableName FROM MappingTable WHERE Username='"+ myUsername +"'";
           ResultSet res = stmt.executeQuery(query);
           return res;
       }catch(SQLException e){
           e.printStackTrace();
           stmt = null;
           query = "";
           return null;
       }
    }
}//class
