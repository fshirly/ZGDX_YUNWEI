package com.fable.insightview.platform.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * 缓存帮助类
 *
 */
public final class CacheUtil {
	
	//创建一个线程安全map
	private Map<String, Object> map = Collections.synchronizedMap(new HashMap<String, Object>()); 
	
	private static CacheUtil cacheUtil = new CacheUtil();
	
	private CacheUtil()
	{
		
	}
	
	/**
	 * 单实例
	 */
	public static CacheUtil getInstance()
	{
		return cacheUtil;
	}
	
	/**
	 * 清除map
	 */
	public void clear()
	{
		map.clear();
	}
	
	/**
	 * 判断对象是否存在
	 */
	public boolean containsKey(String key)
	{
		if (map.containsKey(key))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * 将值添加到map中
	 */
	public void put(String key,Object value)
	{
		map.put(key, value);
	}
	
	/**
	 * 获取值
	 * 
	 */
	public Object get(String key)
	{
		return map.get(key);
	}
	


}
