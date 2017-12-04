package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.deleteMonitorObject.mapper.DeleteDeviceMapper;
import com.fable.insightview.monitor.deleteMonitorObject.service.IDelCommon;

@Service("common_73")
public class DeleteUPS extends DeleteObjectServiceImpl {

	@Autowired
	DeleteDeviceMapper deviceMapper;
	@Autowired
	IDelCommon delCommon;

	@Override
	public boolean delete(Map<String, Object> map) {
		Map<String, Object> parm;
		Object MOID = null;
		for (String moid : map.keySet()) {
			MOID = map.get(moid);
		}

		// 删除任务性能表
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("PerfRoomEastUps", MOID);
		taskMap.put("PerfRoomInvtUps", MOID);
		taskMap.put("SysPerfPollTask", MOID);
		taskMap.put("MOIPInfo", MOID);
		taskMap.put("SNMPCommunityCache", MOID);
		for (String taskKey : taskMap.keySet()) {
			parm = new HashMap<String, Object>();
			parm.put("tableName", taskKey);
			parm.put("MOID", MOID);
			deviceMapper.deleteTaskAndIP(parm);
		}

		/*
		 * // 删除告警数据 Map<String, Object> alarmMap = new HashMap<String,
		 * Object>(); alarmMap.put("AlarmActiveDetail", MOID);
		 * alarmMap.put("AlarmHistoryDetail", MOID);
		 * alarmMap.put("AlarmOriginalEventDetail", MOID); for(String alarmKey :
		 * alarmMap.keySet()){ parm = new HashMap<String, Object>();
		 * parm.put("alarmTableName", alarmKey); parm.put("MOID", MOID); int k;
		 * do{ k= deviceMapper.deleteAlarmDetail(parm); }while(k>0); }
		 * 
		 * // 删除告警通知策略、告警视图定义、自动派单设置 Map<String, Object> alarmFiterMap = new
		 * HashMap<String, Object>(); alarmFiterMap.put("AlarmNotifyFilter",
		 * MOID); alarmFiterMap.put("AlarmViewFilter", MOID);
		 * alarmFiterMap.put("AlarmDispatchFilter", MOID); for(String fiterKey :
		 * alarmFiterMap.keySet()){ parm = new HashMap<String, Object>();
		 * parm.put("tableName", fiterKey); parm.put("MOID", MOID); int n; do{ n
		 * = deviceMapper.deleteAlarmFilter(parm); }while(n>0); }
		 */
		// 删除监测器模板
		deviceMapper.deleteTemplate(map);

		int i = deviceMapper.deleteMODevice(map);
		return (i > 0) ? true : false;
	}

	@Override
	public void deletePerfAndAlarm(Map<String, Object> map) {
		Map<String, Object> parm;
		Object MOID = null;
		for (String moid : map.keySet()) {
			MOID = map.get(moid);
		}
		// 删除告警数据
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		alarmMap.put("AlarmActiveDetail", MOID);
		alarmMap.put("AlarmHistoryDetail", MOID);
		alarmMap.put("AlarmOriginalEventDetail", MOID);
		for (String alarmKey : alarmMap.keySet()) {
			parm = new HashMap<String, Object>();
			parm.put("alarmTableName", alarmKey);
			parm.put("MOID", MOID);
			int k;
			do {
				k = deviceMapper.deleteAlarmDetail(parm);
			} while (k > 0);
		}

		// 删除告警通知策略、告警视图定义、自动派单设置
		Map<String, Object> alarmFiterMap = new HashMap<String, Object>();
		alarmFiterMap.put("AlarmNotifyFilter", MOID);
		alarmFiterMap.put("AlarmViewFilter", MOID);
		alarmFiterMap.put("AlarmDispatchFilter", MOID);
		for (String fiterKey : alarmFiterMap.keySet()) {
			parm = new HashMap<String, Object>();
			parm.put("tableName", fiterKey);
			parm.put("MOID", MOID);
			int n;
			do {
				n = deviceMapper.deleteAlarmFilter(parm);
			} while (n > 0);
		}

		// 删除告警重定义
		delCommon.delAlarmEventRedefine(MOID.toString());

	}

}
