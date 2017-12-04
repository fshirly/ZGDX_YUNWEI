package nl.justobjects.pushlet.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterParamObject {
	
	private List alarmLevel;
	private List alarmType;
	private List alarmSourceObj;
	private List alarmEvent; 
	private Map roomFilter;
	private String topoFilter;

	public FilterParamObject(){
		alarmLevel = new ArrayList();
		alarmType = new ArrayList();
		alarmSourceObj = new ArrayList();
		alarmEvent = new ArrayList();
		roomFilter = new HashMap();
		topoFilter = "";
	}
	
	public String getTopoFilter() {
		return topoFilter;
	}

	public void setTopoFilter(String topoFilter) {
		this.topoFilter = topoFilter;
	}

	public Map getRoomFilter() {
		return roomFilter;
	}

	public void setRoomFilter(Map roomFilter) {
		this.roomFilter = roomFilter;
	}

	public List getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(List alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public List getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(List alarmType) {
		this.alarmType = alarmType;
	}
	public List getAlarmSourceObj() {
		return alarmSourceObj;
	}
	public void setAlarmSourceObj(List alarmSourceObj) {
		this.alarmSourceObj = alarmSourceObj;
	}
	public List getAlarmEvent() {
		return alarmEvent;
	}
	public void setAlarmEvent(List alarmEvent) {
		this.alarmEvent = alarmEvent;
	}
}
