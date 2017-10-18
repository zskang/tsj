package cn.promore.bf.serivce.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.bean.Module;
import cn.promore.bf.dao.BaseDao;
import cn.promore.bf.dao.ModuleDao;
import cn.promore.bf.serivce.ModuleService;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class ModuleServiceImpl  extends BaseServiceImpl<Module, Integer> implements ModuleService {
	@Resource
	private ModuleDao moduleDao;
	
	public Module getRoot() {
		return moduleDao.getRoot();
	}
	@Resource(type=ModuleDao.class)
	public void setDao(BaseDao<Module, Integer> baseDao) {
		super.setDao(baseDao);
	}
	public List<Module> getChild(Integer pId) {
		return moduleDao.getChild(pId);
	}
	public List<Module> getChild(Integer pId, Integer type) {
		return moduleDao.getChildByType(pId, type);
	}
	public List<Module> findPermissionNames() {
		return moduleDao.findPermissionNames();
	}
	public List<Module> findChildByPid(Integer pid) {
		return moduleDao.findChildByPid(pid);
	}
	public List<Module> findAllDatas() {
		return moduleDao.findAllDatas();
	}
	public List<Module> getModuleChild(Integer pId) {
		return moduleDao.getModuleChild(pId);
	}
	public List<Module> getModule(String name) {
		return moduleDao.getModule(name);
	}
	public List<Module> getModuleFile(Integer pId) {
		return moduleDao.getModuleFile(pId);
	}
	public List<Module> getAllRootModules() {
		return moduleDao.getAllRootModules();
	}
	public List<Module> getDoneFiles(Integer moduleId){
		return moduleDao.getDoneFiles(moduleId);
	}
	public Module getModuleByPath(String path, String docType) {
		return moduleDao.getDoneFiles(path, docType);
	}
	public void insert(Module module) {
		moduleDao.insert(module);
	}
	public List<Module> getCompletionInfoFile(Integer projectId) {
		return moduleDao.getCompletionInfoFile(projectId);
	}
	public void deleteModuleById(Integer id) {
		List<Module> list = moduleDao.getChild(id);
		for(Module module:list){
			moduleDao.deleteModuleById(module.getId());
		}
		moduleDao.deleteModuleById(id);
	}
}