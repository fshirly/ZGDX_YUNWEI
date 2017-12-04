package com.fable.insightview.monitor.alarmmgr.devicewblistsetting.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackListBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackPortListBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListMonitorsBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListTaskBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.mapper.AlarmBlackListMapper;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.mapper.AlarmBlackPortListMapper;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.mapper.SysBlackListMonitorsMapper;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.mapper.SysBlackListTaskMapper;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.service.IDeviceWBSettingService;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.server.Server;
import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.harvester.mapper.HarvesterMapper;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;
@Service
public class DeviceWBSettingServiceImpl implements IDeviceWBSettingService {

	@Autowired
	private AlarmBlackListMapper alarmBlackListMapper;
	
	@Autowired
	private AlarmBlackPortListMapper alarmBlackPortListMapper;
	
	@Autowired
	private SysBlackListTaskMapper sysBlackListTaskMapper;
	
	@Autowired
	private SysBlackListMonitorsMapper sysBlackListMonitorsMapper;
	
	@Autowired
	private HarvesterMapper harvesterMapper;
	
	@Override
	public List<AlarmBlackListBean> getAlarmBlackList(Page page) {
		return alarmBlackListMapper.selectAlarmBlackList(page);
	}

	@Override
	public List<MODevice> getInterfaceListByDeviceIP(Page<MODevice> page) {
		return alarmBlackListMapper.selectInterfaceListByDeviceIP(page);
	}

	@Override
	public int insertIntoAlarmBlackList(AlarmBlackListBean alarmBlackListBean) {
		alarmBlackListMapper.insertIntoAlarmBlackList(alarmBlackListBean);
		return alarmBlackListBean.getBlackID();
	}

	@Override
	public void insertIntoAlarmBlackPortList(AlarmBlackPortListBean alarmBlackPortListBean) {
		alarmBlackPortListMapper.insertIntoAlarmBlackPortList(alarmBlackPortListBean);
	}

	@Override
	public boolean deleltAlarmBlackListRow(AlarmBlackListBean row) {
		return alarmBlackListMapper.deleteAlarmBlackListRow(row) > 0;
	}

	@Override
	public boolean deleteAlarmBlackPortListRows(AlarmBlackPortListBean row) {
		return alarmBlackPortListMapper.deleteAlarmBlackPortListRow(row) >= 0;
	}

	@Override
	public boolean updateIntoAlarmBlackList(AlarmBlackListBean row) {
		return alarmBlackListMapper.updateIntoAlarmBlackList(row)>0;
	}

	@Override
	public boolean updateIntoAlarmBlackPortList(AlarmBlackPortListBean row) {
		return alarmBlackPortListMapper.updateIntoAlarmBlackPortList(row) > 0;
		
	}

	@Override
	public Integer getBlackIDByDeviceIPAndPortType(String deviceIP,String portType) {
		return alarmBlackListMapper.selectBlackIDByDeviceIPAndPortType(deviceIP,portType);
	}

	@Override
	public Map<String, Object> getColletorList() {
		List<SysServerHostInfo> hostList = harvesterMapper.getServerHostLst();
		List<SysServerHostInfo> resultLst = new ArrayList<SysServerHostInfo>();
		int status = 2;
		for (int i = 0; i < hostList.size(); i++) {
			Dispatcher dispatcher = Dispatcher.getInstance();
			ManageFacade facade = dispatcher.getManageFacade();
			
			//获得所有的服务
			List<Server> allServers = facade.listAllActiveServers();
			
			for (Server server : allServers) {
				if(hostList.get(i).getIpaddress().equals(server.getIp()) && "PerfPoll".equals(server.getProcessName())){
					status = 1;
					break;
				}else{
					continue;
				}
			}
			if(status == 1){
				resultLst.add(hostList.get(i));
			}
			status = 2;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", resultLst);
		return result;
	
	}

	@Override
	public Long getMOIDByDeviceIp(String deviceIP) {
		return alarmBlackListMapper.getMOIDByDeviceIp(deviceIP);
	}

	@Override
	public Integer insertIntoSysBlackListTask(SysBlackListTaskBean sysBlackListTaskBean) {
		sysBlackListTaskMapper.insertSysBlackListTask(sysBlackListTaskBean);
		return sysBlackListTaskBean.getTaskID();
	}

	@Override
	public Integer insertIntoSysBlackListMonitors(SysBlackListMonitorsBean sysBlackListMonitorsBean) {
		sysBlackListMonitorsMapper.insertSysBlackListMonitors(sysBlackListMonitorsBean);
		return sysBlackListMonitorsBean.getID();
	}

	@Override
	public Map<String, Object> getMonitorInfo(String kind) {
		return alarmBlackListMapper.selectMonitorInfo(kind);
	}

	@Override
	public List<MODevice> getPortListByDeviceIP(Page<MODevice> page) {
		return alarmBlackListMapper.selectPortListByDeviceIP(page);
	}

	@Override
	public boolean checkBlackIDInSysBlackListTask(Integer blackID) {
		List<SysBlackListTaskBean> resultList = sysBlackListTaskMapper.selectRowByBlackID(blackID);
		if(!resultList.isEmpty()){
			return  true;
		}else{
			return false;
		}
	}

	@Override
	public boolean updateSysBlackListTask(Integer blackID, String operateStatus,String collectorID,String oldCollectorID) {
		return sysBlackListTaskMapper.updateOperateStatusByBlackID(blackID,operateStatus,collectorID,oldCollectorID) > 0;
	}
	
	@Override
	public boolean checkBlackIDAndPort(AlarmBlackPortListBean alarmBlackPortListBean){
		if(alarmBlackPortListMapper.checkBlackIDAndPort(alarmBlackPortListBean)!=null){
			return alarmBlackPortListMapper.checkBlackIDAndPort(alarmBlackPortListBean).size()>0;
		}else{
			return false;
		}
	}

	@Override
	public boolean checkDeviceIP(String deviceip,String portType) {
		return alarmBlackListMapper.selectBlackIDByDeviceIPAndPortType(deviceip, portType)!=null;
	}
}
