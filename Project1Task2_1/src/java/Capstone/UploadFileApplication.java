/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Capstone;

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
    
    final int BUFFER = 2048;
    static final int TOOBIG = 0x6400000; // Max size of unzipped data, 100MB
    static final int TOOMANY = 1024;     // Max number of files
// ...
 
private String validateFilename(String filename, String intendedDir)
      throws java.io.IOException 
{
            File f = new File(filename);
            String canonicalPath = f.getCanonicalPath();
 
            File iD = new File(intendedDir);
            String canonicalID = iD.getCanonicalPath();
   
            if (canonicalPath.startsWith(canonicalID)) {
                    return canonicalPath;
            } else {
                    throw new IllegalStateException("File is outside extraction target directory.");
            }
} 

    
    
   public void unpackFileFolder(String fileName) throws IOException
   {
                System.out.println("Gets here");
                FileInputStream fis = new FileInputStream(fileName);
                System.out.println("here");
                ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
                ZipEntry entry;
                int entries = 0;
                long total = 0;
                try {
                  while ((entry = zis.getNextEntry()) != null) {
                    System.out.println("Extracting: " + entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    // Write the files to the disk, but ensure that the filename is valid,
                    // and that the file is not insanely big
                    String name = validateFilename(entry.getName(), ".");
                    if (entry.isDirectory()) {
                      System.out.println("Creating directory " + name);
                      new File(name).mkdir();
                      continue;
                    }
                    FileOutputStream fos = new FileOutputStream(name);
                    BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
                    while (total + BUFFER <= TOOBIG && (count = zis.read(data, 0, BUFFER)) != -1) {
                      dest.write(data, 0, count);
                      total += count;
                    }
                    dest.flush();
                    dest.close();
                    zis.closeEntry();
                    entries++;
                    if (entries > TOOMANY) {
                      throw new IllegalStateException("Too many files to unzip.");
                    }
                    if (total > TOOBIG) {
                      throw new IllegalStateException("File being unzipped is too big.");
                    }
                  }
                } finally {
                  zis.close();
                }
    }    




    
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
      //  LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}