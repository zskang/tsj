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

import cn.promore.bf.bean.Notify;
import cn.promore.bf.dao.NotifyDao;

@Repository
public class NotifyDaoImpl extends BaseDaoImpl<Notify,Integer>  implements NotifyDao {

	public Integer findDataNo(Notify notify,Date startDate,Date endDate) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Notify.class);
		if(null!=notify&&null!=notify.getIsReaded())criteria.add(Restrictions.eq("isReaded", notify.getIsReaded()));
		if(null!=notify&&StringUtils.isNotEmpty(notify.getTitle()))criteria.add(Restrictions.like("title",notify.getTitle(),MatchMode.ANYWHERE));
		if(null!=notify&&null!=notify.getReceiver()&&null!=notify.getReceiver().getId())criteria.add(Restrictions.eq("receiver.id",notify.getReceiver().getId()));
		if(null!=startDate)criteria.add(Restrictions.ge("createTime",startDate));
		if(null!=endDate)criteria.add(Restrictions.le("createTime",endDate));
		return ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();//addOrder(Order.desc("createDate")).
	}

	@SuppressWarnings("unchecked")
	public List<Notify> findDatas(Notify notify,Date startDate,Date endDate, Integer startIndex,Integer pageSize) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Notify.class);
		if(null!=notify&&null!=notify.getIsReaded())criteria.add(Restrictions.eq("isReaded", notify.getIsReaded()));
		if(null!=notify&&StringUtils.isNotEmpty(notify.getTitle()))criteria.add(Restrictions.like("title",notify.getTitle(),MatchMode.ANYWHERE));
		if(null!=notify&&null!=notify.getReceiver()&&null!=notify.getReceiver().getId())criteria.add(Restrictions.eq("receiver.id",notify.getReceiver().getId()));
		if(null!=startDate)criteria.add(Restrictions.ge("createTime",startDate));
		if(null!=endDate)criteria.add(Restrictions.le("createTime",endDate));
		criteria.addOrder(Order.desc("createTime"));
		return criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notify> findUnReadedNotify(Integer recieverId) {
		return this.getSessionFactory().getCurrentSession().createQuery("from Notify where receiver.id = :recieverId and isReaded = false order by createTime desc").setInteger("recieverId", recieverId).list();
	}

	
}
