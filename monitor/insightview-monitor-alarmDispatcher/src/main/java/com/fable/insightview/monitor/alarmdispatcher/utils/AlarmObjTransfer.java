package com.fable.insightview.monitor.alarmdispatcher.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.monitor.event.EventType;
import com.fable.insightview.platform.event.BaseEvent;

public class AlarmObjTransfer {

	public static Logger logger = LoggerFactory.getLogger(AlarmObjTransfer.class);

	public static BaseEvent transfer(AlarmNode node) {

		BaseEvent basenode = new BaseEvent();

		// alarmStatus 11-新告警 12-自动清除 13-自动次数重复；14-自动次数重复,且级别升级
		// alarmOperateStatus 21-未确认    22-已确认    23-已派发    24-人工清除
		if (node.getAlarmStatus() == 11 && node.getAlarmOperateStatus() == 21) {
			basenode.setEventName(EventType.ALARM_NEW.name());
		} else if (node.getAlarmStatus() == 12
				|| node.getAlarmOperateStatus() == 24) {
			basenode.setEventName(EventType.ALARM_CLEAR.name());
		} else if (node.getAlarmStatus() == 14) {
			basenode.setEventName(EventType.ALARM_UPGRADE.name());
		} else if (node.getAlarmStatus() == 13) {
			basenode.setEventName(EventType.ALARM_UPDATE.name());
		}

		basenode.setTarget(node);
		return basenode;
	}
}