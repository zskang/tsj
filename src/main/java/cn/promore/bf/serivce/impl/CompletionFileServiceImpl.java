package cn.promore.bf.serivce.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.CompletionFile;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.CompletionFileDao;
import cn.promore.bf.serivce.CompletionFileService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CompletionFileServiceImpl extends BaseServiceImpl<CompletionFile, Integer>  implements CompletionFileService {

	@Resource
	private CompletionFileDao completionFileDao;
	
	@Resource(type=CompletionFileDao.class)
	public void setDao(BaseDao<CompletionFile, Integer> baseDao) {
		super.setDao(baseDao);
	}
}
