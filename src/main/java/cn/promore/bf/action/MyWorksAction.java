package cn.promore.bf.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.Role;
import cn.promore.bf.bean.User;
import cn.promore.bf.model.ActRuTask;
import cn.promore.bf.model.BusBaseEntity;
import cn.promore.bf.model.MyTask;
import cn.promore.bf.mydao.ExecutionEntityMapper;
import cn.promore.bf.serivce.BusBaseService;
import cn.promore.bf.serivce.MyTaskService;
import cn.promore.bf.serivce.SysActModuleService;

@Controller
@Action(value = "myWorksAction")
@ParentPackage("huzdDefault")
@Results({ @Result(name = "tojson", type = "json", params = { "root", "message", "excludeNullProperties", "true" }),
		@Result(name = "taskPage", type = "json") })
public class MyWorksAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8041495674990822812L;
	public static Logger logger = LoggerFactory.getLogger(MyWorksAction.class);
	List<MyTask> MyTaskList = new ArrayList<MyTask>();

	private String s_name;
	private Page page;
	private boolean flag = true;
	private String message;
	private String id;
	private String state;
	@Resource
	private RepositoryService repositoryService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private ExecutionEntityMapper executionEntityMapper;

	private String taskId;
	private String taskName;
	private String startDate;
	private String endDate;
	private String wfStatus;
	private String projectId;
	private String moduleId;
	private String searchType;
	private String type;

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	@Resource
	private FormService formService;
	@Resource
	private TaskService taskService;
	@Resource
	private MyTaskService myTaskService;
	@Resource
	private BusBaseService busBaseService;

	public MyWorksAction() {
		MDC.put("operateModuleName", "代办事项管理");
	}

	public String taskPage() throws ParseException {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		logger.info(user.getUsername() + "--" + user.getId());
		if (null == page)
			page = new Page();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("assignee_", user.getId());
		if (StringUtils.isNoneBlank(taskName)) {
			map.put("title", "'%" + taskName + "%'");
		}
		if (StringUtils.isNoneBlank(startDate)) {
			map.put("startDate", startDate);
		}
		if (StringUtils.isNoneBlank(endDate)) {
			map.put("endDate", endDate);
		}
		if (StringUtils.isNoneBlank(searchType) && searchType != "" && !"".equals(searchType)
				&& !"null".equals(searchType)) {
			map.put("searchType", searchType);
		}

		// 支持 外部查询
		if (StringUtils.isNoneBlank(projectId) && projectId != "" && !"".equals(projectId)
				&& !"null".equals(projectId)) {
			map.put("projectId", projectId);
		}

		if (StringUtils.isNoneBlank(moduleId) && moduleId != "" && !"".equals(moduleId) && !"null".equals(moduleId)) {
			map.put("moduleId", moduleId);
		}
		map.put("pageIndex", (page.getCurrentPage() - 1) * page.getPageSize());
		map.put("pageSize", page.getPageSize());

		page.setTotalRecords(myTaskService.findMyTasks(map));
		List<ActRuTask> list = myTaskService.findMyTasksList(map);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ActRuTask actRuTask : list) {
			MyTask task = new MyTask();
			task.setCreateTime(sdf.parse(actRuTask.getCreate_time_()));
			task.setId(actRuTask.getId_());
			task.setName(actRuTask.getName());
			task.setTitle(actRuTask.getTitle());
			task.setState(actRuTask.getState());
			task.setProcInstId(actRuTask.getProc_inst_id_());
			MyTaskList.add(task);
		}
		logger.info(MyTaskList.size() + "~~");
		flag = true;
		message = "查询成功～";
		return "taskPage";
	}

	public String taskPage4other() throws ParseException {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		logger.info(user.getUsername() + "--" + user.getId());
		if (null == page)
			page = new Page();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("assignee_", user.getId());
		if (StringUtils.isNoneBlank(taskName)) {
			map.put("title", "'%" + taskName + "%'");
		}
		if (StringUtils.isNoneBlank(startDate)) {
			map.put("startDate", startDate);
		}
		if (StringUtils.isNoneBlank(endDate)) {
			map.put("endDate", endDate);
		}
		// 支持 外部查询
		if (StringUtils.isNoneBlank(projectId) && projectId != "" && !"".equals(projectId)
				&& !"null".equals(projectId)) {
			map.put("projectId", projectId);
		}

		if (StringUtils.isNoneBlank(moduleId) && moduleId != "" && !"".equals(moduleId) && !"null".equals(moduleId)) {
			map.put("moduleId", moduleId);
		}
		map.put("pageIndex", (page.getCurrentPage() - 1) * page.getPageSize());
		map.put("pageSize", page.getPageSize());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<ActRuTask> list = new ArrayList<ActRuTask>();
		if (type.equals("faqi")) {
			page.setTotalRecords(myTaskService.findMyTasks4Faqi(map));
			list = myTaskService.findMyTasksList4Faqi(map);
		}
		if (type.equals("over")) {
			page.setTotalRecords(myTaskService.findMyTasks4Over(map));
			list = myTaskService.findMyTasksList4Over(map);
		}

		if (type.equals("done")) {
			page.setTotalRecords(myTaskService.findMyTasks4Done(map));
			list = myTaskService.findMyTasksList4Done(map);
		}

		if (type.equals("done") || type.equals("over")) {
			for (ActRuTask actRuTask : list) {
				MyTask task = new MyTask();
				// task.setCreateTime(sdf.parse(actRuTask.getCreate_time_()));
				task.setStartTime(actRuTask.getStart_time_());
				task.setEndTime2(actRuTask.getEnd_time_());
				task.setId(actRuTask.getId_());
				task.setName(actRuTask.getName());
				task.setTitle(actRuTask.getTitle());
				MyTaskList.add(task);
			}
		} else {
			for (ActRuTask actRuTask : list) {
				MyTask task = new MyTask();
				task.setCreateTime(sdf.parse(actRuTask.getCreate_time_()));
				task.setId(actRuTask.getId_());
				task.setName(actRuTask.getName());
				task.setTitle(actRuTask.getTitle());
				MyTaskList.add(task);
			}
		}
		logger.info(MyTaskList.size() + "~~");
		flag = true;
		message = "查询成功～";
		return "taskPage";
	}

	public String pressTaskPage() throws ParseException {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		logger.info(user.getUsername() + "--" + user.getId());
		if (null == page)
			page = new Page();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		List<Integer> ids = new ArrayList<Integer>();
		Set<String> set = new HashSet<String>();
		if (user != null) {
			List<Role> roles = user.getRoles();
			for (Role role : roles) {
				set.add(role.getShortName());
			}
			List<Map<String, Object>> retList2 = null;
			List<Map<String, Object>> retList3 = null;
			List<Map<String, Object>> retList4 = null;
			List<Map<String, Object>> retList5 = null;
			if (set.contains("xmjl")) {
				Map<String, String> inputMap2 = new HashMap<String, String>();
				inputMap2.put("shortName", "xmzg");
				retList2 = executionEntityMapper.queryUserIdByRoleId(inputMap2);
				retList.addAll(retList2);
				Map<String, String> inputMap3 = new HashMap<String, String>();
				inputMap3.put("shortName", "gcbz");
				retList3 = executionEntityMapper.queryUserIdByRoleId(inputMap3);
				retList.addAll(retList3);
				Map<String, String> inputMap4 = new HashMap<String, String>();
				inputMap4.put("shortName", "jszg");
				retList4 = executionEntityMapper.queryUserIdByRoleId(inputMap4);
				retList.addAll(retList4);
				Map<String, String> inputMap5 = new HashMap<String, String>();
				inputMap5.put("shortName", "jsy");
				retList5 = executionEntityMapper.queryUserIdByRoleId(inputMap5);
				retList.addAll(retList5);
			} else if (set.contains("xmzg")) {
				Map<String, String> inputMap3 = new HashMap<String, String>();
				inputMap3.put("shortName", "gcbz");
				retList3 = executionEntityMapper.queryUserIdByRoleId(inputMap3);
				retList.addAll(retList3);
				Map<String, String> inputMap4 = new HashMap<String, String>();
				inputMap4.put("shortName", "jszg");
				retList4 = executionEntityMapper.queryUserIdByRoleId(inputMap4);
				retList.addAll(retList4);
				Map<String, String> inputMap5 = new HashMap<String, String>();
				inputMap5.put("shortName", "jsy");
				retList5 = executionEntityMapper.queryUserIdByRoleId(inputMap5);
				retList.addAll(retList5);
			} else if (set.contains("gcbz")) {
				Map<String, String> inputMap4 = new HashMap<String, String>();
				inputMap4.put("shortName", "jszg");
				retList4 = executionEntityMapper.queryUserIdByRoleId(inputMap4);
				retList.addAll(retList4);
				Map<String, String> inputMap5 = new HashMap<String, String>();
				inputMap5.put("shortName", "jsy");
				retList5 = executionEntityMapper.queryUserIdByRoleId(inputMap5);
				retList.addAll(retList5);
			} else if (set.contains("jszg")) {
				Map<String, String> inputMap5 = new HashMap<String, String>();
				inputMap5.put("shortName", "jsy");
				retList5 = executionEntityMapper.queryUserIdByRoleId(inputMap5);
				retList.addAll(retList5);
			} else if (roles.contains("jsy")) {

			}
		}

		// map.put("assignee_", user.getId());
		for (Map<String, Object> m : retList) {
			ids.add(Integer.parseInt(m.get("userId").toString()));
		}
		map.put("list", ids);
		if (StringUtils.isNoneBlank(taskName)) {
			map.put("title", "'%" + taskName + "%'");
		}
		if (StringUtils.isNoneBlank(startDate)) {
			map.put("startDate", startDate);
		}
		if (StringUtils.isNoneBlank(endDate)) {
			map.put("endDate", endDate);
		}
		if (StringUtils.isNoneBlank(searchType) && searchType != "" && !"".equals(searchType)
				&& !"null".equals(searchType)) {
			map.put("searchType", searchType);
		}

		// 支持 外部查询
		if (StringUtils.isNoneBlank(projectId) && projectId != "" && !"".equals(projectId)
				&& !"null".equals(projectId)) {
			map.put("projectId", projectId);
		}

		if (StringUtils.isNoneBlank(moduleId) && moduleId != "" && !"".equals(moduleId) && !"null".equals(moduleId)) {
			map.put("moduleId", moduleId);
		}
		map.put("pageIndex", (page.getCurrentPage() - 1) * page.getPageSize());
		map.put("pageSize", page.getPageSize());

		page.setTotalRecords(myTaskService.findMyTasksPress(map));
		List<ActRuTask> list = myTaskService.findMyTasksListPress(map);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ActRuTask actRuTask : list) {
			MyTask task = new MyTask();
			task.setCreateTime(sdf.parse(actRuTask.getCreate_time_()));
			task.setId(actRuTask.getId_());
			task.setName(actRuTask.getName());
			task.setTitle(actRuTask.getTitle());
			task.setEndTime(sdf.parse(actRuTask.getDue_date_()));
			task.setChinesename(actRuTask.getChinesename());
			MyTaskList.add(task);
		}
		logger.info(MyTaskList.size() + "~~");
		flag = true;
		message = "查询成功～";
		return "taskPage";
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Resource
	private SysActModuleService sysActModuleService;

	/**
	 * 重定向审核处理页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String redirectPage() throws Exception {
		TaskFormData formData = formService.getTaskFormData(taskId);
		String url = formData.getFormKey();
		String idstr = "";
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processDefine = task.getProcessDefinitionId();
		logger.info("--processDefine----" + processDefine);
		String keystr = processDefine.split(":")[0];
		String wfKey = "";
		String moduleId = "";
		String processInstanceId = "";
		if (keystr != null && keystr != "") {
			BusBaseEntity obj = this.busBaseService.getBusBaseEntityByTaskId(task.getProcessInstanceId());
			idstr = obj.getId() + "";
			wfKey = obj.getWfKey();
			// moduleId=obj.getModuleId();
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("developKey", wfKey);
			moduleId = this.sysActModuleService.getModuleIdByWfKey(mp);
			processInstanceId = obj.getProcessInstanceId();
		}
		logger.info(url);
		message = url + "&taskId=" + taskId + "&id=" + idstr + "&wfKey=" + wfKey + "&moduleId=" + moduleId
				+ "&processInstanceId=" + processInstanceId + "&busstate=" + state;
		logger.info(message);
		return "tojson";
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<MyTask> getMyTaskList() {
		return MyTaskList;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public void setMyTaskList(List<MyTask> myTaskList) {
		MyTaskList = myTaskList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
