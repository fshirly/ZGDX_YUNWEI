package com.fable.insightview.platform.common.dynamicdb.jdbc.sqltype;

import java.util.List;
/**
 * 不同数据库的类型映射
 * @author 郑自辉
 *
 */
public class SqlType {

	/**
	 * 包含的数据库类型
	 */
	private List<Type> types;
	
	/**
	 * 表单设计的数据类型
	 */
	private List<Type> dataTypes;

	public List<Type> getTypes() {
		return types;
	}

	public void setTypes(List<Type> types) {
		this.types = types;
	}

	public List<Type> getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(List<Type> dataTypes) {
		this.dataTypes = dataTypes;
	}
}
