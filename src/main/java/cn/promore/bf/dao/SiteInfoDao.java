package cn.promore.bf.dao;

import cn.promore.bf.bean.SiteInfo;

public interface SiteInfoDao extends BaseDao<SiteInfo,Integer>{
	Integer findALLNo();
}
