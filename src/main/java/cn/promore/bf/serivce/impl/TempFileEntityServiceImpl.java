package cn.promore.bf.serivce.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.promore.bf.model.TempFileEntity;
import cn.promore.bf.mydao.TempFileEntityDao;
import cn.promore.bf.serivce.TempFileEntityService;

@Service
public class TempFileEntityServiceImpl implements TempFileEntityService {

	@Resource
	protected TempFileEntityDao dao;

	@Override
	public int saveEntity(TempFileEntity entity) {
		return dao.saveEntity(entity);
	}

	@Override
	public void removeAllAttachFiles(Map<String, Object> map) {
		dao.removeAllAttachFiles(map);

	}

	@Override
	public List<TempFileEntity> queryListByMap(Map<String, Object> map) {
		return dao.queryListByMap(map);
	}

	@Override
	public TempFileEntity findById(int id) {
		return dao.findById(id);
	}

	@Override
	public void deleteEntityByFileName(Map<String, Object> map) {
		dao.deleteEntityByFileName(map);
	}

	@Override
	public int queryCountByMap(Map<String, Object> map) {
		return dao.queryCountByMap(map);
	}

	@Override
	public List<Map<String, Object>> queryListByMap2(Map<String, Object> map) {
		return dao.queryListByMap2(map);
	}

	@Override
	public void updateEntitys(Map<String, Object> map) {
		dao.updateEntitys(map);
	}

	@Override
	public void updateEntitys2(Map<String, Object> map3) {
		dao.updateEntitys2(map3);
	}

	@Override
	public void updateEntitysByYwid(Map<String, Object> map) {
		dao.updateEntitysByYwid(map);
	}

}
