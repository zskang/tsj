package cn.promore.bf.serivce;

import java.util.List;
import java.util.Map;

import cn.promore.bf.bean.Page;


public interface StatisticsService {
	public List<Map<String, Object>> queryUserRankingList(Page page,Map<String, Object> map);
	public Integer queryUserRankingCount(Map<String, Object> map);
	public List<Map<String, Object>> queryUserRankingLists(Map<String, Object> map);
	public List<Map<String, Object>> queryProjectRankingList(Page page,Map<String, Object> map);
	public Integer queryProjectRankingCount(Map<String, Object> map);
	public List<Map<String, Object>> queryProjectRankingLists(Map<String, Object> map);
	public List<Map<String, Object>> queryModuleTaskList(Map<String, String> map);
	public List<Map<String, Object>> showModuleFileList(Map<String, String> map);
	public List<Map<String, Object>> queryModuleIdByTaskId(String taskId);
	public Map<String, Object> queryActHiComments(String taskId);
	
	
	
}