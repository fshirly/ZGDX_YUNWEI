package com.fable.insightview.platform.modulelog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.util.Cast;
import com.fable.insightview.platform.common.util.Tool;
import com.fable.insightview.platform.modulelog.entity.FbsSessionLogBean;
import com.fable.insightview.platform.modulelog.mapper.FbsSessionLogBeanMapper;
import com.fable.insightview.platform.modulelog.service.SessionLogService;

/**
 * 系统登录日志管理Service实现类
 * 
 * @author zhouwei
 */
@Service
public class SessionLogServiceImpl implements SessionLogService {
    //private static final Logger logger = Logger.getLogger(SessionLogServiceImpl.class);

    @Autowired
    private FbsSessionLogBeanMapper fbsSessionLogBeanMapper;

	@Override
	public int selectBySessionId(String sessionId) {
		return fbsSessionLogBeanMapper.selectBySessionId(sessionId);
	}

	@Override
	public int insert(FbsSessionLogBean record) {
		record.setId(Cast.guid2Str(Tool.newGuid()));
		return fbsSessionLogBeanMapper.insert(record);
	}

}
