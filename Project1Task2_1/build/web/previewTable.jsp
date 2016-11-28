<%-- 
    Document   : previewTable
    Created on : Nov 4, 2016, 12:46:52 AM
    Author     : NaTT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.sql.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@page  import="Capstone.DBConnectionManager" %>


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
        <title>Create Table 2</title>
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
            <span>Log-in</span> &nbsp;| &nbsp;
            <span>Upload</span> &nbsp;| &nbsp;
            <span>Query/Download</span> &nbsp;| &nbsp;
            <span>Create Table</span> &nbsp;| &nbsp;
            <span>Admin</span> &nbsp;| &nbsp;
            <span>About</span>
        </div>
        <!-- End of header-->
        
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Query/Download</h1>
        </div>

        <div class="w3-container">
          <p>Table Preview.</p>
        </div>
               
        <!-- http://www.java2s.com/Tutorial/Java/0360__JSP/OutputResultSet.htm -->
        <div class="w3-responsive" style = "margin-left: 0.5cm">
            <TABLE class="w3-table w3-striped w3-bordered w3-border" BORDER="1">
                <TR>
                    <% for(int i=1; i<=columnCount; i++){
                        out.println("<th>"+  rsmd.getColumnName(i) + "</th>");
                    } 
                    %>
                </TR>
                <%  int previewLimit=0;
                    while(resultset.next()){ %>
                <TR>
                    <%  if(previewLimit==20)
                            break;
                        for(int i=1; i<=columnCount; i++){
                        out.println("<td>"+  resultset.getString(i) + "</td>");
                        previewLimit++;
                    } 
                    %>
                </TR>
                <% } %>
            </TABLE>
        </div>
        
        
        <form action="Palindrome" method="GET" style = "margin-left: 0.25cm">    
            <div class="w3-row-padding">
                    

                    <br><br><br>
                <input style = "margin-left: 0cm; margin-bottom: 1cm; width: 2.5cm;" 
                       type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Previous"/> 
                <input style = "margin-left: 0.25cm; margin-bottom: 1cm; width: 2.5cm;" 
                   type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Next" />
            </div>
            <br><br>
        </form>


        
        <div class="w3-container w3-bottom" style="height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <span>Copy Right - Heinz College</span>
        </div>

    </body>
</html>
