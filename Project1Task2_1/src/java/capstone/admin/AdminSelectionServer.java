/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.admin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ellie
 */
@WebServlet(name = "AdminSelectionServer", urlPatterns = {"/AdminSelectionServer"})
public class AdminSelectionServer extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminSelectionServer</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminSelectionServer at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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

        
        
        processRequest(request, response);
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
        HttpSession session = request.getSession(false);

                if (session.getAttribute("Username") == null)
                { //checks if there's a LOGIN_USER set in session...
                    String page = "5.0_Admin-Choose.jsp";                    
                    request.setAttribute("value", page);
                    request.getRequestDispatcher("login.jsp").forward(request, response);

                }
                
        String selection = request.getParameter("queryType");
        if(selection.equals("createUser"))
        {
            response.sendRedirect("5.1_Admin-CreateNewUser.jsp");
        }
        if(selection.equals("addPerms"))
        {
            response.sendRedirect("5.2_Admin-AddPermissions.jsp");
        }
        if(selection.equals("deletePerms"))
        {
            response.sendRedirect("5.3_Admin-DeletePermissions.jsp");
        }        
        
        processRequest(request, response);
    }


}
