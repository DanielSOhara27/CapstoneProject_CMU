package capstone.upload;


import capstone.connection.DBConnectionManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
@WebServlet(name = "UploadChooseFormServlet", urlPatterns = {"/UploadChooseFormServlet"})
public class UploadChooseFormServlet extends HttpServlet {
    
    Statement stmt = null;
    Connection connection = null;
    DBConnectionManager manager = DBConnectionManager.getInstance();
    ArrayList<String> observationChoices = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Anshuaa GET");
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
        System.out.println("Anshuaa");
        String nextPage = null;
        System.out.println("here!");
        String requestChoice = request.getParameter("observationType");
        System.out.println(requestChoice);
        if (requestChoice.equals("Fungi")) {
            nextPage = "UploadFungi.jsp";
        }
        if (requestChoice.toLowerCase().contains("moth")) {
            nextPage = "UploadMothEpidoptera.jsp";
        }
        RequestDispatcher view = request.getRequestDispatcher(nextPage);
        System.out.println(nextPage);
        view.forward(request, response);
    }
    public void getObservationOptions() throws SQLException {
        ResultSet rs;
        connection = manager.getConnection();
        stmt = connection.createStatement();
        String query = "Select type from ObservationsTable";
        System.out.println(query);
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            observationChoices.add(rs.getString(1));
        }
    }
}
