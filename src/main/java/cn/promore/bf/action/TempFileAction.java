package cn.promore.bf.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.bean.User;
import cn.promore.bf.model.TempFileEntity;
import cn.promore.bf.serivce.TempFileEntityService;
import cn.promore.bf.unit.DateUtil;
import cn.promore.bf.unit.PropertiesUtils;
import cn.promore.bf.unit.StringUtil;
import cn.promore.bf.unit.UploadHelper;

@Controller
@Action(value = "tempFileAction")
@ParentPackage("huzdDefault")
@Results({ @Result(name = "result", type = "json", params = { "includeProperties", "flag,message" }),
		@Result(name = "mapJson", type = "json"),
		@Result(name = "tempFileList", type = "json", params = { "includeProperties",
				"tempFileEntities\\[\\d+\\]\\.(id|projectId|moduleId|fileName|filePath|createTime|lstUpdateTime|createMan|lstUpdateMan),flag,message" }) })
public class TempFileAction extends BaseAction {
	/**
	 *
	 */
	private static final long serialVersionUID = 6678949481450568316L;
	public static Logger logger = LoggerFactory.getLogger(BusBaseAction.class);

	List<TempFileEntity> tempFileEntities = new ArrayList<TempFileEntity>();
	@Resource
	private TempFileEntityService tempFileService;
	TempFileEntity tempFileEntity;
	private boolean flag;
	private String message;
	private String id;
	private String moduleId;
	private String ywid;
	private String wfKey;
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getYwid() {
		return ywid;
	}

	public void setYwid(String ywid) {
		this.ywid = ywid;
	}

	private String projectId;
	private List<Map<String, Object>> data;
	private String processFlag;

	public TempFileAction() {
		MDC.put("operateModuleName", "临时文件管理");
	}

	/**
	 * 新增页面 根据当前项目id 和模块id 获取 其临时文件
	 *
	 * @return
	 */
	public String getTmpFiles4Add() {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		if (user == null) {
			flag = false;
			message = "no user login";
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("moduleId", moduleId);
		map.put("projectId", projectId);
		map.put("lstUpdateMan", user.getId() + "");
		map.put("wfKey", wfKey);
		map.put("state", "add");
		map.put("ywid", "add");
		data = this.tempFileService.queryListByMap2(map);
		flag = true;
		message = "ok";
		return "mapJson";
	}

	/**
	 * 新增页面 根据当前项目id 和模块id 获取 其临时文件
	 *
	 * @return
	 */
	public String getTmpFiles4EditAndWork() {
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
		if (user == null) {
			flag = false;
			message = "no user login";
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		logger.info(ywid + "=====" + state);
		map.put("ywid", ywid);
		map.put("state", state);
		map.put("wfKey", wfKey);
		map.put("projectId", projectId);
		map.put("moduleId",moduleId);
		data = this.tempFileService.queryListByMap2(map);
		flag = true;
		message = "ok";
		return "mapJson";
	}

	/**
	 * 保存入库
	 * <p>
	 * 仅仅保存相对路径
	 *
	 * @return
	 */
	public String saveEntity() {
		try {
			String tempPath = "";
			User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
			if (user == null) {
				flag = false;
				message = "no user login";
				return null;
			}
			logger.info(ywid);
			logger.info(moduleId);
			logger.info(projectId);
			logger.info(state);
			logger.info(wfKey);
			logger.info(user.getId() + "");
			tempFileEntity.setModuleId(moduleId);
			tempFileEntity.setProjectId(projectId);
			tempFileEntity.setWfKey(wfKey);
			tempFileEntity.setState(state);
			if (StringUtils.isNoneBlank(ywid)) {
				tempFileEntity.setYwid(ywid);
			}
			tempFileEntity.setCreateMan(user.getId() + "");
			tempFileEntity.setLstUpdateMan(user.getId() + "");// 新增和修改时候
			tempFileEntity.setCreateTime(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", new Date()));
			// Map<String, Object> map = new HashMap<String, Object>();
			String fileName = tempFileEntity.getFileName();
			logger.info(fileName);
			String filename_ = fileName.substring(0, fileName.lastIndexOf("."));
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			filename_ = filename_ + "-" + DateUtil.formatDate("yyyyMMddHHmmss", new Date());
			String lstFileName = filename_ + "." + suffix;
			// map.put("fileName", lstFileName);
			// map.put("state", "add");
			// 查询当前临时文件有没有了 有就不在重复添加了
			// int count = this.tempFileService.queryCountByMap(map);
			// if (count == 0) {
			if (!"1".equals(processFlag)) {
				// if (count == 0) {
				// 需对 已选择对文件 进行 复制到临时文件夹中。
				// 从 model 里面选的文件 复制到 projectid ／moduleid ／下面
				String modelFilePath = tempFileEntity.getFilePath();
				if (isWindows()) {
					modelFilePath = modelFilePath.replace("/", "\\");
				}
				logger.info("modelFilePath========" + modelFilePath);
				logger.info("======================================");
				Properties p = PropertiesUtils.getPropertiesByName("config");
				if (p != null) {
					String templatePath = p.getProperty("templatePath");
					String tempFilePath = p.getProperty("tempPath");
					if (StringUtil.isNotBlank(templatePath) && StringUtil.isNotBlank(tempFilePath)) {
						String srcFilePath = templatePath + modelFilePath;
						String destFilePath = tempFilePath + File.separator + "project_" + projectId + File.separator
								+ moduleId + File.separator;
						logger.info("===========================srcFilePath={}", srcFilePath);
						File srcfile = new File(srcFilePath);
						String remoteEditUrl = "";
						if (srcfile.exists()) {
							if ("shgcsl_45_process".equals(wfKey) || "gcslzg_44_process".equals(wfKey)
									|| "wzzkjhtz_16_do_nd".equals(wfKey) || "wzzkjhtz_16_do_jd".equals(wfKey)
									|| "wzzkjhtz_16_do_yd".equals(wfKey) || "wzzkjhtz_16_do_rwz".equals(wfKey)
								//	|| "gf_14_gx".equals(wfKey) || "tj_14_gx".equals(wfKey)
									|| "sgrz_52_process".equals(wfKey) || "sgrzjc_52_process".equals(wfKey)) {
								lstFileName = fileName;
								String docPath = p.getProperty("docPath");
								String filepath = docPath + File.separator + "project_" + projectId + File.separator
										+ modelFilePath.substring(6, modelFilePath.length());
								srcfile = new File(filepath);
								if (!srcfile.exists()) {
									srcfile = new File(srcFilePath);
								}
								File tempFile = new File(destFilePath + lstFileName);
								if (tempFile.exists()) {
									tempFile.delete();
								}
							}
							logger.info(lstFileName);
							tempPath = UploadHelper.saveFile(destFilePath, lstFileName, srcfile);
							logger.info(tempPath);
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
							tempPath = remoteEditUrl + "?filePath=" + tempPath;
							tempFileEntity.setFilePath(tempPath);
							tempFileEntity.setFileName(lstFileName);
							tempFileEntity.setSrcFilePath(modelFilePath.substring(6, modelFilePath.length()));
							this.tempFileService.saveEntity(tempFileEntity);
						} else {
							logger.info("文件不存在啊；请速查");
						}
					}
				} else {
					logger.info("========p=null了");
				}
				// }
			} else {

				// 归卷的文件复制到临时目录中。
				// 从 model 里面选的文件 复制到 projectid ／moduleid ／下面
				String modelFilePath = tempFileEntity.getFilePath();
				if (isWindows()) {
					modelFilePath = modelFilePath.replace("/", "\\");
				}
				logger.info("modelFilePath========" + modelFilePath);
				logger.info("======================================");
				Properties p = PropertiesUtils.getPropertiesByName("config");
				if (p != null) {
					String templatePath = p.getProperty("templatePath");
					String tempFilePath = p.getProperty("tempPath");
					if (StringUtil.isNotBlank(templatePath) && StringUtil.isNotBlank(tempFilePath)) {
						String srcFilePath = templatePath + modelFilePath;
						String destFilePath = tempFilePath + File.separator + "project_" + projectId + File.separator
								+ moduleId + File.separator;
						logger.info("===========================srcFilePath={}", srcFilePath);
						String remoteEditUrl = "";
						fileName = tempFileEntity.getFileName();
						if ("doc".equals(tempFileEntity.getFileType()) || "docx".equals(tempFileEntity.getFileType())) {
							remoteEditUrl = p.getProperty("remoteEditUrl.Word");
						}
						if ("xls".equals(tempFileEntity.getFileType()) || "xlsx".equals(tempFileEntity.getFileType())) {
							remoteEditUrl = p.getProperty("remoteEditUrl.Excel");
						}
						if ("pptv".equals(tempFileEntity.getFileType())) {
							remoteEditUrl = p.getProperty("remoteEditUrl.Ppt");
						}
						tempFileEntity.setFilePath("");
						tempFileEntity.setFileName(fileName);
						tempFileEntity.setSrcFilePath(modelFilePath);
						this.tempFileService.saveEntity(tempFileEntity);
					}
				} else {
					logger.info("========p=null了");
				}
			}
			// }
			flag = true;
			message = tempPath;
			// logger.info("临时文件保存成功了..");
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
		logger.info(tempFileEntity.getFileName());
		try {
			Properties p = PropertiesUtils.getPropertiesByName("config");
			if (p != null) {
				String tempPath = p.getProperty("tempPath");
				File file = new File(tempPath + File.separator + "project_" + projectId + File.separator + moduleId
						+ File.separator + tempFileEntity.getFileName());
				if (file.exists()) {
					file.delete();
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fileName", tempFileEntity.getFileName());
			this.tempFileService.deleteEntityByFileName(map);
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

	public TempFileEntity getTempFileEntity() {
		return tempFileEntity;
	}

	public void setTempFileEntity(TempFileEntity tempFileEntity) {
		this.tempFileEntity = tempFileEntity;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public List<TempFileEntity> getTempFileEntities() {
		return tempFileEntities;
	}

	public void setTempFileEntities(List<TempFileEntity> tempFileEntities) {
		this.tempFileEntities = tempFileEntities;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public String getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}

	public String getWfKey() {
		return wfKey;
	}

	public void setWfKey(String wfKey) {
		this.wfKey = wfKey;
	}
}
