package cn.promore.bf.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.promore.bf.bean.Project;
import cn.promore.bf.bean.ProjectUsers;
import cn.promore.bf.dao.ProjectUsersDao;

@Repository
public class ProjectUsersDaoImpl extends BaseDaoImpl<ProjectUsers,Integer>  implements ProjectUsersDao {

	@Override
	public void deleteByProjectId(Integer id) {
		this.getSessionFactory().getCurrentSession().createQuery(" delete from ProjectUsers p where p.project.id = :id").setInteger("id",id).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> findUserProjects(Integer id) {
		return this.getSessionFactory().getCurrentSession().createQuery("select distinct u.project from ProjectUsers u where u.user.id = :id").setInteger("id",id).list();
	}
	
}
