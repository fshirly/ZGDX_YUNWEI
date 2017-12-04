package com.fable.insightview.platform.importdata.resolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface Mapping {
	int columnIdex() default 0; //��һ��
	String columnName() default "";//�����
}
