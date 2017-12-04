package com.fable.insightview.monitor.perf.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.perf.entity.PerfPollTaskBean;
import com.fable.insightview.platform.page.Page;

/**
 * 离线采集任务
 *
 */
public interface IOfflinePerftaskService {
	/**
	 * 获得分页查询的离线采集任务
	 */
	List<PerfPollTaskBean> getOfflinePerfTask(Page<PerfPollTaskBean> page);
	
	/**
	 * 获得采集的离线采集机
	 */
	Map<String, Object> findHostLst();
	
}
