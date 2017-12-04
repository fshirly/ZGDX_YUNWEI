package com.fable.insightview.monitor.networkchannel.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.platform.page.Page;

/**
 * 设备监测视图mapper
 *
 */
public interface NetworkMonitorviewMapper {
	/**
	 * 从PerfDeviceAvailability查询设备可用性情况
	 * @param map
	 * @return
	 */
	public List<Map<String ,Object>> queryDeviceAvalibilityInfo(Map<String ,Object> map);
	
	/**
	 * 重要接口运行情况
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryImportantIfInfo(Map<String ,Object> map);
	
	/**
	 * 查询设备的名称以及IP
	 * @param deviceMoid
	 * @return
	 */
	public Map<String ,Object> queryDeviceNameIp(int deviceMoid);
	
	/**
	 * 重要接口带宽利用率趋势
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryImportantIfUsage(Map<String ,Object> map);
	
	/**
	 * 查询监测接口的连通性、管理状态等信息
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryIfStatusList(Map<String ,Object> map);
	
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
	 * 查询设备接口的top10丢包错包
	 * @param deviceMoid
	 * @return
	 */
	public List<Map<String ,Object>> queryTop10IfDiscardsAndErrors(Map<String ,Object> map);
	
	/**
	 * 查询最近24h的CPU或内存使用率
	 * @param map
	 * @return
	 */
	public List<Map<String ,Object>> queryTop10Usage24h(Map<String ,Object> map);
	
	/**
	 * 查询当前的CPU或内存使用率
	 * @param map
	 * @return
	 */
	public Float queryDeviceCurrentUsage(Map<String ,Object> map);
	
	/**
	 * 查询设备的物理内存
	 * @param deviceMoid
	 * @return
	 */
	public long queryDevicePhyMem(int deviceMoid);
	
	/**
	 * 查询CPU或内存的利用率趋势
	 * @param map
	 * @return
	 */
	public List<Map<String ,Object>> queryUsageLine(Map<String ,Object> map);
}
