package cn.promore.bf.dao;

import java.util.Date;
import java.util.List;

import cn.promore.bf.bean.Option;

public interface OptionDao extends BaseDao<Option,Integer>{
	Integer findDataNo(Option option,Date startDate,Date endDate);
	List<Option> findDatas(Option option, Integer startIndex,Integer pageSize,Date startDate,Date endDate);
	List<Option> findByCategory(String category);
}
