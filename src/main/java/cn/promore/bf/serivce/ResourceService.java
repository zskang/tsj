package cn.promore.bf.serivce;

import java.util.List;

import cn.promore.bf.bean.Resource;
import cn.promore.bf.bean.Role;

public interface ResourceService extends BaseService<Resource,Integer>{
	
	static final Integer RESOURCE_TYPE_MENU = 1;
	static final Integer RESOURCE_TYPE_FUNCTION = 2;
	
	public Resource getRoot();
	public List<Resource> getChild(Integer pId);
	public List<Resource> getChild(Integer pId,Integer type);
	public List<Resource> findRoleResources(Integer roleIds,Integer resType);
	public List<Resource> findRoleResources(List<Role> roles,Integer pId,Integer resType);
	public List<Resource> findPermissionNames();
	public List<Resource> findChildByPid(Integer pid);
	public List<Resource> findAllDatas();
}
