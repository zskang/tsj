package cn.promore.bf.serivce;

import java.util.Date;
import java.util.List;

import cn.promore.bf.bean.Log;
import cn.promore.bf.bean.Page;

public interface LogService extends BaseService<Log,Integer>{
	public Integer findDatasNo(Log log,Date startDate,Date endDate);
	public List<Log> findDatas(Log log,Page page,Date startDate,Date endDate);
}
