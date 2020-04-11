package app;

import entity.Album;
import entity.Artist;
import entity.Chart;
import java.util.ArrayList;
import java.util.List;
import repo.AbstractRepository;
import repo.AlbumRepository;
import repo.ArtistRepository;
import repo.ChartRepository;
import repo.RepositoryFactory;
import util.DatabaseUtil;
import util.PersistenceUtil;
import util.TableFiller;

public class AlbumManager {
    
    public static void main(String[] args)
    {
        AlbumManager lab9 = new AlbumManager();
        //lab9.compulsory();
        //lab9.optional();
        lab9.bonus();
    }
    
    public void compulsory()
    {
        ArtistRepository ar = new ArtistRepository();
        AlbumRepository alr = new AlbumRepository();
        Artist artist = new Artist("Gigel", "Romania");
        ar.create(artist);
        
        artist = ar.findById(1);
        System.out.println(artist.getName() + " " + artist.getCountry());

        List<Artist> artists = ar.findByName("Gigel");
        artists.forEach(a -> System.out.println(a.getName() + " " + a.getCountry()));
        
        Album album = new Album("Manele vechi", 1999, artist);
        alr.create(album);
        
        album = alr.findById(1);
        System.out.println(album.getName() + " " + album.getReleaseYear());
        
        List<Album> albums = alr.findByArtist(artist);
        albums.forEach(a -> System.out.println(a.getName() + " " + a.getReleaseYear()));

        PersistenceUtil.close();
    }
    
    public void optional()
    {
        
        ArtistRepository ar = new ArtistRepository();
        AlbumRepository alr = new AlbumRepository();
        ChartRepository cr = new ChartRepository();
        Chart chart = new Chart("Top Manele");
        cr.create(chart);
        
        chart = cr.findById(1);
        System.out.println(chart.getName());
        
        
        Artist artist1 = ar.findById(1);
        Artist artist2 = ar.findById(2);
        List<Artist> artists = new ArrayList<>();
        artists.add(artist1);
        artists.add(artist2);
        cr.setRanking(chart, artists);
        
        
        List<Artist> ranking = cr.getRanking(chart);
        ranking.forEach((a -> System.out.println(a.getName() + " " + a.getCountry())));
        
        
        Artist artist = new Artist("Ionel", "China");
        RepositoryFactory rf = new RepositoryFactory();
        AbstractRepository<Artist> abr = rf.create("Artist");
        abr.create(artist);
        
        PersistenceUtil.close();
        DatabaseUtil.close();
    }
            
    public void bonus()
    {
        TableFiller.fillTables(100, 100);
    }
    
}
