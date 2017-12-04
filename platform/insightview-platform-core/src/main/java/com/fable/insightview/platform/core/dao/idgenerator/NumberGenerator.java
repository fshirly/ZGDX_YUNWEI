package com.fable.insightview.platform.core.dao.idgenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface NumberGenerator {
	String name();
	int allocationSize() default 10;
	int begin() default 10001;
}
