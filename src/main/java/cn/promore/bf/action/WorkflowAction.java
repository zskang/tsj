package cn.promore.bf.action;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.bean.Page;
import cn.promore.bf.constant.Constant;
import cn.promore.bf.model.ActInst;
import cn.promore.bf.model.Comments;
import cn.promore.bf.model.DeployEntity;
import cn.promore.bf.model.NodeConfigEntity;
import cn.promore.bf.model.SysActModuleEntity;
import cn.promore.bf.serivce.ActInstService;
import cn.promore.bf.serivce.NodeConfigService;
import cn.promore.bf.serivce.StatisticsService;
import cn.promore.bf.serivce.SysActModuleService;
import cn.promore.bf.unit.DateUtil;

/**
 * 工作流相关
 * 
 * @author : zg
 * @date 2017年5月8日 下午8:35:34
 */
@Controller
@Action(value = "workflowAction")
@ParentPackage("huzdDefault")
@Results({ @Result(name = "result", type = "json", params = { "includeProperties", "flag,message" }),
		@Result(name = "nodeConfigEntityList", type = "json", params = { "includeProperties",
				"nodeConfigEntityList\\[\\d+\\]\\.(id|nodeName|datestr|nodeid|wfkey),page\\.(\\w+),flag,message",
				"excludeNullProperties", "true" }),
		@Result(name = "nodeConfigEntity", type = "json", params = { "includeProperties",
				"nodeConfigEntity\\.(id|nodeid|nodeName|datestr|wfkey),flag,message" }),
		@Result(name = "deploylist", type = "json", params = { "includeProperties",
				"deployEntityList\\[\\d+\\]\\.(id|name|deploymentTime|moduleName),page\\.(\\w+),flag,message",
				"excludeNullProperties", "true" }),
		@Result(name = "definelist", type = "json", params = { "includeProperties",
				"processDefinitionList\\[\\d+\\]\\.(id|name|key|version|resourceName|diagramResourceName|deploymentId),page\\.(\\w+),flag,message",
				"excludeNullProperties", "true" }),
		@Result(name = "commentsList", type = "json", params = { "includeProperties",
				"commentsList\\[\\d+\\]\\.(taskid|processinstanceid|time|userid|message|start_time_|end_time_|act_name_|assignee_|assingneeName_),page\\.(\\w+),flag,message",
				"excludeNullProperties", "true" }), })
public class WorkflowAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7126319427830990244L;
	public static Logger logger = LoggerFactory.getLogger(WorkflowAction.class);

	private boolean flag = true;
	private String message;
	private Page page;
	private List<DeployEntity> deployEntityList = new ArrayList<DeployEntity>();

	public List<DeployEntity> getDeployEntityList() {
		return deployEntityList;
	}

	public void setDeployEntityList(List<DeployEntity> deployEntityList) {
		this.deployEntityList = deployEntityList;
	}

	List<ProcessDefinition> processDefinitionList;
	private String s_name;
	private String dataIds;

	private File filedata;
	private String filedataFileName;
	private String filedataContentType;

	private String pProcessInstanceId;

	private NodeConfigEntity nodeConfigEntity;
	private String nodeConfigEntityid;
	private String datestr;

	public String getDatestr() {
		return datestr;
	}

	public void setDatestr(String datestr) {
		this.datestr = datestr;
	}

	public String getNodeConfigEntityid() {
		return nodeConfigEntityid;
	}

	public void setNodeConfigEntityid(String nodeConfigEntityid) {
		this.nodeConfigEntityid = nodeConfigEntityid;
	}

	@Resource
	private ProcessEngine processEngine;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private TaskService taskService;
	@Resource
	private SysActModuleService sysActModuleService;

	@Resource
	private RuntimeService runtimeService;

	@Resource
	private HistoryService historyService;

	@Resource
	private ActInstService actInstService;

	@Resource
	private StatisticsService statisticsService;
	
	private String deploymentId;
	private String diagramResourceName;

	private String processInstanceId;
	private String wfkey;
	private List<Comments> commentsList = new ArrayList<Comments>();

	public List<Comments> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<Comments> commentsList) {
		this.commentsList = commentsList;
	}

	private String selectNodeId;

	public WorkflowAction() {
		MDC.put("operateModuleName", "流程管理");
	}

	public String editNodeConfig() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", nodeConfigEntityid);
			nodeConfigEntity = this.nodeConfigService.getEntity(map);
			nodeConfigEntity.setDatestr(datestr);
			this.nodeConfigService.updateNodeCondig(nodeConfigEntity);
			flag = true;
			message = "操作成功";
		} catch (Exception e) {
			flag = false;
			message = "操作失败";
			e.printStackTrace();
		}
		return "result";
	}

	public String getNodeConfigDetail() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", nodeConfigEntityid);
		nodeConfigEntity = this.nodeConfigService.getEntity(map);
		return "nodeConfigEntity";
	}

	private List<NodeConfigEntity> nodeConfigEntityList;
	@Resource
	private NodeConfigService nodeConfigService;

	public String nodeConfiglist() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wfkey", wfkey);
		nodeConfigEntityList = this.nodeConfigService.queryNodeConfiglist(map);
		if (nodeConfigEntityList.size() == 0) {
			RepositoryService repositoryService1 = processEngine.getRepositoryService();
			ProcessDefinition pd = repositoryService1.createProcessDefinitionQuery().processDefinitionKey(wfkey)
					.latestVersion().singleResult();
			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService1)
					.getDeployedProcessDefinition(pd.getId());
			List<ActivityImpl> activitiList = processDefinition.getActivities();
			for (ActivityImpl activity : activitiList) {
				NodeConfigEntity et = new NodeConfigEntity();
				// logger.info(activity.getId() + "----" +
				// activity.getProperty("name"));
				et.setDatestr("2");// 初始化 为2天
				et.setNodeid(activity.getId());
				et.setNodeName((String) activity.getProperty("name"));
				et.setWfkey(wfkey);
				this.nodeConfigService.saveEntity(et);
			}
		}
		// 再查询一次
		nodeConfigEntityList = this.nodeConfigService.queryNodeConfiglist(map);
		flag = true;
		message = "ok";
		return "nodeConfigEntityList";
	}

	private List<Map<String, Object>> data;

	public String listHistoryCommentWithProcessInstanceId()
			throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		LinkedList<Comment> commentList = new LinkedList<Comment>();
		HistoryService historyService = processEngine.getHistoryService();
		// 使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
		List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()// 历史任务表查询
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.list();
		// 遍历集合，获取每个任务ID
		if (htiList != null && htiList.size() > 0) {
			for (HistoricTaskInstance hti : htiList) {
				// 任务ID
				String htaskId = hti.getId();
				// 获取批注信息
				List<Comment> taskList = taskService.getTaskComments(htaskId);// 对用历史完成后的任务ID
				Collections.reverse(taskList);
				commentList.addAll(taskList);
			}
		}
		for (Comment com : commentList) {
			Comments c = new Comments();
			String commId = com.getId();
			Map<String, Object> typeMap = statisticsService.queryActHiComments(commId);
			String username = "";
			String auditUserId = "";
			if(typeMap != null){
				if(null != typeMap.get("username")){
					username = typeMap.get("username").toString();
				}
				if(null != typeMap.get("userid")){
					auditUserId = typeMap.get("userid").toString();
				}
				
			}
			// 根据taskid 去查询其他的相关信息
			if (StringUtils.isNoneBlank(com.getTaskId())) {
				ActInst actInst = this.actInstService.queryObjByTaskId(com.getTaskId());
				if (actInst != null) {
					if (StringUtils.isNoneBlank(actInst.getAct_name_())) {
						c.setAct_name_(actInst.getAct_name_());
					}
					
					if (StringUtils.isNoneBlank(actInst.getAssignee_())) {
						c.setUserid(actInst.getAssignee_());
					}
					if(StringUtils.isNoneBlank(auditUserId)){
						c.setUserid(auditUserId);
					}
					if (StringUtils.isNoneBlank(actInst.getAssingneeName_())) {
						c.setAssigneeName_(actInst.getAssingneeName_());
					}
					if(!"".equals(username)){
						c.setAssigneeName_(username);
					}
					if (StringUtils.isNoneBlank(actInst.getStart_time_())) {
						c.setStart_time_(actInst.getStart_time_());
					}
					if (StringUtils.isNoneBlank(actInst.getEnd_time_())) {
						c.setEnd_time_(actInst.getEnd_time_());
					}else{
//						c.setEnd_time_("暂无");
						c.setEnd_time_(actInst.getStart_time_());
					}
				}
			}
			if (StringUtils.isNoneBlank(com.getId())) {
				c.setId(com.getId());
			}
			if (StringUtils.isNoneBlank(DateUtil.formatDateYYYY_MM_DD_HH_MM_SS(com.getTime()))) {
				c.setTime(DateUtil.formatDateYYYY_MM_DD_HH_MM_SS(com.getTime()));
			}else{
				c.setTime("");
			}
			if (StringUtils.isNoneBlank(com.getFullMessage())) {
				c.setMessage(com.getFullMessage());
			} else {
				c.setMessage("无");
			}

			if (StringUtils.isNoneBlank(com.getProcessInstanceId())) {
				c.setProcessinstanceid(com.getProcessInstanceId());
			}
			if (StringUtils.isNoneBlank(com.getTaskId())) {
				c.setTaskid(com.getTaskId());
			}
			commentsList.add(c);
		}
		List<Map<String, Object>> mmps = new ArrayList<Map<String, Object>>();
		for (Comments comments : commentsList) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = convertBean(comments);
			mmps.add(map);
		}
		data = mmps;
		logger.info(data.size() + "===size");
		flag = true;
		message = "ok";
		return "commentsList";
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map convertBean(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	public String showView() throws Exception {
		InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, diagramResourceName);
		OutputStream out = response.getOutputStream();
		for (int b = -1; (b = inputStream.read()) != -1;) {
			out.write(b);
		}
		out.close();
		inputStream.close();
		return null;
	}

	/**
	 * 查询部署
	 * 
	 * @return
	 */
	public String deploylist() {
		SecurityUtils.getSubject().checkPermission("workflow:get");
		if (null == page)
			page = new Page();
		long deployCount = repositoryService.createDeploymentQuery().deploymentNameLike("%" + s_name + "%").count();
		page.setTotalRecords(Integer.parseInt(deployCount + ""));
		List<Deployment> deployList = repositoryService.createDeploymentQuery()// 创建流程查询实例
				.orderByDeploymenTime().desc() // 降序
				.deploymentNameLike("%" + s_name + "%") // 根据Name模糊查询
				.listPage((page.getCurrentPage() - 1) * page.getPageSize(), page.getPageSize());
		for (Deployment de : deployList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", de.getId());
			DeployEntity entity = this.actInstService.queryInfoBy(map);
			if (entity != null) {
				deployEntityList.add(entity);
			}
		}
		MDC.put("operateContent", "流程部署查询");
		LOG.info("");
		return "deploylist";
	}

	/**
	 * 获取流程定义
	 * 
	 * @return
	 */
	public String definelist() {
		SecurityUtils.getSubject().checkPermission("workflow:getdefine");
		if (null == page)
			page = new Page();
		long deployCount = repositoryService.createDeploymentQuery().deploymentNameLike("%" + s_name + "%").count();
		page.setTotalRecords(Integer.parseInt(deployCount + ""));
		processDefinitionList = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc()
				.processDefinitionNameLike("%" + s_name + "%")
				.listPage((page.getCurrentPage() - 1) * page.getPageSize(), page.getPageSize());
		MDC.put("operateContent", "流程部署查询");
		LOG.info("");
		return "definelist";
	}

	/**
	 * 部署流程
	 * 
	 * @return
	 */
	public String saveDeploy() {
		SecurityUtils.getSubject().checkPermission("workflow:add");
		flag = true;
		try {
			LOG.info("filedata:" + filedata.getName() + filedata.getPath());
			LOG.info("选择了模块编号：" + selectNodeId);
			String path = ServletActionContext.getServletContext().getRealPath("/admin/upload/attachment/activiti");
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			String fileType = filedataFileName.substring(filedataFileName.lastIndexOf("."));
			if (!".zip".equals(fileType)) {
				flag = flag && false;
				message = "文件格式不正确!请重新选择!";
				return "result";
			}
			String filePath = path + "\\" + filedataFileName;
			try {
				if (new File(filePath).exists()) {
					new File(filePath).delete();
				}
				FileUtils.copyFile(filedata, new File(filePath));
				flag = true;
				MDC.put("operateContent", "用户文件上传：" + filePath);
				LOG.info("");
			} catch (IOException e) {
				e.printStackTrace();
			}
			LOG.info("filePath==" + filePath);
			String workflowName = Constant.getWfNameByFileName(filedataFileName);
			LOG.info("workflowName==" + workflowName);
			// 获取当前class对象
			InputStream inputStream = new FileInputStream(filePath);
			// 实例化zip输入流对象 获取指定文件资源流
			ZipInputStream zipInputStream = new ZipInputStream(inputStream);
			// 添加zip是输入流
			Deployment deployment = repositoryService.createDeployment().name(workflowName)
					.addZipInputStream(zipInputStream).deploy();
			LOG.info("流程部署ID:" + deployment.getId());
			LOG.info("流程部署Name:" + deployment.getName());
			MDC.put("operateContent", "流程部署ID:" + deployment.getId());
			MDC.put("operateContent", "流程部署Name:" + deployment.getName());
			save(selectNodeId, filedataFileName.substring(0, filedataFileName.indexOf(".")));
			flag = flag && true;
			message = "部署成功！";
			LOG.info("");
		} catch (Exception e) {
			flag = flag && false;
			message = e.getMessage();
			e.printStackTrace();
		}
		return "result";
	}

	/**
	 * 保存流程配置与项目模块关系
	 * 
	 * @param moduleId
	 * @param developKey
	 */
	private void save(String moduleId, String developKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("developKey", developKey);
		this.sysActModuleService.deleteByMap(map);
		SysActModuleEntity entity = new SysActModuleEntity();
		entity.setModuleId(moduleId);
		entity.setDevelopKey(developKey);
		this.sysActModuleService.addEntity(entity);
		LOG.info("保存关系成功！");
	}

	/**
	 * 删除部署
	 * 
	 * @return
	 */
	public String deleteDeploy() {
		SecurityUtils.getSubject().checkPermission("workflow:delete");
		String[] aIds = dataIds.split(",");
		flag = true;
		try {
			for (String id : aIds) {
				repositoryService.deleteDeployment(id, true);
				flag = flag && true;
				message = "卸载部署成功！";
				MDC.put("operateContent", "删除部署信息！id:" + id);
				LOG.info("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = flag && false;
			message = e.getMessage();
		}
		return "result";
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public boolean isFlag() {
		return flag;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getDiagramResourceName() {
		return diagramResourceName;
	}

	public void setDiagramResourceName(String diagramResourceName) {
		this.diagramResourceName = diagramResourceName;
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

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}

	public String getFiledataFileName() {
		return filedataFileName;
	}

	public String getWfkey() {
		return wfkey;
	}

	public void setWfkey(String wfkey) {
		this.wfkey = wfkey;
	}

	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}

	public String getFiledataContentType() {
		return filedataContentType;
	}

	public void setFiledataContentType(String filedataContentType) {
		this.filedataContentType = filedataContentType;
	}

	public List<ProcessDefinition> getProcessDefinitionList() {
		return processDefinitionList;
	}

	public void setProcessDefinitionList(List<ProcessDefinition> processDefinitionList) {
		this.processDefinitionList = processDefinitionList;
	}

	public String getDataIds() {
		return dataIds;
	}

	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getpProcessInstanceId() {
		return pProcessInstanceId;
	}

	public void setpProcessInstanceId(String pProcessInstanceId) {
		this.pProcessInstanceId = pProcessInstanceId;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public String getSelectNodeId() {
		return selectNodeId;
	}

	public void setSelectNodeId(String selectNodeId) {
		this.selectNodeId = selectNodeId;
	}

	public List<NodeConfigEntity> getNodeConfigEntityList() {
		return nodeConfigEntityList;
	}

	public void setNodeConfigEntityList(List<NodeConfigEntity> nodeConfigEntityList) {
		this.nodeConfigEntityList = nodeConfigEntityList;
	}

	public NodeConfigEntity getNodeConfigEntity() {
		return nodeConfigEntity;
	}

	public void setNodeConfigEntity(NodeConfigEntity nodeConfigEntity) {
		this.nodeConfigEntity = nodeConfigEntity;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
}
