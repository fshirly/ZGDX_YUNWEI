package com.fable.insightview.platform.common.dynamicdb.hibernate;
/**
 * 存放动态模型需要的常量
 * @author 郑自辉
 *
 */
public class DynamicDBConstants {

	/**
	 * 动态表对应的映射文件的位置
	 */
	public static String hbmFileDir;
	
	static
	{
		hbmFileDir = Thread.currentThread().getContextClassLoader().getResource("/hbm").getPath();
	}
	
	/**
	 * 动态模型模板文件
	 */
	public static final String templateHbmFileName = "tbl.hbm.xml";
	
	/**
	 * 动态字段组件名称
	 */
	public static final String CUSTOM_COMPONENT_NAME = "customProperties";
	
	/**
	 * 动态表主键名称
	 */
	public static final String PRIMARY_KEY_NAME = "id";
}
