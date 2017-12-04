package com.fable.insightview.monitor.alarmmgr.alarmmaintenanceploicy.mapper;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.alarmmaintenanceploicy.entity.AlarmMaintenancePloicyBean;
import com.fable.insightview.platform.page.Page;

public interface AlarmMaintenancePloicyMapper {
	
	int deleteByPrimaryKey(Integer id);
	
	/**
	 *@Description:分页查询
	 *@param page
	 *@returnType List<AlarmMaintenancePolicyBean>
	 */
	List<AlarmMaintenancePloicyBean> queryInfoList(Page<AlarmMaintenancePloicyBean> page);
		
	int insertSelective(AlarmMaintenancePloicyBean vo); 
	
	int updateByPrimaryKey(AlarmMaintenancePloicyBean vo);

	AlarmMaintenancePloicyBean queryByID(Integer id);
	
	int checkName(String name);
	
	int checkName2(AlarmMaintenancePloicyBean vo);
	
	int checkMOName(Integer id);
}
