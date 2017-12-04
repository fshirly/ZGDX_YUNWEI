package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;
import com.fable.insightview.monitor.deleteMonitorObject.mapper.DeleteDeviceMapper;
import com.fable.insightview.monitor.deleteMonitorObject.service.IDelCommon;
@Service("common_9")
public class DeleteVmObject extends DeleteObjectServiceImpl{

	@Autowired
	DeleteDeviceMapper deviceMapper;
	@Autowired
	IDelCommon delCommon;
	private static Map<String, Object> temp = new HashMap<String, Object>();
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
		 Map<String, Object> childMOIDMap = new HashMap<String, Object>();
		 childMOIDMap.put("MOCPUs", MOID);
		 childMOIDMap.put("MOStorage", MOID);
		 childMOIDMap.put("MOVolumes", MOID);
		 childMOIDMap.put("MOMemories", MOID);
		 childMOIDMap.put("MONetworkIf", MOID);
//		 Map<String, Object> temp = new HashMap<String, Object>();
		 for(String parmKey : childMOIDMap.keySet()){
			 parm = new HashMap<String, Object>();
			 parm.put("tableName", parmKey);
			 parm.put("MOID", childMOIDMap.get(parmKey));
			 List<Integer> childMoid = deviceMapper.queryChildMOID(parm);
			 if(childMoid.size()>0){
				 String moid = childMoid.toString().replace("[", "").replace("]", "");
				 temp.put(parmKey, moid);
			 }
		 }
		
		 /*// 删除性能数据
		Map<String, Object> hosPrefMap = new HashMap<String, Object>();
		hosPrefMap.put("PerfVmCPU", "MOCPUs");
		hosPrefMap.put("PerfVmDisk", "MOVolumes");
		hosPrefMap.put("PerfVmMemory", "MOMemories");
		hosPrefMap.put("PerfVmNetwork", "MONetworkIf");
		hosPrefMap.put("PerfVmDatastore", "MOStorage");
		for(String hostKey:hosPrefMap.keySet()){
			parm = new HashMap<String, Object>();
			parm.put("prefTableName", hostKey);
			for(String key : temp.keySet()){
				if(key.equals(hosPrefMap.get(hostKey))){
					parm.put("MOID",temp.get(key));
				}
			}
			if(parm.containsKey("MOID")){
				int j;
				do{
					j=deviceMapper.deletePrefData(parm);
				}while(j>0);
			}
		}
		// 删除告警数据
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		alarmMap.put("AlarmActiveDetail", MOID);
		alarmMap.put("AlarmHistoryDetail", MOID);
		alarmMap.put("AlarmOriginalEventDetail", MOID);
		alarmMap.put("MOKPIThreshold", MOID);
		for(String alarmKey : alarmMap.keySet()){
			parm = new HashMap<String, Object>();
			parm.put("alarmTableName", alarmKey);
			parm.put("MOID", MOID);
			 int k;
			 do{
				 k= deviceMapper.deleteAlarmDetail(parm);
			 }while(k>0);
		}
		*/
		// 删除任务性能表
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("SysPerfPollTask", MOID);
		taskMap.put("MOIPInfo", MOID);
		taskMap.put("MOVMInfo", MOID);
		 for(String taskKey : taskMap.keySet()){
			 	parm = new HashMap<String, Object>();
				parm.put("tableName", taskKey);
				parm.put("MOID", MOID);
				deviceMapper.deleteTaskAndIP(parm);
		 }
		// 删除该设备的子对象及其性能快照数据
			Map<String, Object> PrefMap = new HashMap<String, Object>();
			PrefMap.put("PerfSnapshotVM", MOID);
			PrefMap.put("MOCPUs", MOID);
			PrefMap.put("MOMemories", MOID);
			PrefMap.put("MONetworkIf", MOID);
			PrefMap.put("MOVolumes", MOID);
			PrefMap.put("MOStorage", MOID);
			for(String prefKey : PrefMap.keySet()){
				parm = new HashMap<String, Object>();
				parm.put("prefTableName", prefKey);
				parm.put("MOID", MOID);
				int w;
				do{
					w=deviceMapper.deletePrefSnapshotAndChild(parm);
				}while(w>0);
			}
	/*	 // 删除告警通知策略、告警视图定义、自动派单设置
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
		 //删除凭证
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
		
		// 删除性能数据
		Map<String, Object> hosPrefMap = new HashMap<String, Object>();
		hosPrefMap.put("PerfVmCPU", "MOCPUs");
		hosPrefMap.put("PerfVmDisk", "MOVolumes");
		hosPrefMap.put("PerfVmMemory", "MOMemories");
		hosPrefMap.put("PerfVmNetwork", "MONetworkIf");
		hosPrefMap.put("PerfVmDatastore", "MOStorage");
		for(String hostKey:hosPrefMap.keySet()){
			parm = new HashMap<String, Object>();
			parm.put("prefTableName", hostKey);
			for(String key : temp.keySet()){
				if(key.equals(hosPrefMap.get(hostKey))){
					parm.put("MOID",temp.get(key));
				}
			}
			if(parm.containsKey("MOID")){
				int j;
				do{
					j=deviceMapper.deletePrefData(parm);
				}while(j>0);
			}
		}
		// 删除告警数据
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		alarmMap.put("AlarmActiveDetail", MOID);
		alarmMap.put("AlarmHistoryDetail", MOID);
		alarmMap.put("AlarmOriginalEventDetail", MOID);
		alarmMap.put("MOKPIThreshold", MOID);
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
