package capstone.query;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Anshu
 */
@WebServlet(name = "QuerySiteSensorTypeServlet", urlPatterns = {"/QuerySiteSensorTypeServlet"})
public class QuerySiteSensorTypeServlet extends HttpServlet {

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
        String[] tables = request.getParameterValues("tables");
        String startDate = request.getParameter("start_date");
        String startTime = request.getParameter("start_time");
        String endDate = request.getParameter("end_date");
        String endTime = request.getParameter("end_time");
        String flag = request.getParameter("flag");
        
        HorizontalJoinTables horizontalJoinTables = new HorizontalJoinTables();
        String tempTable = horizontalJoinTables.joinTables(tables[0], tables[1], startDate, startTime, endDate, endTime, flag);
        System.out.println("temp table in Servlet: " + tempTable);
        request.setAttribute("previewTable", tempTable);
        String nextView = "QueryPreviewData.jsp";
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
