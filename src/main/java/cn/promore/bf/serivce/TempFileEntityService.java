package cn.promore.bf.serivce;

import java.util.List;
import java.util.Map;

import cn.promore.bf.model.TempFileEntity;

public interface TempFileEntityService {

	int saveEntity(TempFileEntity entity);

	/**
	 * @param map
	 * @return
	 */
	void removeAllAttachFiles(Map<String, Object> map);

	List<TempFileEntity> queryListByMap(Map<String, Object> map);

	TempFileEntity findById(int id);

	void deleteEntityByFileName(Map<String, Object> map);

	int queryCountByMap(Map<String, Object> map);

	List<Map<String, Object>> queryListByMap2(Map<String, Object> map);

	/**
	 * @param map
	 */
	void updateEntitys(Map<String, Object> map);

	/**
	 * @param map3
	 */
	void updateEntitys2(Map<String, Object> map3);

	void updateEntitysByYwid(Map<String, Object> map3);
}
