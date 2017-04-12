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
@WebServlet(urlPatterns = {"/AddUserServlet"})
public class AddUserServlet extends HttpServlet {
            
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
                
        
        String message = null;
        String nextPage = null;
        

        
        
        
        String firstname = request.getParameter("firstName");
        String lastname = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String position = request.getParameter("radios");
        
        System.out.println(position);
        boolean exists;
            try {
                exists = checkForUser(username);
                System.out.println("here");
                System.out.println(exists);
            } catch (SQLException ex) {
                Logger.getLogger(AddUserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            
        if(exists = true)
        {
            message = "Username already exists in the system. Please create a new username.";
        }
        if(!password1.equals(password2)) 
        {
            System.out.println(password1 + " " + password2);
            message = "Passwords entered do not match. Try again.";
            nextPage = "5.1_Admin-CreateNewUser.jsp";
        }
        else
        {
            if(position.equals("researcher"))
            {
                try {
                    int i = insertIntoUserTable(firstname, lastname, username, password1, 0);
                    if(i > 0)
                    {
                        message = "User created succesfully.";
                        nextPage = "5.1_Admin-CreateNewUser.jsp"; // set to login page
                    }
                    else
                    {
                        message = "User creation unsuccessful. Please try again.";
                        nextPage = "5.1_Admin-CreateNewUser.jsp";
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AddUserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(position.equals("faculty"))
            {
                try {
                    int i = insertIntoUserTable(firstname, lastname, username, password1, 1);
                    int j = insertIntoPermsTable(username);
                    if(i > 0 && j > 0)
                    {
                    message = "User created successfully & permissions added to the database"; 
                    nextPage = "5.1_Admin-CreateNewUser.jsp";     // redirect to log in page 
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AddUserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        }
        

            
            RequestDispatcher view = request.getRequestDispatcher(nextPage);
            request.setAttribute("message", message);
            System.out.println(nextPage);
            view.forward(request, response);
        
    }

    public boolean checkForUser(String username) throws SQLException
    {
        System.out.println(username + " in the check for user method.");
        boolean exists;
        ResultSet rs;
        connection = manager.getConnection();
        stmt = connection.createStatement();
        
        String query = "select * from users where uname = '" + username + "'";
        System.out.println(query);
        rs = stmt.executeQuery(query);
        exists = rs.next();
        
        return exists;
    }
    

    public int insertIntoUserTable(String firstname, String lastname, String username, String password, int faculty) throws SQLException
    {
        System.out.println(firstname + " " + lastname + " " + username + " " + password + " " + faculty);
        connection = manager.getConnection();
        stmt = connection.createStatement();
        
        String query = "insert into users(first_name, last_name, uname, password, faculty) values ('" + firstname + "','" + lastname + "','" +  username + "','" + password + "','" + faculty + "')";
        System.out.println(query);
    
        int i = stmt.executeUpdate(query);
        System.out.println(i);
        return i;
  
    }
    
    public int insertIntoPermsTable(String username) throws SQLException
    {

        connection = manager.getConnection();
        stmt = connection.createStatement();  
        String query = "insert into permissions(uname, faculty_name, active) values ('" + username + "','" + username + "', '1')";
        System.out.println(query);
        
        int i = stmt.executeUpdate(query);
        System.out.println(i);
        return i;
    }
    
}
