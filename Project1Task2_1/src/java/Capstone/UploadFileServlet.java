/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capstone;
//Daniel Comment through netbeans
// Ellie comment netbeans 
// Anshu OK 
// Natt OK
//Sophie finally working.

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;







/**
 *
 * @author Ellie
 */
@WebServlet(name = "UploadFileServlet", urlPatterns = {"/UploadFileServlet"})
@MultipartConfig
public class UploadFileServlet extends HttpServlet {

    UploadFileApplication ufa = null;
    
        private static final long serialVersionUID = 1L;
     
    // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "C:\\Users\\Ellie\\Documents\\UploadTests";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      //  processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nextView = null;
        

        //process only if its multipart content

      if(ServletFileUpload.isMultipartContent(request)){

            try {
                System.out.println("gets to try");
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
     
                   for(FileItem item : multiparts){

                    if(!item.isFormField()){

                        String name = new File(item.getName()).getName();
                        System.out.println("name "+name);
                        item.write(new File("C:\\Users\\Ellie\\Documents\\UploadTests" + File.separator + name));
                        nextView = "index.jsp";
            
                    }
                    if(item.isFormField())
                    {
                     String name = item.getFieldName();
                     System.out.println("field name: " + name);
                     String value = item.getString();
                     System.out.println("value entered by user: " + value);
                    }
                   }
                  

                
        
 

               //File uploaded successfully

               request.setAttribute("message", "File Uploaded Successfully");

            } catch (Exception ex) {

               request.setAttribute("message", "File Upload Failed due to " + ex);
               System.out.println(ex);
               System.out.println("I am here in exception");
               nextView = "index.jsp";   

            }

 

        }else{
          System.out.println("I am here in else statement");

            nextView = "index.jsp";     
     }

 

            RequestDispatcher view = request.getRequestDispatcher(nextView);
            view.forward(request, response);

 

    }

    


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>.
    


}
