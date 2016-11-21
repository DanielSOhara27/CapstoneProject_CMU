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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    String urlParameter_Test2 ="{\"userTable\": ["
            + "{\"nameColumn\": \"Original_Timestamp\",\"dataType\": \"Date\", \"Format\":\"seconds\"},"
            + "{\"nameColumn\": \"BV(Volts)\", \"dataType\": \"String\", \"Format\":\"\"},"
            + "{\"nameColumn\": \"T (Deg C)\", \"dataType\": \"String\", \"Format\":\"\"},"
            + "{\"nameColumn\": \"DO (mg/L)\", \"dataType\": \"String\", \"Format\":\"\"},"
            + "{\"nameColumn\": \"Q()\", \"dataType\": \"String\", \"Format\":\"\"}"
            + "]}";
    
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
                out.println(printDescribeTable(sqlResult));
            }
            sqlResult = null;
            sqlResult = mappingTableService.DescribeTables("MappingColumn");
            if(sqlResult != null){
                out.println(printDescribeTable(sqlResult));
            }
            
            sqlResult = null;
            sqlResult = mappingTableService.DescribeTables("Mapping_ETL_Rules");
            if(sqlResult != null){
                out.println(printDescribeTable(sqlResult));
            }
            
            sqlResult = mappingTableService.InsertMetaData_MappingTable2(urlParameter_Test);
            if(sqlResult != null){
               
                out.println(mappingTableService.PrintCreateTable(sqlResult));
                
                out.println("<br />");
                String tableName = sqlResult.getString("TableName");
                sqlResult = mappingTableService.CreateUserTable(urlParameter_Test2, tableName);
            }//if
            System.out.println("After the mappingtable if");
            out.println("</body>");
            out.println("</html>");
            
            stmt = null;
        

        } catch (SQLException ex) {
            Logger.getLogger(CreateTableFormServlet.class.getName()).log(Level.SEVERE, null, ex);
        }//try print
    }//end of method
    
    public String printDescribeTable(ResultSet sqlResult){
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
        try {
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
        } catch (SQLException ex) {
            Logger.getLogger(CreateTableFormServlet.class.getName()).log(Level.SEVERE, null, ex);
            myTable = "<p>There was an error printing the describe table</p></table>";
        }
                myTable = myTable + "</table><br /><br />";
                
                return myTable;
    
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
