package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;
import com.fable.insightview.monitor.deleteMonitorObject.mapper.DeleteDeviceMapper;
import com.fable.insightview.monitor.deleteMonitorObject.service.IDelCommon;

@Service("common_75")
public class DeleteVcent extends DeleteObjectServiceImpl{
	
	@Autowired
	DeleteDeviceMapper deviceMapper;
	@Autowired
	IDelCommon delCommon;
	
	@Override
	public boolean delete(Map<String, Object> map) {
		Object MOID=null;
		for(String moid: map.keySet()){
			MOID = map.get(moid);
		}
		// 删除SysNetworkDiscoverTask表
		Map<String, Object>   discoverParm = new HashMap<String, Object>();
		discoverParm = new HashMap<String, Object>();
		discoverParm.put("MOID", MOID);
		List<ServiceBean> deviceList =  deviceMapper.queryDeviceInfo(discoverParm);
		for (ServiceBean device : deviceList) {
			discoverParm.put("MOName", device.getMOName());
			discoverParm.put("IP", device.getIp());
			deviceMapper.deleteDiscoverTask(discoverParm);
		}
		
		//删除告警重定义
		delCommon.delAlarmEventRedefine(MOID.toString());
		
		// 删除凭证
		Map<String, Object>   parm = new HashMap<String, Object>();
			parm.put("tableName", "SysVMIfCommunity");
			parm.put("MOID", MOID);
		 deviceMapper.deleteSNMP(parm);
		 
		// 删除采集性能表
			Map<String, Object>   taskMap = new HashMap<String, Object>();
			taskMap.put("SysPerfPollTask", MOID);
			taskMap.put("MOIPInfo", MOID);
			 for(String taskKey : taskMap.keySet()){
				 	parm = new HashMap<String, Object>();
					parm.put("tableName", taskKey);
					parm.put("MOID", MOID);
					deviceMapper.deleteTaskAndIP(parm);
			 }
			 // 删除监测器模板
			 deviceMapper.deleteTemplate(map);
			 
		 int i = deviceMapper.deleteMODevice(map);
		return (i>0)?true:false;
	}
	@Override
	public void deletePerfAndAlarm(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

}
