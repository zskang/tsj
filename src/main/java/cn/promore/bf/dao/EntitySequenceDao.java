package cn.promore.bf.dao;

import cn.promore.bf.bean.EntitySequence;


public interface EntitySequenceDao extends BaseDao<EntitySequence,Integer>{
	EntitySequence findByName(String name,String type);
	void deleteByType(String type);
}
