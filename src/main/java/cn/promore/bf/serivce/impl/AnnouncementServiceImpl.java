package cn.promore.bf.serivce.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.Announcement;
import cn.promore.bf.bean.Page;
import cn.promore.bf.dao.AnnouncementDao;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.serivce.AnnouncementService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class AnnouncementServiceImpl extends BaseServiceImpl<Announcement,Integer> implements AnnouncementService {
	
	@Resource
	private AnnouncementDao announcementDao;
	
	@Resource(type=AnnouncementDao.class)
	public void setDao(BaseDao<Announcement, Integer> baseDao) {
		super.setDao(baseDao);
	}

	public Integer findDataNo(Announcement annou) {
		return announcementDao.findDataNo(annou);
	}

	public List<Announcement> findDatas(Announcement annou, Page page) {
		return announcementDao.findDatas(annou,(page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize());
	}
	
}
