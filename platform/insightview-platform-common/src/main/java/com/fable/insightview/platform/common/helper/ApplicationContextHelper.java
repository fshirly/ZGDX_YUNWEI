package com.fable.insightview.platform.common.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
/**
 * 保存对应的Spring容器，供以后获取bean使用
 * @author 郑自辉
 *
 */
public class ApplicationContextHelper implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		ApplicationContextHelper.applicationContext = arg0;
	}
	
	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	public static Object getBean(String beanName)
	{
		return applicationContext.getBean(beanName);
	}
	
	public static <T> T getBean(String beanName,Class<T> c)
	{
		return applicationContext.getBean(beanName, c);
	}
	
	public static void publishEvent(ApplicationEvent event) {
		applicationContext.publishEvent(event);
	}
}
