<%-- 
    Document   : QuerySensorTypeSite(Vertical join)
    Created on : Nov 4, 2016, 12:46:52 AM
    Author     : Anshu Agrawal
--%>

<%@page import="capstone.connection.DBConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%
    DBConnectionManager DBManager = DBConnectionManager.getInstance();
    Connection connection = DBManager.getConnection();
    Statement statement = connection.createStatement();
    String sensorType = request.getParameter("sensorType");
    //String sensorType = "DO";
    ResultSet resultset = statement.executeQuery("SELECT `SensorType`,`TableName`,`SiteID`,`ModelID`,`SensorID`,`Username` FROM `MappingTable` where `SensorType`='" + sensorType + "'");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Query</title>
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
    </head>
    <style> 
        span {
            display: inline-block;
            vertical-align: middle;
            line-height: normal;      
        }
    </style>
    <body>
        <!-- Start header -->
        <div class="w3-container" style="height: 3.1cm; background-color: #533678;align-content: center; ">
            <br>
            <a href="http://www.chatham.edu">
                <img src="https://www.chatham.edu/_images/_logos/Chatham-U-H-logo-400.png" alt="Chatham University" title="Chatham University" align="center">
            </a><br><br>
            <span style="color: white;font-size: 17px;font-family: proxima-nova;"> FALK SCHOOL OF SUSTAINABILITY & ENVIRONMENT</span>
        </div>
        <div class="w3-container w3-white" style="height: 0.07cm;">
        </div>
        <div class="w3-container" style="height: 1cm; line-height: 0.9cm; background-color: #46434A; color: whitesmoke;">
            <span><a href='login.jsp'>Log-in</a></span> &nbsp;| &nbsp;
            <span><a href='UploadHomePage.jsp'>Upload</a></span> &nbsp;| &nbsp;
            <span><a href='QueryHomePage.jsp'>Query/Download</a></span> &nbsp;| &nbsp;
            <span><a href='4.0_CreateTable-initialize.jsp'>Create Table</a></span> &nbsp;| &nbsp;
            <span><a href='5.0_Admin-Choose.jsp'>Admin</a></span> &nbsp;| &nbsp;
            <span><a href='6.0_About.jsp'>About</a></span>
        </div>
        <!-- End of header-->
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Query/Download</h1>
        </div>
        <form id= "querysensorsite" action="QuerySensorTypeSiteServlet" method="POST">    
            <div class="w3-row-padding"  style = "margin-left: 0.5cm">
                <div class="w3-container">
                    <p>Choose the sites you want to get the data from.</p>
                </div>
                <!-- http://www.java2s.com/Tutorial/Java/0360__JSP/OutputResultSet.htm -->
                <TABLE class="w3-table w3-striped w3-bordered w3-border" BORDER="1" style = "width:auto;">
                    <tr>
                        <td>Select</td>
                        <td>SensorType</td>
                        <td>TableName</td>
                        <td>SiteID</td>
                        <td>ModelID</td>
                        <td>SensorID</td>
                        <td>Username</td>
                    </tr>
                    <% while (resultset.next()) {
                    %>       
                    <tr>
                        <td align="center"><input type="checkbox" name="tables" value="<%= resultset.getString(2)%>"></td>
                        <td><%= resultset.getString(1)%></td>
                        <td><%= resultset.getString(2)%></td>
                        <td><%= resultset.getString(3)%></td>
                        <td><%= resultset.getString(4)%></td>
                        <td><%= resultset.getString(5)%></td>
                        <td><%= resultset.getString(6)%></td>
                    </tr>
                    <% }%>
                </TABLE>
                <br>
                <div class="w3-row-padding">
                    <div class="w3-quarter">
                        <p><b>Start Date:</b></p>
                        <input style = "margin-bottom: 0.3cm; width: 8cm;" 
                               type="date" class="w3-btn w3-blue-grey w3-center" name="start_date" value="StartDate" />
                        <BR>
                        <p><b>End Date:</b><BR></p>
                        <input style = "margin-bottom: 0.3cm; width: 8cm;" 
                               type="date" class="w3-btn w3-blue-grey w3-center" name="end_date" value="EndDate" />
                        <BR>
                    </div>
                    <div class = "w3-half">
                        <p><b>Choose Start Date Time:</b></p>
                        <input style = "margin-bottom: 0.3cm; width: 8cm;" 
                               type="time" class="w3-btn w3-blue-grey w3-center" name="start_time" value="start_time" />
                        <br>
                        <p><b>Choose End Date Time:</b></p>
                        <input style = "margin-bottom: 0.3cm; width: 8cm;" 
                               type="time" class="w3-btn w3-blue-grey w3-center" name="end_time" value="end_time" />
                        <BR>
                    </div>
                </div>

                <div style="margin-bottom: 3cm;">
                    <BR>
                    <label for ="letter">Include Yellow flag?</label>
                    <select name ="flag">
                        <option value="yes"> Include</option>
                        <option value="no"> Do not include</option>
                    </select>
                    <input style = " margin-left: 4cm; width: 3cm;" 
                           type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Query" />
                </div>
                    
            </div>
        </form>
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>        
                
    </body>
</html>
