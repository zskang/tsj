package cn.promore.bf.action;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;

import cn.promore.bf.bean.Module;
import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.User;
import cn.promore.bf.constant.Constant;
import cn.promore.bf.model.BusBaseEntity;
import cn.promore.bf.model.BusDocEntity;
import cn.promore.bf.model.FileEntity;
import cn.promore.bf.model.NodeConfigEntity;
import cn.promore.bf.model.TempFileEntity;
import cn.promore.bf.mydao.ExecutionEntityMapper;
import cn.promore.bf.serivce.BusBaseService;
import cn.promore.bf.serivce.BusDocService;
import cn.promore.bf.serivce.EntitySequenceService;
import cn.promore.bf.serivce.FileEntityService;
import cn.promore.bf.serivce.ModuleService;
import cn.promore.bf.serivce.MyTaskService;
import cn.promore.bf.serivce.NodeConfigService;
import cn.promore.bf.serivce.SysActModuleService;
import cn.promore.bf.serivce.TempFileEntityService;
import cn.promore.bf.serivce.UserService;
import cn.promore.bf.unit.DateUtil;
import cn.promore.bf.unit.EntityCodeHelper;
import cn.promore.bf.unit.PropertiesUtils;
import cn.promore.bf.unit.StringUtil;
import cn.promore.bf.unit.UploadHelper;

/**
 * 基础业务处理类
 *
 * @author : zg
 */
@Controller
@Action(value = "busCommonAction")
@ParentPackage("huzdDefault")
@Results({ @Result(name = "fileNotExist", type = "redirect", location = "/404.html"),
		@Result(name = "download", type = "stream", params = { "contentType", "application/octet-stream", "inputName",
				"downloadFile", "bufferSize", "1024", "contentDisposition", "attachment;filename=\"${fileName}\"" }),
		@Result(name = "result", type = "json", params = { "includeProperties", "flag,message" }) })
public class BusCommonAction extends BaseAction {
	/**
	 *
	 */
	private static final long serialVersionUID = 6678949481450568716L;
	private static final String FILE_EXT = "image/png,image/gif,image/jpeg,application/vnd.ms-excel,application/msword,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/kswps,application/kset,application/ksdps,application/zip,application/octet-stream,application/x-zip-compressed";
	public static Logger logger = LoggerFactory.getLogger(BusCommonAction.class);
	private boolean flag;
	private String message;
	private Page page;
	private Integer nextState;
	private Integer previoState;
	private String sfUpload;
	private String sfEdit;
	private String sfTz;
	private String taskId;
	private File[] filedata;
	private String[] filedataFileName;
	private String[] filedataContentType;
	private String projectId;
	private String projectName;
	private String projectType;
	private String moduleId;
	private String wfKey;
	private String state;
	@Resource
	EntitySequenceService entitySequenceService;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	private String comment;
	private String nextName;
	private String doName;
	private String wfName;
	private String nextMan;
	private String isChild;

	private String doType;

	private String users;

	@Resource
	private FileEntityService fileEntityService;
	@Resource
	private TempFileEntityService tempFileService;
	@Resource
	private HistoryService historyService;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private BusDocService busDocService;
	@Resource
	private BusBaseService busBaseService;
	@Resource
	private UserService userService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;

	private String nodes;

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

	@Resource
	private ExecutionEntityMapper executionEntityMapper;
	@Resource
	private ModuleService moduleService;
	// 附件列表
	private List<FileEntity> fileEntities;
	private List<BusBaseEntity> busBaseEntity = new ArrayList<BusBaseEntity>();
	// 业务涉及在线编辑文档列表
	private List<BusDocEntity> docEntities = new ArrayList<BusDocEntity>();
	private BusDocEntity busDocEntity;
	private String processInstanceId;

	public BusCommonAction() {
		MDC.put("operateModuleName", "任务流程管理");
	}

	/**
	 * 保存至项目路径下 按照 原 模块路径来
	 *
	 * @return
	 * @throws IOException
	 */
	public String saveToDirectory() throws IOException {
		try {
			User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
			String docPath = "";
			String templatePath = "";
			String resultPath = "";
			Properties p = PropertiesUtils.getPropertiesByName("config");
			if (p != null) {
				docPath = p.getProperty("docPath");
				templatePath = p.getProperty("templatePath");
			}
			String tempdocPath = busDocEntity.getDocPath();
			if (isWindows()) {
				tempdocPath = tempdocPath.replace("/", "\\");
			}
			System.out.println(tempdocPath);
			// 复制
			String destPath = docPath + File.separator + "project_" + projectId + File.separator
					+ tempdocPath.substring(6, tempdocPath.length());
			// logger.info(destPath);
			String srcFilePath = templatePath + tempdocPath;
			// logger.info(srcFilePath);
			String fileName = busDocEntity.getDocName();
			// logger.info(fileName);
			File srcFile = new File(srcFilePath);
			if (srcFile.exists()) {
				resultPath = UploadHelper.saveFile(destPath, fileName, srcFile);
			}
			// 然后保存入库
			busDocEntity.setBusId(busBaseEntity.get(0).getId() + "");
			busDocEntity.setCreateMan(user.getId() + "");
			busDocEntity.setCreateTime(DateUtil.formatDate("yyyy-MM-dd", new Date()));
			// logger.info(busDocEntity.toString());
			busDocEntity.setState("0");
			this.busDocService.saveEntity(busDocEntity);
			// logger.info("new doc create ");
			flag = true;
			message = resultPath;
		} catch (AuthorizationException e) {
			flag = false;
			message = "不具备添加对应申请权限范围的权限!!!!上传文件格式支持：doc,docx,zip, xlsx,xls,png,gif,jpeg,ppt!";
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			message = e.getMessage();
		}
		return "result";
	}

	/**
	 * 流程处理方法 流程 中 可能 涉及 需要上传附件的 若需要在线编辑 则在编辑时候 直接保存至文件服务区中，流程中无需操作
	 *
	 * @return
	 * @throws IOException
	 */
	public String doWork() throws IOException {
		try {
			List<String> fileExt = Arrays.asList(StringUtils.split(FILE_EXT, ","));
			if (null != filedata && filedata.length > 0) {
				Integer index = 0;
				for (File uploadFile : filedata) {
					JSONObject fileResult = new JSONObject();
					fileResult.put("fileName", filedataFileName[index]);
					if (!UploadHelper.checkFileCanUpload(uploadFile, filedataContentType[index], fileExt)) {
						flag = false;
						message = "不具备添加对应申请权限范围的权限!!!!上传文件格式支持：doc,docx,zip, xlsx,xls,png,gif,jpeg,ppt!";
						return "result";
					}
				}
			}
			if ("Y".equals(sfUpload)) {
				saveFiles(busBaseEntity.get(0).getId());
			}
			String taskExcuter = busBaseEntity.get(0).getTaskExcuter();
			logger.info("taskExcuter======{}", taskExcuter);
			String nextName_ = "";
			if (StringUtil.isNotBlank(nextName) && !"NULL".equals(nextName)) {
				nextName_ = "待" + nextName;
			}
			int res = 0;
			if ("ok".equals(doType)) {
				logger.info("提交操作========nextState==" + nextState + "---comment==" + comment + "---sfTz==" + sfTz
						+ "----taskExcuter==" + taskExcuter + "---nextName_=" + nextName_);
				res = this.doCompleteWork(busBaseEntity.get(0).getId(), taskId, nextName_, taskExcuter,
						comment + "(操作通过)", nextState, sfTz, users, wfKey);
				logger.info("操作结果：" + res + "");
			} else {
				logger.info("驳回操作=========" + previoState + "---" + comment + "----" + taskId);
				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("message", "驳回");
				variables.put("msg", comment + "(操作驳回)");
				Task task = getProcInstId(taskId);
				String processInstanceId = task.getProcessInstanceId();
				String currentTaskDefKey = task.getTaskDefinitionKey();
				HistoricTaskInstance historicTaskInstance = getPreTaskDefinitionKey(processInstanceId,
						currentTaskDefKey);
				String assignee = historicTaskInstance.getAssignee();
				res = 1;
				System.out.println("完成任务：任务ID：" + taskId);
				// 驳回时候 设置下一步操作人 为当前登陆人
				this.doChangeBusState(busBaseEntity.get(0).getId() + "", 3, "N", assignee);
				jumpToTask(taskId, variables);
				logger.info("操作结果：" + res + "");
			}
			if (res == 0) {
				flag = false;
				message = "操作失败！";
				return "result";
			} else {
				flag = true;
				message = "操作成功！";
				return "result";
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			message = "操作失败！";
			return "result";
		}
	}

	@Resource
	private ProcessEngine processEngine;

	private int doCompleteWork(Integer id, String taskId, String note, String taskExcuter, String comment,
			Integer nextState, String sfTz, String userStr, String wfKey) {
		logger.info(
				"##########################################doCompleteWork#################################################");
		int res = 1;
		Map<String, Object> variables = new HashMap<String, Object>();
		try {
			variables.put("msg", note);
			boolean flag = this.doChangeBusState(id + "", nextState, sfTz, taskExcuter);
			if (flag) {
				variables.put("message", "通过");
				variables.put("taskExcuter", taskExcuter);
				String procInstId = "";
				BusBaseEntity entity = this.busBaseService.getBusBaseEntityById(id);
				procInstId = entity.getProcessInstanceId();
				if (99 != nextState) {
					logger.info("0000000000000000000-----" + nextState);
					String nextId = this.getNextNode(entity.getProcessInstanceId());
					int dateVariable = queryDatastrByParam(nextId, wfKey);
					variables.put("dateVariable", DateUtils.addDays(new Date(), dateVariable));
					if ("kzwfcgl_30_process".equals(wfKey)) {
						runtimeService.setVariable(procInstId, "taskExcuter", taskExcuter);
					}
				}
				if ("kzwfcgl_30_process".equals(wfKey)) {// 控制网复测
					List<String> userList = new ArrayList<String>();
					if (userStr != null && !"".equals(userStr)) {
						String[] userArray = userStr.split(", ");
						for (String userId : userArray) {
							userList.add(userId);
						}
						variables.put("taskExcuterList", userList);
					}
				}
				// String taskDefKey =
				// taskService.createTaskQuery().taskId(taskId).singleResult().getTaskDefinitionKey();

				// 添加批注信息
				// variables.put("taskDefKey", taskDefKey);
				processEngine.getTaskService().addComment(taskId, null, comment);
				processEngine.getTaskService()/** 与正在执行的任务管理相关的Service */
						.complete(taskId, variables);
				System.out.println("完成任务：任务ID：" + taskId);
				res = 1;
			} else {
				res = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			res = 0;
		}
		logger.info(
				"#############################################doCompleteWork##############################################");
		return res;
	}

	/**
	 * 获取当前流程的下一个节点
	 * 
	 * @param procInstanceId
	 * @return
	 */
	public String getNextNode(String procInstanceId) {
		// 1、首先是根据流程ID获取当前任务：
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(procInstanceId).list();
		String nextId = "";
		for (Task task : tasks) {
			RepositoryService rs = processEngine.getRepositoryService();
			// 2、然后根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
			ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) rs)
					.getDeployedProcessDefinition(task.getProcessDefinitionId());
			List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
			// 3、根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
			String excId = task.getExecutionId();
			RuntimeService runtimeService = processEngine.getRuntimeService();
			ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
					.singleResult();
			String activitiId = execution.getActivityId();
			// 4、然后循环activitiList
			// 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
			for (ActivityImpl activityImpl : activitiList) {
				String id = activityImpl.getId();
				if (activitiId.equals(id)) {
					logger.debug("当前任务：" + activityImpl.getProperty("name")); // 输出某个节点的某种属性
					List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
					for (PvmTransition tr : outTransitions) {
						PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
						logger.debug("下一步任务任务：" + ac.getProperty("name"));
						nextId = ac.getId();
					}
					break;
				}
			}
		}
		return nextId;
	}

	@Autowired
	private NodeConfigService nodeConfigService;

	private int queryDatastrByParam(String nodeId, String wfKey) {
		int datestr = 2;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nodeId", nodeId);
		map.put("wfkey", wfKey);
		NodeConfigEntity entity = this.nodeConfigService.getEntity(map);
		if (entity != null && StringUtils.isNoneBlank(entity.getDatestr())) {
			datestr = Integer.parseInt(entity.getDatestr());
		}
		return datestr;
	}

	@Resource
	private MyTaskService myTaskService;

	private boolean doChangeBusState(String id, Integer state, String sfTz, String taskExcuter) throws IOException {
		boolean flag2 = false;
		BusBaseEntity entity = this.busBaseService.getBusBaseEntityById(Integer.parseInt(id));
		if (entity != null) {
			// 添加下一步操作人设置
			logger.info("下一步操作人为======" + taskExcuter);
			entity.setTaskExcuter(taskExcuter);
			entity.setState(state + "");
			if ((("gf_14_gx".equals(wfKey) || "tj_14_gx".equals(wfKey)) && state == 4)
					|| ("sfgl_46_process".equals(wfKey) && state == 1)) {
				String projectId_ = projectId;
				// 第一步：再查新流程复核时候 把 收集流程的归档的文件复制到
				// 查找 收集流程已经归档的文件
				Properties p = PropertiesUtils.getPropertiesByName("config");
				String docPath = "";
				String tempPath = "";
				if (p != null) {
					docPath = p.getProperty("docPath");
					tempPath = p.getProperty("tempPath");
					// 扫描下面路径下的文件
					String sourceFilesPath = "";
					if ("gf_14_gx".equals(wfKey)) {
						sourceFilesPath = docPath + File.separator + "project_" + projectId_ + File.separator
								+ "03技术文件管理" + File.separator + "0303规范标准台账" + File.separator + "030301有效技术文件"
								+ File.separator;
					} else if ("tj_14_gx".equals(wfKey)) {
						sourceFilesPath = docPath + File.separator + "project_" + projectId_ + File.separator
								+ "03技术文件管理" + File.separator + "0304图集标准台账" + File.separator + "030401有效技术文件"
								+ File.separator;
					} else if ("sfgl_46_process".equals(wfKey)) {
						sourceFilesPath = docPath + File.separator + "project_" + projectId_ + File.separator
								+ "11过程管理资料" + File.separator + "1108工程数量总控台账" + File.separator + "W110801工程数量总控台账"
								+ File.separator;
					}
					String destFilePath = tempPath + File.separator + "project_" + projectId_ + File.separator;
					File src = new File(sourceFilesPath);
					File destFile_ = new File(destFilePath);
					if (!destFile_.exists()) {
						destFile_.mkdir();
					}
					File[] files = src.listFiles();
					LinkedList<File> list = new LinkedList<File>();
					if (files != null && files.length > 0) {
						for (File file2 : files) {
							if (file2.isDirectory()) {
							} else {
								list.add(file2);
							}
						}

						for (File file2 : files) {
							if (file2.exists() && !file2.getName().equals(".DS_Store") && !file2.isDirectory()) {
								String overPath = UploadHelper.saveFile(destFilePath, null, file2);
								System.out.println(overPath);
								// 插入数据至临时文件表
								TempFileEntity tempFileEntity = new TempFileEntity();
								User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
								tempFileEntity.setCreateMan(user.getId() + "");
								tempFileEntity.setCreateTime(DateUtil.formatDateYYYY_MM_DD_HH_MM_SS(new Date()));
								tempFileEntity.setFileName(file2.getName());
								String remoteEditUrl = "";
								String suffix = file2.getName().substring(file2.getName().lastIndexOf(".") + 1);
								tempFileEntity.setFileType(suffix);
								if ("doc".equals(tempFileEntity.getFileType())
										|| "docx".equals(tempFileEntity.getFileType())) {
									remoteEditUrl = p.getProperty("remoteEditUrl.Word");
								}
								if ("xls".equals(tempFileEntity.getFileType())
										|| "xlsx".equals(tempFileEntity.getFileType())) {
									remoteEditUrl = p.getProperty("remoteEditUrl.Excel");
								}
								if ("pptv".equals(tempFileEntity.getFileType())) {
									remoteEditUrl = p.getProperty("remoteEditUrl.Ppt");
								}
								tempFileEntity.setFilePath(remoteEditUrl + "?filePath=" + overPath);
								tempFileEntity.setModuleId(moduleId);
								tempFileEntity.setProjectId(projectId_);
								tempFileEntity.setYwid(id);

								//tempPath = remoteEditUrl + "?filePath=" + file2.getAbsolutePath();
								//tempFileEntity.setSrcFilePath(tempPath);
								if("gf_14_gx".equals(wfKey) || "tj_14_gx".equals(wfKey)){
									tempFileEntity.setSrcFilePath(overPath);
								}
								if ("sfgl_46_process".equals(wfKey)) {
									tempFileEntity.setSrcFilePath(overPath);
								}
								tempFileEntity.setWfKey(wfKey);
								tempFileEntity.setState("progress");
								tempFileEntity.setLstUpdateMan(user.getId() + "");
								this.tempFileService.saveEntity(tempFileEntity);
							}
						}
					}

				}

			}
			if (state == 99) {
				if ("aqjsjd_36_index".equals(wfKey) || "gxjsjd_36_index".equals(wfKey) || "szgl_18_index".equals(wfKey)
						|| "sgfabzjh_19_do".equals(wfKey)) {
					this.startDetailProcess(id + "");
				}
				if ("jsgzjj_09_index".equals(wfKey)) {
					// 工作交接流程在审核通过之后 需要把 当前人的所有代办事项 转入交接人的，名下
					updateACT_RU_TASK(entity);
					updateBaseInfo(entity);
					updateHisTaskToJJr(entity);
				}

				if ("jsyzlmb_process".equals(wfKey) || "jszgzlmb_process".equals(wfKey)
						|| "gcbzzlmb_process".equals(wfKey) || "xmzgzlmb_process".equals(wfKey)) {
					// 模板流程特殊处理
					dealTemplateProcess(projectId, moduleId, id + "");
				} else if ("shgcsl_45_process".equals(wfKey) || "gcslzg_44_process".equals(wfKey)
						|| "wzzkjhtz_16_do_nd".equals(wfKey) || "wzzkjhtz_16_do_jd".equals(wfKey)
						|| "wzzkjhtz_16_do_yd".equals(wfKey) || "wzzkjhtz_16_do_rwz".equals(wfKey)
						// || "gf_14_gx".equals(wfKey) ||
						// "tj_14_gx".equals(wfKey)
						|| "sgrz_52_process".equals(wfKey) || "sgrzjc_52_process".equals(wfKey)) {
					// 实耗工程数量流程
					// 1. 判断归卷目录是否有文档，如果有删除,临时目录的文件移到归卷目录
					// 2. model目录的文档删除,临时目录的文件移到modle目录tj_14_gx

					User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ywid", id);
					// map.put("moduleId", moduleId);
					// map.put("lstUpdateMan", user.getId() + "");
					String docPath = "";
					String tempPath = "";
					String templatePath = "";
					String modelPath = "";
					Properties p = PropertiesUtils.getPropertiesByName("config");
					if (p != null) {
						docPath = p.getProperty("docPath");
						templatePath = p.getProperty("templatePath");
					}
					// 查询当前人 编辑的人临时 文档
					List<TempFileEntity> tempFileEntitys = this.tempFileService.queryListByMap(map);
					for (TempFileEntity tempEntity : tempFileEntitys) {
						// 复制 文件 从临时文件夹 到 项目文件夹
						String filepath = tempEntity.getFilePath();
						if (filepath != null) {
							String ss[] = filepath.split("filePath");
							if (ss.length > 1) {
								logger.info(ss[0] + "-------" + ss[1].substring(1));
								File file = new File(ss[1].substring(1));
								if (file.exists()) {
									String destFilePath = docPath + File.separator + "project_" + projectId
											+ tempEntity.getSrcFilePath();
									destFilePath = destFilePath.substring(0, destFilePath.lastIndexOf(File.separator))
											+ File.separator;
									logger.info("destFilePath======={}", destFilePath);

									String completedFileName = docPath + File.separator + "project_" + projectId
											+ tempEntity.getSrcFilePath();
									logger.info("completedFileName========{}", completedFileName);
									File completedFile = new File(completedFileName);
									if (completedFile.exists()) {
										completedFile.delete();
									}
									tempPath = UploadHelper.saveFile(destFilePath, null, file);

									// 拼接模板文件的目录
									modelPath = templatePath + File.separator + "model" + tempEntity.getSrcFilePath();
									logger.info("modelPath========{}", modelPath);
									// 判断模板下面是否有文件，如果有先删除
									File modelFile = new File(modelPath);
									if (modelFile.exists()) {
										// modelFile.delete();
									}
									// String modelFilePath =
									// modelPath.substring(0,
									// modelPath.lastIndexOf(File.separator))
									// + File.separator;
									// tempPath =
									// UploadHelper.saveFile(modelFilePath,
									// null, file);

									logger.info(ss[1].substring(1) + "临时文件复制到项目下面了》》》" + destFilePath);
									logger.info(tempPath);
									BusDocEntity busDocEntity = new BusDocEntity();
									busDocEntity.setBusId(id);
									busDocEntity.setCreateMan(user.getId() + "");
									busDocEntity.setCreateTime(DateUtil.formatDate("yyyy-MM-dd", new Date()));
									busDocEntity.setDocName(tempEntity.getFileName());
									busDocEntity.setDocPath(destFilePath);
									busDocEntity.setDocType(tempEntity.getFileName().substring(
											tempEntity.getFileName().indexOf(".") + 1,
											tempEntity.getFileName().length()));
									busDocEntity.setState("0");
									this.busDocService.saveEntity(busDocEntity);
									logger.info("new doc create  ");
								}
							}
						}

					}

					copyTemp2ModelDirectory(id + "");
					deleteTempFiles(id + "");

					logger.info("#########复制临时文件附件至项目下面##########");
					copyFile2ProjectDirectorys(id, projectId);
					logger.info("#########复制临时文件附件至项目下面##########END");
					deleteUploadFiles(id);
					logger.info("#########删除原上传附件目录##########END");
					entity.setSjwcsj(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
				} else {
					// add by zg 2017 09 10 11 50
					// 查新归卷时候 把归档文件复制到收集流程的归档路径下
					if ("tj_14_gx".equals(wfKey) || "gf_14_gx".equals(wfKey) || "sfgl_46_process".equals(wfKey)) {
						copyTemp2CXDirectorys(id + "", wfKey);
					}
					logger.info("#########复制临时文档文件至项目下面##########");
					copyTemp2ProjectDirectory(id + "");
					logger.info("#########复制临时文档文件至项目下面##########END");
					deleteTempFiles(id + "");
					logger.info("#########删除临时文档文件##########END");

					logger.info("#########复制临时文件附件至项目下面##########");
					copyFile2ProjectDirectorys(id, projectId);
					logger.info("#########复制临时文件附件至项目下面##########END");
					deleteUploadFiles(id);
					logger.info("#########删除原上传附件目录##########END");
					entity.setSjwcsj(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
				}
			}
			this.busBaseService.updateBusBaseEntity(entity);
			flag2 = true;
		}
		return flag2;
	}

	/**
	 * 根据业务，需要拷贝一份文档至 收集模块下。
	 * 
	 * @param string
	 * @throws IOException
	 */
	private void copyTemp2CXDirectorys(String ywid, String wfKey) throws IOException {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ywid", ywid);
		String docPath = "";
		String tempPath = "";
		Properties p = PropertiesUtils.getPropertiesByName("config");
		if (p != null) {
			docPath = p.getProperty("docPath");
		}
		// 查询当前人 编辑的人临时 文档
		List<TempFileEntity> tempFileEntitys = this.tempFileService.queryListByMap(map);
		for (TempFileEntity entity : tempFileEntitys) {
			// 复制 文件 从临时文件夹 到 项目文件夹
			String filepath = entity.getFilePath();
			if (filepath != null) {
				String ss[] = filepath.split("filePath");
				if (ss.length > 1) {
					String destFilePath = "";
					logger.info(ss[0] + "-------" + ss[1].substring(1));
					File file = new File(ss[1].substring(1));
					if ("sfgl_46_process".equals(wfKey)) {
						file = new File(entity.getSrcFilePath());
					}
					if (file.exists()) {
						if (wfKey.equals("gf_14_gx")) {
							destFilePath = docPath + File.separator + "project_" + projectId + File.separator
									+ "03技术文件管理" + File.separator + "0303规范标准台账" + File.separator + "030301有效技术文件"
									+ File.separator;
						} else if (wfKey.equals("tj_14_gx")) {
							destFilePath = docPath + File.separator + "project_" + projectId + File.separator
									+ "03技术文件管理" + File.separator + "0304图集标准台账" + File.separator + "030401有效技术文件"
									+ File.separator;
						} else if ("sfgl_46_process".equals(wfKey)) {
							destFilePath = docPath + File.separator + "project_" + projectId + File.separator
									+ "11过程管理资料" + File.separator + "1108工程数量总控台账" + File.separator + "W110801工程数量总控台账"
									+ File.separator;
						}
						destFilePath = destFilePath.substring(0, destFilePath.lastIndexOf(File.separator))
								+ File.separator;
						logger.info(destFilePath);
						tempPath = UploadHelper.saveFile(destFilePath, null, file);
						logger.info(ss[1].substring(1) + "临时文件复制到项目下面了》》》" + destFilePath);
						logger.info(tempPath);
						BusDocEntity busDocEntity = new BusDocEntity();
						busDocEntity.setBusId(ywid);
						busDocEntity.setCreateMan(user.getId() + "");
						busDocEntity.setCreateTime(DateUtil.formatDate("yyyy-MM-dd", new Date()));
						busDocEntity.setDocName(entity.getFileName());
						busDocEntity.setDocPath(destFilePath);
						String hz = entity.getFileName().substring(entity.getFileName().indexOf(".") + 1,
								entity.getFileName().length());
						busDocEntity.setDocType(hz);
						busDocEntity.setState("0");
						this.busDocService.saveEntity(busDocEntity);
					}
				}
			}

		}
	}

	private void updateHisTaskToJJr(BusBaseEntity entity) {
		logger.info("开始更新历史办理任务---至 接交人 ");
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", entity.getUserid());
		map.put("bjjr", entity.getBjjr());
		map.put("projectId", entity.getProjectId());
		this.myTaskService.updateHisTaskExcuters(map);
		logger.info("交接历史任务完毕!");
	}

	private void updateBaseInfo(BusBaseEntity entity) {
		// 修改 任务发起人 为 当前bjjr 修改taskExcuter 为bjjr
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", entity.getUserid());
		map.put("bjjr", entity.getBjjr());
		map.put("projectId", entity.getProjectId());
		this.busBaseService.updateBusBaseEntity4Gzjj(map);

	}

	private void updateACT_RU_TASK(BusBaseEntity entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", entity.getUserid());
		map.put("bjjr", entity.getBjjr());
		map.put("projectId", entity.getProjectId());
		this.myTaskService.updateTaskExcuters(map);

	}

	private void dealTemplateProcess(String projectId, String moduleId, String busid) throws IOException {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectId", projectId);
		map.put("moduleId", moduleId);
		map.put("lstUpdateMan", user.getId() + "");
		// String docPath = "";
		// String tempPath = "";
		String templatePath = "";
		Properties p = PropertiesUtils.getPropertiesByName("config");
		if (p != null) {
			// docPath = p.getProperty("docPath");
			templatePath = p.getProperty("templatePath");
		}

		// 查询
		List<Map<String, Object>> data = this.tempFileService.queryListByMap2(map);
		String projectStr = "project_" + projectId;
		for (Map<String, Object> m : data) {
			String srcFilePath = m.get("srcFilePath").toString();
			String fileName = m.get("fileName").toString();
			logger.info("fileName===={}", fileName);
			String modelPath = templatePath + File.separator + "model"
					+ srcFilePath.substring(srcFilePath.indexOf(projectStr) + projectStr.length());
			logger.info("modelPath===={}", modelPath);
			File file = new File(srcFilePath + fileName);
			String path = File.separator + "model"
					+ srcFilePath.substring(srcFilePath.indexOf(projectStr) + projectStr.length());
			path = path.replaceAll("\\\\", "/");
			logger.info("path===={}", path);
			String docType = fileName.substring(fileName.lastIndexOf(".") + 1);
			logger.info("docType===={}", docType);
			Module module = moduleService.getModuleByPath(path, docType);
			if (module != null) {
				logger.info("module.getId()={}", module.getId());
				Module sysModule = moduleService.findById(module.getId());
				module.setName(fileName);
				module.setDesc(fileName);
				module.setOrder(sysModule.getOrder() + 1);
				module.setParent(sysModule.getParent());
				module.setId(null);
				moduleService.insert(module);
				logger.info("-------insert into sys_module sucess--------");
			}
			UploadHelper.saveFile(modelPath, fileName, file);
		}

	}

	// 删除当前项目下 当前流程实例下 所有已经上传的附件文件数据
	private void deleteUploadFiles(String id) {
		List<FileEntity> attachFileList = this.fileEntityService.queryListByBusId(Integer.parseInt(id));
		for (FileEntity file : attachFileList) {
			String filePath = file.getPath();
			File attachFile = new File(filePath);
			if (attachFile.exists()) {
				attachFile.delete();
			}
			this.fileEntityService.deleteObj(file);
		}
	}

	private void copyFile2ProjectDirectorys(String id, String projectId) {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		String path = "";
		Properties p = PropertiesUtils.getPropertiesByName("config");
		String destFilePath = p.getProperty("docPath") + File.separator + "project_" + projectId + File.separator;
		List<FileEntity> attachFileList = this.fileEntityService.queryListByBusId(Integer.parseInt(id));
		for (FileEntity file : attachFileList) {
			String filePath = file.getPath();
			File attachFile = new File(filePath);
			if (attachFile.exists()) {
				try {
					path = UploadHelper.saveFile(destFilePath,
							UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getFileName()).toString(),
							attachFile);
					BusDocEntity busDocEntity = new BusDocEntity();
					busDocEntity.setBusId(id);
					busDocEntity.setCreateMan(user.getId() + "");
					busDocEntity.setCreateTime(DateUtil.formatDate("yyyy-MM-dd", new Date()));
					busDocEntity.setDocName(file.getFileName());
					busDocEntity.setDocPath(path);
					busDocEntity.setDocType(file.getFileName().substring(file.getFileName().indexOf(".") + 1,
							file.getFileName().length()));
					busDocEntity.setState("1");
					this.busDocService.saveEntity(busDocEntity);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void deleteTempFiles(String ywid) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ywid", ywid);
		List<TempFileEntity> tempFileEntities = this.tempFileService.queryListByMap(map);
		for (TempFileEntity entity : tempFileEntities) {
			String filePath = entity.getFilePath();
			logger.info(filePath);
			if (StringUtils.isNoneBlank(filePath)) {
				String[] fileSourcePath = filePath.split("filePath=");
				if (fileSourcePath.length > 1 && fileSourcePath != null) {
					String soucePath = fileSourcePath[1];
					File file = new File(soucePath);
					if (file.exists()) {
						file.delete();
						logger.info("临时文件" + soucePath + " 已经删除。");
					}

				}
			}

		}

	}

	/**
	 * 发起交底明细任务
	 *
	 * @param id2
	 */
	private void startDetailProcess(String id2) {
		List<BusBaseEntity> list = this.busBaseService.queryListByParentId(Integer.parseInt(id2));
		for (BusBaseEntity entity : list) {
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("id", entity.getId());
			String taskExcuter = entity.getZrrenid();
			logger.info(taskExcuter);
			variables.put("taskExcuter", taskExcuter);
			variables.put("dateVariable", DateUtils.addDays(new Date(), 2));
			logger.info("即将启动子流程：" + entity.getWfKey());
			// 启动流程
			ProcessInstance pi = runtimeService.startProcessInstanceByKey(entity.getWfKey(), variables);
			taskService.addComment(null, pi.getProcessInstanceId(), "系统发起流程");
			// 根据流程实例Id查询任务
			// Task task =
			// taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
			// variables.put("dateVariable", DateUtils.addDays(new Date(), 2));
			// taskService.addComment(task.getId(), null, "系统发起流程");
			// 完成 任务
			// taskService.complete(task.getId(), variables);
			if ("aqjsjd_36_index".equals(wfKey) || "gxjsjd_36_index".equals(wfKey) || "szgl_18_index".equals(wfKey)
					|| "sgfabzjh_19_do".equals(wfKey)) {
				if ("aqjsjd_36_index".equals(wfKey) || "gxjsjd_36_index".equals(wfKey)) {

					entity.setState(Constant.STATE.DBZJDJH);
				}
				if ("szgl_18_index".equals(wfKey) || "sgfabzjh_19_do".equals(wfKey)) {
					entity.setState(Constant.STATE.DBZ);
				}
				entity.setTaskExcuter(taskExcuter);
			}

			// 其他 陆续补充！
			entity.setProcessInstanceId(pi.getProcessInstanceId());
			if (StringUtils.isNoneBlank(nodes)) {
				entity.setNodes(Integer.parseInt(nodes));
			} else {
				entity.setNodes(4);
			}
			this.busBaseService.updateBusBaseEntity(entity);
			// logger.info("启动完一条了====" + entity.getId() + "---taskId===" +
			// task.getId());
		}
	}

	/**
	 * 提交流程
	 *
	 * @return
	 * @throws IOException
	 */
	public String startApply() throws IOException {
		BusBaseEntity obj = this.busBaseService.getBusBaseEntityById(busBaseEntity.get(0).getId());
		if (obj == null) {
			flag = false;
			message = "数据异常！";
			return "result";
		}
		// 对于 子任务 需要 判断 其对应的 父任务是否已经完结
		if ("gxjsjd_36_do".equals(obj.getWfKey()) || "aqjsjd_36_do".equals(obj.getWfKey())
				|| "sggc_01_do".equals(obj.getWfKey())) {
			int parentId = obj.getParentId();
			if (parentId > 0) {
				BusBaseEntity parentObj = this.busBaseService.getBusBaseEntityById(parentId);
				if (parentObj != null) {
					String state = parentObj.getState();
					if (StringUtil.isNotBlank(state) && "99".equals(state)) {
						// 说明 其父任务 已经完结 了 那么 就是子任务 单线走了
					} else {
						flag = false;
						message = "计划任务尚未完结，故不能提交流程！";
						return "result";
					}
				}
			}
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		variables.put("id", busBaseEntity.get(0).getId());
		variables.put("taskExcuter", user.getId());
		variables.put("dateVariable", DateUtils.addDays(new Date(), 2));

		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("ywid", busBaseEntity.get(0).getId() + "");
		map3.put("state", "progress");
		map3.put("oldestate", "edit");
		this.tempFileService.updateEntitysByYwid(map3);
		// 启动流程
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(obj.getWfKey(), variables);
		// taskService.addComment(null, pi.getProcessInstanceId(),
		// user.getChinesename() + "发起流程");
		String taskExcuter = obj.getTaskExcuter();
		variables.put("taskExcuter", taskExcuter);
		// 根据流程实例Id查询任务
		Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
		taskService.addComment(task.getId(), null, user.getChinesename() + "提交流程");
		// 完成任务
		taskService.complete(task.getId(), variables);
		logger.info(pi.getProcessInstanceId());
		setWfState(wfKey, obj);
		obj.setTaskExcuter(taskExcuter);
		obj.setProcessInstanceId(pi.getProcessInstanceId());
		// 更改业务数据状态
		obj.setSjkssj(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
		this.busBaseService.updateBusBaseEntity(obj);
		flag = true;
		message = "操作成功！";
		return "result";
	}

	/**
	 * 根据实际的业务流程 设置 提交流程时候 第一步的等待状态 签字确认
	 *
	 * @param wfKey
	 * @param obj
	 */
	public void setWfState(String wfKey, BusBaseEntity obj) {
		if ("gxjsjd_36_check".equals(obj.getWfKey()) || "aqjsjd_36_check".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DJCTB);
		} else if ("sgdc_01_index".equals(obj.getWfKey()) || "xmglchs_03_do".equals(obj.getWfKey())
				|| "kgbg_04_main".equals(obj.getWfKey()) || "fgbg_04_main".equals(obj.getWfKey())
				|| "tgbg_04_main".equals(obj.getWfKey()) || "szgl_18_index".equals(obj.getWfKey())
				|| "szgl_18_do".equals(obj.getWfKey()) || "gf_14_gx".equals(obj.getWfKey())
				|| "gf_14_sj".equals(obj.getWfKey()) || "tj_14_gx".equals(obj.getWfKey())
				|| "tj_14_sj".equals(obj.getWfKey()) || "jsfwht_15_index".equals(obj.getWfKey())
				|| "wzzkjhtz_16_do_rwz".equals(obj.getWfKey()) || "jsfwht_15_do".equals(obj.getWfKey())
				|| "jsfwht_15_do".equals(obj.getWfKey()) || "gfxzdgcsgfazxqkybb_22_do".equals(obj.getWfKey())
				|| "gjgx_23_main".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DFH);
		} else if ("sgdc_01_do".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DSH);
		} else if ("jxsbxqjh_17_do".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.OVER);
		} else if ("szgl_18_jc".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DGJ);
		} else if ("jsgljd_02".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DZL);
		} else if ("xmglchs_03_main".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DGJ);
		} else if ("jsryxxgwzzjfg_07_main".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DHZJZ);
		} else if ("jsgzjj_09_index".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DQZQR);
		} else if ("wlwjgl_10_index".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DWJZX);
		} else if ("nbwjgl_11_index".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DWJHG);
		} else if ("wjjyjl_12_index".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DWJHG);
		} else if ("wjffjl_13_index".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DFFDJ);
		} else if ("tjsgfa_20_index".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DZGYZ);
		} else if ("IIIjsgfa_21_check".equals(obj.getWfKey())) {
			obj.setState(Constant.STATE.DZGYZ);
		} else {
			obj.setState(Constant.STATE.DSH);
		}
	}

	public String getNextWfState() {
		String nextState = "";
		if ("gxjsjd_36_check".equals(wfKey) || "aqjsjd_36_check".equals(wfKey)) {
			nextState = Constant.STATE.DJCTB;
		} else if ("sgdc_01_index".equals(wfKey) || "xmglchs_03_do".equals(wfKey) || "kgbg_04_main".equals(wfKey)
				|| "fgbg_04_main".equals(wfKey) || "tgbg_04_main".equals(wfKey) || "szgl_18_index".equals(wfKey)
				|| "szgl_18_do".equals(wfKey) || "gf_14_gx".equals(wfKey) || "gf_14_sj".equals(wfKey)
				|| "tj_14_gx".equals(wfKey) || "tj_14_sj".equals(wfKey) || "jsfwht_15_index".equals(wfKey)
				|| "wzzkjhtz_16_do_rwz".equals(wfKey) || "jsfwht_15_do".equals(wfKey) || "jsfwht_15_do".equals(wfKey)
				|| "gfxzdgcsgfazxqkybb_22_do".equals(wfKey) || "gjgx_23_main".equals(wfKey)) {
			nextState = Constant.STATE.DFH;
		} else if ("sgdc_01_do".equals(wfKey)) {
			nextState = Constant.STATE.DSH;
		} else if ("szgl_18_jc".equals(wfKey)) {
			nextState = Constant.STATE.DGJ;
		} else if ("jxsbxqjh_17_do".equals(wfKey)) {
			nextState = Constant.STATE.OVER;
		} else if ("jsgljd_02".equals(wfKey)) {
			nextState = Constant.STATE.DZL;
		} else if ("xmglchs_03_main".equals(wfKey)) {
			nextState = Constant.STATE.DGJ;
		} else if ("jsryxxgwzzjfg_07_main".equals(wfKey)) {
			nextState = Constant.STATE.DHZJZ;
		} else if ("jsgzjj_09_index".equals(wfKey)) {
			nextState = Constant.STATE.DQZQR;
		} else if ("wlwjgl_10_index".equals(wfKey)) {
			nextState = Constant.STATE.DWJZX;
		} else if ("nbwjgl_11_index".equals(wfKey)) {
			nextState = Constant.STATE.DWJHG;
		} else if ("wjjyjl_12_index".equals(wfKey)) {
			nextState = Constant.STATE.DWJHG;
		} else if ("wjffjl_13_index".equals(wfKey)) {
			nextState = Constant.STATE.DFFDJ;
		} else if ("tjsgfa_20_index".equals(wfKey)) {
			nextState = Constant.STATE.DZGYZ;
		} else if ("IIIjsgfa_21_check".equals(wfKey)) {
			nextState = Constant.STATE.DZGYZ;
		} else {
			nextState = Constant.STATE.DSH;
		}
		flag = true;
		message = nextState;
		logger.info("操作成功了");
		return "result";
	}

	/**
	 * 新增页面 操作临时文件 上传附件 保存时候 将临时文件复制 至 项目下面 实际路径下
	 *
	 * @return
	 * @throws IOException
	 */
	public String saveCommonApply() throws IOException {
		try {
			logger.info(busBaseEntity.size() + "==size");
			logger.info(wfKey);
			int parentId = 0;
			int ywid = 0;
			String childTaskExcuter = "";
			String jsjdindex_jhwcsj = "";
			String jsjddo_jhwcsj = "";
			String taskExecuter = "";

			if ("gxjsjd_36_index".equals(wfKey) || "aqjsjd_36_index".equals(wfKey) || "szgl_18_index".equals(wfKey)
					|| "sgfabzjh_19_do".equals(wfKey)) {
				logger.info("####################################################################################");
				for (int y = 0; y < busBaseEntity.size(); y++) {
					BusBaseEntity t4 = busBaseEntity.get(y);
					if (y == 0) {
						if ("gxjsjd_36_index".equals(wfKey) || "aqjsjd_36_index".equals(wfKey)
								|| "szgl_18_index".equals(wfKey) || "sgfabzjh_19_do".equals(wfKey)) {
							jsjdindex_jhwcsj = t4.getJhwcsj1();// 计划完成时间
							logger.info(jsjdindex_jhwcsj);
						}
					}
					if (y >= 1) {
						BusBaseEntity bbEntity = busBaseEntity.get(y);
						if ("aqjsjd_36_index".equals(wfKey) || "gxjsjd_36_index".equals(wfKey)
								|| "szgl_18_index".equals(wfKey) || "sgfabzjh_19_do".equals(wfKey)) {
							// 此时 就是 要获取其子任务的计划完成时间了
							jsjddo_jhwcsj = bbEntity.getJhwcsj2();
							logger.info(jsjddo_jhwcsj);
							logger.info("此时需要做时间的验证");
							int err = compare_date(jsjdindex_jhwcsj + " 59:59", jsjddo_jhwcsj + " 59:59");
							if (err > 0) {
								flag = false;
								message = "计划流程完成时间超过计划编制完成时间！";
								return "result";
							}
						}
					}
				}
			}

			List<String> fileExt = Arrays.asList(StringUtils.split(FILE_EXT, ","));
			if (null != filedata && filedata.length > 0) {
				Integer index = 0;
				for (File uploadFile : filedata) {
					JSONObject fileResult = new JSONObject();
					fileResult.put("fileName", filedataFileName[index]);
					if (!UploadHelper.checkFileCanUpload(uploadFile, filedataContentType[index], fileExt)) {
						flag = false;
						message = "不具备添加对应申请权限范围的权限!!!!上传文件格式支持：doc,docx,zip,xlsx,xls,png,gif,jpeg,ppt!";
						return "result";
					}
				}
			}
			boolean b = false;
			logger.info("####################################################################################");
			BusBaseEntity entity = new BusBaseEntity();
			for (int i = 0; i < busBaseEntity.size(); i++) {
				BusBaseEntity t = busBaseEntity.get(i);
				// add new
				if (i == 0) {
					taskExecuter = t.getTaskExcuter();
					entity = t;
					if ("gxjsjd_36_index".equals(wfKey) || "aqjsjd_36_index".equals(wfKey)) {
						t.setJdsj(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
						jsjdindex_jhwcsj = t.getJhwcsj1();// 计划完成时间
						logger.info(jsjdindex_jhwcsj);
					}
					if ("szgl_18_index".equals(wfKey)) {
						childTaskExcuter = t.getTaskExcuter();
					}
					t.setZrrenid(t.getTaskExcuter());
					if ("gxjsjd_36_do".equals(wfKey) || "aqjsjd_36_do".equals(wfKey) || "szgl_18_do".equals(wfKey)) {
						t.setTaskExcuter(t.getTaskExcuter());
					}

					if ("jsgzjj_09_index".equals(wfKey)) {
						User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
						if (t.getTaskExcuter().equals(user.getId())) {
							flag = false;
							message = "接交人不能是自己！";
							continue;

						}
						t.setBjjr(t.getTaskExcuter());
					}
					t.setWfKey(wfKey);
					setCommonValues(t);
					this.busBaseService.saveBusBaseEntity(t);
					parentId = t.getId();
					ywid = t.getId();
					if ("gxjsjd_36_index".equals(wfKey) || "aqjsjd_36_index".equals(wfKey)
							|| "szgl_18_index".equals(wfKey)) {
					} else {
						if (!"create".equals(doType)) {
							User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
							Map<String, Object> map3 = new HashMap<String, Object>();
							map3.put("projectId", projectId);
							map3.put("moduleId", moduleId);
							map3.put("createMan", user.getId() + "");
							map3.put("wfKey", wfKey);
							map3.put("state", state);
							map3.put("newstate", "edit");
							map3.put("ywid", ywid);
							this.tempFileService.updateEntitys2(map3);
						}
					}
				}
				logger.info(parentId + "");
				if (i >= 1) {// 有子任务 则需要保存 子业务基本信息
					// 然后从1 开始 循环 保存入库。
					BusBaseEntity bbEntity = busBaseEntity.get(i);
					bbEntity.setWfKey(getChildWfKeyBy(wfKey, bbEntity.getFadj()));
					if ("aqjsjd_36_do".equals(wfKey) || "gxjsjd_36_do".equals(wfKey)) {
						jsjddo_jhwcsj = bbEntity.getJhwcsj2();
						int err = compare_date(jsjddo_jhwcsj, jsjdindex_jhwcsj);
						if (err > 0) {
							break;
						}
					}
					setCommonValues(bbEntity);
					bbEntity.setJhkssj(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
					bbEntity.setParentId(parentId);
					if ("szgl_18_index".equals(wfKey)) {
						bbEntity.setTaskExcuter(childTaskExcuter);
					}
					if ("gxjsjd_36_index".equals(wfKey) || "aqjsjd_36_index".equals(wfKey)
							|| "szgl_18_index".equals(wfKey)) {
						bbEntity.setTaskExcuter(t.getTaskExcuter());
					}
					this.busBaseService.saveBusBaseEntity(bbEntity);
					logger.info("生成 新的 childId=" + bbEntity.getId());
				}
				b = true;
			}
			if ("Y".endsWith(sfUpload)) {
				saveFiles(ywid);
			}
			// 由于需求改动， 这一步 放 流程最后一步来做 update by zhougang 2017 07 15
			// copyTemp2ProjectDirectory(projectId, moduleId, ywid + "");
			if (b) {
				logger.info(doType);
				if ("create".equals(doType)) {
					logger.info("创建并发起流程...");
					Map<String, Object> variables = new HashMap<String, Object>();
					User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
					variables.put("id", entity.getId());
					variables.put("taskExcuter", user.getId());
					variables.put("dateVariable", DateUtils.addDays(new Date(), 2));
					// 启动流程
					ProcessInstance pi = runtimeService.startProcessInstanceByKey(wfKey, variables);
					// taskService.addComment(null, pi.getDeploymentId(),
					// "系统发起流程");
					variables.put("taskExcuter", taskExecuter);
					// 根据流程实例Id查询任务
					Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId())
							.singleResult();
					taskService.addComment(task.getId(), null, "系统提交流程");
					// 完成任务
					taskService.complete(task.getId(), variables);
					logger.info(pi.getProcessInstanceId());
					setWfState(wfKey, entity);
					entity.setProcessInstanceId(pi.getProcessInstanceId());
					// 更改业务数据状态
					entity.setSjkssj(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
					this.busBaseService.updateBusBaseEntity(entity);
					Map<String, Object> map3 = new HashMap<String, Object>();
					map3.put("ywid", ywid + "");
					map3.put("state", "progress");
					map3.put("oldestate", "add");
					map3.put("oldywid", "add");
					map3.put("wfKey", wfKey);
					map3.put("projectId", projectId);
					map3.put("moduleId", moduleId);
					this.tempFileService.updateEntitysByYwid(map3);
					logger.info("创建并发起流程...end");

					if ("swtz_33_process".equals(wfKey) || "jxsbxqjh_17_do".equals(wfKey)) {
						boolean flag = this.doChangeBusState(entity.getId() + "", 99, "Y", taskExecuter);
						logger.info("------flag={}", flag);
						logger.info("------收文台账处理结束");
					}
				}
				flag = true;
				message = "操作成功！";
				logger.info("操作成功了");
			}
		} catch (AuthorizationException e) {
			flag = false;
			message = "不具备添加对应申请权限范围的权限";
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			message = e.getMessage();
		}
		return "result";
	}

	public static int compare_date(String DATE1, String DATE2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				// System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				// System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 复制文件到 项目实际文件夹下
	 *
	 * @return
	 * @throws Exception
	 */
	public String copyFileToProjectDirectoryPath() throws Exception {
		logger.info(busDocEntity.getDocPath());
		logger.info(busDocEntity.getDocName());
		logger.info(projectId);
		logger.info(moduleId);
		logger.info(projectType);
		String path = "";
		Properties p = PropertiesUtils.getPropertiesByName("config");
		// 此时需要讲 传入的文件 安装 docpath 复制到 项目里面 对应到路径下
		File file = new File(busDocEntity.getDocPath());
		String destFilePath = p.getProperty("docPath") + File.separator + "project_" + projectId + File.separator
				+ busDocEntity.getDocPath().substring(6, busDocEntity.getDocPath().length());
		logger.info(destFilePath);
		if (file.exists()) {
			path = UploadHelper.saveFile(destFilePath,
					UUID.randomUUID() + "." + FilenameUtils.getExtension(busDocEntity.getDocName()).toString(), file);
		}
		flag = true;
		// 返回最新的路径回来
		message = path;
		return "result";
	}

	/**
	 * 修改数据
	 *
	 * @return
	 * @throws IOException
	 */
	public String updateCommonApply() throws IOException {
		try {
			logger.info(busBaseEntity.size() + "==size");
			int size = busBaseEntity.size();
			int parentId = 0;
			// 先清理子任务数据
			if (size >= 1) {
				cleanChildObjects(busBaseEntity);
			}
			String jsjdindex_jhwcsj = "";
			String taskExcuter = "";
			String jsjddo_jhwcsj = "";
			if ("gxjsjd_36_index".equals(wfKey) || "aqjsjd_36_index".equals(wfKey)) {
				logger.info("####################################################################################");
				for (int y = 0; y < busBaseEntity.size(); y++) {
					BusBaseEntity t4 = busBaseEntity.get(y);
					if (y == 0) {
						if ("gxjsjd_36_index".equals(wfKey) || "aqjsjd_36_index".equals(wfKey)) {
							jsjdindex_jhwcsj = t4.getJhwcsj1();// 计划完成时间
							logger.info(jsjdindex_jhwcsj);
						}
					}
					if (y >= 1) {
						BusBaseEntity bbEntity = busBaseEntity.get(y);
						if ("aqjsjd_36_index".equals(wfKey) || "gxjsjd_36_index".equals(wfKey)) {
							// 此时 就是 要获取其子任务的计划完成时间了
							jsjddo_jhwcsj = bbEntity.getJhwcsj2();
							logger.info(jsjddo_jhwcsj);
							logger.info("此时需要做时间的验证");
							int err = compare_date(jsjdindex_jhwcsj + " 59:59", jsjddo_jhwcsj + " 59:59");
							if (err > 0) {
								flag = false;
								message = "原计划完成时间不能大于技术交底执行任务中的计划完成时间！！";
								return "result";
							}
						}
					}
				}
			}
			List<String> fileExt = Arrays.asList(StringUtils.split(FILE_EXT, ","));
			if (null != filedata && filedata.length > 0) {
				Integer index = 0;
				for (File uploadFile : filedata) {
					JSONObject fileResult = new JSONObject();
					fileResult.put("fileName", filedataFileName[index]);
					if (!UploadHelper.checkFileCanUpload(uploadFile, filedataContentType[index], fileExt)) {
						flag = false;
						message = "不具备添加对应申请权限范围的权限!!!!上传文件格式支持：doc,docx,zip, xlsx,xls,png,gif,jpeg,ppt!";
						return "result";
					}
				}
			}
			BusBaseEntity entity = new BusBaseEntity();
			logger.info("####################################################################################");
			// 再修改 新增数据
			for (int i = 0; i < busBaseEntity.size(); i++) {
				if (i == 0) {
					BusBaseEntity t = busBaseEntity.get(i);
					entity = t;
					if ("aqjsjd_36_do".equals(wfKey) || "gxjsjd_36_do".equals(wfKey)
							|| "IIIjsgfa_21_index".equals(wfKey) || "tjsgfa_20_index".equals(wfKey)
							|| "szgl_18_do".equals(wfKey)) {
						if ("save".equals(doType)) {
							t.setTaskExcuter(t.getZrrenid());
							taskExcuter = t.getZrrenid();
						} else {
							logger.info(t.getTaskExcuter());
							taskExcuter = t.getTaskExcuter();
						}
						if ("jsgzjj_09_index".equals(wfKey)) {
							t.setBjjr(t.getTaskExcuter());
						}
					} else {
						logger.info(t.getTaskExcuter());
						taskExcuter = t.getTaskExcuter();
						// 如果流程实例Id不为空，并且调用次方法中，说明是驳回后点击修改保存的，那么这个地方就不能修改bus_base_info表的taskExcuter字段
						if (StringUtil.isNotBlank(processInstanceId)) {
							System.out.println("------------processInstanceId 不为空----------");
							t.setTaskExcuter(null);
						}
					}
					logger.info("t===" + t.toString());
					setCommonValues(t);
					this.busBaseService.updateBusBaseEntity(t);
					saveFiles(t.getId());
					parentId = t.getId();
				} else {
					if (i >= 1) {// 有子任务 则需要保存 子业务基本信息
						// 然后从1 开始 循环 保存入库。
						BusBaseEntity bbEntity = busBaseEntity.get(i);
						bbEntity.setWfKey(getChildWfKeyBy(wfKey, bbEntity.getFadj()));
						setCommonValues(bbEntity);
						bbEntity.setJhkssj(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
						bbEntity.setParentId(parentId);
						this.busBaseService.saveBusBaseEntity(bbEntity);
						logger.info("生成 新的 childId=" + bbEntity.getId());
						saveFiles(bbEntity.getId());
					}
				}
			}
			logger.info("当前的操作类型是：" + doType);
			if ("submit".equals(doType)) {
				logger.info("编辑保存完 还需要提交到下一步!");
				this.doCompleteWork(parentId, taskId, "提交流程！！！", taskExcuter, "提交流程", nextState, sfTz, "", wfKey);
				Map<String, Object> map3 = new HashMap<String, Object>();
				map3.put("ywid", parentId + "");
				map3.put("state", "progress");
				map3.put("oldestate", "edit");
				map3.put("oldywid", parentId);
				this.tempFileService.updateEntitysByYwid(map3);
			} else if ("save".equals(doType)) {
				logger.info("编辑保存了");
			} else if ("create".equals(doType)) {
				logger.info("编辑保存了，直接发起流程了..");
				logger.info("创建并发起流程...");
				Map<String, Object> variables = new HashMap<String, Object>();
				User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
				variables.put("id", parentId);
				variables.put("taskExcuter", user.getId());
				variables.put("dateVariable", DateUtils.addDays(new Date(), 2));
				// 启动流程
				ProcessInstance pi = runtimeService.startProcessInstanceByKey(wfKey, variables);
				// taskService.addComment(null, pi.getProcessInstanceId(),
				// user.getChinesename() + "发起流程");
				variables.put("taskExcuter", taskExcuter);
				// 根据流程实例Id查询任务
				Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
				taskService.addComment(task.getId(), null, user.getChinesename() + "修改并发起流程！");
				// 完成任务
				taskService.complete(task.getId(), variables);
				logger.info(pi.getProcessInstanceId());
				setWfState(wfKey, entity);
				entity.setProcessInstanceId(pi.getProcessInstanceId());
				// 更改业务数据状态
				entity.setSjkssj(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
				this.busBaseService.updateBusBaseEntity(entity);
				Map<String, Object> map3 = new HashMap<String, Object>();
				map3.put("ywid", parentId + "");
				map3.put("state", "progress");
				map3.put("oldestate", "edit");
				map3.put("oldywid", parentId + "");
				map3.put("wfKey", wfKey);
				map3.put("projectId", projectId);
				map3.put("moduleId", moduleId);
				this.tempFileService.updateEntitysByYwid(map3);
				logger.info("创建并发起流程...end");
			}
			flag = true;
			message = "操作成功！";
			logger.info("操作成功了");
		} catch (AuthorizationException e) {
			flag = false;
			message = "不具备添加对应申请权限范围的权限";
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			message = e.getMessage();
		}
		return "result";
	}

	private void cleanChildObjects(List<BusBaseEntity> bbentity) {
		int parentId = 0;
		for (int i = 0; i < busBaseEntity.size(); i++) {
			if (i == 0) {
				BusBaseEntity entity = busBaseEntity.get(i);
				parentId = entity.getId();
			}
		}
		logger.info("找到了父id：" + parentId + "，将对此父业务下的所有子业务数据 进行清理！！");
		List<BusBaseEntity> childObjects = this.busBaseService.queryListByParentId(parentId);
		if (childObjects.size() > 0) {
			for (BusBaseEntity bbt : childObjects) {
				this.busBaseService.deleteBusBaseEntity(bbt);
				logger.info("清理了:" + bbt.getYwName() + "-----" + bbt.getId());
			}
			logger.info("清理完毕了。");
		}
	}

	/**
	 * 删除文档与业务关系 和 文件
	 *
	 * @param id
	 */

	@SuppressWarnings("unused")
	private void removeDocsByBusId(int id) {
		// 1.删除文件
		// 2.删除关联关系
		Map<String, Object> map = new HashMap<>();
		map.put("busId", id);
		List<BusDocEntity> list = this.busDocService.queryDocListByMap(map);
		for (BusDocEntity entity : list) {
			String path = entity.getDocPath();
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}
		this.busDocService.removeAll(map);
	}

	@SuppressWarnings("unused")
	private void removeAllAttachFiles(int ywid) {
		Map<String, Object> mp = new HashMap<>();
		mp.put("ywid", ywid);
		logger.info("ywid====" + ywid);
		List<FileEntity> fileEntities = this.fileEntityService.queryListByBusId(ywid);
		for (FileEntity fileEntity : fileEntities) {
			logger.info(fileEntity.getFileName() + "--" + fileEntity.getPath());
			File file = new File(fileEntity.getPath());
			if (file.exists()) {
				file.delete();
			}
		}
		this.fileEntityService.removeAllAttachFiles(mp);
	}

	private void copyTemp2ModelDirectory(String ywid) throws IOException {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ywid", ywid);
		// map.put("moduleId", moduleId);
		// map.put("lstUpdateMan", user.getId() + "");
		String docPath = "";
		String tempPath = "";
		Properties p = PropertiesUtils.getPropertiesByName("config");
		if (p != null) {
			docPath = p.getProperty("docPath");
		}
		// 查询当前人 编辑的人临时 文档
		List<TempFileEntity> tempFileEntitys = this.tempFileService.queryListByMap(map);
		for (TempFileEntity entity : tempFileEntitys) {
			// 复制 文件 从临时文件夹 到 项目文件夹
			String filepath = entity.getFilePath();
			if (filepath != null) {
				String ss[] = filepath.split("filePath");
				if (ss.length > 1) {
					logger.info(ss[0] + "-------" + ss[1].substring(1));
					File file = new File(ss[1].substring(1));
					if (file.exists()) {
						String destFilePath = docPath + File.separator + "project_" + projectId
								+ entity.getSrcFilePath();
						destFilePath = destFilePath.substring(0, destFilePath.lastIndexOf(File.separator))
								+ File.separator;
						logger.info(destFilePath);
						tempPath = UploadHelper.saveFile(destFilePath, null, file);
						logger.info(ss[1].substring(1) + "临时文件复制到项目下面了》》》" + destFilePath);
						logger.info(tempPath);
						BusDocEntity busDocEntity = new BusDocEntity();
						busDocEntity.setBusId(ywid);
						busDocEntity.setCreateMan(user.getId() + "");
						busDocEntity.setCreateTime(DateUtil.formatDate("yyyy-MM-dd", new Date()));
						busDocEntity.setDocName(entity.getFileName());
						busDocEntity.setDocPath(destFilePath);
						busDocEntity.setDocType(entity.getFileName().substring(entity.getFileName().indexOf(".") + 1,
								entity.getFileName().length()));
						busDocEntity.setState("0");
						// this.busDocService.saveEntity(busDocEntity);
						logger.info("new doc create  ");
					}
				}
			}

		}
	}

	/**
	 * 保存时候 将临时文件 复制到 项目路径下
	 *
	 * @param projectId
	 * @param moduleId
	 * @param ywid
	 * @throws IOException
	 */
	private void copyTemp2ProjectDirectory(String ywid) throws IOException {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ywid", ywid);
		// map.put("moduleId", moduleId);
		// map.put("lstUpdateMan", user.getId() + "");
		String docPath = "";
		String tempPath = "";
		Properties p = PropertiesUtils.getPropertiesByName("config");
		if (p != null) {
			docPath = p.getProperty("docPath");
		}
		// 查询当前人 编辑的人临时 文档
		List<TempFileEntity> tempFileEntitys = this.tempFileService.queryListByMap(map);
		for (TempFileEntity entity : tempFileEntitys) {
			// 复制 文件 从临时文件夹 到 项目文件夹
			String filepath = entity.getFilePath();
			if (entity.getSrcFilePath().contains("temp")) {
				continue;
			}
			if (filepath != null) {
				String ss[] = filepath.split("filePath");
				if (ss.length > 1) {
					logger.info(ss[0] + "-------" + ss[1].substring(1));
					File file = new File(ss[1].substring(1));
					if (file.exists()) {
						String destFilePath = docPath + File.separator + "project_" + projectId
								+ entity.getSrcFilePath();
						destFilePath = destFilePath.substring(0, destFilePath.lastIndexOf(File.separator))
								+ File.separator;
						logger.info(destFilePath);
						tempPath = UploadHelper.saveFile(destFilePath, null, file);
						logger.info(ss[1].substring(1) + "临时文件复制到项目下面了》》》" + destFilePath);
						logger.info(tempPath);
						BusDocEntity busDocEntity = new BusDocEntity();
						busDocEntity.setBusId(ywid);
						busDocEntity.setCreateMan(user.getId() + "");
						busDocEntity.setCreateTime(DateUtil.formatDate("yyyy-MM-dd", new Date()));
						busDocEntity.setDocName(entity.getFileName());
						busDocEntity.setDocPath(destFilePath);
						busDocEntity.setDocType(entity.getFileName().substring(entity.getFileName().indexOf(".") + 1,
								entity.getFileName().length()));
						busDocEntity.setState("0");
						this.busDocService.saveEntity(busDocEntity);
						logger.info("new doc create  ");
					}
				}
			}

		}
	}

	/**
	 * 保存附件信息 多附件存储
	 *
	 * @param ywid
	 * @return
	 */
	public boolean saveFiles(int ywid) {
		String distDir = "project_" + projectId;
		boolean flag = true;
		String distFilePath = "";
		Properties p = PropertiesUtils.getPropertiesByName("config");
		if (p != null) {
			distFilePath = p.getProperty("uploadFilePath") + File.separator + distDir + File.separator
					+ UUID.randomUUID() + File.separator;
		}
		logger.info(distFilePath);
		List<String> fileExt = Arrays.asList(StringUtils.split(FILE_EXT, ","));
		if (null != filedata && filedata.length > 0) {
			String path = "";
			Integer index = 0;
			for (File uploadFile : filedata) {
				JSONObject fileResult = new JSONObject();
				fileResult.put("fileName", filedataFileName[index]);
				if (UploadHelper.checkFileCanUpload(uploadFile, filedataContentType[index], fileExt)) {
					try {
						String fileExt_ = FilenameUtils.getExtension(filedataFileName[index]).toString();
						path = UploadHelper.saveFile(distFilePath, UUID.randomUUID() + "."
								+ FilenameUtils.getExtension(filedataFileName[index]).toString(), uploadFile);
						logger.info(path);
						logger.info(filedataFileName[index]);
						saveYwFileRelations(ywid, path, filedataFileName[index], fileExt_);
						flag = true;
					} catch (IOException e) {
						flag = false;
						break;
						// fileResult.put("flag", "false");
						// fileResult.put("path", path);
						// e.printStackTrace();
					}
				} else {
					flag = false;
					break;
					// fileResult.put("flag", "false");
					// fileResult.put("path", path);
				}
				index++;
			}
		}
		return flag;
	}

	/**
	 * 保存业务与附件的关系
	 *
	 * @param ywid
	 * @param path
	 * @param fileName
	 * @param fileExt_
	 */
	private void saveYwFileRelations(int ywid, String path, String fileName, String fileExt_) {
		FileEntity entity = new FileEntity();
		entity.setYwid(ywid);
		entity.setPath(path);
		entity.setFileName(fileName);
		entity.setFileExt(fileExt_);
		entity.setScsj(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
		this.fileEntityService.saveEntity(entity);
		logger.info("保存了 附件 ：" + entity.toString());
	}

	private String getChildWfKeyBy(String wfKey, String fadj) {
		String childWfKey = "";
		if (wfKey.equals(Constant.WFKEY.工序技术交底_计划)) {
			childWfKey = Constant.WFKEY.工序技术交底_执行;
		}
		if (wfKey.equals(Constant.WFKEY.安全技术交底_计划)) {
			childWfKey = Constant.WFKEY.安全技术交底_执行;
		}

		if (wfKey.equals(Constant.WFKEY.施组_计划)) {
			childWfKey = Constant.WFKEY.施组_编制;
		}
		if (wfKey.equals(Constant.WFKEY.施工方案编制计划)) {
			if (fadj.equals("3")) {
				childWfKey = Constant.WFKEY.III;
			} else {
				childWfKey = Constant.WFKEY.特级_I_II;
			}
		}
		return childWfKey;
	}

	@Resource
	private SysActModuleService sysActModuleService;

	private void setCommonValues(BusBaseEntity entity) {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		entity.setProcessInstanceId("");
		entity.setProjectId(projectId);
		entity.setProjectName(projectName);
		entity.setState(Constant.STATE.DTJ);
		entity.setProjectType(projectType);
		entity.setPubTime(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
		entity.setUserid(user.getId() + "");
		String str = entity.getWfKey() + ".zip";
		String ywName = Constant.getWfNameByFileName(str);
		if (StringUtil.isNotBlank(ywName)) {
			entity.setYwName(projectName + "-" + ywName + "-" + DateUtil.formatDate("yyyyMMddHHmmss", new Date()));
		} else {
			entity.setYwName("");
		}
		String mod = moduleId;
		if ("aqjsjd_36_do".equals(entity.getWfKey()) || "gxjsjd_36_do".equals(entity.getWfKey())
				|| "IIIjsgfa_21_index".equals(entity.getWfKey()) || "tjsgfa_20_index".equals(entity.getWfKey())
				|| "szgl_18_do".equals(entity.getWfKey())) {
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("developKey", entity.getWfKey());
			mod = this.sysActModuleService.getModuleIdByWfKey(mp);
		}
		entity.setModuleId(mod);
	}

	/**
	 * @param taskId
	 *            当前任务Id
	 * @return boolean
	 */
	/**
	 * @param taskId
	 * @param map
	 * @return
	 */
	public boolean jumpToTask(String taskId, Map<String, Object> map) {
		String targetTaskDefinitionKey;
		Task task = getProcInstId(taskId);
		String currentTaskDefKey = task.getTaskDefinitionKey();
		final String processInstanceId = task.getProcessInstanceId();
		final String oldTaskId = taskId;
		HistoricTaskInstance historicTaskInstance = getPreTaskDefinitionKey(processInstanceId, currentTaskDefKey);
		targetTaskDefinitionKey = historicTaskInstance.getTaskDefinitionKey();
		final String assignee = historicTaskInstance.getAssignee();
		logger.info("^^^^^^^^^assignee={}", assignee);
		boolean retFlag;
		try {
			final TaskEntity currentTaskEntity = (TaskEntity) taskService.createTaskQuery().taskId(taskId)
					.singleResult();
			// currentTaskEntity.setTaskDefinitionKey(targetTaskDefinitionKey);

			final ActivityImpl activity = getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(),
					targetTaskDefinitionKey);
			// List<PvmTransition> pvmTransitions =
			// activity.getIncomingTransitions();
			// for(PvmTransition pvmTransition: pvmTransitions){
			// pvmTransition.getSource().
			// }

			map.put("taskExcuter", assignee);
			activity.setVariables(map);

			final ExecutionEntity execution = (ExecutionEntity) processEngine.getRuntimeService().createExecutionQuery()
					.executionId(currentTaskEntity.getExecutionId()).singleResult();

			runtimeService.setVariable(execution.getId(), "taskExcuter", assignee);
			final TaskService taskService = processEngine.getTaskService();

			// 包装一个Command对象
			((RuntimeServiceImpl) processEngine.getRuntimeService()).getCommandExecutor().execute(new Command<Void>() {
				@Override
				public Void execute(CommandContext commandContext) {

					// 添加批注信息
					User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
					Integer userId = user.getId();
					Authentication.setAuthenticatedUserId(userId + "");
					taskService.addComment(oldTaskId, processInstanceId, comment + "(操作驳回)");

					// 删除当前的任务
					// 不能删除当前正在执行的任务，所以要先清除掉关联
					currentTaskEntity.setExecutionId(null);
					taskService.saveTask(currentTaskEntity);
					taskService.deleteTask(currentTaskEntity.getId());

					// 创建新任务
					execution.setVariable("taskExcuter", assignee);
					execution.setActivity(activity);
					execution.executeActivity(activity);
					return null;
				}
			});
			Map<String, String> m = new HashMap<String, String>();
			m.put("actId", targetTaskDefinitionKey);
			m.put("procInstId", processInstanceId);
			executionEntityMapper.updateExecution(m);
			// 查询新的taskId
			// Task t =
			// taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
			// String newTaskId = "";
			// if (t != null) {
			// newTaskId = t.getId();
			// }
			// 添加批注信息
			// User user = (User)
			// SecurityUtils.getSubject().getSession().getAttribute("user");
			// Integer userId = user.getId();
			// Authentication.setAuthenticatedUserId(userId + "");
			// taskService.addComment(newTaskId, processInstanceId, comment +
			// "(操作驳回)");
			retFlag = true;
		} catch (Exception e) {
			retFlag = false;
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return retFlag;
	}

	// private SqlSession getSqlSession()
	// {
	// ProcessEngineImpl processEngineImpl = (ProcessEngineImpl)processEngine;
	// DbSqlSessionFactory dbSqlSessionFactory = (DbSqlSessionFactory)
	// processEngineImpl.getProcessEngineConfiguration()
	// .getSessionFactories().get(DbSqlSession.class);
	// SqlSessionFactory sqlSessionFactory =
	// dbSqlSessionFactory.getSqlSessionFactory();
	// return sqlSessionFactory.openSession();
	// }

	public Task getProcInstId(String taskId) {
		logger.info("--------------taskId={}", taskId);
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}

	public HistoricTaskInstance getPreTaskDefinitionKey(String processInstanceId, String currentTaskDefKey) {
		logger.info("--------------processInstanceId={}", processInstanceId);
		HistoricTaskInstance historicTaskInstance = historyService.createNativeHistoricTaskInstanceQuery()
				.sql("SELECT * FROM act_hi_taskinst WHERE PROC_INST_ID_ = '" + processInstanceId
						+ "' AND TASK_DEF_KEY_ != '" + currentTaskDefKey
						+ "' AND DELETE_REASON_ = 'completed' ORDER BY end_time_ DESC LIMIT 1")
				.singleResult();
		return historicTaskInstance;
	}

	private ActivityImpl getActivity(ProcessEngine processEngine, String processDefId, String activityId) {
		ProcessDefinitionEntity pde = getProcessDefinition(processEngine, processDefId);
		return pde.findActivity(activityId);
	}

	private ProcessDefinitionEntity getProcessDefinition(ProcessEngine processEngine, String processDefId) {
		return (ProcessDefinitionEntity) ((RepositoryServiceImpl) processEngine.getRepositoryService())
				.getDeployedProcessDefinition(processDefId);
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

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getNextState() {
		return nextState;
	}

	public void setNextState(Integer nextState) {
		this.nextState = nextState;
	}

	public String getSfUpload() {
		return sfUpload;
	}

	public void setSfUpload(String sfUpload) {
		this.sfUpload = sfUpload;
	}

	public String getSfEdit() {
		return sfEdit;
	}

	public void setSfEdit(String sfEdit) {
		this.sfEdit = sfEdit;
	}

	public String getSfTz() {
		return sfTz;
	}

	public void setSfTz(String sfTz) {
		this.sfTz = sfTz;
	}

	public File[] getFiledata() {
		return filedata;
	}

	public void setFiledata(File[] filedata) {
		this.filedata = filedata;
	}

	public String[] getFiledataFileName() {
		return filedataFileName;
	}

	public void setFiledataFileName(String[] filedataFileName) {
		this.filedataFileName = filedataFileName;
	}

	public String[] getFiledataContentType() {
		return filedataContentType;
	}

	public void setFiledataContentType(String[] filedataContentType) {
		this.filedataContentType = filedataContentType;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getWfKey() {
		return wfKey;
	}

	public void setWfKey(String wfKey) {
		this.wfKey = wfKey;
	}

	public List<FileEntity> getFileEntities() {
		return fileEntities;
	}

	public void setFileEntities(List<FileEntity> fileEntities) {
		this.fileEntities = fileEntities;
	}

	public List<BusBaseEntity> getBusBaseEntity() {
		return busBaseEntity;
	}

	public void setBusBaseEntity(List<BusBaseEntity> busBaseEntity) {
		this.busBaseEntity = busBaseEntity;
	}

	public List<BusDocEntity> getDocEntities() {
		return docEntities;
	}

	public void setDocEntities(List<BusDocEntity> docEntities) {
		this.docEntities = docEntities;
	}

	public BusDocEntity getBusDocEntity() {
		return busDocEntity;
	}

	public void setBusDocEntity(BusDocEntity busDocEntity) {
		this.busDocEntity = busDocEntity;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getNextName() {
		return nextName;
	}

	public void setNextName(String nextName) {
		this.nextName = nextName;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public Integer getPrevioState() {
		return previoState;
	}

	public void setPrevioState(Integer previoState) {
		this.previoState = previoState;
	}

	public String getDoType() {
		return doType;
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getWfName() {
		return wfName;
	}

	public void setWfName(String wfName) {
		this.wfName = wfName;
	}

	public String getDoName() {
		return doName;
	}

	public void setDoName(String doName) {
		this.doName = doName;
	}

	public String getIsChild() {
		return isChild;
	}

	public void setIsChild(String isChild) {
		this.isChild = isChild;
	}

	public String getNextMan() {
		return nextMan;
	}

	public void setNextMan(String nextMan) {
		this.nextMan = nextMan;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

}
