package cn.promore.bf.action;
/**
 * 系统模块类Controller
 * @author wangjg
 * @date   2017-06-17
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import cn.promore.bf.bean.Module;
import cn.promore.bf.bean.Page;
import cn.promore.bf.model.BusDocEntity;
import cn.promore.bf.serivce.BusDocService;
import cn.promore.bf.serivce.EntitySequenceService;
import cn.promore.bf.serivce.ModuleService;
import cn.promore.bf.unit.EntityCodeHelper;

@Controller
@Action(value="moduleAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="tojson",type="json",params={"root","message","excludeNullProperties","true"}),
	      @Result(name="mapJson",type="json"),
		  @Result(name="resJson",type="json",params={"excludeProperties","moduleService","excludeNullProperties","true"}),
		  @Result(name="addResult",type="json",params={"includeProperties","flag,message"}),
		  @Result(name="result",type="json",params={"includeProperties","moduleService,module.*\\.child,module.*\\.roles","excludeNullProperties","true"}),
		  @Result(name="objectResult",type="json",params={"includeProperties","flag,message,module\\.(id|name|desc|type|order|path|icon|level|status|docType|displayCode|childNo),module\\.parent\\.(id|name|desc|type|order|path|icon|level|status|docType|displayCode)","excludeNullProperties","true"}),
		  @Result(name="listResult",type="json",params={"includeProperties","modules\\[\\d+\\]\\.(id|name|desc|type|order|path|icon|level|status|docType|displayCode|childNo),modules\\[\\d+\\]\\.parent\\.(id|name|desc|type|order|path|icon|level|status|docType|displayCode),page\\.(\\w+),flag,message","excludeNullProperties","true"})
		 })
public class ModuleAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	public static Logger LOG = LoggerFactory.getLogger(ModuleAction.class);
	@Resource 
	ModuleService moduleService;
	@Resource
	EntitySequenceService entitySequenceService;
	@Resource
	BusDocService busDocService;
	private cn.promore.bf.bean.Module module;
	private boolean flag = true;
	private String message;
	private Page page;
	private List<cn.promore.bf.bean.Module> modules;
	private Integer moduleId;
	private Map<String, Object> data;
	private String processFlag;
	
	public ModuleAction() {
		MDC.put("operateModuleName","模块管理");
	}
	
	public String queryfilePath(){
		module = moduleService.findById(moduleId);
		filePath(module,"");
		return message;
	}
	
	
	public void filePath(Module module,String message){
		if(module!=null&&module.getLevel()!=0){
			message = module.getName()+"/"+message;
			filePath(module.getParent(),message);
		}
	}
	
	
	public String queryModuleList(){
		List<Module> d1 = moduleService.getModuleChild(moduleId);
		data = new HashMap<String, Object>();
		data.put("L1", d1);
		MDC.put("operateContent","系统模块查询"); 
		LOG.info("");	
		return "mapJson";
	}
	
	public String queryFileModule(String name){
		flag = false;
		if(null!=name&&""!=name){
			modules = moduleService.getModule(name);
			flag = true;
			message="查询文档模板成功！";
		}
		return "listResult";
	}
	
	private void refreshModuleCode(Integer pid){
		if(null!=pid&&0!=pid){
			List<cn.promore.bf.bean.Module> childResources = moduleService.findChildByPid(pid);
			for(cn.promore.bf.bean.Module temp:childResources){
				String resourceSequence = entitySequenceService.findNext(EntityCodeHelper.getOrgPrefix(temp.getLevel()),"MODULE");
				String resourceDisplayCode = EntityCodeHelper.generateCode(temp.getLevel(),temp.getParent().getDisplayCode(),resourceSequence);
				temp.setDisplayCode(resourceDisplayCode);
				moduleService.saveOrUpdate(temp);
				refreshModuleCode(temp.getId());
			}
		}
	}
	
	public String freshCode(){
		flag = false;
		if(null!=moduleId&&0!=moduleId){
			entitySequenceService.resetEntitySequence("MODULE");
			refreshModuleCode(moduleId);
			flag = true;
			message="资源编码刷新成功。";
		}
		return "addResult";
	}
	
	
	public int queryModuleLevel2Length(List<Module> list){
		int i =0;
		int j =0;
		for(Module module:list){
			i = module.getName().trim().length();
			j = i>j?i:j;
		}
		return j;
	}
	
	public int queryModuleLevel3Length(List<Module> list){
		int i =0;
		int j =0;
		for(Module module:list){
			List<Module> lists = module.getChild();
			for(Module m:lists){
				if(m.getType()==1){
					i = m.getName().trim().length();
					j = i>j?i:j;
				}
			}
		}
		return j;
	}
	
	
	
	public String queryModuleListById() throws IOException{
		response.setCharacterEncoding("utf-8");
		flag = false;
		module = moduleService.findById(moduleId);
		int level1length = 0;
		int level2length = 0;
		List<Module> list = new ArrayList<Module>();
		List<Module> secondlist = new ArrayList<Module>();
		StringBuffer strBuffer = new StringBuffer("{\"module\":");
		if(module.getLevel()==0){
			strBuffer.append("[");
			modules = module.getChild();
			for(int i=0;i<modules.size();i++){
				strBuffer.append("{\"name\":\""+modules.get(i).getName()+"\",\"id\":\""+modules.get(i).getId()+"\",\"icon\":\""+modules.get(i).getIcon()+"\"");
				list = modules.get(i).getChild();
				strBuffer.append(",\"child\":[");
				for(int k=0;k<list.size();k++){
					strBuffer.append("{\"name\":\""+list.get(k).getName()+"\",\"id\":\""+list.get(k).getId()+"\",\"icon\":\""+list.get(k).getIcon()+"\"");
					if(list.get(k).getChildNo() !=0){
						strBuffer.append(",\"child\":[");
						secondlist = list.get(k).getChild();
						for(int j=0;j<secondlist.size();j++){
							strBuffer.append("{\"name\":\""+secondlist.get(j).getName()+"\",\"id\":\""+secondlist.get(j).getId()+"\",\"icon\":\""+secondlist.get(j).getIcon()+"\"}");
							if(j!=secondlist.size()-1)strBuffer.append(",");
						}
						strBuffer.append("]");
					}
					strBuffer.append("}");
					if(k!=list.size()-1)strBuffer.append(",");
				}
				strBuffer.append("]");
				strBuffer.append("}");
				if(i!=modules.size()-1)strBuffer.append(",");
			}
			strBuffer.append("]}");
			MDC.put("operateContent","模板查询"); 
			LOG.info("");
			response.getWriter().append(strBuffer.toString());
		}else if(module.getLevel()==1){
			strBuffer.append("[{\"name\":\""+module.getName()+"\",\"id\":\""+moduleId+"\",\"pid\":\"null\"");
			if(module.getChildNo() != 0){
				strBuffer.append(",\"childrens\":[");
				modules = module.getChild();
				level1length = queryModuleLevel2Length(modules);
				level2length = queryModuleLevel3Length(modules);
				for(int j=0;j<modules.size();j++){
					strBuffer.append("{\"name\":\""+modules.get(j).getName()+"\",\"length\":\""+level1length+"\",\"id\":\""+modules.get(j).getId()+"\",\"pid\":\""+modules.get(j).getParent().getId()+"\"");
					if(modules.get(j).getChildNo() !=0){
						strBuffer.append(",\"childrens\":[");
						list = modules.get(j).getChild();
						secondlist.clear();
						for(Module m:list){
							if(m.getType() ==1){secondlist.add(m);}
						}
						for(int i = 0;i<secondlist.size();i++){
							strBuffer.append("{\"name\":\""+secondlist.get(i).getName()+"\",\"length\":\""+level2length+"\",\"id\":\""+secondlist.get(i).getId()+"\",\"pid\":\""+secondlist.get(i).getParent().getId()+"\",\"childrens\":[]}");
							if(i!=secondlist.size()-1)strBuffer.append(",");
						}
						strBuffer.append("]");
					}else{
						strBuffer.append(",\"childrens\":[]");
					}
					strBuffer.append("}");
					if(j!=modules.size()-1)strBuffer.append(",");
				}
				strBuffer.append("]");
			}else{
				strBuffer.append(",\"childrens\":[]");
			}
			strBuffer.append("}]}");
			MDC.put("operateContent","模板查询"); 
			LOG.info("");
			response.getWriter().append(strBuffer.toString());
		}else if(module.getLevel()==2){
			Module modul = module.getParent();
			strBuffer.append("{\"name\":\""+modul.getName()+"\",\"id\":\""+modul.getId()+"\",\"pid\":\"null\"");
			strBuffer.append(",\"childrens\":{\"name\":\""+module.getName()+"\",\"id\":\""+module.getId()+"\",\"pid\":\""+modul.getId()+"\"");
			if(module.getChildNo() != 0){
				strBuffer.append(",\"childrens\":[");
				modules = module.getChild();
				for(int j=0;j<modules.size();j++){
					strBuffer.append("{\"name\":\""+modules.get(j).getName()+"\",\"id\":\""+modules.get(j).getId()+"\",\"pid\":\""+module.getId()+"\",\"childrens\":[]}");
					if(j!=modules.size()-1)strBuffer.append(",");
				}
				strBuffer.append("]");
			}
			strBuffer.append("}}}");
			MDC.put("operateContent","模板查询"); 
			LOG.info("");
			response.getWriter().append(strBuffer.toString());
		}else{
			Module modul = module.getParent();
			Module modu = modul.getParent();
			strBuffer.append("{\"fatherName\":\""+modu.getName()+"\",\"fatherId\":\""+modu.getId()+"\"");
			strBuffer.append(",\"child\":{\"name\":\""+modul.getName()+"\",\"id\":\""+modul.getId()+"\"");
			strBuffer.append(",\"child\":{\"name\":\""+module.getName()+"\",\"id\":\""+module.getId()+"\"}");
			strBuffer.append("}}}");
			MDC.put("operateContent","模板查询"); 
			LOG.info("");
			response.getWriter().append(strBuffer.toString());
		}
		return null;
	}
	
	
	public String moduleFile() throws IOException{
		response.setCharacterEncoding("utf-8");
		StringBuffer sb = new StringBuffer("{\"fileList\":[");
		if("1".equals(processFlag)){
			if(message!=null&&message.indexOf(",") > 0){
				String[] msg = message.split(",");
				for(int i=0;i<msg.length;i++){
					BusDocEntity busDocEntity =  busDocService.findById(Integer.parseInt(msg[i]));
					sb.append("{\"id\":\""+busDocEntity.getId()+"\",\"docType\":\""+busDocEntity.getDocType()+"\",\"fileName\":\""+busDocEntity.getDocName()+"\",\"filePath\":\""+busDocEntity.getDocPath().replace("\\", "/")+"\"}");
					if(i!=msg.length-1)sb.append(",");
				}
				sb.append("]}");
				MDC.put("operateContent","模板文件查询"); 
				LOG.info("");	
				response.getWriter().append(sb.toString());
			}else{
				BusDocEntity busDocEntity =  busDocService.findById(Integer.parseInt(message));
				sb.append("{\"id\":\""+busDocEntity.getId()+"\",\"docType\":\""+busDocEntity.getDocType()+"\",\"fileName\":\""+busDocEntity.getDocName()+"\",\"filePath\":\""+busDocEntity.getDocPath().replace("\\", "/")+"\"}");
				sb.append("]}");
				MDC.put("operateContent","模板文件查询"); 
				LOG.info("");	
				response.getWriter().append(sb.toString());
			
			}
		}else{
			if(message!=null&&message.indexOf(",") > 0){
				String[] msg = message.split(",");
				for(int i=0;i<msg.length;i++){
					module = moduleService.findById(Integer.parseInt(msg[i]));
					sb.append("{\"id\":\""+module.getId()+"\",\"docType\":\""+module.getDocType()+"\",\"fileName\":\""+module.getName()+"\",\"filePath\":\""+module.getPath()+"\"}");
					if(i!=msg.length-1)sb.append(",");
				}
				sb.append("]}");
				MDC.put("operateContent","模板文件查询"); 
				LOG.info("");	
				response.getWriter().append(sb.toString());
			}else{
				module = moduleService.findById(Integer.parseInt(message));
				sb.append("{\"id\":\""+module.getId()+"\",\"docType\":\""+module.getDocType()+"\",\"fileName\":\""+module.getName()+"\",\"filePath\":\""+module.getPath()+"\"}");
				sb.append("]}");
				MDC.put("operateContent","模板文件查询"); 
				LOG.info("");	
				response.getWriter().append(sb.toString());
			}
		}
		return null;
	}
	
	
	
	@SuppressWarnings("static-access")
	public String listFile() throws IOException{ 
		response.setCharacterEncoding("utf-8");
		StringBuffer sb = new StringBuffer("[");
		if(null==moduleId) {
			module = moduleService.findById(Integer.parseInt(message));
			if(null==module){
				sb.append("{id:0,pId:null,name:'系统无模块'}");
			}else{
				sb.append("{id:"+module.getId()+",isRoot:true,pId:");
				sb.append((null!=module.getParent())?module.getParent().getId():"null");
				sb.append(",nocheck:"+(module.getType()!=2?"true":"false"));
				sb.append(",name:'"+module.getName()+"',desc:'"+module.getDesc()+"',open:false,isParent:true,code:'"+module.getDisplayCode()+"'}");
			}
			sb.append("]");
			MDC.put("operateContent","流程部署模板文件查询"); 
			LOG.info("");	
			response.getWriter().append(sb.toString());
		}else {
			modules = moduleService.getModuleFile(Integer.valueOf(moduleId));
			for(int i = 0;i<modules.size();i++){
				sb.append("{id:"+modules.get(i).getId()+",code:'"+modules.get(i).getDisplayCode()+"',pId:");
				sb.append((null!=modules.get(i).getParent())?modules.get(i).getParent().getId():"null");
				sb.append(",nocheck:"+(modules.get(i).getType()!=2?"true":"false"));
				sb.append(",name:'"+modules.get(i).getName()+"',desc:'"+modules.get(i).getDesc()+"',open:false"+
							(moduleService.MODULE_TYPE_FUNCTION==modules.get(i).getType()?",icon:'js/ztree/zTreeStyle/img/diy/shield.png'":"")+",isParent:");
				sb.append(modules.get(i).getChild().size()!=0?"true}":"false}");		
				if(i!=modules.size()-1)sb.append(",");
			}
			sb.append("]");
			MDC.put("operateContent","流程部署模板文件查询"); 
			LOG.info("");	
			response.getWriter().append(sb.toString());
		}
		return null;
	}
	
	@SuppressWarnings("static-access")
	public String listAll() throws IOException{
		response.setCharacterEncoding("utf-8");
		StringBuffer sb = new StringBuffer("[");
		if(null==moduleId) {
			module = moduleService.getRoot();
			if(null==module){
				sb.append("{id:0,pId:null,name:'系统无模块'}");
			}else{
				sb.append("{id:"+module.getId()+",isRoot:true,pId:");
				sb.append((null!=module.getParent())?module.getParent().getId():"null");
				sb.append(",name:'"+module.getName()+"',desc:'"+module.getDesc()+"',open:false,isParent:true,code:'"+module.getDisplayCode()+"'}");
			}
			sb.append("]");
			response.getWriter().append(sb.toString());
		}else {
			modules = moduleService.getModuleChild(Integer.valueOf(moduleId));
			for(int i = 0;i<modules.size();i++){
				sb.append("{id:"+modules.get(i).getId()+",code:'"+modules.get(i).getDisplayCode()+"',pId:");
				sb.append((null!=modules.get(i).getParent())?modules.get(i).getParent().getId():"null");
				sb.append(",name:'"+modules.get(i).getName()+"',desc:'"+modules.get(i).getDesc()+"',open:false"+
							(moduleService.MODULE_TYPE_FUNCTION==modules.get(i).getType()?",icon:'js/ztree/zTreeStyle/img/diy/shield.png'":"")+",isParent:");
				sb.append(modules.get(i).getChild().size()!=0?"true}":"false}");		
				if(i!=modules.size()-1)sb.append(",");
			}
			sb.append("]");
			MDC.put("operateContent","流程部署模块查询"); 
			LOG.info("");	
			response.getWriter().append(sb.toString());
		}
		return null;
	}
	
	@SuppressWarnings("static-access")
	public String list() throws IOException{
		SecurityUtils.getSubject().checkPermission("module:get");
		response.setCharacterEncoding("utf-8");
		StringBuffer sb = new StringBuffer("[");
		if(null==moduleId) {
			module = moduleService.getRoot();
			if(null==module){
				sb.append("{id:0,pId:null,name:'系统无模块'}");
			}else{
				sb.append("{id:"+module.getId()+",isRoot:true,pId:");
				sb.append((null!=module.getParent())?module.getParent().getId():"null");
				sb.append(",name:'"+module.getName()+"',desc:'"+module.getDesc()+"',open:false,isParent:true,code:'"+module.getDisplayCode()+"'}");
			}
			sb.append("]");
			response.getWriter().append(sb.toString());
		}else {
			modules = moduleService.getChild(Integer.valueOf(moduleId));
			for(int i = 0;i<modules.size();i++){
				sb.append("{id:"+modules.get(i).getId()+",code:'"+modules.get(i).getDisplayCode()+"',pId:");
				sb.append((null!=modules.get(i).getParent())?modules.get(i).getParent().getId():"null");
				sb.append(",name:'"+modules.get(i).getName()+"',desc:'"+modules.get(i).getDesc()+"',open:false"+
						(moduleService.MODULE_TYPE_FUNCTION==modules.get(i).getType()?",icon:'js/ztree/zTreeStyle/img/diy/shield.png'":"")+",isParent:");
				sb.append(modules.get(i).getChild().size()!=0?"true}":"false}");		
				if(i!=modules.size()-1)sb.append(",");
			}
			sb.append("]");
			MDC.put("operateContent","系统模块查询"); 
			LOG.info("");	
			response.getWriter().append(sb.toString());
		}
		return null;
	}
	
	public String add(){
		SecurityUtils.getSubject().checkPermission("module:add");
		try {
			String orgSequence = entitySequenceService.findNext(EntityCodeHelper.getOrgPrefix(module.getLevel()),"MODULE");
			String newCode = EntityCodeHelper.generateCode(module.getLevel(),module.getParent().getDisplayCode(),orgSequence);
			module.setDisplayCode(newCode);
			moduleService.save(module);
			flag = true;
			MDC.put("operateContent",module.toString()); 
			LOG.info("");	
			List<cn.promore.bf.bean.Module> moduleList =  moduleService.findPermissionNames();
			Map<String,String> permissionNames = new HashMap<String, String>();
			if(null!=moduleList&&moduleList.size()>0){
				for(cn.promore.bf.bean.Module module:moduleList){
					if(ModuleService.MODULE_TYPE_FUNCTION == module.getType())permissionNames.put(module.getPath(),module.getName());
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
			module = moduleService.findById(module.getId());
			flag = true;
			MDC.put("operateContent","查询模块："+module.getId()); 
			LOG.info("");			
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "objectResult";
	}		
	
	public String delete(){
		SecurityUtils.getSubject().checkPermission("module:delete");
		try {
			moduleService.deleteModuleById(moduleId);
			flag = true;
			MDC.put("operateContent","删除系统模块，ID："+moduleId); 
			LOG.info("");	
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "addResult";
	}
	
	public String update(){
		SecurityUtils.getSubject().checkPermission("module:update");
		try {
			//针对根节点的更新修正
			if(null!=module&&null!=module.getParent()&&null==module.getParent().getId()){
				module.setParent(null);
			}
			moduleService.saveOrUpdate(module);
			flag = true;
			MDC.put("operateContent",module.toString()); 
			LOG.info("");	
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "addResult";
	}
	
	@SuppressWarnings("static-access")
	public String queryCompleteFiles() throws IOException{ 
		response.setCharacterEncoding("utf-8");
		StringBuffer sb = new StringBuffer("[");
		if(null == moduleId){
			modules = moduleService.getAllRootModules();
			for(int i = 0;i<modules.size();i++){
				sb.append("{id:"+modules.get(i).getId()+",code:'"+modules.get(i).getDisplayCode()+"',pId:");
				sb.append((null!=modules.get(i).getParent())?modules.get(i).getParent().getId():"null");
				sb.append(",nocheck:"+(modules.get(i).getType()!=2?"true":"false"));
				sb.append(",name:'"+modules.get(i).getName()+"',desc:'"+modules.get(i).getDesc()+"',open:false"+
							(moduleService.MODULE_TYPE_FUNCTION==modules.get(i).getType()?",icon:'js/ztree/zTreeStyle/img/diy/shield.png'":"")+",isParent:");
				sb.append(modules.get(i).getChild().size()!=0?"true}":"false}");
				if(i!=modules.size()-1)sb.append(",");
			}
		}else{
			List<Module> secondList = moduleService.getDoneFiles(moduleId);
			//取归档后的文档
			for(int a=0; a<secondList.size(); a++){
				//Integer pid = secondList.get(a).getId();
				//List<Module> childs = moduleService.findChildByPid(pid);
				sb.append("{id:"+secondList.get(a).getId()+",code:'"+secondList.get(a).getDisplayCode()+"',pId:");
				sb.append(moduleId);
				sb.append(",nocheck:false");
				sb.append(",name:'"+secondList.get(a).getName()+"',desc:'"+secondList.get(a).getName()+"',open:false"+
							(moduleService.MODULE_TYPE_FUNCTION==secondList.get(a).getType()?",icon:'js/ztree/zTreeStyle/img/diy/shield.png'":"")+",isParent:false");
				sb.append("}");
				
				if(a!=secondList.size()-1){
					sb.append(",");
				}
			}
		}
		sb.append("]");
		MDC.put("operateContent","流程部署模板文件查询"); 
		LOG.info("");	
		response.getWriter().append(sb.toString());
		return null;
	}
	
	
	public cn.promore.bf.bean.Module getModule() {
		return module;
	}
	public void setModule(cn.promore.bf.bean.Module module) {
		this.module = module;
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
	public List<cn.promore.bf.bean.Module> getModules() {
		return modules;
	}
	public void setModules(List<cn.promore.bf.bean.Module> modules) {
		this.modules = modules;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public ModuleService getModuleService() {
		return moduleService;
	}
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}
}
