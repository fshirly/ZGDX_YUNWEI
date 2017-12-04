package com.fable.insightview.monitor.networkchannel.service;

import java.util.List;
import java.util.Map;

public interface INetworkOverviewService {
	/**
	 * 查询top10 CPU使用率
	 * @param network 网络类型
	 * @param applicationType 应用类型
	 * @return
	 */
	public List<Map<String ,Object>> queryTop10CPUsage(String network ,String applicationType);
	
	/**
	 * 查询top10 内存使用率
	 * @param network 网络类型
	 * @param applicationType 应用类型
	 * @return
	 */
	public List<Map<String, Object>> queryTop10MemoryUsage(String network ,String applicationType);
	
	/**
	 * 查询top10 接口带宽利用率
	 * @param network 网络类型
	 * @param applicationType 应用类型
	 * @return
	 */
	public List<Map<String ,Object>> queryTop10DeviceIfUsage(String network ,String applicationType);
	
	/**
	 * 查询top10 接口流量
	 * @param network 网络类型
	 * @param applicationType 应用类型
	 * @return
	 */
	public List<Map<String ,Object>> queryTop10DeviceIfFlow(String network ,String applicationType);
	
	/**
	 * 查询top10 接口错包率
	 * @param network 网络类型
	 * @param applicationType 应用类型
	 * @return
	 */
	public List<Map<String, Object>> queryTop10DeviceIfErrors(String network ,String applicationType);
	
	/**
	 * 查询top10 接口丢包率
	 * @param network 网络类型
	 * @param applicationType 应用类型
	 * @return
	 */
	public List<Map<String ,Object>> queryTop10DeviceIfDiscards(String network ,String applicationType);
	
	/**
	 * 查询最近告警数（10条）
	 * @param network 网络类型
	 * @param applicationType 应用类型
	 * @return
	 */
	public List<Map<String, Object>> queryRecentlyNetworkAlarm(String network ,String applicationType);
	
	/**
	 * 查询最近资源变更记录（10条）
	 * @param network 网络类型
	 * @param applicationType 应用类型
	 * @return
	 */
	public List<Map<String ,String>> queryResChangeRecord(String network ,String applicationType);
}
