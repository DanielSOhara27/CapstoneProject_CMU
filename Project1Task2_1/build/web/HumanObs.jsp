<%-- 
    Document   : HumanObs
    Created on : Nov 1, 2016, 3:58:51 PM
    Author     : Ellie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Human Observation Form Selection</title>
    </head>
    <body>
        <h1>Please select the observation form to submit to: </h1>
        <form action ="SelectFormType" method ="GET">
           <label for="letter">List is as follows:</label> <br>
            <select class="form-control" name="observationType" id="menu">         
               <c:forEach var="observationType" items="${observationType}">
                   <option value="${observationType}">${observationType} 
                   </option>
               </c:forEach>
            </select>
                   <input type="submit" value="Go" /> 
            
            
        </form>
    </body>
</html>
