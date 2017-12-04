package com.fable.insightview.monitor.alarmmgr.alarmtype.mapper;

import java.util.List;

import com.fable.insightview.monitor.alarmmgr.alarmtype.entity.AlarmTypeBean;
import com.fable.insightview.platform.page.Page;

/**
 * 
 * @Description:AlarmTypeMapper
 * @author zhurt
 * @date 2014-7-18
 */
public interface AlarmTypeMapper {
	
	int deleteByPrimaryKey(Integer id);

	List<AlarmTypeBean> queryInfoList(Page<AlarmTypeBean> page);
	
	AlarmTypeBean queryByID(Integer id);
	
	//新增前判断名称是否存在
	int checkName(AlarmTypeBean vo);

	//更新前判断名称是否存在
	int checkNameBeforeUpdate(AlarmTypeBean vo);
	
    int insertSelective(AlarmTypeBean vo);


    int updateByPrimaryKey(AlarmTypeBean vo);
    
    List<AlarmTypeBean> getAllAlarmType();
    
    //判断是否被使用，当(NUM > 0),已被使用
    int  getIsUsed (Integer id ); 
}
