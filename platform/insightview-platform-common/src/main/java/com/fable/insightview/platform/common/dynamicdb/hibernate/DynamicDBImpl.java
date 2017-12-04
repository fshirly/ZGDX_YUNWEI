package com.fable.insightview.platform.common.dynamicdb.hibernate;

import static com.fable.insightview.platform.common.dynamicdb.hibernate.DynamicDBConstants.CUSTOM_COMPONENT_NAME;

import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

/**
 * 动态模型的基础实现类
 * 主要保存实体名称和动态字段组件
 * 提供动态组件的查询
 * @author 郑自辉
 *
 */
public class DynamicDBImpl {
	
	/**
	 * 动态字段，相对于dynamic-component，为Map类型
	 */
	private Component customProperties;

	/**
	 * 获取动态组件集合
	 * @return
	 */
	public Component getCustomProperties(String entityName)
	{
		if (null == customProperties)
		{
			Property property = getPersistentClass(entityName).getProperty(CUSTOM_COMPONENT_NAME);
			customProperties = (Component) property.getValue();
		}
		return customProperties;
	}
	
	/**
	 * 获取持久化映射类
	 * @return PersistentClass
	 */
	protected PersistentClass getPersistentClass(String entityName)
	{
		return DynamicDBUtil.getInstance().getClassMapping(entityName);
	}
}
