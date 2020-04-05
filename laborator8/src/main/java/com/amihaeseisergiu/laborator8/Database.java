package com.amihaeseisergiu.laborator8;

import com.github.javafaker.Faker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    
    private static Connection sqlConnection;
    private static Database single_instance = null;
    
    private Database()
    {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            sqlConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "database", "sql");

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Database getInstance()
    {
        if(single_instance == null)
        {
            single_instance = new Database();
        }
        
        return single_instance;
    }
    
    public static ResultSet executeStatement(String sql)
    {
        ResultSet rs = null;
        if(single_instance == null)
        {
            single_instance = new Database();
        }
        try {
            Statement st = sqlConnection.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public static void fillTables(int nrArtists, int nrCharts)
    {
        Faker faker = new Faker();
        ArtistController artistController = new ArtistController();
        AlbumController albumController = new AlbumController();
        ChartController chartController = new ChartController();
        
        List<Album> albumList = new ArrayList<>();
        for(int i = 0; i < nrArtists; i++)
        {
            String name = faker.artist().name().replaceAll("'", "");
            String country = faker.country().name().replaceAll("'", " ");
            artistController.create(name, country);
            Artist artist = artistController.findByName(name);
            
            String title = faker.book().title().replaceAll("'", " ");
            albumController.create(title, artist.getId(), faker.number().numberBetween(1800, 2020));
            Album album = albumController.findByArtist(artist.getId());
            albumList.add(album);
        }
        
        for(int i = 0; i < nrCharts; i++)
        {
            List<Album> temp = new ArrayList<>();
            temp.addAll(albumList);
            Collections.shuffle(temp);
            chartController.create(temp);
        }
    }
    
    public static void close()
    {
        if(single_instance != null)
        {
            try {
                sqlConnection.close();
                single_instance = null;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
