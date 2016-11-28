<%-- 
    Document   : VascularPlantObs
    Created on : Nov 7, 2016, 2:51:06 PM
    Author     : Ellie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vascular Plant Observation</title>
    </head>
    <body>
         <form action ="VascularPlantObsServlet" method ="POST"> 
        <h1> Please submit your Vascular Plant Observation below:</h1>
       
        Species: <input type ="text" name ="Species">
        </br>
        
        <label for="letter">Common Name:</label>
         <select name = "Family">
            <option value="Aceraceae">Aceraceae</option> 
            <option value="Amaryllidaceae">Amaryllidaceae</option> 
            <option value="Anacardiaceae">Anacardiaceae</option> 
            <option value="Apocynaceae">Aceraceae</option> 
            <option value="Araceae">Araceae</option>
            <option value="Balsamaceae">Balsamaceae</option> 
            <option value="Berberidaceae">Berberidaceae</option> 
            <option value="Betulaceae">Betulaceae</option> 
            <option value="Brassicaceae">Brassicaceae</option> 
            <option value="Caryophyllaceae">Caryophyllaceae</option> 
            <option value="Commelinaceae">Commelinaceae</option> 
            <option value="Ericaceae">Ericaceae</option>
            <option value="Fabaceae">Fabaceae</option> 
            <option value="Fagaceae">Fagaceae</option> 
            <option value="Geraniaceae">Geraniaceae</option> 
            <option value="Ginkoaceae">Ginkoaceae</option> 
            <option value="Juglandaceae">Juglandaceae</option> 
            <option value="Lauraceae">Lauraceae</option> 
            <option value="Magnoliaceae">Magnoliaceae</option> 
            <option value="Pinaceae">Pinaceae</option> 
            <option value="Polygonaceae">Polygonaceae</option> 
            <option value="Rosaceae">Rosaceae</option> 
            <option value="Sapindaceae">Sapindaceae</option> 
            <option value="Simaroubaceae">Simaroubaceae</option> 
         </select>
        
        </br>
        
        <label for="letter">Vegetation Type:</label>
         <select name = "VegetationType">
             <option value ="herb"> Herb</option>
             <option value ="shrub"> Shrub</option>
             <option value="tree"> Tree</option>
             <option value="vine"> Vine</option>
         </select>
        
        </br>
        
        <label for ="letter">Habitat:</label>
        <select name ="Habitat">
            <option value="forest"> Forest</option>
            <option value="meadow"> Meadow</option>
            <option value="edge"> Edge</option>
            <option value="disturbed"> Disturbed</option>
        </select>
        
        </br>
        <label for="letter">Origin:</label>
        <select name="Origin">
            <option value="wild"> Wild</option>
            <option value="planted"> Planted</option>
        </select>
        
        </br>
        <label for="letter">Native Status:</label>
        <select name="NativeStatus">
            <option value="native"> Native</option>
            <option value="nonnative">Non-Native</option>
            <option value="invasiv">Invasive</option>
        </select>
        
        </br>
         Date: <input type ="text" name ="Date">
        </br>
        Personnel Name: <input type ="text" name ="PersonnelName">
        </br> 
      <input type="submit" value="Submit" />      
    </body>
</html>
























