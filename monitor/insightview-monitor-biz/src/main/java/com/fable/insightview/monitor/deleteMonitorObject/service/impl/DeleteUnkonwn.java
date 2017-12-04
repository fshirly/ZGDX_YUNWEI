package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;
import com.fable.insightview.monitor.deleteMonitorObject.mapper.DeleteDeviceMapper;
import com.fable.insightview.monitor.deleteMonitorObject.service.IDelCommon;
@Service("common_-1")
public class DeleteUnkonwn extends DeleteObjectServiceImpl {

	@Autowired
	DeleteDeviceMapper deviceMapper;
	@Autowired
	IDelCommon delCommon;
	
	@Override
	public boolean delete(Map<String, Object> map) {
		Map<String, Object> parm;
		Object MOID=null;
		for(String moid: map.keySet()){
			MOID = map.get(moid);
		}
		
		// 删除SysNetworkDiscoverTask表
		parm = new HashMap<String, Object>();
		parm.put("MOID", MOID);
		List<ServiceBean> deviceList =  deviceMapper.queryDeviceInfo(parm);
		for (ServiceBean device : deviceList) {
			parm.put("MOName", device.getMOName());
			parm.put("IP", device.getIp());
			deviceMapper.deleteDiscoverTask(parm);
		}
		
		// 删除任务性能表
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("PerfDeviceAvailability", MOID);
		taskMap.put("SysPerfPollTask", MOID);
		taskMap.put("MOIPInfo", MOID);
		 for(String taskKey : taskMap.keySet()){
			 	parm = new HashMap<String, Object>();
				parm.put("tableName", taskKey);
				parm.put("MOID", MOID);
				deviceMapper.deleteTaskAndIP(parm);
		 }
		 
		/* // 性能快照表
		 Map<String, Object> PrefMap = new HashMap<String, Object>();
		 PrefMap.put("prefTableName", "PerfSnapshotHost");
		 PrefMap.put("MOID", MOID);
		 int w;
			do{
				w=deviceMapper.deletePrefSnapshotAndChild(PrefMap);
			}while(w>0);
		 
			// 删除告警数据
			Map<String, Object> alarmMap = new HashMap<String, Object>();
			alarmMap.put("AlarmActiveDetail", MOID);
			alarmMap.put("AlarmHistoryDetail", MOID);
			alarmMap.put("AlarmOriginalEventDetail", MOID);
			for(String alarmKey : alarmMap.keySet()){
				parm = new HashMap<String, Object>();
				parm.put("alarmTableName", alarmKey);
				parm.put("MOID", MOID);
				 int k;
				 do{
					 k= deviceMapper.deleteAlarmDetail(parm);
				 }while(k>0);
			}
			
		 // 删除告警通知策略、告警视图定义、自动派单设置
		 Map<String, Object> alarmFiterMap = new HashMap<String, Object>();
		 alarmFiterMap.put("AlarmNotifyFilter", MOID);
		 alarmFiterMap.put("AlarmViewFilter", MOID);
		 alarmFiterMap.put("AlarmDispatchFilter", MOID);
		 for(String fiterKey : alarmFiterMap.keySet()){
			 parm = new HashMap<String, Object>();
				parm.put("tableName", fiterKey);
				parm.put("MOID", MOID);
				 int n;
				 do{
					n =  deviceMapper.deleteAlarmFilter(parm);
				 }while(n>0);
		 }*/
		 
		// 删除凭证
		 Map<String, Object> snmpMap = new HashMap<String, Object>();
		 snmpMap.put("SNMPCommunityCache", MOID);
		 snmpMap.put("SysVMIfCommunity", MOID);
		 for(String smpKey : snmpMap.keySet()){
			 parm = new HashMap<String, Object>();
				parm.put("tableName", smpKey);
				parm.put("MOID", snmpMap.get(smpKey));
			 deviceMapper.deleteVhostAndVMSNMP(parm);
		 }
		 
		 // 删除监测器模板
		 deviceMapper.deleteTemplate(map);
		 
		 int i =deviceMapper.deleteMODevice(map);
		 return (i>0)?true:false;
	}

	@Override
	public void deletePerfAndAlarm(Map<String, Object> map) {
		Map<String, Object> parm;
		Object MOID=null;
		for(String moid: map.keySet()){
			MOID = map.get(moid);
		}
		 // 性能快照表
		 Map<String, Object> PrefMap = new HashMap<String, Object>();
		 PrefMap.put("prefTableName", "PerfSnapshotHost");
		 PrefMap.put("MOID", MOID);
		 int w;
			do{
				w=deviceMapper.deletePrefSnapshotAndChild(PrefMap);
			}while(w>0);
		 
			// 删除告警数据
			Map<String, Object> alarmMap = new HashMap<String, Object>();
			alarmMap.put("AlarmActiveDetail", MOID);
			alarmMap.put("AlarmHistoryDetail", MOID);
			alarmMap.put("AlarmOriginalEventDetail", MOID);
			for(String alarmKey : alarmMap.keySet()){
				parm = new HashMap<String, Object>();
				parm.put("alarmTableName", alarmKey);
				parm.put("MOID", MOID);
				 int k;
				 do{
					 k= deviceMapper.deleteAlarmDetail(parm);
				 }while(k>0);
			}
			
		 // 删除告警通知策略、告警视图定义、自动派单设置
		 Map<String, Object> alarmFiterMap = new HashMap<String, Object>();
		 alarmFiterMap.put("AlarmNotifyFilter", MOID);
		 alarmFiterMap.put("AlarmViewFilter", MOID);
		 alarmFiterMap.put("AlarmDispatchFilter", MOID);
		 for(String fiterKey : alarmFiterMap.keySet()){
			 parm = new HashMap<String, Object>();
				parm.put("tableName", fiterKey);
				parm.put("MOID", MOID);
				 int n;
				 do{
					n =  deviceMapper.deleteAlarmFilter(parm);
				 }while(n>0);
		 }
		 
		//删除告警重定义
		 delCommon.delAlarmEventRedefine(MOID.toString());
		
	}

}
