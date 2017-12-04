package com.fable.insightview.monitor.discover.mapper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.discover.entity.SafeDeviceObj;
import com.fable.insightview.monitor.host.entity.MOSafeDevice;
import com.fable.insightview.platform.page.Page;

public interface SafeDeviceMapper {

	
	// 查找安全设备
	LinkedList<SafeDeviceObj> querySafeDeviceList(Page<SafeDeviceObj> page);
	
	/**
	 * 
	 * 
	 * @param deviceIP
	 * @return
	 */
	MOSafeDevice getSafeDeviceDetail(Integer MOID);
	
	List<MOSafeDevice> getSafeDeviceConnNum(Map map);
	
}
