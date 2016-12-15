<%-- 
    Document   : result
    Created on : Feb 1, 2016, 8:18:26 PM
    Author     : Ellie
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="capstone.connection.DBConnectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    DBConnectionManager DBManager = DBConnectionManager.getInstance();
    Connection connection = DBManager.getConnection();
    Statement statement = connection.createStatement();
    //String sensorType = request.getParameter("sensorType");
    //String sensorType = "DO";
    ResultSet resultset = statement.executeQuery("SELECT `SensorType`,`TableName`,`SiteID`,`ModelID`,`SensorID`,`Username` FROM `MappingTable`");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
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
            <span><a href='1.1_login.jsp'>Log-in</a></span> &nbsp;| &nbsp;
            <span><a href='2.1_Upload-ChooseType.jsp'>Upload</a></span> &nbsp;| &nbsp;
            <span><a href='3.1_Query-ChooseType.jsp'>Query/Download</a></span> &nbsp;| &nbsp;
            <span><a href='4.0_CreateTable-initialize.jsp'>Create Table</a></span> &nbsp;| &nbsp;
            <span><a href='5.0_Admin-Choose.jsp.jsp'>Admin</a></span> &nbsp;| &nbsp;
            <span><a href='6.0_About.jsp'>About</a></span>
        </div>
        <!-- End of header -->
       
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Upload Form</h1>
        </div>

        <%
            if(request.getAttribute("message")!=null)
               out.println("<td>" + request.getAttribute("message") + "</td>");
        %>
        
        <div class="w3-container">
          <p>Please submit your zipped file here: </p>
        </div>
        
        <form style="margin-left: 0.5cm;margin-right: 2 cm" action ="UploadFileServlet" method ="POST" enctype="multipart/form-data">  
            
            <input type="file" name="file" /> <br/><br>
            
            <TABLE class="w3-table w3-striped w3-bordered w3-border" BORDER="1" style = "margin-left: 0.5cm; margin-right: 2 cm">
                    <tr>
                        <td>Choose</td>
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
                        <td><input type="radio" name="tables" value="<%= resultset.getString(2)%>"></td>
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
            <input style = "margin-bottom: 1cm; width: 3cm;" 
                   type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Upload File" onclick="document.body.style.cursor='wait'; return true;" />
        </form>                
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>

    </body>
</html>

