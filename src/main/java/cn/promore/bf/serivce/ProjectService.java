package cn.promore.bf.serivce;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.Project;

public interface ProjectService extends BaseService<Project,Integer>{
	
	Integer findDataNo(Project project,Date startDate,Date endDate);
	/**
	 * 查询项目列表
	 * @param project  实体变量
	 * @param startIndex 开始下标
	 * @param pageSize 分页大小
	 * @param startDate 计划结束时间 的查询开始时间
	 * @param endDate 计划结束时间 的查询结束时间
	 * @return 项目列表
	 */
	List<Project> findDatas(Project project,Page page,Date startDate, Date endDate);
	public void save(Project project, Map<String, String[]> roleUsers);
	public void update(Project project, Map<String, String[]> roleUsers);
	Project findByName(String name);
}
