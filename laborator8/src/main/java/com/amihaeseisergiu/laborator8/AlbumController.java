package com.amihaeseisergiu.laborator8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlbumController {
    
    public void create(String name, int artistId, int releaseYear)
    {
        ResultSet rs = Database.executeStatement("insert into albums (name,artist_id,release_year) values('" + name + "'," + artistId + "," + releaseYear + ")");
        try {
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Album findByArtist(int artistId)
    {
        ResultSet rs = Database.executeStatement("select * from albums where artist_id=" + artistId);
        try {
            while(rs.next())
            {
                int albumId = rs.getInt("id");
                String albumName = rs.getString("name");
                int releaseYear = rs.getInt("release_year");
                return new Album(albumId, albumName, artistId, releaseYear);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public Album findById(int id)
    {
        ResultSet rs = Database.executeStatement("select * from albums where id=" + id);
        try {
            while(rs.next())
            {
                int artistId = rs.getInt("artist_id");
                String albumName = rs.getString("name");
                int releaseYear = rs.getInt("release_year");
                return new Album(id, albumName, artistId, releaseYear);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public int getNumberOfAlbums()
    {
        ResultSet rs = Database.executeStatement("select count(*) from albums");
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
