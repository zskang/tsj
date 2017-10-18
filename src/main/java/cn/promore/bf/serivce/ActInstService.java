package cn.promore.bf.serivce;

import java.util.Map;

import cn.promore.bf.model.ActInst;
import cn.promore.bf.model.DeployEntity;

public interface ActInstService {

	ActInst queryObjByTaskId(String taskId);

	String queryObjectByProcessInstanceId(String processInstanceId);

	/**
	 * @param map
	 * @return
	 */
	DeployEntity queryInfoBy(Map<String, Object> map);

}
