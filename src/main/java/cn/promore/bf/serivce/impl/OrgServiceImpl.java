package cn.promore.bf.serivce.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.Organization;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.OrganizationDao;
import cn.promore.bf.serivce.OrgService;
import cn.promore.bf.unit.EntityCodeHelper;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OrgServiceImpl extends BaseServiceImpl<Organization,Integer> implements OrgService {
	@Resource
	private OrganizationDao organizationDao;

	
	@Override
	@Resource(type=OrganizationDao.class)
	public void setDao(BaseDao<Organization, Integer> baseDao) {
		super.setDao(baseDao);
	}

	public Organization getRootOrg() {
		return organizationDao.getRootOrg();
	}

	public List<Organization> getChildOrg(Integer pId,Integer level,String userOrgCode) {
		Map<Integer,String> allLevel = EntityCodeHelper.getOrgLevelWithCode(userOrgCode);
		String levelCode  = (null!=allLevel&&allLevel.size()>0)?allLevel.get(level+1):"";
		return organizationDao.getChildOrg(pId,levelCode);
	}

	public List<Organization> getCountry(Integer pId,Integer userOrgId) {
		return organizationDao.getCountry(pId,userOrgId);
	}

	public List<Organization> getGroup(Integer id) {
		return organizationDao.getGroup(id);
	}

	@Override
	public List<Organization> getChildOrg(Integer pid) {
		return organizationDao.getChildOrg(pid,null);
	}
}
