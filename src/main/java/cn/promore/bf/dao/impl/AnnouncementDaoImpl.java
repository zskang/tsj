package cn.promore.bf.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.promore.bf.bean.Announcement;
import cn.promore.bf.dao.AnnouncementDao;

@Repository
public class AnnouncementDaoImpl extends BaseDaoImpl<Announcement,Integer>  implements AnnouncementDao {

	public Integer findDataNo(Announcement annou) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Announcement.class);
		if (null!=annou&&null!=annou.getCreateDate())criteria.add(Restrictions.ge("createDate",annou.getCreateDate()));
		if (null!=annou&&null!=annou.getExpireData())criteria.add(Restrictions.le("createDate",annou.getExpireData()));
		if (null!=annou&&StringUtils.isNotEmpty(annou.getTitle()))criteria.add(Restrictions.like("title",annou.getTitle(),MatchMode.ANYWHERE));
		return ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();//addOrder(Order.desc("createDate")).
	}

	@SuppressWarnings("unchecked")
	public List<Announcement> findDatas(Announcement annou, Integer startIndex,Integer pageSize) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Announcement.class);
		if (null!=annou&&null!=annou.getCreateDate())criteria.add(Restrictions.ge("createDate",annou.getCreateDate()));
		if (null!=annou&&null!=annou.getExpireData())criteria.add(Restrictions.le("createDate",annou.getExpireData()));
		if (null!=annou&&StringUtils.isNotEmpty(annou.getTitle()))criteria.add(Restrictions.like("title",annou.getTitle(),MatchMode.ANYWHERE));
		criteria.addOrder(Order.desc("createDate"));
		return criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
	}
	
}
