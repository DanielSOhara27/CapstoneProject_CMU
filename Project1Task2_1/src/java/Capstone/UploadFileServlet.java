package Capstone;

import java.io.BufferedOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Ellie
 */
@WebServlet(name = "UploadFileServlet", urlPatterns = {"/UploadFileServlet"})
@MultipartConfig
public class UploadFileServlet extends HttpServlet {

    ProcessFiles processFiles;
    private static final long serialVersionUID = 1L;

    // location to store file uploaded
    private static final String BASE_RECEIVE_DIRECTORY = "C:/TestLoad/";

    // upload settings
    private static final int BUFFER_SIZE = 4096;
    
    String siteName;
    String sensor;
    String model; 
    String folderName;
    
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
        Map<String, String> formFields = new HashMap<>();
        String nextView = null;
        String zipFilePath = null;
        System.out.println("upload file servlet do post hit");
        //process only if its multipart content
        System.out.println("here");
        if (ServletFileUpload.isMultipartContent(request)) {

            try {

                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : multiparts) {

                    if (!item.isFormField()) {

                        String name = new File(item.getName()).getName();
                        zipFilePath = BASE_RECEIVE_DIRECTORY + name;
                        item.write(new File(BASE_RECEIVE_DIRECTORY + name));
                        System.out.println(" * " + zipFilePath);

                    }
                    if (item.isFormField()) {
                        // accepts the entry from the user in key/value pairs with the field name being the key and user's entry being the value 
                        formFields.put(item.getFieldName(), item.getString());

                    }
                }

                System.out.println(formFields.entrySet());
                
                sensor = formFields.get("sensorName");
                siteName = formFields.get("siteName");
                model = formFields.get("model");
                folderName = formFields.get("folderName");

                // check if the params match an existing table and then upload by continuing this process
                // do we need to wait to ensure they have unpacked before processing?
                processFiles = new ProcessFiles();
                extractInsideFiles(zipFilePath, BASE_RECEIVE_DIRECTORY);
                System.out.println("data passed to ETL:  "+ siteName+ model+ sensor+ folderName);
                processFiles.startETL(siteName, model, sensor, folderName);
                                          
                nextView = "intro.jsp";

                //File uploaded successfully
                request.setAttribute("message", "File Uploaded Successfully");

            } catch (Exception ex) {

                request.setAttribute("message", "File Upload Failed due to " + ex);

            }

        } else {

            request.setAttribute("message",
                    "Sorry this Servlet only handles file upload request");

        }

        //   request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    public void extractInsideFiles(String zipFilePath, String destination) throws IOException, SQLException {
        File dir = new File(destination);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileInputStream fis;

        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            String fileName=null;
            while (ze != null) {
                fileName = ze.getName();
                        File newFile = new File(BASE_RECEIVE_DIRECTORY  + fileName);

                        System.out.println("file unzip : "+ newFile.getAbsoluteFile());

                         //create all non exists folders
                         //else you will hit FileNotFoundException for compressed folder
                         new File(newFile.getParent()).mkdirs();

                         FileOutputStream fos = new FileOutputStream(newFile);

                         int len;
                         while ((len = zis.read(buffer)) > 0) {
                             fos.write(buffer, 0, len);
                         }

                         fos.close();
                         ze = zis.getNextEntry();
             
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
            System.out.println(zipFilePath);
            FileUtils.deleteQuietly(new File(zipFilePath));
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}