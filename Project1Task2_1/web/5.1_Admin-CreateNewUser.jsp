
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
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
            <h1 style="color: #533678;"> Create New User</h1>
        </div>

        <div class="w3-container">
            <p>Enter information below to create a new user:</p>
        </div>
        
        <div class="w3-container">
        <form style="margin-left: 0.5cm;" action ="AddUserServlet" method ="POST">         
        <label for="letter">First Name: </label>
            <input type="text" name="firstName" value="" /><br><br><br>              
        <label for="letter">Last Name: </label>
            <input type="text" name="lastName" value="" /><br><br><br>              
        <label for="letter">Username: </label>
            <input type="text" name="username" value="" /><br><br><br>              
        <label for="letter">Password: </label>
            <input type="text" name="password1" value="" /><br><br><br>             
        <label for="letter">Confirm Password: </label>
            <input type="text" name="password2" value="" /><br><br><br>             
        <INPUT TYPE="radio" NAME="radios" VALUE="faculty" CHECKED>
            Faculty 
         
        <INPUT TYPE="radio" NAME="radios" VALUE="researcher">
             Researcher/Other
             <br><br><br>
            
        <input style = "margin-bottom: 1cm; width: 3cm;" 
                   type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Add User" />
        </div>
        
        <BR><BR>
        
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>
        <BR><BR>
    </body>
</html>

