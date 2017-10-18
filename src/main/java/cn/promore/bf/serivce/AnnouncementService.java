package cn.promore.bf.serivce;

import java.util.List;

import cn.promore.bf.bean.Announcement;
import cn.promore.bf.bean.Page;

public interface AnnouncementService extends BaseService<Announcement, Integer>{
	Integer findDataNo(Announcement annou);
	List<Announcement> findDatas(Announcement annou, Page page);
}
