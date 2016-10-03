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
        <h1>Chatham University Falk School of Sustainability & Environment</h1>
        <form action ="UploadDownloadServlet" method ="GET">   
        <h1>Data Portal</h1>            
        <label for="letter">Please select operation:</label>
        <select name ="operation">
                <option value="DownloadData">Download Data</option>            
                <option value="UploadFiles">Upload Files</option>
                <option value="HumanObservation">Human Observation Submittal</option>                
        </select>
        <input type="submit" value="Go" />
        <br><br><br>
    </form>

    </body>
</html>
