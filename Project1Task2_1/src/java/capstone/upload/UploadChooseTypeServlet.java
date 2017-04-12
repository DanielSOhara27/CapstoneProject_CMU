package capstone.upload;
import capstone.connection.DBConnectionManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "UploadChooseTypeServlet", urlPatterns = {"/UploadChooseTypeServlet"})
public class UploadChooseTypeServlet extends HttpServlet {
    Statement stmt = null;
    Connection connection = null;
    DBConnectionManager manager = DBConnectionManager.getInstance();
    private String contextPath;
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
      //  System.out.println("here" + request.getPathInfo());
               // System.out.println(session.getAttribute("Username"));
                if (session.getAttribute("Username") == null)
                { //checks if there's a LOGIN_USER set in session...
                    String page = "UploadHomePage.jsp";                    
                    request.setAttribute("value", page);
                    request.getRequestDispatcher("login.jsp").forward(request, response);

               
                }
                else
                {
                System.out.println(session.getAttribute("Username"));
                
     
        ArrayList<String> observationChoices = new ArrayList<>();
        ArrayList<String> site = new ArrayList<>();
        ArrayList<String> sensor = new ArrayList<>();
        ArrayList<String> model = new ArrayList<>();
        String nextPage = null;
        String radioSelection = request.getParameter("uploadType");
        System.out.println(radioSelection);
        if (radioSelection.equals("observation")) {
            try {
                ResultSet rs;
                connection = manager.getConnection();
                stmt = connection.createStatement();
                String query = "Select type from ObservationsTable";

                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    observationChoices.add(rs.getString(1));
                }
                System.out.println(Arrays.toString(observationChoices.toArray()));
                request.setAttribute("observationChoices", observationChoices);
                request.getRequestDispatcher("UploadObservation.jsp").forward(request, response);
               
            } catch (SQLException ex) {
                Logger.getLogger(UploadChooseTypeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (radioSelection.equals("existing")) {
            try {
                ResultSet rs;
                connection = manager.getConnection();
                stmt = connection.createStatement();
                String query = "Select SiteID, SensorID, ModelID from MappingTable";

                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    site.add(rs.getString(1));
                    sensor.add(rs.getString(2));
                    model.add(rs.getString(3));
                }

                request.setAttribute("site", site);
                request.setAttribute("sensor", sensor);
                request.setAttribute("model", model);
                request.getRequestDispatcher("UploadFileAndTable.jsp").forward(request, response);
            
            } 
            catch (SQLException ex) {
                Logger.getLogger(UploadChooseTypeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }

    }

}
