package com.fable.insightview.platform.dialect;

import com.fable.insightview.platform.support.MyBatisContextMap;

public abstract class AbstractDialect extends MyBatisContextMap implements IDialect {

	public static enum Type { 
		MYSQL, ORACLE, SYBASE, DB2, MSSQL
	}
	
	public abstract Type getDbType();
}
