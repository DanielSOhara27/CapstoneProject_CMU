<%@page import="java.util.*" %>
<%@page import="java.sql.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@page  import="Capstone.DBConnectionManager" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
    DBConnectionManager DBManager = DBConnectionManager.getInstance();
    Connection connection = DBManager.getConnection();

    Statement statement = connection.createStatement() ;
    //ref: http://stackoverflow.com/questions/5003142/show-jdbc-resultset-in-html-in-jsp-page-using-mvc-and-dao-pattern
//ResultSet resultset = (ResultSet)request.getAttribute("queryResult");
ResultSet resultset = statement.executeQuery("SELECT TimeLX, `BV (Volts)`, `T (deg C)`,`DO (mg/l)`,`Q ()`,minute,hour,mday,mon,year,yday,`SourceFile` FROM `Ryan_BRCR01_Phizer_7392-960779_DataTable`");
ResultSetMetaData rsmd = resultset.getMetaData();
int columnCount = rsmd.getColumnCount();

// The column count starts from 1
//for (int i = 1; i <= columnCount; i++ ) {
  //String name = rsmd.getColumnName(i);
//}
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
            <span><a href='1.1_login.jsp'>Log-in</a></span> &nbsp;| &nbsp;
            <span><a href='2.1_Upload-ChooseType.jsp'>Upload</a></span> &nbsp;| &nbsp;
            <span><a href='3.1_Query-ChooseType.jsp'>Query/Download</a></span> &nbsp;| &nbsp;
            <span><a href='4.0_CreateTable-Upload.jsp'>Create Table</a></span> &nbsp;| &nbsp;
            <span><a href='5.0_Admin-Choose.jsp.jsp'>Admin</span> &nbsp;| &nbsp;
            <span><a href='6.0_About.jsp'>About</a></span>
        </div>
        <!-- End of header -->
        
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Pick only columns you want.</h1>
        </div>

        <div class="w3-container">
            <p>Please choose all columns that apply. <BR> </p>
        </div>
        

        
        <div>
        <input style = "margin-left: 18cm; margin-bottom: 2cm; width: 3cm;" 
               type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Previous" />
        
        <input style = " margin-bottom: 2cm; width: 3cm;" 
               type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Next" />
        </div>
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>

    </body>
</html>

