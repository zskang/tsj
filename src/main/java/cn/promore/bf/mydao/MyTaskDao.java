package cn.promore.bf.mydao;

import java.util.List;
import java.util.Map;

import cn.promore.bf.model.ActRuTask;

public interface MyTaskDao {

	Integer findMyTasks(Map<String, Object> mp);

	List<ActRuTask> getMyTaskList(Map<String, Object> map);

	Integer findMyTasks4Other(Map<String, Object> map);

	List<ActRuTask> findMyTasksList4Other(Map<String, Object> map);

	void updateTaskExcuters(Map<String, Object> map);
	
	Integer findMyTasksPress(Map<String, Object> mp);

	List<ActRuTask> getMyTaskListPress(Map<String, Object> map);

	void updateHisTaskExcuters(Map<String, Object> map);

	Integer findMyTasks4Faqi(Map<String, Object> map);

	List<ActRuTask> findMyTasksList4Faqi(Map<String, Object> map);

	Integer findMyTasks4Over(Map<String, Object> map);

	List<ActRuTask> findMyTasksList4Over(Map<String, Object> map);

	Integer findMyTasks4Done(Map<String, Object> map);

	List<ActRuTask> findMyTasksList4Done(Map<String, Object> map);

}
