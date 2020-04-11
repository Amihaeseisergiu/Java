package repo;

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

public class ArtistRepository implements AbstractRepository<Artist> {
    
    @Override
    public void create(Artist artist)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction et = null;

            try
            {
                et = em.getTransaction();
                et.begin();
                em.persist(artist);
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
                ResultSet rs = DatabaseUtil.executeStatement("select max(id)+1 from artists");

                while(rs.next())
                {
                    id = rs.getInt(1);
                }
            
                rs = DatabaseUtil.executeStatement("insert into artists values(" + id + ",'" + artist.getName() + "','" + artist.getCountry() + "')");
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArtistRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public Artist findById(int id)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            String query = "SELECT a FROM Artist a WHERE a.id = :artistId";

            TypedQuery<Artist> tq = em.createQuery(query, Artist.class);
            tq.setParameter("artistId", new BigDecimal(id));
            Artist artist = null;
            try
            {
                artist = tq.getSingleResult();
            }
            catch(NoResultException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                em.close();
            }

            return artist;
        }
        else
        {
            ResultSet rs = DatabaseUtil.executeStatement("select * from artists where id=" + id);
            try {
                while(rs.next())
                {
                    String artistName = rs.getString("name");
                    String artistCountry = rs.getString("country");
                    return new Artist(id, artistName, artistCountry);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ArtistRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }
    
    public List<Artist> findByName(String name)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();

            Query query = em.createNamedQuery("Artist.findByName");
            query.setParameter("name", name);
            List<Artist> artists = null;
            try
            {
                artists = query.getResultList();
            }
            catch(NoResultException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                em.close();
            }

            return artists;
        }
        else
        {
            ResultSet rs = DatabaseUtil.executeStatement("select * from artists where name='" + name + "'");
            List<Artist> artists = new ArrayList<>();
            try {
                while(rs.next())
                {
                    int artistId = rs.getInt("id");
                    String artistName = rs.getString("name");
                    String artistCountry = rs.getString("country");
                    artists.add(new Artist(artistId, artistName, artistCountry));
                }
                return artists;
            } catch (SQLException ex) {
                Logger.getLogger(ArtistRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            return artists;
        }
    }
    
    @Override
    public void delete(int id)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction et = null;
            Artist artist = null;
            try
            {
                et = em.getTransaction();
                et.begin();
                artist = em.find(Artist.class, new BigDecimal(id));
                em.remove(artist);
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
            ResultSet rs = DatabaseUtil.executeStatement("delete from artists where id=" + id);
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ArtistRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
