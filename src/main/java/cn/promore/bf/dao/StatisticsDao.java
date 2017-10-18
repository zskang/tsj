package cn.promore.bf.dao;

import java.util.List;
import java.util.Map;

public interface StatisticsDao {
	public List<Map<String,Object>> queryUserRankingList(Map<String, Object> paramMap,int pageNo, int pageSize);
	public Integer queryUserRankingCount(Map<String, Object> paramMap);
	public List<Map<String, Object>> queryUserRankingLists(Map<String, Object> map);
	public List<Map<String,Object>> queryProjectRankingList(Map<String, Object> paramMap,int pageNo, int pageSize);
	public Integer queryProjectRankingCount(Map<String, Object> paramMap);
	public List<Map<String, Object>> queryProjectRankingLists(Map<String, Object> map);
	public List<Map<String, Object>> queryModuleTaskList(Map<String, String> map);
	public List<Map<String, Object>> showModuleFileList(Map<String, String> map);
	public List<Map<String, Object>> queryModuleIdByTaskId(String taskId);
	public Map<String, Object> queryActHiComments(String taskId);
}
