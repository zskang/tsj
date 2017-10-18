package cn.promore.bf.shiro.extend;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.promore.bf.bean.Resource;
import cn.promore.bf.bean.Role;
import cn.promore.bf.bean.User;
import cn.promore.bf.serivce.ResourceService;
import cn.promore.bf.serivce.RoleService;
import cn.promore.bf.serivce.UserService;
import cn.promore.bf.unit.RoleUtils;

public class MyRealm extends AuthorizingRealm {
	
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	ResourceService resourceService;
	
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		String username = (String) super.getAvailablePrincipal(principals);
		User user = userService.findByUsername(username);
		if(null==user)return null;
		List<Resource> resources =  resourceService.findRoleResources(user.getRoles(),null,ResourceService.RESOURCE_TYPE_FUNCTION);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if(null!=user.getRoles()&&user.getRoles().size()>0){
			for(Role r:user.getRoles()){
				info.addRole(r.getName());
			}
			String permission = "role>*>"+RoleUtils.getMaxRegion(user.getRoles());
			//System.out.println("----------->"+permission);
			info.addObjectPermission(new RoleWildCardPermission(permission));
		}
		if(null!=resources&&resources.size()>0){
			for(Resource resource :resources){
				info.addStringPermission(resource.getPath());
			}
		}
		return info;
	}
	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upt = (UsernamePasswordToken)token;
		String username = upt.getUsername();
		User user = userService.findByUsername(username);
		if(null==user){
			throw new UnknownAccountException();
		}else if(user.getStatus() == 0){
			throw new LockedAccountException();
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),getName());
		return authenticationInfo;
	}
	
	
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public ResourceService getResourceService() {
		return resourceService;
	}
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
}
