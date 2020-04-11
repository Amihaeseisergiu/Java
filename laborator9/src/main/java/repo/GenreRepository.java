package repo;

import entity.Album;
import entity.Artist;
import entity.Genre;
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

public class GenreRepository implements AbstractRepository<Genre>{

    @Override
    public void create(Genre genre)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction et = null;

            try
            {
                et = em.getTransaction();
                et.begin();
                em.persist(genre);
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
                ResultSet rs = DatabaseUtil.executeStatement("select max(id)+1 from genres");
                while(rs.next())
                {
                    id = rs.getInt(1);
                }
                rs = DatabaseUtil.executeStatement("insert into genres values(" + id + ",'" + genre.getName()  + "')");
                
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(GenreRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public Genre findById(int id)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            String query = "SELECT g FROM Genre g WHERE g.id = :genreId";

            TypedQuery<Genre> tq = em.createQuery(query, Genre.class);
            tq.setParameter("genreId", new BigDecimal(id));
            Genre genre = null;
            try
            {
                genre = tq.getSingleResult();
            }
            catch(NoResultException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                em.close();
            }

            return genre;
        }
        else
        {
            ResultSet rs = DatabaseUtil.executeStatement("select * from genres where id=" + id);
            try {
                while(rs.next())
                {
                    int genreId = rs.getInt("id");
                    String genreName = rs.getString("name");
                    int albumId = rs.getInt("album_id");
                    AlbumRepository ar = new AlbumRepository();
                    Album album = ar.findById(albumId);
                    return new Genre(genreId, genreName, album);
                }
            } catch (SQLException ex) {
                Logger.getLogger(GenreRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }
    
    public List<Genre> findByName(String name)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();

            Query query = em.createNamedQuery("Genre.findByName");
            query.setParameter("name", name);
            List<Genre> genres = null;
            try
            {
                genres = query.getResultList();
            }
            catch(NoResultException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                em.close();
            }

            return genres;
        }
        else
        {
            ResultSet rs = DatabaseUtil.executeStatement("select * from genres where name='" + name + "'");
            List<Genre> genres = new ArrayList<>();
            try {
                while(rs.next())
                {
                    int genreId = rs.getInt("id");
                    String genreName = rs.getString("name");
                    int albumId = rs.getInt("album_id");
                    AlbumRepository ar = new AlbumRepository();
                    Album album = ar.findById(albumId);
                    genres.add(new Genre(genreId, genreName, album));
                }
                return genres;
            } catch (SQLException ex) {
                Logger.getLogger(GenreRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            return genres;
        } 
    }
    
    public List<Genre> findByAlbum(Album album)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();

            Query query = em.createNamedQuery("Genre.findByAlbum");
            query.setParameter("albumId", album);
            List<Genre> genres = null;
            try
            {
                genres = query.getResultList();
            }
            catch(NoResultException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                em.close();
            }

            return genres;
        }
        else
        {
            ResultSet rs = DatabaseUtil.executeStatement("select * from genres where album_id=" + album.getId());
            List<Genre> genres = new ArrayList<>();
            try {
                while(rs.next())
                {
                    int genreId = rs.getInt("id");
                    String genreName = rs.getString("name");
                    genres.add(new Genre(genreId, genreName, album));
                }
                return genres;
            } catch (SQLException ex) {
                Logger.getLogger(GenreRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            return genres;
        }
    }

    @Override
    public void delete(int id)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction et = null;
            Genre genre = null;
            try
            {
                et = em.getTransaction();
                et.begin();
                genre = em.find(Genre.class, new BigDecimal(id));
                em.remove(genre);
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
            ResultSet rs = DatabaseUtil.executeStatement("delete from genres where id=" + id);
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(GenreRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
