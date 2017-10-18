package cn.promore.bf.mydao;

import java.util.Map;

import cn.promore.bf.model.ActInst;
import cn.promore.bf.model.ActRuTask;
import cn.promore.bf.model.DeployEntity;

public interface ActInstDao {

	public ActInst queryObjByTaskId(Map<String, Object> map);

	public ActRuTask queryObjectByProcessInstanceId(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	public DeployEntity queryInfoBy(Map<String, Object> map);

}
