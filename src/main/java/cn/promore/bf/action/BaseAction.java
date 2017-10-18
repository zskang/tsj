package cn.promore.bf.action;

/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.promore.bf.bean.User;
import cn.promore.bf.unit.NetUtil;

public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	protected HttpServletRequest request = ServletActionContext.getRequest();
	protected HttpServletResponse response = ServletActionContext.getResponse();
	protected HttpSession session = ServletActionContext.getRequest().getSession();
	String OS = System.getProperty("os.name").toLowerCase();

	public BaseAction() {
		User user = (User) request.getSession().getAttribute("user");
		MDC.put("username", null != user ? user.getUsername() : "anonymous");
		MDC.put("clientIp", NetUtil.getIpAddr(request));
		MDC.put("operateResult", "success");
	}


	public boolean isLinux() {
		return OS.indexOf("linux") >= 0;
	}

	public boolean isWindows() {
		return OS.indexOf("windows") >= 0;
	}

	/**
	 * 返回TOMCAT的安装主目录 TOMCAT_HOME/
	 * 
	 * @return
	 */
	public String returnWebServerRootPath() {
		return new File(returnWebServerDeployPath()).getParentFile().getPath() + System.getProperty("file.separator");
	}

	/***
	 * 对于tomcat来说返回的是TOMCAT_HOME/webapps
	 * 
	 * @return 返回Web容器部署应用的文件夹路径
	 */
	public String returnWebServerDeployPath() {
		return new File(ServletActionContext.getServletContext().getRealPath(System.getProperty("file.separator"))).getParent() + System.getProperty("file.separator");
	}

	/**
	 * 对于tomcat来言返回的是TOMCAT_HOME/webapps/upload/
	 * 
	 * @return 返回上传目录路径
	 */
	public String returnUploadBasePath() {
		return returnWebServerDeployPath() + "upload/";
	}

	/**
	 * 例如：TOMCAT_HOME/webapps/ROOT/
	 * 
	 * @return 返回项目当前部署路径
	 */
	public String returnWebAppBasePath() {
		return ServletActionContext.getServletContext().getRealPath(System.getProperty("file.separator"));
	}
}
