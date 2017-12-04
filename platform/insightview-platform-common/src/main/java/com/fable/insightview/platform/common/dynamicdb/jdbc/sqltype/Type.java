package com.fable.insightview.platform.common.dynamicdb.jdbc.sqltype;
/**
 * 字段类型Model
 * java类型与数据库类型的映射关系
 * @author 郑自辉
 *
 */
public class Type {

	/**
	 * java类型
	 */
	private String java;
	
	/**
	 * 数据库类型
	 */
	private String sql;

	public String getJava() {
		return java;
	}

	public void setJava(String java) {
		this.java = java;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
