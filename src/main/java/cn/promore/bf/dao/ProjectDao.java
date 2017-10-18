package cn.promore.bf.dao;

import java.util.Date;
import java.util.List;

import cn.promore.bf.bean.Project;

public interface ProjectDao extends BaseDao<Project,Integer>{
	/***
	 * 返回总分页条数
	 * @param project 实体变量
	 * @param startDate 计划结束时间 的查询开始时间
	 * @param endDate   计划结束时间 的查询结束时间
	 * @return  总分页条数
	 */
	
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
	List<Project> findDatas(Project project, Integer startIndex,Integer pageSize,Date startDate,Date endDate);

	Project findByName(String name);

}
