package cn.promore.bf.serivce.impl;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.Page;
import cn.promore.bf.dao.StatisticsDao;
import cn.promore.bf.serivce.StatisticsService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class StatisticsServiceImpl implements StatisticsService {
	@Resource
	StatisticsDao statisticsDao;
	
	public List<Map<String, Object>> queryUserRankingList(Page page,Map<String, Object> paramMap) {
		return statisticsDao.queryUserRankingList(paramMap, (page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize());
	}

	public Integer queryUserRankingCount(Map<String, Object> paramMap) {
		return statisticsDao.queryUserRankingCount(paramMap);
	}

	public List<Map<String, Object>> queryUserRankingLists(Map<String, Object> map) {
		return statisticsDao.queryUserRankingLists(map);
	}

	public List<Map<String, Object>> queryProjectRankingList(Page page,Map<String, Object> paramMap) {
		return statisticsDao.queryProjectRankingList(paramMap, (page.getCurrentPage()-1)*page.getPageSize(),page.getPageSize());
	}

	public Integer queryProjectRankingCount(Map<String, Object> paramMap) {
		return statisticsDao.queryProjectRankingCount(paramMap);
	}

	public List<Map<String, Object>> queryProjectRankingLists(Map<String, Object> paramMap) {
		return statisticsDao.queryProjectRankingLists(paramMap);
	}

	public List<Map<String, Object>> queryModuleTaskList(Map<String, String> map) {
		return statisticsDao.queryModuleTaskList(map);
	}

	public List<Map<String, Object>> showModuleFileList(Map<String, String> map) {
		return statisticsDao.showModuleFileList(map);
	}
	
	public List<Map<String, Object>> queryModuleIdByTaskId(String taskId) {
		return statisticsDao.queryModuleIdByTaskId(taskId);
	}

	@Override
	public Map<String, Object> queryActHiComments(String commId) {
		return statisticsDao.queryActHiComments(commId);
	}
}