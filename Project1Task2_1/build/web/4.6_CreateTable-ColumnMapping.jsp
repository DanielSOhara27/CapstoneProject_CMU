<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="javax.xml.parsers.*"%>
<%@page import="org.w3c.dom.*"%>
<%@page import="org.xml.sax.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%  
        String xmlInput =  "<xmlInput>"
	+"<foo> 'x-y' </foo>"
	+ "<bNumCol> 4 </bNumCol>"
	+ "<bColumn1> bColumn1 </bColumn1>"
	+ "<bColumn2> bColumn2 </bColumn2>"
	+ "<bColumn3> bColumn3 </bColumn3>"
	+ "<bColumn4> bColumn4 </bColumn4>"
	+ "<nNumCol> 5 </nNumCol>"
	+ "<nColumn1> nColumn1 </nColumn1>"
	+ "<nColumn2> nColumn2 </nColumn2>"
	+ "<nColumn3> nColumn3 </nColumn3>"
	+ "<nColumn4> nColumn4 </nColumn4>"
	+ "<nColumn5> nColumn5 </nColumn5>"
        + "</xmlInput>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // use the factory to create a documentbuilder
        Document apixml = null;
        List<String> BASEcolumnList = new ArrayList<String>();
        List<String> NEWcolumnList = new ArrayList<String>();
        int BASEnumColumns=0;
        int NEWnumColumns=0;
        try {
            // parse xml
            DocumentBuilder builder = factory.newDocumentBuilder();
            apixml = (Document) builder.parse(new ByteArrayInputStream(xmlInput.getBytes()));
            // parse xml
            NodeList parsedinput = apixml.getElementsByTagName("xmlInput");
            Element input = (Element) parsedinput.item(0);
            
            String bnumColstr = input.getElementsByTagName("bNumCol").item(0).getTextContent();
            BASEnumColumns = Integer.parseInt(bnumColstr.trim());
            //System.out.println("NumCol is " + numColumns);

            for(int i = 0; i < BASEnumColumns; i++){
                String y = Integer.toString(i + 1);
                String columnNames = input.getElementsByTagName("bColumn"+ y).item(0).getTextContent();
                BASEcolumnList.add(columnNames);
                System.out.println("BASEcolumnName"+ i + ": " + columnNames);
            }
            
            String nnumColstr = input.getElementsByTagName("nNumCol").item(0).getTextContent();
            NEWnumColumns = Integer.parseInt(nnumColstr.trim());
            //System.out.println("NumCol is " + numColumns);

            for(int i = 0; i < NEWnumColumns; i++){
                String y = Integer.toString(i + 1);
                String columnNames = input.getElementsByTagName("nColumn"+ y).item(0).getTextContent();
                NEWcolumnList.add(columnNames);
                System.out.println("NEWcolumnName"+ i + ": " + columnNames);
            }

            
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    
   // create a list for testing
   //int colNum=4;
   List<String> BASElist = new ArrayList<String>();
   List<String> NEWlist = new ArrayList<String>();
   for(int i=1; i<=BASEnumColumns; i++){
        BASElist.add("col"+i+"_basecolName");
   }
   
   for(int i=1; i<=NEWnumColumns; i++){
        for(int j=1;j<=2;j++){
           switch (j){
               case 1: NEWlist.add("col"+i+"_newcolName");break;
               case 2: NEWlist.add("col"+i+"_mapNumber");break;
           }
       }
   }
   session.setAttribute("option",6);
   session.setAttribute("colNum",BASEnumColumns);
   session.setAttribute("colNum",NEWnumColumns);
   pageContext.setAttribute("list", BASElist);
   pageContext.setAttribute("list", NEWlist);
   
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Table</title>
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
        <!-- End of header-->
        
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Create Table</h1>
        </div>

        <div class="w3-container">
          <p>Map your columns </p>
        </div>
               
        <form action="CreateTableForm" method="POST" style = "margin-left: 0.25cm">    
            <div class="w3-row-padding">
                <div class="w3-half">

                    <%
                        int count = 1;
                        for (int i = 1; i <= BASElist.size(); i++) {
                                out.print("Column " + count + ": <input name=\"" + BASElist.get(i - 1) + "\"onfocus=\"if (this.value=='" + BASEcolumnList.get(i-1) + "') this.value='';\" type=\"text\" value=\"" + BASEcolumnList.get(i-1) + "\"style=\"color: grey\"/disabled><br> ");
                                count = count + 1;
                        }
                    %>
                </div>
                <div class="w3-half">
                    <%
                        int count2 = 1;
                        for (int i = 1; i <= NEWlist.size(); i++) {
                            if ((i % 2) == 1) {
                                out.print("Column " + count2 + ": <input name=\"" + NEWlist.get(i - 1) + "\"onfocus=\"if (this.value=='" + NEWcolumnList.get((i%2)*count2-1) + "') this.value='';\" type=\"text\" value=\"" + NEWcolumnList.get((i%2)*count2-1) + "\"style=\"color: grey\"/disabled> ");
                                count2 = count2 + 1;
                            }
                            if ((i % 2) == 0){
                                out.print("<input  name=\"" + NEWlist.get(i - 1) + "\"><br>");
                            }
                        }
                    %>
                </div>
            
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

