package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.entity.SystemParamBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISystemParamService {
	SystemParamBean querySystemParam(SystemParamBean systemParamBean);
	/**
	 * 查询列表并分页
	 * @param systemParamBean
	 * @param flexiGridPageInfo
	 * @return
	 */
	List<SystemParamBean> sysParamInfo(SystemParamBean systemParamBean, FlexiGridPageInfo flexiGridPageInfo);
	/**
	 * 根据参数ID或许相关信息  用于修改
	 * @param paramID
	 * @return
	 */
	List<SystemParamBean> getSysParamBeanByConditions(String paramName,
			String paramValue);
	/**
	 * 获取总数
	 * @param systemParamBean
	 * @return
	 */
	int getTotalCount(SystemParamBean systemParamBean);
	/**
	 * 修改
	 * @param systemParamBean
	 * @return
	 */
	int updateDepartmentBean(String paramValue,int parID);
	
	
}
