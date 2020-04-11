package util;

import com.github.javafaker.Faker;
import entity.Album;
import entity.Artist;
import entity.Genre;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import repo.ArtistRepository;

public class TableFiller {
    
    public static void fillTables(int nrAlbums, int nrArtists)
    {
        EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction et = null;
        
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Genre").executeUpdate();
        em.createQuery("DELETE FROM Album").executeUpdate();
        em.createQuery("DELETE FROM Artist").executeUpdate();
        em.getTransaction().commit();
        
        try
        {
            et = em.getTransaction();
            et.begin();
            Faker faker = new Faker();
            ArtistRepository ar = new ArtistRepository();
            
            for(int i = 0; i < nrArtists; i++)
            {
                Artist artist = new Artist(faker.artist().name(), faker.country().name());
                em.persist(artist);
            }
            et.commit();
            
            for(int i = 0; i < nrAlbums; i++)
            {
                et.begin();
                Artist artist = ar.findById(faker.random().nextInt(1, nrArtists-1));
                Album album = new Album(faker.book().title(), faker.random().nextInt(1900, 2020), artist);
                em.persist(album);
                et.commit();
                et.begin();
                Genre genre = new Genre(faker.music().genre(), album);
                em.persist(genre);
                et.commit();
            }
            
        }
        catch(Exception ex)
        {
            if(et != null)
            {
                et.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            em.close();
        }
    }
}
