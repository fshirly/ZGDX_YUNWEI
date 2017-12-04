package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;
import com.fable.insightview.monitor.deleteMonitorObject.mapper.DeleteDBserviceMapper;
import com.fable.insightview.monitor.deleteMonitorObject.service.IDelCommon;
@Service("common_54")
public class DeleteDB2Instance extends DeleteObjectServiceImpl{
	@Autowired
	DeleteDBserviceMapper dbserviceMapper;
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
		List<ServiceBean> DbList =  dbserviceMapper.queryDBInfo(parm);
		for (ServiceBean device : DbList) {
			parm.put("MOName", device.getMOName());
			parm.put("IP", device.getIp());
			dbserviceMapper.deleteDiscoverDBInfo(parm);
		}
		
		// 查询MODB2Instance的MOID
		 String moids = "";
		 Map<String, Object> childMOIDMap = new HashMap<String, Object>();
		 childMOIDMap.put("MODB2Instance", MOID);
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
		
		 // 查询出MODB2Database中moid
	   if(moids !=null &&  !"".equals(moids)){
			 String dbDataBasemoids = "";
			 Map<String, Object> dbMOIDMap = new HashMap<String, Object>();
			 dbMOIDMap.put("MODB2Database", moids);
			 Map<String, Object> dbtemp = new HashMap<String, Object>();
			 for(String dbKey : dbMOIDMap.keySet()){
				 parm = new HashMap<String, Object>();
				 parm.put("tableName", dbKey);
				 parm.put("MOID", dbMOIDMap.get(dbKey));
				 List<Integer> dbMoid = dbserviceMapper.queryDB2DataBaseMOID(parm);
				 if(dbMoid.size()>0){
					 dbDataBasemoids = dbMoid.toString().replace("[", "").replace("]", "");
					 dbtemp.put(dbKey, dbDataBasemoids);
				 }
			 }
		// 删除告警数据
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		alarmMap.put("AlarmActiveDetail", moids);
		alarmMap.put("AlarmHistoryDetail", moids);
		alarmMap.put("AlarmOriginalEventDetail", moids);
		alarmMap.put("MOKPIThreshold", moids);
		for(String alarmKey : alarmMap.keySet()){
			parm = new HashMap<String, Object>();
			parm.put("alarmTableName", alarmKey);
			if(dbDataBasemoids !=null && !"".equals(dbDataBasemoids)){
				parm.put("MOID", moids+","+dbDataBasemoids);
			}else{
				parm.put("MOID", moids);
			}
			 int k;
			 do{
				 k= dbserviceMapper.deleteAlarmDetail(parm);
			 }while(k>0);
			
		}
		// 删除该设备的性能快照数据
			parm = new HashMap<String, Object>();
			parm.put("prefTableName", "PerfSnapshotDatabase");
			if(dbDataBasemoids !=null && !"".equals(dbDataBasemoids)){
				parm.put("MOID", moids+","+dbDataBasemoids);
			}else{
				parm.put("MOID", moids);
			}
			int m;
			do{
				m=dbserviceMapper.deletePrefSnapshot(parm);
			}while(m>0);
		
		// 删除任务性能表
		Map<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("SysPerfPollTask", MOID);
		 for(String taskKey : taskMap.keySet()){
			 	parm = new HashMap<String, Object>();
				parm.put("tableName", taskKey);
				parm.put("MOID", MOID);
				dbserviceMapper.deleteTaskAndvars(parm);
		 }
		 
		 if(dbDataBasemoids !=null && !"".equals(dbDataBasemoids) ){
			 // 删除该设备的子对象
			 parm = new HashMap<String, Object>();
			 parm.put("tableName", "MODBTableSpace");
			 parm.put("MOID", dbDataBasemoids);
			 dbserviceMapper.deleteOralceOrDB2Child(parm);
			 
			 parm = new HashMap<String, Object>();
			 parm.put("tableName", "MODB2BufferPool");
			 parm.put("MOID", dbDataBasemoids);
			 dbserviceMapper.deleteOralceOrDB2Child(parm);
		 }
			
			
			// 删除子对象MODB2Database
			parm = new HashMap<String, Object>();
			parm.put("tableName", "MODB2Database");
			parm.put("MOID", moids);
			dbserviceMapper.deleteSGAOrDB2(parm);
				//删除凭证
				parm = new HashMap<String, Object>();
				parm.put("snmpTableName", "SysDBMSCommunity");
				parm.put("tableName", "MODB2Instance");
				parm.put("DBtype", "DB2");
				parm.put("MOID", MOID);
				dbserviceMapper.deleteSNMP(parm);
		// 删除告警通知策略、告警视图定义、自动派单设置
		 Map<String, Object> alarmFiterMap = new HashMap<String, Object>();
		 alarmFiterMap.put("AlarmNotifyFilter", moids);
		 alarmFiterMap.put("AlarmViewFilter", moids);
		 alarmFiterMap.put("AlarmDispatchFilter", moids);
		 for(String fiterKey : alarmFiterMap.keySet()){
			 parm = new HashMap<String, Object>();
				parm.put("tableName", fiterKey);
				if(dbDataBasemoids !=null && !"".equals(dbDataBasemoids)){
					parm.put("MOID", moids+","+dbDataBasemoids);
				}else{
					parm.put("MOID", moids);
				}
				 int n;
				 do{
					n =  dbserviceMapper.deleteAlarmFilter(parm);
				 }while(n>0);
		 }
		 
		//删除告警重定义
		 delCommon.delAlarmEventRedefine(MOID.toString());
	}
		 // 删除该对象
		 parm = new HashMap<String, Object>();
		 parm.put("tableName", "MODB2Instance");
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
		 
	}

}
