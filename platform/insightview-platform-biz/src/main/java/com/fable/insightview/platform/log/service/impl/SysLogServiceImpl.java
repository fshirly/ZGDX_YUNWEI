package com.fable.insightview.platform.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.log.entity.SysLog;
import com.fable.insightview.platform.log.mapper.ISysLogMapper;
import com.fable.insightview.platform.log.service.ISysLogService;

@Service
public class SysLogServiceImpl implements ISysLogService {

	@Autowired
	private ISysLogMapper logMapper;
	
	@Override
	public void insertSysLog(SysLog sysLog) {
		logMapper.insertSysLog(sysLog);
	}

}
