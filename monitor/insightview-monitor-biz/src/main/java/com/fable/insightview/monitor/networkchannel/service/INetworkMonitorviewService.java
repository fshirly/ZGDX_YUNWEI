package com.fable.insightview.monitor.networkchannel.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.platform.page.Page;

public interface INetworkMonitorviewService {
	/**
	 * 从PerfDeviceAvailability查询设备可用性情况
	 * @param deviceMoid 设备的moid
	 * @param timeBegin 时间区间的开始
	 * @param timeEnd 时间区间的结束
	 * @param timeTip 前端页面显示
	 * @return
	 */
	public Map<String, Object> queryDeviceAvalibilityInfo(int deviceMoid,
			String timeBegin, String timeEnd ,String timeTip);
	
	/**
	 * 重要接口运行情况
	 * @param deviceMoid
	 * @return
	 */
	public List<Map<String, Object>> queryImportantIfInfo(int deviceMoid);
	
	/**
	 * 重要接口带宽利用率趋势
	 * @param deviceMoid
	 * @param timeBegin 查询开始时间
	 * @param timeEnd 查询结束时间
	 * @param dataSplit 按什么方式返回数据 C 通用 H 小时  D 天
	 * @return
	 */
	public Map<String, Object> queryImportantIfUsage(int deviceMoid,
			String timeBegin, String timeEnd ,String dataSplit);
	
	/**
	 * 查询监测接口的连通性、管理状态等信息
	 * @param deviceMoid
	 * @return
	 */
	public List<Map<String, Object>> queryIfStatusList(int deviceMoid);
	
	/**
	 * 查询所有接口的信息
	 * @param deviceMoid
	 * @return
	 */
	public List<MODevice> queryDeviceIfInfo(int deviceMoid);
	
	/**
	 * 接口最近的告警情况
	 * @param deviceMoid
	 * @return
	 */
	public List<Map<String ,String>> queryIfAlarm(Page<Map<String ,Object>> page);
	
	/**
	 * 查询设备接口的top10使用率
	 * @param deviceMoid
	 * @return
	 */
	public List<Map<String, Object>> queryTop10IfUsage(int deviceMoid);
	
	/**
	 * 查询设备接口的top10丢包错包
	 * @param deviceMoid
	 * @return
	 */
	public List<Map<String, Object>> queryTop10IfDiscardsAndErrors(int deviceMoid);
	
	/**
	 * 查询最近24h的CPU或内存使用率
	 * @param deviceMoid
	 * @param tableName 数据表名
	 * @param tableName 关联表名
	 * @param kpiName 指标名称
	 * @return
	 */
	public List<Map<String, Object>> queryTop10Usage24h(int deviceMoid ,String tableName ,String tableName1 ,String kpiName);
	
	/**
	 * 查询当前的内存使用率及24h内存使用率top10
	 * @param deviceMoid
	 * @return
	 */
	public Map<String, Object> queryMemUsage(int deviceMoid);
	
	/**
	 * 查询当前的CPU使用率及24h CPU使用率top10
	 * @param deviceMoid
	 * @return
	 */
	public Map<String, Object> queryCPUsage(int deviceMoid);
	
	/**
	 * 查询设备的物理内存
	 * @param deviceMoid
	 * @return
	 */
	public String queryDevicePhyMem(int deviceMoid);
	
	/**
	 * 查询CPU或内存的利用率趋势
	 * @param deviceMoid
	 * @param timeBegin 查询开始时间
	 * @param timeEnd 查询结束时间
	 * @param tableName 数据表名
	 * @param tableName1 关联表名
	 * @param kpiName 指标名称
	 * @return
	 */
	public List<Map<String, Object>> queryUsageLine(int deviceMoid, String timeBegin,
			String timeEnd, String tableName, String tableName1, String kpiName);
	
	/**
	 * 查询CPU使用率曲线
	 * @param deviceMoid
	 * @param timeBegin 查询开始时间
	 * @param timeEnd 查询结束时间
	 * @param dataSplit 按什么方式返回数据 C 通用 H 小时  D 天
	 * @return
	 */
	public Map<String, Object> queryCpuUsageLine(int deviceMoid,
			String timeBegin, String timeEnd ,String dataSplit);
	
	/**
	 * 查询内存使用率曲线
	 * @param deviceMoid
	 * @param timeBegin 查询开始时间
	 * @param timeEnd 查询结束时间
	 * @param dataSplit 按什么方式返回数据 C 通用 H 小时  D 天
	 * @return
	 */
	public Map<String, Object> queryMemUsageLine(int deviceMoid,
			String timeBegin, String timeEnd ,String dataSplit);
	
	/**
	 * 查询设备名称及IP
	 * @param deviceMoid
	 * @return
	 */
	public Map<String, Object> queryDeviceNameIp(int deviceMoid);

}
