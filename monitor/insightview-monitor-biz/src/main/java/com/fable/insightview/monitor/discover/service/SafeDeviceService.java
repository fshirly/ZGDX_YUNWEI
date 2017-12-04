package com.fable.insightview.monitor.discover.service;

import java.util.Date;
import java.util.List;

import com.fable.insightview.monitor.discover.entity.SafeDeviceObj;
import com.fable.insightview.platform.page.Page;

public interface SafeDeviceService {
	// 查找安全设备
		List<SafeDeviceObj> querySafeDeviceList(Page<SafeDeviceObj> page);
}
