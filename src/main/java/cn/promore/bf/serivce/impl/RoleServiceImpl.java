package cn.promore.bf.serivce.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.Role;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.RoleDao;
import cn.promore.bf.serivce.RoleService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class RoleServiceImpl extends BaseServiceImpl<Role, Integer> implements RoleService {
	@Resource
	private RoleDao roleDao;

	@Override
	@Resource(type=RoleDao.class)
	public void setDao(BaseDao<Role, Integer> baseDao) {
		super.setDao(baseDao);
	}

	public Integer findAllNo() {
		return roleDao.findAllNo();
	}

	@CacheEvict(value="applicationBeanCache",key="#roleId+'_Role_CacheById'",beforeInvocation=true)	
	public void save(Integer roleId, Integer[] resourceIds) {
		for(Integer id:resourceIds){
			roleDao.saveRoleResource(roleId,id);
		}
	}

	public List<Role> findRoles(Page page) {
		return roleDao.findAll((page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize());
	}
	
	@CacheEvict(value="applicationBeanCache",key="#roleId+'_Role_CacheById'",beforeInvocation=true)	
	public void deleteRoleResources(Integer roleId) {
		roleDao.deleteRoleResources(roleId);
	}

	@Override
	public Integer findDataNo(Role role) {
		return roleDao.findDataNo(role);
	}

	@Override
	public List<Role> findDatas(Role role, Page page) {
		return roleDao.findDatas(role,(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize());
	}
}
