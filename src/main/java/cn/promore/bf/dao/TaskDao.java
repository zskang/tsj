package cn.promore.bf.dao;

import java.util.Date;
import java.util.List;

import cn.promore.bf.bean.Task;

public interface TaskDao extends BaseDao<Task,Integer>{
	Integer findDataNo(Task task,Date startDate,Date endDate);
	List<Task> findDatas(Task task,Date startDate,Date endDate, Integer startIndex,Integer pageSize);
	List<Task> findOneUndealLeastTask(String taskNew);
}
