package cn.promore.bf.serivce;

import java.util.Date;
import java.util.List;

import cn.promore.bf.bean.Option;
import cn.promore.bf.bean.Page;


public interface OptionService extends BaseService<Option,Integer>{
	public Integer findDatasNo(Option option,Date startDate,Date endDate);
	public List<Option> findDatas(Option option,Page page,Date startDate,Date endDate);
	public List<Option> findByCategory(String category);
}
