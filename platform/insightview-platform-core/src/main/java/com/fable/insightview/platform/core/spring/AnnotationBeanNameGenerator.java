package com.fable.insightview.platform.core.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import com.fable.insightview.platform.core.constants.ProductDefinition;

public class AnnotationBeanNameGenerator extends
		org.springframework.context.annotation.AnnotationBeanNameGenerator {

	@Override
	public String generateBeanName(BeanDefinition definition,
			BeanDefinitionRegistry registry) {
		String beanName = super.generateBeanName(definition, registry);
		return getFullBeanName(definition.getBeanClassName(), beanName);
	}
	
	public static String getFullBeanName(String className, String beanName){
		String[] fields = className.split("\\.");
		if (fields.length > 4
				&& ProductDefinition.companyName.equals(fields[1])
				&& ProductDefinition.productName.equals(fields[2])
				&& beanName.indexOf(".") < 0) {
			beanName = fields[3] + "." + beanName;
		}
		return beanName;
	}
}
