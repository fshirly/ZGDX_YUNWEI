package com.fable.insightview.platform.core.dao.idgenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface StringGenerator {
	String name();	
	/**
	 * 目前支持两个参数
	 * d：日期
	 * x：流水号
	 * X：当日流水号
	 * format="REC%d{yyMMdd}%x{4}"
	 * @return
	 */
	String format();
}
