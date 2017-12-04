package com.fable.insightview.platform.sysconf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.sysconf.entity.SysConfig;
import com.fable.insightview.platform.sysconf.mapper.SysConfigMapper;
import com.fable.insightview.platform.sysconf.service.SysConfigService;

@Service("sysConfigServiceImpl")
public class SysConfigServiceImpl implements SysConfigService {
	
	@Autowired
	SysConfigMapper sysConfigMapper;

	@Override
	public Map<String, String> getConfByTypeID(int typeID) {
		Map<String, String> result = new HashMap<String, String>();
		for (SysConfig sys : sysConfigMapper.getConfByTypeID(typeID)) {
			result.put(sys.getParaKey(), sys.getParaValue());
		}
		return result;
	}

	@Override
	public String getConfParamValue(int typeID, String paramKey) {
		return sysConfigMapper.getConfParamValue(typeID, paramKey);
	}

	@Override
	public void updateVal(SysConfig sysConfig) {
		sysConfigMapper.updateVal(sysConfig);
	}

	@Override
	public List<SysConfig> getConfMutip(int typeID) {
		return sysConfigMapper.getConfByTypeID(typeID);
	}
}