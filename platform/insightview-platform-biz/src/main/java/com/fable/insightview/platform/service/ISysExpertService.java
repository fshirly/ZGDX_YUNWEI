package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.entity.SysExpertBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface ISysExpertService {

	/**
	 * 根据条件查询专家信息
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
	 * 根据id集合查询当前项目已存在的专家列表
	 * @param expertIdList
	 * @return
	 */
	List<SysExpertBean> getExpertListInCurrentStep(List<Integer> expertIdList);

	/**
	 * 查出所有已存在专家的名字，拼成字符串
	 * @param participateStep
	 * @param projectStepId
	 * @param ids
	 * @return
	 */
//	String getExpertExistNames(Integer participateStep, Integer projectStepId, 
//			String ids);

	/**
	 * 查询过滤已存在专家后的专家列表
	 * @param sysExpertBean
	 * @param flexiGridPageInfo
	 * @param expertIdList
	 * @return
	 */
	List<SysExpertBean> getExpertByConditions(SysExpertBean sysExpertBean,
			FlexiGridPageInfo flexiGridPageInfo, List<Integer> expertIdList);

	/**
	 * 查询过滤已存在的专家后的总记录数
	 * @param sysExpertBean
	 * @param expertIdList
	 * @return
	 */
	int getTotalExceptExist(SysExpertBean sysExpertBean, 
			List<Integer> expertIdList);  

}
