package cn.promore.bf.shiro.extend;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.Permission;

/**
 *@author huzd@si-tech.com.cn or ahhzd@vip.qq
 *@version createrTime:Apr 24, 2017 4:55:19 PM
 **/

public class RoleWildCardPermission implements Permission,Serializable{
	
		private static final long serialVersionUID = -2072787372937622113L;
		private static final Log loger = LogFactory.getLog(RoleWildCardPermission.class);
		
		protected static final boolean DEFAULT_CASE_SENSITIVE = false;
		public static final String  ROLE_EXTEND_WILDCARD_PATTERN = "([A-Za-z0-9]*|\\*)>([A-Za-z0-9]*|\\*)>(\\d+|\\*)";

	    private String resourceName;
	    private String operate;
	    private String authType;
	    
	    public RoleWildCardPermission(){super();}
	    
	    public RoleWildCardPermission(String permissionString){
	    	this(permissionString,DEFAULT_CASE_SENSITIVE);
	    }
	    public RoleWildCardPermission(String permissionString,boolean caseSensitive) {
	    	setParts(permissionString, caseSensitive);
	    }
	    private void setParts(String permissionString,boolean caseSensitive){
	    	if(caseSensitive)permissionString.toLowerCase();
	    	Object[] array = new Object[3];
			Pattern pattern = Pattern.compile(RoleWildCardPermission.ROLE_EXTEND_WILDCARD_PATTERN);
			Matcher matcher = pattern.matcher(permissionString);
			if(matcher.matches()&&matcher.groupCount()>0){
				for (int i = 0; i < matcher.groupCount(); i++) {
					array[i] = matcher.group(i+1);
				}
				resourceName = (String)array[0];
				operate = (String)array[1];
				authType =(String)array[2];
			}
	    }

	    @Override
	    public boolean implies(Permission p) {
	        if(!(p instanceof RoleWildCardPermission))return false;
	        RoleWildCardPermission other = (RoleWildCardPermission) p;
	        boolean isImplies = true;
	        if(!("*".equals(this.resourceName)||this.resourceName.equals(other.resourceName)))isImplies=false;
	        if(!("*".equals(this.operate)||this.operate.equals(other.operate)))isImplies=false;
	        if(!("*".equals(this.authType)||(Integer.valueOf("*".equals(this.authType)?"0":this.authType) < Integer.valueOf("*".equals(other.authType)?"0":other.authType))))isImplies=false;
	        loger.debug("------>校验用户权限结果："+isImplies);
	       // if("20".equals(this.authType)&&this.authType.equals(other.authType))isImplies=true;
	        return isImplies;
	    }
}
