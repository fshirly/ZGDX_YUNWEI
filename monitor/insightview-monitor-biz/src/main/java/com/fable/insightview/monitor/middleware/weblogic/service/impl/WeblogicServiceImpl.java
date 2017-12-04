package com.fable.insightview.monitor.middleware.weblogic.service.impl;

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
import com.fable.insightview.monitor.middleware.tomcat.entity.TomcatOperSituationBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicJDBCPoolBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicJMSBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicJTABean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicJVMHeapBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicMemoryPoolBean;
import com.fable.insightview.monitor.middleware.weblogic.entity.PerfWebLogicThreadPoolBean;
import com.fable.insightview.monitor.middleware.weblogic.mapper.WeblogicMapper;
import com.fable.insightview.monitor.middleware.weblogic.service.IWeblogicService;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;

@Service
public class WeblogicServiceImpl implements IWeblogicService {
	@Autowired
	WeblogicMapper webLogicMapper;
	@Autowired
	OracleMapper orclMapper;
	@Autowired 
	WebSiteMapper webSiteMapper;
	private final Logger logger = LoggerFactory
			.getLogger(WeblogicServiceImpl.class);

	public int getMultiple(){
		return webSiteMapper.getConfParamValue();
	}
	
	@Override
	public List<MOMiddleWareJMXBean> getWeblogicInfo(Integer MOID) {
		List<MOMiddleWareJMXBean> moList = webLogicMapper.getWeblogicInfo(MOID);
		StringBuffer sbf = new StringBuffer();
		if (moList != null) {
			for (int i = 0; i < moList.size(); i++) {
				if (!"".equals(moList.get(i).getJmsName())
						&& moList.get(i).getJmsName() != null
						&& !"null".equals(moList.get(i).getJmsName())) {
					sbf.append(moList.get(i).getJmsName() + ",");
				} 
				if (moList.get(i).getUpTime() != null
						&& !"".equals(moList.get(i).getUpTime())) {
					moList.get(i).setUpTime(
							HostComm.getMsToTime(Long.parseLong(moList.get(i)
									.getUpTime())));
				} else {
					moList.get(i).setUpTime("");
				}
				if (moList.get(i).getHeapSizeMax() != null
						&& !"".equals(moList.get(i).getHeapSizeMax())) {
					moList.get(i).setHeapSizeMax(
							HostComm.getBytesToSize(Long.parseLong(moList.get(i)
									.getHeapSizeMax())));
				} else {
					moList.get(i).setHeapSizeMax("");
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
	public List<PerfWebLogicJVMHeapBean> queryWebLogicMemoryPerf(Map map) {
		return this.webLogicMapper.queryWebLogicMemoryPerf(map);
	}

	@Override
	public List<PerfWebLogicJMSBean> queryServerPerf(Map map) {
		return this.webLogicMapper.queryServerPerf(map);
	}

	@Override
	public PerfWebLogicJVMHeapBean getCurrMemUsage(Map map) {
		return this.webLogicMapper.getCurrMemUsage(map);
	}

	@Override
	public List<PerfWebLogicJDBCPoolBean> getPerfJdbcPoolList(Map map) {
		return this.webLogicMapper.getPerfJdbcPoolList(map);
	}

	@Override
	public PerfWebLogicJDBCPoolBean getJdbcPoolDetailInfos(Map map) {
		return this.webLogicMapper.getJdbcPoolDetailInfos(map);
	}

	@Override
	public List<PerfWebLogicThreadPoolBean> queryWebLogicThreadPoolPerf(Map map) {
		return this.webLogicMapper.queryWebLogicThreadPoolPerf(map);
	}

	@Override
	public List<PerfWebLogicMemoryPoolBean> queryWebLogicMemoryPoolPerf(Map map) {
		return this.webLogicMapper.queryWebLogicMemoryPoolPerf(map);
	}

	@Override
	public List<PerfWebLogicJDBCPoolBean> queryJdbcPerf(Map map) {
		return this.webLogicMapper.queryJdbcPerf(map);
	}

	@Override
	public List<TomcatOperSituationBean> getWebLogicOperSituationInfos(
			Map<String, Object> params) {
		List<TomcatOperSituationBean> beans = webLogicMapper.getWebLogicOperSituationInfos(params);
		TomcatOperSituationBean mo=beans.get(0);
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

		return beans;
	}

	@Override
	public PerfWebLogicJTABean getJTAInfo(Map map) {
		return this.webLogicMapper.getJTAInfo(map);
	}

	@Override
	public PerfWebLogicJTABean getPieInfo(Integer MOID) {
		return this.webLogicMapper.getPieInfo(MOID);
	}

	
}
