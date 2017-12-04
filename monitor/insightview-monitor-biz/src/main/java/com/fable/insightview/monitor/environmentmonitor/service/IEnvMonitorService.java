package com.fable.insightview.monitor.environmentmonitor.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.environmentmonitor.entity.MOReader;
import com.fable.insightview.monitor.environmentmonitor.entity.MOTag;
import com.fable.insightview.monitor.environmentmonitor.entity.MOZoneManagerBean;
import com.fable.insightview.monitor.environmentmonitor.entity.PerfSnapshotRoom;
import com.fable.insightview.monitor.environmentmonitor.entity.UpAndDownThresholdBean;
import com.fable.insightview.platform.page.Page;

public interface IEnvMonitorService {
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
	
	LinkedList<UpAndDownThresholdBean> queryAlarmUpAndDownVlaue(Map parmetermap);
	UpAndDownThresholdBean queryRelationPath(Map conditionMap);
	// 查询上下限
	UpAndDownThresholdBean queryUpAndDownValue(Map parmetermap);
	
	UpAndDownThresholdBean UpAndDownValue(Map parmetermap);
	
	UpAndDownThresholdBean UpAndDownValue2(Map parmetermap);
	
	UpAndDownThresholdBean queryParentClassId(Map<String, Integer> conditionMap);
	UpAndDownThresholdBean parentUpAndDownValue(Map parmetermap);
	
	UpAndDownThresholdBean parentUpAndDownValue2(Map parmetermap);
	// 更新resID
	int updateTagResId(Map map);
	
	// 更新 reader resID
	int updateReaderResId(Map map);
	
	/*根据moid获得阅读器信息*/
	MOReader getMoReaderByMOID(int moID);
	 
	MOTag getMOTagBYMOID(int moID);
	
	LinkedList<MOTag> queryRelationByResID(Map paramMap);
	
	public List<MOTag> queryTemperatureTagList(Page<MOTag> page);
	
	public List<MOTag> queryWaterHoseTagList(Page<MOTag> page);
	
	public List<MOTag> queryTemperatureHumidityTagList(Page<MOTag> page);
	
	public List<MOTag> queryDoorMagneticTagList(Page<MOTag> page);
	
	public List<MOTag> queryDryContactTagList(Page<MOTag> page);
}
