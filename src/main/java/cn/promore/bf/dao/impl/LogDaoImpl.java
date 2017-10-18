package cn.promore.bf.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.promore.bf.bean.Log;
import cn.promore.bf.dao.LogDao;

@Repository
public class LogDaoImpl extends BaseDaoImpl<Log,Integer>  implements LogDao {
	public Integer findDataNo(Log log,Date startDate,Date endDate) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Log.class);
		if(null!=startDate)criteria.add(Restrictions.ge("operateTime",startDate));
		if(null!=endDate)criteria.add(Restrictions.le("operateTime",endDate));
		if(null!=log&&StringUtils.isNotEmpty(log.getUsername()))criteria.add(Restrictions.like("username", log.getUsername(),MatchMode.ANYWHERE));
		return ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Log> findDatas(Log log, Integer startIndex,Integer pageSize,Date startDate,Date endDate) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Log.class);
		if(null!=startDate)criteria.add(Restrictions.ge("operateTime",startDate));
		if(null!=endDate)criteria.add(Restrictions.le("operateTime",endDate));
		if(null!=log&&StringUtils.isNotEmpty(log.getUsername()))criteria.add(Restrictions.like("username", log.getUsername(),MatchMode.ANYWHERE));
		criteria.addOrder(Order.desc("operateTime"));
		return criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
	}
}
