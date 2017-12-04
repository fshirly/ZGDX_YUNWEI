package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.entity.SysMailServerConfigBean;

public interface ISysMailServerService {
	List<SysMailServerConfigBean> getMailServerConfigInfo();

	/* 新增邮件配置 */
	boolean addSysMailConfig(SysMailServerConfigBean mailBean);

	boolean updateSysMailConfig(SysMailServerConfigBean mailBean);
}
