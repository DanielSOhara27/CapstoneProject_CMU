/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.createtable;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.opencsv.CSVReader;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 *
 * @author danielohara
 */
@WebServlet(name = "CreateTableFormServlet", urlPatterns = {"/CreateTableForm"})
public class CreateTableFormServlet extends HttpServlet {
    private static final String BASE_RECEIVE_DIRECTORY = "C:/samplefiles/";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);      
            if (session.getAttribute("Username") == null)
                { //checks if there's a LOGIN_USER set in session...
                    String page = "4.0_CreateTable-initialize.jsp";                    
                    request.setAttribute("value", page);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
        
        response.setContentType("text/html;charset=UTF-8");
        String option = request.getSession().getAttribute("option").toString();
        //System.out.println("the value of option is " + option);
        //testParameters(request, response);
        MappingTable mappingTableService = new MappingTable();
        MappingETL mappingETLService = new MappingETL();
        MappingColumn mappingColumnService = new MappingColumn();
        String result = null;
        String[] params = null;
        int size = -1;
        boolean emptyParameters = true;
        
        /*This outer if is checking to see if the username belongs to a faculty member, 
        if it doesn't then it need to check if the student has an active account and we
        need to corroborate which faculty advisor this student is working with.*/
        if( ! mappingTableService.isFaculty(request.getSession().getAttribute("Username").toString()) ){
            //This inner if is checking the students info against the DB to see if he/she is active
            if( mappingTableService.isStudent(request.getSession().getAttribute("Username").toString()) ){
                //This last part is getting the faculty's info that the student needs
                String aux = mappingTableService.getStudentAdvisor(request.getSession().getAttribute("Username").toString());
                request.setAttribute("Username", aux); //For simplicity we are exchanging the usernames now
                
                System.out.println("The Value of aux is: " + aux);
            }//only faculty are allowed to keep tables under their own name
        }//end of if to check it its NOT faculty
        System.out.println("After checking for User");
        
        switch(option){
            
            case "0":
                //System.out.println("Case 0 -> option: " + option);
                //testParameters(request, response);
                params = mappingTableService.initiliazeMetaData_MappingTable(request.getParameterMap(), request.getParameterNames(), request.getSession().getAttribute("Username").toString());
                if(params!=null){
                    result = params[0];
                    String [] params2 = new String[1];
                    params2[0] = params[1];
                    result = prepareResult("createTable-1", params2, result, -1);
                }else{
                    result = prepareResult("", null, null, -1);
                }
                
                if(result.contains("<ERROR>")){
                    System.out.println("There was an error preparing the result");
                    doResponse(request, response, result, "1.3_error.jsp");
                }
                else{
                    System.out.println("Going to upload jsp");
                    doResponse(request, response, result, "4.1.1_CreateTable-Upload.jsp");
                }
                
                /*result = null;
                option = null;
                size = -1;
                params = null;
                params2 = null;
                mappingTableService = new MappingTable();
                mappingETLService = new MappingETL();
                mappingColumnService = new MappingColumn();*/
                
                break;
                
                
            case "1":
                //System.out.println("Case 1 -> option: " + option + " metadata");
                //testParameters(request, response);
                result = parseXML((String)request.getSession().getAttribute("xmlInput"), "foo", true, 0);
                params = getHeaders(request, response);
                
                /*Preparing XML document for JSP input*/
                if(params != null){
                    result = prepareResult("createTable-2", params,result ,params.length);
                    
                }else{
                    result = prepareResult("", null, null, -1);
                }
                
                /*Sending User to appropriate JSP*/
                if(result.contains("<ERROR>")){
                    mappingTableService.deleteTablaMetaData(request.getSession().getAttribute("xmlInput").toString());
                    System.out.println("There was an error preparing the result");
                    doResponse(request, response, result, "1.3_error.jsp");
                }else{
                   System.out.println("Going to upload jsp");
                    doResponse(request, response, result, "4.3_CreateTable-columns.jsp"); 
                }//end of else
                
                
                /*result = null;
                option = null;
                size = -1;
                params = null;
                mappingTableService = new MappingTable();
                mappingETLService = new MappingETL();
                mappingColumnService = new MappingColumn();*/
                
                break;
                
            
            case "3":
                //System.out.println("Case 3 -> option: " + option + " columns");
                //testParameters(request, response);
                String myInput = request.getSession().getAttribute("xmlInput").toString();
                HashMap <String, String> myColumnMap = mappingTableService.initiliazeColumnData_MappingTable(request.getParameterMap(), request.getSession().getAttribute("xmlInput").toString());
                String myTable = mappingTableService.tablename_MappingTable(myInput);
                
                /*Preparing XML Response to JSP*/
                if(myColumnMap != null && myTable !=null){
                    
                    if(!myTable.isEmpty()){//If myTable IS NOT empty
                       
                       /*Before working the XML Document, we have to create the USER-GENERATED tables first*/
                       params =  mappingTableService.CreateUserTable(myColumnMap, myTable);
                       String foo = parseXML(myInput, "foo", true, 0);
                       size = Integer.parseInt(parseXML(myInput, "NumCol", false, 0));
                       
                       if(params!=null){
                       result = prepareResult("createTable-3", params, foo, size);
                       }else{
                       result = prepareResult("", null, null, -1);
                       }
                       
                    }else{//else sql returned an empty string and there was an error somewhere
                        System.out.println("SQL return an empty string inside Case 3 myTable.isEmpty");
                        result = prepareResult("", null, null, -1);
                    }//inner else
                    
                }else{
                    result = prepareResult("", null, null, -1); 
                }//outer else with myColumnMap and myTable 
                
                /*Sending User to appropriate JSP*/
                if(result.contains("<ERROR>")){
                    mappingTableService.deleteUserTable(request.getSession().getAttribute("xmlInput").toString());
                    mappingTableService.deleteTablaMetaData(request.getSession().getAttribute("xmlInput").toString());
                    System.out.println("There was an error preparing the result");
                    doResponse(request, response, result, "1.3_error.jsp");
                }else{
                   System.out.println("Going to redFlag jsp");
                    doResponse(request, response, result , "4.4_CreateTable-redFlag.jsp"); 
                }//end of else
                
                /*result = null;
                option = null;
                size = -1;
                params = null;
                mappingTableService = new MappingTable();
                mappingETLService = new MappingETL();
                mappingColumnService = new MappingColumn();*/
                break;
                
                
            case "4":
                //System.out.println("Case 4 -> option: " + option + " redFlag");
                //testParameters(request, response);
                emptyParameters = mappingETLService.checkEmptyForm_MappingEtlRules(request.getParameterMap(), request.getSession().getAttribute("xmlInput").toString(), "redFlag");
                
                if( !emptyParameters){
                    //If there is atleast one rule defined by user, then try to insert it into etl table
                    
                    params = mappingETLService.createEtlRule_MappingEtlRules(request.getParameterMap(), request.getSession().getAttribute("xmlInput").toString(), "redFlag");


                    if(params != null){
                        result = parseXML((String) request.getSession().getAttribute("xmlInput"), "foo", true, 0);
                        size = Integer.parseInt(parseXML((String)request.getSession().getAttribute("xmlInput"), "NumCol", false, 0));
                        System.out.println("redFlag -> prepareResult -> ");
                        System.out.println("params size -> " + params.length + "\nresult -> " + result + "\nsize -> " + size);
                        result = prepareResult("createTable-4", params, result, size);
                    }else{
                        result = prepareResult("", null, null, -1);

                    }//end of else
                
                }//if NOT empty parameters
                else{
                    //else, if there are no rules, skip to yellow flag jsp
                    result = request.getSession().getAttribute("xmlInput").toString();
                    
                }//else TRUE empty parameters
                
                if(result.contains("<ERROR>")){
                    mappingETLService.deleteEtl_MappingEtlRules(request.getSession().getAttribute("xmlInput").toString(), "redFlag");
                    mappingTableService.deleteUserTable(request.getSession().getAttribute("xmlInput").toString());
                    mappingTableService.deleteTablaMetaData(request.getSession().getAttribute("xmlInput").toString());
                    System.out.println("There was an error with ETL rules");
                    doResponse(request, response, result, "1.3_error.jsp");
                }else{
                    System.out.println("Going to yellow flag");
                    doResponse(request, response, result, "4.5_CreateTable-yellowFlag.jsp");
                }//end of else
                
                /*
                result = null;
                option = null;
                size = -1;
                params = null;
                mappingTableService = new MappingTable();
                mappingETLService = new MappingETL();
                mappingColumnService = new MappingColumn();*/
                break;
                
                
                
            case "5":
                //testParameters(request, response);
                emptyParameters = mappingETLService.checkEmptyForm_MappingEtlRules(request.getParameterMap(), request.getSession().getAttribute("xmlInput").toString(), "yellowFlag");
                
                if(!emptyParameters){
                    //if there are yellow flag rules defined by the user, then try to insert them into etl table
                    params = mappingETLService.createEtlRule_MappingEtlRules(request.getParameterMap(), request.getSession().getAttribute("xmlInput").toString(), "yellowFlag");
                }else{
                    params = null;
                    //Else, there are no rules for yellow flag so skip this functionality
                }//if else for emptyParameters
                
                /*Checking if basetable exists*/
                //System.out.println("inside yellowFlag case, getting basetableFlag ");
                String myBaseTableFlag = mappingTableService.getBaseTableFlag(request.getSession().getAttribute("xmlInput").toString());
                String baseTableExists = mappingTableService.isBaseTableSet2(myBaseTableFlag, request.getSession().getAttribute("xmlInput").toString());
                //System.out.println("the result of getbasetableflag -> " + myBaseTableFlag + " $$$$\nbaseTableExists2 -> " + baseTableExists + " $$$$");
                
                if(params != null){
                    result = parseXML((String) request.getSession().getAttribute("xmlInput"), "foo", true, 0);
                    size = Integer.parseInt(parseXML((String)request.getSession().getAttribute("xmlInput"), "NumCol", false, 0));
                    result = prepareResult("createTable-4", params, result, size);
                    
                    
                }else if(params == null & emptyParameters){
                    result = request.getSession().getAttribute("xmlInput").toString();
                }else{
                    result = prepareResult("", null, null, -1);
                
                }//end of else
                
                if(result.contains("<ERROR>")){
                    mappingETLService.deleteEtl_MappingEtlRules(request.getSession().getAttribute("xmlInput").toString(), "yellowFlag");
                    mappingTableService.deleteUserTable(request.getSession().getAttribute("xmlInput").toString());
                    mappingTableService.deleteTablaMetaData(request.getSession().getAttribute("xmlInput").toString());
                    System.out.println("There was an error with ETL rules");
                    doResponse(request, response, result, "1.3_error.jsp");
                }else{
                    //System.out.println("the boolean.valueof(basetableflag) -> " + Boolean.valueOf(baseTableExists));
                    if(!Boolean.valueOf(baseTableExists)){//if there is NO base table, you are finished
                        result = prepareResult("createTable-7", null, "<Success> Finished creating your first table </Success>", -1);
                        System.out.println("Going to success");
                        //System.out.println("result -> " + result);
                        doResponse(request, response, result, "4.7_CreateTable-Successful.jsp");
                    }else{//if there IS a base table, now you have to map it.
                        
                        System.out.println("Going to ColumnMapping");
                        //System.out.println("The result from yellowFlag -> " + result);
                        params = mappingTableService.getBaseTableInfo(result);
                        if(params != null){
                            //no errors
                            result = prepareResult("createTable-6", params, result, 0);
                            //System.out.println("The result before sending to ColumnMapping -> " + result);
                            doResponse(request, response, result, "4.6_CreateTable-ColumnMapping.jsp");
                        }else{
                            //There was some error
                            mappingETLService.deleteEtl_MappingEtlRules(request.getSession().getAttribute("xmlInput").toString(), "yellowFlag");
                            mappingTableService.deleteUserTable(request.getSession().getAttribute("xmlInput").toString());
                            mappingTableService.deleteTablaMetaData(request.getSession().getAttribute("xmlInput").toString());
                            result = prepareResult("", null, null, -1);
                            doResponse(request, response, result, "1.3_error.jsp");
                        }//if else statement where we check that there were now errors
                        
                    }//end of inner else that sends users to ColumnMapping
                }//end of else
                
                /*
                result = null;
                option = null;
                size = -1;
                params = null;
                mappingTableService = new MappingTable();
                mappingETLService = new MappingETL();
                mappingColumnService = new MappingColumn();*/
                
                break;
                
                
                
            case "6":
                System.out.println("Case 6 -> option: " + option);
                //testParameters(request, response);
                result = mappingColumnService.createColumnMap_MappingColumns(request.getParameterMap(), request.getSession().getAttribute("xmlInput").toString());
                System.out.println("the result of createColumnMap_MappingColumns -> " + result);
                
                /*Checking if column data was inserted correctly into DB at corresponding mappingTable*/
                if(result != null){
                    result = prepareResult("createTable-7", null, result, -1);
                }else{
                    result = prepareResult("", null, null, -1);
                }//Checking if the operation was a success or not
                
                
                
                //Checking if there was an error or if its
                if(result.contains("<ERROR>")){
                    mappingColumnService.deleteColumnMapping_MappingColumns(request.getSession().getAttribute("xmlInput").toString());
                    mappingETLService.deleteEtl_MappingEtlRules(request.getSession().getAttribute("xmlInput").toString(), "redFlag");
                    mappingTableService.deleteUserTable(request.getSession().getAttribute("xmlInput").toString());
                    mappingTableService.deleteTablaMetaData(request.getSession().getAttribute("xmlInput").toString());
                   System.out.println("There was an error with ETL rules");
                   doResponse(request, response, result, "1.3_error.jsp");
                }else{
                   System.out.println("Going to CreateTable-success");
                   System.out.println("The result from columnMapping -> " + result);
                   doResponse(request, response, result, "4.7_CreateTable-Successful.jsp");
                }//end of else for redirecting user to appropriate page.
               
                
                /*
                result = null;
                option = null;
                size = -1;
                params = null;
                params2 = null;
                mappingTableService = new MappingTable();
                mappingETLService = new MappingETL();
                mappingColumnService = new MappingColumn();*/
                
                break;
                
            default:
                /*
                result = null;
                option = null;
                size = -1;
                params = null;
                params2 = null;
                mappingTableService = new MappingTable();
                mappingETLService = new MappingETL();*/
                //request.getSession().setAttribute("Username", "N/A");
                //request.getSession().setAttribute("xmlInput", "N/A");
                 mappingColumnService.deleteColumnMapping_MappingColumns(request.getSession().getAttribute("xmlInput").toString());
                 mappingETLService.deleteEtl_MappingEtlRules(request.getSession().getAttribute("xmlInput").toString(), "redFlag");
                  mappingETLService.deleteEtl_MappingEtlRules(request.getSession().getAttribute("xmlInput").toString(), "yellowFlag");
                 mappingTableService.deleteUserTable(request.getSession().getAttribute("xmlInput").toString());
                 mappingTableService.deleteTablaMetaData(request.getSession().getAttribute("xmlInput").toString());
                doResponse(request, response, "ERROR", "1.3_error.jsp");
                System.out.println("Default -> option: " + option);
                
                break;
        }
        
    
    }//end of method
    
   
    public void testParameters(HttpServletRequest request, HttpServletResponse response){
        System.out.println("The size of the parameter map inside Test Parameter is : " + request.getParameterMap().size());
        
        try (PrintWriter out = response.getWriter()){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MappingTableForm</title>");            
            out.println("</head>");
            out.println("<body vertical-align=\"middle\" >");
            out.println("<h1>Servlet Connecting Frontend with Backend at " + request.getContextPath() + "</h1>");
            Enumeration<String> myparameters =  request.getParameterNames();
            String auxName;
            
            out.println("<ol>");
            
            while(myparameters.hasMoreElements()){
            auxName = myparameters.nextElement();
            out.println("<li> " + auxName + ": " + request.getParameter(auxName) +" </li>");
            }//while
            
            out.println("</ol>");
            
            out.println("</body></html>");
       }//try
       catch(IOException e){
        e.printStackTrace();
       }
    
    }
   
    public String prepareResult(String action, String[] params, String foo, int size){
        StringBuilder myResult = new StringBuilder();
        int auxNum = 0;
        try{
            myResult.append("<xmlInput>");

            switch(action){


                case "createTable-1":
                    myResult.append("<foo>" + foo + "</foo>");
                    myResult.append(params[0]);
                    break;


                case "createTable-2":
                case "createTable-3":
                case "createTable-4":
                    myResult.append("<foo>" + foo + "</foo>");
                    myResult.append("<NumCol>" + size + "</NumCol>");
                    for(String myParam : params){
                        System.out.println("Size of params " + params.length);
                        System.out.println("myParam -> " + myParam);
                        auxNum++;
                        System.out.println("auxNum -> " + auxNum);
                        myResult.append("<Column" + auxNum + ">" + myParam.trim() + "</Column" + auxNum+"> ");
                    }//for each
                    break;

                case "createTable-6":
                    //doing <foo>
                    System.out.println(foo);
                    String myfoo = parseXML(foo, "foo", true, 0);
                    myResult.append("<foo>" + myfoo + "</foo>");

                    //doing the base Table section
                    myResult.append("<bNumCol>" + params.length + "</bNumCol>");
                    for(int x = 0, y = 1; x < params.length; x++, y++){
                        myResult.append("<bColumn" + y + ">" + params[x] + "</bColumn" + y +">");
                    }

                    //doing the new table section
                    String myNum = parseXML(foo, "NumCol", false, 0);
                    String auxString = null;
                    myResult.append("<nNumCol>" + myNum + "</nNumCol>");
                    for(int x = 0, y=1; x < Integer.parseInt(myNum); x++, y++){
                        auxString = parseXML(foo, "Column"+y, false, 0);
                        myResult.append("<nColumn" + y + ">" + auxString + "</nColumn" + y + ">");
                    }

                    break;

                case "createTable-7":
                    myResult.append("<foo> N/A </foo>");
                    myResult.append(foo);
                    break;



                default:
                    myResult.append("<ERROR>THERE WAS AN ERROR AND NO APPROPRIATE ACTION WAS CHOSEN</ERROR>");
                    break;
            }//end of Switch case

            myResult.append("</xmlInput>");

            return myResult.toString();
        }catch(Exception e){
            e.printStackTrace();
            Logger.getLogger(CreateTableFormServlet.class.getName()).log(Level.SEVERE, null, e);
            return "<ERROR>THERE WAS AN ERROR TRYING TO PREPARE THE RESULT OF THE OPERATION</Error>";
        }
    }//end of method doAction
    
    public void doResponse(HttpServletRequest request, HttpServletResponse response, String result, String next){
        
        
        
        try {
            request.setAttribute("xmlInput", result);
            System.out.println("inside doResponse result -> " + result + " $$$$$$");
            request.getRequestDispatcher(next).forward(request, response);
        
        } catch (ServletException ex) {
            ex.printStackTrace();
            Logger.getLogger(CreateTableFormServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(CreateTableFormServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String[] getHeaders(HttpServletRequest request, HttpServletResponse response){
        System.out.println("Inside CreateTableForm Servlet do Post"); 
        String filePath = null;
        StringBuilder res = new StringBuilder(); 
        String urlXML = (String) request.getSession().getAttribute("xmlInput");
        String foo = parseXML(urlXML, "foo", true, -1);
        String myRow = parseXML(urlXML, "rows", false, 0);
        String myDelimiter = parseXML(urlXML, "delimiter", false, 0);
         
         
                 if (ServletFileUpload.isMultipartContent(request)) {
            
            try {

                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : multiparts) {

                    if (!item.isFormField()) {

                        String name = new File(item.getName()).getName();
                        filePath = BASE_RECEIVE_DIRECTORY + name;
                        item.write(new File(BASE_RECEIVE_DIRECTORY + name));
                        System.out.println(" * " + filePath);
                            
                    }
                    
                    ArrayList<String> results = parseFile(filePath, myRow, myDelimiter);
                    String[] results2 = new String[results.size()];
                    
                    for(int x = 0; x< results.size(); x++){
                        results2[x] = results.get(x);
                    }
                    
                    return results2;
                    /*
                    //request.setAttribute("columnNames", results);
                    //request.setAttribute("columns",results.size());
                    //nextView = "createTable_2.jsp";
                    */
                }
         
          } catch (Exception ex) {
                ex.printStackTrace();
                return null;
                //nextView = "CreateTable_1.1.jsp";

            }

        }
                 
                 
                 
        //RequestDispatcher view = request.getRequestDispatcher(nextView);

        //view.forward(request, response);
        return null;
    }//end of method
    
     // determine the type of split and line number where parse occurs 
    public ArrayList<String> parseFile(String filePath, String myRow, String myDelimiter) throws IOException
     {
     
        BufferedReader br = null;
        ArrayList<String> fileParts = new ArrayList<String>();
        String line = null;
        int lineCount = 0;
        String[] data = null;
        int csvCount = 0;  // this is the only change i made in this part -- other portion is just the addition of one more if statement.
        int RowsToSkip = Integer.parseInt(myRow);
        
        br = new BufferedReader(new FileReader(filePath));
        System.out.println("within parseFile method " + filePath);
            while((line = br.readLine())!= null)
            {
                // System.out.println(line);
                
                //numheader
                if(lineCount == RowsToSkip)
                {
                    data = line.split(myDelimiter); // determine the type of split required  // delimiter
                    System.out.println(line);
                    System.out.println(data.length);
                    
                    
                }
                
                lineCount++;
            }
        
        
                         
        for(int i = 0; i < data.length; i++)
           fileParts.add(data[i]);
               
      return fileParts;
    }
    
    public String parseXML(String xmlString, String nameOfTag, boolean foo, int index){
        DOMParser parser = new DOMParser();
        try {
            parser.parse(new InputSource(new java.io.StringReader(xmlString)));
            Document doc = parser.getDocument();
            String message;
            if(foo){
            message = doc.getElementsByTagName(nameOfTag).item(0).getTextContent();
            }else{
            message = doc.getElementsByTagName(nameOfTag).item(index).getTextContent();
            }
            
            return message;
        } catch (SAXException ex) {
            ex.printStackTrace();
            Logger.getLogger(CreateTableFormServlet.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(CreateTableFormServlet.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
