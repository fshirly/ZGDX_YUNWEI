package com.fable.insightview.platform.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Administrator
 * 
 */
public class DateUtil {
	private static final Logger logger = LogManager.getLogger();
	public static final String DATA_PATTERN_DATE ="yyyy-MM-dd";
	private static final String TIME_ZONE_CN = "GMT-8";
	private static SimpleDateFormat sdf = null;
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String2(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return date == null ? "" : sdf.format(date);
	}
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return date == null ? "" : sdf.format(date);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date string2Date(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("日期转换错误",str);;
		}
		return null;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date string2Date2(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("日期转换错误",str);;
		}
		return null;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date string2Date3(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("日期转换错误",str);;
		}
		return null;
	}
	
	public static Date getFirstDayOfMonth(Date d) {
		sdf = new SimpleDateFormat(DATA_PATTERN_DATE);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(sdf.format(d)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	public static Date getLasyDayOfMonth(Date d) {
		sdf = new SimpleDateFormat(DATA_PATTERN_DATE);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(sdf.format(d)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	public static Date getFirstDayOfWeek(Date d) {
		sdf = new SimpleDateFormat(DATA_PATTERN_DATE);
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_CN));
		try {
			cal.setTime(sdf.parse(sdf.format(d)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
		return cal.getTime();
	}
	
	public static Date getLastDayOfWeek(Date d) {
		sdf = new SimpleDateFormat(DATA_PATTERN_DATE);
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_CN));
		try {
			cal.setTime(sdf.parse(sdf.format(d)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
		return cal.getTime();
	}
	
	public static Date getFirstTimeOfDay(Date d) {
		sdf = new SimpleDateFormat(DATA_PATTERN_DATE);
		Date ret = null;
		try {
			ret = sdf.parse(sdf.format(d));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static Date getLastTimeOfDay(Date d) {
		sdf = new SimpleDateFormat(DATA_PATTERN_DATE);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}
	
}
