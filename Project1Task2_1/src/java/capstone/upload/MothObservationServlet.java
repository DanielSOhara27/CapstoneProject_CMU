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
@WebServlet(name = "MothObservationServlet", urlPatterns = {"/MothObservationServlet"})
public class MothObservationServlet extends HttpServlet {
    PreparedStatement stmt = null;
    Connection connection = null;
    DBConnectionManager manager = DBConnectionManager.getInstance();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("anshu moth");
        
        String nextPage = null;
        String message = null;
        String genus = request.getParameter("Genus");
        String species = request.getParameter("Species");
        String commonName = request.getParameter("CommonName");
        String family = request.getParameter("Family");
        String gender = request.getParameter("Sex");
        String sampleTime = request.getParameter("SampleTime");
        String refDate = request.getParameter("refDate");
        String identifiedBy = request.getParameter("identifiedBy");
        String nativeSpecies = request.getParameter("Native");
        String collected = request.getParameter("Collected");
        int result = 0;
        try {
            result = submitToDatabase(genus, species, commonName, family, gender, sampleTime, refDate, identifiedBy, nativeSpecies, collected);
        } catch (SQLException ex) {
            Logger.getLogger(MothObservationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result > 0) {
            message = "Upload successful. You may submit another upload.";
            nextPage = "UploadHomePage.jsp";

        } else {
            message = "Upload failed. Try again.";
            nextPage = "UploadMothEpidoptera.jsp";
        }
        RequestDispatcher view = request.getRequestDispatcher(nextPage);
        request.setAttribute("message", message);
        System.out.println(nextPage);
        view.forward(request, response);
    }

    public int submitToDatabase(String genus, String species, String commonName, String family,
            String sex, String timeOfSampling, String refDate, String identifier, String nativeStatus, String collected) throws SQLException {
        connection = manager.getConnection();
        stmt = connection.prepareStatement("Insert into Table_Moth(genus, species, common_name, family,"
                + "sex, time_of_sampling, date, identifier, native_status, collected_pinned) "
                + "values (?,?,?,?,?,?,?,?,?, ?)");
        stmt.setString(1, genus);
        stmt.setString(2, species);
        stmt.setString(3, commonName);
        stmt.setString(4, family);
        stmt.setString(5, sex);
        stmt.setString(6, timeOfSampling);
        stmt.setString(7, refDate);
        stmt.setString(8, identifier);
        stmt.setString(9, nativeStatus);
        stmt.setString(10, collected);
        int result = stmt.executeUpdate();
        stmt.clearParameters();
        System.out.println(result);
        return result;
    }
}
