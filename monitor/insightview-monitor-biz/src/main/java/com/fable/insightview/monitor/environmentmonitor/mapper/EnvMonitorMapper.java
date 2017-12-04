package com.fable.insightview.monitor.environmentmonitor.mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.environmentmonitor.entity.MOReader;
import com.fable.insightview.monitor.environmentmonitor.entity.MOTag;
import com.fable.insightview.monitor.environmentmonitor.entity.MOZoneManagerBean;
import com.fable.insightview.monitor.environmentmonitor.entity.PerfSnapshotRoom;
import com.fable.insightview.monitor.environmentmonitor.entity.UpAndDownThresholdBean;
import com.fable.insightview.platform.page.Page;

public interface EnvMonitorMapper {
	/**
	 * 查询ZoneManager列表
	 * @param page
	 * @return
	 */
	public List<MOZoneManagerBean> queryZoneManagerList(Page<MOZoneManagerBean> page);
	/**
	 * 查询阅读器列表
	 * @param page
	 * @return
	 */
	public List<MOReader> queryList(Page<MOReader> page);
	/**
	 * 查询标签列表
	 * @param page
	 * @return
	 */
	public List<MOTag> queryTagList(Page<MOTag> page);
	
	// 同步阅读器
	LinkedList<MOReader> synchronMOReaderToRes(Page<MOReader> page);

	// 同步电子标签
	LinkedList<MOTag> synchronMOTagToRes(Page<MOTag> page);
	
	// 环境监控3D首次加载
	LinkedList<PerfSnapshotRoom> loadEnvDataBy3DResID(Map parmetermap);
	
	// 查询电子标签
	LinkedList<PerfSnapshotRoom> queryElectronicTag(Map parmetermap);
	
	UpAndDownThresholdBean queryRelationPath(Map conditionMap);
	LinkedList<UpAndDownThresholdBean> queryAlarmUpAndDownVlaue(Map parmetermap);
	// 查询上下限
	UpAndDownThresholdBean queryUpAndDownValue(Map parmetermap);
	UpAndDownThresholdBean UpAndDownValue(Map parmetermap);
	UpAndDownThresholdBean queryParentClassId(Map<String, Integer> conditionMap);
	UpAndDownThresholdBean UpAndDownValue2(Map parmetermap);
	UpAndDownThresholdBean parentUpAndDownValue(Map parmetermap);
	
	UpAndDownThresholdBean parentUpAndDownValue2(Map parmetermap);
	// 更新tag resID
	int updateTagResId(Map map);
	
	// 更新reader resID
	int updateReaderResId(Map map);
	
//	// 环境监控3D加载详情
//	LinkedList<PerfSnapshotRoom> loadEnvDetailBy3DResID(Map parmetermap);
	
	MOReader getMoReaderByMOID(int moID);
	
	MOTag getMOTagBYMOID(int moID);
	//根据ResID查询MO集合
	LinkedList<MOTag> queryRelationByResID(Map map);
	
	public List<MOTag> queryTemperatureTagList(Page<MOTag> page);
	
	public List<MOTag> queryWaterHoseTagList(Page<MOTag> page);
	
	public List<MOTag> queryTemperatureHumidityTagList(Page<MOTag> page);
	
	public List<MOTag> queryDoorMagneticTagList(Page<MOTag> page);
	
	public List<MOTag> queryDryContactTagList(Page<MOTag> page);
	
	List<Integer> getReadIDByMOID(int moID);
}
