package com.fable.insightview.platform.notifypolicycfg.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.notifypolicycfg.entity.PolicyTypeBean;

public interface PolicyTypeMapper {
	/**
	 * 获得所有的策略类型
	 */
	List<PolicyTypeBean> getAllType();
	
	/**
	 * 根据id获得策略类型
	 */
	List<PolicyTypeBean> getByTypeID();
}
