package cn.promore.bf.dao;

import cn.promore.bf.bean.EntitySequence;


public interface OrgSequenceDao extends BaseDao<EntitySequence,Integer>{
	EntitySequence findByName(String name);
}
