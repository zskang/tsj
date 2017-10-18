package cn.promore.bf.serivce.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.promore.bf.model.BusDocEntity;
import cn.promore.bf.mydao.BusDocDao;
import cn.promore.bf.serivce.BusDocService;

/**
 * Created by zskang on 2017/6/15.
 */

@Service
public class BusDocServiceImpl implements BusDocService {
	@Resource
	private BusDocDao dao;

	@Override
	public int saveEntity(BusDocEntity obj) {
		return dao.saveEntity(obj);
	}

	@Override
	public void removeAll(Map<String, Object> map) {
		dao.removeAll(map);
	}

	@Override
	public List<BusDocEntity> queryDocListByMap(Map<String, Object> map) {
		return dao.queryListByBusId(map);
	}

	@Override
	public void removeEntityById(int id) {
		dao.removeEntityById(id);
	}

	@Override
	public BusDocEntity findById(int id) {
		return dao.findById(id);
	}
}
