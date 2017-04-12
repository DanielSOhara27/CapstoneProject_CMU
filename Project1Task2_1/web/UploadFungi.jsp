
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
    <body>        <!-- Start header -->
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
            <span><a href='login.jsp'>Log-in</a></span> &nbsp;| &nbsp;
            <span><a href='UploadHomePage.jsp'>Upload</a></span> &nbsp;| &nbsp;
            <span><a href='QueryHomePage.jsp'>Query/Download</a></span> &nbsp;| &nbsp;
            <span><a href='4.0_CreateTable-initialize.jsp'>Create Table</a></span> &nbsp;| &nbsp;
            <span><a href='5.0_Admin-Choose.jsp.jsp'>Admin</a></span> &nbsp;| &nbsp;
            <span><a href='6.0_About.jsp'>About</a></span> &nbsp;| &nbsp;
            <span><a href='logout.jsp'>Log out</a></span>
                                    <%
            if(request.getSession().getAttribute("Username") != null){
                out.print("<span align=\"right;\"> (User: "+request.getSession().getAttribute("Username").toString()+" )</span>");
            } 
            %>
        </div>
        <!-- End of header -->
        
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Fungi Upload Observation.</h1>
        </div>

        <%
            if(request.getAttribute("message")!=null)
               out.println("<td>" + request.getAttribute("message") + "</td>");
        %>
        
        <div class="w3-container">
            <p>Fill in appropriate observation form.</p>
        </div>
        
       <form action ="FungiObservationServlet" method ="POST">        
       <div class="w3-row-padding" style = "margin-left: 0.5cm">
            <div class="w3-half">
            Genus: <input type ="text" style = "height: 0.75cm;"  name ="Genus"><BR><BR>
            Species: <input type ="text" style = "height: 0.75cm;"  name ="Species"><BR><BR>
            Common Name: <input type ="text" style = "height: 0.75cm;" name ="CommonName"><BR><BR>
          
            <label for="letter">Family:</label>
            <select name = "Family" style = "height: 0.75cm;" >
                <option value="Amanitaceae">Amanitaceae</option> 
                <option value="Auriscalpiaceae">Auriscalpiaceae</option>
                <option value="Boletaceae">Boletaceae</option>               
                <option value="Cantharellaceae">Cantharellaceae</option>
                <option value="Cortinariaceae">Cortinariaceae</option>              
                <option value="Fomitopsidaceae">Fomitopsidaceae</option>
                <option value="Ganodermataceae">Ganodermataceae</option> 
                <option value="Hygrophoropsidaceae">Hygrophoropsidaceae</option>
                <option value="Hymenochaetaceae">Hymenochaetaceae</option>
                <option value="Hypocreaceae">Hypocreaceae</option>
                <option value="Meripilaceae">Meripilaceae</option>
                <option value="Phallaceae">Phallaceae</option>
                <option value="Pleurotaceae">Pleurotaceae</option>
                <option value="Polyporaceae">Polyporaceae</option>
                <option value="Russulaceae">Russulaceae</option>
                <option value="Tricholomataceae">Tricholomataceae</option>
                <option value="Xylariaceae">Xylariaceae</option>
            </select><BR><BR>
            
            
            <label for="letter">Feeding Type: </label>
            
            <select name = "Feeding" style = "height: 0.75cm;">
                <option value="Saprotrophic">Saprotrophic</option>
                <option value="Parasitic">Parasitic</option> 
                <option value="Mycorrhizal">Mycorrhizal</option> 
                <option value="Ecto-mycorrhizal">Ecto-mycorrhizal</option> 
                <option value="Arbuscular mycorrhizal">Arbuscular mycorrhizal</option> 
                <option value="Unspedified">Unspedified</option> 
            </select><BR><BR>
            </div>
            <div class="w3-half">
            Identified by: <input type ="text"  style = "height: 0.75cm;" name ="identifiedBy"><BR><BR>
            <p>Date of Discovery:</p>
            <input style = "height: 0.75cm;"
                   type="date" class="w3-btn w3-grey w3-center" name="refDate" value="refDate" /><BR><BR>

            
            <label for="letter">Collected and Archived: </label>
            <select name = "Collected" style = "height: 0.75cm;">
                <option value="Yes">Yes</option>
                <option value="No">No</option> 
            </select>
            </div>
        </div>
            <BR><BR>
            <input style = "margin-left: 20cm; width: 3cm;" 
               type="submit" class="w3-btn w3-blue-grey w3-center" value="Submit" /> 
            </BR>
            
        
        <BR><BR>
        
       </form>
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>

    </body>
</html>

