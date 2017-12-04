package nl.justobjects.pushlet.core;

import java.util.HashMap;
import java.util.Map;

import nl.justobjects.pushlet.util.PushletException;

import com.fable.insightview.monitor.entity.AlarmNode;
 

/*
 * 告警过滤器
 * **/

public class Filter {

	private int filterID;

	private Map<String, Object> params = new HashMap<String, Object>();

	public int getFilterID() {
		return filterID;
	}

	public void setFilterID(int filterID) {
		this.filterID = filterID;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public static Filter create(String userID) throws PushletException {
		Filter filter;
		try {
			filter = (Filter) Config.getClass("","nl.justobjects.pushlet.core.Filter").newInstance();
		} catch (Throwable t) {
			throw new PushletException("Cannot instantiate Controller from config", t);
		} 
		return filter;
	}
	
	public boolean matching(AlarmNode alarm) {

		return true;
	}
}