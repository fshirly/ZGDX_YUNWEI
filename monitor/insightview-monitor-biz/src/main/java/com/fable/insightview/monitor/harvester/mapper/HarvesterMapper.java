package com.fable.insightview.monitor.harvester.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.harvester.entity.SysServerInstallService;
import com.fable.insightview.monitor.harvester.entity.SysServiceInfo;
import com.fable.insightview.platform.page.Page;

public interface HarvesterMapper {
	int insert(SysServiceInfo record);

	int insertSelective(SysServiceInfo record);

	/**
	 *采集机视角
	 */
	List<SysServerInstallService> selectHarvestInfo();

	/**
	 * 采集任务视角
	 * 
	 * @return
	 */
	List<SysServerInstallService> selectHarvestTaskInfo();

	List<SysServerHostInfo> getServerHostLst();

	List<SysServerHostInfo> getServerDomain(Page<SysServerHostInfo> page);

	SysServerInstallService getByID(Integer id);

	List<SysServerHostInfo> getServerHostByIP(String ipaddresss);
	
	boolean delServerInstallService(Integer id);
	
	List<SysServerInstallService> getByServerID(Integer serverid);
	
	boolean delServerHost(Integer serverid);
	
	List<Integer> getDomainByServer(Integer serverid);
	
	List<SysServerHostInfo> getCollectorLst(Map map);
	
	/**
	 * 获得离线采集机
	 * @param processName 服务类型名称
	 */
	List<SysServerHostInfo> getAllServerHost(@Param("processName")String processName);
	
	List<SysServerInstallService> getOfflineCollectorList(Page<SysServerInstallService> page);
	
	int isExitOfflineService(SysServerHostInfo bean);
	
	SysServerHostInfo getOfflineHostByIp(String ipaddress);
	
	int insertHost(SysServerHostInfo bean);
	
	int insertInstallService(SysServerInstallService bean);
	
	List<SysServiceInfo> getAllServiceInfo();
	
	int updateOfflineCollector(SysServerHostInfo bean);
	
	SysServerHostInfo getOfflineHostById(int serverid);
	
	int updateInstallService(SysServerInstallService bean);
	
	int getServiceCountByServerID(int serverid);
	
	boolean delOfflineServerHost(Integer serverid);
	
	boolean batchDelServerInstallService(@Param("ids")String ids);
	
	List<SysServerHostInfo> getOfflineCollectorLst(Map map);

	List<SysServerHostInfo> getOfflineTrapServer(@Param("alarmDefineID")int alarmDefineID);
}