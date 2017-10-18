package cn.promore.bf.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.bean.User;
import cn.promore.bf.serivce.StatisticsService;
import cn.promore.bf.serivce.UserService;
import cn.promore.bf.unit.WebSocketUtil;

@Controller
@Action(value = "sendMessageAction")
@ParentPackage("huzdDefault")
@Results({ @Result(name = "result", type = "json", params = { "root", "message", "excludeNullProperties", "true" }),
		@Result(name = "taskPage", type = "json") })
public class SendMessageAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8041495674990822812L;
	public static Logger logger = LoggerFactory.getLogger(SendMessageAction.class);

	private boolean flag;
	private String message;
	private String taskId;
	private String dueDate;
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private RuntimeService runtimeService;
	
	@Resource 
	private UserService userService;
	
	@Resource
	private StatisticsService statisticsService;

	public SendMessageAction() {
		MDC.put("operateModuleName", "代办事项管理");
	}
	
	public String sendMessage(){
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		logger.info(user.getUsername() + "--" + user.getId());
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String assignee = task.getAssignee();
		String moduleId = "";
		User u = userService.findById(Integer.parseInt(assignee));
		String username = "";
		if(u != null){
			username = u.getUsername();
		}
		List<Map<String, Object>> list = statisticsService.queryModuleIdByTaskId(taskId);
		if(list != null && list.size() > 0){
			Map<String, Object> map = list.get(0);
			moduleId = map.get("moduleId").toString();
		}
		message = username + "：你好，你有工作任务催办，截至日期是"+dueDate+"，请尽快处理。";
		WebSocketUtil.sendMsgToUser(username, "任务催办", message, moduleId);
		return "result";
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	

}
