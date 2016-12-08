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
    String xmlInput = (String) request.getAttribute("xmlInput");
    /*String xmlInput =  "<xmlInput>" +
            "<foo> 'x-y' </foo>"+
            "<NumCol> 5 </NumCol>"+
            "<Column1> Column1 a </Column1>"+
            "<Column2> Column2 b </Column2>"+
            "<Column3> Column3 c </Column3>"+
            "<Column4> Column4 d </Column4>"+
            "<Column5> Column5 e </Column5>"+
        "</xmlInput>"; */
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
        
           //int numColumns=4;
            List<String> list = new ArrayList<String>();
            for(int i=1; i<=numColumns; i++){
                for(int j=1;j<=5;j++){
                    switch (j){
                        case 1: list.add("col"+i+"_colName");break;
                        case 2: list.add("col"+i+"_alias");break;
                        case 3: list.add("col"+i+"aliasCheck");break;
                        case 4: list.add("col"+i+"_dataType");break;
                        case 5: list.add("col"+i+"datetime");break;
                    }
                }
            }
            int option = 3;
            session.setAttribute("option",3);
            session.setAttribute("colNum",numColumns);
            session.setAttribute("xmlInput", request.getAttribute("xmlInput"));
            pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Table</title>
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
        <script src="gen_validatorv4.js" type="text/javascript"></script>
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
        <!-- End of header-->
        
        <div class="w3-container w3-white" >
            <h1 style="color: #533678;"> Create Table</h1>
        </div>

        <div class="w3-container">
          <p>Your new table contains below columns. Please input the information for each column.</p>
          <p>If you would like to name your columns with a new name, re-name and check the box so that we know you would like to change the column name.</p>
          <p>If your date formate is UNIX (looks like this "1448057460") then, choose "Datetime [UNIX]". If you choose "Datetime [Custom]" from the data type drop box, please fill the text box with appropriate date format. For example, YYYY:MM:DD or YY:MM:DD HH:MM:SS. You must use <b> Colon (:)</b> to represent your format.</p>
        </div>
               
        <form id="form" action="CreateTableForm" method="POST" style="margin-left: 0.25cm">    
            <div class="w3-row-padding">
                    
                <%
                    int count=1;
                out.print("<b>Column#: Column Names &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Re-name?  &nbsp New Column Names &nbsp &nbsp &nbsp &nbsp Data Type &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp Custom Datetime Format</b><BR>");
                  for(int i=1; i<= list.size();i++){   
                    if((i % 5)==1)
                    {out.print("Column "+ count +": &nbsp<input name=\"" + list.get(i-1) + "\"onfocus=\"if (this.value=='"+ columnList.get((i%5)*count-1) +"') this.value='';\" type=\"text\" value=\""+columnList.get((i%5)*count-1)+"\"style=\"color: grey\"/disabled> |" );}
                    if((i % 5)==2)
                    {out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"checkbox\" name=\"" + list.get(i-1) + "\" value=\"true\"><input type=\"hidden\" name=\"" + list.get(i-1) + "\" value=\"false\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|" );}
                    if((i % 5)==3)
                    {out.print("&nbsp <input name=\"" + list.get(i-1) + "\"onfocus=\"if (this.value=='"+ columnList.get(((i%5)-2)*count-1) +"') this.value='';\" type=\"text\" value=\""+columnList.get(((i%5)-2)*count-1)+"\"style=\"color: grey\"/> |" ); count++;}
                    if((i % 5)==4)
                    {out.print("&nbsp<select name=\"" + list.get(i-1)+"\"class=\"form-control\" style=\"color: grey; height: 0.75cm;\"> "
                            + "<option value='000' disabled selected>Select Data Type</option>"
                            + "<option value='String'>String/Alphanumeric [ Abc123 ]</option>"
                            + "<option value='String'>Numeric with Symbol [ +3.00 ]</option>"
                            + "<option value='String'>Numeric without Symbol [ 3.00 ]</option>"
                            + "<option value='UNIX'>Datetime [UNIX]</option>"
                            + "<option value='CUSTOM-DATETIME'>Datetime [Custom Date and Time]</option>"
                            + "<option value='CUSTTOM-DATE'>Datetime [Custom Date Only]</option>"
                            + "<option value='CUSTTOM-TIME'>Datetime [Custom Time Only]</option>"
                            + "</select>");}
                    if((i%5)==0)
                    {out.print(" &nbsp<input name=\"" + list.get(i-1) + "\"onfocus=\"if (this.value=='n/a') this.value='';\" type=\"text\" value=\"n/a\"style=\"color: grey\"/><BR>");}
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
                

        
        <script type="text/javascript">
 var frmvalidator = new Validator("form");  
    <%   
    int counts=1;    
    for(int i=1; i<=list.size();i++){
        if((i%5)==2){
        out.print("frmvalidator.addValidation(\""+ list.get(i-1) +"\",\"req\",\"Please input the value in the [New Column Names] field of column: " + ((i%5)-1)*counts + "\");");}
        if((i % 5)==4)
        {out.print("frmvalidator.addValidation(\""+ list.get(i-1) +"\",\"dontselect=000\",\"Please choose the datatype in the [Data Type] field of column: " + ((i%5)-3)*counts + "\");");}
        if((i%5)==0)
        {out.print("frmvalidator.addValidation(\""+ list.get(i-1) +"\",\"req\",\"Please input [n/a] if the value in the [Custom Datetime Format] field of column: " + ((i%5)+1)*counts + " is not a Datetime format\");");
        counts++;}
     }
    %>
</script>

    </body>
</html>
