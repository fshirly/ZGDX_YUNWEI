package com.fable.insightview.monitor.alarmdispatcher.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.alarmdispatcher.core.ReceiveOperateAlarm;
import com.fable.insightview.monitor.entity.AlarmNode;

import nl.justobjects.pushlet.core.Event;
 
/**
 * 当json太长超过属性长度时,分割发送到实时告警 
 */
public class AlarmSplit {
	
	public static Logger logger = LoggerFactory
			.getLogger(ReceiveOperateAlarm.class);

	public static void splitStr(Event event, AlarmNode alarm) {
		String jsonAlarm = AlarmObjToJSON.writeMapJSON(alarm);
		logger.debug("jsonAlarm=="+jsonAlarm);
		int split = jsonAlarm.length() / 250 + 1;
		int length = jsonAlarm.length();
		event.setField("alarmStatus", alarm.getAlarmStatus());
		event.setField("alarmOperateStatus", alarm.getAlarmOperateStatus());
		event.setField("alarmsound", alarm.getAlarmLevel());
		event.setField("alarmID", alarm.getAlarmID());
		switch (split) {
		case 1:
			event.setField("section", 1);
			event.setField("alarmdetail1", jsonAlarm);
			break;
		case 2:
			event.setField("section", 2);
			event.setField("alarmdetail1", jsonAlarm.substring(0, 250));
			event.setField("alarmdetail2", jsonAlarm.substring(250, length));
			break;
		case 3:
			event.setField("section", 3);
			event.setField("alarmdetail1", jsonAlarm.substring(0, 250));
			event.setField("alarmdetail2", jsonAlarm.substring(250, 500));
			event.setField("alarmdetail3", jsonAlarm.substring(500, length));
			break;
		default:
			event.setField("section", 4);
			event.setField("alarmdetail1", jsonAlarm.substring(0, 250));
			event.setField("alarmdetail2", jsonAlarm.substring(250, 500));
			event.setField("alarmdetail3", jsonAlarm.substring(500, 750));
			event.setField("alarmdetail4", jsonAlarm.substring(750, length));
			break;
		}
	}
	
	public static void spit3dRoomJson(Event event, AlarmNode alarm,int resId) {
		String jsonAlarm = AlarmObjToJSON.write3dMapJSON(alarm,resId);
		event.setField("alarmMessage", jsonAlarm);
	}
	
	public static void spitTopoJson(Event event, AlarmNode alarm) {
		String jsonAlarm = AlarmObjToJSON.writeTopoMapJSON(alarm);
		event.setField("alarmMessage", jsonAlarm);
	}
}