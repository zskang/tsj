package cn.promore.bf.serivce;

import java.util.List;

import cn.promore.bf.bean.CompletionInfo;

public interface CompletionInfoService extends BaseService<CompletionInfo,Integer>{
	
	static final Integer CompletionInfo_TYPE_MENU = 1;
	static final Integer CompletionInfo_TYPE_FUNCTION = 2;
	public CompletionInfo getRoot(Integer projectId);
	public List<CompletionInfo> getChild(Integer pId);
	public List<CompletionInfo> getChild(Integer pId,Integer type);
	public List<CompletionInfo> findPermissionNames();
	public List<CompletionInfo> findChildByPid(Integer pid);
	public void deleteCompletionInfoById(Integer id);
	public List<CompletionInfo> queryCompletionInfoFile(Integer projectId); 
	public void saveCompletionInfoForList(List<CompletionInfo> list);
	public CompletionInfo getCompletionInfoByFileId(Integer projectId,Integer fileId);
}
