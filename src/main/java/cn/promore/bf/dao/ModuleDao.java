package cn.promore.bf.dao;

import java.util.List;

import cn.promore.bf.bean.Module;

public interface ModuleDao extends BaseDao<Module,Integer>{

	Module getRoot();
	List<Module> getChild(Integer pId);
	List<Module> getChildByType(Integer pId,Integer type);
	List<Module> findPermissionNames();
	List<Module> findChildByPid(Integer pid);
	List<Module> findAllDatas();
	List<Module> getModuleChild(Integer pId);
	List<Module> getModule(String name);
	List<Module> getModuleFile(Integer pId);
	List<Module> getAllRootModules();
	List<Module> getDoneFiles(Integer moduleId);
	Module getDoneFiles(String path, String docType);
	void insert(Module module);
	List<Module> getCompletionInfoFile(Integer projectId);
	void deleteModuleById(Integer id);
}
