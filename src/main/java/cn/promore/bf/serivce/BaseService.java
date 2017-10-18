package cn.promore.bf.serivce;

import java.util.List;

public interface BaseService<T,PK> {
	public PK save(T entity);
    public void delete(T entity);
    public void deleteById( PK id);
    public void saveOrUpdate(T entity);
    public void update(T entity);
    public void merge(T entity);
    public void evict(T entity);
    public void flush();
    public List<T> findAll();
    public List<T> findAll(Integer startIndex,Integer pageSize);
    public List<T> findByProperty(String propertyName, Object value,int type);
    public T findById(PK id);
}
