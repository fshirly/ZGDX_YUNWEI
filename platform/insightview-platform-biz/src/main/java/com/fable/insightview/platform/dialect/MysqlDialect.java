package com.fable.insightview.platform.dialect;

import com.fable.insightview.platform.page.Page;

public class MysqlDialect extends AbstractDialect {
	
	@Override
	public Type getDbType() {
		return Type.MYSQL;
	}
	
	public String getPageSql(Page<?> page, StringBuffer sqlBuffer) {
		if (page.getPageNo()!=0) {
			int offset = (page.getPageNo() - 1) * page.getPageSize();
			sqlBuffer.append(" limit ").append(offset).append(",")
			.append(page.getPageSize());
			System.out.println(">>>>>>>>>>query sql = " + sqlBuffer.toString());
			return sqlBuffer.toString();
		}else {
			System.out.println(">>>>>>>>>>query sql = " + sqlBuffer.toString());
			return sqlBuffer.toString();
		}
	}

	@Override
	public String currentTimeSql() {
		return "select sysdate()";
	}

	@Override
	public String toNumber(String param) {
		return "select cast(" + param + " as unsigned int)";
	}

	@Override
	public String toString(String param) {
		return "select cast(" + param + " as char)";
	}

	@Override
	public String nvl(String p1, String p2) {
		return "ifnull("+p1+","+p2+")";
	}

	@Override
	public String sysdate() {
		return "sysdate()";
	}

	@Override
	public String when(String expr, String e1, String e2) {
		return "(case when "+ expr + " then " + e1 + " else " + e2 + " end)";
	}

	@Override
	public String concat(String p1, String p2) {
		return "concat(" + p1 + "," + p2 +")";
	}

	@Override
	public String concat(String p1, String p2, String p3) {
		return "concat(" + p1 + "," + p2 + "," + p3 +")";
	}
	
	@Override
	public String concat(String p1, String p2, String p3, String p4, String p5) {
		return "concat(" + p1 + "," + p2 + "," + p3 + "," + p4 +"," + p5 +")";
	}
	
	@Override
	public String limit(int num) {
		return " limit 0," + num;
	}

	@Override
	public String rownum(int num) {
		return " 1=1";
	}

	@Override
	public String groupConcat(String fieldName) {
		return "GROUP_CONCAT(" + fieldName + ")";
	}

	@Override
	public String toDate(String date, String format) {
		return "str_to_date('" + date + "', '" + format + "')";
	}

	@Override
	public String toDate(String date) {
		return "str_to_date('" + date + "', '%Y-%m-%d %H:%i:%s')";
	}

	@Override
	public String dateFormat(String date) {
		return " DATE_FORMAT(" + date + ", '%Y-%m-%d %H:%i:%s')";
	}

	@Override
	public String concat(String p1, String p2, String p3, String p4, String p5,
			String p6, String p7) {
		return "concat(" + p1 + "," + p2 + "," + p3 + "," + p4 +"," + p5 +"," + p6 +"," + p7 +")";
	}
	
}
