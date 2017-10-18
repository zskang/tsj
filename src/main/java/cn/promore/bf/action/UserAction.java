package cn.promore.bf.action;

/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.SiteInfo;
import cn.promore.bf.bean.User;
import cn.promore.bf.serivce.ResourceService;
import cn.promore.bf.serivce.UserService;
import cn.promore.bf.unit.RoleUtils;

@Controller
@Action(value = "userAction")
// execute,input,back,cancel,browse,save,delete,list,index
// @AllowedMethods(value={"login","logout","update","get","add"})
@ParentPackage("huzdDefault")
@Results({ @Result(name = "index", location = "/index.jsp", type = "redirect"),
		@Result(name = "login", location = "/login.jsp", type = "redirect"),
		@Result(name = "optionList", type = "json", params = { "includeProperties",
				"users\\[\\d+\\]\\.(id|username|chinesename|telephone|mobilephone|status|email)" }),
		@Result(name = "objectResult", type = "json", params = { "includeProperties",
				"flag,message,user\\.(id|username|chinesename|telephone|mobilephone|status|password|email),user\\.roles\\[\\d+\\]\\.(id|name),user\\.org\\.(id|name)" }),
		@Result(name = "listResult", type = "json", params = { "includeProperties",
				"users\\[\\d+\\]\\.(id|username|chinesename|telephone|mobilephone|status|email),users\\[\\d+\\]\\.roles\\[\\d+\\]\\.(name),users\\[\\d+\\]\\.org\\.(id|name),page\\.(pageSize|totalRecords|totalPages|currentPage),flag,message" }),
		@Result(name = "result", type = "json", params = { "includeProperties", "flag,message" }),
		@Result(name = "data", type = "json", params = { "root", "data" }),
		@Result(name = "error", location = "/error.html") })
public class UserAction extends BaseAction {
	public static Logger LOG = LoggerFactory.getLogger(UserAction.class);
	private static final long serialVersionUID = -8613055080615406396L;

	@Resource
	UserService userService;
	@Resource
	ResourceService resourceService;

	private Page page;
	private List<User> users;
	private User user;
	private boolean flag = true;
	private String message;
	private String data;
	private String dataIds;

	private String zrrenid;
	private String tel;

	public UserAction() {
		MDC.put("operateModuleName", "用户管理");
	}

	public String login() {
		Subject current = SecurityUtils.getSubject();
		String remember = request.getParameter("remember");
		SiteInfo siteinfo = (SiteInfo) ServletActionContext.getServletContext().getAttribute("siteinfo");
		if (null != siteinfo && siteinfo.getShowValidateCode()) {
			String validateCode = request.getParameter("validateCode");
			String sessionVC = (String) current.getSession().getAttribute("validate_code");
			if (null == sessionVC || !sessionVC.equalsIgnoreCase(validateCode)) {
				session.setAttribute("loginError", "验证码错误");
				return "login";
			}
		}
		if (null != current && !current.isAuthenticated()) {
			UsernamePasswordToken upt = new UsernamePasswordToken(user.getUsername(), user.getPassword());
			upt.setRememberMe(("on".equalsIgnoreCase(remember) ? true : false));
			try {
				current.login(upt);
				User user = userService.findByUsername(upt.getUsername());
				cn.promore.bf.bean.Resource rootResource = resourceService.getRoot();
				// System.out.println(user.getRoles().size());
				List<cn.promore.bf.bean.Resource> resources = resourceService.findRoleResources(user.getRoles(),
						rootResource.getId(), ResourceService.RESOURCE_TYPE_MENU);
				SecurityUtils.getSubject().getSession().setAttribute("user", user);
				SecurityUtils.getSubject().getSession().setAttribute("resources", resources);
				SecurityUtils.getSubject().getSession().setAttribute("maxRoleRegion",
						RoleUtils.getMaxRegion(user.getRoles()));
			} catch (UnknownAccountException e) {
				session.setAttribute("loginError", "用户名不存在");
				return "login";
			} catch (IncorrectCredentialsException e) {
				session.setAttribute("loginError", "用户名或者密码错误");
				return "login";
			} catch (ExcessiveAttemptsException e) {
				session.setAttribute("loginError", "密码错误3次，锁定10分钟");
				return "login";
			} catch (LockedAccountException e) {
				session.setAttribute("loginError", "账户已锁定");
				return "login";
			}
			MDC.put("operateContent", "用户登录，用户名：" + user.getUsername());
			LOG.info("");
			return "index";
		}
		return "index";
	}

	public String queryLxfsByUserId() {
		try {
			String mobilePhone = this.userService.findLxfsById(Integer.parseInt(zrrenid));
			if (StringUtils.isNoneBlank(mobilePhone)) {
				flag = true;
				message = mobilePhone;
			}
		} catch (Exception e) {
			flag = false;
			message = "没有号码";
			e.printStackTrace();
		}
		return "result";
	}

	public String logout() {
		Subject current = SecurityUtils.getSubject();
		current.logout();
		MDC.put("operateContent", "用户退出系统");
		LOG.info("");
		return "login";
	}

	public String add() {
		SecurityUtils.getSubject().checkPermission("user:add");
		try {
			String password = user.getPassword();
			user.setPassword(new Md5Hash(password).toString());
			userService.save(user);
			flag = true;
			MDC.put("operateContent", "用户添加：" + user.toString());
			LOG.info("");
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "result";
	}

	public String optionList() {
		users = userService.findDatasByChinesename(user.getChinesename(), dataIds);
		return "optionList";
	}

	public String list() {
		SecurityUtils.getSubject().checkPermission("user:get");
		page.setTotalRecords(userService.findUserNo(user));
		users = userService.findUsers(page, user);
		MDC.put("operateContent", "用户信息列表查询");
		LOG.info("");
		return "listResult";
	}

	public String delete() {
		SecurityUtils.getSubject().checkPermission("user:delete");
		if (StringUtils.isNotEmpty(dataIds)) {
			String[] dUserIds = dataIds.split(",");
			flag = true;
			for (String temp : dUserIds) {
				try {
					userService.deleteById(Integer.valueOf(temp));
					MDC.put("operateContent", "用户信息删除，编码：" + temp);
					LOG.info("");
				} catch (Exception e) {
					flag = false;
					message = e.getMessage();
				}
			}
		}
		return "result";
	}

	public String get() {
		SecurityUtils.getSubject().checkPermission("user:get");
		try {
			user = userService.findById(user.getId());
			flag = true;
			MDC.put("operateContent", "用户信息查询，编码：" + user.getId());
			LOG.info("");
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "objectResult";
	}

	public String update() {
		SecurityUtils.getSubject().checkPermission("user:update");
		try {
			String oldPsw = userService.findPasswordById(user.getId());
			if (!oldPsw.equals(user.getPassword()))
				user.setPassword(new Md5Hash(user.getPassword()).toString());
			userService.update(user);
			flag = true;
			MDC.put("operateContent", "更新用户：" + user.toString());
			LOG.info("");
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "result";
	}

	public String findByRole() {
		if (StringUtils.isNotEmpty(dataIds)) {
			users = userService.findUserByRoleShortName(dataIds);
		}
		return "listResult";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
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

	public String getZrrenid() {
		return zrrenid;
	}

	public void setZrrenid(String zrrenid) {
		this.zrrenid = zrrenid;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
