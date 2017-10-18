package cn.promore.bf.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.serivce.ActInstService;

@Controller
@Action(value = "showViewByProcessInstanceIdAction")
@ParentPackage("huzdDefault")
@Results({ @Result(name = "index", location = "/currentView.jsp", type = "redirect") })
public class ShowViewByProcessInstanceIdAction extends BaseAction {

	@Resource
	private TaskService taskService;
	@Resource
	private RepositoryService repositoryService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private ActInstService actInstService;

	private String ywName;
	private String processInstanceId;

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public static Logger LOG = LoggerFactory.getLogger(ValidateAction.class);
	private static final long serialVersionUID = -8613055080615406396L;

	public String execute() throws Exception {
		//String ywName2 = URLDecoder.decode(request.getParameter("ywName"), "UTF-8");
		String processInstanceId2 = URLDecoder.decode(request.getParameter("processInstanceId"), "UTF-8");
		//System.out.println(ywName2);
		System.out.println(processInstanceId2);
		String processDefinitionId = "";
		processDefinitionId = actInstService.queryObjectByProcessInstanceId(processInstanceId2);
		System.out.println(processDefinitionId);

		if (StringUtils.isNoneBlank(processDefinitionId)) {
			ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
					.getProcessDefinition(processDefinitionId);
			// 根据流程实例id获取流程实例
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId2)
					.singleResult();
			request.getSession().setAttribute("deploymentId", processDefinitionEntity.getDeploymentId());
			request.getSession().setAttribute("diagramResourceName", processDefinitionEntity.getDiagramResourceName());

			// 根据活动id获取活动实例
			ActivityImpl activityImpl = processDefinitionEntity.findActivity(pi.getActivityId());
			// 整理好View视图返回到显示页面
			if (activityImpl != null) {
				request.getSession().setAttribute("x", activityImpl.getX()); // x坐标
				request.getSession().setAttribute("y", activityImpl.getY()); // y坐标
				request.getSession().setAttribute("width", activityImpl.getWidth()); // 宽度
				request.getSession().setAttribute("height", activityImpl.getHeight()); // 高度
				//request.getSession().setAttribute("ywName", ywName2);
			} else {
				// 得到流程执行对象
				List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(pi.getId()).list();
				// 得到正在执行的Activity的Id
				List<String> activityIds = new ArrayList<String>();
				for (Execution exe : executions) {
					List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
					activityIds.addAll(ids);
				}
				for (String id : activityIds) {
					ActivityImpl activity1 = processDefinitionEntity.findActivity(id);
					request.getSession().setAttribute("x", activity1.getX()); // x坐标
					request.getSession().setAttribute("y", activity1.getY()); // y坐标
					request.getSession().setAttribute("width", activity1.getWidth()); // 宽度
					request.getSession().setAttribute("height", activity1.getHeight()); // 高度
				//	request.getSession().setAttribute("ywName", ywName2);
				}
			}
		}
		return "index";
	}

	public String getYwName() {
		return ywName;
	}

	public void setYwName(String ywName) {
		this.ywName = ywName;
	}

}
