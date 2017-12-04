package com.fable.insightview.platform.importdata.resolver.metadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TextDataResolver{
	int startRow() default 1;
	int endRow() default 0;
	String charsetName() default "UTF-8";
	String lineSeparator() default "[\r\n]+";// 行分隔符
	String columnSeparator() default "\\|";// 列分隔符
}
