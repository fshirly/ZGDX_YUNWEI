package com.fable.insightview.platform.dialect;

import com.fable.insightview.platform.page.Page;


public interface IDialect {

	String getPageSql(Page<?> page, StringBuffer sqlBuffer);
	
	String currentTimeSql();
	
	String toNumber(String param);
	
	String toString(String param);
	
	String concat(String p1, String p2);
	
	String concat(String p1, String p2, String p3);
	
	String concat(String p1, String p2, String p3, String p4, String p5);
	
	String concat(String p1, String p2, String p3, String p4, String p5, String p6, String p7);
	
	String nvl(String p1, String p2);
	
	String sysdate();
	
	String when(String expr, String e1, String e2);

	String rownum(int num);
	
	String limit(int num);
	
	String groupConcat(String fieldName);
	
	String toDate(String date, String format);
	
	String toDate(String date);
	
	String dateFormat(String date);
}
