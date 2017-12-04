package com.fable.insightview.platform.importdata.resolver.metadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ExcelDataResolver {
	int startRow() default 1;
	int endRow() default 0;
	String sheetName() default "";
	int sheetIndex() default 0;
}
