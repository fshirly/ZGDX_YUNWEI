package com.fable.insightview.monitor.networkchannel.mapper;

import java.util.List;
import java.util.Map;

/**
 * 概览视图mapper
 *
 */
public interface NetworkOverviewMapper {
	/**
	 * 查询前十的CPU或者memory使用率
	 * @param map
	 * @return
	 */
	List<Map<String ,Object>> queryTop10CPUMemsage(Map<String ,String> map);
	
	/**
	 * 查询前十的接口带宽、流量、丢包及错包率
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryTop10DeviceIfInfo(Map<String ,String> map);
	
	/**
	 * 查询前十的网络设备告警
	 * @param map
	 * @return
	 */
	List<Map<String ,String>> queryRecentlyNetworkAlarm(Map<String ,String> map);
	
	/**
	 * 查询前十的资源变更记录
	 * @param map
	 * @return
	 */
	List<Map<String ,String>> queryResChangeRecord(Map<String ,String> map);
}
