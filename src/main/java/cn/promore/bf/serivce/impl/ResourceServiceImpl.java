package cn.promore.bf.serivce.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.promore.bf.bean.Resource;
import cn.promore.bf.bean.Role;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.ResourceDao;
import cn.promore.bf.serivce.ResourceService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ResourceServiceImpl extends BaseServiceImpl<Resource, Integer> implements ResourceService {
	@javax.annotation.Resource
	private ResourceDao resourceDao;


//	@CacheEvict(value="applicationBeanCache",key="#id+'_Resource_CacheById'",beforeInvocation=true)
//	public void deleteById(Integer id) {
//		resourceDao.deleteById( id);
//		resourceDao.flush();
//	}
//	
//	@CacheEvict(value="applicationBeanCache",key="#resource.id+'_Resource_CacheById'",beforeInvocation=true,condition="#resource.id != null")
//	public void saveOrUpdate(Resource resource) {
//		resourceDao.saveOrUpdate(resource);
//		resourceDao.flush();
//	}

	@Override
	@javax.annotation.Resource(type=ResourceDao.class)
	public void setDao(BaseDao<Resource, Integer> baseDao) {
		super.setDao(baseDao);
	}

	public Resource getRoot() {
		return resourceDao.getRoot();
	}

	public List<Resource> getChild(Integer pId) {
		return resourceDao.getChild(pId);
	}

	public List<Resource> getChild(Integer pId, Integer type) {
		return resourceDao.getChildByType(pId, type);
	}
	public List<Resource> findRoleResources(Integer roleId,Integer resType) {
			return resourceDao.findRoleResources(roleId,resType);
	}
	
//	@Cacheable(key="#roles.hashCode()+'_'+#pId+'_'+#resType+'_CacheByUserIdAndResType'",cacheNames={"applicationBeanCache"})
	public List<Resource> findRoleResources(List<Role> roles, Integer pId,Integer resType) {
		if(null!=roles&&roles.size()>0){
			List<Integer> roleIds = new ArrayList<Integer>();
			for(Role r:roles){
				if(null!=r&&null!=r.getId())roleIds.add(r.getId());
			}
			return resourceDao.findRoleResources(roleIds,pId,resType);
		}else{
			return null;
		}
		
	}

	@Override
	public List<Resource> findPermissionNames() {
		return resourceDao.findPermissionNames();
	}

	@Override
	public List<Resource> findChildByPid(Integer pid) {
		return resourceDao.findChildByPid(pid);
	}

	@Override
	public List<Resource> findAllDatas() {
		return resourceDao.findAllDatas();
	}

}
