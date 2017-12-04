package com.fable.insightview.platform.common.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.common.dynamicdb.Column;

/**
 * JDBC形式 操作 数据库
 * @author sanyou
 *
 * @Date 2014年6月10日
 */
public interface IJdbcDBQueryService {

	String save(Map<String, Object> map,String compTableName);
	
	void update(Map<String, Object> map,String compTableName);
	
	List<Map<String, String>> listComp(List<String> compIds, String tableName);
	
	List<Column> listColumn(String[] tableName);
	
	Map<String, Object> list(String ciTypeID, Map<String, String> condition, int start, int rows, String[] tableName);
}
