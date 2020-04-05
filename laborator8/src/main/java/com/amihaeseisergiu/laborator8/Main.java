package com.amihaeseisergiu.laborator8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    
    public static void main(String args[])
    {
        Main lab = new Main();
        lab.optional();
        //lab.bonusSingleton();
        //lab.bonusConnectionPool();
    }
    
    public void optional()
    {
        Database.fillTables(100, 3);
        ChartController ac = new ChartController();
        ac.displayRanking();
        Database.close();
    }
    
    public void bonusSingleton()
    {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        
        for(int i = 0; i < 10000; i++)
        {
            executor.execute(() -> {
                try {
                    ResultSet rs = Database.executeStatement("select * from artists");
                    
                    while(rs.next())
                    {
                        System.out.println(rs.getInt(1) + " " + rs.getString(2));
                    }
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            });   
        }
        executor.shutdown();
    }
    
    public void bonusConnectionPool()
    {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        
        for(int i = 0; i < 10000; i++)
        {
            executor.execute(() -> {
                try {
                    HikariCP cp = new HikariCP();
                    ResultSet rs = cp.executeStatement("select * from artists");
                    
                    while(rs.next())
                    {
                        System.out.println(rs.getInt(1) + " " + rs.getString(2));
                    }
                    rs.close();
                    cp.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            });   
        }
        executor.shutdown();
    }
}
