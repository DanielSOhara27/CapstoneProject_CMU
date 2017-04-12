/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.createtable;


import capstone.connection.DBConnectionManager;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 *
 * @author danielohara
 */
public class MappingTable{
    DBConnectionManager manager = DBConnectionManager.getInstance();
    
    HashMap columnMappingMap = initializeColumnMap();
     
    
    public String[] initiliazeMetaData_MappingTable(Map parametersMap, Enumeration<String> parameterNames, String myUsername){
       //System.out.println("the size of ParameterMap inside initialize is " + parametersMap.size());
       Statement stmt = null;
       StringBuilder query = new StringBuilder();
       String[] result = new String[2]; // array containing value of FOO and the xmlInput that next jsp needs
       String[] myParametersAux = new String[parametersMap.size()-1];
       String[] myK = new String[3]; //container for composite keys, there are only 3 keys
       int aux = 0;
       String StringAux = "";
       int size;
       ResultSet myRes = null;
       String baseTableAux = null;
       String baseTableAux2 = null;
       String[] baseTableAux3 = null;
       
       
       try{
           stmt = manager.getConnection().createStatement(); 
           
           /*Need to get list of url parameters that are needed for MetaData without extra fluf*/
           while(parameterNames.hasMoreElements()){
              StringAux = parameterNames.nextElement();
              if( ! StringAux.equalsIgnoreCase("button") ){
                  myParametersAux[aux] = StringAux.trim(); //getting array of urlParameters given by the JSP, excluding buttons and other unnecessary values.
                  aux++;
              }//If the current StringAux is NOT button, then procede
               
           }//end of while parametersNames
           
           query.append("INSERT INTO MappingTable ( ");
           
           size = myParametersAux.length -1;
           
           /*For loop to match the mapping between urlParameters and their DB correspondant value*/
           for(String param : myParametersAux){
               
               StringAux = (String) columnMappingMap.get(param); 
               
               /*
               - This next set of if else statements is used for the last part of the function.
               - In other words, the result of the statements does not affect this for statements output.
               */
               if(StringAux.equalsIgnoreCase("SiteID")){
                   myK[0] = param;
               }else if(StringAux.equalsIgnoreCase("ModelID")){
                   myK[1] = param;
               }else if(StringAux.equalsIgnoreCase("SensorID")){
                   myK[2] = param;
               }
               
               /*This variable is used to check if there is a base table or not in the DB*/
               if(StringAux.equalsIgnoreCase("SensorType")){ 
                   baseTableAux3 = (String[]) parametersMap.get(param);
                   baseTableAux2 = baseTableAux3[0];
               }//This if statement is used to keep track of baseTable
               
               query.append(StringAux); //apending CreateTable-MetaData to query
               
               if(size > 0){
                   query.append(", ");
               }//inner if
               size = size - 1;
           }//for each
           
           query.append(", Username "); //Manually adding user since it is not given in the form
           query.append(", TableName "); //Manually adding tablename since it can be derived from given data.
           query.append(", TypeSensor_BaseTable "); //dynamically checking if base table exists or not
           query.append(", Public ");
           query.append(" )");
           query.append(" SELECT * FROM ( SELECT ");
           
           size = myParametersAux.length -1;
           
           /*For loop to match the value given in the urlparameter with the correct ColumnName*/
           for(String param : myParametersAux){
               StringAux = (String) columnMappingMap.get(param);
               
               aux = getTypeInput(StringAux);
               //System.out.println("Inside Second IF statement printing parameterMap Value ->" + ((String[]) parametersMap.get(param))[0]);
               
               
               /*
               The next set of if-else statements are used to generate syntatically 
               correct sql statements depending on the type of data and value being used.
               */
               if(aux == 1){//its a boolean
               query.append(((String[])parametersMap.get(param))[0]);
               
               }else if(aux == 2){//its a number
               query.append(((String[])parametersMap.get(param))[0]);
               
               }else if(aux == 3){//This parameter is the Delimiter and needs to escape the escape symbol.
               query.append("'\\");
               query.append(((String[])parametersMap.get(param))[0]);
               query.append("'");
               
               }else{ //its a string and not a Delimiter
               query.append("'");
               query.append(((String[])parametersMap.get(param))[0]);
               query.append("'");
               
               }//end of if else statement
               
               
               
               query.append(" AS " + param +"_ALIAS"); //All columns are using an alias because we are simulating a temporary table.
               
               if(size > 0){
                   query.append(", ");
               }//inner if
               size = size - 1;
           }//second for each, now to insert values in sql statement
           query.append(", '" + myUsername + "' AS Username_ALIAS ");
           query.append(", '" + myUsername + "_" + ((String[]) parametersMap.get(myK[0]))[0] + "_" + ((String[]) parametersMap.get(myK[1]))[0] + "_" + ((String[]) parametersMap.get(myK[2]))[0] + "_DataTable' AS Tablename_ALIAS");
           
           
           baseTableAux = isBaseTableSet(baseTableAux2, myUsername);
           if(baseTableAux == null){
               System.out.println("There was an error while checking for base table with sensor = " + baseTableAux2 + " AND owner = " + myUsername);
               //query.append(", 'TRUE' AS BASETABLE_ALIAS")
           }//there was an error
           else if(baseTableAux.equalsIgnoreCase("TRUE")){
               //There is a base table already
                query.append(", 'TRUE' AS BASETABLE_ALIAS");
           }else{
               //There is no base table with the given parameters
               query.append(", 'FALSE' AS BASETABLE_ALIAS");
           }//end of if else statements to check for basetables
           
           query.append(", 'TRUE' AS Public_ALIAS");
           query.append(" ) AS TMP "); //This temporary tablename is used to check that there is already a table that exists with the same UNIQUE-KEY_SIGNITURES.

           /*This next part avoids generating an error where the potential CUSTOM-GENERATED-USER-TABLE might already exist.*/
           query.append("WHERE NOT EXISTS ( ");
           query.append("SELECT SiteID, ModelID, SensorID, Username FROM MappingTable WHERE ");
           query.append("SiteID = '" + ((String[]) parametersMap.get(myK[0]))[0] + "' ");
           query.append("AND ModelID = '" + ((String[]) parametersMap.get(myK[1]))[0] + "' ");
           query.append("AND SensorID = '" + ((String[]) parametersMap.get(myK[2]))[0] + "' ");
           query.append("AND Username = '" + myUsername + "' "); 
           query.append(") LIMIT 1");
           System.out.println(query.toString());
         
           
           //Inserting into the database here
           stmt.executeUpdate(query.toString());
           
           /*Now preraring this method's output for return statement - we will only get here if previous statement was a success*/
           query = new StringBuilder();
           
           query.append("SELECT MappingTable_ID, Username, ROW_TO_SKIP, DELIMITER FROM MappingTable WHERE ");

           query.append("SiteID = '" + ((String[]) parametersMap.get(myK[0]))[0] + "' ");

           query.append("AND ModelID = '" + ((String[]) parametersMap.get(myK[1]))[0] + "' ");
           
           query.append("AND SensorID = '" + ((String[]) parametersMap.get(myK[2]))[0] + "' ");
                      
           query.append("AND Username = '" + myUsername + "' ");
           

           
           myRes = stmt.executeQuery(query.toString());
           myRes.next();
           
           result[0] = myRes.getInt("MappingTable_ID") + "|" + myRes.getString("Username");
           result[1] = "<rows>" + myRes.getInt("Row_To_Skip") + "</rows> <delimiter>" + myRes.getString("Delimiter") + "</delimiter>";
           
           
           
       }catch(SQLException e){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, e);
           e.printStackTrace();
           stmt = null;
           query = null;
           return null;
       }//end of SQLException
       catch(NegativeArraySizeException e){
            Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, e);
           e.printStackTrace();
           stmt = null;
           query = null;
           return null;
       }catch(Exception e){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, e);
           e.printStackTrace();
           stmt = null;
           query = null;
           return null;
       }
       
       return result; //returning the correct String[] to createTableForm servlet.
    }//end of method initiliazeMetaData_MappingTable
    
    public HashMap<String,String> initiliazeColumnData_MappingTable(Map parametersMap, String urlXML){
       Statement stmt = null;
       StringBuilder query = new StringBuilder();
       String foo = parseXML(urlXML, "foo", true, -1);
       String myID = foo.split("\\|")[0];
       String myUser = foo.split("\\|")[1];
       String[] auxMap2;
       int auxCounter;
       String auxString, aux2, aux3;
       HashMap<String, String> auxMap = new HashMap<String,String>();
       boolean second = false;
       
       auxCounter = (parametersMap.size()-1) % Integer.parseInt(parseXML(urlXML, "NumCol", false, 0));
       
       if(auxCounter != 0 ){
           return null;
       }
       
       try{
           stmt = manager.getConnection().createStatement();
           query.append("UPDATE MappingTable SET ");
           query.append(columnMappingMap.get("columnName"));
           query.append(" = '");
           
           auxCounter = Integer.parseInt(parseXML(urlXML, "NumCol", false, 0));
           
           for(int x = 0, y = 1; x<auxCounter; x++, y++){
                if(second){
                   query.append(","); 
                   /*
                   we are separating our structures by comma on top level and pipes on second level
                   For Example: Index|ColName|DataType|Format|Alias,Index2|ColName2|DataType2|Format2|Alias ....
                   */
               }//if
                
               /*Getting the Index and Original Column Name*/
               auxString = "Column"+y;
               auxString = parseXML(urlXML, auxString, false, 0); //Value of ColumnName
               auxString.trim();
               
               query.append(x); //inded
               query.append("|");
               query.append(auxString); //name of column
               query.append("|");
               aux2 = auxString; 
               
               /*Getting Datatype and Format (if it applies)*/
               //System.out.println("GETTING DATATYPE -> col"+y+"_dataType");
               //System.out.println(parametersMap.get("col"+y+"_dataType").toString());
               auxMap2 = (String[]) parametersMap.get("col"+y+"_dataType"); //Value of Datatype
               auxString = auxMap2[0];
               //System.out.println("Getting value of dataType value -> " + auxString);
               
               
               
               /*Getting the format if it exists*/
               if(auxString.toUpperCase().contains("CUSTOM")){
                   //auxString = auxString.substring(auxString.indexOf("[")+1, auxString.indexOf("]"));
                   //auxString.trim();
                   //String[] aux = auxString.split("_");
                   
                    if(auxString.trim().toUpperCase().equalsIgnoreCase("CUSTOM-DATETIME")){
                    aux3 = "datetime";
                   }
                   else if(auxString.trim().toUpperCase().equalsIgnoreCase("CUSTOM-DATE")){
                    aux3 = "date";
                   }
                   else if(auxString.trim().toUpperCase().equalsIgnoreCase("CUSTOM-TIME")){
                    aux3 = "time";
                   }
                   else{
                    aux3 = "datetime";
                   }
                   //aux3 = parseDataType(auxString.toLowerCase());
                   auxMap2 = (String []) parametersMap.get("col"+y+"datetime");
                   auxString = auxMap2[0];
                   
                           
                   query.append(aux3.toUpperCase()); //DataType
                   query.append("|");
                   query.append(auxString.trim()); //Format
                   query.append("|");
                   auxMap.put(aux2, aux3);//Keeping track of column names and datatypes
               }else if(auxString.trim().toUpperCase().contains("UNIX")){
                   auxString = parseDataType(auxString.toLowerCase());
                   
                   auxMap.put(aux2,auxString);
                  // query.append(auxString);
                  query.append("datetime");
                   query.append("|");
                   query.append("UnixTime");
                   query.append("|");
               }else{   
                   auxString = parseDataType(auxString.toLowerCase());
                   auxMap.put(aux2, auxString);//Keeping track of column names and datatypes
                   query.append(auxString); //datatype
                   query.append("|");
                   query.append(" "); //There is no Format, so empty space was used instead.
                   query.append("|");
               }//outer else checking if datatype is date or other
               
               
               /*Getting the Alias of Column (if it applies)*/
               auxString = "col"+y+"_alias";
               auxMap2 = (String []) parametersMap.get(auxString); //Value of alias checkbox
               auxString = auxMap2[0];
               //System.out.println("Getting the Alias of Column if it applies with parameter -> " + auxString + " $$$$");
               if(Boolean.valueOf(auxString.toUpperCase())){
                  
                  auxMap2 = (String[]) parametersMap.get("col"+y+"aliasCheck"); //Getting the alias string
                  auxString = auxMap2[0];
                  auxString.trim();
                  
                  query.append(auxString); //Last section, no more pipes needed.
                  
                  //System.out.println("Inside success if with value of alias -> "+ auxString +" $$$$");
               }else{
                  query.append(" "); //No alias was given, therefore empty space was provided
                  //System.out.println("Inside fail else statement where no alias was given");
               }//End of if - else statement to check for alias
               
               //System.out.println("After alias if else sections");
               
           second = true;
           }//end of for loop that counts each column
           
           query.append("' ");
           
           /*Adding Where clause to query*/
           query.append("WHERE MappingTable_ID = ");
           query.append(myID);
           query.append(" AND Username = ");
           query.append("'"+myUser+"'");
           
           stmt.executeUpdate(query.toString());
           
           //System.out.println("The Query -> " + query.toString());

           
           return auxMap;
       } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return null;
       }
        
    }
    
    public ResultSet DescribeTables (String Metadata_Tablename){
       Statement stmt;
       String query;
       try{
          stmt = manager.getConnection().createStatement();
          
          query = "Describe "+ Metadata_Tablename;
          
          ResultSet res = stmt.executeQuery(query);
          return res;

       }catch(SQLException e){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return null;
       }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return null;
       }
    }

    public String[] CreateUserTable(HashMap<String, String> columnMap, String tableName){
       Statement stmt = null;
       StringBuilder query = new StringBuilder();
       String[] myK = columnMap.keySet().toArray(new String[columnMap.size()]);
       
       try {
            stmt = manager.getConnection().createStatement();

            /*Creating User Table Statement*/
            query.append("CREATE TABLE `" + tableName + "` ");
            query.append("( ");
            query.append(" `userTable_ID` INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,");

            /*Getting the names of the columns -These will be the original only, the ALIAS will be used during output*/

            for(int x = 0; x < myK.length; x++){
                query.append("`" + myK[x].trim() + "`"); //appending the name of the column
                query.append(" " + columnMap.get(myK[x]) + ", "); //appending the datatype of the column
                
            }//for loop to append the names of the columns that were parsed from the sample file

            /*Appending the columns that are used for internal purposes*/
            query.append("`isItFlagged` BOOLEAN, ");
            query.append("`ConvertedTimeStamp` DATETIME, ");
            query.append("`Year` INT(1), ");
            query.append("`yDay` INT(2), ");
            query.append("`Month` INT(2), ");
            query.append("`mDay` INT(2), ");
            query.append("`Hour` INT(2), ");
            query.append("`Minute` INT(2), ");
            query.append("`Second` INT(2), ");
            query.append(" `Source_Filename` VARCHAR(1000)");

            query.append(")");
            
            /*executing Statmenet*/
            stmt.executeUpdate(query.toString());


            
            return myK;
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            stmt = null;
            System.out.println("the Query -> " + query.toString());
            Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return null;
       }
       
    }
    
    public String tablename_MappingTable(String foo){
       Statement stmt;
       String query;
       String myFoo = parseXML(foo, "foo", true, 0);
       String myID = myFoo.split("\\|")[0];
       String myUsername = myFoo.split("\\|")[1];
       try{
         
          stmt = manager.getConnection().createStatement();
          
          query = "SELECT TableName FROM MappingTable WHERE MappingTable_ID =  "+ myID + " AND Username = '" + myUsername + "'";
         // System.out.println("Inside mappingtable tablename query -> " + query);
          
          ResultSet res = stmt.executeQuery(query);
          res.next();
          return res.getString("TableName");

       }catch(SQLException e){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return null;
       }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
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
        catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return null;
       }
        /*If al goes well, then this is what is being returned on Success*/
        myTable = myTable + "</table><br /><br />";
        return myTable;
    }//printCreateTable

    String parseDataType(String dataType){
        try{
            String res = "";
            switch(dataType.toLowerCase()){
                case "int":
                    res = "INT(3)";
                    break;
                case "string":
                    res = "VARCHAR(1000)";
                    break;
                case "date":
                    //res = "DATE";
                    res = "VARCHAR(1000)";
                    break;
                case "datetime":
                    //res = "DATETIME";
                    res = "VARCHAR(1000)";
                    break;
                case "time":
                    //res = "TIME";
                    res = "VARCHAR(1000)";
                    break;
                case "timestamp":
                    //res = "TIMESTAMP";
                    res = "VARCHAR(1000)";
                    break;
                case "unix":
                    //res = "DATETIME";
                    res = "VARCHAR(1000)";
                    break;

                case "boolean":
                case "bool":
                    res = "BOOLEAN";
                    break;
                case "double":
                    res = "DOUBLE";
                    break;
                case "custom":
                    res = "String";
                    break;
                default:
                    res = "VARCHAR(1000)";
                    break;
            }//end of switch
            return res;
            
        }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return null;
       }
        
    }

    public String getUserTableName(String mySiteID, String myModelID, String mySensorID){
       Statement stmt;
       String query;
       ResultSet res;
       String result;
       
       //System.out.println("Inside getUserTableName");
       
       try{
           stmt = manager.getConnection().createStatement();
           //System.out.println("After stmt connection");
           
           query = "SELECT TableName FROM MappingTable WHERE"
                   + " SiteID ='" + mySiteID +"'"
                   + " AND ModelID ='" + myModelID +"'"
                   + " AND SensorID ='" + mySensorID +"'";
           
           //System.out.println("Before executeQuery");
           //System.out.println(query);
           
           res = stmt.executeQuery(query);
           
           //System.out.println("After execute Query");
           
           res.next();
           result = res.getString("TableName");
           
       }catch(SQLException e){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return null;
       }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
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
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, e);
           e.printStackTrace();
           stmt = null;
           query = "";
           return null;
       }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return null;
       }
    }

    public ResultSet test_ResultSet(){
       Statement stmt;
       String query;
       try{
          System.out.println("Inside test_ResultSet");
          stmt = manager.getConnection().createStatement();
          
          System.out.println("Preparing Query");
          query = "SELECT * FROM MappingTable";
          
          System.out.println("Before executeQuery");
          ResultSet res = stmt.executeQuery(query);
          
          System.out.println("Before return res");
          
          return res;

       }catch(SQLException e){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return null;
       }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return null;
       }
    }

    private HashMap<String, String> initializeColumnMap(){
        HashMap localMap = new HashMap<String, String>();
        localMap.put("sitePrefix", "SiteID");
        localMap.put("manufacturer", "ModelID");
        localMap.put("sensorModel", "SensorID");
        localMap.put("username", "Username");
        localMap.put("siteName", "SiteName"); //Not found in JSP
        localMap.put("typeSensor", "SensorType");
        localMap.put("location", "Location");
        localMap.put("numColumn", "NumColumn");
        localMap.put("typeDelimeter", "Delimiter");
        localMap.put("commentSignifier", "Comment_Delimiter");
        localMap.put("rangeBTWreadings", "RangeBetweenReadings"); //Not found in JSP - UPDATED JSP
        localMap.put("acceptedRange", "AcceptableRange"); //Not found in JSP - DELETE
        localMap.put("sensorBase", "TypeSensor_BaseTable"); //Only first table will have this true
        localMap.put("siteBase", "Site_BaseTable"); //Not found in JSP - DELETE
        localMap.put("Public", "Public");
        localMap.put("table", "TableName"); //Created dinamically after
        localMap.put("columnName", "ColumnNames");
        localMap.put("numHeader", "Row_To_Skip");
        
        return localMap;
    }

    public int getTypeInput(String columnName){
        try{
            switch(columnName){
                case "TypeSensor_BaseTable":
                case "Site_BaseTable":
                case "Public":
                    return 1;

                case "Row_To_Skip":
                case "NumColumn":
                    return 2;

                case "Delimiter":
                    return 3;

                default:
                    return 4;
            }//end of switch
        }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return -1;
       }
    }

    public String parseXML(String xmlString, String nameOfTag, boolean foo, int index){
        DOMParser parser = new DOMParser();
        try {
            //System.out.println("xmlString: ->" + xmlString + "\nnameOfTag ->" +nameOfTag + "\nfoo ->" + foo + "\nindex ->" + index);
            parser.parse(new InputSource(new java.io.StringReader(xmlString)));
            Document doc = parser.getDocument();
            String message;
            if(foo){
            message = doc.getElementsByTagName(nameOfTag).item(0).getTextContent();
            //System.out.println("Inside parseXML with value = " + doc.getElementsByTagName(nameOfTag).item(0).toString());
            }else{
            message = doc.getElementsByTagName(nameOfTag).item(index).getTextContent();
            //System.out.println("Inside parseXML with value = " + doc.getElementsByTagName(nameOfTag).item(index).toString());
            }
            
            return message;
        } catch (SAXException ex) {
            ex.printStackTrace();
            Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return null;
       }
    }
    
    public String isBaseTableSet(String sensor, String owner){
       //System.out.println("Inside isBaseTableSet with sensor -> " + sensor + " and owner -> " + owner);
       Statement stmt = null;
       StringBuilder query = new StringBuilder();
       
       try{
           stmt = manager.getConnection().createStatement();
           ResultSet myRes = null;
           String answer = null;
           
           query.append("SELECT COUNT(SensorType) AS RES FROM MappingTable WHERE ");
           query.append("Username = '"+owner+"' ");
           query.append("AND SensorType = '" + sensor + "' ");
           //query.append("AND TypeSensor_BaseTable = 'TRUE'");
           
           //System.out.println("Inside isBaseTableSet with query -> " + query.toString());
           myRes = stmt.executeQuery(query.toString());
           
           myRes.next();
           
           answer = myRes.getString("RES");
           //System.out.println("sql answer -> " + answer + " and equalsIgnorecase(0) -> " + answer.equalsIgnoreCase("0") + " $$$");
           if(answer.equalsIgnoreCase("0")){
               return "TRUE";
           }else{
               return "FALSE";
           }
           
           
       }catch(SQLException ex){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
           ex.printStackTrace();
           System.out.println("SQL exception while checking for basetable");
           stmt = null;
           return null;
       }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return null;
       }
       
    }//end of checkBaseTable
    
    public String isBaseTableSet2(String sensor, String xmlInput){
       //System.out.println("Inside isBaseTableSet2 with sensor -> " + sensor + " and input -> " + xmlInput);
       Statement stmt = null;
       StringBuilder query = new StringBuilder();
       String myfoo = parseXML(xmlInput, "foo", true, -1);
       String myID = myfoo.split("\\|")[0];
       String myUser = myfoo.split("\\|")[1];
       
       try{
           stmt = manager.getConnection().createStatement();
           ResultSet myRes = null;
           String answer = null;
           int id = -1;
           
           query.append("SELECT COUNT(SensorType) AS RES, MappingTable_ID FROM MappingTable WHERE ");
           query.append("Username = '"+myUser+"' ");
           query.append("AND SensorType = '" + sensor + "' ");
           query.append("AND TypeSensor_BaseTable = 'TRUE'");
           
           //System.out.println("Inside isBaseTableSet2 with query -> " + query.toString());
           myRes = stmt.executeQuery(query.toString());
           
           myRes.next();
           
           id = myRes.getInt("MappingTable_ID");
           
           if(id != Integer.parseInt(myID)){
           //System.out.println("isBaseTableSet2 with id -> " + id + " AND myID = " + myID);
           answer = myRes.getString("RES");
           //System.out.println("sql answer -> " + answer + " and answer.equalsIgnoreCase(1) -> " + answer.equalsIgnoreCase("1") + " $$$");
                 if(answer.equalsIgnoreCase("1")){
                     return "TRUE";
                 }else{
                     return "FALSE";
                 }//inner else checking for RES
           }else{
               return "FALSE";
           }//outer else checking if mappingID is the same as the current table
           
           
       }catch(SQLException ex){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
           ex.printStackTrace();
           System.out.println("SQL exception while checking for basetable");
           return null;
       }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return null;
       }
       
    }//end of checkBaseTable
    
    public String getBaseTableFlag(String xmlInput){
       //System.out.println("Inside getBaseTableFlag with xmlInput ->" + xmlInput);
        
       Statement stmt = null;
       StringBuilder query = new StringBuilder();
       String myfoo = parseXML(xmlInput, "foo", true, 0); //getting foo tag from xmlinput
       String myID = myfoo.split("\\|")[0];
       String myUser = myfoo.split("\\|")[1]; //getting username 
       
       try{
           
           stmt = manager.getConnection().createStatement();
           ResultSet myRes = null;
           String answer = null;
           
           query.append("SELECT SensorType FROM MappingTable WHERE ");
           query.append("Username = '"+myUser+"' ");
           query.append("AND MappingTable_ID = " + myID + " ");
           //query.append("AND TypeSensor_BaseTable = 'TRUE'");
           
           //System.out.println("getBaseTable query -> " + query.toString());
           myRes = stmt.executeQuery(query.toString());
           
           myRes.next();
           
           answer = myRes.getString("SensorType");
           
           return answer;
           
           
       }catch(SQLException ex){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
           ex.printStackTrace();
           return null;
       }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return null;
       }
       
    }//end of checkBaseTable
    
    public String[] getBaseTableInfo(String xmlInput){
       //System.out.println("Inside getBaseTableInfo");
       Statement stmt = null;
       StringBuilder query = new StringBuilder();
       String myfoo = parseXML(xmlInput, "foo", true, 0); //getting foo tag from xmlinput
       String myID = myfoo.split("\\|")[0]; //getting current table id
       String myUser = myfoo.split("\\|")[1]; //getting username 
       
       //System.out.println("myfoo -> " + myfoo + "\nmyID -> " + myID + "\nmyUser -> " + myUser);
       
       String mySensorType = null;
       int mysize = 0; //container for number of columns
       ResultSet myRes = null;
       String auxString = null; //container to parse columnnames
       String[] auxStringArray = null; //Array container for split string
       String[] auxStringArray2 = null; //another array container
        
       try{
           
           stmt = manager.getConnection().createStatement();
           
           //getting the SensorType for current table
           query.append("SELECT SensorType from MappingTable WHERE ");
           query.append("Username = '" + myUser + "' ");
           query.append("AND MappingTable_ID = '" + myID + "' ");
           query.append("AND TypeSensor_BaseTable ='FALSE'");
           
           myRes = stmt.executeQuery(query.toString());
           //System.out.println("get SensorType query -> " + query.toString());
           
           myRes.next();
           mySensorType = myRes.getString("SensorType");
           //System.out.println("get SensorType res -> " + mySensorType);
           
           //Getting the Base Table column info
           query = new StringBuilder();
           query.append("SELECT ColumnNames FROM MappingTable where ");
           query.append("Username = '" + myUser + "' ");
           query.append("AND SensorType = '" + mySensorType + "' ");
           query.append("AND TypeSensor_BaseTable ='TRUE'");
           
           //System.out.println("Get Base table info query -> " + query.toString());
           myRes = stmt.executeQuery(query.toString());
           myRes.next();
           auxString = myRes.getString("ColumnNames");
           //System.out.println("Get Base table infor res -> " + auxString);
           auxStringArray = auxString.split(",");
           mysize = auxStringArray.length;
           
           auxStringArray2 = new String[mysize];
           
           /*creating parsed param array that is going to be returned*/
           for(int x = 0; x < auxStringArray.length; x++){
               //first, splitting the auxStringArray at x, 
               //then getting second Position (its the name of the original column),
               //finally we are getting rid of empty spaces
               auxStringArray2[x] = auxStringArray[x].split("\\|")[1].trim(); 
               //System.out.println("parsing auxStringArray["+x+"] -> " + auxStringArray[x] + "\nlength -> " + auxStringArray[x].split("\\|").length);
           }//for loop creating parsing columnNames
           
           return auxStringArray2;
           
           
           
       }catch(SQLException ex){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
           ex.printStackTrace();
           stmt = null;
           System.out.println("Error getting basetable info");
           return null;
       }//end of try catch statement
       catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return null;
       }
        
    }//end of getBaseTableInfo
    
    boolean deleteUserTable(String xmlInput){
       System.out.println("Inside MappingTable's Delete User table with xmlInput -> " + xmlInput);
       boolean empty = false;
       Statement stmt = null;
       StringBuilder query = new StringBuilder();
       String myfoo = parseXML(xmlInput, "foo", true, 0); //getting foo tag from xmlinput
       String myID = myfoo.split("\\|")[0]; //getting current table id
       String myUser = myfoo.split("\\|")[1]; //getting username
       String tablename = null;
       ResultSet myRes = null;
       String aux = null;
       
       
       try{
           
           stmt = manager.getConnection().createStatement();
           
           query.append("SELECT TableName FROM MappingTable WHERE ");
           query.append("MappingTable_ID = " + myID + " ");
           query.append("AND Username = '" + myUser + "' ");
           System.out.println("The query for getting tablename in Delete User table \n query ->" + query.toString());
           myRes = stmt.executeQuery(query.toString());
           myRes.next();
           tablename = myRes.getString("TableName");
           
           query = new StringBuilder();
           
           query.append("DROP TABLE IF EXISTS `" + tablename + "`");
           System.out.println("The query for dropping table -> " + query.toString());
           stmt.executeUpdate(query.toString());
           return empty;
           
       }catch(SQLException ex){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
           ex.printStackTrace();
           System.out.println("There was an error checking for user table");
           return false;
       }//try - catch
       catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return false;
       }
    }//deleteUserTable method
    
    boolean deleteTablaMetaData(String xmlInput){
       System.out.println("Inside MappingTable's MetaData for xmlinput: ");
       System.out.println(xmlInput);
        
       Statement stmt = null;
       StringBuilder query = new StringBuilder();
       String myfoo = parseXML(xmlInput, "foo", true, 0); //getting foo tag from xmlinput
       String myID = myfoo.split("\\|")[0]; //getting current table id
       String myUser = myfoo.split("\\|")[1]; //getting username
       
       
       try{
           
           stmt = manager.getConnection().createStatement();
           
           
           query.append("DELETE FROM MappingTable WHERE ");
           query.append("MappingTable_ID = " + myID + " ");
           query.append("AND Username = '" + myUser + "' ");
           System.out.println("The Query for delete mappingtable -> " + query.toString());
           stmt.executeUpdate(query.toString());
           
           return true;
           
       }catch(SQLException ex){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
           ex.printStackTrace();
           System.out.println("There was an error Deleting the user table");
           return false;
       }//try - catch
       catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return false;
       }
    }//deleteTableMetaData
    
    boolean isStudentActiveCheck(String name){
        boolean answer = false;
        Statement stmt = null;
        StringBuilder query = new StringBuilder();
        ResultSet myRes = null;
        int active = -1;
        
       try{
           
           stmt = manager.getConnection().createStatement();
           
           query.append("SELECT active FROM permissions where uname = '" + name + "'");
           
           myRes = stmt.executeQuery(query.toString());
           myRes.next();
           active = myRes.getInt("active");
           
           if(active == -1 || active > 1){
               answer = false;
               throw new Exception("The student account information is corrupt, please check with your DBA");
           
           }else{
               answer = true;
           }
           
           
           return answer;
       }catch(SQLException ex){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
           ex.printStackTrace();
           System.out.println("There was an error checking the student's account in the database");
           return false;
       }//try - catch
       catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("There was an error checking the student's account in the database");
            e.printStackTrace();
            stmt = null;
            return false;
       }//end of catch
    }//end of method
    
    String getStudentAdvisor(String name){
        boolean answer = false;
        Statement stmt = null;
        StringBuilder query = new StringBuilder();
        ResultSet myRes = null;
        int active = -1;
        String aux = null;
        
       try{
           
           stmt = manager.getConnection().createStatement();
           
           if(isStudentActiveCheck(name)){
               query.append("SELECT faculty_name FROM permissions WHERE uname = '" + name + "'");
               myRes = stmt.executeQuery(query.toString());
               myRes.next();
               aux = myRes.getString("faculty_name");
               query = new StringBuilder();
               myRes = null;
               
               query.append("SELECT uname FROM users WHERE uname = '" + aux + "'");
               myRes = stmt.executeQuery(query.toString());
               myRes.next();
               
               aux = myRes.getString("uname");
               
               if(isFaculty(aux)){
                   //If the resulting username is for a faculty
                   return aux;
               }else{
                  System.out.println("Severe Error: That faculty does not exist");
                  throw new Exception("Severe Error: That faculty does not exist"); 
               }
               
           }//end of if statement
           else{
               System.out.println("Severe Error: That faculty does not exist");
               throw new Exception("Severe Error: That faculty does not exist");
           }
           
       }catch(SQLException ex){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
           ex.printStackTrace();
           System.out.println("Severe Error: That faculty does not exist");
           return null;
       }//try - catch
       catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            System.out.println("Severe Error: That faculty does not exist");
            return null;
       }//end of catch
    }//end of method
    
    boolean isFaculty(String name){
        boolean answer = true;
        Statement stmt = null;
        StringBuilder query = new StringBuilder();
        ResultSet myRes = null;
        int faculty = -1;
        
       try{
           
           stmt = manager.getConnection().createStatement();
           
           query.append("SELECT faculty FROM users where uname = '" + name + "'");
           
           myRes = stmt.executeQuery(query.toString());
           myRes.next();
           faculty = myRes.getInt("faculty");
           
           if(faculty == -1 || faculty > 1){
               answer = true;
               throw new Exception("The student account information is corrupt, please check with your DBA");
           
           }else if(faculty == 1){
               answer = true;
           }else{
               answer = false;
           }//if else statement
           
           
           return answer;
           
           
       }catch(SQLException ex){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
           ex.printStackTrace();
           System.out.println("There was an error Deleting the user table");
           return true;
       }//try - catch
       catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return true;
       }//end of catch
    }//end of method
    
    boolean isStudent(String name){
        boolean answer = false;
        Statement stmt = null;
        StringBuilder query = new StringBuilder();
        ResultSet myRes = null;
        int active = -1;
        
       try{
           
           stmt = manager.getConnection().createStatement();
           
           query.append("SELECT active FROM permissions where uname = '" + name + "'");
           
           myRes = stmt.executeQuery(query.toString());
           myRes.next();
           active = myRes.getInt("active");
           
           if(active == -1 || active > 1){
               answer = false;
               throw new Exception("The student account information is corrupt, please check with your DBA");
           
           }else{
               answer = true;
           }
           
           
           return answer;
       }catch(SQLException ex){
           Logger.getLogger(MappingTable.class.getName()).log(Level.SEVERE, null, ex);
           ex.printStackTrace();
           System.out.println("There was an error checking the student's account in the database");
           return false;
       }//try - catch
       catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("There was an error checking the student's account in the database");
            e.printStackTrace();
            stmt = null;
            return false;
       }//end of catch
    }//end of method
}//class
