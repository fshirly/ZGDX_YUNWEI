/**
 * 
 */
package com.fable.insightview.platform.common.sqlparser.impl;

import com.fable.insightview.platform.common.sqlparser.Page;
import com.fable.insightview.platform.common.util.StringUtil;

/**
 * @author zhouwei
 * 
 */
public class MysqlParser extends AbstractParser {

	@Override
	public String toDate(String value, String type) {
		String format = "";
		if (StringUtil.equalsIgnoreCase("date", type)) {
			format = "%Y-%m-%d";
		} else if (StringUtil.equalsIgnoreCase("datetime", type)) {
			format = "%Y-%m-%d %H:%i:%S";
		} else {
			return "";
		}
		return "date_format(" + value + ", '" + format + "')";
	}

	@Override
	public <T> String getPageSql(String sql, Page<T> page) {
		StringBuilder sqlBuilder = new StringBuilder(sql.length() + 20);
        sqlBuilder.append(sql);
        sqlBuilder.append(" limit "+page.getStartRow()+","+page.getPageSize());
        return sqlBuilder.toString();
	}

}
