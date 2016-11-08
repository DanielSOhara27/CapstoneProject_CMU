/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Path;
import javax.servlet.http.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import java.util.zip.*;


/**
 *
 * @author Ellie
 */
public class UploadFileApplication {
    
    final int BUFFER_SIZE = 4096;
    static final int TOOBIG = 0x6400000; // Max size of unzipped data, 100MB
    static final int TOOMANY = 1024;     // Max number of files
// ...

    
   public void unpackFileFolder(String fileName) throws IOException
    {
                System.out.println("Gets here");
                System.out.println(fileName);
                System.out.println("here");
                ZipInputStream zis = new ZipInputStream(new FileInputStream(fileName));
                ZipEntry entry = zis.getNextEntry();
                System.out.println(entry);
                while (entry != null) {
                    System.out.println("Extracting: " + entry);
                    String filePath = "C:\\Users\\LP\\Documents\\UploadTests\\" + entry.getName();                 
                    if (!entry.isDirectory()) {
                      extractFile(zis, filePath);
                      System.out.println("here within not a directory");
                    }
                    else
                    {
                        File dir = new File(filePath);
                        dir.mkdir();
                    }   
                    zis.closeEntry();
                    entry = zis.getNextEntry();
                    } 
                  zis.close();
    }





        private void extractFile(ZipInputStream zis, String filePath) throws IOException
        {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] bytesIn = new byte[BUFFER_SIZE];
            int read = 0;
            while((read = zis.read(bytesIn))!= -1)
            {
                bos.write(bytesIn, 0, read);
            }
            bos.close();
        }
}