package com.fable.insightview.monitor.alarmmgr.alarmlevel.mapper;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.alarmlevel.entity.AlarmLevelBean;
import com.fable.insightview.platform.page.Page;

public interface AlarmLevelMapper {
	
	int deleteByPrimaryKey(Integer id);
	
	List<AlarmLevelBean> queryInfoList(Page<AlarmLevelBean> page);
	
	/**
	 *@Description:查询告警等级
	 *@returnType List<AlarmLevelInfo>
	 */
	List<AlarmLevelBean> queryLevelInfo();
	
	//判断名称是否存在
	int checkName(String name);
	
	int checkNameBeforeUpdate(AlarmLevelBean vo);
	
	int insertSelective(AlarmLevelBean vo); 
	
	int updateByPrimaryKey(AlarmLevelBean vo);

	AlarmLevelBean queryByID(Integer id);
	
	List<AlarmLevelBean> getAllAlarmLevel();
	
	//判断是否被使用，当(NUM > 0),已被使用
    int  getIsUsed (Integer id ); 
}