package cn.promore.bf.serivce.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.serivce.BaseService;



@CacheConfig(cacheNames={"applicationBeanCache"})
@Transactional(propagation=Propagation.REQUIRED)
public class BaseServiceImpl<T,PK extends Serializable> implements BaseService<T, PK> {
	
	protected BaseDao<T,PK> baseDao;
	
    @Resource  
    public void setDao(BaseDao<T,PK> baseDao) {  
        this.baseDao = baseDao;  
    }  
	
    @CacheEvict(key="#entity.id+'_'+#root.targetClass+'_CacheById'",beforeInvocation=true)
	public void evict(T entity){
		baseDao.evict(entity);
	}
	
	public PK save(T entity) {
		return baseDao.save(entity);
	}
	
	@CacheEvict(key="#entity.id+'_'+#root.targetClass+'_CacheById'",beforeInvocation=true)
	public void delete(T entity) {
		baseDao.delete(entity);	
	}
	
	@CacheEvict(key="#id+'_'+#root.targetClass+'_CacheById'",beforeInvocation=true)
	public void deleteById(PK id) {
		baseDao.deleteById(id);	
	}

	@CacheEvict(key="#entity.id+'_'+#root.targetClass+'_CacheById'",beforeInvocation=true)
	public void saveOrUpdate(T entity) {
		baseDao.saveOrUpdate(entity);	
	}

	public List<T> findAll() {
		return baseDao.findAll();
	}
	
	public List<T> findByProperty(String propertyName, Object value, int type) {
		return baseDao.findByProperty(propertyName, value, type);
	}
	
	@Cacheable(key="#id+'_'+#root.targetClass+'_CacheById'")
	public T findById(PK id) {
		return baseDao.findById(id);
	}

	public List<T> findAll(Integer startIndex, Integer pageSize) {
		return baseDao.findAll();
	}
	
	@Override
	public void flush() {
		baseDao.flush();
	}
	
	@CacheEvict(key="#entity.id+'_'+#root.targetClass+'_CacheById'",beforeInvocation=true)
	public void update(T entity) {
		baseDao.update(entity);
	}

	@CacheEvict(key="#entity.id+'_'+#root.targetClass+'_CacheById'",beforeInvocation=true)
	public void merge(T entity){
		baseDao.merge(entity);
	}

}
