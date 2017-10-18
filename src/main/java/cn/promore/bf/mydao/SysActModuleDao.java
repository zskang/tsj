package cn.promore.bf.mydao;

import java.util.Map;

import cn.promore.bf.model.SysActModuleEntity;

public interface SysActModuleDao {

	int addEntity(SysActModuleEntity obj);

	/**
	 * @param map
	 * @return
	 */
	int queryCount(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	String getModuleIdByWfKey(Map<String, Object> map);

	/**
	 * @param map
	 */
	void deleteByMap(Map<String, Object> map);

}
