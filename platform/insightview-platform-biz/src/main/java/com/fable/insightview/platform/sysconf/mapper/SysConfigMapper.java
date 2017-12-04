package com.fable.insightview.platform.sysconf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.sysconf.entity.SysConfig;

public interface SysConfigMapper {

	public List<SysConfig> getConfByTypeID(int typeID);

	public String getConfParamValue(@Param("type") Integer typeID,
			@Param("paraKey") String paramKey);
	
	/**
	 * 新增
	 */
	int insertConfig(SysConfig sysConfig);
	
	/**
	 * 删除
	 */
	boolean delByType(@Param("type")int type);
	
	/**
	 * 修改val值
	 * @param sysConfig
	 */
	void updateVal (SysConfig sysConfig);
}