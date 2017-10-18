package cn.promore.bf.serivce;

import cn.promore.bf.model.BusBaseEntity;

import java.util.List;
import java.util.Map;


public interface BusBaseService {
	  List<BusBaseEntity> getAllPage(Map<String, Object> map);

	  int getCount(Map<String, Object> map);

	  int saveBusBaseEntity(BusBaseEntity obj);

	  BusBaseEntity getBusBaseEntityById(int id);

	  int updateBusBaseEntity(BusBaseEntity obj);

	  BusBaseEntity getBusBaseEntityByTaskId(String processInstanceId);

	  void deleteBusBaseEntity(BusBaseEntity obj);

	  void saveRelationWith(String modulerId, String deployName);

	  List<BusBaseEntity> queryListByParentId(int parentId);

	/**
	 * @param map
	 * @return
	 */
	  int getCountforNoneDone(Map<String, Object> map);

	void updateBusBaseEntity4Gzjj(Map<String, Object> map);
}
