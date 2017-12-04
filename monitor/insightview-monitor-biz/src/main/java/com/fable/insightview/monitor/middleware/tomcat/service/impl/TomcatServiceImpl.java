package com.fable.insightview.monitor.middleware.tomcat.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.entity.MOMySQLDBServerBean;
import com.fable.insightview.monitor.database.mapper.MOOracleRollSEGMapper;
import com.fable.insightview.monitor.database.mapper.MOOracleSGAMapper;
import com.fable.insightview.monitor.database.mapper.OracleMapper;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMSBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJTABean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJVMBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJdbcDSBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJdbcPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareMemoryBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareServletBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareThreadPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareWebModuleBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatClassLoadBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatJVMHeapBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatMemoryPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.PerfTomcatThreadPoolBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.TomcatOperSituationBean;
import com.fable.insightview.monitor.middleware.tomcat.entity.TomcatWebModuleBean;
import com.fable.insightview.monitor.middleware.tomcat.mapper.TomcatMapper;
import com.fable.insightview.monitor.middleware.tomcat.service.ITomcatService;
import com.fable.insightview.monitor.middleware.websphere.entity.MoMiddleWareJ2eeAppBean;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.page.Page;

@Service
public class TomcatServiceImpl implements ITomcatService {
	@Autowired
	TomcatMapper tomcatMapper;
	@Autowired
	OracleMapper orclMapper;
	@Autowired 
	WebSiteMapper webSiteMapper;

	private final Logger logger = LoggerFactory
			.getLogger(TomcatServiceImpl.class);

	@Override
	public MOMiddleWareJMXBean getTmChartAvailability(Map map) {
		return tomcatMapper.getTmChartAvailability(map);
	}
	
	public int getMultiple(){
		return webSiteMapper.getConfParamValue();
	}
	
	@Override
	public MOMiddleWareJMXBean getTmInfo(Map map) {
		MOMiddleWareJMXBean mo = tomcatMapper.getTmInfo(map);
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
							if (KPINameDef.up==mo.getPerfValue()) {
								mo.setOperStatusName("up.png");
								mo.setOperaTip("UP");
							} else if (KPINameDef.down==mo.getPerfValue()) {
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
						mo.setLevelicon("right.png");
						mo.setAlarmLevelName("正常");
					}
					if("".equals(mo.getMoalias())||"null".equals(mo.getMoalias())||mo.getMoalias()==null){
						mo.setMoalias("");
					}else{
						mo.setMoalias("("+mo.getMoalias()+")");
					}	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mo;
	}

	@Override
	public List<PerfTomcatMemoryPoolBean> queryTmMemoryPoolPerf(Map map) {
		return tomcatMapper.queryTmMemoryPoolPerf(map);
	}

	@Override
	public List<PerfTomcatJVMHeapBean> queryTmMemoryUsedPerf(Map map) {
		return tomcatMapper.queryTmMemoryUsedPerf(map);
	}

	@Override
	public List<TomcatWebModuleBean> getTmInstanceList(Integer moID) {
		List<TomcatWebModuleBean> lst = tomcatMapper.getTmInstanceList(moID);
		for (int i = 0; i < lst.size(); i++) {
			TomcatWebModuleBean bean = lst.get(i);
			String warName = bean.getWarName();
			String uri = warName.substring(0, warName.lastIndexOf("/"));
			warName = warName.substring(warName.lastIndexOf("/")+1);
			bean.setWarName(warName);
			bean.setUri(uri);
		}
		return lst;
	}

	@Override
	public List<MOMiddleWareJdbcDSBean> getJdbcDSCount(Map map) {
		return tomcatMapper.getJdbcDSCount(map);
	}

	@Override
	public List<MOMiddleWareJdbcDSBean> getJdbcDSValue(Map map) {
		return tomcatMapper.getJdbcDSValue(map);
	}

	@Override
	public List<MOMiddleWareThreadPoolBean> getThreadPoolCount(Map map) {
		return tomcatMapper.getThreadPoolCount(map);
	}

	@Override
	public List<MOMiddleWareThreadPoolBean> getThreadPoolValue(Map map) {
		return tomcatMapper.getThreadPoolValue(map);
	}

	@Override
	public List<MOMiddleWareMemoryBean> getMemoryPoolValue(Map map) {
		return tomcatMapper.getMemoryPoolValue(map);
	}

	@Override
	public List<MOMiddleWareMemoryBean> getBarChartMemory(Map map) {
		return tomcatMapper.getBarChartMemory(map);
	}

	@Override
	public List<TomcatOperSituationBean> getTomcatOperSituationInfos(
			Map<String, Object> params) {
		List<TomcatOperSituationBean> beans = tomcatMapper
				.getTomcatOperSituationInfos(params);
		if (beans.size() > 0) {
			if ("null".equals(beans.get(0).getAlarmLevel())
					|| "".equals(beans.get(0).getAlarmLevel())
					|| beans.get(0).getAlarmLevel() == null
					|| beans.get(0).getAlarmLevel() == 0) {
				beans.get(0).setLevelIcon("right.png");
			} else {
				beans.get(0).setLevelIcon(beans.get(0).getAlarmLevel()+".png");
			}
			for (int i = 0; i < beans.size(); i++) {
				String tempTime = beans.get(i).getStartupTime();
				tempTime = tempTime.substring(0, tempTime.length() - 2);
				beans.get(i).setStartupTime(tempTime);
			}
		}

		return beans;
	}

	@Override
	public List<PerfTomcatClassLoadBean> getTomcatClassLoaderInfos(Map<String,Object> params) {
		return tomcatMapper.getTomcatClassLoaderInfos(params);
	}

	@Override
	public List<TomcatOperSituationBean> getDeviceStatusAndResponseTime(Map map) {
		return tomcatMapper.getDeviceStatusAndResponseTime(map);
	}

	@Override
	public List<PerfTomcatJVMHeapBean> getJvmHeapInfos(Map<String, Object> params) {
		return tomcatMapper.getJvmHeapInfos(params);
	}

	@Override
	public List<PerfTomcatThreadPoolBean> getPerfThreadPoolList(Map map) {
		return tomcatMapper.getPerfThreadPoolList(map);
	}

	@Override
	public List<MOMiddleWareThreadPoolBean> queryNumsByMoID(Map map) {
		return tomcatMapper.queryNumsByMoID(map);
	}

	@Override
	public List<PerfTomcatClassLoadBean> getListInfo(
			Page<PerfTomcatClassLoadBean> page) {
		return tomcatMapper.getListInfo(page);
	}

	@Override
	public List<MOMiddleWareJdbcPoolBean> getJdbcPoolInfo(
			Page<MOMiddleWareJdbcPoolBean> page) {
		return tomcatMapper.getJdbcPoolInfo(page);
	}

	@Override
	public List<MOMiddleWareJMSBean> getJMSInfo(Page<MOMiddleWareJMSBean> page) {
		return tomcatMapper.getJMSInfo(page);
	}

	@Override
	public List<MOMiddleWareJTABean> getJTAInfo(Page<MOMiddleWareJTABean> page) {
		return tomcatMapper.getJTAInfo(page);
	}

	@Override
	public List<MOMiddleWareMemoryBean> queryMemNameByMoID(Map map) {
		return tomcatMapper.queryMemNameByMoID(map);
	}

	public String formatValue(long bytes) {
		BigDecimal filesize = new BigDecimal(bytes);
		BigDecimal megabyte = new BigDecimal(1024 * 1024);
		float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
				.floatValue();
		String value = null;
		if (returnValue > 1) {
			value = returnValue + "  MB ";
		} else {
			BigDecimal kilobyte = new BigDecimal(1024);
			returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
					.floatValue();
			value = returnValue + "  KB ";
		}
		return value;
	}

	@Override
	public List<MOMiddleWareThreadPoolBean> getThreadPoolList(
			Page<MOMiddleWareThreadPoolBean> page) {
		return tomcatMapper.getThreadPoolList(page);
	}

	@Override
	public List<MOMiddleWareJdbcDSBean> getJdbcDSList(
			Page<MOMiddleWareJdbcDSBean> page) {
		return tomcatMapper.getJdbcDSList(page);
	}

	@Override
	public List<MoMiddleWareJ2eeAppBean> getJ2eeAppList(
			Page<MoMiddleWareJ2eeAppBean> page) {
		return tomcatMapper.getJ2eeAppList(page);
	}

	@Override
	public MOMiddleWareThreadPoolBean getThreadPoolByID(int moId) {
		return tomcatMapper.getThreadPoolByID(moId);
	}

	@Override
	public PerfTomcatClassLoadBean getClassLoadByID(int moId) {
		return tomcatMapper.getClassLoadByID(moId);
	}

	@Override
	public MOMiddleWareJdbcDSBean getJdbcDSByID(int moId) {
		return tomcatMapper.getJdbcDSByID(moId);
	}

	@Override
	public MOMiddleWareJdbcPoolBean getJdbcPoolByID(int moId) {
		return tomcatMapper.getJdbcPoolByID(moId);
	}

	@Override
	public MOMiddleWareMemoryBean getMemPoolByID(int moId) {
		return tomcatMapper.getMemPoolByID(moId);
	}

	@Override
	public MOMiddleWareJTABean getMiddleWareJTAByID(int moId) {
		return tomcatMapper.getMiddleWareJTAByID(moId);
	}

	@Override
	public MOMiddleWareJVMBean getMiddlewareJvmByID(int moId) {
		return tomcatMapper.getMiddlewareJvmByID(moId);
	}

	@Override
	public MoMiddleWareJ2eeAppBean getJ2eeAppByID(int moId) {
		return tomcatMapper.getJ2eeAppByID(moId);
	}

	@Override
	public MOMiddleWareJMSBean getMiddleJMSByID(int moId) {
		return tomcatMapper.getMiddleJMSByID(moId);
	}

	@Override
	public List<MOMiddleWareWebModuleBean> getWebModuleList(
			Page<MOMiddleWareWebModuleBean> page) {
		return tomcatMapper.getWebModuleList(page);
	}

	@Override
	public List<MOMiddleWareServletBean> getServletList(
			Page<MOMiddleWareServletBean> page) {
		return tomcatMapper.getServletList(page);
	}

	@Override
	public MOMiddleWareWebModuleBean getWebModuleByID(int moId) {
		return tomcatMapper.getWebModuleByID(moId);
	}

	@Override
	public MOMiddleWareServletBean getServletByID(int moId) {
		return tomcatMapper.getServletByID(moId);
	}

	@Override
	public List<MOMiddleWareThreadPoolBean> getThreadPoolPerf(int moId) {
		return tomcatMapper.getThreadPoolPerf(moId);
	}

	@Override
	public MOMiddleWareJdbcPoolBean getFirstJdbcPool(Map map) {
		return tomcatMapper.getFirstJdbcPool(map);
	}
}
