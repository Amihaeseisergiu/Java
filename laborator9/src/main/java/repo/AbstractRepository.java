package repo;


public interface AbstractRepository<T> {
    
    void create(T entity);
    
    public T findById(int id);
    
    public void delete(int id);
}
