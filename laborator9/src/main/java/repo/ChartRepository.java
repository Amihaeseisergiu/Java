package repo;

import entity.Artist;
import entity.Chart;
import entity.ChartLink;
import java.math.BigDecimal;
import java.math.BigInteger;
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

public class ChartRepository implements AbstractRepository<Chart> {
    
    @Override
    public void create(Chart chart)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction et = null;

            try
            {
                et = em.getTransaction();
                et.begin();
                em.persist(chart);
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
                ResultSet rs = DatabaseUtil.executeStatement("select max(id)+1 from charts");
                while(rs.next())
                {
                    id = rs.getInt(1);
                }
                rs = DatabaseUtil.executeStatement("insert into charts values(" + id + ",'" + chart.getName() + "')");
                
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ChartRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public Chart findById(int id)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            String query = "SELECT c FROM Chart c WHERE c.id = :chartId";

            TypedQuery<Chart> tq = em.createQuery(query, Chart.class);
            tq.setParameter("chartId", new BigDecimal(id));
            Chart chart = null;
            try
            {
                chart = tq.getSingleResult();
            }
            catch(NoResultException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                em.close();
            }

            return chart;
        }
        else
        {
            ResultSet rs = DatabaseUtil.executeStatement("select * from charts where id=" + id);
            try {
                while(rs.next())
                {
                    int chartId = rs.getInt("id");
                    String chartName = rs.getString("name");
                    rs.close();
                    return new Chart(chartId, chartName);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ChartRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            return null;
        }
    }
    
    public List<Chart> findByName(String name)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();

            Query query = em.createNamedQuery("Chart.findByName");
            query.setParameter("name", name);
            List<Chart> charts = null;
            try
            {
                charts = query.getResultList();
            }
            catch(NoResultException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                em.close();
            }

            return charts;
        }
        else
        {
            ResultSet rs = DatabaseUtil.executeStatement("select * from charts where name='" + name + "'");
            List<Chart> charts = new ArrayList<>();
            try {
                while(rs.next())
                {
                    int id = rs.getInt("id");
                    String chartName = rs.getString("name");
                    charts.add(new Chart(id, chartName));
                }
                rs.close();
                return charts;
            } catch (SQLException ex) {
                Logger.getLogger(ChartRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            return charts;
        }
    }
    
    public List<Chart> findByArtist(Artist artist)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();

            Query query = em.createNamedQuery("ChartLink.findByArtist");
            query.setParameter("artistId", artist);
            List<Chart> charts = null;
            try
            {
                charts = query.getResultList();
            }
            catch(NoResultException ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                em.close();
            }

            return charts;
        }
        else
        {
            ResultSet rs = DatabaseUtil.executeStatement("select * from chart_link where artist_id=" + artist.getId());
            List<Chart> charts = new ArrayList<>();
            try {
                while(rs.next())
                {
                    int chartId = rs.getInt("chart_id");
                    Chart chart = findById(chartId);
                    charts.add(chart);
                }
                rs.close();
                return charts;
            } catch (SQLException ex) {
                Logger.getLogger(ChartRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            return charts;
        }
    }
    
    public List<Artist> getRanking(Chart chart)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();

            Query query = em.createNamedQuery("ChartLink.getRanking");
            query.setParameter("chartId", chart);
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
            ResultSet rs = DatabaseUtil.executeStatement("select * from chart_link where chart_id=" + chart.getId() + " order by chart_position asc");
            List<Artist> artists = new ArrayList<>();
            try {
                while(rs.next())
                {
                    int artistId = rs.getInt("artist_id");
                    ArtistRepository ar = new ArtistRepository();
                    Artist artist = ar.findById(artistId);
                    artists.add(artist);
                }
                rs.close();
                return artists;
            } catch (SQLException ex) {
                Logger.getLogger(ChartRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

            return artists;
        }
    }
    
    public void setRanking(Chart chart, List<Artist> ranking)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction et = null;

            try
            {
                et = em.getTransaction();
                et.begin();
                for(int i = 0; i < ranking.size(); i++)
                {
                    ChartLink cl = new ChartLink();
                    cl.setArtistId(ranking.get(i));
                    cl.setChartId(chart);     
                    cl.setChartPosition(new BigInteger(String.valueOf(i+1)));
                    em.persist(cl);
                }
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
            try {
                int id = 1;
                ResultSet rs = DatabaseUtil.executeStatement("select max(id)+1 from chart_link");
                while(rs.next())
                {
                    id = rs.getInt(1);
                }
                
                for(int i = 0; i < ranking.size(); i++)
                {
                    rs = DatabaseUtil.executeStatement("insert into chart_link values(" + id + "," + ranking.get(i).getId() + "," + chart.getId() + "," + (i+1) + ")");
                    id++;
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ChartRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void delete(int id)
    {
        if(ConfigurationFile.getImplementation().equals("jpa"))
        {
            EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            EntityTransaction et = null;
            Chart chart = null;
            try
            {
                et = em.getTransaction();
                et.begin();
                chart = em.find(Chart.class, new BigDecimal(id));
                em.remove(chart);
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
            ResultSet rs = DatabaseUtil.executeStatement("delete from charts where id=" + id);
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ChartRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
