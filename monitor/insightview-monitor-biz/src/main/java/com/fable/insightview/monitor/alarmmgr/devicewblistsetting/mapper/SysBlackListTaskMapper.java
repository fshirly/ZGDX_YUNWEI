package com.fable.insightview.monitor.alarmmgr.devicewblistsetting.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.alarmmgr.devicewblistsetting.entity.SysBlackListTaskBean;

public interface SysBlackListTaskMapper {

	void insertSysBlackListTask(SysBlackListTaskBean sysBlackListTaskBean);

	List<SysBlackListTaskBean> selectRowByBlackID(@Param("blackID")Integer blackID);

	int updateOperateStatusByBlackID(@Param("blackID")Integer blackID,
			@Param("operateStatus")String operateStatus, 
			@Param("collectorID") String collectorID,
			@Param("oldCollectorID") String oldCollector);

}
