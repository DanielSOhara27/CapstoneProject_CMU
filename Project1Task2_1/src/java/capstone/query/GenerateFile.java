package capstone.query;

import capstone.connection.DBConnectionManager;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Anshu
 */
public class GenerateFile {
    
// declare final variables
    DBConnectionManager manager = DBConnectionManager.getInstance();
    Statement stmt = null;
    Connection connection = null;

    public static void main(String[] args) throws SQLException, IOException {
        GenerateFile genDownloadFile = new GenerateFile();
        //genDownloadFile.exportData(file,outFile);
    }
    public String exportData(String tempTable, String[] columns) throws SQLException, IOException {

        connection = manager.getConnection();
        stmt = connection.createStatement();
        StringBuilder columnString = new StringBuilder();
        for(int i=0;i<columns.length;i++){
            if(i!=columns.length-1)
                columnString.append(columns[i]).append(",");
            else
                columnString.append(columns[i]);
        }
        String query = "Select `" + columnString.toString().replace(",", "`,`") + "` from `" + tempTable+"`";
        System.out.println(query);
        String fileName = new SimpleDateFormat("yyyyMMddhhmm'.csv'").format(new Date());
        ResultSet rs = stmt.executeQuery(query);
        String filePath = "C:/TestDownload/ExportData_"+fileName;
        CSVWriter writer = new CSVWriter(new FileWriter(filePath));
        writer.writeAll(rs, true);
        writer.flush();
        writer.close();
        return filePath;
    }
}
