package com.fable.insightview.monitor.alarmmgr.devicewblistsetting.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackListBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackPortListBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListMonitorsBean;
import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListTaskBean;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.platform.page.Page;

public interface IDeviceWBSettingService {
	/**
	 * 获取所有设备黑白名单设置列表
	 * @return
	 */
	List<AlarmBlackListBean> getAlarmBlackList(Page page);

	/**
	 * 获取所有设备物理接口列表
	 * @return
	 */
	List<MODevice> getInterfaceListByDeviceIP(Page<MODevice> page);

	/**
	 * 向设备黑白名单设置列表插入数据
	 * @param alarmBlackListBean
	 * @return
	 */
	int insertIntoAlarmBlackList(AlarmBlackListBean alarmBlackListBean);

	/**
	 * 向设备黑白名单接口/端口列表插入数据
	 * @param alarmBlackPortListBean
	 */
	void insertIntoAlarmBlackPortList(AlarmBlackPortListBean alarmBlackPortListBean);

	/**
	 * 从设备黑白名单设置列表中删除行
	 * @param row
	 */
	boolean deleltAlarmBlackListRow(AlarmBlackListBean row);

	/**
	 * 设备黑白名单接口/端口列表中删除行
	 * @param row
	 */
	boolean deleteAlarmBlackPortListRows(AlarmBlackPortListBean row);

	/**
	 * 更新设备黑白名单设置列表
	 * @param alarmBlackListBean
	 * @return
	 */
	boolean updateIntoAlarmBlackList(AlarmBlackListBean alarmBlackListBean);

	/**
	 * 设备黑白名单接口/端口列表
	 * @param alarmBlackPortListBean
	 * @return 
	 */
	boolean updateIntoAlarmBlackPortList(AlarmBlackPortListBean alarmBlackPortListBean);

	/**
	 * 根据设备IP，接口端口类型获取BlackID
	 * @param deviceIP
	 * @param portType
	 * @return
	 */
	Integer getBlackIDByDeviceIPAndPortType(String deviceIP,String portType);

	 /**
	  * 获取性能采集机
	 * @return
	 */
	Map<String, Object>  getColletorList();

	/**
	 * 根据设备IP获取MOID
	 * @return
	 */
	Long getMOIDByDeviceIp(String deviceIP);

	/**
	 * 向黑白名单任务列表【SysBlackListTask】插入数据
	 * @param sysBlackListTaskBean
	 */
	Integer insertIntoSysBlackListTask(SysBlackListTaskBean sysBlackListTaskBean);

	/**
	 * 向黑白名单监测器列表【SysBlackListMonitors】插入数据
	 * @param sysBlackListMonitorsBean
	 */
	Integer insertIntoSysBlackListMonitors(SysBlackListMonitorsBean sysBlackListMonitorsBean);

	/**
	 * 获取nmap监测器信息
	 * @param string
	 * @return
	 */
	Map<String, Object> getMonitorInfo(String string);
	
	/**
	 * 获取设备服务端口
	 * @return
	 */
	List<MODevice> getPortListByDeviceIP(Page<MODevice> page);

	/**
	 * 判断SysBlackListTask是否已经有对应的BlackID
	 * @param blackID
	 * @return
	 */
	boolean checkBlackIDInSysBlackListTask(Integer blackID);

	/**
	 * 修改SysBlackListTask状态
	 * @param blackID
	 * @param operateStatus
	 * @param collectorID
	 * @return
	 */
	boolean updateSysBlackListTask(Integer blackID, String operateStatus, String collectorID,String oldCollectorID);
	
	boolean checkBlackIDAndPort(AlarmBlackPortListBean alarmBlackPortListBean);

	boolean checkDeviceIP(String deviceip, String portType);
}
