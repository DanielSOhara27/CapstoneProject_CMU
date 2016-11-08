package capstone;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ellie
 */
@WebServlet(name = "UploadDownloadServlet.java", urlPatterns = {"/UploadDownloadServlet"})
public class UploadDownloadServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    
    
    
    
    public void init ()
    {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           
            String selection = request.getParameter("selection");
                    
                // determine what type of device our user is
                String ua = request.getHeader("User-Agent");

                boolean mobile;
                // prepare the appropriate DOCTYPE for the view pages
                if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
                    mobile = true;
                    request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
                } else {
                    mobile = false;
                    request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
                }
            
            
            String nextView = null;
            try {
             
             if(request.getParameter("operation").equals("DownloadData"))
                     {
                         
                      // user directed to download page    
                     }
             if(request.getParameter("operation").equals("UploadFiles"))
             {
                 // user directed to upload page
                 nextView = "UploadFile.jsp";
             }
             
             if(request.getParameter("operation").equals("HumanObservation"))
             {
                 // user directed to upload page
                 nextView = "HumanObs.jsp";
             }
             
         }
             catch (NullPointerException | NumberFormatException e)
    {
        nextView = "index.jsp";
    }
              
    RequestDispatcher view = request.getRequestDispatcher(nextView);
    view.forward(request, response);
         
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
       // processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
