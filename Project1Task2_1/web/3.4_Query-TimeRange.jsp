
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Query</title>
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
            <h1 style="color: #533678;"> Choose time range.</h1>
        </div>

        <div class="w3-container">
            <p>Please choose a start and end date for your data. <BR> </p>
        </div>
        
        <div class="w3-container">
        <BR>
        <p>Start Date:</p><BR>
        <input style = "margin-bottom: 1cm; width: 8cm;" 
               type="date" class="w3-btn w3-blue-grey w3-center" name="start_date" value="StartDate" />
        <BR>
        <p>End Date:<BR></p>
        <input style = "margin-bottom: 1cm; width: 8cm;" 
               type="date" class="w3-btn w3-blue-grey w3-center" name="end_date" value="EndDate" />
        </div>
        <BR><BR>
        <input style = "margin-bottom: 1cm; width: 3cm;" 
               type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Previous" />
        
        <input style = "margin-bottom: 1cm; width: 3cm;" 
               type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Next" />
        
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>

    </body>
</html>

