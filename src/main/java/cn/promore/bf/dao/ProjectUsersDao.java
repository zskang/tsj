package cn.promore.bf.dao;

import java.util.List;

import cn.promore.bf.bean.Project;
import cn.promore.bf.bean.ProjectUsers;


public interface ProjectUsersDao extends BaseDao<ProjectUsers,Integer>{
	void deleteByProjectId(Integer id);
	List<Project> findUserProjects(Integer id);
}
