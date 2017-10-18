package cn.promore.bf.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.promore.bf.bean.Organization;
import cn.promore.bf.dao.OrganizationDao;

@Repository
public class OrganizationDaoImpl extends BaseDaoImpl<Organization,Integer>  implements OrganizationDao {

	public Organization getRootOrg() {
		return (Organization) this.getSessionFactory().getCurrentSession().createQuery("from Organization z where z.parent = null").uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Organization> getChildOrg(Integer pId,String orgCode) {
		StringBuffer sql = new StringBuffer("from Organization z where z.parent.id = :pId ");
		if(StringUtils.isNotEmpty(orgCode)){
			sql.append(" and z.code = '"+orgCode+"' ");
		}
		return this.getSessionFactory().getCurrentSession().createQuery(sql.toString()).setInteger("pId",pId).list();
	}

	@SuppressWarnings("unchecked")
	public List<Organization> getCountry(Integer pId,Integer userOrgId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Organization.class);
		criteria.add(Restrictions.eq("level",1));
		if(null!=pId&&0!=pId)criteria.add(Restrictions.eq("parent.id",pId));
		if(null!=userOrgId&&pId != userOrgId)criteria.add(Restrictions.eq("id",userOrgId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Organization> getGroup(Integer id) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Organization.class);
		criteria.add(Restrictions.eq("level",2));
		if(null!=id&&0!=id)criteria.add(Restrictions.eq("parent.id",id));
		return criteria.list();		
	}

}
