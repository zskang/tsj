package cn.promore.bf.dao;

import java.util.List;
import cn.promore.bf.bean.Role;


public interface RoleDao extends BaseDao<Role,Integer>{
	Integer findAllNo();
	Integer saveRoleResource(Integer roleId, Integer id);
	void deleteRoleResources(Integer roleId);
	Role findByShortName(String shortName);
	Integer findDataNo(Role role);
	List<Role> findDatas(Role role, Integer startIndex, Integer pageSize);
}
