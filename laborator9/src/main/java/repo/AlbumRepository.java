package repo;

import entity.Album;
import entity.Artist;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import util.ConfigurationFile;
import util.DatabaseUtil;
import util.PersistenceUtil;

public class AlbumRepository implements AbstractRepository<Album> {
    
    @Override
    public void create(Album album)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction et = null;

            try
            {
                et = em.getTransaction();
                et.begin();
                em.persist(album);
                et.commit();
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
        else
        {
            try
            {
                int id = 1;
                ResultSet rs = DatabaseUtil.executeStatement("select max(id)+1 from albums");
                while(rs.next())
                {
                    id = rs.getInt(1);
                }
                rs = DatabaseUtil.executeStatement("insert into albums values(" + id + ",'" + album.getName() + "'," + album.getArtistId().getId().intValue() + "," + album.getReleaseYear().intValue() + ")");
                
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AlbumRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public Album findById(int id)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            String query = "SELECT a FROM Album a WHERE a.id = :albumId";

            TypedQuery<Album> tq = em.createQuery(query, Album.class);
            tq.setParameter("albumId", new BigDecimal(id));
            Album album = null;
            try
            {
                album = tq.getSingleResult();
            }
            catch(NoResultException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                em.close();
            }

            return album;
        }
        else
        {
            ResultSet rs = DatabaseUtil.executeStatement("select * from albums where id=" + id);
            try {
                while(rs.next())
                {
                    int artistId = rs.getInt("artist_id");
                    String albumName = rs.getString("name");
                    int releaseYear = rs.getInt("release_year");
                    ArtistRepository ar = new ArtistRepository();
                    Artist artist = ar.findById(artistId);
                    return new Album(id, albumName, releaseYear, artist);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AlbumRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }
    
    public List<Album> findByName(String name)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();

            Query query = em.createNamedQuery("Album.findByName");
            query.setParameter("name", name);
            List<Album> albums = null;
            try
            {
                albums = query.getResultList();
            }
            catch(NoResultException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                em.close();
            }

            return albums;
        }
        else
        {
            ResultSet rs = DatabaseUtil.executeStatement("select * from albums where name='" + name + "'");
            List<Album> albums = new ArrayList<>();
            try {
                while(rs.next())
                {
                    int id = rs.getInt("id");
                    int artistId = rs.getInt("artist_id");
                    String albumName = rs.getString("name");
                    int releaseYear = rs.getInt("release_year");
                    ArtistRepository ar = new ArtistRepository();
                    Artist artist = ar.findById(artistId);
                    albums.add(new Album(id, albumName, releaseYear, artist));
                }
                return albums;
            } catch (SQLException ex) {
                Logger.getLogger(AlbumRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            return albums;
        } 
    }
    
    public List<Album> findByArtist(Artist artist)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();

            Query query = em.createNamedQuery("Album.findByArtist");
            query.setParameter("artistId", artist);
            List<Album> albums = null;
            try
            {
                albums = query.getResultList();
            }
            catch(NoResultException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                em.close();
            }

            return albums;
        }
        else
        {
            ResultSet rs = DatabaseUtil.executeStatement("select * from albums where artist_id=" + artist.getId());
            List<Album> albums = new ArrayList<>();
            try {
                while(rs.next())
                {
                    int albumId = rs.getInt("id");
                    String albumName = rs.getString("name");
                    int releaseYear = rs.getInt("release_year");
                    albums.add(new Album(albumId, albumName, releaseYear, artist));
                }
                return albums;
            } catch (SQLException ex) {
                Logger.getLogger(AlbumRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            return albums;
        }
    }

    @Override
    public void delete(int id)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction et = null;
            Album album = null;
            try
            {
                et = em.getTransaction();
                et.begin();
                album = em.find(Album.class, new BigDecimal(id));
                em.remove(album);
                et.commit();
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
        else
        {
            ResultSet rs = DatabaseUtil.executeStatement("delete from albums where id=" + id);
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AlbumRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
