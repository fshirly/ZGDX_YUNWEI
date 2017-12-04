package com.fable.insightview.monitor.middleware.tomcat.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.mapper.OracleMapper;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.mapper.MiddlewareMapper;
import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareService;
import com.fable.insightview.monitor.website.mapper.WebSiteMapper;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.page.Page;

@Service
public class MiddlewareServiceImpl implements IMiddlewareService {
	@Autowired
	MiddlewareMapper middlewareMapper;
	@Autowired
	OracleMapper orclMapper;
	@Autowired
	WebSiteMapper webSiteMapper;

	public int getMultiple() {
		return webSiteMapper.getConfParamValue();
	}

	@Override
	public List<MOMiddleWareJMXBean> queryList(Page<MOMiddleWareJMXBean> page) {
		
		// 查询分页数据
		Map<Integer, String> osMap = DictionaryLoader
				.getConstantItems("OperStatus");// 可用状态
		int period=1;
		long curr=0;
		long currTime=orclMapper.getCurrentDate().getTime();
		List<MOMiddleWareJMXBean> orclLst=middlewareMapper.queryList(page);
		try {
			if(orclLst!=null){
				for (int i = 0; i < orclLst.size(); i++) {
					MOMiddleWareJMXBean mo = orclLst.get(i);
					Date updateAlarmTime = mo.getUpdateAlarmTime();
					Date collectTime=mo.getCollectTime();
					if(mo.getDoIntervals()==null || "".equals(mo.getDoIntervals())){
						period=mo.getDefDoIntervals()*getMultiple()*60000;
					}else{
						period=mo.getDoIntervals()*getMultiple()*1000;
					}
					if(collectTime!=null){
						curr=currTime-mo.getCollectTime().getTime();
						if(curr<=period){
							if (1 == mo.getOperStatus()) {
								mo.setOperaTip("UP");
								mo.setOperStatusName("up.png");
							} else if (2 == mo.getOperStatus()) {
								mo.setOperaTip("DOWN");
								mo.setOperStatusName("down.png");
							} 
							
							if (updateAlarmTime != null) {
								String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
								mo.setDurationTime(durationTime);
							}else{
								mo.setDurationTime("");
							}
						}else{
							mo.setOperaTip("未知");
							mo.setOperStatusName("unknown.png");
							String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
							mo.setDurationTime(durationTime);
						}
					}else{
						mo.setOperaTip("未知");
						mo.setOperStatusName("unknown.png");
						mo.setDurationTime("");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orclLst;
	}

	@Override
	public MOMiddleWareJMXBean selectMoMidByPrimaryKey(Integer moId) {
		return middlewareMapper.selectMoMidByPrimaryKey(moId);
	}

	@Override
	public MOMiddleWareJMXBean getMiddleWareInfo(int moId) {
		return middlewareMapper.getMiddleWareInfo(moId);
	}

	@Override
	public int getJMXByIPAndType(MOMiddleWareJMXBean bean) {
		return middlewareMapper.getJMXByIPAndType(bean);
	}

	@Override
	public MOMiddleWareJMXBean getJMXInfoByIPAndType(MOMiddleWareJMXBean bean) {
		return middlewareMapper.getJMXInfoByIPAndType(bean);
	}

	@Override
	public int updateMiddleWareJMX(MOMiddleWareJMXBean bean) {
		return middlewareMapper.updateMiddleWareJMX(bean);
	}

	@Override
	public List<MOMiddleWareJMXBean> queryAll() {
		return middlewareMapper.queryAll();
	}

	@Override
	public MOMiddleWareJMXBean getJMXInfoByIPAndTypeAlias(
			MOMiddleWareJMXBean bean) {
		return middlewareMapper.getJMXInfoByIPAndTypeAlias(bean);
	}

	@Override
	public List<MOMiddleWareJMXBean> getListByCondition(Map map) {
		// TODO Auto-generated method stub
		return middlewareMapper.queryListByCondition(map);
	}

}
