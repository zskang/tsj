package cn.promore.bf.action;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.io.IOException;
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

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.User;
import cn.promore.bf.serivce.EntitySequenceService;
import cn.promore.bf.serivce.ResourceService;
import cn.promore.bf.unit.EntityCodeHelper;

@Controller
@Action(value="resAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="tojson",type="json",params={"root","message","excludeNullProperties","true"}),
		 @Result(name="resJson",type="json",params={"excludeProperties","resourceService","excludeNullProperties","true"}),
		 @Result(name="addResult",type="json",params={"includeProperties","flag,message"}),
		 @Result(name="result",type="json",params={"includeProperties","resourceService,resource.*\\.child,resource.*\\.roles","excludeNullProperties","true"}),
		 @Result(name="objectResult",type="json",params={"includeProperties","flag,message,resource\\.(id|name|desc|type|order|path|icon|level|status|displayCode|childNo),resource\\.parent\\.(id|name|desc|type|order|path|icon|level|status|displayCode)","excludeNullProperties","true"}),
		 @Result(name="listResult",type="json",params={"includeProperties","resources\\[\\d+\\]\\.(id|name|desc|type|order|path|icon|level|status|displayCode|childNo),resources\\[\\d+\\]\\.parent\\.(id|name|desc|type|order|path|icon|level|status|displayCode),page\\.(\\w+),flag,message","excludeNullProperties","true"})
		 })
public class ResourceAction extends BaseAction{
	private static final long serialVersionUID = -6535046756157494130L;
	public static Logger LOG = LoggerFactory.getLogger(ResourceAction.class);
	@Resource 
	ResourceService resourceService;
	@Resource
	EntitySequenceService entitySequenceService;
	private cn.promore.bf.bean.Resource resource;
	private boolean flag = true;
	private String message;
	private Page page;
	private List<cn.promore.bf.bean.Resource> resources;
	private Integer resId;
	
	public ResourceAction() {
		MDC.put("operateModuleName","资源管理");
	}
	
	private void refreshResourceCode(Integer pid){
		if(null!=pid&&0!=pid){
			List<cn.promore.bf.bean.Resource> childResources = resourceService.findChildByPid(pid);
			for(cn.promore.bf.bean.Resource temp:childResources){
				String resourceSequence = entitySequenceService.findNext(EntityCodeHelper.getOrgPrefix(temp.getLevel()),"RESOURCE");
				String resourceDisplayCode = EntityCodeHelper.generateCode(temp.getLevel(),temp.getParent().getDisplayCode(),resourceSequence);
				temp.setDisplayCode(resourceDisplayCode);
				resourceService.saveOrUpdate(temp);
				refreshResourceCode(temp.getId());
			}
		}
	}
	
	public String freshCode(){
		SecurityUtils.getSubject().checkPermission("resource:refreshCode");
		flag = false;
		if(null!=resId&&0!=resId){
			entitySequenceService.resetEntitySequence("RESOURCE");
			refreshResourceCode(resId);
			flag = true;
			message="资源编码刷新成功。";
		}
		return "addResult";
	}
	
    public String loadNavigate(){
    	User user = (User)request.getSession().getAttribute("user");
    	resources = null!=resId?resourceService.findRoleResources(user.getRoles(),resId,ResourceService.RESOURCE_TYPE_MENU):null;
    	return "listResult";
    }
	
	public String list() throws IOException{
		SecurityUtils.getSubject().checkPermission("resource:get");
		response.setCharacterEncoding("utf-8");
		StringBuffer sb = new StringBuffer("[");
		if(null==resId) {
			resource = resourceService.getRoot();
			if(null==resource){
				sb.append("{id:0,pId:null,name:'系统无资源'}");
			}else{
				sb.append("{id:"+resource.getId()+",isRoot:true,pId:");
				sb.append((null!=resource.getParent())?resource.getParent().getId():"null");
				sb.append(",name:'"+resource.getName()+"',desc:'"+resource.getDesc()+"',open:false,isParent:true,code:'"+resource.getDisplayCode()+"'}");
			}
			sb.append("]");
			response.getWriter().append(sb.toString());
		}else {
			resources = resourceService.getChild(Integer.valueOf(resId));
			for(int i = 0;i<resources.size();i++){
				sb.append("{id:"+resources.get(i).getId()+",code:'"+resources.get(i).getDisplayCode()+"',pId:");
				sb.append((null!=resources.get(i).getParent())?resources.get(i).getParent().getId():"null");
				sb.append(",name:'"+resources.get(i).getName()+"',desc:'"+resources.get(i).getDesc()+"',open:false"+
						(ResourceService.RESOURCE_TYPE_FUNCTION==resources.get(i).getType()?",icon:'js/ztree/zTreeStyle/img/diy/shield.png'":"")+",isParent:");
				sb.append(resources.get(i).getChild().size()!=0?"true}":"false}");		
				if(i!=resources.size()-1)sb.append(",");
			}
			sb.append("]");
			MDC.put("operateContent","系统资源查询"); 
			LOG.info("");	
			response.getWriter().append(sb.toString());
		}
		return null;
	}
    
	public String add(){
		SecurityUtils.getSubject().checkPermission("resource:add");
		try {
			String orgSequence = entitySequenceService.findNext(EntityCodeHelper.getOrgPrefix(resource.getLevel()),"RESOURCE");
			String newCode = EntityCodeHelper.generateCode(resource.getLevel(),resource.getParent().getDisplayCode(),orgSequence);
			resource.setDisplayCode(newCode);
			resourceService.save(resource);
			flag = true;
			MDC.put("operateContent",resource.toString()); 
			LOG.info("");	
			
			List<cn.promore.bf.bean.Resource> resourceList =  resourceService.findPermissionNames();
			Map<String,String> permissionNames = new HashMap<String, String>();
			if(null!=resourceList&&resourceList.size()>0){
				for(cn.promore.bf.bean.Resource resource_:resourceList){
					if(ResourceService.RESOURCE_TYPE_FUNCTION == resource_.getType())permissionNames.put(resource_.getPath(),resource_.getName());
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
		SecurityUtils.getSubject().checkPermission("resource:get");
		try {
			resource = resourceService.findById(resource.getId());
			flag = true;
			MDC.put("operateContent","查询资源："+resource.getId()); 
			LOG.info("");			
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "objectResult";
	}		
	
	public String delete(){
		SecurityUtils.getSubject().checkPermission("resource:delete");
		try {
			resourceService.deleteById(resId);
			flag = true;
			MDC.put("operateContent","删除系统资源，ID："+resId); 
			LOG.info("");	
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "addResult";
	}
	
	public String update(){
		SecurityUtils.getSubject().checkPermission("resource:update");
		try {
			//针对根节点的更新修正
			if(null!=resource&&null!=resource.getParent()&&null==resource.getParent().getId()){
				resource.setParent(null);
			}
			resourceService.saveOrUpdate(resource);
			flag = true;
			MDC.put("operateContent",resource.toString()); 
			LOG.info("");	
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "addResult";
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
	public ResourceService getResourceService() {
		return resourceService;
	}
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	public cn.promore.bf.bean.Resource getResource() {
		return resource;
	}
	public void setResource(cn.promore.bf.bean.Resource resource) {
		this.resource = resource;
	}
	public List<cn.promore.bf.bean.Resource> getResources() {
		return resources;
	}
	public void setResources(List<cn.promore.bf.bean.Resource> resources) {
		this.resources = resources;
	}
	public Integer getResId() {
		return resId;
	}
	public void setResId(Integer resId) {
		this.resId = resId;
	}
}
