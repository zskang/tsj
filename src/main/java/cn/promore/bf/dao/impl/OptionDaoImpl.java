package cn.promore.bf.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.promore.bf.bean.Option;
import cn.promore.bf.dao.OptionDao;

@Repository
public class OptionDaoImpl extends BaseDaoImpl<Option,Integer>  implements OptionDao {

	@SuppressWarnings("unchecked")
	public List<Option> findByCategory(String category) {
		return StringUtils.isNotEmpty(category)?this.getSessionFactory().getCurrentSession().createQuery("from Option p where p.category = :category order by category,order").setString("category",category).list():null;
	}

	public Integer findDataNo(Option option,Date startDate,Date endDate) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Option.class);
		if(null!=option&&StringUtils.isNotEmpty(option.getCategory()))criteria.add(Restrictions.like("category", option.getCategory(),MatchMode.ANYWHERE));
		if(null!=option&&StringUtils.isNotEmpty(option.getCategoryname()))criteria.add(Restrictions.like("categoryname", option.getCategoryname(),MatchMode.ANYWHERE));
		if(null!=option&&StringUtils.isNotEmpty(option.getName()))criteria.add(Restrictions.like("name", option.getName(),MatchMode.ANYWHERE));
		return ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Option> findDatas(Option option, Integer startIndex,Integer pageSize,Date startDate,Date endDate) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Option.class);
		if(null!=option&&StringUtils.isNotEmpty(option.getCategory()))criteria.add(Restrictions.like("category", option.getCategory(),MatchMode.ANYWHERE));
		if(null!=option&&StringUtils.isNotEmpty(option.getCategoryname()))criteria.add(Restrictions.like("categoryname", option.getCategoryname(),MatchMode.ANYWHERE));
		if(null!=option&&StringUtils.isNotEmpty(option.getName()))criteria.add(Restrictions.like("name", option.getName(),MatchMode.ANYWHERE));
		return criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
	}
	
}
