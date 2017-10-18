package cn.promore.bf.serivce.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.ShUserDto;
import cn.promore.bf.bean.User;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.UserDao;
import cn.promore.bf.serivce.UserService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService {
	@Resource
	private UserDao userDao;
	
	
	@Override
	@Resource(type=UserDao.class)
	public void setDao(BaseDao<User, Integer> baseDao) {
		super.setDao(baseDao);
	}


	@Caching(cacheable={
			//@Cacheable(value="applicationBeanCache",key="#result.id+'_User_CacheById'"),
			@Cacheable(value="applicationBeanCache",key="#username+'_User_CacheById'")
	})
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	public Integer findUserNo(User user) {
		return userDao.findUserNo(user);
	}

	public List<User> findUsers(Page page,User user) {
		return userDao.findUsers(user,(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize());
	}
	
	
	@Caching(evict={
			@CacheEvict(value="applicationBeanCache",key="#user.username+'_User_CacheById'",beforeInvocation=true),
			@CacheEvict(value="applicationBeanCache",key="#user.id+'_User_CacheById'",beforeInvocation=true),
			@CacheEvict(value="applicationBeanCache",key="#user.id+'_'+#root.targetClass+'_CacheById'",beforeInvocation=true)
			
	})
	public void update(User user) {
		userDao.update(user);
	}

	public Long getDataNo(String entityName) {
		return userDao.getDataNo(entityName);
	}

	@Override
	public String findPasswordById(Integer id) {
		return userDao.findPasswordById(id);
	}

	
	@Override
	public String findLxfsById(Integer id) {
		return userDao.findLxfsById(id);
	}

	@Override
	public List<User> findDatasByChinesename(String chinesename,String roleShortName) {
		return userDao.findDatasByChinesename(chinesename,roleShortName);
	}


	@Override
	public List<ShUserDto> findAllByParams(String params) {
		return null;
	}


	@Override
	public List<User> findUserByRoleShortName(String dataIds) {
		return userDao.findDatasByChinesename(null,dataIds);
	}
}
