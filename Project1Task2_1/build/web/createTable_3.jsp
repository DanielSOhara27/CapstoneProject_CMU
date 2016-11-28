<%-- 
    Document   : createTable_3
    Created on : Nov 4, 2016, 12:46:52 AM
    Author     : NaTT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Table 3</title>
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
    </head>
    
    <style> 
        mark {
            background-color: red;
            color: black;
        }
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
            <h1 style="color: #533678;"> Create Table</h1>
        </div>
        
        <div class="w3-container">
            <p>Your new table contains 4 columns. Please input the data validation rules for <mark><b>RED</b></mark> flag (optional).</p>
          
        </div>
        
        <form action="Palindrome" method="GET" style = "margin-left: 0.25cm">    
            <div class="w3-row-padding">
                        
                <label for="letter"> Column 1: </label>
                &nbsp;<input type="checkbox" name="missing" value="missingCheck"> Remove Missing Value | 

                &nbsp;<input type="checkbox" name="range" value="rangeCheck"> Range :
                <input type="text" name="lowerThan" value="Lower Than" style="color: grey; width: 2.7cm;"/>
                <select name="and_or" class="form-control" style="color: grey; height: 0.78cm;">
                    <option disabled selected>AND/OR</option>
                    <option value='and'>AND</option>
                    <option value='or'>OR</option>
                </select> 
                <input type="text" name="greaterThan" value="Greater Than" style="color: grey;width: 2.7cm;"/> &nbsp;| 

                &nbsp;<input type="checkbox" name="variance" value="varianceCheck"> Consider Variance: 
                <select name="p_or_n" class="form-control" style="color: grey; height: 0.78cm;">
                    <option disabled selected>sign</option>
                    <option value='p'>+</option>
                    <option value='n'>-</option>
                    <option value='p_or_n'>+/-</option>
                </select> 
                <input type="text" name="varianceValue" value="Value" style="color: grey; width: 2.5cm;"/>
                <input type="text" name="varianceRow" value="# Rows to validate" style="color: grey; width: 4cm;"/><br>

                <!-------------------------------------------------------------->

                <label for="letter"> Column 2: </label>
                &nbsp;<input type="checkbox" name="missing" value="missingCheck"> Remove Missing Value | 

                &nbsp;<input type="checkbox" name="range" value="rangeCheck"> Range :
                <input type="text" name="lowerThan" value="Lower Than" style="color: grey; width: 2.7cm;"/>
                <select name="and_or" class="form-control" style="color: grey; height: 0.78cm;">
                    <option disabled selected>AND/OR</option>
                    <option value='and'>AND</option>
                    <option value='or'>OR</option>
                </select> 
                <input type="text" name="greaterThan" value="Greater Than" style="color: grey;width: 2.7cm;"/> &nbsp;| 

                &nbsp;<input type="checkbox" name="variance" value="varianceCheck"> Consider Variance: 
                <select name="p_or_n" class="form-control" style="color: grey; height: 0.78cm;">
                    <option disabled selected>sign</option>
                    <option value='p'>+</option>
                    <option value='n'>-</option>
                    <option value='p_or_n'>+/-</option>
                </select> 
                <input type="text" name="varianceValue" value="Value" style="color: grey; width: 2.5cm;"/>
                <input type="text" name="varianceRow" value="# Rows to validate" style="color: grey; width: 4cm;"/><br>
                 <!-------------------------------------------------------------->

                <label for="letter"> Column 3: </label>
                &nbsp;<input type="checkbox" name="missing" value="missingCheck"> Remove Missing Value | 

                &nbsp;<input type="checkbox" name="range" value="rangeCheck"> Range :
                <input type="text" name="lowerThan" value="Lower Than" style="color: grey; width: 2.7cm;"/>
                <select name="and_or" class="form-control" style="color: grey; height: 0.78cm;">
                    <option disabled selected>AND/OR</option>
                    <option value='and'>AND</option>
                    <option value='or'>OR</option>
                </select> 
                <input type="text" name="greaterThan" value="Greater Than" style="color: grey;width: 2.7cm;"/> &nbsp;| 

                &nbsp;<input type="checkbox" name="variance" value="varianceCheck"> Consider Variance: 
                <select name="p_or_n" class="form-control" style="color: grey; height: 0.78cm;">
                    <option disabled selected>sign</option>
                    <option value='p'>+</option>
                    <option value='n'>-</option>
                    <option value='p_or_n'>+/-</option>
                </select> 
                <input type="text" name="varianceValue" value="Value" style="color: grey; width: 2.5cm;"/>
                <input type="text" name="varianceRow" value="# Rows to validate" style="color: grey; width: 4cm;"/><br>
                <!-------------------------------------------------------------->

                <label for="letter"> Column 4: </label>
                &nbsp;<input type="checkbox" name="missing" value="missingCheck"> Remove Missing Value | 

                &nbsp;<input type="checkbox" name="range" value="rangeCheck"> Range :
                <input type="text" name="lowerThan" value="Lower Than" style="color: grey; width: 2.7cm;"/>
                <select name="and_or" class="form-control" style="color: grey; height: 0.78cm;">
                    <option disabled selected>AND/OR</option>
                    <option value='and'>AND</option>
                    <option value='or'>OR</option>
                </select> 
                <input type="text" name="greaterThan" value="Greater Than" style="color: grey;width: 2.7cm;"/> &nbsp;| 

                &nbsp;<input type="checkbox" name="variance" value="varianceCheck"> Consider Variance: 
                <select name="p_or_n" class="form-control" style="color: grey; height: 0.78cm;">
                    <option disabled selected>sign</option>
                    <option value='p'>+</option>
                    <option value='n'>-</option>
                    <option value='p_or_n'>+/-</option>
                </select> 
                <input type="text" name="varianceValue" value="Value" style="color: grey; width: 2.5cm;"/>
                <input type="text" name="varianceRow" value="# Rows to validate" style="color: grey; width: 4cm;"/><br>
                    
                <br><br><br>    
                <input style = "margin-left: 0cm; margin-bottom: 1cm; width: 2.5cm;" 
                   type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Previous" />
                <input style = "margin-left: 0.25cm; margin-bottom: 1cm; width: 2.5cm;" 
                   type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Next"/>
          
            </div>
            <br><br>
            
        </form>
  
        </div>

        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>

    </body>
</html>
