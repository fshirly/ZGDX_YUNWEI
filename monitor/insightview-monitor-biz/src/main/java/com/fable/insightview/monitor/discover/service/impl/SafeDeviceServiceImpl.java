package com.fable.insightview.monitor.discover.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.discover.entity.SafeDeviceObj;
import com.fable.insightview.monitor.discover.mapper.SafeDeviceMapper;
import com.fable.insightview.monitor.discover.service.SafeDeviceService;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.page.Page;
@Service
public class SafeDeviceServiceImpl implements SafeDeviceService {
	@Autowired
	SafeDeviceMapper safeDeviceMapper;
	@Autowired 
	WebSiteMapper webSiteMapper;
	@Override
	public List<SafeDeviceObj> querySafeDeviceList(Page<SafeDeviceObj> page) {
		
		int period=1;
		long curr=0;
		long currTime=System.currentTimeMillis();
		List<SafeDeviceObj> safeLst = safeDeviceMapper.querySafeDeviceList(page);
		try {
			if(safeLst!=null){
				for (int i = 0; i < safeLst.size(); i++) {
					SafeDeviceObj mo = safeLst.get(i);
					Integer defDoInterval = mo.getDefDoIntervals();
					Date updateAlarmTime = mo.getUpdateAlarmTime();
					Date collectTime=mo.getCollectTime();
					if(defDoInterval!=null&&mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
						period=mo.getDefDoIntervals()*getMultiple()*60000;
					}else if(defDoInterval!=null){
						period=mo.getDoIntervals()*getMultiple()*1000;
					}
					if(collectTime!=null){
						curr=currTime-mo.getCollectTime().getTime();
						if(curr<=period){
							if ("1".equals(mo.getOperstatus())) {
								mo.setOperaTip("UP");
								//mo.setOperstatus("up.png");
							} else if ("2".equals(mo.getOperstatus())) {
								mo.setOperaTip("DOWN");
								//mo.setOperstatus("down.png");
							} 
							if (updateAlarmTime != null) {
								String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
								mo.setDurationTime(durationTime);
							}else{
								mo.setDurationTime("");
							}
						}else{
							mo.setOperaTip("未知");
							//mo.setOperstatus("unknown.png");
							String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
							mo.setDurationTime(durationTime);
							}
					}else{
						mo.setOperaTip("未知");
						//mo.setOperstatus("unknown.png");
						mo.setDurationTime("");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return safeLst;
	}
	public int getMultiple(){
		return webSiteMapper.getConfParamValue();
	}
}
