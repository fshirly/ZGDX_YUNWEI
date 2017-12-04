package com.fable.insightview.platform.sysconf.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.sysconf.entity.SysConfig;

public interface SysConfigService {
	
	public Map<String,String> getConfByTypeID(int typeID);
	
	public String getConfParamValue(int typeID,String paramKey);
	
	/**
	 * 修改系统初始化value
	 * @param sysconfig
	 */
	void updateVal (SysConfig sysConfig) ;
	
	/**
	 * 查询类型列表信息
	 * @param typeID
	 * @return
	 */
	List<SysConfig> getConfMutip(int typeID);
}