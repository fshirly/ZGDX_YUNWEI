package com.fable.insightview.monitor.discover.mapper;

import java.util.List;

import com.fable.insightview.monitor.discover.entity.SysNetworkDiscoverTask;
import com.fable.insightview.platform.page.Page;

public interface SysNetworkDiscoverTaskMapper {
	int deleteByPrimaryKey(Integer taskid);

	int insert2(SysNetworkDiscoverTask record);

	int insertSelective(SysNetworkDiscoverTask record);

	SysNetworkDiscoverTask selectByPrimaryKey(Integer taskid);

	int updateByPrimaryKeySelective(SysNetworkDiscoverTask record);

	int updateByPrimaryKey(SysNetworkDiscoverTask record);

	List<SysNetworkDiscoverTask> selectDiscoverTasks(
			Page<SysNetworkDiscoverTask> page);

	SysNetworkDiscoverTask getTaskInfoByTaskId(int taskid);

	int updateTaskProgressStatus(SysNetworkDiscoverTask bean);

	List<SysNetworkDiscoverTask> getAllTask();

	List<SysNetworkDiscoverTask> selectDeviceTasks(
			Page<SysNetworkDiscoverTask> page);
	
	List<SysNetworkDiscoverTask> selectOfflineTasks(Page<SysNetworkDiscoverTask> page);
	
	/**
	 * 根据采集机获得离线发现任务
	 */
	List<SysNetworkDiscoverTask> getOfflineTaskByHost(int collectorId);
}