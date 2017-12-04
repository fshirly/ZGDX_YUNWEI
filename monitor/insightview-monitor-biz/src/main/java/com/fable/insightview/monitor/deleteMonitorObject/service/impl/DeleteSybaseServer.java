package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;
import com.fable.insightview.monitor.deleteMonitorObject.mapper.DeleteDBserviceMapper;
import com.fable.insightview.monitor.deleteMonitorObject.service.IDelCommon;
@Service("common_81")
public class DeleteSybaseServer extends DeleteObjectServiceImpl{
	@Autowired
	DeleteDBserviceMapper dbserviceMapper;
	@Autowired
	IDelCommon delCommon;
	
	private String moids="";
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
		List<ServiceBean> DbList =  dbserviceMapper.queryDBInfo(parm);
		for (ServiceBean device : DbList) {
			parm.put("MOName", device.getMOName());
			parm.put("IP", device.getIp());
			dbserviceMapper.deleteDiscoverDBInfo(parm);
		}
		
		//删除告警重定义
		delCommon.delAlarmEventRedefine(MOID.toString());
		
		// 查询MOMsSQLServer的MOID
//		 String moids = "";
		 Map<String, Object> childMOIDMap = new HashMap<String, Object>();
		 childMOIDMap.put("MOSybaseServer", MOID);
		 Map<String, Object> temp = new HashMap<String, Object>();
		 for(String parmKey : childMOIDMap.keySet()){
			 parm = new HashMap<String, Object>();
			 parm.put("tableName", parmKey);
			 parm.put("MOID", childMOIDMap.get(parmKey));
			 List<Integer> childMoid = dbserviceMapper.queryChildMOID(parm);
			 if(childMoid.size()>0){
				   moids = childMoid.toString().replace("[", "").replace("]", "");
				 temp.put(parmKey, moids);
			 }
		 }
		 if(moids !=null && !"".equals(moids)){
	/*	// 删除告警数据
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		alarmMap.put("AlarmActiveDetail", moids);
		alarmMap.put("AlarmHistoryDetail", moids);
		alarmMap.put("AlarmOriginalEventDetail", moids);
		alarmMap.put("MOKPIThreshold", moids);
		for(String alarmKey : alarmMap.keySet()){
			parm = new HashMap<String, Object>();
			parm.put("alarmTableName", alarmKey);
			parm.put("MOID", moids);
			 int k;
			 do{
				 k= dbserviceMapper.deleteAlarmDetail(parm);
			 }while(k>0);
			
		}
		// 删除该设备的性能快照数据
			parm = new HashMap<String, Object>();
			parm.put("prefTableName", "PerfSnapshotDatabase");
			parm.put("MOID", moids);
			int m;
			do{
				m=dbserviceMapper.deletePrefSnapshot(parm);
			}while(m>0);*/
		
		// 删除任务性能表
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("SysPerfPollTask", MOID);
		 for(String taskKey : taskMap.keySet()){
			 	parm = new HashMap<String, Object>();
				parm.put("tableName", taskKey);
				parm.put("MOID", MOID);
				dbserviceMapper.deleteTaskAndvars(parm);
		 }
		 		 
		// 删除该设备的子对象
			Map<String, Object> PrefMap = new HashMap<String, Object>();
			PrefMap.put("MOSybaseDevice", moids);
			PrefMap.put("MOSybaseDatabase", moids);
			for(String prefKey : PrefMap.keySet()){
				parm = new HashMap<String, Object>();
				parm.put("tableName", prefKey);
				parm.put("MOID", moids);
				int w;
				do{
					w=dbserviceMapper.deleteChildDevice(parm);
				}while(w>0);
			}
				//删除凭证
				parm = new HashMap<String, Object>();
				parm.put("snmpTableName", "SysDBMSCommunity");
				parm.put("tableName", "MOSybaseServer");
				parm.put("DBtype", "Sybase");
				parm.put("MOID", MOID);
				dbserviceMapper.deleteSNMP(parm);
		/* // 删除告警通知策略、告警视图定义、自动派单设置
		 Map<String, Object> alarmFiterMap = new HashMap<String, Object>();
		 alarmFiterMap.put("AlarmNotifyFilter", moids);
		 alarmFiterMap.put("AlarmViewFilter", moids);
		 alarmFiterMap.put("AlarmDispatchFilter", moids);
		 for(String fiterKey : alarmFiterMap.keySet()){
			 parm = new HashMap<String, Object>();
				parm.put("tableName", fiterKey);
				parm.put("MOID", moids);
				 int n;
				 do{
					n =  dbserviceMapper.deleteAlarmFilter(parm);
				 }while(n>0);
		 }*/
		} 
		 // 删除该对象
		 parm = new HashMap<String, Object>();
		 parm.put("tableName", "MOSybaseServer");
		 parm.put("MOID", MOID);
		 dbserviceMapper.deleteMODevice(parm);
		 
		 // 删除监测器模板
		 dbserviceMapper.deleteTemplate(map);
		 
		 // 删除DBMS对象
		int i =  dbserviceMapper.deleteDBMSservice(map);
		return (i>0)?true:false;
	}
	@Override
	public void deletePerfAndAlarm(Map<String, Object> map) {
		Map<String, Object> parm;
		 
		 if(null !=moids && !"".equals(moids)){
			 // 删除告警数据
			 Map<String, Object> alarmMap = new HashMap<String, Object>();
			 alarmMap.put("AlarmActiveDetail", moids);
			 alarmMap.put("AlarmHistoryDetail", moids);
			 alarmMap.put("AlarmOriginalEventDetail", moids);
			 alarmMap.put("MOKPIThreshold", moids);
			 for(String alarmKey : alarmMap.keySet()){
				 parm = new HashMap<String, Object>();
				 parm.put("alarmTableName", alarmKey);
				 parm.put("MOID", moids);
				 int k;
				 do{
					 k= dbserviceMapper.deleteAlarmDetail(parm);
				 }while(k>0);
				 
			 }
			 // 删除该设备的性能快照数据
			 parm = new HashMap<String, Object>();
			 parm.put("prefTableName", "PerfSnapshotDatabase");
			 parm.put("MOID", moids);
			 int m;
			 do{
				 m=dbserviceMapper.deletePrefSnapshot(parm);
			 }while(m>0);
			
			 // 删除告警通知策略、告警视图定义、自动派单设置
			 Map<String, Object> alarmFiterMap = new HashMap<String, Object>();
			 alarmFiterMap.put("AlarmNotifyFilter", moids);
			 alarmFiterMap.put("AlarmViewFilter", moids);
			 alarmFiterMap.put("AlarmDispatchFilter", moids);
			 for(String fiterKey : alarmFiterMap.keySet()){
				 parm = new HashMap<String, Object>();
					parm.put("tableName", fiterKey);
					parm.put("MOID", moids);
					 int n;
					 do{
						n =  dbserviceMapper.deleteAlarmFilter(parm);
					 }while(n>0);
			 }
		 }
		 
	}

}
