package cn.promore.bf.dao;

import java.util.List;

import cn.promore.bf.bean.Announcement;

public interface AnnouncementDao extends BaseDao<Announcement,Integer>{
	Integer findDataNo(Announcement annou);
	List<Announcement> findDatas(Announcement annou, Integer startIndex,Integer pageSize);
}
