package com.fable.insightview.platform.importdata.resolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.importdata.resolver.metadata.Processor;

public class ProcessorImpl {

	private final static String FIELD_HANDLE_SUFFIX = "Handle";

	private String fieldName;

	private Object entity;

	private Processor processor;

	private String methodName;

	private Object target;

	private Object[] params;

	public ProcessorImpl(String fieldName, Processor processor, Object entity) {
		this.fieldName = fieldName;
		this.processor = processor;
		this.entity = entity;

		if (!StringUtils.isEmpty(processor.methodName())) {
			this.methodName = processor.methodName();
		} else {
			if (!StringUtils.isEmpty(fieldName)) {
				this.methodName = fieldName + FIELD_HANDLE_SUFFIX;
			} else {
				
				String simpleName = entity.getClass().getSimpleName();
				this.methodName = simpleName.substring(0, 1).toLowerCase()
						+ simpleName.substring(1) + FIELD_HANDLE_SUFFIX;
			}
		}

		if (!StringUtils.isEmpty(processor.targetBean())) {
			target = BeanLoader.getBean(processor.targetBean());
		} else {
			target = entity;
		}
	}

	public void invoke() {
		try {
			Method method = ReflectionUtils.findMethod(target.getClass(),methodName, null);
			if(method.getParameterTypes().length == 1){
				params = new Object[1];
				if (!StringUtils.isEmpty(fieldName)) {
					this.params[0] = PropertyUtils.getProperty(entity, fieldName);
				}else{
					this.params[0] = entity;
				}
			}else if(method.getParameterTypes().length == 2){
				params = new Object[2];
				if (!StringUtils.isEmpty(fieldName)) {
					this.params[0] = PropertyUtils.getProperty(entity, fieldName);
				}
				this.params[1] = entity;
			}
			
			Object result = ReflectionUtils.invokeMethod(method, target,
					params);
			if (!StringUtils.isEmpty(fieldName)) {
				PropertyUtils.setProperty(entity, fieldName, result);
			}
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public final String getMethodName() {
		return methodName;
	}

	public final void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public final Object[] getParams() {
		return params;
	}

	public final void setParams(Object[] params) {
		this.params = params;
	}

	public final Object getTarget() {
		return target;
	}

	public final void setTarget(Object target) {
		this.target = target;
	}
}
