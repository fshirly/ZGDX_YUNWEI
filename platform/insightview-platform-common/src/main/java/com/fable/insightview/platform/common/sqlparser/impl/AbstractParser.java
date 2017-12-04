/**
 * 
 */
package com.fable.insightview.platform.common.sqlparser.impl;

import com.fable.insightview.platform.common.sqlparser.Parser;

/**
 * @author Administrator
 *
 */
public abstract class AbstractParser implements Parser {
	@Override
	public String getCountSql(String sql) {
		StringBuilder sqlBuilder = new StringBuilder(sql.length() + 30);
		sqlBuilder.append("select count(1) from (");
        sqlBuilder.append(sql);
		sqlBuilder.append(" ) t");
        return sqlBuilder.toString();
	}
}
