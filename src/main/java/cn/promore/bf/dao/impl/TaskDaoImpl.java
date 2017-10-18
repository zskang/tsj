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

import cn.promore.bf.bean.Task;
import cn.promore.bf.dao.TaskDao;

@Repository
public class TaskDaoImpl extends BaseDaoImpl<Task,Integer>  implements TaskDao {

	public Integer findDataNo(Task task,Date startDate,Date endDate) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Task.class);
		if(null!=task&&StringUtils.isNotEmpty(task.getName()))criteria.add(Restrictions.like("name",task.getName(),MatchMode.ANYWHERE));
		if(null!=task&&null!=task.getAuthor()&&null!=task.getAuthor().getId())criteria.add(Restrictions.eq("author.id",task.getAuthor().getId()));
		if(null!=task&&StringUtils.isNotEmpty(task.getStatus()))criteria.add(Restrictions.eq("status",task.getStatus()));
		if(null!=startDate)criteria.add(Restrictions.ge("createTime",startDate));
		if(null!=endDate)criteria.add(Restrictions.le("createTime",endDate));
		return ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();//addOrder(Order.desc("createDate")).
	}

	@SuppressWarnings("unchecked")
	public List<Task> findDatas(Task task,Date startDate,Date endDate, Integer startIndex,Integer pageSize) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Task.class);
		if(null!=task&&StringUtils.isNotEmpty(task.getName()))criteria.add(Restrictions.like("name",task.getName(),MatchMode.ANYWHERE));
		if(null!=task&&null!=task.getAuthor()&&null!=task.getAuthor().getId())criteria.add(Restrictions.eq("author.id",task.getAuthor().getId()));
		if(null!=task&&StringUtils.isNotEmpty(task.getStatus()))criteria.add(Restrictions.eq("status",task.getStatus()));
		if(null!=startDate)criteria.add(Restrictions.ge("createTime",startDate));
		if(null!=endDate)criteria.add(Restrictions.le("createTime",endDate));
		if(null!=task&&StringUtils.isNotEmpty(task.getName()))criteria.add(Restrictions.like("name",task.getName(),MatchMode.ANYWHERE));
		criteria.addOrder(Order.desc("id"));
		criteria.setCacheable(true);
		return criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Task> findOneUndealLeastTask(String status) {
		return this.getSessionFactory().getCurrentSession().createQuery(" from Task t where t.status = :status order by createTime desc ").setString("status",status).list();
	}
	
}
