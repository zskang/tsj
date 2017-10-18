package cn.promore.bf.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import cn.promore.bf.unit.AuthShiroUtil;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = -5028732646533110563L;
	private static final Log LOGGER = LogFactory.getLog(ExceptionInterceptor.class);

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		LOGGER.debug("ExceptionInterceptor invoke");
		String result = null;
		try {
			result = invocation.invoke();
		} catch (UnauthorizedException e) {
			Map<String,String> permissionNames  = (Map<String, String>)ServletActionContext.getContext().getApplication().get("permissionNames");
			String forbitPermission = AuthShiroUtil.getPermission(e.getMessage());
			String forbitPermissionName = StringUtils.isNotEmpty(forbitPermission)?permissionNames.get(forbitPermission):"合法";
			
			HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
			
			String requestAccept  = request.getHeader("Accept");
			LOGGER.debug("ExceptionInterceptor request Accept:"+requestAccept);
			if(StringUtils.isNotEmpty(requestAccept)&&requestAccept.indexOf("application/json")!=-1){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("flag",false);
				jsonObject.put("message","您不具备【"+forbitPermissionName+"】权限！本次异步资源请求被禁止！");
				response.setCharacterEncoding("utf-8");
				response.getWriter().append(jsonObject.toString());
				//return "JsonNoPermission";
				return null;
			}else if(StringUtils.isNotEmpty(requestAccept)&&requestAccept.indexOf("text/html")!=-1){
				response.sendRedirect("noAuth.jsp");
				return null;
				//return "RequestNoPermission";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
