package cn.promore.bf.serivce;

import java.util.Date;
import java.util.List;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.Task;

public interface TaskService extends BaseService<Task, Integer>{
	Integer findDataNo(Task task, Date startDate, Date endDate);
	List<Task> findDatas(Task task,Date startDate,Date endDate, Page page);
}
