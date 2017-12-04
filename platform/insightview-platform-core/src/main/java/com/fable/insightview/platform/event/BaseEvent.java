package com.fable.insightview.platform.event;

public class BaseEvent {
	private String eventName;
	private Object target;
	
	public BaseEvent() {
	}

	public BaseEvent(String eventName) {
		super();
		this.eventName = eventName;
	}
	
	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
}
