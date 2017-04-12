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
public class MappingETL {
    
    DBConnectionManager manager = DBConnectionManager.getInstance();
    
    HashMap columnMappingMap = initializeColumnMap();
    
    public String[] createEtlRule_MappingEtlRules(Map parameterMap, String xmlInput, String flag){
       Statement stmt = null;
       StringBuilder query = new StringBuilder();
       
      // System.out.println("paramterMap size :" + parameterMap.size() + "\n xmlInput : " + xmlInput + "\n flag : " + flag);
       
       
       try{
            stmt = manager.getConnection().createStatement();
            
            String foo = parseXML(xmlInput, "foo", true, 0); //foo being passed back and forth around
            String myID = foo.split("\\|")[0]; //mappingtable id
            String myUsername = foo.split("\\|")[1]; //the session username
            int myNumCol = Integer.parseInt(parseXML(xmlInput, "NumCol", false, 0)); //the number of columns for dynamic form
            String auxString = null;
            String myTablename = null;
            ResultSet myRes = null;
            String[] params = new String[3];
            String[] auxS = new String[1];
            int aux = 0;
            boolean second = false;
            
            /*Getting the name of the table from the DB*/
            query.append("SELECT TableName FROM MappingTable WHERE MappingTable_ID = ");
            query.append(myID);
            query.append(" AND Username = '" + myUsername + "'");
            
            //System.out.println("The id Query -> " + query.toString());
            myRes = stmt.executeQuery(query.toString());
            myRes.next();
            myTablename = myRes.getString("TableName");
            
            

            
            query = new StringBuilder(); //Resetting the string builder for a new query
            
            /*This is the header of the sql insert statement needed to map custom columns*/
            query.append("INSERT INTO Mapping_ETL_Rules (Source_TableName, Source_ColumnName, Rule_Type, Rule, FlagType) VALUES ");
            
            /*Starting to append the sets of values.*/
            for(int x = 0, y = 1; x < myNumCol; x++, y++){
                auxString = parseXML(xmlInput, "Column"+y, false, 0);
                
                //System.out.println("the _missing parameter -> " + parameterMap.get("col" + y + "_missing"));
               // System.out.println("the .equals -> " + parameterMap.get("col" + y + "_missing").)
                
                /*Creating the ELT rule that checks for missing values*/
                auxS = (String []) parameterMap.get("col" + y + "_missing");
                if(Boolean.valueOf((auxS[0]))){
                    if(second){
                        query.append(", ");
                    }
                    query.append("( '" + myTablename + "', ");
                    query.append("'" + auxString.trim() + "', ");
                    query.append("'Missing', ");
                    query.append("'Missing Value', ");
                    query.append("'Exclude'");
                    query.append(")");
                    
                    second = true;
                    
                }//first if inside the for loop -> This one is for excludes (AKA missing values)
                
                
                /*Creating the ETL for ranges by checking if they wanted a range*/
                auxS = (String []) parameterMap.get("col" + y + "_range");
                if(Boolean.valueOf((auxS[0]))){
                    auxS = (String[]) parameterMap.get("col"+y+"_lowerThan");
                        params[0] = auxS[0].trim();
                        //Integer.parseInt(auxS[0]); //This is a redundant function implemented to make sure that the value is numeric
                    auxS = (String[]) parameterMap.get("col"+y+"_andOr");
                        params[1] = auxS[0].trim();
                    auxS = (String[]) parameterMap.get("col"+y+"_greaterThan");
                        params[2] = auxS[0].trim();
                        //Integer.parseInt(auxS[0]); //This is a redundant function implemented to make sure that the value is numeric
                    if(second){
                        query.append(", ");
                    }
                    query.append("( '" + myTablename + "', ");
                    query.append("'" + auxString.trim() + "', ");
                    query.append("'Range', ");
                    query.append("'" + params[0] + ":" + params[1].toUpperCase() + ":" + params[2] + "', ");
                    if(flag.equalsIgnoreCase("redFlag")){query.append("'Exclude' ");}
                    else if(flag.equalsIgnoreCase("yellowFlag")){query.append("'Flag' ");}
                    else{query.append("'Flag' ");}
                    query.append(")");
                    
                    second = true;
                    
                    
                }//second if inside the for loop -> This one is for ranges that lead to redFlags.
                
            }//for loop to create sets values inserted into the query
            
           // System.out.println("the query Master -> " + query.toString());
            stmt.executeUpdate(query.toString());
                
                /*Preparing method output to be returned*/
                params = new String[myNumCol];
                for(int x = 0, y = 1; x < myNumCol; x++,y++){
                    auxString = parseXML(xmlInput, "Column"+y, false, 0);
                    params[x] = auxString.trim();
                    System.out.println("Inside " + flag + "  params["+x+"] -> " + params[x] + " $$$");
                }
                
                return params;
       
       }catch(SQLException e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
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
    
    public boolean checkEmptyForm_MappingEtlRules(Map parameterMap, String xmlInput, String flag){
       try{
        //System.out.println("Inside check empty with xmlInput -> " + xmlInput);
        boolean empty = true;
        String myfoo = parseXML(xmlInput, "foo", true, 0);
        String numcol = parseXML(xmlInput, "NumCol", false, 0);
       // System.out.println("myfoo -> " + myfoo + "\nnumcol -> " + numcol);
        int num = Integer.parseInt(numcol);
        String auxString;
        String auxString2;
        String[] auxStringArray;

        //System.out.println("Going to check empty in for loop");
        for(int x=0, y=1; x < num; x++, y++){
            auxStringArray = (String[]) parameterMap.get("col"+ y + "_missing");
            auxString = auxStringArray[0];
            auxStringArray = (String[]) parameterMap.get("col" + y + "_range");
            auxString2 = auxStringArray[0];
            //System.out.println("auxString -> " + auxString + " auxString2 -> " + auxString2);
            if(auxString.toLowerCase().equalsIgnoreCase("true") || auxString2.toLowerCase().equalsIgnoreCase("true")){
                empty = false;
                break;
            }//if there is atleast one example where either missing or range is true
        }//for loop to look for atleast 1 filled in spot

        //try{
        //}
        //System.out.println("Checking Empty with empty -> " + empty);
        return empty;
       }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
       }
    }
    
    public boolean deleteEtl_MappingEtlRules(String xmlInput, String flag){
       //System.out.println("Inside MappingETL DeleteETL with FLAG -> " + flag + "\nxmlInput ->" + xmlInput);
       Statement stmt = null;
       StringBuilder query = new StringBuilder();
       
       
       try{
            stmt = manager.getConnection().createStatement();
            
            ResultSet myRes = null;
            String myfoo = parseXML(xmlInput, "foo", false, 0);
            String myID = myfoo.split("\\|")[0];
            String myUser = myfoo.split("\\|")[1];
            String tablename = null;
            String myflag = null;
            
            if(flag.equalsIgnoreCase("yellowFlag")){
                myflag = "Flag";
            }else if(flag.equalsIgnoreCase("redFlag")){
                myflag = "Exclude";
            }//if-else statements for flag parameters
            
            query.append("SELECT  TableName FROM Mappingtable WHERE ");
            query.append("MappingTable_ID = " + myID + " ");
            query.append("AND Username = '" + myUser + "' ");
            
            myRes = stmt.executeQuery(query.toString());
            //System.out.println("Inside the ETLDELETE && getting the tablename query -> " + query.toString());
            myRes.next();
            tablename = myRes.getString("TableName");
            
            query = new StringBuilder();
            query.append("DELETE FROM Mapping_ETL_Rules WHERE ");
            query.append("Source_TableName = '"+tablename+"' ");
            query.append("AND FlagType = '" + myflag + "'");
           // System.out.println("Inside the ETL DELETE query -> " + query.toString());
            stmt.executeUpdate(query.toString());
            return true;
            
       }catch(SQLException ex){
           Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, ex);
           ex.printStackTrace();
           System.out.println("Error deleting etl rules");
           return false;
       }//try-catch
       catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            stmt = null;
            return false;
       }
    }//deleteEtl method
    
    public HashMap<String, String> initializeColumnMap(){
        HashMap<String, String> myMap = new HashMap<String, String>();
        
        myMap.put("tablename","Source_TableName");
        myMap.put("ColName","Source_ColumnName");
        myMap.put("RuleType","Rule_Type");
        myMap.put("rule","Rule");
        myMap.put("flag","FlagType");
        
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
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }catch(Exception e){
            Logger.getLogger(MappingETL.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return null;
       }
    }
    
    
}
