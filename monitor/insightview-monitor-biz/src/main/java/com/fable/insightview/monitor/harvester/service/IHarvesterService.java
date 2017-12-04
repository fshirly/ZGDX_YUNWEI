package com.fable.insightview.monitor.harvester.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.harvester.entity.SysServerInstallService;

public interface IHarvesterService {
	/**
	 * 重启程序
	 */
	public Map<String, Object> resartProgram(
			SysServerInstallService sysServerInstallService);
	
	public int getStatus(String ip,String processName);
	
	List<SysServerInstallService> getharvestLsinfo(List<SysServerInstallService> list);
	
	Map<String, Object> delServerInstallService(SysServerInstallService bean);
}
