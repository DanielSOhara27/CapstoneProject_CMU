package capstone.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Anshu Agrawal
 */
public class DBConnectionManager {
private static DBConnectionManager manager = null;
    private Connection conn;
    private DBConnectionManager(){
    String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    String DB_NAME = "capstonetest";
    String URL = "jdbc:mysql://localhost/" + DB_NAME;
    String DB_USER = "root";
    String DB_PWD = "anshu";
    try {
        Class.forName(JDBC_DRIVER);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(DBConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    try {
        conn = (Connection) DriverManager.getConnection(URL, DB_USER, DB_PWD);
    } catch (SQLException ex) {
        Logger.getLogger(DBConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    }   
    public static DBConnectionManager getInstance(){
        if(manager==null){
            manager = new DBConnectionManager();
        }
        return manager;
    }
    public Connection getConnection(){
        return conn;
    }
}
