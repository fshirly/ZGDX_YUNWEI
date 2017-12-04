package com.fable.insightview.platform.modulelog.service;

import com.fable.insightview.platform.modulelog.entity.FbsSessionLogBean;


/**
 * 系统登录日志管理Service接口
 * 
 * @author zhouwei
 */
public interface SessionLogService {
	int selectBySessionId(String sessionId);
	
	int insert(FbsSessionLogBean record);
}
