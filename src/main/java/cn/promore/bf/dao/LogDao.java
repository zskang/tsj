package cn.promore.bf.dao;

import java.util.Date;
import java.util.List;

import cn.promore.bf.bean.Log;

public interface LogDao extends BaseDao<Log,Integer>{
	Integer findDataNo(Log log,Date startDate,Date endDate);
	List<Log> findDatas(Log log, Integer startIndex,Integer pageSize,Date startDate,Date endDate);
}
