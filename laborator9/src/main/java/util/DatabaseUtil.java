package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseUtil {
    
    private static Connection sqlConnection = null;
    
    private DatabaseUtil()
    {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            sqlConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "database", "sql");

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Connection getConnection()
    {
        if(sqlConnection == null)
        {
            DatabaseUtil du = new DatabaseUtil();
            return sqlConnection;
        }
        
        return sqlConnection;
    }
    
    public static ResultSet executeStatement(String sql)
    {
        ResultSet rs = null;
        if(sqlConnection == null)
        {
            DatabaseUtil du = new DatabaseUtil();
        }
        try {
            Statement st = sqlConnection.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public static void close()
    {
        if(sqlConnection != null)
        {
            try {
                sqlConnection.close();
                sqlConnection = null;
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
