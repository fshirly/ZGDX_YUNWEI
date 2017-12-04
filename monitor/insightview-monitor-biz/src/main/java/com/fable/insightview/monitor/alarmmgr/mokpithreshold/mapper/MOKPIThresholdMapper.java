package com.fable.insightview.monitor.alarmmgr.mokpithreshold.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.alarmmgr.entity.MOKPIThresholdBean;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.platform.page.Page;

public interface MOKPIThresholdMapper {
	/* 查询所有 */
	List<MOKPIThresholdBean> selectThreshold(Page<MOKPIThresholdBean> page);

	MOKPIThresholdBean getThresholdById(int ruleID);

	boolean delThreshold(List<Integer> ruleIDs);

	int insertThreshold(MOKPIThresholdBean bean);

	int updateThreshold(MOKPIThresholdBean bean);

	int getByConditions(MOKPIThresholdBean bean);

	int getByIDs(MOKPIThresholdBean bean);

	String getMOName(MOKPIThresholdBean bean);

	com.fable.insightview.monitor.discover.entity.MODeviceObj getDeviceById(int moid);

	List<MOKPIThresholdBean> getThreshold(Page<MOKPIThresholdBean> page);
	
	MOKPIThresholdBean getInfoBySourceMOID(@Param("moID") String moID, @Param("sourceMOID") String sourceMOID, @Param("classID") String classID);
}
