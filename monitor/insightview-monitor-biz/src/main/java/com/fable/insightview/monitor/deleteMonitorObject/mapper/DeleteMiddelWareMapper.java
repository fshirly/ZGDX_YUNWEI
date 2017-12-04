package com.fable.insightview.monitor.deleteMonitorObject.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;

public interface DeleteMiddelWareMapper {

	/***
	 * 删除快照表_中间件
	 * @param map
	 * @return
	 */
	int deleteSnapshot(Map<String, Object> map);
	
	/***
	 * 删除采集任务表
	 * @param map
	 * @return
	 */
	int deletetask(Map<String, Object> map);
	/***
	 * 删除中间件的子对象
	 * @param map
	 * @return
	 */
	int deleteMiddelWareChildDevice(Map<String, Object> map);
	 /**
	  * 删除设备告警数据
	  * @param map
	  * @return
	  */
	 int deleteAlarmDetail(Map<String, Object> map);
	 
	 /***
		 * 删除告警通知策略、告警视图定义、自动派单设置
		 * @param map
		 * @return
		 */
		int deleteAlarmFilter(Map<String,Object> map);
		/**
		 * 查询j2eeAPP的moid
		 * @param map
		 * @return
		 */
		List<Integer> queryJ2eeAppMOID(Map<String,Object> map);
		/**
		 * 删除凭证
		 * @param map
		 * @return
		 */
		int deleteSNMP(Map<String,Object> map);
		
		/**
		 * 删除该设备
		 * @param map
		 * @return
		 */
		int deleteDevice(Map<String,Object> map);
		
		List<ServiceBean> queryMiddleWareInfo(Map<String,Object> map);
		
		int deleteDiscoveMiddleWare(Map<String,Object> map);
		 /**
		 * 删除监测器模板
		 * @param map
		 * @return
		 */
		int deleteTemplate(Map<String,Object> map); 
}
