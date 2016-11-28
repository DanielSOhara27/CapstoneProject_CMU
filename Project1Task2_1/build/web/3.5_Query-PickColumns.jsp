
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="javax.xml.parsers.*"%>
<%@page import="org.w3c.dom.*"%>
<%@page import="org.xml.sax.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%  
   // create a list for testing
   int colNum=4;
   List<String> list = new ArrayList<String>();
   for(int i=1; i<=colNum; i++){
       for(int j=1;j<=4;j++){
           switch (j){
               case 1: list.add("col"+i+"_colName");break;
               case 2: list.add("col"+i+"_dataType");break;
               case 3: list.add("col"+i+"_maxLength");break;
               case 4: list.add("col"+i+"_relevant");break;
           }
       }
   }
   session.setAttribute("colNum",colNum);
   pageContext.setAttribute("list", list);
   
    String xmlInput = "";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // use the factory to create a documentbuilder
        Document apixml = null;
        try {
            // parse xml
            DocumentBuilder builder = factory.newDocumentBuilder();
            apixml = (Document) builder.parse(new ByteArrayInputStream(xmlInput.getBytes()));
            Element columns = apixml.getDocumentElement();
            //title = recipe1.getElementsByTagName("title").item(0).getTextContent();
            //ingredients = recipe1.getElementsByTagName("ingredients").item(0).getTextContent();
            //error = recipe1.getElementsByTagName("error").item(0).getTextContent().trim();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


%>
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
            <span><a href='4.0_CreateTable-Upload.jsp'>Create Table</a></span> &nbsp;| &nbsp;
            <span><a href='5.0_Admin-Choose.jsp.jsp'>Admin</span> &nbsp;| &nbsp;
            <span><a href='6.0_About.jsp'>About</a></span>
        </div>
        <!-- End of header-->
        
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Pick Columns</h1>
        </div>

        <div class="w3-container">
          <p>Pick all the columns you wish to have in your final file. </p>
        </div>
               
        <form action="Palindrome" method="GET" style = "margin-left: 0.25cm">    
            <div class="w3-row-padding" >   
                <%
                    int count2 = 1;
                    for (int i = 1; i <= list.size(); i++) {
                        if ((i % 4) == 1) {
                            out.print("Column " + count2 + ": <input name=\"" + list.get(i - 1) + "\"onfocus=\"if (this.value=='" + list.get(i - 1) + "') this.value='';\" type=\"text\" value=\"" + list.get(i - 1) + "\"style=\"color: grey\"disabled/> ");
                            count2 = count2 + 1;
                        }
                        if ((i % 4) == 0) {
                             out.print("<input type=\"checkbox\" name=\"" + list.get(i-1) + "\" value=\"entryYes\"><BR> ");  
                        }
                    }
                %>
            </div>
                    <BR><BR>
                    <div>
                        <input style = "margin-left: 0cm; margin-bottom: 1cm; width: 2.5cm;" 
                               type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Previous"/> 
                        <input style = "margin-left: 0.25cm; margin-bottom: 1cm; width: 2.5cm;" 
                               type="submit" class="w3-btn w3-blue-grey w3-center" name="button" value="Next" />
                    </div>
            <br><br>
        </form>


        <div class="w3-container w3-bottom" style="margin-top: 0.5cm; height: 1.3cm; line-height: 1.3cm; background-color: #533678;color: white; ">
            <center><span>Woodland Road | Pittsburgh, PA 15232 | Main: 412-365-1100 | Admission: 800-837-1290</span></center>
        </div>

    </body>
</html>
