package cn.promore.bf.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;
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
import cn.promore.bf.bean.CompletionInfo;
import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.Project;
import cn.promore.bf.bean.User;
import cn.promore.bf.serivce.CompletionInfoService;
import cn.promore.bf.serivce.ModuleService;
import cn.promore.bf.serivce.ProjectService;
import cn.promore.bf.serivce.ProjectUsersService;
import cn.promore.bf.unit.PropertiesUtil;
import cn.promore.bf.unit.ZipUtil;

@Controller
@Action(value="projectAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="objectResult",type="json",params={"includeProperties","flag,message,"
                  		                       + "project\\.(id|name|startDate|endDate|finishDate|price|complete),"
		         						   + "project\\.master\\.(id|username|chinesename|telephone|mobilephone),"
		         						   + "project\\.parent\\.(id|name),"
		         						   + "project\\.type\\.(id|name),"
		         						   + "project\\.projectUsers\\[\\d+\\]\\.(id|createTime),"
		         						   + "project\\.projectUsers\\[\\d+\\]\\.user\\.(id|username|chinesename|telephone|mobilephone),"
		         						   + "project\\.projectUsers\\[\\d+\\]\\.role\\.(id|name|shortName)",
		         						   "excludeNullProperties","true"}),
		 @Result(name="listResult",type="json",params={"includeProperties","projects\\[\\d+\\]\\.(id|name|startDate|endDate|finishDate|price|complete),"
		         						   + "projects\\[\\d+\\]\\.master\\.(id|username|chinesename|telephone|mobilephone),"
		         						   + "projects\\[\\d+\\]\\.type\\.(id|name),"
		         						   + "projects\\[\\d+\\]\\.parent\\.(id|name),"
		         						   + "projects\\[\\d+\\]\\.projectUsers\\[\\d+\\]\\.(id|createTime),"
		         						   + "projects\\[\\d+\\]\\.projectUsers\\[\\d+\\]\\.user\\.(id|username|chinesename|telephone|mobilephone),"
		         						   + "projects\\[\\d+\\]\\.projectUsers\\[\\d+\\]\\.role\\.(id|name|shortName),"
		         						   + "page\\.(\\w+),flag,message","excludeProperties","*handler"}),
		 @Result(name="optionList",type="json",params={"includeProperties","projects\\[\\d+\\]\\.(id|name)"}),
		 @Result(name="result",type="json",params={"includeProperties","flag,message"}),
		 @Result(name="messageResult",type="json",params={"root","message"}),
		 @Result(name="config",type="json",params={"root","flag"})
		 })
public class ProjectAction extends BaseAction{
	public static Logger LOG = LoggerFactory.getLogger(ProjectAction.class);
	private static final long serialVersionUID = -8613055080615406396L;
	
	@Resource 
	ProjectService projectService;
	@Resource
	ModuleService moduleService;
	@Resource
	ProjectUsersService projectUsersService;
	@Resource
	CompletionInfoService completionInfoService;
	private Project project;
	private boolean flag = true;
	private String message;
	private Page page;
	private List<Project> projects;
	private String dataIds;
	private Date startDate;
	private Date endDate;

	public ProjectAction() {
		MDC.put("operateModuleName","立项中心");
	}
	
	public String checkName(){
		flag = false;
		if(null!=project&&StringUtils.isNotEmpty(project.getName())){
			Project selectedProject = projectService.findByName(project.getName());
			if(null==project.getId()&&null==selectedProject){
				flag=true;
			}else if(null==project.getId()&&null!=selectedProject){
				flag = false;
			}else if(null!=project.getId()&&null==selectedProject){
				flag = true;
			}else if(null!=project.getId()&&null!=selectedProject&&project.getName().equals(selectedProject.getName())){
				flag = true;
			}
		}
		return "config";
	}
	
	public String download() throws Exception{
		if(null!=project&&null!=project.getId()){
			project =  projectService.findById(project.getId());
			MDC.put("operateContent","立项文档下载，项目名称："+project.getName()); 
			LOG.info("");	
			String fileName = project.getName();
			BufferedInputStream bis = null;
		    BufferedOutputStream bos = null;
		    bis = new BufferedInputStream(new FileInputStream(project.getDownloadPath()));
		    bos = new BufferedOutputStream(response.getOutputStream());
		    long fileLength = new File(project.getDownloadPath()).length();
		    response.setCharacterEncoding("UTF-8");
		    String userAgent = request.getHeader("User-Agent");
		    byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes(): fileName.getBytes("UTF-8"); // fileName.getBytes("UTF-8")处理safari的乱码问题
		    fileName = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
		    response.setHeader("Content-disposition",String.format("attachment; filename=\"%s\"", fileName+".zip"));
		    response.setHeader("Content-Length", String.valueOf(fileLength));
		    byte[] buff = new byte[2048];
		    int bytesRead;
		    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
		        bos.write(buff, 0, bytesRead);
		    }
		    bis.close();
		    bos.close();
		    response.flushBuffer();
		}
		return null;
	}
	
	public String ensureMainProject(){
		project = projectService.findById(project.getId());
		session.setAttribute("project",project);
		flag = true;
		return "objectResult";
	}
	
	
	
	public String loadUserProjects(){
		User user = (User)session.getAttribute("user");
		projects = projectUsersService.findUserProjects(user.getId());
		if(null==projects||projects.size()==0){
			session.setAttribute("project",null);
			flag = true;
		}else if(null!=projects&&projects.size()==1){
			session.setAttribute("project",projects.get(0));
			flag = true;
		}else{
			flag = false;
		}
		return "listResult";
	}

	public String listAll(){
		projects = projectService.findAll();
		return "optionList";
	}
	
	public String findUserAllProjects(){
		User user = (User)session.getAttribute("user");
		projects = projectUsersService.findUserProjects(user.getId());
		return "optionList";
	}
	
	public String find(){
		SecurityUtils.getSubject().checkPermission("project:get");
		try {
			project = (null!=project&&null!=project.getId())?projectService.findById(project.getId()):null;
			flag = true;
			MDC.put("operateContent","查询立项条目，编码："+project.getId()); 
			LOG.info("");			
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "objectResult";
	}	
	
	public String list(){
		SecurityUtils.getSubject().checkPermission("project:get");
		page.setTotalRecords(projectService.findDataNo(project,startDate,endDate));
		projects = projectService.findDatas(project,page,startDate,endDate);
		MDC.put("operateContent","立项中心列表查询"); 
		LOG.info("");
		return "listResult";
	}
	
//	private void createPath(Module module,String parentPath) throws IOException{
//		if(null!=module&&module.getType()==1&&null!=module.getChild()&&module.getLevel()<3&&module.getChild().size()>0){
//			for(Module m:module.getChild()){
//				if(m.getType()==1){
//					String path = parentPath + m.getName() + System.getProperty("file.separator");
//					FileUtils.forceMkdir(new File(path));
//					createPath(m,path);
//				}
//			}
//		}
//	}
	
	public String zipDoc(){
		project = projectService.findById(project.getId());
		if(null!=project&&StringUtils.isEmpty(project.getDownloadPath())){
			String basePath = PropertiesUtil.getKeyValue("docPath");
			String docPath = basePath + System.getProperty("file.separator")+"files"+System.getProperty("file.separator");
			String downloadPath = basePath+System.getProperty("file.separator")+"download"+System.getProperty("file.separator");
			String projectDocPath = docPath + System.getProperty("file.separator") + "project_"+project.getId() + System.getProperty("file.separator");
			String downloadDocPath = downloadPath + System.getProperty("file.separator") + "project_"+project.getId() + ".zip";
			try {
				FileUtils.forceMkdir(new File(downloadPath));
				ZipUtil.zip(projectDocPath, downloadDocPath);
				project.setDownloadPath(downloadDocPath);
				projectService.update(project);
				flag =true;
			} catch (Exception e) {
				flag = false;
				message = e.getMessage();
				e.printStackTrace();
			}
//			String docPath = returnWebServerRootPath() + "docs"+System.getProperty("file.separator")+"files"+System.getProperty("file.separator");
//			String downloadPath = returnWebServerRootPath() + "docs"+System.getProperty("file.separator")+"download"+System.getProperty("file.separator");
//			String projectDocPath = docPath + System.getProperty("file.separator") + "project_"+project.getId() + System.getProperty("file.separator");
//			String downloadDocPath = downloadPath + System.getProperty("file.separator") + "project_"+project.getId() + ".zip";
//			try {
//				FileUtils.forceMkdir(new File(downloadPath));
//				ZipUtil.zip(projectDocPath, downloadDocPath);
//				project.setDownloadPath(downloadDocPath);
//				projectService.update(project);
//				flag =true;
//			} catch (Exception e) {
//				flag = false;
//				message = e.getMessage();
//				e.printStackTrace();
//			}
		}else{
			flag = true;
		}
		return "result";
	}
	
	
	public String add(){
		SecurityUtils.getSubject().checkPermission("project:add");
		try {
			if(null!=project&&null!=project.getParent()&&null==project.getParent().getId())project.setParent(null);
			Map<String,String[]> allParams =  request.getParameterMap();
			Map<String,String[]> roleUsers = new HashMap<String, String[]>();
			Pattern pattern =  Pattern.compile("pro_usr_([a-zA-Z0-9]+)_hide");
			for(String key:allParams.keySet()){
				Matcher matcher = pattern.matcher(key);
				if(matcher.find()){
					roleUsers.put(matcher.group(1),allParams.get(key)[0].split(","));
				}
			}
			projectService.save(project,roleUsers);
			//创建文件夹路径
//			String docPath = returnWebServerRootPath() + "docs"+System.getProperty("file.separator")+"files"+System.getProperty("file.separator");
//			String projectDocPath = docPath + System.getProperty("file.separator") + "project_"+project.getId() + System.getProperty("file.separator");
//			Module rootModule = moduleService.getRoot();
			//createPath(rootModule,projectDocPath);
			//创建竣工管理模块
			CompletionInfo completionInfo = new CompletionInfo();
			completionInfo.setProject(project);
			completionInfo.setLevel(0);
			completionInfo.setDesc("竣工管理");
			completionInfo.setName("竣工管理");
			completionInfo.setOrder(1);
			completionInfo.setDisplayCode("-1");
			completionInfo.setType(1);
			completionInfo.setStatus("N");
			completionInfoService.save(completionInfo);
			flag = true;
			message = "添加成功！";
			MDC.put("operateContent","立项中心项目添加："+project.toString()); 
			LOG.info("");
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			message = e.getMessage();
		}
		return "result";
	}
	
	public String update(){
			SecurityUtils.getSubject().checkPermission("project:update");
			try {
				if(null!=project&&null!=project.getParent()&&null==project.getParent().getId())project.setParent(null);
				Map<String,String[]> allParams =  request.getParameterMap();
				Map<String,String[]> roleUsers = new HashMap<String, String[]>();
				Pattern pattern =  Pattern.compile("pro_usr_([a-zA-Z0-9]+)_hide");
				for(String key:allParams.keySet()){
					Matcher matcher = pattern.matcher(key);
					if(matcher.find()){
						roleUsers.put(matcher.group(1),allParams.get(key)[0].split(","));
					}
				}				
				projectService.update(project,roleUsers);
					flag = true;
				MDC.put("operateContent","立项更新："+project.toString()); 
				LOG.info("");
			} catch (Exception e) {
				flag = false;
				message = "更新失败！请重试。";
			}
		return "result";
	}
	
	public String delete(){
		message = "";
		if(StringUtils.isNotEmpty(dataIds)){
			String[] roleIds = dataIds.split(",");
			flag = true;
			for(String temp:roleIds){
				try {
					projectService.deleteById(Integer.valueOf(temp));
					MDC.put("operateContent","项目删除，编码："+temp); 
					LOG.info("");					
				} catch (Exception e) {
					flag = false;
					e.printStackTrace();
					message += "项目删除失败，可能存在关联关系！";
				}				
			}
		}
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
	public String getDataIds() {
		return dataIds;
	}
	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
}
