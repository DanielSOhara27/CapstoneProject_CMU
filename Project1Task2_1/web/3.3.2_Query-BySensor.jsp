
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 



<%  
   // create a list for testing
   int NumSites=3;
   List<String> list = new ArrayList<String>();
   for(int i=1; i<=NumSites; i++){
      list.add("Sensor "+i);
   }

   pageContext.setAttribute("list", list);
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
          <p>There are 3 sensor types associated with "Site 1". Please select <b>a pair</b> of sensors that you want to query.</p>
        </div>
               
        <form action="Palindrome" method="GET" style = "margin-left: 0.25cm">    
            <div class="w3-row-padding">
                    
                <%
                  
                  for(int i=1; i<=list.size();i++){
                                      
                    out.print("<input type=\"checkbox\" name=\"" + list.get(i-1) + "\" value=\"entryYes\"> " + list.get(i-1) + "<br>");  
                  }
                %>

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
