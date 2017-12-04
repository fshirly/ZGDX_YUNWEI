package com.fable.insightview.platform.core.spring;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class InstantiationTracingBeanPostProcessor implements BeanPostProcessor {

	public static Logger logger = LoggerFactory
			.getLogger(InstantiationTracingBeanPostProcessor.class);

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println("-------" + beanName + "----------S:" + new Date());
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if (beanName.indexOf("#") < 0) {
			System.out.println("-------" + beanName + "----------E:" + new Date());
		}
		return bean;
	}

}
