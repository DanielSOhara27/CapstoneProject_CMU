    <%-- 
    Document   : result
    Created on : Feb 1, 2016, 8:18:26 PM
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
        <h1> This is the upload form</h1><br>
        <h2>Please submit your zipped file here: </h2>
        <form action ="uploadFile" method ="POST" enctype="multipart/form-data">  
            File:
            <input type="file" name="file" /> <br/>
            <input type="submit" value="Upload File" />
        </form>
   

    </body>
</html>
