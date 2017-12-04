package com.fable.insightview.monitor.offlinecollector.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.harvester.entity.SysServerInstallService;
import com.fable.insightview.monitor.harvester.entity.SysServiceInfo;
import com.fable.insightview.platform.page.Page;

/**
 * 离线采集机
 *
 */
public interface IOfflineCollectorService {
	/**
	 * 获得离线采集机任务列表
	 */
	Map<String, Object> getOfflineCollectorList(Page<SysServerInstallService> page);
	
	/**
	 * 校验采集服务是否存在
	 */
	boolean isExist(SysServerHostInfo bean);
	
	/**
	 * 根据IP获得离线采集机
	 */
	SysServerHostInfo getOfflineHostByIp(String ipaddress);
	
	/**
	 * 新增采集机
	 */
	boolean addHost(SysServerHostInfo bean);
	
	/**
	 * 新增采集机服务
	 */
	boolean addInstallService(SysServerInstallService bean);
	
	/**
	 * 获得所有的服务
	 */
	List<SysServiceInfo> getAllServiceInfo();
	
	/**
	 * 更新采集机
	 */
	boolean updateHost(SysServerHostInfo bean);
	
	/**
	 * 根据id获得离线采集机
	 */
	SysServerHostInfo getOfflineHostById(int serverid);
	
	/**
	 * 更新采集机服务
	 */
	boolean updateInstallService(SysServerInstallService bean);
	
	int getServiceCountByServerID(int serverid);
	
	boolean delOfflineServerHost(Integer serverid);
	
	boolean isInUse(SysServerInstallService bean);
	
	/**
	 * 删除
	 */
	boolean delHostService(SysServerInstallService bean);
	
	/**
	 * 批量删除
	 */
	Map<String, Object> batchDelHostService(String ids,String serverIds);
}
