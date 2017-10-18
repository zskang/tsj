package cn.promore.bf.serivce.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.Option;
import cn.promore.bf.bean.Page;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.OptionDao;
import cn.promore.bf.serivce.OptionService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OptionServiceImpl extends BaseServiceImpl<Option, Integer> implements OptionService {
	@Resource
	private OptionDao optionDao;
	
	
	@Override
	@Resource(type=OptionDao.class)
	public void setDao(BaseDao<Option, Integer> baseDao) {
		super.setDao(baseDao);
	}

	public Integer findDatasNo(Option option,Date startDate,Date endDate) {
		return optionDao.findDataNo(option,startDate,endDate);
	}

	public List<Option> findDatas(Option option, Page page,Date startDate,Date endDate) {
		return optionDao.findDatas(option,(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize(),startDate,endDate);
	}
	
	public List<Option> findByCategory(String category) {
		return optionDao.findByCategory(category);
	}
}
