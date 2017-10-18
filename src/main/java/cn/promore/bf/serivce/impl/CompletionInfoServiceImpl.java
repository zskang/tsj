package cn.promore.bf.serivce.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.CompletionInfo;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.CompletionInfoDao;
import cn.promore.bf.serivce.CompletionInfoService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class CompletionInfoServiceImpl extends BaseServiceImpl<CompletionInfo, Integer>  implements CompletionInfoService {
	@Resource
	private CompletionInfoDao completionInfoDao;
	
	@Resource(type=CompletionInfoDao.class)
	public void setDao(BaseDao<CompletionInfo, Integer> baseDao) {
		super.setDao(baseDao);
	}

	public CompletionInfo getRoot(Integer projectId) {
		return completionInfoDao.getRoot(projectId);
	}

	public List<CompletionInfo> getChild(Integer pId) {
		return completionInfoDao.getChild(pId);
	}

	public List<CompletionInfo> getChild(Integer pId, Integer type) {
		return completionInfoDao.getChildByType(pId, type);
	}

	public List<CompletionInfo> findPermissionNames() {
		return completionInfoDao.findPermissionNames();
	}

	public List<CompletionInfo> findChildByPid(Integer pid) {
		return completionInfoDao.findChildByPid(pid);
	}

	public void deleteCompletionInfoById(Integer id) {
		List<CompletionInfo> list = completionInfoDao.getChild(id);
		for(CompletionInfo completionInfo:list){
			completionInfoDao.deleteCompletionInfoById(completionInfo.getId());
		}
		completionInfoDao.deleteCompletionInfoById(id);
	}

	public List<CompletionInfo> queryCompletionInfoFile(Integer projectId) {
		return completionInfoDao.queryCompletionInfoFile(projectId);
	}

	public void saveCompletionInfoForList(List<CompletionInfo> list) {
		for(CompletionInfo completionInfo:list){
			completionInfoDao.save(completionInfo);
		}
	}

	public CompletionInfo getCompletionInfoByFileId(Integer projectId,Integer fileId) {
		return completionInfoDao.getCompletionInfoByFileId(projectId, fileId);
	}
}
