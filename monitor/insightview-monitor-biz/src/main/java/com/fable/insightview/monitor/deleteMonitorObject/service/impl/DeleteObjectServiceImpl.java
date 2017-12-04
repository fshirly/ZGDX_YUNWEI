package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.Map;

import com.fable.insightview.monitor.deleteMonitorObject.service.DeleteMonitorObject;

public abstract class DeleteObjectServiceImpl implements DeleteMonitorObject {

    @Override
	public boolean deleteData (Map<String, Object> map) { 
    	 return delete (map);
    }
    @Override
    public void deletePerfAndAlarmData(Map<String, Object> map){
		 deletePerfAndAlarm(map);
    }
    public abstract boolean delete (Map<String, Object> map);
    
    /***
     * 删除性能数据及告警数据
     * @param map
     * @return
     */
    public abstract void deletePerfAndAlarm (Map<String, Object> map);
}
