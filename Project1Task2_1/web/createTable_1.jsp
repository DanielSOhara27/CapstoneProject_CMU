<%-- 
    Document   : createTable_1
    Created on : Nov 4, 2016, 12:46:52 AM
    Author     : NaTT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Table 1</title>
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
        <!-- End of header -->
        
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Create Table</h1>
        </div>

        <div class="w3-container">
          <p>Welcome to create table page. Please fill in the fields below to create your table.</p>
        </div>
        
        <form action="Palindrome" method="GET">    
            <div class="w3-row-padding">

                <div class="w3-half">
                    <label for="letter"> Number of Columns: &nbsp; &nbsp; &nbsp;</label>
                    <input type="text" name="numColumn" value="" /><br><br>
                    <label for="letter"> Type of Delimeter: &nbsp; &nbsp; &nbsp; &nbsp; </label>
                    <input type="text" name="typeDelimeter" value="" /><br><br>
                    <label for="letter"> Comment Line Signifier: &nbsp;</label>
                    <input type="text" name="lineSignifier" value="" /><br><br>
                    <label for="letter"> Number of header's line: </label>
                    <input type="text" name="lineSignifier" value="" /><br><br>
                </div>
                <div class="w3-half">
                    <label for="letter"> Type of Sensor: &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <input type="text" name="typeSensor" value="" /><br><br>
                    <label for="letter"> Site Prefix: &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</label>
                    <input type="text" name="sitePrefix" value="" /><br><br>
                    <label for="letter"> Sensor Model: &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <input type="text" name="sensorModel" value="" /><br><br>
                    <label for="letter"> Sensor Manufacturer: </label>
                    <input type="text" name="manufacturer" value="" /><br><br>
                    <label for="letter"> Location: &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <input type="text" name="location" value="" /><br><br><br>
                </div>
                
            <input style = "margin-left: 0.25cm; margin-bottom: 1cm; width: 2.5cm;" 
                   type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Next" />
            </div>
        </form>
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>

    </body>
</html>

