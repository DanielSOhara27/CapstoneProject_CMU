/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.createtable;

import capstone.connection.DBConnectionManager;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author danielohara
 */
public class MappingColumn {
    
    DBConnectionManager manager = DBConnectionManager.getInstance();
    
    HashMap columnMappingMap = initializeColumnMap();
    
    public String createColumnMap_MappingColumns(Map parameterMap, String xmlInput){
       Statement stmt = null;
       StringBuilder query = new StringBuilder(); //container that is going to progressively build the string for sql queries
       
       
       try{
            stmt = manager.getConnection().createStatement();
            
            System.out.println("The value of xmlInput -> " + xmlInput);
                    
                    
            String foo = parseXML(xmlInput, "foo", true, 0); //foo being passed back and forth around
            String myDID = foo.split("\\|")[0]; //Basetable mappingtable id needed to find tablename
            String myUsername = foo.split("\\|")[1]; //The session User
            String myBID = null; //container for the basetable id
            
            
            
            System.out.println("myfoo -> " + foo + "\nmyDID -> "+ myDID + "\nmyUser -> " + myUsername);
            
            int myBNumCol = Integer.parseInt(parseXML(xmlInput, "bNumCol", false, 0)); //the number of base table columns 
            int mydNumCol = Integer.parseInt(parseXML(xmlInput, "nNumCol", false, 0)); //the number of destination table columns
            
            System.out.println("bCol -> " + myBNumCol + "\ndCol -> " + mydNumCol);
            
            String[] bTableArray = new String[myBNumCol]; //container for basetable column names
            /*Inserting basetable names into array so as to create dynamic mappping*/
            for(int x = 0, y=1; x < bTableArray.length; x++, y++){
                bTableArray[x] = parseXML(xmlInput, "bColumn"+y, false, 0);
            }//end of for loop
            
            String mybTablename = null; //container of base table name
            String mydTablename = null; // container of destination table name
            String mydTypeSensor = null; //container of type of association
            
            /*Auxiliary variables needed to parse data*/
            String bAuxString = null;
            String dAuxString = null;
            ResultSet myRes = null;
            int aux = 0;
            boolean second = false;
            
           
            
            /*Getting the name of the destination table from the DB*/
            query = new StringBuilder(); //cleaning the query variable
            query.append("SELECT TableName, SensorType FROM MappingTable WHERE MappingTable_ID = ");
            query.append(myDID);
            query.append(" AND Username = '" + myUsername + "'");
            
            System.out.println("Get Destination table query -> " + query.toString());
            myRes = stmt.executeQuery(query.toString());
            myRes.next();
            mydTablename = myRes.getString("TableName");
            mydTypeSensor = myRes.getString("SensorType");
            System.out.println("dTable -> " + mydTablename + "\nbTypeSensor -> " + mydTypeSensor);
            
            /*Getting the id for the base table*/
            query = new StringBuilder(); //cleaning the query variable
            query.append("SELECT MappingTable_ID FROM MappingTable WHERE ");
            query.append("SensorType = '" + mydTypeSensor + "' ");
            query.append("AND Username ='" + myUsername +"' ");
            System.out.println("Getting id for Base Table query -> " + query.toString());
            
            
            myRes = stmt.executeQuery(query.toString());
            myRes.next();
            myBID = Integer.toString(myRes.getInt("MappingTable_ID"));
            System.out.println("myBID -> " + myBID);
            
            /*Getting the name of the base table from the DB*/
            query = new StringBuilder(); //cleaning the query variable
            query.append("SELECT TableName FROM MappingTable WHERE MappingTable_ID = ");
            query.append(myBID);
            query.append(" AND Username = '" + myUsername + "'");
            
            System.out.println("Getting name of base table query -> " + query.toString());
            myRes = stmt.executeQuery(query.toString());
            myRes.next();
            mybTablename = myRes.getString("TableName");
            System.out.println("Base tablename -> " + mybTablename);

            
            query = new StringBuilder(); //Resetting the string builder for a new query
            
            /*This is the header of the sql insert statement needed to map columns*/
            query.append("INSERT INTO MappingColumn (TypeOfAssociation, Source_DataType, Source_ColumnName, Source_TableName, Dest_DataType, Dest_ColumnName, Dest_TableName) VALUES ");
            
            /*Starting to append the sets of values.*/
            for(int x = 0, y = 1; x < mydNumCol; x++, y++){
                String[] dTableAux = (String[]) parameterMap.get("col"+y+"_mapNumber");
                
                if(dTableAux != null || dTableAux[0].trim().equalsIgnoreCase("-1")){
                
                bAuxString = bTableArray[(Integer.parseInt(dTableAux[0].trim()) - 1)]; //base table column name
                dAuxString = parseXML(xmlInput, "nColumn"+y, false, 0); // destination table column name
               
                
               if(second){
                    query.append(", "); //will only add a comma to second set of values
                   }//checks if there is already a set values before it

                   query.append("( "); //Start of sql value set

                   /*base table part*/ 
                   query.append("'" + myUsername + " " + mydTypeSensor+ "', ");
                   query.append("'string', ");
                   query.append("'" + bAuxString + "' , ");
                   query.append("'" + mybTablename + "' , ");

                   /*destination table part*/
                   query.append("'string', ");
                   query.append("'" + dAuxString + "' , ");
                   query.append("'" + mydTablename + "' ");

                   query.append(")"); //End of sql value set

                   second = true; //This variables starts of as false for the first round and subsecuently becomes true;
                }
            }//for loop to create sets values inserted into the query
            
            System.out.println("the query -> " + query.toString());
            stmt.executeUpdate(query.toString());
               
                
                return "<Success>You have successfully mapped the columns of your new table to the existing basetable for " + mydTypeSensor + "</Success>";
       
       }catch(SQLException e){
           Logger.getLogger(MappingColumn.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return null;
       }catch(Exception e){
           Logger.getLogger(MappingColumn.class.getName()).log(Level.SEVERE, null, e);
           e.printStackTrace();
           stmt = null;
           return null;
       }
    } 
    
    public boolean deleteColumnMapping_MappingColumns(String xmlInput){
      Statement stmt = null;
      StringBuilder query = new StringBuilder(); //container that is going to progressively build the string for sql queries
      ResultSet myRes = null;
      
      String myfoo = parseXML(xmlInput, "foo", true, 0);
      String myID = myfoo.split("\\|")[0];
      String myUser = myfoo.split("\\|")[1];
      String myBaseTable = null;
      String TypeSensor = null;
      String myDestTable = null;
      
      try{
            stmt = manager.getConnection().createStatement();
            
            query.append("SELECT TableName, SensorType FROM MappingTable WHERE ");
            query.append("MappingTable_ID = " + myID + " ");
            query.append("AND Username = '" + myUser + "' ");
            
            myRes = stmt.executeQuery(query.toString());
            myRes.next();
            myDestTable = myRes.getString("TableName");
            TypeSensor = myRes.getString("SensorType");
            
            query = new StringBuilder();
            query.append("SELECT TableName FROM MappingTable WHERE ");
            query.append("SensorType = '" + TypeSensor + "' " );
            query.append("AND Username = '" + myUser + "' ");
            query.append("AND TypeSensor_BaseTable = 'TRUE'");
            
            myRes = stmt.executeQuery(query.toString());
            myRes.next();
            myBaseTable = myRes.getString("TableName");
            
            query = new StringBuilder();
            query.append("DELETE FROM TableName WHERE ");
            query.append("Source_TableName = '" + myBaseTable + "' ");
            query.append("AND Dest_TableName = '" + myDestTable + "' ");
            stmt.executeUpdate(query.toString());
            return true;
            
      }catch(SQLException ex){
          Logger.getLogger(MappingColumn.class.getName()).log(Level.SEVERE, null, ex);
          ex.printStackTrace();
          System.out.println("Error MappingColumn table");
          return false;
      }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return false;
       }
      
    }//deleteColumnMapping
    
    public HashMap<String, String> initializeColumnMap(){
        HashMap<String, String> myMap = new HashMap<String, String>();
        
        myMap.put("sensor","TypeOfAssociation");
        myMap.put("sdt","Source_DataType");
        myMap.put("scn","Source_ColumnName");
        myMap.put("stn","Source_TableName");
        myMap.put("ddt","Dest_DataType");
        myMap.put("dcn","Dest_ColumnName");
        myMap.put("dtn","Dest_TableName");
        
        return myMap;
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
            Logger.getLogger(MappingColumn.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(MappingColumn.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return null;
       }//end of catch
    }//end of method
    
    
}//end of class
