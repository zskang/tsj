package cn.promore.bf.serivce.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.Log;
import cn.promore.bf.bean.Page;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.LogDao;
import cn.promore.bf.serivce.LogService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class LogServiceImpl extends BaseServiceImpl<Log, Integer> implements LogService {
	@Resource
	private LogDao logDao;
	
	@Override
	@Resource(type=LogDao.class)
	public void setDao(BaseDao<Log, Integer> baseDao) {
		super.setDao(baseDao);
	}

	public Integer findDatasNo(Log log,Date startDate,Date endDate) {
		return logDao.findDataNo(log,startDate,endDate);
	}

	public List<Log> findDatas(Log log, Page page,Date startDate,Date endDate) {
		return logDao.findDatas(log,(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize(),startDate,endDate);
	}
}
