package com.fable.insightview.monitor.deleteMonitorObject.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;

public interface DeleteReaderMapper {

	 /**
	  * 删除性能快照
	  * @param map
	  * @return
	  */
	 int deleteReaderSnapshot(Map<String, Object> map);
	 
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
	   * 删除凭证
	   * @param map
	   * @return
	   */
	  int deleteSNMP (Map<String, Object> map);
	  
	  /**
		  * 删除该性能任务表
		  * @param map
		  * @return
		  */
		  int deleteTask(Map<String, Object> map);
	  /**
		 * 删除子对象（电子标签）
		 * @param map
		 * @return
		 */
		int deleteMOTag(Map<String, Object> map);
		
		/**
		 * 删除该设备
		 * @param map
		 * @return
		 */
		int deleteMODevice(Map<String, Object> map);
		/**
		 * 查询阅读器的MOID
		 * @param map
		 * @return
		 */
		List<Integer> queryReaderMOID (Map<String, Object> map);
		/***
		 * 为删除SysNetworkDiscoverTask查询阅读器
		 * @param map
		 * @return
		 */
		List<ServiceBean> queryReaderInfo (Map<String, Object> map);
		/**
		 * 删除SysNetworkDiscoverTask表
		 * @param map
		 * @return
		 */
		int deleteReaderDiscoverTask(Map<String, Object> map);
		
		 /**
		 * 删除监测器模板
		 * @param map
		 * @return
		 */
		int deleteTemplate(Map<String,Object> map); 
}
