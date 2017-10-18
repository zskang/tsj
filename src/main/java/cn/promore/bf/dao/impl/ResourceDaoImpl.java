package cn.promore.bf.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import cn.promore.bf.bean.Resource;
import cn.promore.bf.dao.ResourceDao;
import cn.promore.bf.serivce.ResourceService;

@Repository
public class ResourceDaoImpl extends BaseDaoImpl<Resource,Integer>  implements ResourceDao {

	public Resource getRoot() {
//		return (Resource) this.getSessionFactory().getCurrentSession().createQuery("from Resource z where z.parent = null").setCacheable(true).uniqueResult();
		return (Resource) this.getSessionFactory().getCurrentSession().createQuery("from Resource z where z.parent = null").uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Resource> getChild(Integer pId) {
		return this.getSessionFactory().getCurrentSession().createQuery("from Resource z where z.parent.id = :pId order by z.order ").setInteger("pId",pId).list();
	}

	@SuppressWarnings("unchecked")
	public List<Resource> getChildByType(Integer pId, Integer type) {
//		return this.getSessionFactory().getCurrentSession().createQuery("from Resource z where z.parent.id = :pId and z.type = :type order by z.order ")
//				.setInteger("pId",pId).setInteger("type",type).setCacheable(true).list();
		return this.getSessionFactory().getCurrentSession().createQuery("from Resource z where z.parent.id = :pId and z.type = :type order by z.order ")
				.setInteger("pId",pId).setInteger("type",type).list();
	}

	@SuppressWarnings("unchecked")
	public List<Resource> findRoleResources(Integer roleId,Integer resType) {
		StringBuffer sql = new StringBuffer(" select distinct p from Role as r inner join r.resources as p where p.status = 'N' and r.id in (:roleId) ");
		if(null!=resType)sql.append(" and p.type = :type ");
		sql.append(" order by p.displayCode asc ");
		Query query = this.getSessionFactory().getCurrentSession().createQuery(sql.toString()).setInteger("roleId",roleId);
//		query.setCacheable(true);
		if(null!=resType)query.setInteger("type",resType);		 
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Resource> findRoleResources(List<Integer> roleIds, Integer pId,Integer resType) {
		StringBuffer sql = new StringBuffer(" select distinct p from Role as r inner join r.resources as p where p.status = 'N' and r.id in (:roleId) ");
		if(null!=resType)sql.append(" and p.type = :type ");
		if(null!=pId&&0!=pId)sql.append(" and p.parent.id = :pId ");
		sql.append(" order by p.order ");
		Query query = this.getSessionFactory().getCurrentSession().createQuery(sql.toString()).setParameterList("roleId",roleIds);
//		query.setCacheable(true);
		if(null!=resType)query.setInteger("type",resType);		
		if(null!=pId&&0!=pId)query.setInteger("pId",pId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> findPermissionNames() {
		return this.getSessionFactory().getCurrentSession().createQuery("from Resource r where r.type = :type").setInteger("type",ResourceService.RESOURCE_TYPE_FUNCTION).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> findChildByPid(Integer pid) {
		return this.getSessionFactory().getCurrentSession().createQuery("from Resource r where r.parent.id = :pid order by r.order asc").setInteger("pid",pid).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resource> findAllDatas() {
		return this.getSessionFactory().getCurrentSession().createQuery("from Resource r order by r.order asc").list();
	}

}
