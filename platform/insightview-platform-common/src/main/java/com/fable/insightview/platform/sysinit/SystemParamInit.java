package com.fable.insightview.platform.sysinit;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 
 * @author Administrator
 *
 */
public class SystemParamInit {

	private static ResourceBundle systemParamBundle;
	
	public static Map<String, Integer> mapCount = new HashMap<String, Integer>();
	
	//初始化文件服务器地址
	static {
		if(null == systemParamBundle){
			initBound();
		}
	}

	/**
	 * 根据配置文件里面的key获取值
	 * @param key
	 * @return
	 */
	public static String getValueByKey(String key){
		return systemParamBundle.getString(key);
	}
	
	private static void initBound(){
		if(systemParamBundle == null){
			systemParamBundle = ResourceBundle.getBundle("systemParam");
		}	
	}
	
}
