
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
            <span><a href='1.1_login.jsp'>Log-in</a></span> &nbsp;| &nbsp;
            <span><a href='2.1_Upload-ChooseType.jsp'>Upload</a></span> &nbsp;| &nbsp;
            <span><a href='3.1_Query-ChooseType.jsp'>Query/Download</a></span> &nbsp;| &nbsp;
            <span><a href='4.0_CreateTable-initialize.jsp'>Create Table</a></span> &nbsp;| &nbsp;
            <span><a href='5.0_Admin-Choose.jsp.jsp'>Admin</span> &nbsp;| &nbsp;
            <span><a href='6.0_About.jsp'>About</a></span>
        </div>
        <!-- End of header -->
        
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Choose time range.</h1>
        </div>

        <div class="w3-container">
            <p>Please choose a start and end date for your data. <BR> </p>
        </div>
        
        <div class="w3-row-padding">
            <div class="w3-half">
                <BR>
                <p><b>Start Date:</b></p>
                <input style = "margin-bottom: 1cm; width: 8cm;" 
                       type="date" class="w3-btn w3-blue-grey w3-center" name="start_date" value="StartDate" />
                <BR>
            </div>
            <div class = "w3-half">
                <BR>
                <p><b>Choose Start Date Time:</b></p>
                <input style = "margin-bottom: 1cm; width: 8cm;" 
                       type="time" class="w3-btn w3-blue-grey w3-center" name="start_time" value="start_time" />
                <BR>
            </div>
        </div>

        <div class="w3-row-padding">
            <div class ="w3-half">
                <BR>
                <p><b>End Date:</b><BR></p>
                <input style = "margin-bottom: 1cm; width: 8cm;" 
                       type="date" class="w3-btn w3-blue-grey w3-center" name="end_date" value="EndDate" />
                <BR>
            </div>
            <div class = "w3-half">
                <BR>
                <p><b>Choose End Date Time:</b></p>
                <input style = "margin-bottom: 1cm; width: 8cm;" 
                       type="time" class="w3-btn w3-blue-grey w3-center" name="end_time" value="end_time" />
                <BR>
            </div>
        </div>
        
        <BR><BR>
        <div>
        <label for ="letter">Include Yellow flag?</label>
        <select name ="flag">
            <option value="yes"> Include</option>
            <option value="no"> Do not include</option>
        </select>
        <BR><BR>
        </div>
        
        <div>
        <input style = "margin-left: 18cm; margin-bottom: 2cm; width: 3cm;" 
               type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Previous" />
        
        <input style = " margin-bottom: 2cm; width: 3cm;" 
               type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Query" />
        </div>
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>

    </body>
</html>

