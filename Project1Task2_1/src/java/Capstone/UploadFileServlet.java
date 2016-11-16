
package Capstone;
//Daniel Comment through netbeans
// Ellie comment netbeans 
// Anshu OK 
// Natt OK
//Sophie finally working.

import java.io.BufferedOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Ellie
 */
@WebServlet(name = "UploadFileServlet", urlPatterns = {"/UploadFileServlet"})
@MultipartConfig
public class UploadFileServlet extends HttpServlet {

        ProcessFiles processFiles = null;
        private static final long serialVersionUID = 1L;
     
    // location to store file uploaded
        private static final String BASE_RECEIVE_DIRECTORY = "C:/TestLoad/";

        // upload settings
      private static final int  BUFFER_SIZE = 4096;
    
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
        
        processFiles = new ProcessFiles();
        
        //1/process only if its multipart content

      if(ServletFileUpload.isMultipartContent(request)){

            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
     
                   for(FileItem item : multiparts){

                    if(!item.isFormField()){

                       String n = new File(item.getName()).getName();
 
                          System.out.println("name "+n);
                        File uploadedFile = new File("C:\\Users\\Ellie\\Documents\\UploadTests" + File.separator + n);
                        nextView = "index.jsp";
                        
                        try
                        {
                          ZipInputStream zin = new ZipInputStream(new FileInputStream(uploadedFile));
                          ZipEntry entry;
                          String name, dir;
                          while ((entry = zin.getNextEntry()) != null)
                          {
                            name = entry.getName();
                            if( entry.isDirectory() )
                            {
                              mkdirs(BASE_RECEIVE_DIRECTORY,name);
                              continue;
                            }
                            /* this part is necessary because file entry can come before
                             * directory entry where is file located
                             * i.e.:
                             *   /foo/foo.txt
                             *   /foo/
                             */
                            dir = dirpart(name);
                            if( dir != null )
                              mkdirs(BASE_RECEIVE_DIRECTORY,dir);

                            extractFile(zin, BASE_RECEIVE_DIRECTORY, name);
                            
                          }
                          zin.close();
                        } 
                        catch (IOException e)
                        {
                          e.printStackTrace();
                        }
            
                    }
                    if(item.isFormField())
                    {
                     // accepts the entry from the user in key/value pairs with the field name being the key and user's entry being the value 
                     formFields.put(item.getFieldName(), item.getString());

                    }
                   }
                  
                   System.out.println(formFields.entrySet());
                   
                
        
 

               //File uploaded successfully

               request.setAttribute("message", "File Uploaded Successfully");

            } catch (Exception ex) {

               request.setAttribute("message", "File Upload Failed due to " + ex);
               System.out.println(ex);
               System.out.println("I am here in exception");
               nextView = "index.jsp";   

            }

 

        }else{
          System.out.println("I am here in else statement");

            nextView = "index.jsp";     
     }

 

            RequestDispatcher view = request.getRequestDispatcher(nextView);
            view.forward(request, response);

 

    }


        private static void mkdirs(String outdir,String path)
        {
          File d = new File(outdir, path);
          if( !d.exists() )
            d.mkdirs();
        }


          private static void extractFile(ZipInputStream in, String outdir, String name) throws IOException
        {
          byte[] buffer = new byte[BUFFER_SIZE];
          BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outdir,name)));
          int count = -1;
          while ((count = in.read(buffer)) != -1)
            out.write(buffer, 0, count);
          out.close();
        }
  

        private static String dirpart(String name)
        {
          int s = name.lastIndexOf( File.separatorChar );
          return s == -1 ? null : name.substring( 0, s );
        }
        
        
        
}
