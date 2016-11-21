
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log-In</title>
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
            <h1 style="color: #533678;"> Log-In</h1>
        </div>

        <div class="w3-container">
          <p>Log-In using your user id and password: </p>
        </div>
        
        <form style="margin-left: 0.5cm;" action ="uploadFile" method ="POST" enctype="multipart/form-data">  
            
            <label for="letter">Enter id here:</label>
            <input type="text" name="user_id" value="" /><br><br>        
            <label for="letter">Enter password here:</label>
            <input type="text" name="password" value="" /><br><br><br>           
            <input style = "margin-left: 0.25cm; margin-bottom: 1cm; width: 2.5cm;" 
                   type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Submit" />
        </form>                
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>

    </body>
</html>
