package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.SysMailServerConfigBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface ISysMailServerDao extends GenericDao<SysMailServerConfigBean> {
     
	List<SysMailServerConfigBean> getMailServerConfigInfo();
	/* 新增邮件配置 */
	boolean addSysMailConfig(SysMailServerConfigBean mailBean);
	/* 修改邮件配置 */
	boolean updateSysMailConfig(SysMailServerConfigBean mailBean);


}
