package com.fable.insightview.platform.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.dynamicdb.Column;
import com.fable.insightview.platform.common.dynamicdb.api.IDynamicDBQuery;
import com.fable.insightview.platform.common.service.IJdbcDBQueryService;

@Service("jdbcDBQueryService")
public class JdbcDBQueryServiceImpl implements IJdbcDBQueryService {
	
	@Autowired
	private IDynamicDBQuery jdbcDBQuery;

	@Override
	public String save(Map<String, Object> map, String compTableName) {
		return jdbcDBQuery.save(map, compTableName);
	}

	@Override
	public void update(Map<String, Object> map, String compTableName) {
		jdbcDBQuery.update(map, compTableName);
	}

	@Override
	public List<Map<String, String>> listComp(List<String> compIds,	String tableName) {
		return jdbcDBQuery.listComp(compIds, tableName);
	}

	@Override
	public List<Column> listColumn(String[] tableName) {
		return jdbcDBQuery.listColumn(tableName);
	}

	@Override
	public Map<String, Object> list(String ciTypeID,
			Map<String, String> condition, int start, int rows,
			String[] tableName) {
		return jdbcDBQuery.list(ciTypeID,condition, start, rows, tableName);
	}
}
