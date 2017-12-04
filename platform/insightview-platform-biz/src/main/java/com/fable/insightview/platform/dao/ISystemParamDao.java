package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.SystemParamBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISystemParamDao extends GenericDao<SystemParamBean> {
	List<SystemParamBean> querySystemParam(SystemParamBean systemParamBean);
	/**
	 * 查询所有
	 * @param systemParamBean
	 * @return
	 */
	List<SystemParamBean> sysParamInfo(SystemParamBean systemParamBean, FlexiGridPageInfo flexiGridPageInfo);
	/**
	 * 根据参数ID获取相关信息  用于修改
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

