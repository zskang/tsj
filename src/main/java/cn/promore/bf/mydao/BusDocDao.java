package cn.promore.bf.mydao;

import java.util.List;
import java.util.Map;

import cn.promore.bf.model.BusDocEntity;

/**
 * Created by zskang on 2017/6/15.
 */
public interface BusDocDao {

	int saveEntity(BusDocEntity obj);

	void removeAll(Map<String, Object> map);

	List<BusDocEntity> queryListByBusId(Map<String, Object> map);

	void removeEntityById(int id);

	BusDocEntity findById(int id);

}
