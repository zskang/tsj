package cn.promore.bf.mydao;

import java.util.List;
import java.util.Map;

import cn.promore.bf.model.FileEntity;

public interface FileEntityDao {
	int saveEntity(FileEntity entity);

	List<FileEntity> queryListByYwid(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	void removeAllAttachFiles(Map<String, Object> map);

	FileEntity findById(int id);

	List<Map<String, Object>> queryListByYwid2(Map<String, Object> mp);

	void deleteEntity(Map<String, Object> mp);
}
