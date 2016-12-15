<%-- 
    Document   : PreviewData
    Created on : Nov 4, 2016, 12:46:52 AM
    Author     : Anshu
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
    //ref: http://stackoverflow.com/questions/5003142/show-jdbc-resultset-in-html-in-jsp-page-using-mvc-and-dao-pattern
//ResultSet resultset = (ResultSet)request.getAttribute("queryResult");
    //ResultSet resultset = statement.executeQuery("SELECT TimeLX, `BV (Volts)`, `T (deg C)`,`DO (mg/l)`,`Q ()`,minute,hour,mday,mon,year,yday,`SourceFile` FROM `Ryan_BRCR01_Phizer_7392-960779_DataTable`");
    ResultSet resultset = statement.executeQuery("SELECT * FROM `" + request.getAttribute("previewTable") + "`");
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
        <script type="text/javascript">
            function SetAllCheckBoxes(FormName, FieldName, CheckValue)
            {
                if (!document.forms[FormName])
                    return;
                var objCheckBoxes = document.forms[FormName].elements[FieldName];
                if (!objCheckBoxes)
                    return;
                var countCheckBoxes = objCheckBoxes.length;
                if (!countCheckBoxes)
                    objCheckBoxes.checked = CheckValue;
                else
                    // set the check value for all check boxes
                    for (var i = 0; i < countCheckBoxes; i++)
                        objCheckBoxes[i].checked = CheckValue;
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
            <h1 style="color: #533678;"> Query/Download</h1>
        </div>

        <div class="w3-container">
        </div>
        
        <!-- http://www.java2s.com/Tutorial/Java/0360__JSP/OutputResultSet.htm -->
        <!--http://www.somacon.com/p117.php-->
        <form id= "querysitesensor" action="DownloadFileServlet" method="POST" name="previewdataform" style = "margin-left: 0.25cm"> 
            <div style="overflow:scroll;height:100%;width:100%;overflow:auto">
            <p>Data Preview. Select columns you want in the dataset.</p>
            <input type="button" onclick="SetAllCheckBoxes('previewdataform', 'previewTableHeader', true);" value="Check All!">
            <input type="button" onclick="SetAllCheckBoxes('previewdataform', 'previewTableHeader', false);" value="Uncheck All!">
            <input type="hidden" name="tempTable" value="<%=request.getAttribute("previewTable")%>">
            <input type="submit" name="Submit" value="Download">
            
                <table class="w3-table w3-striped w3-bordered w3-border" BORDER="1" style="margin-left: 1cm;">
                    <TR>
                        <% for (int i = 1; i <= columnCount; i++) {%>
                        <th><%=rsmd.getColumnName(i)%><br><input type="checkbox" name="previewTableHeader" value="<%= rsmd.getColumnName(i)%>"></th>
                            <%    }
                            %>
                    </TR>
                    <%  int previewLimit = 0;
                        while (resultset.next()) {
                            if (previewLimit == 10) {
                                break;
                            } %>
                    <TR>
                        <%
                            for (int i = 1; i <= columnCount; i++) {
                                out.println("<td>" + resultset.getString(i) + "</td>");
                            }
                            previewLimit++;
                        %>
                    </TR>
                    <% }%>
                </table>
            </div>
            
        </form>
    </body>
</html>
