package com.fable.insightview.monitor.sysdbmscommunity.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.entity.SysNetworkDiscoverTask;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.discover.mapper.SysNetworkDiscoverTaskMapper;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;
import com.fable.insightview.monitor.dispatcher.utils.DispatcherUtils;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.monitor.perf.service.impl.PerfTaskServiceImpl;
import com.fable.insightview.monitor.sysdbmscommunity.entity.SysDBMSCommunityBean;
import com.fable.insightview.monitor.sysdbmscommunity.mapper.SysDBMSCommunityMapper;
import com.fable.insightview.monitor.sysdbmscommunity.service.ISysDBMSCommunityService;

@Service
public class SysDBMSCommunityServiceImpl implements ISysDBMSCommunityService {
	private final Logger logger = LoggerFactory
			.getLogger(PerfTaskServiceImpl.class);

	@Autowired
	SysDBMSCommunityMapper dbmsCommunityMapper;
	@Autowired
	SysNetworkDiscoverTaskMapper sysNetworkDiscover;
	@Autowired
	MODeviceMapper deviceMapper;

	@Override
	public boolean addDBMSCommunity(SysDBMSCommunityBean bean) {
		try {
			dbmsCommunityMapper.insertDBMSCommunity(bean);
			return true;
		} catch (Exception e) {
			logger.error("数据库验证异常：" + e.getMessage());
			return false;
		}
	}

	/**
	 * 新增前验证是否已存在
	 */
	@Override
	public boolean checkbeforeAdd(SysDBMSCommunityBean bean) {
		int count = 0;
		if(!"DB2".equals(bean.getDbmsType())){
			count = dbmsCommunityMapper.getByIPAndTypeAndPort(bean);
		}else{
			count = dbmsCommunityMapper.getByIPAndTypeAndPortAndName(bean).size();
		}
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 修改前验证是否已存在
	 */
	@Override
	public boolean checkbeforeUpdate(SysDBMSCommunityBean bean) {
		int count = 0;
		if(!"DB2".equals(bean.getDbmsType())){
			count = dbmsCommunityMapper.getByIDAndIPAndTypeAndPort(bean);
		}else{
			count = dbmsCommunityMapper.getByIDAndIPAndTypeAndPortAndName(bean);
		}
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 新增发现任务
	 */
	@Override
	public Map<String, Object> addDiscoverTask2(String deviceIP, int creator,
			String moClassNames, int port ,String dbName) {
		logger.info("新增发现任务。。。。。start");
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
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
		discover.setCollectorid(-1);
		discover.setUpdateinterval(-1);
		discover.setDbName(dbName);
		if (port != -1) {
			discover.setPort(port);
		}
		try {
			sysNetworkDiscover.insert2(discover);
			int taskid = discover.getTaskid();
			result.put("taskid", taskid);
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

			flag = true;
		} catch (Exception e) {
			logger.error("新增发现任务失败：" + e.getMessage());
			flag = false;
		}
		result.put("flag", flag);
		return result;
	}

	@Override
	public boolean updateDBMSCommunity(SysDBMSCommunityBean bean) {
		try {
			dbmsCommunityMapper.updateDBMSCommunity(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新数据库验证异常：" + e.getMessage());
			return false;
		}
	}

	@Override
	public SysDBMSCommunityBean getDBMSByID(int id) {
		return dbmsCommunityMapper.getDBMSByID(id);
	}

	@Override
	public SysDBMSCommunityBean getDBMSByTaskId(int taskId) {
		return dbmsCommunityMapper.getDBMSByTaskId(taskId);
	}

	@Override
	public SysDBMSCommunityBean getInfoByID(int id) {
		return dbmsCommunityMapper.getInfoByID(id);
	}

	@Override
	public boolean isDiscovered(String deviceIP) {
		List<MODeviceObj> deviceList = deviceMapper.getDeviceListByIP(deviceIP);
		if (deviceList.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<SysDBMSCommunityBean> getDatabaseCommunityByConditions(
			Page<SysDBMSCommunityBean> page) {
		List<SysDBMSCommunityBean> databaseList = dbmsCommunityMapper
				.getDBMSInfo(page);
		return databaseList;
	}

	@Override
	public boolean delDBMSCommunity(SysDBMSCommunityBean bean) {
		logger.info("执行删除操作。。。");
		int i = dbmsCommunityMapper.deleteByPrimaryKey(bean.getId());
		if (i == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delDBMS(List<Integer> ids) {
		try {
			dbmsCommunityMapper.delDBMS(ids);
		} catch (Exception e) {
			logger.error("删除异常：" + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean addDiscoverTask(String deviceIP, int creator,
			String moClassNames, int port, String dbName) {
		logger.info("新增发现任务。。。。。start");
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
		discover.setCollectorid(-1);
		discover.setUpdateinterval(-1);
		discover.setDbName(dbName);
		try {
			sysNetworkDiscover.insert2(discover);
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
			logger.error("新增发现任务失败：" + e.getMessage());
			return false;
		}
	}

	@Override
	public List<SysDBMSCommunityBean> getByIP(SysDBMSCommunityBean bean) {
		return dbmsCommunityMapper.getByIP(bean);
	}

	@Override
	public boolean updateDBMSCommunityByID(SysDBMSCommunityBean bean) {
		try {
			dbmsCommunityMapper.updateDBMSCommunityByID(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新数据库验证异常：" + e.getMessage());
			return false;
		}
	}

	@Override
	public List<SysDBMSCommunityBean> getByIPAndPort(SysDBMSCommunityBean bean) {
		return dbmsCommunityMapper.getByIPAndPort(bean);
	}

	@Override
	public List<SysDBMSCommunityBean> getByConditions(SysDBMSCommunityBean bean) {
		return dbmsCommunityMapper.getByConditions(bean);
	}

	@Override
	public List<SysDBMSCommunityBean> getByIPAndTypeAndPortAndName(
			SysDBMSCommunityBean bean) {
		return dbmsCommunityMapper.getByIPAndTypeAndPortAndName(bean);
	}

	@Override
	public boolean updateDBMSCommunity2(SysDBMSCommunityBean bean) {
		try {
			dbmsCommunityMapper.updateDBMSCommunity2(bean);
			return true;
		} catch (Exception e) {
			logger.error("更新数据库验证异常：" + e.getMessage());
			return false;
		}
	}

}