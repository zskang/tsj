package cn.promore.bf.serivce.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.SiteInfo;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.SiteInfoDao;
import cn.promore.bf.serivce.SiteInfoService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class SiteInfoServiceImpl extends BaseServiceImpl<SiteInfo, Integer> implements SiteInfoService {
	@Resource
	private SiteInfoDao siteInfoDao;

	@Override
	@Resource(type=SiteInfoDao.class)
	public void setDao(BaseDao<SiteInfo, Integer> baseDao) {
		super.setDao(baseDao);
	}
	
	public List<SiteInfo> findSiteInfos(Page page) {
		return siteInfoDao.findAll((page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize());
	}

	public Integer findAllNo() {
		return siteInfoDao.findALLNo();
	}
}
