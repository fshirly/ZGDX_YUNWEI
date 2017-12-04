package com.fable.insightview.platform.common.dynamicdb.api;


/**
 * 操作动态表结构的接口
 * 包含创建表、添加字段、删除字段、删除表
 * @author 郑自辉
 *
 */
public interface IDynamicDB {

	/**
	 * 创建表结构
	 * @param tableName 表名
	 */
	void createTable(String tableName);
	
	/**
	 * 创建表
	 * @param tableName 表名
	 * @param primaryKey 主键名称
	 */
	void createTable(String tableName,String primaryKey);
	
	/**
	 * 添加字段
	 * @param tableName 表名
	 * @param columName 字段名
	 * @param columnType 字段类型
	 */
	void addColumn(String tableName,String columName,String columnType);
	
	/**
	 * 添加字段
	 * @param tableName 表名
	 * @param columnName 字段名
	 * @param javaType 字段JAVA类型
	 * @param length 字段长度
	 */
	void addColumn(String tableName,String columnName,String javaType,int length);
	
	/**
	 * 删除字段
	 * @param tableName 表名
	 * @param colomnName 字段名
	 * @param isLogic 是否逻辑删除
	 */
	void removeColumn(String tableName,String colomnName,boolean isLogic);
	
	/**
	 * 删除表结构
	 * @param tableName 表名
	 */
	void dropTable(String tableName);
	
}
