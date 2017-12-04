package com.fable.insightview.platform.dialect;

import com.fable.insightview.platform.page.Page;

public class OracleDialect extends  AbstractDialect {
	
	@Override
	public Type getDbType() {
		return Type.ORACLE;
	}
	
	public String getPageSql(Page<?> page, StringBuffer sqlBuffer) {
		
		//计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
		int offset = (page.getPageNo() - 1) * page.getPageSize() + 1;
		sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(offset + page.getPageSize());
		sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
		return sqlBuffer.toString();
	}
	
	public String currentTimeSql() {
		return "select sysdate from dual";
	}

	public String toNumber(String param) {
		return "to_number("+param+")";
	}

	public String toString(String param) {
		return "to_char("+param+")";
	}

	public String concat(String p1, String p2) {
		return p1 + "||" + p2;
	}
	
	@Override
	public String concat(String p1, String p2, String p3) {
		return p1 + "||" + p2 +  "||" + p3;
	}
	
	public boolean supportFullJoin() {
		return true;
	}
	public String nvl(String p1, String p2) {
		return "nvl("+p1+","+p2+")";
	}

	@Override
	public String sysdate() {
		return "sysdate";
	}

	@Override
	public String when(String expr, String e1, String e2) {
		return "(case when "+ expr + " then " + e1 + " else " + e2 + " end)";
	}

	@Override
	public String limit(int num) {
		return "";
	}

	@Override
	public String rownum(int num) {
		return " rownum <= " + num;
	}

	@Override
	public String groupConcat(String fieldName) {
		return "wmsys.wm_concat(" + fieldName + ")";
	}

	@Override
	public String toDate(String date) {
		return "to_date('" + date + "', 'yyyy-mm-dd hh24:mi:ss')";
	}
	
	@Override
	public String toDate(String date, String format) {
		return "to_date('" + date + "', '" + format + "')";
	}

	@Override
	public String dateFormat(String date) {
		return "to_char(" + date + ", 'yyyy-mm-dd hh24:mi:ss')";
	}

	@Override
	public String concat(String p1, String p2, String p3, String p4, String p5) {
		return p1 + "||" + p2 +  "||" + p3+  "||" + p4+  "||" + p5;
	}

	@Override
	public String concat(String p1, String p2, String p3, String p4, String p5,
			String p6, String p7) {
		return p1 + "||" + p2 +  "||" + p3+  "||" + p4+  "||" + p5+  "||" + p6+  "||" + p7;
	}
}
