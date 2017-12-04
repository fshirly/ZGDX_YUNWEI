package com.fable.insightview.monitor.environmentmonitor.service;

import java.util.List;

import com.fable.insightview.monitor.environmentmonitor.entity.MOZoneManagerBean;
import com.fable.insightview.platform.page.Page;

/**
 * 管理对象ZoneManager
 */
public interface IZoneManagerService {
	/**
	 * 获得数据列表
	 */
	List<MOZoneManagerBean> getZoneManagerList(Page<MOZoneManagerBean> page);

	/**
	 * 根据id获得信息
	 */
	MOZoneManagerBean getZoneManagerInfo(Integer moID);

	/**
	 * 根据ip和对象类型获的详情
	 */
	MOZoneManagerBean getZoneManagerByIP(MOZoneManagerBean bean);

	public boolean updateZoneManager(MOZoneManagerBean bean);
}
