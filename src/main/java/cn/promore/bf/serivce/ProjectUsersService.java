package cn.promore.bf.serivce;

import java.util.List;

import cn.promore.bf.bean.Project;
import cn.promore.bf.bean.ProjectUsers;

public interface ProjectUsersService extends BaseService<ProjectUsers,Integer>{
	List<Project> findUserProjects(Integer id);
}
