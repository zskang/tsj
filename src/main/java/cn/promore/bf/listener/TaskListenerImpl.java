package cn.promore.bf.listener;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

/**
 * 监听 任务分配 指定人？
 * 
 * @author : Administrator
 * @date 2017年5月16日 下午4:22:29
 */
public class TaskListenerImpl implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2179407920841647390L;

	@Override
	public void notify(DelegateTask delegateTask) {
		String executeInstanceId = delegateTask.getProcessInstanceId();
		String nextLevel = "Nobody";
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

		List<Task> listtask = processEngine.getTaskService().createTaskQuery().executionId(executeInstanceId).list();

		if (listtask.size() > 0 && listtask != null) {

			String lastassignee = listtask.get(0).getAssignee(); // 取得ID

			String prcessName = listtask.get(0).getProcessDefinitionId();

			String[] processDefinitionid = prcessName.split(":");
			String processDefId = processDefinitionid[0];

			List<HistoricTaskInstance> list = processEngine.getHistoryService().createHistoricTaskInstanceQuery()
					.executionId(executeInstanceId).list();

			delegateTask.setAssignee(nextLevel);

		}

	}

}
