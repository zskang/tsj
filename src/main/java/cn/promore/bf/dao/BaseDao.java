package cn.promore.bf.dao;

import java.util.List;

public interface BaseDao<T,PK> {
	public PK save(T entity);
    public void delete(T entity);
    public void deleteById(PK id);
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
