package com.fable.insightview.platform.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.fable.insightview.platform.core.spring.AnnotationBeanNameGenerator;

public final class BeanLoader implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	public static Object getBean(String beanName){
		if(beanName.indexOf(".") > 0){
			return applicationContext.getBean(beanName);
		}
		String fullBeanName = AnnotationBeanNameGenerator.getFullBeanName(getCallClassName(), beanName);
		try{
			return applicationContext.getBean(fullBeanName);
		}catch(BeansException e){
			return applicationContext.getBean(beanName);
		}
	}
	
	private static String getCallClassName(){
		StackTraceElement[] stackElements =  new Throwable().getStackTrace();
		return stackElements[2].getClassName();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
