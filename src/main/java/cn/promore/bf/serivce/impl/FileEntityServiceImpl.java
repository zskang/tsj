package cn.promore.bf.serivce.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.promore.bf.model.FileEntity;
import cn.promore.bf.mydao.FileEntityDao;
import cn.promore.bf.serivce.FileEntityService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FileEntityServiceImpl implements FileEntityService {
	@Resource
	private FileEntityDao dao;

	@Override
	public int saveEntity(FileEntity entity) {
		return dao.saveEntity(entity);
	}

	@Override
	public void removeAllAttachFiles(Map<String, Object> map) {

		dao.removeAllAttachFiles(map);
	}

	@Override
	public List<FileEntity> queryListByBusId(Integer id) {
		Map<String, Object> mp = new HashMap<>();
		mp.put("ywid", id);
		return dao.queryListByYwid(mp);
	}

	@Override
	public FileEntity findById(int id) {
		return dao.findById(id);
	}

	@Override
	public List<Map<String, Object>> queryListByBusId2(Integer id) {
		Map<String, Object> mp = new HashMap<>();
		mp.put("ywid", id);
		return dao.queryListByYwid2(mp);
	}

	@Override
	public void deleteObj(FileEntity docFile) {
		Map<String, Object> mp = new HashMap<>();
		mp.put("ID", docFile.getId());
		dao.deleteEntity(mp);
	}
}
