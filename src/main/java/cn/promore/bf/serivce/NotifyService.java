package cn.promore.bf.serivce;

import java.util.Date;
import java.util.List;

import cn.promore.bf.bean.Notify;
import cn.promore.bf.bean.Page;

public interface NotifyService extends BaseService<Notify,Integer>{
	Integer findDataNo(Notify notify, Date startDate, Date endDate);
	List<Notify> findDatas(Notify notify,Date startDate,Date endDate, Page page);
	public List<Notify> findUnReadedNotify(Integer recieverId);
}
