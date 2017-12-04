package com.fable.insightview.monitor.middleware.tomcat.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.middleware.tomcat.mapper.MiddlewareSyncMapper;
import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareSyncService;

@Service
public class MiddlewareSyncServiceImpl implements IMiddlewareSyncService {
	@Autowired
	private MiddlewareSyncMapper middlewareSyncMapper;
	
	@SuppressWarnings("all")
	public List<Map> queryByTableName(String tableName){
		Map map = new HashMap<String, String>();
		map.put("tableName", tableName);
		return middlewareSyncMapper.selectByTableName(map);
	}
}
