package cn.promore.bf.action;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
@Action(value = "showViewAction")
@ParentPackage("huzdDefault")
@Results({ @Result(name = "index", location = "/currentView.jsp", type = "redirect") })
public class ShowViewAction extends BaseAction {

	@Resource
	private TaskService taskService;
	@Resource
	private RepositoryService repositoryService;

	@Resource
	private RuntimeService runtimeService;

	private String ywName;
	private String taskId; 

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public static Logger LOG = LoggerFactory.getLogger(ValidateAction.class);
	private static final long serialVersionUID = -8613055080615406396L;

	public String execute() throws Exception {

		Task task = taskService.createTaskQuery() // 创建任务查询
				.taskId(taskId) // 根据任务id查询
				.singleResult();
		// 获取流程定义id
		String processDefinitionId = task.getProcessDefinitionId();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery() // 创建流程定义查询
				// 根据流程定义id查询
				.processDefinitionId(processDefinitionId).singleResult();
		// 部署id
		// mav.addObject("deploymentId", processDefinition.getDeploymentId());

		request.getSession().setAttribute("deploymentId", processDefinition.getDeploymentId());
		request.getSession().setAttribute("diagramResourceName", processDefinition.getDiagramResourceName());
		// mav.addObject("diagramResourceName",
		// processDefinition.getDiagramResourceName()); // 图片资源文件名称

		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		// 获取流程实例id
		String processInstanceId = task.getProcessInstanceId();
		// 根据流程实例id获取流程实例
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();

		// 根据活动id获取活动实例
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(pi.getActivityId());
		// 整理好View视图返回到显示页面 
		request.getSession().setAttribute("x", activityImpl.getX()); // x坐标
		request.getSession().setAttribute("y", activityImpl.getY()); // y坐标
		request.getSession().setAttribute("width", activityImpl.getWidth()); // 宽度
		request.getSession().setAttribute("height", activityImpl.getHeight()); // 高度
		
		//request.getSession().setAttribute("ywName", ywName);

		return "index";

	}

	public String getYwName() {
		return ywName;
	}

	public void setYwName(String ywName) {
		this.ywName = ywName;
	}

}
