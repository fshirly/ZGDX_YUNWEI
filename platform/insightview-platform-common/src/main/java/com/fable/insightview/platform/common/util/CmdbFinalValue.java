package com.fable.insightview.platform.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * CMDB常量接口
 * 
 * @author wul
 * 
 */
public interface CmdbFinalValue {

	// 监测ResTypeId数据数组
	// 虚拟机、物理机、虚拟主机、路由器、交换机、oracle、mysql、接口、磁盘、内存、CPU
	public final static String[] monitorIdArr = { "5", "6", "7", "8", "9",
			"10", "11", "12", "13", "15", "16" };

	// 监测ResTypeId数据Map
	public final static Map monitorIdMap = new HashMap() {
		{
			put("9", "虚拟机");
			put("7", "物理机");
			put("8", "虚拟主机");
			put("5", "路由器");
			put("6", "交换机");
			put("15", "oracle");
			put("16", "mysql");
			put("10", "接口");
			put("11", "磁盘");
			put("13", "内存");
			put("12", "CPU");
		}
	};

	// CMDB ResTypeId数据数组
	// 虚拟机、物理机、虚拟主机、路由器、交换机、oracle、mysql、接口、磁盘、内存、CPU
	public final static Map cmdbIdMap = new HashMap() {
		{
			put("虚拟机", "10047");
			put("物理机", "10048");
			put("虚拟主机", "10049");
			put("路由器", "10050");
			put("交换机", "10057");
			put("oracle", "10051");
			put("mysql", "10052");
			put("接口", "10053");
			put("磁盘", "10054");
			put("内存", "10055");
			put("CPU", "10056");
		}
	};
}