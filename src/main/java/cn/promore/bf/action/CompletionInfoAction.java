package cn.promore.bf.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

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

import cn.promore.bf.bean.CompletionFile;
import cn.promore.bf.bean.Module;
import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.Project;
import cn.promore.bf.bean.User;
import cn.promore.bf.model.BusDocEntity;
import cn.promore.bf.serivce.BusDocService;
import cn.promore.bf.serivce.CompletionFileService;
import cn.promore.bf.serivce.EntitySequenceService;
import cn.promore.bf.serivce.CompletionInfoService;
import cn.promore.bf.serivce.ModuleService;
import cn.promore.bf.unit.EntityCodeHelper;
import cn.promore.bf.unit.PropertiesUtil;
import cn.promore.bf.unit.ZipUtil;
import cn.promore.bf.bean.CompletionInfo;

@Controller
@Action(value="completionInfoAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="tojson",type="json",params={"root","message","excludeNullProperties","true"}),
	      @Result(name="mapJson",type="json"),
	      @Result(name="messageResult",type="json",params={"root","message"}),
		  @Result(name="resJson",type="json",params={"excludeProperties","completionInfoService","excludeNullProperties","true"}),
		  @Result(name="addResult",type="json",params={"includeProperties","flag,message"}),
		  @Result(name="result",type="json",params={"includeProperties","completionInfoService,completionInfo.*\\.child,completionInfo.*\\.roles","excludeNullProperties","true"}),
		  @Result(name="objectResult",type="json",params={"includeProperties","flag,message,completionInfo\\.(id|name|desc|type|order|path|status|level|displayCode|childNo),completionInfo\\.parent\\.(id|name|desc|type|order|path|status|level|displayCode)","excludeNullProperties","true"}),
		  @Result(name="listResult",type="json",params={"includeProperties","completionInfos\\[\\d+\\]\\.(id|name|desc|type|order|path|status|level|displayCode|childNo),completionInfos\\[\\d+\\]\\.parent\\.(id|name|desc|type|order|path|status|level|displayCode),page\\.(\\w+),flag,message","excludeNullProperties","true"})
		 })
public class CompletionInfoAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	public static Logger LOG = LoggerFactory.getLogger(CompletionInfoAction.class);
	
	@Resource 
	CompletionInfoService completionInfoService;
	@Resource
	EntitySequenceService entitySequenceService;
	@Resource
	ModuleService moduleService;
	@Resource
	BusDocService busDocService;
	@Resource
	CompletionFileService completionFileService;
	
	private CompletionInfo completionInfo;
	private boolean flag = true;
	private String message;
	private Page page;
	private List<CompletionInfo> completionInfos;
	private Integer completionInfoId;
	private Integer projectId;
	private Map<String, Object> data;
	private String completionInfoIds;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormats = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	
	public void downloadFile() throws Exception{
		BusDocEntity busDocEntity = busDocService.findById(completionInfoId);
		MDC.put("operateContent","项目归卷文档下载，文件名称："+busDocEntity.getDocName()); 
		LOG.info("");	
		String fileName = busDocEntity.getDocName();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		long fileLength = 0;
		if(busDocEntity.getState().equals("1")){
			bis = new BufferedInputStream(new FileInputStream(busDocEntity.getDocPath()));
			fileLength = new File(busDocEntity.getDocPath()).length();
		}else{
			bis = new BufferedInputStream(new FileInputStream(busDocEntity.getDocPath()+busDocEntity.getDocName()));
			fileLength = new File(busDocEntity.getDocPath()+busDocEntity.getDocName()).length();
		}
		bos = new BufferedOutputStream(response.getOutputStream());
		response.setCharacterEncoding("UTF-8");
		String userAgent = request.getHeader("User-Agent");
		byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes(): fileName.getBytes("UTF-8"); // fileName.getBytes("UTF-8")处理safari的乱码问题
		fileName = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
		response.setHeader("Content-disposition",String.format("attachment; filename=\"%s\"", fileName));
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
	
	public void download() throws Exception{
		CompletionFile completionFile = completionFileService.findById(Integer.parseInt(message));
		MDC.put("operateContent","项目竣工文档下载，项目名称："+completionFile.getName()); 
		LOG.info("");	
		String fileName = completionFile.getName();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		bis = new BufferedInputStream(new FileInputStream(completionFile.getPath()));
		bos = new BufferedOutputStream(response.getOutputStream());
		long fileLength = new File(completionFile.getPath()).length();
		response.setCharacterEncoding("UTF-8");
		String userAgent = request.getHeader("User-Agent");
		byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes(): fileName.getBytes("UTF-8"); // fileName.getBytes("UTF-8")处理safari的乱码问题
		fileName = new String(bytes, "ISO-8859-1"); // 各浏览器基本都支持ISO编码
		response.setHeader("Content-disposition",String.format("attachment; filename=\"%s\"", fileName));
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
	
	
	public String zipDoc(){
		List<File> list = new ArrayList<File>();
		if(completionInfoIds.indexOf(",") == -1 && completionInfoIds!=null){
			CompletionInfo info = completionInfoService.findById(Integer.parseInt(completionInfoIds));
			if(info != null){
				BusDocEntity docEntity = busDocService.findById(info.getFileId());
				if(docEntity!=null){
					if(docEntity.getState().equals("1")){
						list.add(new File(info.getPath()));
					}else{
						list.add(new File(info.getPath()+info.getName()));
					}
				}
			}
		}else{
			String[] completionIds = completionInfoIds.split(",");
			for(String completionId:completionIds){
				CompletionInfo info = completionInfoService.findById(Integer.parseInt(completionId));
				if(info != null){
					BusDocEntity docEntity = busDocService.findById(info.getFileId());
					if(docEntity!=null){
						if(docEntity.getState().equals("1")){
							list.add(new File(info.getPath()));
						}else{
							list.add(new File(info.getPath()+info.getName()));
						}
					}
				}
			}
		}
		if(list.size()>0){
			Project project = (Project)session.getAttribute("project");
			String basePath = PropertiesUtil.getKeyValue("docPath");
			String downloadPath = basePath+System.getProperty("file.separator")+"download"+System.getProperty("file.separator");
			String fileName = project.getName() +"_"+dateFormats.format(new Date())+".zip";
			String docPath = downloadPath+fileName;
			try {
				ZipUtil.zips(list,docPath);
				CompletionFile completionFile = new CompletionFile();
				completionFile.setCreateDate(new Date());
				completionFile.setCreater((User)session.getAttribute("user"));
				completionFile.setName(fileName);
				completionFile.setPath(docPath);
				int j = completionFileService.save(completionFile);
				flag = true;
				message = j+"";
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
				message = e.getMessage();
			}
		}else{
			flag = false;
			message = "文档不存在";
		}
		return "addResult";
	}
	
	public String deleteCompletionInfoFile(){
		if(completionInfoIds.indexOf(",") == -1 && completionInfoIds!=null){
			completionInfoService.deleteById(Integer.parseInt(completionInfoIds));
		}else{
			String[] completionIds = completionInfoIds.split(",");
			for(String completionId:completionIds){
				completionInfoService.deleteById(Integer.parseInt(completionId));
			}
		}
		flag = true;
		MDC.put("operateContent","删除系统模块，ID："+completionInfoId); 
		LOG.info("");	
		return "addResult";
	}
	
	public String moveModuleFile(){
		SecurityUtils.getSubject().checkPermission("completionInfo:add");
		User creater = (User)session.getAttribute("user");
		try {
			BusDocEntity docEntity = new BusDocEntity();
			List<CompletionInfo> list = new ArrayList<CompletionInfo>();
			CompletionInfo completion = new CompletionInfo();
			if(completionInfo.getPath().indexOf(",") == -1 && completionInfo.getPath()!=null){
				//逻辑判断，当前项目文档已经在竣工资料，不再添加
				completion = completionInfoService.getCompletionInfoByFileId(completionInfo.getProject().getId(), Integer.parseInt(completionInfo.getPath()));
				if(completion == null){
					docEntity = busDocService.findById(Integer.parseInt(completionInfo.getPath()));
					completionInfo.setPath(docEntity.getDocPath());
					completionInfo.setName(docEntity.getDocName());
					completionInfo.setFileId(docEntity.getId());
					completionInfo.setDesc("操作人："+creater.getChinesename()+"，时间："+dateFormat.format(new Date())+"，将归卷文档："+docEntity.getDocName()+"，平移至竣工资料");
					completionInfo.setOrder(1);
					String orgSequence = entitySequenceService.findNext(EntityCodeHelper.getOrgPrefix(completionInfo.getLevel()),"COMPLETIONINFO");
					String newCode = EntityCodeHelper.generateCode(completionInfo.getLevel(),completionInfo.getParent().getDisplayCode(),orgSequence);
					completionInfo.setDisplayCode(newCode);
					completionInfo.setType(2);
					completionInfo.setStatus("N");
					completionInfo.setCreateDate(new Date());
					completionInfo.setCreater(creater);
					list.add(completionInfo);
				}
			}else{
				String[] fileIds = completionInfo.getPath().split(",");
				for (int i = 0; i < fileIds.length; i++) {
					//逻辑判断，当前项目文档已经在竣工资料，不再添加
					completion = completionInfoService.getCompletionInfoByFileId(completionInfo.getProject().getId(), Integer.parseInt(fileIds[i]));
					if(completion == null){
						completion = new CompletionInfo();
						docEntity = busDocService.findById(Integer.parseInt(fileIds[i]));
						completion.setParent(completionInfo.getParent());
						completion.setLevel(completionInfo.getLevel());
						completion.setProject(completionInfo.getProject());
						completion.setPath(docEntity.getDocPath());
						completion.setName(docEntity.getDocName());
						completion.setFileId(docEntity.getId());
						completion.setDesc("操作人："+creater.getChinesename()+"，时间："+dateFormat.format(new Date())+"，将归卷文档："+docEntity.getDocName()+"，平移至竣工资料");
						completion.setOrder(i);
						String orgSequence = entitySequenceService.findNext(EntityCodeHelper.getOrgPrefix(completion.getLevel()),"COMPLETIONINFO");
						String newCode = EntityCodeHelper.generateCode(completion.getLevel(),completion.getParent().getDisplayCode(),orgSequence);
						completion.setDisplayCode(newCode);
						completion.setType(2);
						completion.setStatus("N");
						completion.setCreateDate(new Date());
						completion.setCreater(creater);
						list.add(completion);
					}else{
						continue;
					}
		        }
			}
			completionInfoService.saveCompletionInfoForList(list);
			//创建文档目录
			String docPath = returnWebServerRootPath() + "docs"+System.getProperty("file.separator")+"files"+System.getProperty("file.separator");
			String projectDocPath = docPath + System.getProperty("file.separator") + "project_"+completionInfo.getProject().getId() + System.getProperty("file.separator");
			System.out.println("docPath="+docPath);
			System.out.println("projectDocPath="+projectDocPath);
			flag = true;
			MDC.put("operateContent",completionInfo.toString()); 
			LOG.info("");	
			List<CompletionInfo> completionInfoList =  completionInfoService.findPermissionNames();
			Map<String,String> permissionNames = new HashMap<String, String>();
			if(null!=completionInfoList&&completionInfoList.size()>0){
				for(CompletionInfo completionInfo:completionInfoList){
					if(CompletionInfoService.CompletionInfo_TYPE_FUNCTION == completionInfo.getType())permissionNames.put(completionInfo.getPath(),completionInfo.getName());
				}
			}
			ServletActionContext.getServletContext().setAttribute("permissionNames",permissionNames);
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "addResult";
	}
	
	
	public String getCompletionInfoFile(){
		StringBuffer sb = new StringBuffer();
		completionInfos = completionInfoService.queryCompletionInfoFile(projectId);
		if(completionInfos==null){
			sb.append("{id:0,pId:null,name:'系统无模块'}");
		}else{
			int index = completionInfos.size();
			for(CompletionInfo temp:completionInfos){
				sb.append("{id:"+temp.getId()+",pId:");
				sb.append((null!=temp.getParent())?temp.getParent().getId():"null");
				sb.append(",nocheck:"+(temp.getType()!=2?"true":"false"));
				sb.append(",name:'"+temp.getName()+"',path:'"+temp.getPath()+temp.getName()+"',open:true,isParent:true,checked:"+(temp.getType()==1?true:false)+(2==temp.getType()?",icon:'js/ztree/zTreeStyle/img/diy/2.png'":"")+"}");
				if(index>1)sb.append(",");
				index--;
			}
		}
		flag = true;
		message = "";
		StringBuffer ret = new StringBuffer();
		ret.append("{flag:"+flag+",message:'"+message+"',completionInfo:["+sb.toString()+"]}");
		message = ret.toString();
		return "messageResult";
	}
	//获取归卷文档
	public String getModuleFile(){
		StringBuffer sb = new StringBuffer();
		List<Module> list = moduleService.getCompletionInfoFile(projectId);
		if(list==null){
			sb.append("{id:0,pId:null,name:'暂无归卷文档'}");
			flag = false;
		}else{
			Set<Module> moduleSet = new HashSet<Module>();
			for(Module module:list){
				Module m = moduleService.findById(Integer.parseInt(module.getIcon()));
				module.setParent(m);
				moduleSet.add(module);
				moduleSetFile(moduleSet,m);
			}
			int index = moduleSet.size();
			for(Module temp:moduleSet){
				sb.append("{id:"+temp.getId()+",pId:");
				sb.append((null!=temp.getParent())?temp.getParent().getId():"null");
				sb.append(",nocheck:"+(temp.getType()!=2?"true":"false"));
				sb.append(",name:'"+temp.getName()+"',path:'"+temp.getPath()+"',open:true,isParent:true,checked:"+(temp.getType()==1?true:false)+(2==temp.getType()?",icon:'js/ztree/zTreeStyle/img/diy/2.png'":"")+"}");
				if(index>1)sb.append(",");
				index--;
			}
			flag = true;
		}
		message = "";
		StringBuffer ret = new StringBuffer();
		ret.append("{flag:"+flag+",message:'"+message+"',module:["+sb.toString()+"]}");
		message = ret.toString();
		return "messageResult";
	}
	
	public void moduleSetFile(Set<Module> moduleSet,Module module){
		if(module.getParent()!=null){
			moduleSetFile(moduleSet,module.getParent());
		}
		moduleSet.add(module);
	}
	
	public String list() throws IOException{
		response.setCharacterEncoding("utf-8");
		StringBuffer sb = new StringBuffer("[");
		if(null==completionInfoId) {
			completionInfo = completionInfoService.getRoot(projectId);
			if(null==completionInfo){
				sb.append("{id:0,pId:null,name:'系统无模块'}");
			}else{
				sb.append("{id:"+completionInfo.getId()+",isRoot:true,pId:");
				sb.append((null!=completionInfo.getParent())?completionInfo.getParent().getId():"null");
				sb.append(",name:'"+completionInfo.getName()+"',open:false,isParent:true,code:'"+completionInfo.getDisplayCode()+"'}");
			}
			sb.append("]");
			response.getWriter().append(sb.toString());
		}else {
			completionInfos = completionInfoService.getChild(Integer.valueOf(completionInfoId),1);
			for(int i = 0;i<completionInfos.size();i++){
				sb.append("{id:"+completionInfos.get(i).getId()+",code:'"+completionInfos.get(i).getDisplayCode()+"',pId:");
				sb.append((null!=completionInfos.get(i).getParent())?completionInfos.get(i).getParent().getId():"null");
				sb.append(",name:'"+completionInfos.get(i).getName()+"',open:false"+
						(CompletionInfoService.CompletionInfo_TYPE_FUNCTION==completionInfos.get(i).getType()?",icon:'js/ztree/zTreeStyle/img/diy/2.png'":"")+",isParent:");
				sb.append(completionInfos.get(i).getChild().size()!=0?"true}":"false}");		
				if(i!=completionInfos.size()-1)sb.append(",");
			}
			sb.append("]");
			MDC.put("operateContent","竣工树模块查询"); 
			LOG.info("");	
			response.getWriter().append(sb.toString());
		}
		return null;
	}
	
	public String add(){
		SecurityUtils.getSubject().checkPermission("completionInfo:add");
		User creater = (User)session.getAttribute("user");
		try {
			String orgSequence = entitySequenceService.findNext(EntityCodeHelper.getOrgPrefix(completionInfo.getLevel()),"COMPLETIONINFO");
			String newCode = EntityCodeHelper.generateCode(completionInfo.getLevel(),completionInfo.getParent().getDisplayCode(),orgSequence);
			completionInfo.setDisplayCode(newCode);
			completionInfo.setCreateDate(new Date());
			completionInfo.setCreater(creater);
			completionInfoService.save(completionInfo);
			//创建文档目录
			String docPath = returnWebServerRootPath() + "docs"+System.getProperty("file.separator")+"files"+System.getProperty("file.separator");
			String projectDocPath = docPath + System.getProperty("file.separator") + "project_"+completionInfo.getProject().getId() + System.getProperty("file.separator");
			System.out.println("projectDocPath="+projectDocPath);
			flag = true;
			MDC.put("operateContent",completionInfo.toString()); 
			LOG.info("");	
			List<CompletionInfo> completionInfoList =  completionInfoService.findPermissionNames();
			Map<String,String> permissionNames = new HashMap<String, String>();
			if(null!=completionInfoList&&completionInfoList.size()>0){
				for(CompletionInfo completionInfo:completionInfoList){
					if(CompletionInfoService.CompletionInfo_TYPE_FUNCTION == completionInfo.getType())permissionNames.put(completionInfo.getPath(),completionInfo.getName());
				}
			}
			ServletActionContext.getServletContext().setAttribute("permissionNames",permissionNames);
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "addResult";
	}
	
	public String get(){
		try {
			completionInfo = completionInfoService.findById(completionInfo.getId());
			flag = true;
			MDC.put("operateContent","查询竣工树模块："+completionInfo.getId()); 
			LOG.info("");			
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "objectResult";
	}	
	
	public String delete(){
		SecurityUtils.getSubject().checkPermission("completionInfo:delete");
		try {
			completionInfoService.deleteCompletionInfoById(completionInfoId);
			flag = true;
			MDC.put("operateContent","删除竣工树模块，ID："+completionInfoId); 
			LOG.info("");	
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "addResult";
	}
	
	public String update(){
		SecurityUtils.getSubject().checkPermission("completionInfo:update");
		User creater = (User)session.getAttribute("user");
		try {
			//针对根节点的更新修正
			if(null!=completionInfo&&null!=completionInfo.getParent()&&null==completionInfo.getParent().getId()){
				completionInfo.setParent(null);
			}
			completionInfo.setCreateDate(new Date());
			completionInfo.setCreater(creater);
			completionInfoService.saveOrUpdate(completionInfo);
			flag = true;
			MDC.put("operateContent",completionInfo.toString()); 
			LOG.info("");	
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "addResult";
	}
	
	public String freshCode(){
		flag = false;
		if(null!=completionInfoId&&0!=completionInfoId){
			entitySequenceService.resetEntitySequence("COMPLETIONINFO");
			refreshModuleCode(completionInfoId);
			flag = true;
			message="竣工树编码刷新成功。";
		}
		return "addResult";
	}
	
	private void refreshModuleCode(Integer pid){
		if(null!=pid&&0!=pid){
			List<CompletionInfo> childResources = completionInfoService.getChild(pid,1);
			for(CompletionInfo temp:childResources){
				String resourceSequence = entitySequenceService.findNext(EntityCodeHelper.getOrgPrefix(temp.getLevel()),"COMPLETIONINFO");
				String resourceDisplayCode = EntityCodeHelper.generateCode(temp.getLevel(),temp.getParent().getDisplayCode(),resourceSequence);
				temp.setDisplayCode(resourceDisplayCode);
				completionInfoService.saveOrUpdate(temp);
				refreshModuleCode(temp.getId());
			}
		}
	}
	
	
	public CompletionInfo getCompletionInfo() {
		return completionInfo;
	}
	public void setCompletionInfo(CompletionInfo completionInfo) {
		this.completionInfo = completionInfo;
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
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<CompletionInfo> getCompletionInfos() {
		return completionInfos;
	}
	public void setCompletionInfos(List<CompletionInfo> completionInfos) {
		this.completionInfos = completionInfos;
	}
	public Integer getCompletionInfoId() {
		return completionInfoId;
	}
	public void setCompletionInfoId(Integer completionInfoId) {
		this.completionInfoId = completionInfoId;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public String getCompletionInfoIds() {
		return completionInfoIds;
	}
	public void setCompletionInfoIds(String completionInfoIds) {
		this.completionInfoIds = completionInfoIds;
	}	
}