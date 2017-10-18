package cn.promore.bf.serivce.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.promore.bf.model.ActRuTask;
import cn.promore.bf.mydao.MyTaskDao;
import cn.promore.bf.serivce.MyTaskService;

@Service
public class MyTaskServiceImpl implements MyTaskService {

	@Resource
	private MyTaskDao dao;

	@Override
	public Integer findMyTasks(Map<String, Object> map) {
		return dao.findMyTasks(map);
	}

	@Override
	public List<ActRuTask> findMyTasksList(Map<String, Object> map) {
		return dao.getMyTaskList(map);
	}

	@Override
	public Integer findMyTasks4Other(Map<String, Object> map) {
		return dao.findMyTasks4Other(map);
	}

	@Override
	public List<ActRuTask> findMyTasksList4Other(Map<String, Object> map) {
		return dao.findMyTasksList4Other(map);
	}

	@Override
	public void updateTaskExcuters(Map<String, Object> map) {
		dao.updateTaskExcuters(map);
	}
	
	@Override
	public Integer findMyTasksPress(Map<String, Object> map) {
		return dao.findMyTasksPress(map);
	}

	@Override
	public List<ActRuTask> findMyTasksListPress(Map<String, Object> map) {
		return dao.getMyTaskListPress(map);
	}

	@Override
	public void updateHisTaskExcuters(Map<String, Object> map) {
		dao.updateHisTaskExcuters(map);
	}

	@Override
	public Integer findMyTasks4Faqi(Map<String, Object> map) {
		return dao.findMyTasks4Faqi(map);
	}

	@Override
	public List<ActRuTask> findMyTasksList4Faqi(Map<String, Object> map) {
		return dao.findMyTasksList4Faqi(map);
	}

	@Override
	public Integer findMyTasks4Over(Map<String, Object> map) {
		return dao.findMyTasks4Over(map);
	}

	@Override
	public List<ActRuTask> findMyTasksList4Over(Map<String, Object> map) {
		return dao.findMyTasksList4Over(map);
	}

	@Override
	public Integer findMyTasks4Done(Map<String, Object> map) {
		return dao.findMyTasks4Done(map);
	}

	@Override
	public List<ActRuTask> findMyTasksList4Done(Map<String, Object> map) {
		return dao.findMyTasksList4Done(map);
	}

}
