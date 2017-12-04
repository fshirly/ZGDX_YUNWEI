package com.fable.insightview.platform.interceptor;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.springframework.util.ReflectionUtils;

import com.fable.insightview.platform.core.dao.idgenerator.impl.IDGeneratorFactory;
import com.fable.insightview.platform.core.dao.idgenerator.impl.IDGeneratorFactory.FieldGenerator;

public class BatchIdsInterceptor {
	@SuppressWarnings("rawtypes")
	public void dynamicSetIds(JoinPoint joinPoint) {
		Object[] arguments = joinPoint.getArgs();
		for (Object param : arguments) {
			if (param instanceof List) {
				for (Object entity : (List) param) {
					setIdentifer(entity);
				}
			} else {
				setIdentifer(param);
			}
		}
	}
	private void setIdentifer(Object entity) {
		FieldGenerator fieldGen = IDGeneratorFactory.getInstance().getFieldGenerator(entity.getClass());
		if (fieldGen != null) {
			fieldGen.field.setAccessible(true);
			ReflectionUtils.setField(fieldGen.field, entity, fieldGen.gen.generate());
		}
	}
}
