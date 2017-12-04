package com.fable.insightview.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.ISystemParamDao;
import com.fable.insightview.platform.entity.SystemParamBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.service.ISystemParamService;

/**
 * 单位组织Service
 * 
 * @author 武林
 * 
 */
@Service("systemParamServiceImpl")
public class SystemParamServiceImpl implements ISystemParamService {

	@Autowired
	protected ISystemParamDao systemParamDao;
	@Override
	public SystemParamBean querySystemParam(SystemParamBean systemParamBean) {
		return systemParamDao.querySystemParam(systemParamBean).get(0);
	}
	@Override
	public List<SystemParamBean> sysParamInfo(SystemParamBean systemParamBean, FlexiGridPageInfo flexiGridPageInfo) {
		List<SystemParamBean> sysparamLst = systemParamDao.sysParamInfo(systemParamBean, flexiGridPageInfo);
		return sysparamLst;
	}

	@Override
	public int getTotalCount(SystemParamBean systemParamBean) {
		return systemParamDao.getTotalCount(systemParamBean);
	}
	@Override
	public int updateDepartmentBean(String paramValue,int parID) {
		return systemParamDao.updateDepartmentBean(paramValue,parID);
	}
	@Override
	public List<SystemParamBean> getSysParamBeanByConditions(String paramName,
			String paramValue) {
		return systemParamDao.getSysParamBeanByConditions(paramName, paramValue);
	}

}
