package cn.promore.bf.dao;

import java.util.List;

import cn.promore.bf.bean.Organization;


public interface OrganizationDao extends BaseDao<Organization,Integer>{
	Organization getRootOrg();
	List<Organization> getChildOrg(Integer pId,String level);
	List<Organization> getCountry(Integer pId,Integer userOrgId);
	List<Organization> getGroup(Integer id);
}
