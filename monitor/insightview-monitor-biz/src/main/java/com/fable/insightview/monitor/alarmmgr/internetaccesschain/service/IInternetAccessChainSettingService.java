package com.fable.insightview.monitor.alarmmgr.internetaccesschain.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackListBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackPortListBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListMonitorsBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListTaskBean;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.platform.page.Page;

public interface IInternetAccessChainSettingService {
	/**
	 * 获取所有互联网链路配置
	 * @return
	 */
	List<AlarmBlackListBean> getIACSettingList(Page page);
	
	Long getMOIDByDeviceIp(String deviceIP);

	Integer getBlackIDByDeviceIPAndPortType(String string, String value);

	int insertIntoAlarmBlackList(AlarmBlackListBean alarmBlackListBean);

	boolean insertIntoAlarmBlackPortList(AlarmBlackPortListBean alarmBlackPortListBean);

	boolean checkBlackIDInSysBlackListTask(Integer blackID);

	Integer insertIntoSysBlackListTask(SysBlackListTaskBean sysBlackListTaskBean);

	Map<String, Object> getMonitorInfo(String kind);

	boolean insertIntoSysBlackListMonitors(SysBlackListMonitorsBean sysBlackListMonitorsBean);

	List<MODevice> getChainListByDeviceIP(Page<MODevice> page);

	boolean updateIntoAlarmBlackList(AlarmBlackListBean row);

	boolean updateSysBlackListTask(Integer blackID, String operateStatus, String collectorID, String oldCollector);
	
	boolean deleteAlarmBlackPortListRows(AlarmBlackPortListBean row);
}
