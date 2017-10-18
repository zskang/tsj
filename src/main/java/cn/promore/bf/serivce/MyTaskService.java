package cn.promore.bf.serivce;

import java.util.List;
import java.util.Map;

import cn.promore.bf.model.ActRuTask;

public interface MyTaskService {

	Integer findMyTasks(Map<String, Object> map);

	List<ActRuTask> findMyTasksList(Map<String, Object> map); 

	Integer findMyTasks4Other(Map<String, Object> map);

	List<ActRuTask> findMyTasksList4Other(Map<String, Object> map);

	void updateTaskExcuters(Map<String, Object> map);
	
	Integer findMyTasksPress(Map<String, Object> map);

	List<ActRuTask> findMyTasksListPress(Map<String, Object> map);

	void updateHisTaskExcuters(Map<String, Object> map);

	Integer findMyTasks4Faqi(Map<String, Object> map);

	List<ActRuTask> findMyTasksList4Faqi(Map<String, Object> map);

	Integer findMyTasks4Over(Map<String, Object> map);

	List<ActRuTask> findMyTasksList4Over(Map<String, Object> map);

	Integer findMyTasks4Done(Map<String, Object> map);

	List<ActRuTask> findMyTasksList4Done(Map<String, Object> map); 

}
