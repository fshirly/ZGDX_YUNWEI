package com.fable.insightview.monitor.perf.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.molist.entity.SysMoInfoBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateUsedBean;
import com.fable.insightview.monitor.molist.mapper.SysMoInfoMapper;
import com.fable.insightview.monitor.perf.service.IObjectPerfConfigService;

@Service
public class ObjectPerfConfigServiceImpl implements IObjectPerfConfigService {
	private final Logger logger = LoggerFactory.getLogger(ObjectPerfConfigServiceImpl.class);
	
	@Autowired
	SysMoInfoMapper sysMoInfoMapper;
	/**
	 * 获得模板Id
	 */
	@Override
	public int getTemplateID(int moId, int moClassId) {
		SysMonitorsTemplateUsedBean templateUsedBean = sysMoInfoMapper.getTemplateByMoIDAndMOClassID(moId, moClassId);
		if(templateUsedBean != null){
			return templateUsedBean.getTemplateID();
		}
		return -1;
	}
	
	@Override
	public List<String> listMoListByTemplete(int templateID) {
		return sysMoInfoMapper.getMoTypeListByTemplateId(templateID);
	}

	@Override
	public List<SysMoInfoBean> getMoList(int moId, int moClassId) {
		List<SysMoInfoBean> moList = new ArrayList<SysMoInfoBean>();
		if(moClassId == 15 || moClassId ==16 || moClassId ==54 || moClassId ==81 || moClassId ==86 || moClassId == 19 || moClassId==20 || moClassId==53 || moClassId==44 || moClassId==91 || moClassId==92 || moClassId==93 || moClassId==94){
			moList = sysMoInfoMapper.getMoByMoClassId(moClassId);
		}else{
			moList = sysMoInfoMapper.getMoByManuAndCategory(moId);
			if(moList == null || moList.size() == 0){
				moList = sysMoInfoMapper.getMoByManufacturer(moId);
			}
		}
		return moList;
	}

	/**
	 * 绑定模板
	 */
	@Override
	public boolean setTmemplate(SysMonitorsTemplateUsedBean bean) {
		try {
			int delCount = sysMoInfoMapper.delMoTemplateOfMoDevice(bean.getMoID());
			if(delCount>=0 && bean.getTemplateID() != -1){
				SysMonitorsTemplateUsedBean beanTemp = new SysMonitorsTemplateUsedBean();
				beanTemp.setTemplateID(bean.getTemplateID());
				beanTemp.setMoClassID(bean.getMoClassID());
				beanTemp.setMoID(bean.getMoID());
				sysMoInfoMapper.insertMoTemplateOfMoDevice(beanTemp);
			}
			return true;
		} catch (Exception e) {
			logger.error("绑定模板异常："+e);
		}
		return false;
	}
	

}
