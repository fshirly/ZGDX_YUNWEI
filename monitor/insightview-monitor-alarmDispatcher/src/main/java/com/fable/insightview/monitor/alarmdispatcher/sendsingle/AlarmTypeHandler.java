package com.fable.insightview.monitor.alarmdispatcher.sendsingle;

import com.fable.insightview.monitor.entity.AlarmNode;

public class AlarmTypeHandler extends Handler {
	@Override
	public boolean handleRequest(AlarmNode a) {
		boolean temp = true;
		if(0 != keys.size()){
			temp = keys.contains(a.getAlarmType()); 
		}
		if(null == getSuccessor()){
			return temp;	
		}
		return temp && getSuccessor().handleRequest(a);
	}
}
