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

import java.io.*;
import javax.servlet.http.*;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;


/**
 *
 * @author Ellie
 */
@WebServlet(name = "UploadFileServlet", urlPatterns = {"/UploadFileServlet"})
@MultipartConfig
public class UploadFileServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        
        // set destination place
        final String path = "C:\\Users\\Ellie\\Desktop";
        final Part filePart = request.getPart("file");
        System.out.println("here" + filePart);
        final String fileName = getFileName(filePart);
                
        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();
       
        
        out = new FileOutputStream(new File(path + File.separator + fileName));
        filecontent = filePart.getInputStream();
        System.out.println("hello" + filecontent);
        
                BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        
        String line;
        try
        {
            br = new BufferedReader(new InputStreamReader(filecontent));
            while((line = br.readLine()) != null)
            {
             sb.append(line);
             System.out.println(line);
            }
            
        
        }
        catch(IOException e)
        {
         e.printStackTrace();
        }
        
        
        
        int read = 0;
        final byte[] bytes = new byte[1024];
        while ((read = filecontent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        
        

        writer.println("New file " + fileName + " created at " + path);
        
     //   LOGGER.log(Level.INFO, "File{0}being uploaded to {1}", 
              //  new Object[]{fileName, path});
        if (out != null) {
            out.close();
        }
        if (filecontent != null) {
            filecontent.close();
        }
        if (writer != null) {
            writer.close();
        }
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
    
    
    
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
      //  LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}
