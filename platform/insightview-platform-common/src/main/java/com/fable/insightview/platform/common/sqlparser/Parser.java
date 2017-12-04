/**
 * 
 */
package com.fable.insightview.platform.common.sqlparser;

/**
 * @author zhouwei
 * 
 */
public interface Parser {

	/**
	 * 日期函数转换
	 * 
	 * @param value
	 * @param formatStr
	 * @return
	 */
	String toDate(String value, String formatStr);
	
	/**
	 * 得到countSql
	 * 
	 * @param sql
	 * @return
	 */
	String getCountSql(String sql);

	/**
	 * 分页sql
	 * 
	 * @param sql
	 * @return
	 */
	<T> String getPageSql(String sql, Page<T> page);
}
