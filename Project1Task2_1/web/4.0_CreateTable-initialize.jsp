<%-- 
    Document   : createTable_1
    Created on : Nov 4, 2016, 12:46:52 AM
    Author     : NaTT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% session.setAttribute("option",0);%>
<%-- <% session.setAttribute("Username", "Ryan1"); %> --%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Table</title>
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
        <script src="gen_validatorv4.js" type="text/javascript"></script>
    </head>
    
    <style> 
        span {
            display: inline-block;
            vertical-align: middle;
            line-height: normal;      
        }
    </style>   
    
    <body>
        <!--<h5>${option}</h5>
        <h5>${Username}</h5>
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
            <span><a href='6.0_About.jsp'>About</a></span> &nbsp;| &nbsp;
            <span><a href='logout.jsp'>Log out</a></span>
            
            <%
            if(request.getSession().getAttribute("Username") != null){
                out.print("<span align=\"right;\"> (User: "+request.getSession().getAttribute("Username").toString()+" )</span>");
            } 
            %>
            
            
        </div>
        <!-- End of header -->
        
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Create Table</h1>
        </div>

        <div class="w3-container">
          <p>Welcome to create table page. Please fill in the fields below to create your table.</p>
        </div>
        
        <form id="form" action="CreateTableForm" method="POST">    
            <div class="w3-row-padding">

                <div class="w3-half">
                    <label for="letter"> Number of Columns: &nbsp; &nbsp; &nbsp;</label>
                    <input type="text" name="numColumn" value="" /><br><br>
                    <label for="letter"> Type of Delimeter: &nbsp; &nbsp; &nbsp; &nbsp; </label>
                    <input type="text" name="typeDelimeter" value="" /><br><br>
                    <label for="letter"> Comment Line Signifier: &nbsp;</label>
                    <input type="text" name="commentSignifier" value="" /><br><br>
                    <label for="letter"> Number of header lines to skip: </label>
                    <input type="text" name="numHeader" value="" /><BR><BR>
                    <label for="letter"> Data generation frequency: </label>
                    <input type="text" name="rangeBTWreadings" value="" /><BR><BR>
                </div>
                <div class="w3-half">
                    <label for="letter"> Type of Sensor: &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <input type="text" name="typeSensor" value="" /><br><br>
                    <label for="letter"> Site Prefix: &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</label>
                    <input type="text" name="sitePrefix" value="" /><br><br>
                    <label for="letter"> Sensor ID: &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <input type="text" name="sensorModel" value="" /><br><br>
                    <label for="letter"> Sensor Manufacturer/Model: </label>
                    <input type="text" name="manufacturer" value="" /><br><br>
                    <label for="letter"> Location: &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <input type="text" name="location" value="" /><br><br><br>
                </div>
                
            <input style = "margin-left: 0.25cm; margin-bottom: 2cm; width: 2.5cm;" 
                   type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Next" />
            </div>
        </form>
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>
        <script  type="text/javascript">
         var frmvalidator = new Validator("form");
         frmvalidator.addValidation("numColumn","req","Please input the value in the \"Number of Columns\" field");
         frmvalidator.addValidation("numColumn","numeric","Please input \"Numeric\" value in the \"Number of Columns\" field");

         frmvalidator.addValidation("typeDelimeter","req","Please input the value in the \"Type of Delimeter\" field");
         frmvalidator.addValidation("typeDelimeter","maxlen=1","The value in \"Type of Delimeter\" should be 1 character long");

         
         frmvalidator.addValidation("commentSignifier","maxlen=1","The value in \"Comment Line Signifier:\" should be 1 character long");

         frmvalidator.addValidation("numHeader","req","Please input the value in the \"Number of header's line\" field");
         frmvalidator.addValidation("numHeader","numeric","Please input \"Numeric\" value in the \"Number of header's line\" field");

         frmvalidator.addValidation("rangeBTWreadings","req","Please input the value in the \"How often does the sensor read data\" field");
         frmvalidator.addValidation("rangeBTWreadings","numeric","Please input \"Numeric\" value in the \"How often does the sensor read data\" field");

         frmvalidator.addValidation("typeSensor","req","Please input the value in the \"Type of Sensor\" field");

         frmvalidator.addValidation("sitePrefix","req","Please input the value in the \"Site Prefix\" field");

         frmvalidator.addValidation("sensorModel","req","Please input the value in the \"Sensor Model\" field");

         frmvalidator.addValidation("manufacturer","req","Please input the value in the \"Sensor Manufacturer\" field");

         frmvalidator.addValidation("location","req","Please input the value in the \"Location\" field");

        </script>
    </body>
</html>


