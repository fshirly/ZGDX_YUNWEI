package com.fable.insightview.monitor.discover.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.discover.entity.SysNetworkDiscoverTask;
import com.fable.insightview.monitor.discover.mapper.SysNetworkDiscoverTaskMapper;
import com.fable.insightview.monitor.discover.service.IOfflineDiscoverService;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.dispatcher.utils.DispatcherUtils;
import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.harvester.mapper.HarvesterMapper;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.page.Page;

@Service
public class OfflineDiscoverServiceImpl implements IOfflineDiscoverService{
	Logger logger = LoggerFactory.getLogger(OfflineDiscoverServiceImpl.class);
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	HarvesterMapper harvesterMapper;
	@Autowired
	SysNetworkDiscoverTaskMapper discoverTaskMapper;
	
	@Override
	public Map<String, Object> findHostLst() {
		String processName = "Discovery";
		List<SysServerHostInfo> hostList = harvesterMapper.getAllServerHost(processName);
		for (SysServerHostInfo host : hostList) {
			host.setParentId(0);
		}
		String hostListJson = JsonUtil.toString(hostList);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("hostListJson", hostListJson);
		return result;
	}
	@Override
	public boolean addOfflineTask(SysNetworkDiscoverTask task) {
		try {
			discoverTaskMapper.insert2(task);
			return true;
		} catch (Exception e) {
			logger.error("新增离线任务失败：",e);
		}
		return false;
	}
	@Override
	public boolean addSingleOfflineTask(String deviceIP, int creator,
			String moClassNames, int port, String dbName, String isOffline,
			int collectorid) {
		logger.info("新增单对象的离线发现任务。。。。。start");
		SysNetworkDiscoverTask discover = new SysNetworkDiscoverTask();
		discover.setTasktype(4);
		discover.setIpaddress1(deviceIP);
		discover.setIpaddress2(deviceIP);
		discover.setWebipaddress(DispatcherUtils.getLocalAddress());
		discover.setCreator(creator);
		discover.setCreatetime(new Date());
		discover.setProgressstatus(1);
		discover.setOperatestatus(1);
		discover.setMoClassNames(moClassNames);
		discover.setPort(port);
		discover.setCollectorid(collectorid);
		discover.setUpdateinterval(-1);
		discover.setDbName(dbName);
		discover.setIsOffline(isOffline);
		try {
			discoverTaskMapper.insert2(discover);
			Dispatcher dispatch = Dispatcher.getInstance();
			ManageFacade facade = dispatch.getManageFacade();
			List<TaskDispatcherServer> servers = facade
					.listActiveServersOf(TaskDispatcherServer.class);
			if (servers.size() > 0) {
				String topic = "TaskDispatch";
				TaskDispatchNotification message = new TaskDispatchNotification();
				message.setDispatchTaskType(TaskType.TASK_DISCOVERY);
				facade.sendNotification(servers.get(0).getIp(), topic, message);
			}
			return true;
		} catch (Exception e) {
			logger.error("新增单对象的离线发现任务失败：" + e.getMessage());
			return false;
		}
	}
	@Override
	public List<SysNetworkDiscoverTask> selectOfflineTasks(
			Page<SysNetworkDiscoverTask> page) {
		List<SysNetworkDiscoverTask> taskLst = discoverTaskMapper.selectOfflineTasks(page);
		for (SysNetworkDiscoverTask task : taskLst) {
			if (task.getCreatetime() != null) {
				String createtimeStr = dateFormat.format(task.getCreatetime());
				task.setCreatetimeStr(createtimeStr);
			}
		}
		return taskLst;
	}

}
