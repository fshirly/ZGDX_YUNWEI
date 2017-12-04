package com.fable.insightview.monitor.discover.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.discover.entity.SysNetworkDiscoverTask;
import com.fable.insightview.platform.page.Page;

/**
 * 离线发现
 * 
 * @author Administrator
 * 
 */
public interface IOfflineDiscoverService {
	/**
	 * 获得发现的离线采集机
	 */
	Map<String, Object> findHostLst();

	/**
	 * 新增离线发现任务
	 */
	boolean addOfflineTask(SysNetworkDiscoverTask task);

	/**
	 * 新增单对象的离线发现任务
	 */
	public boolean addSingleOfflineTask(String deviceIP, int creator,
			String moClassNames, int port, String dbName, String isOffline,
			int collectorid);
	
	/**
	 * 离线发现任务列表数据
	 */
	List<SysNetworkDiscoverTask> selectOfflineTasks(Page<SysNetworkDiscoverTask> page);
}
