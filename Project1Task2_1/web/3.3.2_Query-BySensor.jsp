<%-- 
    Document   : QuerySiteSensorType(Horizontal join)
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
    String site = request.getParameter("site");
    //String site = "BRCR01";
    ResultSet resultset = statement.executeQuery("SELECT `TableName`,`SiteID`,`ModelID`,`SensorID`,`Username`,`SensorType` FROM `MappingTable` where `SiteID`='" + site + "'");

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Query</title>
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
        <script type="text/javascript">

            /***********************************************
             * Limit number of checked checkboxes script- by JavaScript Kit (www.javascriptkit.com)
             * This notice must stay intact for usage
             * Visit JavaScript Kit at http://www.javascriptkit.com/ for this script and 100s more
             ***********************************************/

            function checkboxlimit(checkgroup, limit) {
                var checkgroup = checkgroup
                var limit = limit
                for (var i = 0; i < checkgroup.length; i++) {
                    checkgroup[i].onclick = function () {
                        var checkedcount = 0
                        for (var i = 0; i < checkgroup.length; i++)
                            checkedcount += (checkgroup[i].checked) ? 1 : 0
                        if (checkedcount > limit) {
                            alert("Please select " + limit + " checkboxes")
                            this.checked = false
                        }
                    }
                }
            }

        </script>
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
            <span><a href='1.1_login.jsp'>Log-in</a></span> &nbsp;| &nbsp;
            <span><a href='2.1_Upload-ChooseType.jsp'>Upload</a></span> &nbsp;| &nbsp;
            <span><a href='3.1_Query-ChooseType.jsp'>Query/Download</a></span> &nbsp;| &nbsp;
            <span><a href='4.0_CreateTable-initialize.jsp'>Create Table</a></span> &nbsp;| &nbsp;
            <span><a href='5.0_Admin-Choose.jsp.jsp'>Admin</a></span> &nbsp;| &nbsp;
            <span><a href='6.0_About.jsp'>About</a></span>
        </div>
        <!-- End of header-->

        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Query by Site Step 2</h1>
        </div>


        <form id= "querysitesensor" action="QuerySiteSensorTypeServlet" method="GET" style = "margin-left: 0.25cm">    
            <div class="w3-row-padding">

                <div class="w3-container">
                    <p>Pick Only Two Please!</p>
                </div>

                <!-- http://www.java2s.com/Tutorial/Java/0360__JSP/OutputResultSet.htm -->
                <TABLE class="w3-table w3-striped w3-bordered w3-border" BORDER="1" style = "margin-left: 0.5cm">

                    <tr>
                        <td>Select</td>
                        <td>TableName</td>
                        <td>SiteID</td>
                        <td>ModelID</td>
                        <td>SensorID</td>
                        <td>Username</td>
                        <td>SensorType</td>
                    </tr>

                    <% while (resultset.next()) {
                    %>       

                    <tr>
                        <td><input type="checkbox" name="tables" value="<%= resultset.getString(1)%>"></td>
                        <td><%= resultset.getString(1)%></td>
                        <td><%= resultset.getString(2)%></td>
                        <td><%= resultset.getString(3)%></td>
                        <td><%= resultset.getString(4)%></td>
                        <td><%= resultset.getString(5)%></td>
                        <td><%= resultset.getString(6)%></td>
                    </tr>
                    <% }%>


                </TABLE>
                <br><br>
                <div class="w3-row-padding">
                    <div class="w3-half">
                        <BR>
                        <p><b>Start Date:</b></p>
                        <input style = "margin-bottom: 1cm; width: 8cm;" 
                               type="date" class="w3-btn w3-blue-grey w3-center" name="start_date" value="StartDate" />
                        <BR>
                    </div>
                    <div class = "w3-half">
                        <BR>
                        <p><b>Choose Start Date Time:</b></p>
                        <input style = "margin-bottom: 1cm; width: 8cm;" 
                               type="time" class="w3-btn w3-blue-grey w3-center" name="start_time" value="start_time" />
                        <BR>
                    </div>
                </div>

                <div class="w3-row-padding">
                    <div class ="w3-half">
                        <BR>
                        <p><b>End Date:</b><BR></p>
                        <input style = "margin-bottom: 1cm; width: 8cm;" 
                               type="date" class="w3-btn w3-blue-grey w3-center" name="end_date" value="EndDate" />
                        <BR>
                    </div>
                    <div class = "w3-half">
                        <BR>
                        <p><b>Choose End Date Time:</b></p>
                        <input style = "margin-bottom: 1cm; width: 8cm;" 
                               type="time" class="w3-btn w3-blue-grey w3-center" name="end_time" value="end_time" />
                        <BR>
                    </div>
                </div>

                <BR><BR>
                <div>
                    <label for ="letter">Include Yellow flag?</label>
                    <select name ="flag">
                        <option value="yes"> Include</option>
                        <option value="no"> Do not include</option>
                    </select>
                    <BR><BR>
                </div>

                <div>
                    <input style = "margin-left: 18cm; margin-bottom: 2cm; width: 3cm;" 
                           type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Previous" />

                    <input style = " margin-bottom: 2cm; width: 3cm;" 
                           type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Query" />
                </div>



        </form>
        <script type="text/javascript">

//Syntax: checkboxlimit(checkbox_reference, limit)
            checkboxlimit(document.forms.querysitesensor.tables, 2)

        </script>

    </body>
</html>
