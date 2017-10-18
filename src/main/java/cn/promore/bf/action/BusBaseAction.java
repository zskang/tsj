package cn.promore.bf.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.codec.Base64;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.ProjectUsers;
import cn.promore.bf.bean.ShUserDto;
import cn.promore.bf.bean.User;
import cn.promore.bf.constant.Constant;
import cn.promore.bf.model.BusBaseEntity;
import cn.promore.bf.model.BusDocEntity;
import cn.promore.bf.model.FileEntity;
import cn.promore.bf.model.TempFileEntity;
import cn.promore.bf.serivce.BusBaseService;
import cn.promore.bf.serivce.BusDocService;
import cn.promore.bf.serivce.FileEntityService;
import cn.promore.bf.serivce.ProjectService;
import cn.promore.bf.serivce.SysActModuleService;
import cn.promore.bf.serivce.TempFileEntityService;
import cn.promore.bf.serivce.UserService;
import cn.promore.bf.unit.DateUtil;
import cn.promore.bf.unit.PropertiesUtils;
import cn.promore.bf.unit.StringUtil;
import cn.promore.bf.unit.UploadHelper;

/**
 * 基础业务处理类
 *
 * @author : zg
 */
@Controller
@Action(value = "busBaseAction")
@ParentPackage("huzdDefault")
@Results({ @Result(name = "fileNotExist", type = "redirect", location = "/404.html"),
		@Result(name = "download", type = "stream", params = { "contentType", "application/octet-stream", "inputName",
				"downloadFile", "bufferSize", "1024", "contentDisposition", "attachment;filename=\"${fileName}\"" }),
		@Result(name = "fileExtList", type = "json", params = { "includeProperties",
				"fileEntities\\[\\d+\\]\\.(id|fileName|path|state|ywid|scsj),flag,message" }),
		@Result(name = "mapJson", type = "json"),
		@Result(name = "busDocList", type = "json", params = { "includeProperties",
				"docEntities\\[\\d+\\]\\.(id|docName|docPath|state|busId|createTime|docType),flag,message" }),
		@Result(name = "entityInfo", type = "json", params = { "includeProperties",
				"entityInfo\\.(id|wdbh|parentId|gcdwmc|jsjdmc|zrrenid|lxfs|jhkssj|sjkssj|jhwcsj1|jhwcsj2|sjwcsj|sjwcsj2|remark|jdsj|remark1|remark2|userid|state|processInstanceId|taskExcuter|projectName|wfKey|moduleId|projectType|projectId|pubTime|nodes|ywName|bgbh|ziping|xmjlb|jianli|yezhu|zigs|gsi|gfgs|sfjd|sfff|sftz|sfgfxznd|fadj|fwdw|fbfxmc|bjjr|jsgljdfl),flag,message" }),
		@Result(name = "result", type = "json", params = { "includeProperties", "flag,message" }),
		@Result(name = "entityList", type = "json", params = { "includeProperties",
				"entityList\\[\\d+\\]\\.(id|wdbh|parentId|gcdwmc|jsjdmc|zrrenid|lxfs|jhkssj|sjkssj|jhwcsj1|jhwcsj2|sjwcsj|sjwcsj2|remark1|remark2|jdsj|userid|state|processInstanceId|taskExcuter|projectName|wfKey|moduleId|projectType|projectId|pubTime|nodes|ywName|bgbh|ziping|xmjlb|jianli|yezhu|zigs|gsi|gfgs|sfjd|sfff|sftz|sfgfxznd|fadj|fwdw|fbfxmc|bjjr|jsgljdfl),page\\.(\\w+),flag,message,projectUsers\\[\\d+\\]\\.user\\.(id|username|chinesename),projectUsers\\[\\d+\\]\\.role\\.(id|name)" }) })
public class BusBaseAction extends BaseAction {
	/**
	 *
	 */
	private static final long serialVersionUID = 6678949481450568716L;
	public static Logger logger = LoggerFactory.getLogger(BusBaseAction.class);
	private boolean flag;
	private String message;
	private String id;
	private Page page;
	@Resource
	ProjectService projectService;
	@Resource
	private BusBaseService busBaseService;
	private static final String FILE_EXT = "image/png,image/gif,image/jpeg,application/vnd.ms-excel,application/msword,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/kswps,application/kset,application/ksdps";
	@Resource
	private UserService userService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	private BusBaseEntity obj;
	private String details;
	private String taskId;
	private String moduleId;
	private String sjwcsj;
	private String sjkssj;
	private String jhwcsj1;
	private String jhwcsj2;
	private String jhkssj;
	private String projectId;
	private String projectName;
	private String projectType;
	private BusBaseEntity entityInfo;
	private String processInstanceId;
	private String wfKey;// wfkey
	private Integer state;
	private String docNames;
	private String docPaths;
	private String comment;
	private String path;

	private String params;
	private List<ShUserDto> userList;
	private String taskExcuter;// 下一步审核人设定id
	@Resource
	private HistoryService historyService;
	@Resource
	private RepositoryService repositoryService;
	private List<BusBaseEntity> entityList;
	private File[] filedata;
	private String[] filedataFileName;
	private String[] filedataContentType;

	@Resource
	private FileEntityService fileEntityService;
	@Resource
	private TempFileEntityService tempFileService;
	@Resource
	private BusDocService busDocService;
	@Resource
	private SysActModuleService sacService;
	// 附件列表
	List<FileEntity> fileEntities;

	private ArrayList<BusBaseEntity> busBaseEntity = new ArrayList<>();

	// 业务涉及在线编辑文档列表
	List<BusDocEntity> docEntities = new ArrayList<BusDocEntity>();

	BusDocEntity busDocEntity;

	private String docState;

	private String doType;

	public BusBaseAction() {
		MDC.put("operateModuleName", "任务流程管理");
	}

	private String fileId;

	public String deleteExtFile() {
		try {
			if (StringUtils.isNoneBlank(fileId)) {
				FileEntity filee = this.fileEntityService.findById(Integer.parseInt(fileId));
				if (filee != null) {
					File docFile = new File(filee.getPath());
					if (docFile.exists()) {
						docFile.delete();
						logger.info("删除成功！");
					}
					this.fileEntityService.deleteObj(filee);
				}
			}

			flag = true;
			message = "删除操作成功！";
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

	public String deleteBusDocFile() {
		try {
			BusDocEntity doc = this.busDocService.findById(busDocEntity.getId());
			if (doc != null) {
				File docFile = new File(doc.getDocPath() + doc.getDocName());
				if (docFile.exists()) {
					docFile.delete();
					logger.info("删除成功！");
				}
			}
			this.busDocService.removeEntityById(busDocEntity.getId());

			flag = true;
			message = "删除操作成功！";
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

	/**
	 * 根据流程部署key 查询其对应模块id 用于选择相关文档
	 *
	 * @return
	 */
	public String getModuleIdByWfKey() {
		logger.info(wfKey);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("developKey", wfKey);
		String moduleId = this.sacService.getModuleIdByWfKey(mp);
		flag = true;
		message = moduleId;
		return "result";

	}

	/**
	 * 根据传入的文件名称 和路径 复制一份 到 项目的临时文件夹下
	 *
	 * @return
	 * @throws Exception
	 */
	public String copyFileToTempDirectoryPath() throws Exception {

		Properties p = PropertiesUtils.getPropertiesByName("config");
		// 此时需要讲 传入的文件 安装 docpath 复制到 项目里面 对应到路径下
		File file = new File(busDocEntity.getDocPath());
		String destFilePath = p.getProperty("tempPath") + File.separator + "project_" + projectId + File.separator
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

	private List<ProjectUsers> projectUsers;

	/**
	 * 根据业务id 查询其对应的子表数据信息 比如 36号流程的 执行任务业务数据
	 *
	 * @return
	 */
	public String listDetails() {
		logger.info("查询子表数据。。。");
		logger.info(busBaseEntity.get(0).getId() + "====当前业务id====");
		entityList = this.busBaseService.queryListByParentId(busBaseEntity.get(0).getId());
		BusBaseEntity entity = this.busBaseService.getBusBaseEntityById(busBaseEntity.get(0).getId());
		projectUsers = this.projectService.findById(Integer.parseInt(entity.getProjectId())).getProjectUsers();
		flag = true;
		return "entityList";
	}

	/**
	 * 根据当前登陆人 和流程key 查询业务数据列表
	 *
	 * @return
	 */
	public String listApplys() {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", user.getId());
		map.put("wfKey", wfKey);
		map.put("projectId", projectId);
		logger.info("当前登录人id=" + user.getId());
		logger.info("当前 wfKey=" + wfKey);
		logger.info("当前 projectId=" + projectId);
		if (null == page)
			page = new Page();
		map.put("pageIndex", (page.getCurrentPage() - 1) * page.getPageSize());
		map.put("pageSize", page.getPageSize());
		page.setTotalRecords(busBaseService.getCount(map));
		entityList = busBaseService.getAllPage(map);
		MDC.put("operateContent", "列表查询");
		flag = true;
		message = "查询成功！";
		logger.info("");
		return "entityList";
	}

	/**
	 * 查询当前业务的明细信息
	 *
	 * @return
	 */
	public String getDetail() {
		entityInfo = this.busBaseService.getBusBaseEntityById(busBaseEntity.get(0).getId());
		flag = true;
		message = "ok";
		return "entityInfo";
	}

	private List<Map<String, Object>> data;

	/**
	 * 根据业务id 查询相关附件信息列表
	 *
	 * @return
	 */
	public String getFileExtList() {
		// fileEntities =
		// this.fileEntityService.queryListByBusId(busBaseEntity.get(0).getId());
		setData(this.fileEntityService.queryListByBusId2(busBaseEntity.get(0).getId()));
		flag = true;
		message = "ok";
		return "mapJson";
	}

	/**
	 * 获取已经选择的文档
	 *
	 * @return
	 */
	public String getBusDocList() {
		Map<String, Object> map = new HashMap<>();
		map.put("busId", busBaseEntity.get(0).getId());
		List<BusDocEntity> doclist = this.busDocService.queryDocListByMap(map);
		for (BusDocEntity entity : doclist) {
			BusDocEntity et = entity;
			setDocPathByType4Edt(et);
			docEntities.add(et);
		}
		flag = true;
		message = "ok";
		return "busDocList";
	}

	private void setDocPathByType4Edt(BusDocEntity et) {
		String remoteEditUrl = "";
		Properties p = PropertiesUtils.getPropertiesByName("config");
		remoteEditUrl = p.getProperty("remoteEditUrl.Word");
		if (StringUtil.isNotBlank(et.getDocType())) {
			if (et.getDocType().equals("doc") || et.getDocType().equals("docx")) {
				remoteEditUrl = p.getProperty("remoteEditUrl.Word");
			}
			if (et.getDocType().equals("xls") || et.getDocType().equals("xlsx")) {
				remoteEditUrl = p.getProperty("remoteEditUrl.Excel");
			}
			if (et.getDocType().equals("pptv")) {
				remoteEditUrl = p.getProperty("remoteEditUrl.Ppt");
			}
			String editUrl = remoteEditUrl + "?fileName=" + et.getDocPath() + et.getDocName();
			et.setDocPath(editUrl);
		}
	}

	/**
	 * 保存附件信息 多附件存储
	 *
	 * @param ywid
	 * @return
	 */
	public boolean saveFiles(int ywid) {
		String distDir = "project_" + projectId + "/";
		boolean flag = true;
		String distFilePath = "";
		Properties p = PropertiesUtils.getPropertiesByName("config");
		if (p != null) {
			distFilePath = p.getProperty("uploadFilePath") + File.separator + distDir + File.separator
					+ DateUtil.formatDate("yyyyMMdd", new Date()) + File.separator;
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
						fileResult.put("flag", "false");
						fileResult.put("path", path);
						e.printStackTrace();
					}
				} else {
					flag = false;
					fileResult.put("flag", "false");
					fileResult.put("path", path);
				}
				index++;
			}
		}
		return flag;
	}

	/**
	 * 保存业务数据 根据实际的业务来操作
	 * <p>
	 * 对象数组 传入： 如果对象的id 数组 第一个对象 0 默认是 父业务 数据 大于0 的 都是 子业务数据 子业务数据
	 *
	 * @return
	 */
	public String saveApply() {
		try {
			// logger.info(moduleId);
			logger.info(busBaseEntity.size() + "==size");
			int parentId = 0;
			int ywid = 0;
			for (int i = 0; i < busBaseEntity.size(); i++) {
				BusBaseEntity t = busBaseEntity.get(i);
				logger.info("t===" + t.toString());
				// add new
				if (i == 0) {
					if ("gxjsjd_36_index".equals(wfKey)) {
						t.setJdsj(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
					}
					t.setZrrenid(taskExcuter);
					t.setWfKey(wfKey);
					setCommonValues(t);
					this.busBaseService.saveBusBaseEntity(t);
					parentId = t.getId();
					ywid = t.getId();
				}
				logger.info(parentId + "");
				if (i >= 1) {// 有子任务 则需要保存 子业务基本信息
					// 然后从1 开始 循环 保存入库。
					BusBaseEntity bbEntity = busBaseEntity.get(i);
					bbEntity.setWfKey(getChildWfKeyBy(wfKey));
					setCommonValues(bbEntity);
					bbEntity.setJhkssj(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
					bbEntity.setParentId(parentId);
					this.busBaseService.saveBusBaseEntity(bbEntity);
					logger.info("生成 新的 childId=" + bbEntity.getId());
				}
			}
			saveFiles(ywid);
			saveDocFiles(docNames, docPaths, ywid + "", docState);
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

	public String updateCheckApply() {
		try {
			// logger.info(moduleId);
			logger.info(busBaseEntity.size() + "==size");
			int ywid = 0;
			for (int i = 0; i < busBaseEntity.size(); i++) {
				BusBaseEntity t = busBaseEntity.get(i);
				logger.info("t===" + t.toString());
				t.setZrrenid(taskExcuter);
				t.setWfKey(wfKey);
				setCommonValues(t);
				this.busBaseService.updateBusBaseEntity(t);
				ywid = t.getId();
			}
			removeAllAttachFiles(ywid);
			saveFiles(ywid);
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

	public String deleteFileByFileName() {
		logger.info(busDocEntity.getId() + "");
		try {
			Properties p = PropertiesUtils.getPropertiesByName("config");
			if (p != null) {
				String docPath = p.getProperty("docPath");
				File file = new File(
						docPath + File.separator + "project_" + projectId + File.separator + busDocEntity.getDocPath());
				if (file.exists()) {
					file.delete();
				}
			}
			this.busDocService.removeEntityById(busDocEntity.getId());
			flag = true;
			message = "删除成功";
			logger.info("临时文件删除成功了..");
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

	/**
	 * 修改数据
	 *
	 * @return
	 */
	public String updateApply() {
		try {
			// logger.info(moduleId);
			logger.info(busBaseEntity.size() + "==size");
			int size = busBaseEntity.size();
			int parentId = 0;
			// 先清理子任务数据
			if (size >= 1) {
				cleanChildObjects(busBaseEntity);
			}
			// 再修改 新增数据
			for (int i = 0; i < busBaseEntity.size(); i++) {
				if (i == 0) {
					BusBaseEntity t = busBaseEntity.get(i);
					removeAllAttachFiles(t.getId());
					removeTmpDocsByBusId(t.getId());
					logger.info("t===" + t.toString());
					setCommonValues(t);
					this.busBaseService.updateBusBaseEntity(t);
					saveFiles(t.getId());
					saveDocFiles(docNames, docPaths, t.getId() + "", "0");
					parentId = t.getId();
				} else {
					if (i >= 1) {// 有子任务 则需要保存 子业务基本信息
						// 然后从1 开始 循环 保存入库。
						BusBaseEntity bbEntity = busBaseEntity.get(i);
						bbEntity.setWfKey(getChildWfKeyBy(wfKey));
						setCommonValues(bbEntity);
						bbEntity.setJhkssj(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
						bbEntity.setParentId(parentId);
						this.busBaseService.saveBusBaseEntity(bbEntity);
						logger.info("生成 新的 childId=" + bbEntity.getId());
						saveFiles(bbEntity.getId());
						saveDocFiles(docNames, docPaths, bbEntity.getId() + "", "0");
					}
				}
			}
			flag = true;
			message = "操作成功！";
			logger.info("操作");
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

	private void cleanChildObjects(ArrayList<BusBaseEntity> bbentity) {
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
				removeAllAttachFiles(bbt.getId());
				removeTmpDocsByBusId(bbt.getId());
				this.busBaseService.deleteBusBaseEntity(bbt);
				logger.info("清理了:" + bbt.getYwName() + "-----" + bbt.getId());
			}
			logger.info("清理完毕了。");
		}
	}

	private FileEntity fileEntity;
	private InputStream downloadFile;

	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String download() {
		if (null != fileEntity && 0 != fileEntity.getId()) {
			fileEntity = fileEntityService.findById(fileEntity.getId());
			if (StringUtils.isNotEmpty(fileEntity.getPath()) && StringUtils.isNoneEmpty(fileEntity.getFileName())) {
				try {
					File extfile = new File(fileEntity.getPath());
					if (null != extfile && extfile.exists()) {
						setDownloadFile(new FileInputStream(extfile));
						fileName = fileEntity.getFileName().substring(0, fileEntity.getFileName().indexOf("."));
						String agent = request.getHeader("USER-AGENT");
						String fileExt = fileEntity.getFileExt();
						if (null != agent) {
							if (-1 != agent.indexOf("Firefox")) {// Firefox
								fileName = "=?UTF-8?B?" + (new String(Base64.encode(fileName.getBytes("UTF-8"))))
										+ "?=";
								fileName = fileName + "." + fileExt;
							} else if (-1 != agent.indexOf("Chrome")) {// Chrome
								fileName = new String(fileName.getBytes(), "ISO8859-1");
								fileName = fileName + "." + fileExt;
							} else {// IE7+
								fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
								fileName = StringUtils.replace(fileName, "+", "%20");// 替换空格
								fileName = fileName + "." + fileExt;
							}
						} else {
							fileName = fileName + "." + fileExt;
						}
						flag = true;
						return "download";
					} else {
						return "fileNotExist";
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private void setCommonValues(BusBaseEntity entity) {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		entity.setProcessInstanceId(processInstanceId);
		entity.setProjectId(projectId);
		entity.setProjectName(projectName);
		entity.setState(Constant.STATE.DTJ);
		entity.setProjectType(projectType);
		entity.setPubTime(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
		entity.setUserid(user.getId() + "");
		String str = entity.getWfKey() + ".zip";
		String ywName = Constant.getWfNameByFileName(str);
		if (StringUtil.isNotBlank(ywName)) {
			entity.setYwName(ywName.substring(3));
		} else {
			entity.setYwName("");
		}
		logger.info(ywName);
		if ("aqjsjd_36_index".equals(wfKey) || "gxjsjd_36_index".equals(wfKey)) {
			entity.setNodes(Constant.NODECONSTANT.NODE_JSJDJH);
		}
		if ("aqjsjd_36_do".equals(wfKey) || "gxjsjd_36_do".equals(wfKey)) {
			entity.setNodes(Constant.NODECONSTANT.NODE_JSJDMX);
		}
		if ("aqjsjd_36_check".equals(wfKey) || "gxjsjd_36_check".equals(wfKey)) {
			entity.setNodes(Constant.NODECONSTANT.NODE_JSJDJC);
		}
		entity.setModuleId(moduleId);
	}

	/**
	 * 保存选择的在线编辑的文档数据
	 *
	 * @param docNames
	 * @param docPaths
	 * @param id
	 */
	private void saveDocFiles(String docNames, String docPaths, String id, String state) {
		logger.info(docNames);
		logger.info(docPaths);
		if (StringUtil.isNotBlank(docNames) && StringUtil.isNotBlank(docPaths) && StringUtil.isNotBlank(id)) {
			String[] docName_ = docNames.split(",");
			String[] docPath_ = docPaths.split(",");
			for (int i = 0; i < docName_.length; i++) {
				BusDocEntity busDocEntity = new BusDocEntity();
				busDocEntity.setBusId(id);
				busDocEntity.setCreateMan("1");
				busDocEntity.setCreateTime(DateUtil.formatDate("yyyy-MM-dd", new Date()));
				busDocEntity.setDocName(docName_[i]);
				busDocEntity.setDocPath(docPath_[i]);
				logger.info(busDocEntity.toString());
				busDocEntity.setState(state);
				int a = this.busDocService.saveEntity(busDocEntity);
				logger.info("new doc create：" + a + "");
			}
		}
	}

	/**
	 * 删除文档与业务关系 和 文件
	 *
	 * @param id
	 */

	private void removeTmpDocsByBusId(int id) {
		// 1.删除文件
		// 2.删除关联关系
		Map<String, Object> map = new HashMap<>();
		map.put("ywid", id);
		List<TempFileEntity> list = this.tempFileService.queryListByMap(map);
		for (TempFileEntity entity : list) {
			String path = entity.getFilePath();
			String[] ss = path.split("=");
			map.put("fileName", entity.getFileName());
			if (ss.length > 0) {
				File file = new File(ss[1]);
				if (file.exists()) {
					file.delete();
				}
			}
			this.tempFileService.deleteEntityByFileName(map);
		}
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

	private String nextStep;

	public String doEditSh() {

		return "result";
	}

	public String doEditFh() {

		return "result";
	}

	public String listAllShUsers() {
		setUserList(userService.findAllByParams(params));
		return "optionList";
	}

	public List<FileEntity> getFileEntities() {
		return fileEntities;
	}

	public void setFileEntities(List<FileEntity> fileEntities) {
		this.fileEntities = fileEntities;
	}

	public FileEntity getFileEntity() {
		return fileEntity;
	}

	public void setFileEntity(FileEntity fileEntity) {
		this.fileEntity = fileEntity;
	}

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

	private String getChildWfKeyBy(String wfKey) {
		String childWfKey = "";
		if (wfKey.equals(Constant.WFKEY.工序技术交底_计划)) {
			childWfKey = Constant.WFKEY.工序技术交底_执行;
		}
		if (wfKey.equals(Constant.WFKEY.安全技术交底_计划)) {
			childWfKey = Constant.WFKEY.安全技术交底_执行;
		}
		return childWfKey;
	}

	/**
	 * 删除 未提交流程的 申请
	 *
	 * @return
	 * @throws IOException
	 */
	public String deleteApply() throws IOException {
		String userAgent = request.getHeader("user-agent");
		Boolean isIE = (userAgent.toLowerCase().indexOf("msie") != -1 || userAgent.toLowerCase().indexOf("rv:11") != -1)
				? true : false;
		if (!org.springframework.util.StringUtils.isEmpty(id)) {
			logger.info(id);

			BusBaseEntity entity = this.busBaseService.getBusBaseEntityById(Integer.parseInt(id));
			// 对于 子任务 需要 判断 其对应的 父任务是否已经完结
			if ("gxjsjd_36_do".equals(entity.getWfKey()) || "aqjsjd_36_do".equals(entity.getWfKey())
					|| "szgl_18_do".equals(entity.getWfKey())) {
				flag = false;
				message = "子任务不可以删除！";
				return "result";
			}
			if (StringUtils.isNoneBlank(entity.getProcessInstanceId())) {
				this.runtimeService.deleteProcessInstance(entity.getProcessInstanceId(), "驳回后 不想继续操作，故删除 重新来过！");
			}
			this.busBaseService.deleteBusBaseEntity(entity);
			List<BusBaseEntity> list = this.busBaseService.queryListByParentId(Integer.parseInt(id));
			for (BusBaseEntity entitys : list) {
				this.busBaseService.deleteBusBaseEntity(entitys);
			}
			this.removeAllAttachFiles(Integer.parseInt(id));
			this.removeTmpDocsByBusId(Integer.parseInt(id));
		}
		flag = true;
		message = "删除成功！";
		return "result";
		// response.setContentType((isIE ? "text/html" : "application/json") +
		// ";charset=utf-8");
		// response.getWriter().append("{flag:" + flag + ",message:'" + message
		// + "'}");
		// response.flushBuffer();
		// return null;
	}

	/**
	 * 提交流程
	 *
	 * @return
	 * @throws IOException
	 */
	public String startApply() throws IOException {

		logger.info("id==" + id);

		BusBaseEntity obj = this.busBaseService.getBusBaseEntityById(Integer.parseInt(id));

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

		variables.put("id", id);

		variables.put("taskExcuter", user.getId());

		variables.put("dateVariable", DateUtils.addDays(new Date(), 2));

		// 启动流程
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(obj.getWfKey(), variables);

		String taskExcuter = obj.getTaskExcuter();

		variables.put("taskExcuter", taskExcuter);

		// 根据流程实例Id查询任务
		Task task = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
		// 完成任务
		taskService.complete(task.getId(), variables);

		BusBaseEntity entity = this.busBaseService.getBusBaseEntityById(Integer.parseInt(id));
		entity.setState(Constant.STATE.DSH);
		if ("gxjsjd_36_check".equals(entity.getWfKey()) || "aqjsjd_36_check".equals(entity.getWfKey())) {
			entity.setState(Constant.STATE.DJCTB);// 发起流程时候 需要根据实际情况来定
		}
		if ("sgdc_01_index".equals(entity.getWfKey()) || "xmglchs_03_do".equals(entity.getWfKey())) { // 第一步
																										// 待复核
																										// 第二步
																										// 待审核
																										// 第三步
			// 待归卷 第四步 over
			entity.setState(Constant.STATE.DFH);
		}
		if ("sgdc_01_do".equals(entity.getWfKey())) { // 第一步 待编制 第二步 待审核 第三步 待审批
			// 第四步 待归卷 第五步 over
			entity.setState(Constant.STATE.DBZ);
		}
		logger.info(pi.getProcessInstanceId());

		entity.setProcessInstanceId(pi.getProcessInstanceId());
		// 更改业务数据状态
		this.busBaseService.updateBusBaseEntity(entity);
		flag = true;
		message = "操作成功！";
		return "result";
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BusBaseEntity getObj() {
		return obj;
	}

	public void setObj(BusBaseEntity obj) {
		this.obj = obj;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public List<ShUserDto> getUserList() {
		return userList;
	}

	public void setUserList(List<ShUserDto> userList) {
		this.userList = userList;
	}

	public String getTaskExcuter() {
		return taskExcuter;
	}

	public void setTaskExcuter(String taskExcuter) {
		this.taskExcuter = taskExcuter;
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

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getWfKey() {
		return wfKey;
	}

	public void setWfKey(String wfKey) {
		this.wfKey = wfKey;
	}

	public String getSjwcsj() {
		return sjwcsj;
	}

	public void setSjwcsj(String sjwcsj) {
		this.sjwcsj = sjwcsj;
	}

	public String getSjkssj() {
		return sjkssj;
	}

	public void setSjkssj(String sjkssj) {
		this.sjkssj = sjkssj;
	}

	public String getJhwcsj1() {
		return jhwcsj1;
	}

	public void setJhwcsj1(String jhwcsj1) {
		this.jhwcsj1 = jhwcsj1;
	}

	public String getJhwcsj2() {
		return jhwcsj2;
	}

	public void setJhwcsj2(String jhwcsj2) {
		this.jhwcsj2 = jhwcsj2;
	}

	public String getJhkssj() {
		return jhkssj;
	}

	public void setJhkssj(String jhkssj) {
		this.jhkssj = jhkssj;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<BusBaseEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<BusBaseEntity> entityList) {
		this.entityList = entityList;
	}

	public BusBaseEntity getEntityInfo() {
		return entityInfo;
	}

	public void setEntityInfo(BusBaseEntity entityInfo) {
		this.entityInfo = entityInfo;
	}

	public String getDocNames() {
		return docNames;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setDocNames(String docNames) {
		this.docNames = docNames;
	}

	public String getDocPaths() {
		return docPaths;
	}

	public void setDocPaths(String docPaths) {
		this.docPaths = docPaths;
	}

	public ArrayList<BusBaseEntity> getBusBaseEntity() {
		return busBaseEntity;
	}

	public void setBusBaseEntity(ArrayList<BusBaseEntity> busBaseEntity) {
		this.busBaseEntity = busBaseEntity;
	}

	public List<BusDocEntity> getDocEntities() {
		return docEntities;
	}

	public void setDocEntities(List<BusDocEntity> docEntities) {
		this.docEntities = docEntities;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public InputStream getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(InputStream downloadFile) {
		this.downloadFile = downloadFile;
	}

	public BusDocEntity getBusDocEntity() {
		return busDocEntity;
	}

	public void setBusDocEntity(BusDocEntity busDocEntity) {
		this.busDocEntity = busDocEntity;
	}

	public String getDocState() {
		return docState;
	}

	public void setDocState(String docState) {
		this.docState = docState;
	}

	public String getDoType() {
		return doType;
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public List<ProjectUsers> getProjectUsers() {
		return projectUsers;
	}

	public void setProjectUsers(List<ProjectUsers> projectUsers) {
		this.projectUsers = projectUsers;
	}

}
