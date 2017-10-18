package cn.promore.bf.action;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.Role;
import cn.promore.bf.serivce.ResourceService;
import cn.promore.bf.serivce.RoleService;

@Controller
@Action(value="roleAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="objectResult",type="json",params={"includeProperties","flag,message,role\\.(id|name|desc|canInherit|isBuiltIn|region|shortName)","excludeNullProperties","true"}),
		 @Result(name="listResult",type="json",params={"includeProperties","roles\\[\\d+\\]\\.(id|name|desc|canInherit|isBuiltIn|region|shortName),page\\.(\\w+),flag,message,authRoles\\[\\d+\\]\\.*","excludeNullProperties","true"}),
		 @Result(name="result",type="json",params={"includeProperties","flag,message"}),
		 @Result(name="messageResult",type="json",params={"root","message"}),
		 @Result(name="config",type="json",params={"root","flag"})
		 })
public class RoleAction extends BaseAction{
	public static Logger LOG = LoggerFactory.getLogger(RoleAction.class);
	private static final long serialVersionUID = -8613055080615406396L;
	
	@Resource 
	RoleService roleService;
	@Resource
	ResourceService resourceService;
	private Role role;
	private boolean flag = true;
	private String message;
	private Page page;
	private List<Role> roles;
	private List<cn.promore.bf.bean.Resource> resources;
	private String dataIds;
	private List<Integer> authRoles;

	public RoleAction() {
		MDC.put("operateModuleName","角色管理");
	}
	
	public String add(){
		SecurityUtils.getSubject().checkPermission("role:add");
		try {
			if(null!=role&&StringUtils.isNotEmpty(role.getRegion())){
				SecurityUtils.getSubject().checkPermission("role>add>"+role.getRegion());
				roleService.save(role);
				flag = true;
				message = "添加成功！";
			}else{
				flag = false;
				message = "添加错误，关键参数缺失！";
			}
			MDC.put("operateContent","角色添加："+role.toString()); 
			LOG.info("");
		} catch (AuthorizationException e) {
			flag = false;
			message = "不具备添加对应角色权限范围的权限";
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			message = e.getMessage();
		}
		return "result";
	}
	
	public String get(){
		SecurityUtils.getSubject().checkPermission("role:get");
		try {
			role = roleService.findById(role.getId());
			flag = true;
			MDC.put("operateContent","查询角色，编码："+role.getId()); 
			LOG.info("");			
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "objectResult";
	}
	
	public String update(){
			SecurityUtils.getSubject().checkPermission("role:update");
			Role temp = roleService.findById(role.getId());
			try {
				SecurityUtils.getSubject().checkPermission("role>update>"+temp.getRegion());
				if(null!=temp){
					temp.setName(role.getName());
					temp.setCanInherit(role.getCanInherit());
					temp.setDesc(role.getDesc());
					temp.setShortName(role.getShortName());
					temp.setRegion(role.getRegion());
					roleService.update(temp);
					flag = true;
				}
				MDC.put("operateContent","角色更新："+role.toString()); 
				LOG.info("");
			} catch (AuthorizationException e) {
				flag = false;
				message = "不具备更新角色【"+temp.getName()+"】的权限";
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
			Role tempRole = null;
			for(String temp:roleIds){
				try {
					tempRole = roleService.findById(Integer.valueOf(temp));
					SecurityUtils.getSubject().checkPermission("role>delete>"+tempRole.getRegion());
					roleService.deleteById(Integer.valueOf(temp));
					MDC.put("operateContent","角色删除，编码："+temp); 
					LOG.info("");					
				} catch (AuthorizationException e) {
					flag = false;
					message += "不具备删除角色【"+tempRole.getName()+"】的权限";
				}  catch (Exception e) {
					flag = false;
					e.printStackTrace();
					message += "该角色有关联用户，请先解除关联后再操作！";
				}				
			}
		}
		return "result";
	}
	
	public String list(){
		SecurityUtils.getSubject().checkPermission("role:get");
		if(null!=page){
			 page.setTotalRecords(roleService.findDataNo(role));
			 roles = roleService.findDatas(role,page);
		}else{
			 roles = roleService.findAll();
		}
		MDC.put("operateContent","角色列表查询"); 
		LOG.info("");
		return "listResult";
	}
	
	public String roleList(){
		roles = roleService.findAll();
		authRoles = new ArrayList<Integer>();
		for (Role temp:roles){
			if(SecurityUtils.getSubject().isPermitted("role>*>"+temp.getRegion()))authRoles.add(temp.getId());
		}
		MDC.put("operateContent","角色列表查询"); 
		LOG.info("");
		return "listResult";
	}
	
	public String config() throws IOException{
		String type = request.getParameter("type");
		Role tempRole = roleService.findById(Integer.valueOf(role.getId()));
		if("load".equals(type)){//首先加载资源列表、其次加载角色对应的资源
			StringBuffer sb = new StringBuffer();
			response.setCharacterEncoding("utf-8");
			try {
				SecurityUtils.getSubject().checkPermission("role>config>"+tempRole.getRegion());
				List<cn.promore.bf.bean.Resource> rp = resourceService.findRoleResources(role.getId(),null);
				resources = resourceService.findAllDatas();
				if(null==resources||0==resources.size()){
					sb.append("{id:0,pId:null,name:'无可配置资源'}");
				}else{
					int index = resources.size();
					for(cn.promore.bf.bean.Resource temp:resources){
						sb.append("{id:"+temp.getId()+",pId:");
						sb.append((null!=temp.getParent())?temp.getParent().getId():"null");
						sb.append(",name:'"+temp.getName()+"',desc:'"+temp.getDesc()+
								  "',open:true,isParent:true,checked:"+(rp.contains(temp)?true:false)+
								  (ResourceService.RESOURCE_TYPE_FUNCTION==temp.getType()?",icon:'js/ztree/zTreeStyle/img/diy/9.png'":"")+"}");
						if(index>1)sb.append(",");
						index--;
					}
				}
				flag = true;
				message = "";
			}catch (AuthorizationException e) {
				flag = false;
				message = "不具备配置角色【"+tempRole.getName()+"】的权限";
			}
			StringBuffer ret = new StringBuffer();
			ret.append("{flag:"+flag+",message:'"+message+"',resources:["+sb.toString()+"]}");
			message = ret.toString();
			return "messageResult";
		}else{
			try {
				SecurityUtils.getSubject().checkPermission("role>config>"+tempRole.getRegion());
				roleService.deleteRoleResources(role.getId());
				String resourceIds = request.getParameter("resIds");
				if(StringUtils.isNotEmpty(resourceIds)){
					String[] ids_str = resourceIds.split(",");
					Integer[] ids = new Integer[ids_str.length]; 
					for(int i = 0;i < ids_str.length;i++){
						ids[i] = Integer.valueOf(ids_str[i]);
					}
					roleService.save(role.getId(), ids);
				}
				flag = true;
				message = "角色权限配置成功！";
				MDC.put("operateContent","角色权限配置,资源ID:"+resourceIds); 
				LOG.info("");
			}catch (AuthorizationException e) {
				flag = false;
				message = "不具备配置角色【"+tempRole.getName()+"】的权限";
			}   catch (Exception e) {
				flag = false;
				message = e.getMessage();
			}
			return "result";
		}
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getDataIds() {
		return dataIds;
	}
	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}
	public List<cn.promore.bf.bean.Resource> getResources() {
		return resources;
	}
	public void setResources(List<cn.promore.bf.bean.Resource> resources) {
		this.resources = resources;
	}

	public List<Integer> getAuthRoles() {
		return authRoles;
	}

	public void setAuthRoles(List<Integer> authRoles) {
		this.authRoles = authRoles;
	}
}
