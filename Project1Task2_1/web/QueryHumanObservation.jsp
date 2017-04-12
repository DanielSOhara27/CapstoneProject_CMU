<%-- 
    Document   : QueryHumanObservation
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
    ResultSet resultset = statement.executeQuery("SELECT table_name,type FROM `ObservationsTable`");

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


        <form action="QueryHumanObservationServlet" method="GET" style = "margin-left: 0.5cm">    
            <div class="w3-row-padding">

                <div class="w3-container">
                    <p>Available Observation Tables</p>
                </div>
                <!-- http://www.java2s.com/Tutorial/Java/0360__JSP/OutputResultSet.htm -->
                <TABLE class="w3-table w3-striped w3-bordered w3-border" BORDER="1" style = "width:auto;">

                    <tr>
                        <td>Select</td>
                        <td>TableName</td>
                        <td>Type</td>

                    </tr>
                    <% while (resultset.next()) {
                    %>       

                    <tr align="center">
                        <td><input type="radio" name="observationTable" value="<%= resultset.getString(1)%>"></td>
                        <td><%= resultset.getString(1)%></td>
                        <td><%= resultset.getString(2)%></td>
                    </tr>
                    <% }%>

                </TABLE>
                    <BR>
                <input type="submit" class="w3-btn w3-blue-grey w3-center" name="submit" value="Submit">
                </form>


        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>

                </body>
                </html>
