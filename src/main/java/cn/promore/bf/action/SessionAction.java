package cn.promore.bf.action;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.MDC;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.bean.Page;

@Controller
@Action(value="sessionAction")
@ParentPackage("huzdDefault")
@Results({
		  @Result(name="objectResult",type="json",params={"includeProperties","flag,message,user\\.(id|username|chinesename|telephone|mobilephone|status|password),user\\.roles\\[\\d+\\]\\.(id|name),user\\.org\\.(id|name)"}),
		  @Result(name="listResult",type="json",params={"includeProperties","users\\[\\d+\\]\\.(id|username|chinesename|telephone|mobilephone|status),users\\[\\d+\\]\\.roles\\[\\d+\\]\\.(name),users\\[\\d+\\]\\.org\\.(id|name),page\\.(pageSize|totalRecords|totalPages|currentPage),flag,message"}),
		  @Result(name="result",type="json",params={"includeProperties","flag,message"}),
		  @Result(name="data",type="json",params={"root","data"}),
	      @Result(name="error",location="/error.html")
		})
public class SessionAction extends BaseAction{
	public static Logger LOG = LoggerFactory.getLogger(SessionAction.class);
	private static final long serialVersionUID = -8613055080615406396L;
	
	@Resource 
	SessionDAO sessionDAO;
	private Page page;
	private boolean flag = true;
	private String message;
	private String data;
	private String dataIds;

	public SessionAction() {
		MDC.put("operateModuleName","在线会话管理");
	}

	public String list(){
		//SecurityUtils.getSubject().checkPermission("session:get");
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		//page.setTotalRecords((null!=sessions&&sessions.size()>0)?sessions.size():0);
		for(Session session:sessions){
			Collection<Object> keys = session.getAttributeKeys();
			for(Object key_:keys){
				System.out.println(key_);
			}
		}
		MDC.put("operateContent","用户会话列表查询"); 
		LOG.info("");	
		return "listResult";
	}	
	
//	public String logout(){
//		Subject current = SecurityUtils.getSubject();
//		current.logout();
//		MDC.put("operateContent","用户退出系统"); 
//		LOG.info("");	
//		return "login";
//	}
//	
//	public String add(){
//		SecurityUtils.getSubject().checkPermission("user:add");
//		try {
//			String password = user.getPassword();
//			user.setPassword(new Md5Hash(password).toString());
//			userService.save(user);
//			flag = true;
//			MDC.put("operateContent","用户添加："+user.toString()); 
//			LOG.info("");	
//		} catch (Exception e) {
//			flag = false;
//			message = e.getMessage();
//		}
//		return "result";
//	}

//	
//	public String delete(){
//		SecurityUtils.getSubject().checkPermission("user:delete");
//		if(StringUtils.isNotEmpty(dataIds)){
//			String[] dUserIds = dataIds.split(",");
//			flag = true;
//			for(String temp:dUserIds){
//				try {
//					userService.deleteById(Integer.valueOf(temp));
//					MDC.put("operateContent","用户信息删除，编码："+temp); 
//					LOG.info("");						
//				} catch (Exception e) {
//					flag = false;
//					message = e.getMessage();
//				}				
//			}
//		}
//		return "result";
//	}
//	
//	public String get(){
//		SecurityUtils.getSubject().checkPermission("user:get");
//		try {
//			user = userService.findById(user.getId());
//			flag = true;
//			MDC.put("operateContent","用户信息查询，编码："+user.getId()); 
//			LOG.info("");	
//		} catch (Exception e) {
//			flag = false;
//			message = e.getMessage();
//		}
//		return "objectResult";
//	}
//	public String update(){
//		SecurityUtils.getSubject().checkPermission("user:update");
//		try {
//			String oldPsw = userService.findPasswordById(user.getId());
//			if(!oldPsw.equals(user.getPassword()))user.setPassword(new Md5Hash(user.getPassword()).toString());
//			userService.update(user);
//			flag = true;
//			MDC.put("operateContent","更新用户："+user.toString()); 
//			LOG.info("");	
//		} catch (Exception e) {
//			flag = false;
//			message = e.getMessage();
//		}
//		return "result";
//	}	
//	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

	public String getDataIds() {
		return dataIds;
	}

	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}
}
