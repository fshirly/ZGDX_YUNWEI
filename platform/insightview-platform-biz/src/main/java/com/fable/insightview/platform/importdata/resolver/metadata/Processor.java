package com.fable.insightview.platform.importdata.resolver.metadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Processor {
	String methodName() default "";//方法名称 默认方法为当前实体对象的method=fieldName+“Handle”，
	String targetBean() default "";//目标bean名称 默认为当前实体对象
}
