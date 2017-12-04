package com.fable.insightview.monitor.alarmmgr.devicewblistsetting.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.AlarmBlackListBean;
import com.fable.insightview.monitor.harvester.entity.SysServerHostInfo;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.platform.page.Page;


public interface AlarmBlackListMapper {
	List<AlarmBlackListBean> selectAlarmBlackList(Page page);
	List<MODevice> selectInterfaceListByDeviceIP(Page<MODevice> page);
	Integer insertIntoAlarmBlackList(AlarmBlackListBean alarmBlackListBean);
	Integer deleteAlarmBlackListRow(AlarmBlackListBean row);
	Integer updateIntoAlarmBlackList(AlarmBlackListBean row);
	Integer selectBlackIDByDeviceIPAndPortType(@Param("deviceIP")String deviceIP,@Param("portType")String portType);
	Long getMOIDByDeviceIp(@Param("deviceIP")String deviceIP);
	Map<String, Object> selectMonitorInfo(@Param("kind")String kind);
	List<MODevice> selectPortListByDeviceIP(Page<MODevice> page);
	List<MODevice> selectChainListByDeviceIP(Page<MODevice> page);
}