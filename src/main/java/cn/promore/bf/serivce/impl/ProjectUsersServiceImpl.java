package cn.promore.bf.serivce.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.Project;
import cn.promore.bf.bean.ProjectUsers;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.ProjectUsersDao;
import cn.promore.bf.serivce.ProjectUsersService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ProjectUsersServiceImpl extends BaseServiceImpl<ProjectUsers,Integer>  implements ProjectUsersService {
	
	@Resource
	ProjectUsersDao projectUsersDao;
	
	@Resource(type=ProjectUsersDao.class)  
    public void setDao(BaseDao<ProjectUsers,Integer> dao) {  
        super.setDao(dao);  
    }

	@Override
	public List<Project> findUserProjects(Integer id) {
		return projectUsersDao.findUserProjects(id);
	}  
}
