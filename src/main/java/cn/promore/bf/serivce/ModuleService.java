package cn.promore.bf.serivce;

import java.util.List;

import cn.promore.bf.bean.Module;

public interface ModuleService extends BaseService<Module,Integer>{

	static final Integer MODULE_TYPE_MENU = 1;
	static final Integer MODULE_TYPE_FUNCTION = 2;
	public Module getRoot();
	public List<Module> getChild(Integer pId);
	public List<Module> getChild(Integer pId,Integer type);
	public List<Module> findPermissionNames();
	public List<Module> findChildByPid(Integer pid);
	public List<Module> findAllDatas();
	public List<Module> getModuleChild(Integer pId);
	public List<Module> getModule(String name);
	public List<Module> getModuleFile(Integer pId);
	List<Module> getAllRootModules();
	List<Module> getDoneFiles(Integer moduleId);
	public Module getModuleByPath(String path, String docType);
	public void insert(Module module);
	public List<Module> getCompletionInfoFile(Integer projectId);
	public void deleteModuleById(Integer id);
}
