package com.fable.insightview.monitor.offlinecollector.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.alarmmgr.entity.SysTrapTaskBean;
import com.fable.insightview.monitor.alarmmgr.traptask.mapper.SysTrapTaskMapper;
import com.fable.insightview.monitor.discover.entity.SysNetworkDiscoverTask;
import com.fable.insightview.monitor.discover.mapper.SysNetworkDiscoverTaskMapper;
import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.harvester.entity.SysServerInstallService;
import com.fable.insightview.monitor.harvester.entity.SysServiceInfo;
import com.fable.insightview.monitor.harvester.mapper.HarvesterMapper;
import com.fable.insightview.monitor.offlinecollector.service.IOfflineCollectorService;
import com.fable.insightview.monitor.perf.entity.PerfTaskInfoBean;
import com.fable.insightview.monitor.perf.mapper.PerfTaskInfoMapper;
import com.fable.insightview.monitor.syslog.entity.SysSyslogTaskBean;
import com.fable.insightview.monitor.syslog.mapper.SysSyslogTaskMapper;
import com.fable.insightview.platform.page.Page;

@Service
public class OfflineCollectorServiceImpl implements IOfflineCollectorService {
	private static final Logger logger = LoggerFactory.getLogger(OfflineCollectorServiceImpl.class);
	
	private static final int DISCOVERY = 1;
	private static final int PERFPOLL = 2;
	private static final int TRAPSERVER = 4;
	private static final int SYSLOGSERVER= 7;
	@Autowired
	HarvesterMapper harvesterMapper;
	@Autowired
	SysNetworkDiscoverTaskMapper sysNetworkDiscoverTaskMapper;
	@Autowired
	PerfTaskInfoMapper perfTaskInfoMapper;
	@Autowired
	SysTrapTaskMapper trapTaskMapper;
	@Autowired
	SysSyslogTaskMapper syslogTaskMapper;
	
	@Override
	public Map<String, Object> getOfflineCollectorList(
			Page<SysServerInstallService> page) {
		List<SysServerInstallService> list = harvesterMapper.getOfflineCollectorList(page);
		int total = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", list);
		result.put("total", total);
		return result;
	}

	@Override
	public boolean isExist(SysServerHostInfo bean) {
		int count = harvesterMapper.isExitOfflineService(bean);
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public SysServerHostInfo getOfflineHostByIp(String ipaddress) {
		return harvesterMapper.getOfflineHostByIp(ipaddress);
	}

	@Override
	public boolean addHost(SysServerHostInfo bean) {
		try {
			harvesterMapper.insertHost(bean);
			return true;
		} catch (Exception e) {
			logger.error("新增离线采集机异常！",e);
		}
		return false;
	}

	@Override
	public boolean addInstallService(SysServerInstallService bean) {
		try {
			harvesterMapper.insertInstallService(bean);
			return true;
		} catch (Exception e) {
			logger.error("新增离线采集机服务异常！",e);
		}
		return false;
	}

	@Override
	public List<SysServiceInfo> getAllServiceInfo() {
		return harvesterMapper.getAllServiceInfo();
	}

	@Override
	public boolean updateHost(SysServerHostInfo bean) {
		try {
			harvesterMapper.updateOfflineCollector(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新离线采集机服务异常！",e);
		}
		return false;
	}

	@Override
	public SysServerHostInfo getOfflineHostById(int serverid) {
		return harvesterMapper.getOfflineHostById(serverid);
	}

	@Override
	public boolean updateInstallService(SysServerInstallService bean) {
		try {
			harvesterMapper.updateInstallService(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新服务异常！",e);
		}
		return false;
	}

	@Override
	public int getServiceCountByServerID(int serverid) {
		return harvesterMapper.getServiceCountByServerID(serverid);
	}

	@Override
	public boolean delOfflineServerHost(Integer serverid) {
		return harvesterMapper.delOfflineServerHost(serverid);
	}

	@Override
	public boolean delHostService(SysServerInstallService bean) {
		boolean delService = false;
		boolean delHost = true;
		//删除该服务
		delService = harvesterMapper.delServerInstallService(bean.getId());
		//如果该采集机 没有采集服务则删除
		int count = harvesterMapper.getServiceCountByServerID(bean.getServerid());
		if(count == 0){
			delHost = harvesterMapper.delOfflineServerHost(bean.getServerid());
		}
		if(delService && delHost){
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> batchDelHostService(String ids, String serverIds) {
		boolean delService = false;
		boolean delHost = true;
		boolean delResult = false;
		Map<String, String> idMap = this.getDelIDs(ids);
		String delIds = idMap.get("delIds");
		String unDelIds = idMap.get("unDelIds");
		if(!"".equals(delIds)){
			//删除服务
			delService = harvesterMapper.batchDelServerInstallService(delIds);
			//删除没有服务的采集机
			String[] splits = serverIds.split(",");
			try {
				for (int i = 0; i < splits.length; i++) {
					int serverId = Integer.parseInt(splits[i]);
					int count = harvesterMapper.getServiceCountByServerID(serverId);
					if(count == 0){
						logger.info("删除id为：{}的采集机！",serverId);
						delHost = harvesterMapper.delOfflineServerHost(serverId);
						if(!delHost){
							break;
						}
					}
				}
			} catch (NumberFormatException e) {
				logger.error("删除采集机异常！",e);
			}
			if(delService && delHost){
				delResult = true;
			}
			delResult = false;
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("delResult", delResult);
		result.put("unDelIds", unDelIds);
		return result;
	}
	
	private Map<String, String> getDelIDs(String ids){
		String[] splits = ids.split(",");
		String delIds = "";
		String unDelIds = "";
		for (int i = 0; i < splits.length; i++) {
			Integer id = Integer.parseInt(splits[i]);
			SysServerInstallService serverInstallService = harvesterMapper.getByID(id);
			boolean isUsed = this.isInUse(serverInstallService);
			if(!isUsed){
				delIds += id + ",";
			}else{
				unDelIds += id + ",";
			}
		}
		if(!"".equals(delIds)){
			delIds = delIds.substring(0,delIds.lastIndexOf(","));
		}
		if(!"".equals(unDelIds)){
			unDelIds = unDelIds.substring(0,unDelIds.lastIndexOf(","));
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("delIds", delIds);
		result.put("unDelIds", unDelIds);
		return result;
	}

	@Override
	public boolean isInUse(SysServerInstallService bean) {
		boolean isUsed = false;
		int installServiceId = bean.getInstallServiceId();
		int serverId = bean.getServerid();
		//发现
		if(installServiceId  == DISCOVERY){
			List<SysNetworkDiscoverTask> taskLst = sysNetworkDiscoverTaskMapper.getOfflineTaskByHost(serverId);
			if(taskLst.size() > 0){
				isUsed = true;
			}
		}
		//采集
		else if(installServiceId == PERFPOLL){
			List<PerfTaskInfoBean> taskLst = perfTaskInfoMapper.getOfflineTaskByHost(serverId);
			if(taskLst.size() > 0){
				isUsed = true;
			}
		}
		//trap
		else if(installServiceId == TRAPSERVER){
			List<SysTrapTaskBean> taskLst = trapTaskMapper.getOfflineTaskByHost(serverId);
			if(taskLst.size() > 0){
				isUsed = true;
			}
		}
		//syslog
		else if(installServiceId == SYSLOGSERVER){
			List<SysSyslogTaskBean> taskLst = syslogTaskMapper.getOfflineTaskByHost(serverId);
			if(taskLst.size() > 0){
				isUsed = true;
			}
		}
		return isUsed;
	}

}
