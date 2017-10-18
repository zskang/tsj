package cn.promore.bf.serivce;

import java.util.List;
import java.util.Map;

import cn.promore.bf.model.BusDocEntity;

public interface BusDocService {

	int saveEntity(BusDocEntity obj);

	/**
	 * @param map
	 * @return
	 */
	void removeAll(Map<String, Object> map);

	List<BusDocEntity> queryDocListByMap(Map<String, Object> map);

	void removeEntityById(int id);

	BusDocEntity findById(int i);
}
