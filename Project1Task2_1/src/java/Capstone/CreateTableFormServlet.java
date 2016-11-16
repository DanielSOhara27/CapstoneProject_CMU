/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capstone;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Capstone.MappingTable;
import java.sql.ResultSet;

/**
 *
 * @author danielohara
 */
@WebServlet(name = "CreateTableFormServlet", urlPatterns = {"/CreateTableForm"})
public class CreateTableFormServlet extends HttpServlet {
    DBConnectionManager manager = DBConnectionManager.getInstance();
    Statement stmt = null;
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
    System.out.println("Inside CreateTableForm Servlet");
    
    MappingTable mappingTableService = new MappingTable();
    ResultSet sqlResult = null;
    
    System.out.println("Trying CreateTable function");
    
    String urlParameter_Test =" {\"SiteID\": \"BRCR01\",\"ModelID\": \"Phizer\",\"SensorID\": \"7392-960779\",\"Username\": \"Ryan1\",\"SiteName\": \"WeatherForest-TESTNAME\",\"SensorType\": \"DO\",\"Location\": \"Lat:11234123, Lng:102352\",\"NumColumn\": 5,\"ColumnNames\": \"Original_Timestamp:seconds,BV(Volts):String,T (Deg C):String,DO (mg/L):String,Q():String\",\"Row_To_Skip\": 5,\"Delimiter\": \";\",\"RangeBetweenReadings\": \"15 min\",\"AcceptableRange\": \"20 min\",\"TypeSensor_BaseTable\": \"false\",\"Site_BaseTable\": \"false\",\"Public\": \"true\"}";
    System.out.println(urlParameter_Test);
    
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MappingTableForm</title>");            
            out.println("</head>");
            out.println("<body vertical-align=\"middle\" >");
            out.println("<h1>Servlet MappingTableForm mk2 at " + request.getContextPath() + "</h1>");
            System.out.println("Before the mappingTable if");
            /*if(mappingTableService.InsertMetaData_MappingTable(urlParameter_Test)){
                out.println("<p> CREATE TABLE FINISHED SUCCESSFULLY </p>");
            }else{
                out.println("<p> SOMETHING WENT WRONG </p>");
            }*/
            
            sqlResult = null;
            sqlResult = mappingTableService.DescribeTables("MappingTable");
            if(sqlResult != null){
                String myTable = "<h3>Mapping Table</h3>"
                        + "<table border=\"1\">"
                        + "<tr>"
                        + "<th>Field</th>"
                        + "<th>Type</th>"
                        + "<th>Null</th>"
                        + "<th>Key</th>"
                        + "<th>Default</th>"
                        + "<th>Extra</th>"
                        + "</tr>";
                while(sqlResult.next()){
                    myTable = myTable + "<tr>";
                    myTable = myTable + "<td>" + sqlResult.getString("Field") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Type") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Null") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Key") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Default") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Extra") + "</td>";
                    myTable = myTable + "</tr>";
                }
                myTable = myTable + "</table><br /><br />";
                out.println(myTable);
            }
            sqlResult = null;
            sqlResult = mappingTableService.DescribeTables("MappingColumn");
            if(sqlResult != null){
                String myTable = "<h3>How the Mapping Column</h3>"
                        + "<table border=\"1\">"
                        + "<tr>"
                        + "<th>Field</th>"
                        + "<th>Type</th>"
                        + "<th>Null</th>"
                        + "<th>Key</th>"
                        + "<th>Default</th>"
                        + "<th>Extra</th>"
                        + "</tr>";
                while(sqlResult.next()){
                    myTable = myTable + "<tr>";
                    myTable = myTable + "<td>" + sqlResult.getString("Field") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Type") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Null") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Key") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Default") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Extra") + "</td>";
                    myTable = myTable + "</tr>";
                }
                myTable = myTable + "</table><br /><br />";
                out.println(myTable);
            }
            
            sqlResult = null;
            sqlResult = mappingTableService.DescribeTables("Mapping_ETL_Rules");
            if(sqlResult != null){
                String myTable = "<h3>How the Mapping ETL Rules</h3>"
                        + "<table border=\"1\">"
                        + "<tr>"
                        + "<th>Field</th>"
                        + "<th>Type</th>"
                        + "<th>Null</th>"
                        + "<th>Key</th>"
                        + "<th>Default</th>"
                        + "<th>Extra</th>"
                        + "</tr>";
                while(sqlResult.next()){
                    myTable = myTable + "<tr>";
                    myTable = myTable + "<td>" + sqlResult.getString("Field") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Type") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Null") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Key") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Default") + "</td>";
                    myTable = myTable + "<td>" + sqlResult.getString("Extra") + "</td>";
                    myTable = myTable + "</tr>";
                }
                myTable = myTable + "</table><br /><br />";
                out.println(myTable);
            }
            
            sqlResult = mappingTableService.InsertMetaData_MappingTable2(urlParameter_Test);
            if(sqlResult != null){
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
                }
                myTable = myTable + "</table><br /><br />";
                out.println(myTable);
            }else{
                out.println("<p> SOMETHING WENT WRONG </p>");
            }
            
            
            System.out.println("After the mappingtable if");
            out.println("</body>");
            out.println("</html>");
        }catch(Exception e) {
            e.printStackTrace();
            stmt = null;
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
