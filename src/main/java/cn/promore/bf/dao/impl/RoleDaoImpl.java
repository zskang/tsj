package cn.promore.bf.dao.impl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import cn.promore.bf.bean.Role;
import cn.promore.bf.dao.RoleDao;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role,Integer>  implements RoleDao {

	public Integer findAllNo() {
		return ((Long) this.getSessionFactory().getCurrentSession().createQuery("select count(*) from Role").uniqueResult()).intValue();
	}

	public Integer saveRoleResource(Integer roleId, Integer id) {
		String sql = "insert into sys_role_resource values(?,?)";
		return this.getSessionFactory().getCurrentSession().createSQLQuery(sql).setInteger(0,roleId).setInteger(1,id).executeUpdate();
	}

	public void deleteRoleResources(Integer roleId) {
		String sql = "delete from sys_role_resource where role_id = ?";
		this.getSessionFactory().getCurrentSession().createSQLQuery(sql).setInteger(0, roleId).executeUpdate();
	}

	@Override
	public Role findByShortName(String shortName) {
		return (Role) this.getSessionFactory().getCurrentSession().createQuery(" from Role r where r.shortName = :shortName").setString("shortName", shortName).uniqueResult();
	}

	@Override
	public Integer findDataNo(Role role) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Role.class);
		if(null!=role&&StringUtils.isNotEmpty(role.getName()))criteria.add(Restrictions.ilike("name",role.getName(),MatchMode.ANYWHERE));
		return ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findDatas(Role role, Integer startIndex, Integer pageSize) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Role.class);
		if(null!=role&&StringUtils.isNotEmpty(role.getName()))criteria.add(Restrictions.like("name",role.getName(),MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("region"));
		criteria.addOrder(Order.asc("id"));
		return criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
	}
}
