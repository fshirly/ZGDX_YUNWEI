package com.fable.insightview.monitor.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.portal.mapper.EditInfoMapper;
import com.fable.insightview.monitor.portal.service.IEditInfoService;
import com.fable.insightview.monitor.topo.entity.TopoBean;
import com.fable.insightview.monitor.topo.mapper.TopoMapper;

@Service
public class EditInfoServiceImpl implements IEditInfoService {

	@Autowired
	EditInfoMapper editInfoMapper;
	@Autowired
	TopoMapper topoMapper;

	@Override
	public List<MODevice> getDeviceName(Map map) {
		return editInfoMapper.getDeviceName(map);
	}

	@Override
	public Integer getMoClassIDBymoid(Integer moid) {
		return editInfoMapper.getMoClassIDBymoid(moid);
	}

	@Override
	public List<TopoBean> getAllTopo() {
		return topoMapper.getAllTopo();
	}

}
