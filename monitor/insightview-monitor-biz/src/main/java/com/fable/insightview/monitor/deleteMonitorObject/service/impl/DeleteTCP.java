package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.deleteMonitorObject.mapper.WebSiteDeleteMapper;
import com.fable.insightview.monitor.deleteMonitorObject.service.IDelCommon;

@Service("common_4")
public class DeleteTCP extends DeleteObjectServiceImpl{
	
	@Autowired
	WebSiteDeleteMapper webSiteMapper;
	@Autowired
	IDelCommon delCommon;
	
	@Override
	public boolean delete(Map<String, Object> map) {
		Map<String, Object> parm;
		Object MOID=null;
		for(String moid: map.keySet()){
			MOID = map.get(moid);
		}
	/*	// 删除性能表
		Map<String, Object> ftpPrefMap = new HashMap<String, Object>();
		ftpPrefMap.put("PerfSitePort", "MOSitePort");
		ftpPrefMap.put("PerfSnapshotSite", "MOSitePort");
		for(String prefKey :ftpPrefMap.keySet()){
			parm = new HashMap<String, Object>();
			parm.put("prefTableName", prefKey);
			parm.put("tableName", ftpPrefMap.get(prefKey));
			parm.putAll(map);
			int k;
			do{
				k= webSiteMapper.deletePrefAndSnap(parm);
			}while(k>0);
			
		}
		// 删除告警
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		alarmMap.put("AlarmActiveDetail", MOID);
		alarmMap.put("AlarmHistoryDetail", MOID);
		alarmMap.put("AlarmOriginalEventDetail", MOID);
		alarmMap.put("MOKPIThreshold", MOID);
		for(String alarmKey : alarmMap.keySet()){
			 parm = new HashMap<String, Object>();
			 parm.put("tableName", alarmKey);
			 parm.put("MOID", MOID);
			 int n;
				do{
					n= webSiteMapper.deleteWebSiteAlarm(parm);
				}while(n>0);
			 
		}
		
		// 删除告警通知策略、告警视图定义、自动派单设置
		Map<String, Object> alarmFilterMap = new HashMap<String, Object>();
		alarmFilterMap.put("AlarmNotifyFilter", MOID);
		alarmFilterMap.put("AlarmViewFilter", MOID);
		alarmFilterMap.put("AlarmDispatchFilter", MOID);
		for(String alarmFilterKey : alarmFilterMap.keySet()){
			 parm = new HashMap<String, Object>();
			 parm.put("tableName", alarmFilterKey);
			 parm.put("MOID", MOID);
			 int i;
				do{
					i= webSiteMapper.deleteAlarmFilter(parm);
				}while(i>0);
			 
		}*/
		// 根据子对象去获取对象的parentMoids
		Map<String, Object> deviceMap = new HashMap<String, Object>();
		deviceMap.put("tableName", "MOSitePort");
		deviceMap.put("MOID", MOID);
		List<Integer> parentMoids =  webSiteMapper.queryParentMOID(deviceMap);
		 String moids = parentMoids.toString().replace("[", "").replace("]", "");
		 
		// 删除子对象
		Map<String, Object> childMap = new HashMap<String, Object>();
		childMap.put("MOSitePort", MOID);
		childMap.put("SysPerfPollTask", MOID);
		for(String childKey : childMap.keySet()){
			 parm = new HashMap<String, Object>();
			 parm.put("tableName", childKey);
			 parm.put("MOID", MOID);
			 webSiteMapper.deleteChildDevice(parm);
		}
		int i=0;
		if(moids !=null &&  !"".equals(moids)){
			// 删除该对象
			parm = new HashMap<String, Object>();
			parm.put("MOID",moids);
			// 删除监测器模板
			webSiteMapper.deleteTemplate(parm);
			i =	webSiteMapper.deleteDevice(parm);
		}
		return (i>0)?true:false;
	}
	@Override
	public void deletePerfAndAlarm(Map<String, Object> map) {
		Map<String, Object> parm;
		Object MOID=null;
		for(String moid: map.keySet()){
			MOID = map.get(moid);
		}
		// 删除性能表
		Map<String, Object> ftpPrefMap = new HashMap<String, Object>();
		ftpPrefMap.put("PerfSitePort", "MOSitePort");
		ftpPrefMap.put("PerfSnapshotSite", "MOSitePort");
		for(String prefKey :ftpPrefMap.keySet()){
			parm = new HashMap<String, Object>();
			parm.put("prefTableName", prefKey);
			parm.put("tableName", ftpPrefMap.get(prefKey));
			parm.putAll(map);
			int k;
			do{
				k= webSiteMapper.deletePrefAndSnap(parm);
			}while(k>0);
			
		}
		// 删除告警
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		alarmMap.put("AlarmActiveDetail", MOID);
		alarmMap.put("AlarmHistoryDetail", MOID);
		alarmMap.put("AlarmOriginalEventDetail", MOID);
		alarmMap.put("MOKPIThreshold", MOID);
		for(String alarmKey : alarmMap.keySet()){
			 parm = new HashMap<String, Object>();
			 parm.put("tableName", alarmKey);
			 parm.put("MOID", MOID);
			 int n;
				do{
					n= webSiteMapper.deleteWebSiteAlarm(parm);
				}while(n>0);
			 
		}
		
		// 删除告警通知策略、告警视图定义、自动派单设置
		Map<String, Object> alarmFilterMap = new HashMap<String, Object>();
		alarmFilterMap.put("AlarmNotifyFilter", MOID);
		alarmFilterMap.put("AlarmViewFilter", MOID);
		alarmFilterMap.put("AlarmDispatchFilter", MOID);
		for(String alarmFilterKey : alarmFilterMap.keySet()){
			 parm = new HashMap<String, Object>();
			 parm.put("tableName", alarmFilterKey);
			 parm.put("MOID", MOID);
			 int i;
				do{
					i= webSiteMapper.deleteAlarmFilter(parm);
				}while(i>0);
			 
		}
		
		//删除告警重定义
		delCommon.delAlarmEventRedefine(MOID.toString());
		
	}

}
