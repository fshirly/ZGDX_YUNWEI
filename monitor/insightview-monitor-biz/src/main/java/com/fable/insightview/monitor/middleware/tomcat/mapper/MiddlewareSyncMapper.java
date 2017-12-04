package com.fable.insightview.monitor.middleware.tomcat.mapper;

import java.util.List;
import java.util.Map;

public interface MiddlewareSyncMapper {
	@SuppressWarnings("rawtypes") 
	List<Map> selectByTableName(Map map);
}
