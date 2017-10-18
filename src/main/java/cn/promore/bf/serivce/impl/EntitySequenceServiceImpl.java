package cn.promore.bf.serivce.impl;

import java.text.DecimalFormat;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.EntitySequence;
import cn.promore.bf.dao.EntitySequenceDao;
import cn.promore.bf.serivce.EntitySequenceService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class EntitySequenceServiceImpl implements EntitySequenceService {
	@Resource
	private EntitySequenceDao orgSequenceDao;

	@Override
	public synchronized String findNext(String name,String type) {
		DecimalFormat df = new DecimalFormat("000");
		EntitySequence orgSequence = orgSequenceDao.findByName(name,type);
		if(null==orgSequence){
			orgSequence = new EntitySequence(name,1,type);
			orgSequenceDao.save(orgSequence);
			return df.format(1);
		}else{
			Integer cursor = orgSequence.getCursor();
			orgSequence.setCursor(cursor+1);
			orgSequenceDao.update(orgSequence);
			return df.format(cursor+1);
		}
	}

	@Override
	public void resetEntitySequence(String type) {
		orgSequenceDao.deleteByType(type);
	}

}
