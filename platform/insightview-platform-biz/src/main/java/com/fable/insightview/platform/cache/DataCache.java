package com.fable.insightview.platform.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author fangang
 * 缓存数据类
 */
public class DataCache {

	/**
	 * 
	 */
	private DataCache() {
		cm = new CacheManager();
	}
	
	/**
	 * @return
	 */
	public static DataCache getInstance() {
		if(singleObject == null)
			singleObject = new DataCache();
		return singleObject;
	}
	
	/**
	 * @param key
	 * @param content
	 */
	public void addCache(String key, Object content) {
		Cache cache = cm.getCache(key);
		if(cache == null) {
			cache = new Cache(key, 0, false, false, 0, 0);
			cm.addCache(cache);
		}
		
		Element el = new Element(key, content);
		cache.put(el);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Object getCache(String name) {
		Cache ch = cm.getCache(name);
		if(ch == null)
			return null;
		return ch.get(name).getValue();
	}
	
	/**
	 * @param name
	 */
	public void removeCache(String name) {
		Cache ch = cm.getCache(name);
		if(ch != null)
			cm.removeCache(name);
	}
	
	private static DataCache singleObject;
	
	private CacheManager cm;
}
