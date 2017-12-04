package com.fable.insightview.monitor.environmentmonitor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.environmentmonitor.entity.MOZoneManagerBean;
import com.fable.insightview.monitor.environmentmonitor.mapper.MOZoneManagerMapper;
import com.fable.insightview.monitor.environmentmonitor.service.IZoneManagerService;
import com.fable.insightview.platform.page.Page;

@Service
public class ZoneManagerServiceImpl implements IZoneManagerService {
	@Autowired
	MOZoneManagerMapper zoneManagerMapper;

	@Override
	public List<MOZoneManagerBean> getZoneManagerList(
			Page<MOZoneManagerBean> page) {
		return zoneManagerMapper.getZoneManagerList(page);
	}

	@Override
	public MOZoneManagerBean getZoneManagerInfo(Integer moID) {
		return zoneManagerMapper.getZoneManagerInfo(moID);
	}

	@Override
	public MOZoneManagerBean getZoneManagerByIP(MOZoneManagerBean bean) {
		return zoneManagerMapper.getZoneManagerByIP(bean);
	}

	@Override
	public boolean updateZoneManager(MOZoneManagerBean bean) {
		try {
			zoneManagerMapper.updateZoneManager(bean);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
