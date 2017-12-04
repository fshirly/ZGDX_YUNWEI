package com.fable.insightview.monitor.deviceresview.mapper;

import java.util.List;

import com.fable.insightview.monitor.deviceresview.entity.DeviceResViewBean;
import com.fable.insightview.platform.page.Page;

public interface DeviceResViewMapper {
	/**
	 * 获得添加资源表格数据
	 */
	List<DeviceResViewBean> getDeviceInfo(Page<DeviceResViewBean> page);
	
	/**
	 * 根据重定义规则获得添加的资源
	 */
	List<DeviceResViewBean> getDeviceByRuleID(int ruleId);
}