package com.fable.insightview.monitor.environmentmonitor.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.environmentmonitor.entity.MOReader;
import com.fable.insightview.monitor.environmentmonitor.entity.MOTag;
import com.fable.insightview.monitor.environmentmonitor.entity.MOZoneManagerBean;
import com.fable.insightview.monitor.environmentmonitor.entity.PerfSnapshotRoom;
import com.fable.insightview.monitor.environmentmonitor.entity.UpAndDownThresholdBean;
import com.fable.insightview.monitor.environmentmonitor.mapper.EnvMonitorMapper;
import com.fable.insightview.monitor.environmentmonitor.service.IEnvMonitorService;
import com.fable.insightview.platform.page.Page;

@Service
public class EnvMonitorServiceImpl implements IEnvMonitorService{
	@Autowired
	EnvMonitorMapper envMapper;

	@Override
	public List<MOReader> queryList(Page<MOReader> page) {
		return envMapper.queryList(page);
	}

	@Override
	public List<MOTag> queryTagList(Page<MOTag> page) {
		return envMapper.queryTagList(page);
	}
	
	@Override
	public LinkedList<PerfSnapshotRoom> loadEnvDataBy3DResID(Map parmetermap) {
		return envMapper.loadEnvDataBy3DResID(parmetermap);
	}

	// 同步阅读器
	@Override
	public LinkedList<MOReader> synchronMOReaderToRes(Page<MOReader> page) {
		return envMapper.synchronMOReaderToRes(page);
	}

	// 同步电子标签
	@Override
	public LinkedList<MOTag> synchronMOTagToRes(Page<MOTag> page) {
		return envMapper.synchronMOTagToRes(page);
	}

	@Override
	public int updateTagResId(Map map) {
		return envMapper.updateTagResId(map);
	}

	@Override
	public int updateReaderResId(Map map) {
		return envMapper.updateReaderResId(map);
	}

	@Override
	public List<MOZoneManagerBean> queryZoneManagerList(
			Page<MOZoneManagerBean> page) {
		return envMapper.queryZoneManagerList(page);
	}

	@Override
	public MOReader getMoReaderByMOID(int moID) {
		return envMapper.getMoReaderByMOID(moID);
	}

	@Override
	public MOTag getMOTagBYMOID(int moID) {
		return envMapper.getMOTagBYMOID(moID);
	}
	
	@Override
	public LinkedList<MOTag> queryRelationByResID(Map paramMap) {
		return envMapper.queryRelationByResID(paramMap);
	}

	 

	@Override
	public UpAndDownThresholdBean queryUpAndDownValue(Map parmetermap) {
		return envMapper.queryUpAndDownValue(parmetermap);
	}

	@Override
	public LinkedList<PerfSnapshotRoom> queryElectronicTag(Map parmetermap) {
		return envMapper.queryElectronicTag(parmetermap);
	}

	@Override
	public List<MOTag> queryDoorMagneticTagList(Page<MOTag> page) {
		return envMapper.queryDoorMagneticTagList(page);
	}

	@Override
	public List<MOTag> queryDryContactTagList(Page<MOTag> page) {
		return envMapper.queryDryContactTagList(page);
	}

	@Override
	public List<MOTag> queryTemperatureHumidityTagList(Page<MOTag> page) {
		return envMapper.queryTemperatureHumidityTagList(page);
	}

	@Override
	public List<MOTag> queryTemperatureTagList(Page<MOTag> page) {
		return envMapper.queryTemperatureTagList(page);
	}

	@Override
	public List<MOTag> queryWaterHoseTagList(Page<MOTag> page) {
		return envMapper.queryWaterHoseTagList(page);
	}

	@Override
	public UpAndDownThresholdBean UpAndDownValue(Map parmetermap) {
		// TODO Auto-generated method stub
		return envMapper.UpAndDownValue(parmetermap);
	}

	@Override
	public UpAndDownThresholdBean UpAndDownValue2(Map parmetermap) {
		// TODO Auto-generated method stub
		return envMapper.UpAndDownValue2(parmetermap);
	}

	@Override
	public UpAndDownThresholdBean parentUpAndDownValue(Map parmetermap) {
		// TODO Auto-generated method stub
		return envMapper.parentUpAndDownValue(parmetermap);
	}

	@Override
	public UpAndDownThresholdBean parentUpAndDownValue2(Map parmetermap) {
		// TODO Auto-generated method stub
		return envMapper.parentUpAndDownValue2(parmetermap);
	}

	@Override
	public UpAndDownThresholdBean queryParentClassId(Map<String, Integer> conditionMap) {
		// TODO Auto-generated method stub
		return envMapper.queryParentClassId(conditionMap);
	}

	@Override
	public LinkedList<UpAndDownThresholdBean> queryAlarmUpAndDownVlaue(
			Map parmetermap) {
		// TODO Auto-generated method stub
		return envMapper.queryAlarmUpAndDownVlaue(parmetermap);
	}

	@Override
	public UpAndDownThresholdBean queryRelationPath(Map conditionMap) {
		// TODO Auto-generated method stub
		return envMapper.queryRelationPath(conditionMap);
	}
}