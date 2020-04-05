package com.amihaeseisergiu.laborator8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArtistController {
    
    public void create(String name, String country)
    {
        ResultSet rs = Database.executeStatement("insert into artists (name,country) values('" + name + "','" + country + "')");
        try {
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Artist findByName(String name)
    {
        ResultSet rs = Database.executeStatement("select * from (select * from artists where name='" + name + "') where rownum<2");
        try {
            while(rs.next())
            {
                int artistId = rs.getInt("id");
                String artistName = rs.getString("name");
                String artistCountry = rs.getString("country");
                return new Artist(artistId, artistName, artistCountry);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public Artist findById(int id)
    {
        ResultSet rs = Database.executeStatement("select * from artists where id=" + id);
        try {
            while(rs.next())
            {
                String artistName = rs.getString("name");
                String artistCountry = rs.getString("country");
                return new Artist(id, artistName, artistCountry);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public int getNumberOfArtists()
    {
        ResultSet rs = Database.executeStatement("select count(*) from artists");
        try {
            while(rs.next())
            {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
}
