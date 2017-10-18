package cn.promore.bf.action;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.Task;
import cn.promore.bf.bean.User;
import cn.promore.bf.serivce.TaskService;

@Controller
@Action(value="taskAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="result",type="json",params={"includeProperties","flag,message"}),
	      @Result(name="download",type="stream",params={"contentType","application/octet-stream","inputName","downloadFile","bufferSize","1024","contentDisposition","attachment;filename=\"${fileName}\""}),
		  @Result(name="objectResult",type="json",
		          params={"includeProperties","flag,message,task\\.(id|name|createTime|finishTime|status|templatePath|filePath|totalRecords|remark),"
		          		+ "task\\.author\\.(id|chinesename)"}),
	      @Result(name="listResult",type="json",
	              params={"includeProperties",
	    		  "tasks\\[\\d+\\]\\.(id|name|createTime|finishTime|status|templatePath|filePath|totalRecords|remark),"
	      		+ "tasks\\[\\d+\\]\\.author\\.(id|chinesename),"
	      		+ "page\\.(\\w+),flag,message"})
		})
public class TaskAction extends BaseAction{
	public static Logger LOG = LoggerFactory.getLogger(TaskAction.class);
	private static final long serialVersionUID = -8613055080615406396L;
	
	@Resource(name="taskService_") 
	TaskService taskService;
	private Task task;
	private boolean flag = true;
	private String message;
	private List<Task> tasks;
	private Page page;
	private String dataIds;
	private Date startDate;
	private Date endDate;
	private InputStream downloadFile;
	private String fileName;
	
	public TaskAction() {
		MDC.put("operateModuleName","任务管理");
	}

	public String list(){
		SecurityUtils.getSubject().checkPermission("task:get");
		if(null==task){
			task = new Task();
		}
		task.setAuthor((User)session.getAttribute("user"));
		page.setTotalRecords(taskService.findDataNo(task,startDate,endDate));
		tasks = taskService.findDatas(task,startDate,endDate,page);
		MDC.put("operateContent","下载任务列表查询"); 
		LOG.info("");			
		return "listResult";
	}
	
	public String delete(){
		SecurityUtils.getSubject().checkPermission("task:delete");
		String[] aIds = dataIds.split(",");
		flag = true;
		try {
			for(String id:aIds){
				taskService.deleteById(Integer.valueOf(id));
				flag = flag&&true;
				MDC.put("operateContent","删除下载任务信息！id:"+id); 
				LOG.info("");	
			}			
		} catch (Exception e) {
			flag = flag&&false;
			message = e.getMessage();
		}
		return "result";
	}
	
	public String find(){
		SecurityUtils.getSubject().checkPermission("task:get");
		task = taskService.findById(task.getId());
		MDC.put("operateContent","查询下载任务信息，ID："+task.getId()); 
		LOG.info("");	
		return "objectResult";
	}

	public String download(){
		SecurityUtils.getSubject().checkPermission("task:download");
		if(null!=task&&null!=task.getId()){
			task = taskService.findById(task.getId());
			if(StringUtils.isNotEmpty(task.getFilePath())){
				try {
					downloadFile = new FileInputStream(new File(task.getFilePath()));
					fileName = task.getName();
					String agent = request.getHeader("USER-AGENT");
					String fileExt = ".xls";
					if (null != agent){ 
			            if (-1 != agent.indexOf("Firefox")) {//Firefox  
			            	fileName = "=?UTF-8?B?" + (new String(Base64.encode(fileName.getBytes("UTF-8"))))+ "?=";
			            	fileName = fileName+fileExt;  
			            }else if (-1 != agent.indexOf("Chrome")) {//Chrome  
			            	fileName = new String(fileName.getBytes(), "ISO8859-1");  
			            	fileName = fileName+fileExt;  
			            } else {//IE7+  
			            	fileName = java.net.URLEncoder.encode(fileName, "UTF-8");  
			            	fileName = StringUtils.replace(fileName, "+", "%20");//替换空格  
			            	fileName = fileName+fileExt;  
			            }  
			        } else {  
			        	fileName = fileName+fileExt;  
			        } 
					return "download";
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
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

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public InputStream getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(InputStream downloadFile) {
		this.downloadFile = downloadFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
