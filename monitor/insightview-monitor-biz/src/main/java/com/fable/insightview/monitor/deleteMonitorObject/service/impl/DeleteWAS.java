package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;
import com.fable.insightview.monitor.deleteMonitorObject.mapper.DeleteMiddelWareMapper;
import com.fable.insightview.monitor.deleteMonitorObject.service.IDelCommon;

@Service("common_19")
public class DeleteWAS extends DeleteObjectServiceImpl {

	@Autowired
	DeleteMiddelWareMapper middelWareMapper;
	@Autowired
	IDelCommon delCommon;

	@Override
	public boolean delete(Map<String, Object> map) {
		Map<String, Object> parm;
		Object MOID = null;
		for (String moid : map.keySet()) {
			MOID = map.get(moid);
		}

		// 删除SysNetworkDiscoverTask表
		parm = new HashMap<String, Object>();
		parm.put("MOID", MOID);
		List<ServiceBean> middleWareList = middelWareMapper
				.queryMiddleWareInfo(parm);
		for (ServiceBean device : middleWareList) {
			parm.put("MOName", device.getMOName());
			parm.put("IP", device.getIp());
			middelWareMapper.deleteDiscoveMiddleWare(parm);
		}

		/*
		 * // 删除快照表 Map<String, Object> snapAndTaskMap = new HashMap<String,
		 * Object>(); snapAndTaskMap.put("PerfSnapshotMiddleware",MOID); for
		 * (String snapKey : snapAndTaskMap.keySet()) { parm = new
		 * HashMap<String, Object>(); parm.put("prefTableName", snapKey);
		 * parm.put("MOID", snapAndTaskMap.get(snapKey)); int i; do{
		 * i=middelWareMapper.deleteSnapshot(parm); }while(i>0); }
		 * 
		 * // 删除告警 Map<String, Object> alarmMap = new HashMap<String, Object>();
		 * alarmMap.put("AlarmActiveDetail", MOID);
		 * alarmMap.put("AlarmHistoryDetail", MOID);
		 * alarmMap.put("AlarmOriginalEventDetail", MOID);
		 * alarmMap.put("MOKPIThreshold", MOID); for(String alarmKey :
		 * alarmMap.keySet()){ parm = new HashMap<String, Object>();
		 * parm.put("alarmTableName", alarmKey); parm.put("MOID", MOID); int k;
		 * do{ k = middelWareMapper.deleteAlarmDetail(parm); }while(k>0); }
		 * 
		 * // 删除告警通知策略、告警视图定义、自动派单设置 Map<String, Object> alarmFilterMap = new
		 * HashMap<String, Object>(); alarmFilterMap.put("AlarmNotifyFilter",
		 * MOID); alarmFilterMap.put("AlarmViewFilter", MOID);
		 * alarmFilterMap.put("AlarmDispatchFilter", MOID); for(String
		 * alarmFilterKey : alarmFilterMap.keySet()){ parm = new HashMap<String,
		 * Object>(); parm.put("tableName", alarmFilterKey); parm.put("MOID",
		 * MOID); int m; do{ m = middelWareMapper.deleteAlarmFilter(parm);
		 * }while(m>0);
		 * 
		 * }
		 */
		// 查询MOMiddleWareJ2eeApp的MOID
		parm = new HashMap<String, Object>();
		parm.put("tableName", "MOMiddleWareJ2eeApp");
		parm.put("MOID", MOID);
		List<Integer> j2eeMOIDList = middelWareMapper.queryJ2eeAppMOID(parm);
		String j2eeMOID = j2eeMOIDList.toString().replace("[", "")
				.replace("]", "");

		if (j2eeMOID != null && !"".equals(j2eeMOID)) {
			// 查询MOMiddleWareWebModule的MOID
			parm = new HashMap<String, Object>();
			parm.put("tableName", "MOMiddleWareWebModule");
			parm.put("MOID", j2eeMOID);
			List<Integer> webModubleMOIDList = middelWareMapper
					.queryJ2eeAppMOID(parm);
			String webModubleMOID = webModubleMOIDList.toString()
					.replace("[", "").replace("]", "");

			if (webModubleMOID != null && !"".equals(webModubleMOID)) {
				// 删除MOMiddleWareWebModule的子对象
				parm = new HashMap<String, Object>();
				parm.put("tableName", "MOMiddleWareServlet");
				parm.put("MOID", webModubleMOID);
				middelWareMapper.deleteMiddelWareChildDevice(parm);
			}
			// 删除MOMiddleWareJ2eeApp的子对象
			Map<String, Object> j2eechildMap = new HashMap<String, Object>();
			j2eechildMap.put("MOMiddleEJBModule", j2eeMOID);
			j2eechildMap.put("MOMiddleWareWebModule", j2eeMOID);
			for (String j2eechildKey : j2eechildMap.keySet()) {
				parm = new HashMap<String, Object>();
				parm.put("tableName", j2eechildKey);
				parm.put("MOID", j2eechildMap.get(j2eechildKey));
				middelWareMapper.deleteMiddelWareChildDevice(parm);
			}
		}

		// 删除子对象
		Map<String, Object> childMap = new HashMap<String, Object>();
		childMap.put("MOMiddleWareJTA", MOID);
		childMap.put("MOMiddleWareJVM", MOID);
		childMap.put("MOMiddleWareServlet", MOID);
		childMap.put("MOMiddleWareThreadPool", MOID);
		childMap.put("MOMiddleWareJ2eeApp", MOID);
		childMap.put("MOMiddleWareJdbcDS", MOID);
		childMap.put("MOMiddleWareJdbcPool", MOID);
		childMap.put("MOMiddleWareJMS", MOID);
		for (String childKey : childMap.keySet()) {
			parm = new HashMap<String, Object>();
			parm.put("tableName", childKey);
			parm.put("MOID", MOID);
			middelWareMapper.deleteMiddelWareChildDevice(parm);
		}

		// 删除采集任务表
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("tableName", "SysPerfPollTask");
		taskMap.put("MOID", MOID);
		middelWareMapper.deletetask(taskMap);
		/**
		 * 删除凭证
		 */
		parm = new HashMap<String, Object>();
		parm.put("wareType", "websphere");
		parm.put("MOID", MOID);
		middelWareMapper.deleteSNMP(parm);
		// 删除监测器模板
		middelWareMapper.deleteTemplate(map);
		// 删除该对象
		int i = middelWareMapper.deleteDevice(map);
		return (i > 0) ? true : false;
	}

	@Override
	public void deletePerfAndAlarm(Map<String, Object> map) {
		Map<String, Object> parm;
		Object MOID = null;
		for (String moid : map.keySet()) {
			MOID = map.get(moid);
		}
		// 删除快照表
		Map<String, Object> snapAndTaskMap = new HashMap<String, Object>();
		snapAndTaskMap.put("PerfSnapshotMiddleware", MOID);
		for (String snapKey : snapAndTaskMap.keySet()) {
			parm = new HashMap<String, Object>();
			parm.put("prefTableName", snapKey);
			parm.put("MOID", snapAndTaskMap.get(snapKey));
			int i;
			do {
				i = middelWareMapper.deleteSnapshot(parm);
			} while (i > 0);
		}

		// 删除告警
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		alarmMap.put("AlarmActiveDetail", MOID);
		alarmMap.put("AlarmHistoryDetail", MOID);
		alarmMap.put("AlarmOriginalEventDetail", MOID);
		alarmMap.put("MOKPIThreshold", MOID);
		for (String alarmKey : alarmMap.keySet()) {
			parm = new HashMap<String, Object>();
			parm.put("alarmTableName", alarmKey);
			parm.put("MOID", MOID);
			int k;
			do {
				k = middelWareMapper.deleteAlarmDetail(parm);
			} while (k > 0);
		}

		// 删除告警通知策略、告警视图定义、自动派单设置
		Map<String, Object> alarmFilterMap = new HashMap<String, Object>();
		alarmFilterMap.put("AlarmNotifyFilter", MOID);
		alarmFilterMap.put("AlarmViewFilter", MOID);
		alarmFilterMap.put("AlarmDispatchFilter", MOID);
		for (String alarmFilterKey : alarmFilterMap.keySet()) {
			parm = new HashMap<String, Object>();
			parm.put("tableName", alarmFilterKey);
			parm.put("MOID", MOID);
			int m;
			do {
				m = middelWareMapper.deleteAlarmFilter(parm);
			} while (m > 0);

		}

		//删除告警重定义
		delCommon.delAlarmEventRedefine(MOID.toString());
	}

}
