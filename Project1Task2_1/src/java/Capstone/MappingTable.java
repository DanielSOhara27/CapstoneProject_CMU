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
     
    
    public boolean InsertMetaData_MappingTable(String createTableParameterSet1){
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
            return true;
       }catch(SQLException e){
            e.printStackTrace();
            stmt = null;
            myJSONobject = null;
            return false;
       } catch (JSONException ex) {
            ex.printStackTrace();
            //Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
            stmt = null;
            myJSONobject = null;
            return false;
        }
        
        
    }//InsertMetaData_MappingTable

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
          
          int numColumns2 = myJSONobject.getInt("numColumn");
          
          query = "CREATE TABLE " + tableName + " ";
          query = query + "( ";
          
          
          
          stmt = manager.getConnection().createStatement();
          
          
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
}//class
