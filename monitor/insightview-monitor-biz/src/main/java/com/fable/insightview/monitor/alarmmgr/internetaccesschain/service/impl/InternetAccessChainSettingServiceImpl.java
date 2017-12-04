package com.fable.insightview.monitor.alarmmgr.internetaccesschain.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackListBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackPortListBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListMonitorsBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListTaskBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.mapper.AlarmBlackListMapper;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.mapper.AlarmBlackPortListMapper;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.mapper.SysBlackListMonitorsMapper;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.mapper.SysBlackListTaskMapper;
import com.fable.insightview.monitor.alarmmgr.internetaccesschain.service.IInternetAccessChainSettingService;
import com.fable.insightview.monitor.harvester.mapper.HarvesterMapper;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.platform.page.Page;
@Service
public class InternetAccessChainSettingServiceImpl implements IInternetAccessChainSettingService {

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
	public List<AlarmBlackListBean> getIACSettingList(Page page) {
		return alarmBlackListMapper.selectAlarmBlackList(page);
	}

	@Override
	public Long getMOIDByDeviceIp(String deviceIP) {
		 return alarmBlackListMapper.getMOIDByDeviceIp(deviceIP);
	}

	@Override
	public Integer getBlackIDByDeviceIPAndPortType(String deviceIP,String portType) {
		return alarmBlackListMapper.selectBlackIDByDeviceIPAndPortType(deviceIP,portType);
	}
	
	@Override
	public int insertIntoAlarmBlackList(AlarmBlackListBean alarmBlackListBean) {
		alarmBlackListMapper.insertIntoAlarmBlackList(alarmBlackListBean);
		return alarmBlackListBean.getBlackID();
	}
	
	@Override
	public boolean insertIntoAlarmBlackPortList(AlarmBlackPortListBean alarmBlackPortListBean) {
		return alarmBlackPortListMapper.insertIntoAlarmBlackPortList(alarmBlackPortListBean)>0;
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
	public Integer insertIntoSysBlackListTask(SysBlackListTaskBean sysBlackListTaskBean) {
		sysBlackListTaskMapper.insertSysBlackListTask(sysBlackListTaskBean);
		return sysBlackListTaskBean.getTaskID();
	}
	
	@Override
	public Map<String, Object> getMonitorInfo(String kind) {
		return alarmBlackListMapper.selectMonitorInfo(kind);
	}
	
	@Override
	public boolean insertIntoSysBlackListMonitors(SysBlackListMonitorsBean sysBlackListMonitorsBean) {
		return sysBlackListMonitorsMapper.insertSysBlackListMonitors(sysBlackListMonitorsBean)>0;
	}
	
	@Override
	public List<MODevice> getChainListByDeviceIP(Page<MODevice> page) {
		return alarmBlackListMapper.selectChainListByDeviceIP(page);
	}
	
	@Override
	public boolean updateIntoAlarmBlackList(AlarmBlackListBean row) {
		return alarmBlackListMapper.updateIntoAlarmBlackList(row)>0;
	}
	
	@Override
	public boolean updateSysBlackListTask(Integer blackID, String operateStatus,String collectorID,String oldCollector) {
		return sysBlackListTaskMapper.updateOperateStatusByBlackID(blackID,operateStatus,collectorID,oldCollector) > 0;
	}

	@Override
	public boolean deleteAlarmBlackPortListRows(AlarmBlackPortListBean row) {
		return alarmBlackPortListMapper.deleteAlarmBlackPortListRow(row) > 0;
	}
}
