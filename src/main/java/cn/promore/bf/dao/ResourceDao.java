package cn.promore.bf.dao;

import java.util.List;

import cn.promore.bf.bean.Resource;


public interface ResourceDao extends BaseDao<Resource,Integer>{
	Resource getRoot();
	List<Resource> getChild(Integer pId);
	List<Resource> getChildByType(Integer pId,Integer type);
	List<Resource> findRoleResources(Integer roleId, Integer resType);
	List<Resource> findRoleResources(List<Integer> roleIds, Integer pId,Integer resType);
	List<Resource> findPermissionNames();
	List<Resource> findChildByPid(Integer pid);
	List<Resource> findAllDatas();
}
