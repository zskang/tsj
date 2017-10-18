package cn.promore.bf.serivce.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.promore.bf.model.SysActModuleEntity;
import cn.promore.bf.mydao.SysActModuleDao;
import cn.promore.bf.serivce.SysActModuleService;

@Service
public class SysActModuleServiceImpl implements SysActModuleService {

	@Resource
	private SysActModuleDao dao;

	@Override
	public int addEntity(SysActModuleEntity obj) {
		return dao.addEntity(obj);
	}

	@Override
	public int queryCount(Map<String, Object> map) {
		return dao.queryCount(map);
	}

	@Override
	public String getModuleIdByWfKey(Map<String, Object> mp) {
		return dao.getModuleIdByWfKey(mp);
	}

	@Override
	public void deleteByMap(Map<String, Object> map) {
		dao.deleteByMap(map);
	}

}
