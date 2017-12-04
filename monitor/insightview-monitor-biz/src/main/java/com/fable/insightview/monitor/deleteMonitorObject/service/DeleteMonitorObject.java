package com.fable.insightview.monitor.deleteMonitorObject.service;

import java.util.Map;

public interface DeleteMonitorObject {

	/**
	 * 删除监测对象数据
	 * @param map
	 * @return
	 */
	 boolean deleteData(Map<String, Object> map);
	 /***
	  * 删除监测对象的性能数据及告警相关数据
	  * @param map
	  * @return
	  */
	 void deletePerfAndAlarmData(Map<String, Object> map);
}
