package cn.promore.bf.serivce;

import java.util.List;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.SiteInfo;


public interface SiteInfoService extends BaseService<SiteInfo,Integer>{
	public List<SiteInfo> findSiteInfos(Page page);
	public Integer findAllNo();

	
}
