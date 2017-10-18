package cn.promore.bf.serivce;

import java.util.List;
import java.util.Map;

import cn.promore.bf.model.FileEntity;

public interface FileEntityService {

	int saveEntity(FileEntity entity);

	/**
	 * @param map
	 * @return
	 */
	void removeAllAttachFiles(Map<String, Object> map);

    List<FileEntity> queryListByBusId(Integer id);

	FileEntity findById(int id);

	List<Map<String, Object>> queryListByBusId2(Integer id);

	void deleteObj(FileEntity docFile);
}
