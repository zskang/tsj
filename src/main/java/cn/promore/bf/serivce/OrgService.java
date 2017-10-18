package cn.promore.bf.serivce;

import java.util.List;

import cn.promore.bf.bean.Organization;

public interface OrgService extends BaseService<Organization,Integer>{
	public Organization getRootOrg();
	public List<Organization> getChildOrg(Integer pid);
	public List<Organization> getChildOrg(Integer pId,Integer level,String userOrgCode);
	public List<Organization> getCountry(Integer pId,Integer userOrgId);
	public List<Organization> getGroup(Integer id);
}
