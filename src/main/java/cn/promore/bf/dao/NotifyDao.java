package cn.promore.bf.dao;

import java.util.Date;
import java.util.List;

import cn.promore.bf.bean.Notify;

public interface NotifyDao extends BaseDao<Notify,Integer>{
	Integer findDataNo(Notify notify,Date startDate,Date endDate);
	List<Notify> findDatas(Notify notify,Date startDate,Date endDate, Integer startIndex,Integer pageSize);
	List<Notify> findUnReadedNotify(Integer recieverId);
}
