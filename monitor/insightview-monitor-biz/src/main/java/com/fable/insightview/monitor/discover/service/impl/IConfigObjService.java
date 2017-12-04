package com.fable.insightview.monitor.discover.service.impl;

import java.util.Map;

import com.fable.insightview.monitor.alarmmgr.entity.MOKPIThresholdBean;

public interface IConfigObjService {
	/**
	 * 获得阈值列表
	 */
	Map<String, Object> listThreshold(MOKPIThresholdBean mokpiThresholdBean);
	
}
