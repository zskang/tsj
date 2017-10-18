package cn.promore.bf.serivce.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.Project;
import cn.promore.bf.bean.ProjectUsers;
import cn.promore.bf.bean.Role;
import cn.promore.bf.bean.User;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.ProjectDao;
import cn.promore.bf.dao.ProjectUsersDao;
import cn.promore.bf.dao.RoleDao;
import cn.promore.bf.dao.UserDao;
import cn.promore.bf.serivce.ProjectService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ProjectServiceImpl extends BaseServiceImpl<Project,Integer>  implements ProjectService {
	
	@Resource
	ProjectDao projectDao;
	
	@Resource
	ProjectUsersDao projectUsersDao;
	
	@Resource
	UserDao userDao;
	
	@Resource
	RoleDao roleDao;
	
	@Resource(type=ProjectDao.class)  
    public void setDao(BaseDao<Project,Integer> dao) {  
        super.setDao(dao);  
    }  


	@Override
	public Integer findDataNo(Project project, Date startDate, Date endDate) {
		return projectDao.findDataNo(project, startDate, endDate);
	}

	@Override
	public List<Project> findDatas(Project project, Page page, Date startDate, Date endDate) {
		return projectDao.findDatas(project,(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize(), startDate, endDate);
	}


	@Override
	public void save(Project project, Map<String, String[]> roleUsers) {
		projectDao.save(project);
		if(null!=roleUsers&&roleUsers.size()>0){
			Role role = null;
			for(String key:roleUsers.keySet()){
				role = roleDao.findByShortName(key);
				String[] userIds = roleUsers.get(key);
				for(String id:userIds){
					if(StringUtils.isNotEmpty(id)){
						ProjectUsers projectUsers = new ProjectUsers();
						projectUsers.setCreateTime(new Date());
						projectUsers.setProject(project);
						projectUsers.setRole(role);
						projectUsers.setUser(new User(Integer.valueOf(id)));
						projectUsersDao.save(projectUsers);
					}
				}
			}
		}
		
	}

	@Override
	@CacheEvict(key="#project.id+'_'+#root.targetClass+'_CacheById'",beforeInvocation=true,value="applicationBeanCache")
	public void update(Project project, Map<String, String[]> roleUsers) {
		projectDao.update(project);
		if(null!=roleUsers&&roleUsers.size()>0){
			Role role = null;
			projectUsersDao.deleteByProjectId(project.getId());
			for(String key:roleUsers.keySet()){
				role = roleDao.findByShortName(key);
				String[] userIds = roleUsers.get(key);
				for(String id:userIds){
					if(StringUtils.isNotEmpty(id)){
						ProjectUsers projectUsers = new ProjectUsers();
						projectUsers.setCreateTime(new Date());
						projectUsers.setProject(project);
						projectUsers.setRole(role);
						projectUsers.setUser(new User(Integer.valueOf(id)));
						projectUsersDao.save(projectUsers);
					}
				}
			}
		}
	}


	@Override
	public Project findByName(String name) {
		return projectDao.findByName(name);
	}
}
