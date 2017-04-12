/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.admin;

import capstone.connection.DBConnectionManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(urlPatterns = {"/AddPermissions"})
public class AddPermissions extends HttpServlet {
            
        Statement stmt = null;
        Connection connection = null;   
        DBConnectionManager manager = DBConnectionManager.getInstance();

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
            out.println("<title>Servlet AddUserServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddUserServlet at " + request.getContextPath() + "</h1>");
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

        
        System.out.println("here in add permissions");
        
        connection = manager.getConnection();
        
        
        String message = null;
        String nextPage = null;
        
        String adminUsername = request.getParameter("adminUserName");
        String adminPassword = request.getParameter("adminPassword");
        String newUser = request.getParameter("newUsername");
        
        System.out.println(adminUsername);
        System.out.println(adminPassword);
        System.out.println(newUser);

        
        boolean adminPerms = false;
        boolean userExists = false;
        boolean checkPermsExist = false;
      
            try {
                 adminPerms = checkFacultyPerms(adminUsername, adminPassword);
                 System.out.println(adminPerms);
                 userExists = checkStudentExists(newUser);
                 System.out.println(userExists);
                 
                 checkPermsExist = checkIfUserHasPerms(adminUsername, newUser);
                 System.out.println(checkPermsExist);
                 
            } catch (SQLException ex) {
                Logger.getLogger(AddPermissions.class.getName()).log(Level.SEVERE, null, ex);
            }
           
         
        if(adminPerms == true && userExists == true)
        {
            int i = 0;
            try {
                i = insertIntoPermsTable(newUser, adminUsername);
            } catch (SQLException ex) {
                Logger.getLogger(AddPermissions.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(i > 0)
            {
                message = "Permissions added successfully";
                nextPage = "5.2_Admin-AddPermissions.jsp";
            }
        }
        else
        {
            message = "Permissions not added. Check faculty autentification and be sure that username has been registered.";
            nextPage = "5.2_Admin-AddPermissions.jsp";
        }
            
            
            
        
   //   nextPage = "AddPermissions.jsp";   

        
        
        RequestDispatcher view = request.getRequestDispatcher(nextPage);
        request.setAttribute("message", message);
        System.out.println(nextPage);
          view.forward(request, response);
    }


    public int insertIntoPermsTable(String username, String facultyName) throws SQLException
    {

        connection = manager.getConnection();
        stmt = connection.createStatement();  
        String query = "insert into permissions(uname, faculty_name, active) values ('" + username + "','" + facultyName + "', '1')";
        System.out.println(query);
        
        int i = stmt.executeUpdate(query);
        System.out.println(i);
        return i;
    } 
    
    
    public boolean checkFacultyPerms(String username, String password) throws SQLException             
    {
        ResultSet rs;
        connection = manager.getConnection();
        stmt = connection.createStatement();
        String query = "Select * from users where uname = '" + username + "' and password = '" + password + "' and faculty = '1'";
        System.out.println(query);
        
        rs = stmt.executeQuery(query);
        boolean results = rs.next();
        
        return results;
    }
    
    public boolean checkStudentExists(String student) throws SQLException
    {
        ResultSet rs;
        connection = manager.getConnection();
        stmt = connection.createStatement();       
        
        String query = "select * from users where uname = '" + student + "'";
        System.out.println(query);
        rs = stmt.executeQuery(query);
        boolean results = rs.next();
        
        return results;
    }
    
    
    public boolean checkIfUserHasPerms(String admin, String user) throws SQLException
    {
        ResultSet rs;
        connection = manager.getConnection();
        stmt = connection.createStatement();    
        
        String query = "select * from permissions where uname = '" + user + "' and faculty_name = '" + admin + "' and active = '1'";
        System.out.println(query);
        
        rs = stmt.executeQuery(query);
        boolean result = rs.next();
        
        return result;
        
    }
    

}
