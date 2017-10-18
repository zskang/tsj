package cn.promore.bf.dao;

import java.util.List;

import cn.promore.bf.bean.CompletionInfo;

public interface CompletionInfoDao extends BaseDao<CompletionInfo,Integer>{

	CompletionInfo getRoot(Integer projectId);
	List<CompletionInfo> getChild(Integer pId);
	List<CompletionInfo> getChildByType(Integer pId,Integer type);
	List<CompletionInfo> findPermissionNames();
	List<CompletionInfo> findChildByPid(Integer pid);
	void deleteCompletionInfoById(Integer id);
	List<CompletionInfo> queryCompletionInfoFile(Integer projectId); 
	CompletionInfo getCompletionInfoByFileId(Integer projectId,Integer fileId);
}
