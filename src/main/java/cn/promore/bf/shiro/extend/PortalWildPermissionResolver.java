package cn.promore.bf.shiro.extend;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.InvalidPermissionStringException;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 *@author huzd@si-tech.com.cn or ahhzd@vip.qq
 *@version createrTime:Apr 24, 2017 5:04:45 PM
 *
 * 自定义Shiro permission 权限处理类
 * 针对角色处理权限扩展一种形如 role>update>11 的权限字符串；
 * 最后一位为权限的处理范围标识；越小权限越大；* 为所有
 **/

public class PortalWildPermissionResolver implements PermissionResolver {
	private static final Log logger = LogFactory.getLog(PortalWildPermissionResolver.class);

	@Override
	public Permission resolvePermission(String permissionString) {
			logger.debug("PortalWildPermissionResolver is invoke for ["+permissionString+"]");
			if(StringUtils.isEmpty(permissionString)) throw new InvalidPermissionStringException("权限通配符不能为空",permissionString);
			Pattern pattern = Pattern.compile(RoleWildCardPermission.ROLE_EXTEND_WILDCARD_PATTERN);
			Matcher matcher = pattern.matcher(permissionString);
			if(matcher.matches()){
				return new RoleWildCardPermission(permissionString);
			}
	        return new WildcardPermission(permissionString);  
	}

}
