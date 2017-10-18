package cn.promore.bf.serivce;

import java.util.List;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.Role;

public interface RoleService extends BaseService<Role, Integer>{
    public Integer findAllNo();
    public List<Role> findByProperty(String propertyName, Object value,int type);
    public void save(Integer roleId,Integer[] permissionIds);
	public List<Role> findRoles(Page page);
	public void deleteRoleResources(Integer roleId);
	public Integer findDataNo(Role role);
	public List<Role> findDatas(Role role, Page page);
}
