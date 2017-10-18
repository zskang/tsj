package cn.promore.bf.serivce;

import java.util.List;
import java.util.Map;

import cn.promore.bf.model.NodeConfigEntity;

public interface NodeConfigService {

	List<NodeConfigEntity> queryNodeConfiglist(Map<String, Object> map);

	void saveEntity(NodeConfigEntity et);

	NodeConfigEntity getEntity(Map<String, Object> map);

	void updateNodeCondig(NodeConfigEntity nodeConfigEntity);

}
