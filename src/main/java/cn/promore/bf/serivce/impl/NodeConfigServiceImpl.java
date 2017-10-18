package cn.promore.bf.serivce.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.promore.bf.model.NodeConfigEntity;
import cn.promore.bf.mydao.NodeConfigDao;
import cn.promore.bf.serivce.NodeConfigService;

@Service
public class NodeConfigServiceImpl implements NodeConfigService {

	@Resource
	private NodeConfigDao dao;

	@Override
	public List<NodeConfigEntity> queryNodeConfiglist(Map<String, Object> map) {
		return dao.queryNodeConfiglist(map);
	}

	@Override
	public void saveEntity(NodeConfigEntity et) {
		dao.saveEntity(et);
	}

	@Override
	public NodeConfigEntity getEntity(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.getEntity(map);
	}

	@Override
	public void updateNodeCondig(NodeConfigEntity nodeConfigEntity) {
		dao.updateNodeCondig(nodeConfigEntity);
	}

}
