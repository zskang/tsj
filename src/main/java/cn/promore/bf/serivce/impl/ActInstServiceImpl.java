package cn.promore.bf.serivce.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.promore.bf.model.ActInst;
import cn.promore.bf.model.ActRuTask;
import cn.promore.bf.model.DeployEntity;
import cn.promore.bf.mydao.ActInstDao;
import cn.promore.bf.serivce.ActInstService;

@Service
public class ActInstServiceImpl implements ActInstService {

	@Resource
	private ActInstDao dao;

	@Override
	public ActInst queryObjByTaskId(String taskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskId);
		return dao.queryObjByTaskId(map);
	}

	@Override
	public String queryObjectByProcessInstanceId(String processInstanceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("processInstanceId", processInstanceId);
		ActRuTask ru = dao.queryObjectByProcessInstanceId(map);
		if (ru != null) {
			return ru.getProc_def_id_();
		}
		return "";
	}

	@Override
	public DeployEntity queryInfoBy(Map<String, Object> map) {
		return dao.queryInfoBy(map);
	}

}
