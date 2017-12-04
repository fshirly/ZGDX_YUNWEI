package com.fable.insightview.platform.interceptor;

import org.aspectj.lang.JoinPoint;
import org.springframework.util.ReflectionUtils;

import com.fable.insightview.platform.core.dao.idgenerator.impl.IDGeneratorFactory;
import com.fable.insightview.platform.core.dao.idgenerator.impl.IDGeneratorFactory.FieldGenerator;
public class IdInterceptor{
	public void dynamicSetId(JoinPoint joinPoint){
        Object[] arguments = joinPoint.getArgs();
        for(Object param : arguments){
        	FieldGenerator fieldGen = IDGeneratorFactory.getInstance().getFieldGenerator(param.getClass());
        	if(fieldGen != null){
        		fieldGen.field.setAccessible(true);
        		ReflectionUtils.setField(fieldGen.field, param, fieldGen.gen.generate());
        	}
        }
	}
}
