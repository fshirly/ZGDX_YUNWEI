package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;
import com.fable.insightview.monitor.deleteMonitorObject.mapper.DeleteReaderMapper;

@Service("common_44")
public class DeleteZoneManger extends DeleteObjectServiceImpl{
	
	@Autowired
	DeleteReaderMapper  readerMapper;
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
		List<ServiceBean> middleWareList =  readerMapper.queryReaderInfo(parm);
		for (ServiceBean device : middleWareList) {
			parm.put("MOName", device.getMOName());
			parm.put("IP", device.getIp());
			readerMapper.deleteReaderDiscoverTask(parm);
		}
		
		/*// 删除性能快照
			parm = new HashMap<String, Object>();
			parm.put("prefTableName", "PerfSnapshotRoom");
			parm.put("MOID", MOID);
			int n;
			do{
				n = readerMapper.deleteReaderSnapshot(parm);
			}while(n>0);
		// 删除告警
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
					k =  readerMapper.deleteAlarmDetail(parm);
				}while(k>0);
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
			 int m;
				do{
					m =  readerMapper.deleteAlarmFilter(parm);
				}while(m>0);
			 
		}*/
		// 删除性能任务表
		 parm = new HashMap<String, Object>();
		 parm.put("tableName", "SysPerfPollTask");
		 parm.put("MOID", MOID);
		 readerMapper.deleteTask(parm);
		 
		 // 查询阅读器的MOID
		 List<Integer> resderMOIDList =readerMapper.queryReaderMOID(map);
		 String readerMOID = resderMOIDList.toString().replace("[", "").replace("]", "");
		 
		 if(readerMOID !=null &&  !"".equals(readerMOID)){
			 //删除电子标签子对象
			 parm = new HashMap<String, Object>();
			 parm.put("tableName", "MOTag");
			 parm.put("MOID", readerMOID);
			 readerMapper.deleteMOTag(parm);
		 }
		 
		// 删除阅读器子对象
		 parm = new HashMap<String, Object>();
		 parm.put("tableName", "MOReader");
		 parm.put("MOID", MOID);
		 readerMapper.deleteMOTag(parm);
		 
		// 删除凭证
		 parm = new HashMap<String, Object>();
		 parm.put("snmptableName", "SysRoomCommunity");
		 parm.put("tableName", "MOZoneManager");
		 parm.put("MOID", MOID);
		 readerMapper.deleteSNMP(parm);
		 // 删除监测器模板
		 readerMapper.deleteTemplate(map);
		 // 删除该动环系统对象
		 parm = new HashMap<String, Object>();
		 parm.put("tableName", "MOZoneManager");
		 parm.put("MOID", MOID);
		 int i = readerMapper.deleteMODevice(parm);
		return (i>0)?true:false;
	}
	@Override
	public void deletePerfAndAlarm(Map<String, Object> map) {
		Map<String, Object> parm;
		Object MOID=null;
		for(String moid: map.keySet()){
			MOID = map.get(moid);
		}
		// 删除性能快照
			parm = new HashMap<String, Object>();
			parm.put("prefTableName", "PerfSnapshotRoom");
			parm.put("MOID", MOID);
			int n;
			do{
				n = readerMapper.deleteReaderSnapshot(parm);
			}while(n>0);
		// 删除告警
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
					k =  readerMapper.deleteAlarmDetail(parm);
				}while(k>0);
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
			 int m;
				do{
					m =  readerMapper.deleteAlarmFilter(parm);
				}while(m>0);
			 
		}
	}

}
