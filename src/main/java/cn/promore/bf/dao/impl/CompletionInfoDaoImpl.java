package cn.promore.bf.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.promore.bf.bean.CompletionInfo;
import cn.promore.bf.dao.CompletionInfoDao;
import cn.promore.bf.serivce.CompletionInfoService;
@Repository
public class CompletionInfoDaoImpl extends BaseDaoImpl<CompletionInfo,Integer> implements CompletionInfoDao {

	public CompletionInfo getRoot(Integer projectId) {
		return (CompletionInfo) this.getSessionFactory().getCurrentSession().createQuery("from CompletionInfo z where z.parent = null and z.project.id =:projectId").setInteger("projectId",projectId).setCacheable(true).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<CompletionInfo> getChild(Integer pId) {
		return this.getSessionFactory().getCurrentSession().createQuery("from CompletionInfo z where z.parent.id = :pId order by z.order ").setInteger("pId",pId).list();
	}
	@SuppressWarnings("unchecked")
	public List<CompletionInfo> getChildByType(Integer pId, Integer type) {
		return this.getSessionFactory().getCurrentSession().createQuery("from CompletionInfo z where z.parent.id = :pId and z.type = :type order by z.order ").setInteger("pId",pId).setInteger("type",type).setCacheable(true).list();
	}

	@SuppressWarnings("unchecked")
	public List<CompletionInfo> findPermissionNames() {
		return this.getSessionFactory().getCurrentSession().createQuery("from CompletionInfo r where r.type = :type").setInteger("type",CompletionInfoService.CompletionInfo_TYPE_FUNCTION).list();
	}
	@SuppressWarnings("unchecked")
	public List<CompletionInfo> findChildByPid(Integer pid) {
		return this.getSessionFactory().getCurrentSession().createQuery("from CompletionInfo r where r.parent.id = :pid  order by r.order asc").setInteger("pid",pid).list();
	}

	public void deleteCompletionInfoById(Integer id) {
		super.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	public List<CompletionInfo> queryCompletionInfoFile(Integer projectId) {
		return this.getSessionFactory().getCurrentSession().createQuery("from CompletionInfo r where r.project.id =:projectId").setInteger("projectId",projectId).list();
	}

	@SuppressWarnings("unchecked")
	public CompletionInfo getCompletionInfoByFileId(Integer projectId,Integer fileId) {
		List<CompletionInfo> list = this.getSessionFactory().getCurrentSession().createQuery("from CompletionInfo r where r.project.id = :projectId and r.fileId = :fileId order by r.order asc").setInteger("projectId",projectId).setInteger("fileId",fileId).list();
		return list != null && list.size() > 0 ? list.get(0): null;
	}
}