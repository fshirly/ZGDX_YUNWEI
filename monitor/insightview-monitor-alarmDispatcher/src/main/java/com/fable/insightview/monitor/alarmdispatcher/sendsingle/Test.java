package com.fable.insightview.monitor.alarmdispatcher.sendsingle;

import com.fable.insightview.monitor.entity.AlarmNode;

public class Test {
	public static void main(String[] args) {
		AlarmLevelHandler l = new AlarmLevelHandler();
		AlarmTypeHandler t = new AlarmTypeHandler();
		AlarmDefineHandler d = new AlarmDefineHandler();
		AlarmSourceHandler s = new AlarmSourceHandler();
		l.setSuccessor(t);
		t.setSuccessor(d);
		d.setSuccessor(s);
		
		// 监听告警等级1,2,3
//		l.getKeys().add(1);
		// 告警类型
		t.getKeys().add(2);
		// 告警定义
//		d.getKeys().add(2);
		// 告警源
//		s.getKeys().add(10001);
		
		AlarmNode a = new AlarmNode();
		a.setAlarmLevel(1);
		a.setAlarmType(1);
		a.setSourceMOID(10000);
		a.setAlarmDefineID(1);
		
		int sum = l.getKeys().size();
		sum += t.getKeys().size();
		sum += d.getKeys().size();
		sum += s.getKeys().size();
		if(0 == sum){
			System.out.println(false);
		} else {
			System.out.println(l.handleRequest(a));
		}
	}
}
