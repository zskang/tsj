package cn.promore.bf.dao.impl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import cn.promore.bf.bean.User;
import cn.promore.bf.dao.UserDao;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User,Integer>  implements UserDao {
	
	public User findByUsername(String username) {
		return (User) sessionFactory.getCurrentSession().createQuery("from User u where u.username = :username").setString("username", username).setCacheable(true).uniqueResult();
	}

	public Integer findUserNo(User user) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(User.class).createAlias("org","o");
		criteria.setProjection(Projections.rowCount());
		if(null!=user&&null!=user.getOrg()&&null!=user.getOrg().getId())criteria.add(Restrictions.eq("o.id",user.getOrg().getId()));
		if(null!=user&&null!=user.getOrg()&&StringUtils.isNotEmpty(user.getOrg().getCode())&&!"-1".equals(user.getOrg().getCode()))criteria.add(Restrictions.like("o.code",user.getOrg().getCode(),MatchMode.START));
		if(null!=user&&StringUtils.isNotEmpty(user.getUsername()))criteria.add(Restrictions.like("username", user.getUsername(),MatchMode.ANYWHERE));
		if(null!=user&&StringUtils.isNotEmpty(user.getChinesename()))criteria.add(Restrictions.like("chinesename", user.getChinesename(),MatchMode.ANYWHERE));
		if(null!=user&&StringUtils.isNotEmpty(user.getMobilephone()))criteria.add(Restrictions.eq("mobilephone", user.getMobilephone()));
		return Integer.valueOf(criteria.uniqueResult().toString());
	}

	@SuppressWarnings("unchecked")
	public List<User> findUsers(User user, Integer startIndex, Integer pageSize) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(User.class).createAlias("org","o");
		if(null!=user&&null!=user.getOrg()&&null!=user.getOrg().getId())criteria.add(Restrictions.eq("o.id",user.getOrg().getId()));
		if(null!=user&&null!=user.getOrg()&&StringUtils.isNotEmpty(user.getOrg().getCode())&&!"-1".equals(user.getOrg().getCode()))criteria.add(Restrictions.like("o.code",user.getOrg().getCode(),MatchMode.START));
		if(null!=user&&StringUtils.isNotEmpty(user.getUsername()))criteria.add(Restrictions.like("username", user.getUsername(),MatchMode.ANYWHERE));
		if(null!=user&&StringUtils.isNotEmpty(user.getChinesename()))criteria.add(Restrictions.like("chinesename", user.getChinesename(),MatchMode.ANYWHERE));
		if(null!=user&&StringUtils.isNotEmpty(user.getMobilephone()))criteria.add(Restrictions.eq("mobilephone", user.getMobilephone()));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
	}

	public Long getDataNo(String entityName){
		Object result =  this.getSessionFactory().getCurrentSession().createQuery("select count(*) as count from "+entityName+" d").uniqueResult();
		return (Long) result;
	}

	@Override
	public String findPasswordById(Integer id) {
		return (String)this.getSessionFactory().getCurrentSession().createQuery("select password from User where id = :id").setInteger("id",id).uniqueResult();
	}
	
	@Override
	public String findLxfsById(Integer id) {
		return (String)this.getSessionFactory().getCurrentSession().createQuery("select mobilephone from User where id = :id").setInteger("id",id).uniqueResult();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<User> findDatasByChinesename(String chinesename,String roleShortName) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(User.class);
		criteria.createAlias("roles","r",JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("status",1));
		if(StringUtils.isNotEmpty(roleShortName))criteria.add(Restrictions.eq("r.shortName", roleShortName));
		if(StringUtils.isNotEmpty(chinesename))criteria.add(Restrictions.like("chinesename",chinesename,MatchMode.ANYWHERE));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		return criteria.list();
	}
	
}
