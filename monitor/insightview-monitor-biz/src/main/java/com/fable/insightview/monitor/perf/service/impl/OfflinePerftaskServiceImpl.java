package com.fable.insightview.monitor.perf.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.environmentmonitor.entity.MOZoneManagerBean;
import com.fable.insightview.monitor.environmentmonitor.mapper.MOZoneManagerMapper;
import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.harvester.mapper.HarvesterMapper;
import com.fable.insightview.monitor.middleware.middlewarecommunity.service.ISysMiddleWareCommunityService;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.mapper.MiddlewareMapper;
import com.fable.insightview.monitor.perf.entity.PerfPollTaskBean;
import com.fable.insightview.monitor.perf.entity.PerfTaskInfoBean;
import com.fable.insightview.monitor.perf.mapper.PerfPollTaskMapper;
import com.fable.insightview.monitor.perf.mapper.PerfTaskInfoMapper;
import com.fable.insightview.monitor.perf.service.IObjectPerfConfigService;
import com.fable.insightview.monitor.perf.service.IOfflinePerftaskService;
import com.fable.insightview.monitor.perf.service.IPerfTaskService;
import com.fable.insightview.monitor.sysdbmscommunity.service.ISysDBMSCommunityService;
import com.fable.insightview.monitor.sysroomcommunity.service.ISysRoomCommunityService;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.snmpcommunity.service.ISysVMIfCommunityService;

/**
 * 离线采集任务
 * 
 */
@Service
public class OfflinePerftaskServiceImpl implements IOfflinePerftaskService {
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final int ORACLE = 15;
	private static final int MYSQL = 16;
	private static final int DB2 = 54;
	private static final int SYBASE = 81;
	private static final int MSSQL = 86;
	private static final int WEBSPHERE = 19;
	private static final int TOMCAT = 20;
	private static final int WEBLOGIC = 53;
	private static final int ROOM = 44;
	private static final int DNS = 91;
	private static final int FTP = 92;
	private static final int HTTP = 93;
	private static final int TCP = 94;
	@Autowired
	PerfPollTaskMapper perfPollTaskMapper;
	@Autowired
	PerfTaskInfoMapper perfTaskInfoMapper;
	@Autowired
	MiddlewareMapper middlewareMapper;
	@Autowired
	MOZoneManagerMapper zoneManagerMapper;
	@Autowired
	IPerfTaskService perfTaskService;
	@Autowired
	IObjectPerfConfigService objectPerfConfigService;
	@Autowired
	ISysVMIfCommunityService sysVMIfCommunityService;
	@Autowired
	ISysDBMSCommunityService sysDBMSCommunityService;
	@Autowired
	ISysMiddleWareCommunityService middleWareCommunityService;
	@Autowired
	ISysRoomCommunityService roomCommunityService;
	@Autowired
	HarvesterMapper harvesterMapper;
	
	@Override
	public List<PerfPollTaskBean> getOfflinePerfTask(Page<PerfPollTaskBean> page) {
		List<PerfPollTaskBean> taskList = perfPollTaskMapper
				.getOfflinePerfTask(page);
		for (PerfPollTaskBean task : taskList) {
			//对象类型
			int moClassId = task.getMoClassId();
			
			//数据库
			if (moClassId == ORACLE || moClassId == MYSQL || moClassId == DB2
					|| moClassId == SYBASE || moClassId == MSSQL) {
				PerfTaskInfoBean perf = perfTaskInfoMapper
						.getByTaskIdAndMOID(task.getTaskId());
				if (perf != null) {
					String deviceIp = perf.getDeviceIp();
					task.setDeviceIp(deviceIp);
				}
			}
			
			//中间件
			if (moClassId == WEBSPHERE || moClassId == TOMCAT
					|| moClassId == WEBLOGIC) {
				MOMiddleWareJMXBean middle = middlewareMapper
						.selectMoMidByPrimaryKey(task.getMoId());
				if (middle != null) {
					String deviceIp = middle.getIp();
					task.setDeviceIp(deviceIp);
				}
			}

			//机房环境
			if (moClassId == ROOM) {
				// 获得被采设备的IP
				MOZoneManagerBean zoneManagerBean = zoneManagerMapper
						.getZoneManagerInfo(task.getMoId());
				if (zoneManagerBean != null) {
					String deviceIp = zoneManagerBean.getIpAddress();
					task.setDeviceIp(deviceIp);
				}
			}

			//站点
			if (moClassId == DNS || moClassId == FTP || moClassId == HTTP
					|| moClassId == TCP) {
				task.setDeviceIp(perfTaskService.getSiteName(task.getMoId(),
						moClassId));
			}
			// 获得对应的监测器数
			int moCounts = perfPollTaskMapper
					.getMonitorCounts(task.getTaskId());
			task.setMoCounts(moCounts);
		}
		return taskList;
	}

	@Override
	public Map<String, Object> findHostLst() {
		String processName = "PerfPoll";
		List<SysServerHostInfo> hostList = harvesterMapper.getAllServerHost(processName);
		for (SysServerHostInfo host : hostList) {
			host.setParentId(0);
		}
		String hostListJson = JsonUtil.toString(hostList);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("hostListJson", hostListJson);
		return result;
	}
}
