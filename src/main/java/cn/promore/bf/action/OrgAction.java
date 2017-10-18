package cn.promore.bf.action;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.bean.Organization;
import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.User;
import cn.promore.bf.serivce.EntitySequenceService;
import cn.promore.bf.serivce.OrgService;
import cn.promore.bf.unit.EntityCodeHelper;

@Controller
@Action(value="orgAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="tojson",type="json",params={"root","message","excludeNullProperties","true"}),
		 @Result(name="orgJson",type="json",params={"excludeProperties","orgService","excludeNullProperties","true"}),
		 @Result(name="addResult",type="json",params={"includeProperties","flag,message"}),
		 @Result(name="userAuthOrg",type="json",params={"includeProperties","organizations\\[\\d+\\]\\.(id|name|desc|level),organizations\\[\\d+\\]\\.parent\\.(id|name|level)"})
		 })
public class OrgAction extends BaseAction{
	public static Logger LOG = LoggerFactory.getLogger(OrgAction.class);
	private static final long serialVersionUID = -8613055080615406396L;
	
	@Resource 
	OrgService orgService;
	@Resource
	EntitySequenceService entitySequenceService;
	private Organization org;
	private boolean flag;
	private String message;
	private Page page;
	private List<Organization> organizations;
	private Integer orgId;
	private String level;
	
	public OrgAction() {
		MDC.put("operateModuleName","组织管理");
	}
	
	private void refreshResourceCode(Integer pid){
		if(null!=pid&&0!=pid){
			List<Organization> childOrgs = orgService.getChildOrg(pid);
			for(Organization temp:childOrgs){
				String orgSequence = entitySequenceService.findNext(EntityCodeHelper.getOrgPrefix(temp.getLevel()),"ORG");
				String resourceDisplayCode = EntityCodeHelper.generateCode(temp.getLevel(),temp.getParent().getCode(),orgSequence);
				temp.setCode(resourceDisplayCode);
				orgService.saveOrUpdate(temp);
				refreshResourceCode(temp.getId());
			}
		}
	}
	
	public String freshCode(){
		SecurityUtils.getSubject().checkPermission("org:refreshCode");
		flag = false;
		if(null!=orgId&&0!=orgId){
			entitySequenceService.resetEntitySequence("ORG");
			refreshResourceCode(orgId);
			flag = true;
			message="组织机构编码刷新成功。";
		}
		return "addResult";
	}
	
	public String list() throws IOException{
		SecurityUtils.getSubject().checkPermission("org:get");
		User user = (User)session.getAttribute("user");
		response.setCharacterEncoding("utf-8");
		StringBuffer sb = new StringBuffer("[");
		if(null==orgId||0==orgId) {
			org = orgService.getRootOrg();
			if(null==org){
				sb.append("{id:0,pId:null,name:'无组织节点'}");
			}else{
				sb.append("{id:"+org.getId()+",isRoot:true,pId:");
				sb.append((null!=org.getParent())?org.getParent().getId():"null");
				sb.append(",name:'"+org.getName()+"',code:'"+org.getCode()+"',desc:'"+org.getDesc()+"',open:false,isParent:true}");
			}
			sb.append("]");
			response.getWriter().append(sb.toString());
		}else {
			//level 为父节点组织机构层级
			organizations = orgService.getChildOrg(Integer.valueOf(orgId),Integer.valueOf(level),user.getOrg().getCode());
			for(int i = 0;i<organizations.size();i++){
				sb.append("{id:"+organizations.get(i).getId()+",pId:");
				sb.append((null!=organizations.get(i).getParent())?organizations.get(i).getParent().getId():"null");
				sb.append(",name:'"+organizations.get(i).getName()+"',code:'"+organizations.get(i).getCode()+"',desc:'"+organizations.get(i).getDesc()+"',open:false,isParent:");
				sb.append(organizations.get(i).getChild().size()!=0?"true}":"false}");		
				if(i!=organizations.size()-1)sb.append(",");
			}
			sb.append("]");
			MDC.put("operateContent","组织机构树查询"); 
			LOG.info("");	
			response.getWriter().append(sb.toString());
		}
		return null;
	}
	
	public String add(){
		SecurityUtils.getSubject().checkPermission("org:add");
		try {
			if(null!=org){
				String orgSequence = entitySequenceService.findNext(EntityCodeHelper.getOrgPrefix(org.getLevel()),"ORG");
				String newOrgCode = EntityCodeHelper.generateCode(org.getLevel(),org.getParent().getCode(),orgSequence);
				org.setCode(newOrgCode);
				orgService.save(org);
				flag = true;
			}else{
				flag = false;
			}
			MDC.put("operateContent",org.toString()); 
			LOG.info("");	
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "addResult";
	}
	
	public String delete(){
		SecurityUtils.getSubject().checkPermission("org:delete");
		try {
			orgService.deleteById(orgId);
			flag = true;
			MDC.put("operateContent","删除组织机构，ID："+orgId); 
			LOG.info("");	
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "addResult";
	}
	
	public String update(){
		SecurityUtils.getSubject().checkPermission("org:update");
		try {
			if(null!=org&&null!=org.getParent()&&null==org.getParent().getId()){
				org.setParent(null);
			}
			orgService.saveOrUpdate(org);
			flag = true;
			MDC.put("operateContent",org.toString()); 
			LOG.info("");	
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "addResult";
	}	
	
//	public String userAuthOrg() throws IOException{
//		User user = (User)request.getSession().getAttribute("user");
//		response.setCharacterEncoding("utf-8");
//		StringBuffer sb = new StringBuffer("[");
//		//如果是初始化首次加载组织机构数
//		if(null==orgId){
//			//如果用户是社区办用户
//			if(null!=user&&user.getOrg().getLevel()==0){
//				organizations = orgService.getChildOrg(orgService.getRootOrg().getId());
//			}else if(null!=user&&user.getOrg().getLevel() == 3){
//				organizations = new ArrayList<Organization>();
//				organizations.add(orgService.findById(user.getOrg().getId()));
//			//如果是村居用户	
//			}else{
//				organizations = orgService.getChildOrg(user.getOrg().getId());
//			}
//		}else{
//			organizations = orgService.getChildOrg(orgId);
//		}
//		for(int i = 0;i<organizations.size();i++){
//			sb.append("{id:"+organizations.get(i).getId()+",pId:");
//			sb.append((null!=organizations.get(i).getParent())?organizations.get(i).getParent().getId():"null");
//			sb.append(",name:'"+organizations.get(i).getName()+"',desc:'"+organizations.get(i).getDesc()+"',open:false,isParent:");
//			sb.append(organizations.get(i).getChild().size()!=0?"true}":"false}");		
//			if(i!=organizations.size()-1)sb.append(",");
//		}
//		sb.append("]");
//		response.getWriter().append(sb.toString());
//		MDC.put("operateContent","用户授权组织机构查询"); 
//		LOG.info("");	
//		return null;
//	}
	
//	public String authOrg(){
//		Organization levelOne = new Organization();//所属村居
//		Organization levelTwo = new Organization();//所属网格
//		Organization levelThree = new Organization();//所属村民组
//		organizations = new ArrayList<Organization>();
//		User user = (User)request.getSession().getAttribute("user");
//		if(null!=user&&user.getOrg().getLevel()==3){
//			levelThree = orgService.findById(user.getOrg().getId());
//			levelTwo = orgService.findById(levelThree.getId()).getParent();
//			levelOne = orgService.findById(levelTwo.getId()).getParent();
//		}else if(null!=user&&user.getOrg().getLevel()==2){
//			//levelTwo = user.getOrg();
//			levelTwo = orgService.findById(user.getOrg().getId());
//			levelOne = orgService.findById(levelTwo.getId());
//			//levelOne = user.getOrg().getParent();
//		}else if(null!=user&&user.getOrg().getLevel()==1){
//			//levelOne = user.getOrg();
//			levelOne = orgService.findById(user.getOrg().getId());
//		}
//		
//		//orgId level;
//		if("country".equals(level)){
//			if(null!=levelOne.getId()){
//				
//				organizations.add(levelOne);
//			}else {
//				organizations = orgService.getChildOrg(orgService.getRootOrg().getId());
//			}
//		}else if("grid".equals(level)){
//			if(null!=levelTwo.getId()){
//				organizations.add(levelTwo);
//			}else{
//				organizations = null!=orgId?orgService.getChildOrg(orgId):null;
//			}
//		}else if("group".equals(level)){
//			if(null!=levelThree.getId()){
//				organizations.add(levelThree);
//			}else {
//				organizations = null!=orgId?orgService.getChildOrg(orgId):null;
//			}
//		}
//		MDC.put("operateContent","用户授权组织机构查询"); 
//		LOG.info("");	
//		return "userAuthOrg";
//	}	
	
	public String loadCountry(){
		User user = (User)request.getSession().getAttribute("user");
		organizations = orgService.getCountry(orgService.getRootOrg().getId(),user.getOrg().getId());
		return "userAuthOrg";
	}
	public String loadGroup(){
		organizations = orgService.getGroup(orgId);
		return "userAuthOrg";
	}
	
	
	public String allOrgs(){
		organizations = orgService.getRootOrg().getChild();
		return "orgJson";
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
	public Organization getOrg() {
		return org;
	}
	public void setOrg(Organization org) {
		this.org = org;
	}
	public List<Organization> getOrganizations() {
		return organizations;
	}
	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}
