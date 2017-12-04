package com.fable.insightview.monitor.perf.mapper;

import java.util.List;

import com.fable.insightview.monitor.perf.entity.SysMonitorsOfMOClassBean;

public interface SysMonitorsOfMOClassMapper {

	List<String> getByMOClassID(int moClassId);
	
	List<SysMonitorsOfMOClassBean> getByMOClassID2(int moClassId);

}