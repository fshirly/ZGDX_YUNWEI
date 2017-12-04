package com.fable.insightview.monitor.dutydesk.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.dutydesk.bean.ResDeviceBean;

public interface DutyDeskMapper {
	/**
	 * 获得资源监控的列表数据
	 */
	List<ResDeviceBean> getResMODeviceInfo(Map map);
	
	/**
	 * 获得所有的3d机房
	 */
	List<ResDeviceBean> getRoom3Ds();
	
	/**
	 * 获得地址数据字典id
	 */
	Integer getAreaId(String areaName);

}
