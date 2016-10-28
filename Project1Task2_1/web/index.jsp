<%-- 
    Document   : index
    Created on : Jan 31, 2016, 10:11:11 PM
    Author     : Ellie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Falk School Data Portal</title>
    </head>
    <body>
        <h1 style="color:purple;"><b>Chatham University Falk School of Sustainability & Environment</b></h1>
        <form action ="UploadDownloadServlet" method ="GET">   
        <h1 style="color:purple;">Data Portal</h1>     
        <label for="letter">Please select operation:</label> <br>
        <select name ="operation">
                <option value="DownloadData">Download Data</option>            
                <option value="UploadFiles">Upload Files</option>
                <option value="HumanObservation">Human Observation Submittal</option>
                <option value="CreateTableJSP">Create Table</option>
        </select>
        <input type="submit" value="Go" />
        <br><br><br>
        <center>
        <img src = "ehc.png">
        </center>
    </form>

    </body>
</html>
