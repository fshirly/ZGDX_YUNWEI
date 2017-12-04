package com.fable.insightview.monitor.deleteMonitorObject.mapper;

import java.util.List;
import java.util.Map;

public interface WebSiteDeleteMapper {

	/**
	 * 删除站点的性能数据、性能快照数据
	 * @param map
	 * @return
	 */
	int deletePrefAndSnap(Map<String,Object> map);
	/**
	 * 删除站点告警信息
	 * @param map
	 * @return
	 */
	int deleteWebSiteAlarm(Map<String,Object> map);
	
	/***
	 * 删除告警通知策略、告警视图定义、自动派单设置
	 * @param map
	 * @return
	 */
	int deleteAlarmFilter(Map<String,Object> map);
	/**
	 * 删除子对象数据
	 */
	int deleteChildDevice(Map<String,Object> map);
	/**
	 * 删除该对象
	 * @param map
	 * @return
	 */
	int deleteDevice(Map<String,Object> map);
	
	/**
	 * 删除ftp凭证
	 * @param map
	 * @return
	 */
	int deleteSysSite(Map<String,Object> map);
	/**
	 * 删除http凭证
	 * @param map
	 * @return
	 */
	int deleteHttpSysSite(Map<String,Object> map);
	/**
	 * 查询站点的parentmoid
	 * @param map
	 * @return
	 */
	List<Integer> queryParentMOID(Map<String,Object> map);
	 /**
	 * 删除监测器模板
	 * @param map
	 * @return
	 */
	int deleteTemplate(Map<String,Object> map); 
}
