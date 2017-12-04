package com.fable.insightview.platform.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.ISysMailServerDao;
import com.fable.insightview.platform.entity.SysMailServerConfigBean;
import com.fable.insightview.platform.service.ISysMailServerService;

/**
 * 邮件设置Service
 * 
 * @author 王淑平
 * 
 */
@Service("sysMailServerService")
public class SysMailServerServiceImpl implements ISysMailServerService {

	@Autowired
	protected ISysMailServerDao mailServerDao;

	@Override
	public boolean addSysMailConfig(SysMailServerConfigBean mailBean) {
		return mailServerDao.addSysMailConfig(mailBean);
	}

	@Override
	public boolean updateSysMailConfig(SysMailServerConfigBean mailBean) {
		return mailServerDao.updateSysMailConfig(mailBean);
	}

	@Override
	public List<SysMailServerConfigBean> getMailServerConfigInfo() {
		return mailServerDao.getMailServerConfigInfo();
	}

}
