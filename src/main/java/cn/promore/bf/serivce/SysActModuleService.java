package cn.promore.bf.serivce;

import java.util.Map;

import cn.promore.bf.model.SysActModuleEntity;

public interface SysActModuleService {
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
