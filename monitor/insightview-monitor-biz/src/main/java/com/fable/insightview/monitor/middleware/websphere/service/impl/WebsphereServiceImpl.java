package com.fable.insightview.monitor.middleware.websphere.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.database.mapper.OracleMapper;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMidWareJdbcDsBean;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMidWareJdbcPoolBean;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMiddleWareJ2eeAppBean;
import com.fable.insightview.monitor.middleware.websphere.entity.PerfDeviceAvailabilityBean;
import com.fable.insightview.monitor.middleware.websphere.entity.PerfWASJDBCPoolBean;
import com.fable.insightview.monitor.middleware.websphere.entity.PerfWASJVMHeapBean;
import com.fable.insightview.monitor.middleware.websphere.entity.PerfWASThreadPoolBean;
import com.fable.insightview.monitor.middleware.websphere.entity.WebEjbMoudleInfoBean;
import com.fable.insightview.monitor.middleware.websphere.entity.WebMoudleInfosBean;
import com.fable.insightview.monitor.middleware.websphere.entity.WebSphereOperSituationBean;
import com.fable.insightview.monitor.middleware.websphere.mapper.WebsphereMapper;
import com.fable.insightview.monitor.middleware.websphere.service.IWebsphereService;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;

@Service
public class WebsphereServiceImpl implements IWebsphereService {
	@Autowired
	WebsphereMapper webMapper;
	@Autowired
	OracleMapper orclMapper;
	@Autowired 
	WebSiteMapper webSiteMapper;
	
	private final Logger logger = LoggerFactory
			.getLogger(WebsphereServiceImpl.class);

	@Override
	public List<MOMiddleWareJMXBean> getWasInfo(Integer MOID) {
		List<MOMiddleWareJMXBean> moList = webMapper.getWasInfo(MOID);
		StringBuffer sbf = new StringBuffer();
		if (moList != null) {
			for (int i = 0; i < moList.size(); i++) {
				if (!"".equals(moList.get(i).getJmsName())
						&& moList.get(i).getJmsName() != null
						&& !"null".equals(moList.get(i).getJmsName())) {
					sbf.append(moList.get(i).getJmsName() + ",");
				} 
			}
			if (sbf == null || sbf.length()==0) {
				moList.get(0).setJmsName("");
			} else {
				sbf.deleteCharAt(sbf.length() - 1);
				moList.get(0).setJmsName(sbf.toString());
			}
			if("".equals(moList.get(0).getMoalias())||"null".equals(moList.get(0).getMoalias())||moList.get(0).getMoalias()==null){
				moList.get(0).setMoalias("");
			}else{
				moList.get(0).setMoalias("("+moList.get(0).getMoalias()+")");
			}
		}
		return moList;
	}

	@Override
	public List<PerfWASJVMHeapBean> queryWasMemoryPerf(Map map) {
		return webMapper.queryWasMemoryPerf(map);
	}

	@Override
	public List<PerfDeviceAvailabilityBean> queryWasResponsePerf(Map map) {
		return webMapper.queryWasResponsePerf(map);
	}

	@Override
	public List<PerfWASThreadPoolBean> queryWasThreadPoolPerf(Map map) {
		return webMapper.queryWasThreadPoolPerf(map);
	}
	public int getMultiple(){
		return webSiteMapper.getConfParamValue();
	}
	
	@Override
	public List<WebSphereOperSituationBean> getWebSphereOperSituationInfos(Map<String,Object> params) {
		List<WebSphereOperSituationBean> webBean = webMapper
				.getWebSphereOperSituationInfos(params);
		WebSphereOperSituationBean mo=webBean.get(0);
		int period=1;
		long curr=0;
		long currTime=orclMapper.getCurrentDate().getTime();
		try {
			if(mo!=null){
					Date updateAlarmTime = mo.getUpdateAlarmTime();
					Date collectTime=mo.getCollectTime();
					if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
						period=mo.getDefDoIntervals()*getMultiple()*60000;
					}else{
						period=mo.getDoIntervals()*getMultiple()*1000;
					}
					if (collectTime != null) {
						curr=currTime-mo.getCollectTime().getTime();
						if(curr<=period){
							if (KPINameDef.up==mo.getUseStatus()) {
								mo.setOperStatusName("up.png");
								mo.setOperaTip("UP");
							} else if (KPINameDef.down==mo.getUseStatus()) {
								mo.setOperStatusName("down.png");
								mo.setOperaTip("DOWN");
							}
							if (updateAlarmTime != null) {
								String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
								mo.setDurationTime(durationTime);
							}else{
								mo.setDurationTime("");
							}
						}else{
							mo.setOperStatusName("unknown.png");
							mo.setOperaTip("未知");
							String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
							mo.setDurationTime(durationTime);
						}
					}else{
						mo.setOperStatusName("unknown.png");
						mo.setOperaTip("未知");
						mo.setDurationTime("");
					}
						
					if ("null".equals(mo.getAlarmLevel()) || "".equals(mo.getAlarmLevel())
							|| mo.getAlarmLevel() == null || 0==mo.getAlarmLevel() || "0".equals(mo.getAlarmLevel())  ) {
						mo.setLevelIcon("right.png");
					}else{
						mo.setLevelIcon(mo.getAlarmLevel()+".png");
					}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return webBean;
	}

	@Override
	public List<WebSphereOperSituationBean> getDeviceStatus(Map map) {
		return webMapper.getDeviceStatus(map);
	}

	@Override
	public MoMiddleWareJ2eeAppBean getWebAppInfos(Integer moID) {
		return webMapper.getWebAppInfos(moID);
	}

	@Override
	public List<WebMoudleInfosBean> getWebMoudleInfos(Map<String,Object> params) {
		return webMapper.getWebMoudleInfos(params);
	}

	@Override
	public List<WebEjbMoudleInfoBean> getWebEjbInfos(Integer moID) {
		return webMapper.getWebEjbInfos(moID);
	}

	@Override
	public List<PerfWASThreadPoolBean> getWasThreadPoolInfo(Map<String,Object> params) {
		return webMapper.getWasThreadPoolInfo(params);
	}

	@Override
	public List<PerfWASJDBCPoolBean> getPerfJdbcPoolList(Map map) {
		return webMapper.getPerfJdbcPoolList(map);
	}

	@Override
	public List<MoMidWareJdbcPoolBean> queryNumsByMoID(Map map) {
		return webMapper.queryNumsByMoID(map);
	}

	@Override
	public List<WebMoudleInfosBean> queryWebAppPerf(Map map) {
		return webMapper.queryWebAppPerf(map);
	}

	@Override
	public List<MoMidWareJdbcPoolBean> getJdbcPoolInfos(Integer moID) {
		return webMapper.getJdbcPoolInfos(moID);
	}

	@Override
	public List<MoMidWareJdbcDsBean> getJdbcDsInfos(Integer moID) {
		return webMapper.getJdbcDsInfos(moID);
	}

	@Override
	public MoMidWareJdbcDsBean getJdbcDsDetailInfos(Integer moID) {
		return webMapper.getJdbcDsDetailInfos(moID);
	}
}
