package com.fable.insightview.platform.importdata.resolver.metadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Reloadable {
	boolean value() default true; //缓存 不重新加载
}
