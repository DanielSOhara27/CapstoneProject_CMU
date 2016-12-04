<%-- 
    Document   : createTable_2
    Created on : Nov 4, 2016, 12:46:52 AM
    Author     : NaTT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="javax.xml.parsers.*"%>
<%@page import="org.w3c.dom.*"%>
<%@page import="org.xml.sax.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%  
        String xmlInput =  "<xmlInput>" +
            "<foo> 'x-y' </foo>"+
            "<NumCol> 5 </NumCol>"+
            "<Column1> Column1 a </Column1>"+
            "<Column2> Column2 b </Column2>"+
            "<Column3> Column3 c </Column3>"+
            "<Column4> Column4 d </Column4>"+
            "<Column5> Column5 e </Column5>"+
        "</xmlInput>";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // use the factory to create a documentbuilder
        Document apixml = null;
        List<String> columnList = new ArrayList<String>();
        int numColumns=0;
        try {
            // parse xml
            DocumentBuilder builder = factory.newDocumentBuilder();
            apixml = (Document) builder.parse(new ByteArrayInputStream(xmlInput.getBytes()));
            // parse xml
            NodeList parsedinput = apixml.getElementsByTagName("xmlInput");
            Element input = (Element) parsedinput.item(0);
            String numColstr = input.getElementsByTagName("NumCol").item(0).getTextContent();
            numColumns = Integer.parseInt(numColstr.trim());
            //System.out.println("NumCol is " + numColumns);

            for(int i = 0; i < numColumns; i++){
                String y = Integer.toString(i + 1);
                String columnNames = input.getElementsByTagName("Column"+ y).item(0).getTextContent();
                columnList.add(columnNames);
                //System.out.println("columnName"+ i + ": " + columnNames);
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
   List<String> list = new ArrayList<String>();
   for(int i=1; i<=numColumns; i++){
       for(int j=1;j<=8;j++){
           switch (j){
               case 1: list.add("col"+i+"_missing");break;
               case 2: list.add("col"+i+"_range");break;
               case 3: list.add("col"+i+"_lowerThan");break;
               case 4: list.add("col"+i+"_andOr");break;
               case 5: list.add("col"+i+"_greaterThan");break;
               //case 6: list.add("col"+i+"_variance");break;
               //case 7: list.add("col"+i+"_sign");break;
               //case 8: list.add("col"+i+"_value");break;
           }
       }
   }
   session.setAttribute("option",5);
   session.setAttribute("colNum",numColumns);
   pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Table</title>
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
    </head>
    
    <style> 
        mark {
            background-color: yellow;
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
            <span><a href='1.1_login.jsp'>Log-in</a></span> &nbsp;| &nbsp;
            <span><a href='2.1_Upload-ChooseType.jsp'>Upload</a></span> &nbsp;| &nbsp;
            <span><a href='3.1_Query-ChooseType.jsp'>Query/Download</a></span> &nbsp;| &nbsp;
            <span><a href='4.0_CreateTable-initialize.jsp'>Create Table</a></span> &nbsp;| &nbsp;
            <span><a href='5.0_Admin-Choose.jsp.jsp'>Admin</a></span> &nbsp;| &nbsp;
            <span><a href='6.0_About.jsp'>About</a></span>
        </div>
        <!-- End of header-->
        
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Create Table</h1>
        </div>

        <div class="w3-container">
           <p>Your new table contains below columns. Please input the data validation rules for <mark><b>YELLOW</b></mark> flag (optional).</p>
        </div>
               
        <form action="CreateTableForm" method="POST" style = "margin-left: 0.25cm">    
            <div class="w3-row-padding">
                    
                
                
                <%
                    int count=1;
                  out.print("<b>Column#: Column Names &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp &nbsp &nbsp Remove Missing Values  &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp Numeric Range </b><BR>");
                  for(int i=1; i<=list.size();i++){
                    if((i % 5)==1)
                    {out.print("Column "+ count +": <input name=\"" + list.get(i-1) + "\"onfocus=\"if (this.value=='"+ columnList.get((i%5)*count-1) +"') this.value='';\" type=\"text\" value=\""+columnList.get((i%5)*count-1)+"\"style=\"color: grey\"/disabled>"
                            + " &nbsp <input name=\"" + list.get(i-1) +"\" type=\"checkbox\" value=\"true\" style=\"color: grey\"/><input name=\"" + list.get(i-1) +"\" type=\"hidden\" value=\"false\" style=\"color: grey\"/> Remove Missing Value | " );
                    count=count+1;}
                    if((i % 5)==2)
                    {out.print(" <input name=\"" + list.get(i-1) +"\" type=\"checkbox\" value=\"true\" style=\"color: grey\"/><input name=\"" + list.get(i-1) +"\" type=\"hidden\" value=\"false\" style=\"color: grey\"/> Range : &nbsp" );}
                    if((i % 5)==3)
                    {out.print("<input name=\"" + list.get(i-1) + "\"onfocus=\"if (this.value=='"+ list.get(i-1) +"') this.value='';\" type=\"text\" style=\"color: grey; width: 2.8cm;\"/> " );}
                    if((i % 5)==4)
                    //{out.print("<input name=\"" + list.get(i-1) + "\" type=\"text\" value=\""+ "DataType"+list.get(i-1)+"\"/>" );}
                    {out.print("<select name=\"" + list.get(i-1)+"\"class=\"form-control\" style=\"color: grey; height: 0.75cm;\"> "
                            + "<option disabled selected>AND/OR</option>"
                            + "<option value='and'>AND</option>"
                            + "<option value='or'>OR</option></select> ");}
                    if((i % 5)==0)
                    {out.print("<input name=\"" + list.get(i-1) + "\"onfocus=\"if (this.value=='"+ list.get(i-1) +"') this.value='';\" type=\"text\" style=\"color: grey; width: 2.8cm;\"/><BR>" );}
                                        
                  }
                %>

                    <br><br><br>
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
