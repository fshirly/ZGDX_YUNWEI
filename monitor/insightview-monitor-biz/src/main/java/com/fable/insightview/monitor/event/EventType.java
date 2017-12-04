package com.fable.insightview.monitor.event;

public enum EventType {
	ALARM_NEW,     //新告警	
	ALARM_CONFIRM, //确认告警
	ALARM_CLEAR,   //清除告警
	ALARM_DELETE,  //删除告警
	
	ALARM_UPGRADE, //告警升级
	ALARM_UPDATE,  //告警更新
	
	MOALARM_LEVEL_CHANGE, //监测对象最高告警级别变更
}
