package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.SysExpertBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISysExpertDao extends GenericDao<SysExpertBean>{

	/**
	 * 按条件查询专家信息
	 * @param sysExpertBean
	 * @param flexiGridPageInfo
	 * @return
	 */
	List<SysExpertBean> getExpertByConditions(SysExpertBean sysExpertBean,
			FlexiGridPageInfo flexiGridPageInfo);

	/**
	 * 查询总记录数
	 * @param sysExpertBean
	 * @return
	 */
	int getTotalCount(SysExpertBean sysExpertBean);

	/**
	 * 根据id集合查询已存在的专家列表
	 * @param ids
	 * @return
	 */
	List<SysExpertBean> getSysExpertsInCurrentStep(String ids);

	/**
	 * 根据条件查询专家姓名
	 * @param participateStep
	 * @param projectStepId
	 * @param id
	 * @return
	 */
//	SysExpertBean getExpertByCondition(Integer participateStep,
//			Integer projectStepId, int id);

	/**
	 * 查询并过滤掉已存在的专家列表
	 * @param sysExpertBean
	 * @param flexiGridPageInfo
	 * @param ids
	 * @return
	 */
	List<SysExpertBean> getExpertByConditions(SysExpertBean sysExpertBean,
			FlexiGridPageInfo flexiGridPageInfo, String ids);

	/**
	 * 查询并过滤掉已存在的专家后的总记录数
	 * @param sysExpertBean
	 * @param ids
	 * @return
	 */
	int getTotalExceptExist(SysExpertBean sysExpertBean,  
			String ids);    
 
}
