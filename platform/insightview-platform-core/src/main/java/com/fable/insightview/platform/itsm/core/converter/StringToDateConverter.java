package com.fable.insightview.platform.itsm.core.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * STRING 类型转换为 DATE类型
 * 
 * @author 汪朝 20130930
 * 
 */
public class StringToDateConverter implements Converter<String, Date> {

	/**
	 * 时间型
	 */
	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 日期型
	 */
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public Date convert(String text) {
		if (StringUtils.hasText(text)) {
			try {
				if (text.indexOf(":") == -1 && text.length() == 10) {
					return this.dateFormat.parse(text);
				} else if (text.indexOf(":") > 0 && text.length() == 19) {
					return this.datetimeFormat.parse(text);
				} else {
					throw new IllegalArgumentException("Could not parse date, date format is error !");
				}
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		} else {
			return null;
		}
	}
}
