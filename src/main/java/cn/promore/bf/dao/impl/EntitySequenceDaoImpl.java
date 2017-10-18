package cn.promore.bf.dao.impl;

import org.springframework.stereotype.Repository;

import cn.promore.bf.bean.EntitySequence;
import cn.promore.bf.dao.EntitySequenceDao;

@Repository
public class EntitySequenceDaoImpl extends BaseDaoImpl<EntitySequence,Integer>  implements EntitySequenceDao {

	@Override
	public EntitySequence findByName(String name,String type){
		return (EntitySequence) this.getSessionFactory().getCurrentSession().createQuery(" from EntitySequence where name = :name and type = :type").setString("name",name).setString("type",type).uniqueResult();
	}

	@Override
	public void deleteByType(String type) {
		this.getSessionFactory().getCurrentSession().createQuery("delete from EntitySequence where type = :type").setString("type",type).executeUpdate();
	}


}
