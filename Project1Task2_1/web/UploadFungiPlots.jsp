
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
            <h1 style="color: #533678;"> Upload Fungi Plots Observation.</h1>
        </div>

        <div class="w3-container">
            <p>Fill in appropriate observation form for Fungi Plots.</p>
        </div>
        
        <form action ="FungiPlotsObservationServlet" method ="POST" style="margin-left:0.5cm;">
        <div class="w3-row-padding" style = "margin-left: 0.5cm">
        <div class="w3-half">
        Plot ID: <input type ="text" style = "height: 0.75cm;" name ="plotID"><BR><BR>
        Tree ID: <input type ="text" style = "height: 0.75cm;" name ="treeID"><BR><BR>
        Location: <input type ="text" style = "height: 0.75cm;" name ="location"><BR><BR>
        Date: <input type ="text" style = "height: 0.75cm;" name ="date"><BR><BR>
        Personnel: <input type ="text" style = "height: 0.75cm;" name ="personnel"><BR><BR>
        <label for="letter">Direction:</label>
        <select name = "direction" style = "height: 0.75cm;" >
            <option value="N">North</option> 
            <option value="S">South</option> 
            <option value="E">East</option> 
            <option value="W">West</option>
        </select><BR><BR>  
        </div>
        <div class="w3-half">
        Acer Count: <input type ="text" style = "height: 0.75cm;" name ="acerCount"><BR><BR>
        Largest Acer: <input type ="text" style = "height: 0.75cm;" name ="largestAcer"><BR><BR>
        Smallest Acer <input type ="text" style = "height: 0.75cm;" name ="smallestAcer"><BR><BR>
        Quercus Count: <input type ="text" style = "height: 0.75cm;" name ="quercusCount"><BR><BR>
        Largest Quercus: <input type ="text" style = "height: 0.75cm;" name ="largestQuercus"><BR><BR>
        Smallest Quercus: <input type ="text" style = "height: 0.75cm;" name ="smallestQuercus"><BR><BR>

        </div>
            <div>
              Notes: <input type ="text" style = "height:5cm; width:15cm;" name ="notes"><BR><BR>
            </div>
        </div><BR>
    
        
        <input style = "margin-bottom: 1cm; width: 3cm;" 
               type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Submit" />

        <BR>
        </form>
        
        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>
        <BR><BR>
    </body>
</html>

