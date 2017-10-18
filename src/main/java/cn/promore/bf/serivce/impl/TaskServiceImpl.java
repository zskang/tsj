package cn.promore.bf.serivce.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.Task;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.TaskDao;
import cn.promore.bf.serivce.TaskService;

@Service(value="taskService_")
@Transactional(propagation=Propagation.REQUIRED)
public class TaskServiceImpl extends BaseServiceImpl<Task, Integer> implements TaskService {
	@Resource
	private TaskDao taskDao;

	@Override
	@Resource(type=TaskDao.class)
	public void setDao(BaseDao<Task, Integer> baseDao) {
		super.setDao(baseDao);
	}

	public Integer findDataNo(Task task,Date startDate,Date endDate) {
		return taskDao.findDataNo(task,startDate,endDate);
	}

	public List<Task> findDatas(Task task,Date startDate,Date endDate, Page page) {
		return taskDao.findDatas(task,startDate,endDate,(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize());
	}
}
