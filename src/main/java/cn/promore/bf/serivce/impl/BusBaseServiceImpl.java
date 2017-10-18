package cn.promore.bf.serivce.impl;

import cn.promore.bf.model.BusBaseEntity;
import cn.promore.bf.mydao.BusBaseDao;
import cn.promore.bf.serivce.BusBaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusBaseServiceImpl implements BusBaseService {
	@Resource
	private BusBaseDao dao;

	@Override
	public List<BusBaseEntity> getAllPage(Map<String, Object> map) {
		return dao.getAllPage(map);
	}

	@Override
	public int getCount(Map<String, Object> map) {
		return dao.getCount(map);
	}

	@Override
	public int saveBusBaseEntity(BusBaseEntity obj) {
		return dao.saveBusBaseEntity(obj);
	}

	@Override
	public BusBaseEntity getBusBaseEntityById(int id) {
		return dao.getBusBaseEntityById(id);
	}

	@Override
	public int updateBusBaseEntity(BusBaseEntity obj) {
		return dao.updateBusBaseEntity(obj);
	}

	@Override
	public BusBaseEntity getBusBaseEntityByTaskId(String processInstanceId) {
		return dao.getBusBaseEntityByTaskId(processInstanceId);
	}

	@Override
	public void deleteBusBaseEntity(BusBaseEntity obj) {
		dao.deleteBusBaseEntity(obj);
	}

	@Override
	public void saveRelationWith(String modulerId, String deployName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modulerId", modulerId);
		map.put("deployName", deployName);
		dao.saveRelationWith(modulerId, deployName);
	}

	@Override
	public List<BusBaseEntity> queryListByParentId(int parentId) {
		return dao.queryListByParentId(parentId);
	}

	@Override
	public int getCountforNoneDone(Map<String, Object> map) {
		return dao.getCountforNoneDone(map);
	}

	@Override
	public void updateBusBaseEntity4Gzjj(Map<String, Object> map) {
		dao.updateBusBaseEntity4Gzjj(map);
	}
}
