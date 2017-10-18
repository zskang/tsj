package cn.promore.bf.serivce.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.Notify;
import cn.promore.bf.bean.Page;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.NotifyDao;
import cn.promore.bf.serivce.NotifyService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class NotifyServiceImpl extends BaseServiceImpl<Notify,Integer> implements NotifyService {
	@Resource
	private NotifyDao notifyDao;

	@Override
	@Resource(type=NotifyDao.class)
	public void setDao(BaseDao<Notify, Integer> baseDao) {
		super.setDao(baseDao);
	}

	public Integer findDataNo(Notify notify,Date startDate,Date endDate) {
		return notifyDao.findDataNo(notify,startDate,endDate);
	}

	public List<Notify> findDatas(Notify notify,Date startDate,Date endDate, Page page) {
		return notifyDao.findDatas(notify,startDate,endDate,(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize());
	}

	@Override
	public List<Notify> findUnReadedNotify(Integer recieverId) {
		return notifyDao.findUnReadedNotify(recieverId);
	}

}
