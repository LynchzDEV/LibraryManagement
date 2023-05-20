package kongkawdee;

import java.util.logging.*;
import java.sql.*;

public class LibraryConnection {
    public static Connection connect() {
        String URL = "jdbc:mysql://localhost:3306/account";
        String username = "root";
        String password = "Palm123456za";
        Connection connection = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, username, password);
        }
        catch(ClassNotFoundException | SQLException ex){
            Logger.getLogger(LibraryConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
}
