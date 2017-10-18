package cn.promore.bf.mydao;

import java.util.List;
import java.util.Map;

import cn.promore.bf.model.NodeConfigEntity;

public interface NodeConfigDao {

	List<NodeConfigEntity> queryNodeConfiglist(Map<String, Object> map);

	void saveEntity(NodeConfigEntity et);

	NodeConfigEntity getEntity(Map<String, Object> map);

	void updateNodeCondig(NodeConfigEntity nodeConfigEntity);

}
