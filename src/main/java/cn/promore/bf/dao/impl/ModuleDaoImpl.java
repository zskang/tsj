package cn.promore.bf.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import cn.promore.bf.bean.Module;
import cn.promore.bf.dao.ModuleDao;
import cn.promore.bf.serivce.ModuleService;
@Repository
public class ModuleDaoImpl extends BaseDaoImpl<Module,Integer>  implements ModuleDao {

	public Module getRoot() {
		return (Module) this.getSessionFactory().getCurrentSession().createQuery("from Module z where z.parent = null").setCacheable(true).uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public List<Module> getChild(Integer pId) {
		return this.getSessionFactory().getCurrentSession().createQuery("from Module z where z.parent.id = :pId order by z.order ").setInteger("pId",pId).list();
	}
	@SuppressWarnings("unchecked")
	public List<Module> findAllDatas() {
		return this.getSessionFactory().getCurrentSession().createQuery("from Module r order by r.order asc").list();
	}
	@SuppressWarnings("unchecked")
	public List<Module> getChildByType(Integer pId, Integer type) {
		return this.getSessionFactory().getCurrentSession().createQuery("from Module z where z.parent.id = :pId and z.type = :type order by z.order ").setInteger("pId",pId).setInteger("type",type).setCacheable(true).list();
	}
	@SuppressWarnings("unchecked")
	public List<Module> findPermissionNames() {
		return this.getSessionFactory().getCurrentSession().createQuery("from Module r where r.type = :type").setInteger("type",ModuleService.MODULE_TYPE_FUNCTION).list();
	}
	@SuppressWarnings("unchecked")
	public List<Module> findChildByPid(Integer pid) {
		return this.getSessionFactory().getCurrentSession().createQuery("from Module r where r.parent.id = :pid order by r.order asc").setInteger("pid",pid).list();
	}
	@SuppressWarnings("unchecked")
	public List<Module> getModuleChild(Integer pId) {
		return this.getSessionFactory().getCurrentSession().createQuery("from Module z where z.parent.id = :pId and z.type = 1 and level<=3 and z.status = 'N' order by z.order ").setInteger("pId",pId).list();
	}
	@SuppressWarnings("unchecked")
	public List<Module> getModule(String name) {
		return this.getSessionFactory().getCurrentSession().createQuery("from Module z where z.type = 2 and z.status = 'N' and z.name like :name order by z.order ").setString("name", "%"+name+"%").list();
	}
	@SuppressWarnings("unchecked")
	public List<Module> getModuleFile(Integer pId) {
		return this.getSessionFactory().getCurrentSession().createQuery("from Module z where z.parent.id = :pId  and z.status = 'N' order by z.order ").setInteger("pId",pId).list();
	}	
	@SuppressWarnings("unchecked")
	public List<Module> getAllRootModules() {
		return this.getSessionFactory().getCurrentSession().createQuery("from Module z where level<=3 and z.type=1 order by z.order ").list();
	}
	@SuppressWarnings("unchecked")
	public List<Module> getDoneFiles(Integer moduleId) {
		return this.getSessionFactory().getCurrentSession().createSQLQuery("SELECT b.id, c.displayCode, b.docName name, c.type FROM bus_base_info a, bus_doc_info b, sys_module c  WHERE a.id = b.busId AND a.moduleId = c.id AND a.moduleId = "+moduleId+" AND a.state = '99'").setResultTransformer(Transformers.aliasToBean(Module.class)).list();
	}
	@SuppressWarnings("unchecked")
	public Module getDoneFiles(String path, String docType) {
		List<Module> list = this.getSessionFactory().getCurrentSession().createSQLQuery("SELECT id, displayCode, icon, level, name, path, status, type, docType FROM sys_module WHERE path LIKE '%"+path+"%' AND docType = '"+docType+"' ORDER BY displayOrder DESC  LIMIT 1").setResultTransformer(Transformers.aliasToBean(Module.class)).list();
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	public void insert(Module module) {
		this.getSessionFactory().getCurrentSession().save(module);
	}
	@SuppressWarnings("unchecked")
	public List<Module> getCompletionInfoFile(Integer projectId) {
		 SQLQuery query = this.getSessionFactory().getCurrentSession().createSQLQuery("select a.id,c.id as icon,a.docName as name,concat(a.docPath,a.docName) path,2 type from sys_module c,bus_doc_info a,bus_base_info b where a.busId = b.id and c.id = b.moduleId and b.state = '99' and b.projectId =:projectId");
		 query.setInteger("projectId",projectId);
		 query.addScalar("id", StandardBasicTypes.INTEGER);
		 query.addScalar("name", StandardBasicTypes.STRING);
		 query.addScalar("path", StandardBasicTypes.STRING);
		 query.addScalar("icon", StandardBasicTypes.STRING);
		 query.addScalar("type", StandardBasicTypes.INTEGER);
		 query.setResultTransformer(Transformers.aliasToBean(Module.class));
		 return query.list();
	}
	public void deleteModuleById(Integer id) {
		super.deleteById(id);
	}
}
