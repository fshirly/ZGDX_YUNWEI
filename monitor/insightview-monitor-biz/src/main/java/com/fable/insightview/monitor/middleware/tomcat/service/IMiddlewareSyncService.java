package com.fable.insightview.monitor.middleware.tomcat.service;

import java.util.List;
import java.util.Map;

public interface IMiddlewareSyncService {
	@SuppressWarnings("rawtypes")
	public List<Map> queryByTableName(String tableName);
}
