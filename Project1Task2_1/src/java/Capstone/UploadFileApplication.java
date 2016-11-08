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
    
    final int BUFFER_SIZE = 4096;
    static final int TOOBIG = 0x6400000; // Max size of unzipped data, 100MB
    static final int TOOMANY = 1024;     // Max number of files
// ...

    
  
   public UploadFileApplication()
   {}
   
   
   public void Test(File f)
   {
       System.out.println("here");
       
       try
       {
           ZipInputStream zis = new ZipInputStream(new FileInputStream(f));
           System.out.println("here after zis");
            
           
           
       }
           catch (IOException e)
            {
              e.printStackTrace();
            }
       
   }
}