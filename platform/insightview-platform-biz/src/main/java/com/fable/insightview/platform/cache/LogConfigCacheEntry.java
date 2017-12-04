package com.fable.insightview.platform.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.modulelog.entity.LogConfigBean;

/**
 * @author fangang
 * 日志配置缓存
 */
public class LogConfigCacheEntry implements Serializable {
	private static final long serialVersionUID = 1L;
	private Map<String, LogConfigBean> logConfigs;

	/**
	 * 
	 * @param sysId
	 * @param lst
	 */
	public LogConfigCacheEntry(List<LogConfigBean> lst) {
		logConfigs = new HashMap<String, LogConfigBean>();
		for(LogConfigBean obj : lst) {
			LogConfigBean newObj = new LogConfigBean();
			newObj.setIsStart(obj.getIsStart());
			newObj.setTimeCfg(obj.getTimeCfg());
			logConfigs.put(obj.getType(), newObj);
		} 
	}
	
	/**
	 * @param type
	 * @return
	 */
	public LogConfigBean getLogConfig(String type){
		return logConfigs.get(type);
	}
	
	/**
	 * 
	 * @param type
	 * @param obj
	 */
	public void addLogConfig(String type, LogConfigBean obj){
		synchronized(logConfigs) {
			if(!logConfigs.containsKey(type))
				logConfigs.put(type, obj);
		}
	}
	
	/**
	 * @param type
	 * @return
	 */
	public boolean containType(String type) {
		return logConfigs.containsKey(type);
	}
	
	/**
	 * @return
	 */
	public static String getCacheKey(String sysId) {
		return sysId + "_LogConfigs";
	}
}
