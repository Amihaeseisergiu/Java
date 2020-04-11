package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {
    
    private static EntityManagerFactory entityManagerFactory = null;
    
    private PersistenceUtil()
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("MusicAlbumsPU");
    }
    
    public static EntityManagerFactory getEntityManagerFactory()
    {
        if(entityManagerFactory == null)
        {
            PersistenceUtil pu = new PersistenceUtil();
            return entityManagerFactory;
        }
        return entityManagerFactory;
    }
 
    public static void close()
    {
        if(entityManagerFactory != null)
            entityManagerFactory.close();
    }
}
