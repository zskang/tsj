package cn.promore.bf.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.promore.bf.bean.Project;
import cn.promore.bf.dao.ProjectDao;

@Repository
public class ProjectDaoImpl extends BaseDaoImpl<Project,Integer>  implements ProjectDao {

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Project> findByDocumenter(Integer id) {
//		return this.getSessionFactory().getCurrentSession().createQuery("select p from Project p left join p.projectEngineer e where e.id = :id").setInteger("id",id).list();
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Project> findByTechnician(Integer id) {
//		return this.getSessionFactory().getCurrentSession().createQuery("select p from Project p left join p.technician e where e.id = :id").setInteger("id",id).list();
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Project> findByTechofficor(Integer id) {
//		return this.getSessionFactory().getCurrentSession().createQuery("select p from Project p left join p.techofficor e where e.id = :id").setInteger("id",id).list();
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Project> findByWorkMinister(Integer id) {
//		return this.getSessionFactory().getCurrentSession().createQuery("select p from Project p left join p.workMinister e where e.id = :id").setInteger("id",id).list();
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Project> findByProjectEngineer(Integer id) {
//		return this.getSessionFactory().getCurrentSession().createQuery("select p from Project p left join p.projectEngineer e where e.id = :id").setInteger("id",id).list();
//	}

	@Override
	public Integer findDataNo(Project project, Date startDate, Date endDate) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Project.class);
		if(null!=startDate)criteria.add(Restrictions.ge("finishDate",startDate));
		if(null!=endDate)criteria.add(Restrictions.le("finishDate", endDate));
		if(null!=project&&StringUtils.isNotEmpty(project.getName()))criteria.add(Restrictions.like("name",project.getName(),MatchMode.ANYWHERE));
		if(null!=project&&null!=project.getType()&&null!=project.getType().getId())criteria.add(Restrictions.eq("type.id",project.getType().getId()));
		if(null!=project&&null!=project.getMaster()&&null!=project.getMaster().getId())criteria.add(Restrictions.eq("master.id",project.getMaster().getId()));
		return ((Long)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> findDatas(Project project, Integer startIndex, Integer pageSize, Date startDate, Date endDate) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Project.class,"p");
		if(null!=startDate)criteria.add(Restrictions.ge("p.finishDate",startDate));
		if(null!=endDate)criteria.add(Restrictions.le("p.finishDate", endDate));
		if(null!=project&&StringUtils.isNotEmpty(project.getName()))criteria.add(Restrictions.like("name",project.getName(),MatchMode.ANYWHERE));
		if(null!=project&&null!=project.getType()&&null!=project.getType().getId())criteria.add(Restrictions.eq("p.type.id",project.getType().getId()));
		if(null!=project&&null!=project.getMaster()&&null!=project.getMaster().getId())criteria.add(Restrictions.eq("p.master.id",project.getMaster().getId()));
		return criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
	}

	@Override
	public Project findByName(String name) {
		return (Project) this.getSessionFactory().getCurrentSession().createQuery(" from Project p where p.name = :name").setString("name",name).uniqueResult();
	}
}
