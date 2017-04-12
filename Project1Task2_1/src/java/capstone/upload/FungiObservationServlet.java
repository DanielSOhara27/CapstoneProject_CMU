package capstone.upload;
import capstone.connection.DBConnectionManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
@WebServlet(name = "FungiObservationServlet", urlPatterns = {"/FungiObservationServlet"})
public class FungiObservationServlet extends HttpServlet {
    PreparedStatement stmt = null;
    Connection connection = null;
    DBConnectionManager manager = DBConnectionManager.getInstance();
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
        String nextPage = null;
        String message = null;
        String genus = request.getParameter("Genus");
        String species = request.getParameter("Species");
        String commonName = request.getParameter("CommonName");
        String family = request.getParameter("Family");
        String feeding = request.getParameter("Feeding");
        String identifier = request.getParameter("identifiedBy");
        String refDate = request.getParameter("refDate");
        String collected = request.getParameter("Collected");
        System.out.println(genus + " " + species + " " + commonName + " " + family + " " + feeding + " " + identifier + " " + refDate + " " + collected);
        int result = 0;
        try {
            result = submitToDatabase(genus, species, commonName, family, feeding, identifier, refDate, collected);
        } catch (SQLException ex) {
            Logger.getLogger(FungiObservationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result > 0) {
            message = "Upload successful. You may submit another upload.";
            nextPage = "UploadFungi.jsp";
        } else {
            message = "Upload failed. Try again.";
            nextPage = "UploadFungi.jsp";
        }
        RequestDispatcher view = request.getRequestDispatcher(nextPage);
        request.setAttribute("message", message);
        System.out.println(nextPage);
        view.forward(request, response);
    }

    public int submitToDatabase(String genus, String species, String commonName, String family, String feeding, String identifier, String refDate, String collected) throws SQLException {
        connection = manager.getConnection();
        stmt = connection.prepareStatement("Insert into Table_Fungi(genus, species, common_name, family, feeding_type, identified_by, date, collected_archived) values (?,?,?,?,?,?,?,?)");
        stmt.setString(1, genus);
        stmt.setString(2, species);
        stmt.setString(3, commonName);
        stmt.setString(4, family);
        stmt.setString(5, feeding);
        stmt.setString(6, identifier);
        stmt.setString(7, refDate);
        stmt.setString(8, collected);
        int result = stmt.executeUpdate();
        stmt.clearParameters();
        System.out.println(result);
        return result;
    }
}
