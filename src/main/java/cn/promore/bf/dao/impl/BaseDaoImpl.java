package cn.promore.bf.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.promore.bf.dao.BaseDao;

public class BaseDaoImpl<T,PK extends Serializable> implements BaseDao<T, PK> {
	
	@Autowired
	SessionFactory sessionFactory;
	private Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {  
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();  
        clazz = (Class<T>) type.getActualTypeArguments()[0];  
    }  
	
	public void evict(T entity){
		try {
			sessionFactory.getCurrentSession().evict(entity);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public PK save(T entity) {
		try {
			return (PK) sessionFactory.getCurrentSession().save(entity);
		} catch (RuntimeException  e) {
			throw e;
		}
	}
	
	public void delete(T entity) {
		try {
			sessionFactory.getCurrentSession().delete(entity);
		} catch (RuntimeException  e) {
			throw e;
		}		
	}
	
	public void deleteById(PK id) {
		try {
			sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(clazz,id));
		} catch (RuntimeException  e) {
			throw e;
		}		
	}

	public void saveOrUpdate(T entity) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(entity);
		} catch (RuntimeException  e) {
			throw e;
		}		
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		try {
			return sessionFactory.getCurrentSession().createQuery("from "+clazz.getName()).list();
		} catch (RuntimeException  e) {
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String propertyName, Object value, int type) {
		StringBuffer sql = new StringBuffer("from "+clazz.getName() +" as m where m."+propertyName);
		if(1==type)sql.append(" = ? ");
		if(2==type)sql.append(" like ? ");
		try {
			return sessionFactory.getCurrentSession().createQuery(sql.toString()).setString(0,(String)value).list();
		} catch (RuntimeException  e) {
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public T findById(PK id) {
		try {
		 return	(T)sessionFactory.getCurrentSession().get(clazz,id);
		} catch (RuntimeException  e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(Integer startIndex, Integer pageSize) {
		return sessionFactory.getCurrentSession().createQuery("from "+clazz.getName()).setMaxResults(pageSize).setFirstResult(startIndex).list();
	}
	

	@Override
	public void flush() {
		this.getSessionFactory().getCurrentSession().flush();
	}
	
	public void update(T entity) {
		sessionFactory.getCurrentSession().update(entity);
		//sessionFactory.getCurrentSession().flush();
	}

	public void merge(T entity){
		sessionFactory.getCurrentSession().merge(entity);
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
