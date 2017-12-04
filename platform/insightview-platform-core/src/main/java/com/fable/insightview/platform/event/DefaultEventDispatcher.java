package com.fable.insightview.platform.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultEventDispatcher implements EventDispatcher{
	
	public static Logger logger = LoggerFactory.getLogger(DefaultEventDispatcher.class);
	
	private volatile Map<String, TreeMap<Integer, List<Listener>>> listeners = new HashMap<String, TreeMap<Integer, List<Listener>>>();
	
	/**
	 * 添加侦听器
	 * @param type
	 * @param listener
	 * @param priority 越高，越优先收到事件
	 * @return
	 */
	public synchronized boolean  addEventListener(String eventName, Listener listener, int priority) {
		TreeMap<Integer, List<Listener>> list = listeners.get(eventName);
		if(list == null) {
			list = new TreeMap<Integer, List<Listener>>();
			listeners.put(eventName, list);
		}
		List<Listener> group = list.get(priority);
		if(group == null) {
			group = new ArrayList<Listener>();
			list.put(priority, group);
		}
		if(group.contains(listener))
			return false;
		
		group.add(listener);
		
		return true;
	}
	
	/**
	 * 发布一个事件，所以侦听器都在同一事务中完成
	 * @param event
	 * @return
	 */
	public boolean dispatchEvent(BaseEvent event) {
		logger.debug("dispatch Event: {}", event.getEventName());
		TreeMap<Integer, List<Listener>> list = listeners.get(event.getEventName());
		
		boolean success = true;
		if(list != null) {
			logger.debug("Event: {} has {} listeners", event.getEventName(), list.size());
			
			for(List<Listener> group : list.descendingMap().values()) {
				for(Listener listener : group) {
					try{
						if(!listener.onEvent(event)) {
							success = false;
						}
					}catch(Exception e){
						logger.error("dispatch Event: {}, listener execute fail!", event.getEventName(), e);
					}
				}
			}
		}
		return success;
	}
	
	/**
	 * 判断是否有某类型的侦听器
	 * @param type
	 * @return
	 */
	public boolean hasEventListener(String eventName) {
		TreeMap<Integer, List<Listener>> list = listeners.get(eventName);
		if(list != null) {
			for(List<Listener> group : list.values()) {
				if(group.size() > 0){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 删除一个侦听器
	 * @param type
	 * @param listener
	 * @return
	 */
	public boolean removeEventListener(String eventName, Listener listener) {
		TreeMap<Integer, List<Listener>> list = listeners.get(eventName);
		if(list != null) {
			for(List<Listener> group : list.descendingMap().values()) {
				for(Listener l : group) {
					if(l == listener) {
						group.remove(l);
						return true;
					}
				}
			}
		}
		return false;
	}

}
