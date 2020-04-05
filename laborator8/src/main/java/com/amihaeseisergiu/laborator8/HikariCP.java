package com.amihaeseisergiu.laborator8;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HikariCP {
 
    HikariDataSource ds;
    Connection con;
    
    public HikariCP()
    {
        String configFile = "db.proprieties";
            
        HikariConfig cfg = new HikariConfig(configFile);
        ds = new HikariDataSource(cfg);
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(HikariCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet executeStatement(String sql)
    {
        PreparedStatement pst;
        ResultSet rs = null;
        try
        {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(HikariCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void close()
    {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(HikariCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        ds.close();
    }
}
