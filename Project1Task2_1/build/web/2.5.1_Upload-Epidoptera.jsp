
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
            <span><a href='4.0_CreateTable-Upload.jsp'>Create Table</a></span> &nbsp;| &nbsp;
            <span><a href='5.0_Admin-Choose.jsp.jsp'>Admin</span> &nbsp;| &nbsp;
            <span><a href='6.0_About.jsp'>About</a></span>
        </div>
        <!-- End of header -->
        
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Upload Observation.</h1>
        </div>

        <div class="w3-container">
            <p>Fill in appropriate observation form.</p>
        </div>
        
        
        <div>
            Genus: <input type ="text" name ="Genus"><BR>
            Species: <input type ="text" name ="Species"><BR>
            Common Name: <input type ="text" name ="CommonName"><BR>
          
            <label for="letter">Family:</label>
            <select name = "Family">
                <option value="Aceraceae">Arctiidae</option> 
                <option value="Crambidae">Crambidae</option>
                <option value="Depressariidae">Depressariidae</option>
                <option value="Erebidae">Erebidae</option>
                <option value="Geometridae">Geometridae</option>
                <option value="Lasiocampidae">Lasiocampidae</option>
                <option value="Limacodidae">Limacodidae</option>
                <option value="Noctuidae">Noctuidae</option>
                <option value="Notodontidae">Notodontidae</option>
                <option value="Oecophoridae">Oecophoridae</option>
                <option value="Pyralidae">Pyralidae</option>
                <option value="Saturniidae">Saturniidae</option>
                <option value="Sphingidae">Sphingidae</option>
                <option value="Tortricidae">Tortricidae</option>
                <option value="Yponomeutidae">Yponomeutidae</option>
            </select>

            </br>

            <label for="letter">Sex:</label>
            <select name = "Sex">
                <option value="Unknown">Unknown</option>
                <option value="Female">Female</option> 
                <option value="Male">Male</option>
            </select>
            
            <label for="letter">Time of Sampling:</label>
            <select name = "SampleTime">
                <option value="Morning">Morning</option>
                <option value="Evening">Evening</option> 
            </select>
            
            <BR>
            <p>Date of Reference Image:</p>
            <input style = "margin-bottom: 1cm; width: 5cm;" 
                   type="date" class="w3-btn w3-grey w3-center" name="refDate" value="refDate" /><BR>

            Identified by: <input type ="text" name ="identifiedBy"><BR>
            <label for="letter">Native Status:</label>
            <select name = "Native">
                <option value="Unknown">Unknown</option>
                <option value="Native">Native</option> 
                <option value="NonNatvie">Non-Native</option>
            </select><BR>
            
            <label for="letter">Collected and Pinned:</label>
            <select name = "Collected">
                <option value="Yes">Yes</option>
                <option value="No">No</option> 
            </select>
            <BR>
            <input type="submit" value="Submit" />   
            </BR>


        </div><BR><BR>
    
        
        <input style = "margin-bottom: 1cm; width: 3cm;" 
               type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Next" />
        
        <input style = "margin-bottom: 1cm; width: 3cm;" 
               type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Before" />
        <BR>
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>

    </body>
</html>

