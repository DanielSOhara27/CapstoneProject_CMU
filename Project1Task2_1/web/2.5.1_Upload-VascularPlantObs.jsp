<!--2.5.1_Upload-VascularPlantObs-->


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload</title>
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
            <span><a href='1.1_login.jsp'>Log-in</a></span> &nbsp;| &nbsp;
            <span><a href='2.1_Upload-ChooseType.jsp'>Upload</a></span> &nbsp;| &nbsp;
            <span><a href='3.1_Query-ChooseType.jsp'>Query/Download</a></span> &nbsp;| &nbsp;
            <span><a href='4.0_CreateTable-initialize.jsp'>Create Table</a></span> &nbsp;| &nbsp;
            <span><a href='5.0_Admin-Choose.jsp.jsp'>Admin</a></span> &nbsp;| &nbsp;
            <span><a href='6.0_About.jsp'>About</a></span>
        </div>
        <!-- End of header -->
        
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Vascular Plant Observation.</h1>
        </div>

        <div class="w3-container">
            <p>Fill in appropriate observation form for Vascular Plant Observation.</p>
        </div>
        
        
        <div>
            Species: <input type ="text" style = "height: 0.75cm;" name ="Species"><BR><BR>
          
            <label for="letter">Common Name:</label>
            <select name = "Family" style = "height: 0.75cm;" >
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
            </select><BR><BR>

            <label for="letter">Vegetation Type:</label>
            <select name = "VegetationType" style = "height: 0.75cm;" >
                <option value ="herb"> Herb</option>
                <option value ="shrub"> Shrub</option>
                <option value="tree"> Tree</option>
                <option value="vine"> Vine</option>
            </select><BR><BR>

            <label for ="letter">Habitat:</label>
            <select name ="Habitat" style = "height: 0.75cm;" >
                <option value="forest"> Forest</option>
                <option value="meadow"> Meadow</option>
                <option value="edge"> Edge</option>
                <option value="disturbed"> Disturbed</option>
            </select><BR><BR>
            
            <label for="letter">Origin:</label>
            <select name="Origin" style = "height: 0.75cm;" >
                <option value="wild"> Wild</option>
                <option value="planted"> Planted</option>
            </select><BR><BR>
            
            <label for="letter">Native Status:</label>
            <select name="NativeStatus" style = "height: 0.75cm;" >
                <option value="native"> Native</option>
                <option value="nonnative">Non-Native</option>
                <option value="invasiv">Invasive</option>
            </select><BR><BR>
            
            Date: <input type ="text" style = "height: 0.75cm;" name ="Date">
            <BR><BR>
            Personnel Name: <input type ="text" style = "height: 0.75cm;" name ="PersonnelName">
            <BR><BR>
                    <input style = "margin-bottom: 1cm; width: 3cm;" 
               type="submit" class="w3-btn w3-blue-grey w3-center" value="Submit" /> 
            </BR>
        </div><BR><BR>
        <BR>
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>

    </body>
</html>

