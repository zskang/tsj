package cn.promore.bf.dao.impl;

import org.springframework.stereotype.Repository;

import cn.promore.bf.bean.SiteInfo;
import cn.promore.bf.dao.SiteInfoDao;

@Repository
public class SiteInfoDaoImpl extends BaseDaoImpl<SiteInfo,Integer>  implements SiteInfoDao {

	public Integer findALLNo() {
		return ((Long)this.getSessionFactory().getCurrentSession().createQuery(" select count(*) from SiteInfo ").uniqueResult()).intValue();
	}
	
}
