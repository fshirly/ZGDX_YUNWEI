package com.fable.insightview.monitor.dutydesk.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.fable.insightview.monitor.dutydesk.bean.ResDeviceBean;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.monitor.host.entity.MODevice;

/**
 * 值班服务台
 *
 */
public interface IDutyDeskService {
	/**
	 * 获得各区域资源监控设备
	 */
	List<ResDeviceBean> getResMODevice();
	
	/**
	 * 获得所有的拓扑及默认展示的拓扑
	 */
	Map<String, Object> getTopos();
	
	/**
	 * 获得所有的机房及默认展示的机房
	 */
	Map<String, Object> get3dRooms();
	
	/**
	 * 获得top10告警
	 */
	List<Map<String, Object>> getTopAlarm(Map<String, Object> paramMap);
	
	/**
	 * 告警详情
	 */
	AlarmNode getAlarmDtail(Map<String, Integer> paramMap);
	
	/**
	 *  获得告警列表数据
	 */
	Map<String, Object> getAlarmList(@RequestBody Map<String, Object> paramMap);
	
	/**
	 * 获得top10Cpu
	 */
	List<MODevice> getTopCpu(Map<String, Object> paramMap);
	
	/**
	 * 获得top10内存
	 */
	List<MODevice> getTopMemory(Map<String, Object> paramMap);
	
	/**
	 * 获得top10磁盘
	 */
	List<MODevice> getTopVolumes(Map<String, Object> paramMap);
	
	/**
	 *  获得Cpu更多列表数据
	 */
	Map<String, Object> getCpuList(@RequestBody Map<String, Object> paramMap);
	
	/**
	 *  获得内存更多列表数据
	 */
	Map<String, Object> getMemList(@RequestBody Map<String, Object> paramMap);
	
	/**
	 *  获得磁盘更多列表数据
	 */
	Map<String, Object> getVolList(@RequestBody Map<String, Object> paramMap);
	
	/**
	 *  获得告警列表中查询条件：告警级别、告警类型、告警状态下拉框数据
	 */
	Map<String, Object> getAlarmConditions();
}
