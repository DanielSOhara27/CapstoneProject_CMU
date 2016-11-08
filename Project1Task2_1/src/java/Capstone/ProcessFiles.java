package Capstone;

import Capstone.DBConnectionManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Statement;

/**
 *
 * @author Anshu Agrawal
 */
public class ProcessFiles {
    DBConnectionManager manager = DBConnectionManager.getInstance();
    Statement stmt = null;
        
    public static void main(String[] args) throws FileNotFoundException, IOException{
    
    
    // get the directory where files reside
    String dirName = "C:/TestLoad/BRCR01 1-26-16";
    
    //get file object to the file diectory
    File directory = new File(dirName);
    
  /*  //get file list from the directory
    String filename[] = directory.list();
    int i;
    for (i = 0; i < filename.length; i++) {
        System.out.println(filename[i]);
       
    }
    System.out.print(i);
    */
  
    int i = 0;
    
    //get fileList
    File fileList[] = directory.listFiles();
    
    //loop thru each file and process it
    for (File file : fileList) {
        if(i==1)
            break;
        System.out.println(file.getName());
       
 
	BufferedReader br = new BufferedReader(new FileReader(file));
 
	String line = null;
        
        
        //need to know how many lines to skip before I hit the actual data
        //here skipping first 3 lines
        
	int skipLines =3;
        int skipLinesCtr =0;
        
        while ((line = br.readLine()) != null) {
            
            if(skipLinesCtr != skipLines){
                skipLinesCtr++;
                continue;
            }
                
            System.out.println(line);
            // need to know what is the type of delimiter it is
            String delimiter =",";
            String[] tokens = line.split(delimiter);
            for(int j=0; j< tokens.length;j++)
                System.out.println(tokens[j]);
            break;
            
            // need to know what table the data will go into
            
		
	}
 	br.close();
        i++;
   }
    System.out.print(i);
  }
    
}
