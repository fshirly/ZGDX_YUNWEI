package com.fable.insightview.monitor.molist.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.alarmmgr.entity.MOKPIThresholdBean;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.molist.entity.ManufacturerInfoBean;
import com.fable.insightview.monitor.molist.entity.ResCategoryDefineBean;
import com.fable.insightview.monitor.molist.entity.SysMOManufacturerResDefBean;
import com.fable.insightview.monitor.molist.entity.SysMoInfoBean;
import com.fable.insightview.monitor.molist.entity.SysMoTemplateIntervalBean;
import com.fable.insightview.monitor.molist.entity.SysMoTypeOfMoClassBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateUsedBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTypeBean;
import com.fable.insightview.monitor.molist.mapper.SysMoInfoMapper;
import com.fable.insightview.monitor.molist.service.ISysMoService;
import com.fable.insightview.monitor.perf.entity.SysMonitorsOfMOClassBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.page.Page;

@Service
public class SysMoServiceImpl implements ISysMoService {
	private static final Logger logger = LoggerFactory.getLogger(SysMoServiceImpl.class);
	@Autowired SysMoInfoMapper sysMoInfoMapper;
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;

	@Override
	public List<SysMonitorsTypeBean> queryMonitorTypes(Page page) {
		return sysMoInfoMapper.queryMonitorTypes(page);
	}

	@Override
	public int insertMonitorType(SysMonitorsTypeBean monitorTypeBean) {
		return sysMoInfoMapper.insertMonitorType(monitorTypeBean);
	}

	@Override
	public int updateMonitorType(SysMonitorsTypeBean monitorTypeBean) {
		return sysMoInfoMapper.updateMonitorType(monitorTypeBean);
	}

	@Override
	public SysMonitorsTypeBean getMonitorTypeByName(String monitorTypeName) {
		return sysMoInfoMapper.getMonitorTypeByName(monitorTypeName);
	}

	@Override
	public SysMonitorsTypeBean getMonitorTypeById(Integer monitorTypeId) {
		return sysMoInfoMapper.getMonitorTypeById(monitorTypeId);
	}

	@Override
	public Integer delMoClassByMonitorTypeId(Integer monitorTypeId) {
		return sysMoInfoMapper.delMoClassByMonitorTypeId(monitorTypeId);
	}

	@Override
	public Integer getMonitorsByMonitorTypeId(Integer monitorTypeId) {
		return sysMoInfoMapper.getMonitorsByMonitorTypeId(monitorTypeId);
	}

	@Override
	public Integer delByMonitorTypeId(Integer monitorTypeId) {
		return sysMoInfoMapper.delByMonitorTypeId(monitorTypeId);
	}

	@Override
	public List<SysMonitorsTypeBean> getAllMonitorTypes() {
		return sysMoInfoMapper.getAllMonitorTypes();
	}

	@Override
	public List<SysMoInfoBean> queryMoInfos(Page<SysMoInfoBean> page) {
		return sysMoInfoMapper.queryMoInfos(page);
	}

	@Override
	public List<ManufacturerInfoBean> queryAllManufacturer() {
		return sysMoInfoMapper.queryAllManufacturer();
	}

	@Override
	public List<ResCategoryDefineBean> queryResCategoryByManuId(Integer resManufacturerID) {
		return sysMoInfoMapper.queryResCategoryByManuId(resManufacturerID);
	}

	@Override
	public int insertMoInfo(SysMoInfoBean sysMoInfoBean) {
		return sysMoInfoMapper.insertMoInfo(sysMoInfoBean);
	}

	@Override
	public List<SysMOManufacturerResDefBean> getMoManuCateByMid(Integer mid) {
		return sysMoInfoMapper.getMoManuCateByMid(mid);
	}

	@Override
	public int insertMoClassRelation(SysMonitorsOfMOClassBean moClassRelationBean) {
		return sysMoInfoMapper.insertMoClassRelation(moClassRelationBean);
	}

	@Override
	public int insertMoManuCateRelation(SysMOManufacturerResDefBean relationBean) {
		return sysMoInfoMapper.insertMoManuCateRelation(relationBean);
	}

	@Override
	public Integer delManuCateById(Integer id) {
		return sysMoInfoMapper.delManuCateById(id);
	}

	@Override
	public List<ResCategoryDefineBean> queryAllResCategory() {
		return sysMoInfoMapper.queryAllResCategory();
	}

	@Override
	public SysMoInfoBean getMoInfoByMid(Integer mid) {
		return sysMoInfoMapper.getMoInfoByMid(mid);
	}

	@Override
	public int updateMoInfo(SysMoInfoBean sysMoInfoBean) {
		return sysMoInfoMapper.updateMoInfo(sysMoInfoBean);
	}

	@Override
	public SysMoInfoBean getMoClassRelationInfoByMid(Integer mid) {
		return sysMoInfoMapper.getMoClassRelationInfoByMid(mid);
	}

	@Override
	public int updateMoClassRelation(
			SysMonitorsOfMOClassBean moClassRelationBean) {
		return sysMoInfoMapper.updateMoClassRelation(moClassRelationBean);
	}

	@Override
	public List<SysMonitorsTemplateBean> queryMoTemplates(
			Page<SysMonitorsTemplateBean> page) {
		return sysMoInfoMapper.queryMoTemplates(page);
	}

	@Override
	public int insertMoTempInterval(SysMoTemplateIntervalBean tempIntervalBean) {
		return sysMoInfoMapper.insertMoTempInterval(tempIntervalBean);
	}

	@Override
	public int insertMoTemplateInfo(SysMonitorsTemplateBean templateBean) {
		return sysMoInfoMapper.insertMoTemplateInfo(templateBean);
	}

	@Override
	public List<String> getMoList(int moClassID) {
		return sysMoInfoMapper.getMoList(moClassID);
	}

	@Override
	public List<String> getMoListByTemplateId(int templateID) {
		return sysMoInfoMapper.getMoListByTemplateId(templateID);
	}

	@Override
	public SysMonitorsTemplateBean getTemplateByID(int templateID) {
		return sysMoInfoMapper.getTemplateByID(templateID);
	}

	@Override
	public int updateMoTemplateInfo(SysMonitorsTemplateBean templateBean) {
		return sysMoInfoMapper.updateMoTemplateInfo(templateBean);
	}

	@Override
	public int delIntervalByTemplateID(int templateID) {
		return sysMoInfoMapper.delIntervalByTemplateID(templateID);
	}

	@Override
	public int delTemplateByID(int templateID) {
		return sysMoInfoMapper.delTemplateByID(templateID);
	}

	@Override
	public List<SysMoTypeOfMoClassBean> queryTypeOfClassByMoClassID(
			Page<SysMoTypeOfMoClassBean> page) {
		return sysMoInfoMapper.queryTypeOfClassByMoClassID(page);
	}

	@Override
	public int queryTypesByMoClassID(int moClassID) {
		return sysMoInfoMapper.queryTypesByMoClassID(moClassID);
	}

	@Override
	public int insertMoTypeOfMoClass(SysMoTypeOfMoClassBean bean) {
		return sysMoInfoMapper.insertMoTypeOfMoClass(bean);
	}

	@Override
	public int delMoTypeOfMoClass(int id) {
		return sysMoInfoMapper.delMoTypeOfMoClass(id);
	}

	@Override
	public Integer delMoInfoByMid(Integer mid) {
		return sysMoInfoMapper.delMoInfoByMid(mid);
	}

	@Override
	public Integer delMoOfClassByMid(Integer mid) {
		return sysMoInfoMapper.delMoOfClassByMid(mid);
	}

	@Override
	public Integer delMoOfManuCateByMid(Integer mid) {
		return sysMoInfoMapper.delMoOfManuCateByMid(mid);
	}

	@Override
	public Integer delMoOfTemplateByMid(Integer mid) {
		return sysMoInfoMapper.delMoOfTemplateByMid(mid);
	}

	@Override
	public int queryMoOfPerfTask(int mid) {
		return sysMoInfoMapper.queryMoOfPerfTask(mid);
	}

	@Override
	public List<SysMonitorsTemplateBean> queryMoTemplatesByClassID(
			Integer moClassID) {
		return sysMoInfoMapper.queryMoTemplatesByClassID(moClassID);
	}

	@Override
	public List<String> getMoTypeListByTemplateId(int templateID) {
		return sysMoInfoMapper.getMoTypeListByTemplateId(templateID);
	}

	@Override
	public int insertMoTemplateOfMoDevice(SysMonitorsTemplateUsedBean bean) {
		return sysMoInfoMapper.insertMoTemplateOfMoDevice(bean);
	}

	@Override
	public int isExistTemplate(Integer moClassID) {
		return sysMoInfoMapper.isExistTemplate(moClassID);
	}

	@Override
	public int delMoTemplateOfMoDevice(Integer moID) {
		return sysMoInfoMapper.delMoTemplateOfMoDevice(moID);
	}

	@Override
	public SysMonitorsTemplateUsedBean getUsedTemplateByMoID(Integer moID) {
		return sysMoInfoMapper.getUsedTemplateByMoID(moID);
	}

	@Override
	public int queryUsedTemplateByTempId(int templateID) {
		return sysMoInfoMapper.queryUsedTemplateByTempId(templateID);
	}

	@Override
	public int getUsedTemplateByTypeId(int monitorTypeID) {
		return sysMoInfoMapper.getUsedTemplateByTypeId(monitorTypeID);
	}

	@Override
	public int getCountByMoClass(String moClass) {
		return sysMoInfoMapper.getCountByMoClass(moClass);
	}

	@Override
	public int getCountByTemplateName(String templateName) {
		return sysMoInfoMapper.getCountByTemplateName(templateName);
	}

	@Override
	public int delTempIntervalByID(int templateID) {
		return sysMoInfoMapper.delTempIntervalByID(templateID);
	}
	
	@Override
	public List<SysMoTemplateIntervalBean> queryIntervalByTypeId(int monitorTypeID) {
		return sysMoInfoMapper.queryIntervalByTypeId(monitorTypeID);
	}

	@Override
	public int delMoClassRelation(SysMonitorsOfMOClassBean moClassRelationBean) {
		return sysMoInfoMapper.delMoClassRelation(moClassRelationBean);
	}

	@Override
	public Map<String, Object> initPortalTree() {
		List<MObjectDefBean> menuLst = mobjectInfoMapper
				.queryMObjectRelation2();
		List<MObjectDefBean> treeData = new ArrayList<MObjectDefBean>();
		for (int i = 0; i < menuLst.size(); i++) {
			MObjectDefBean bean = menuLst.get(i);
			if (bean.getParentClassId() == null) {
				bean.setParentClassId(0);
			}
			if (bean.getClassId() >= 14 && bean.getClassId() != 46
					&& bean.getClassId() != 47 && bean.getClassId() != 48
					&& bean.getClassId() != 49 && bean.getClassId() != 50
					&& bean.getClassId() != 51) {
				treeData.add(bean);
			}
		}
		String menuLstJson = JsonUtil.toString(treeData);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

	@Override
	public int isUsedByTemplate(Map<String, Object> paramMap) {
		return sysMoInfoMapper.isUsedByTemplate(paramMap);
	}

	@Override
	public boolean addMoTemplateOfMoDevice(SysMonitorsTemplateUsedBean bean) {
		int result = 0;
		int delResult = 0;
		logger.info("新增设备{} 与模板关系",bean.getMoIDs());
		if (bean.getMoIDs().contains(",") == true) {
			String[] moIdLst = bean.getMoIDs().split(",");
			for (int i = 0; i < moIdLst.length; i++) {
				logger.info(i + "---删除设备：" + moIdLst[i]+"的模板");
				sysMoInfoMapper.delMoTemplateOfMoDevice(Integer.parseInt(moIdLst[i]));
			}
			if(delResult>=0){
				for (int i = 0; i < moIdLst.length; i++) {
					logger.info(i + "---新增设备：" + moIdLst[i]+"的模板");
					SysMonitorsTemplateUsedBean beanTemp = new SysMonitorsTemplateUsedBean();
					beanTemp.setTemplateID(bean.getTemplateID());
					beanTemp.setMoClassID(bean.getMoClassID());
					beanTemp.setMoID(Integer.parseInt(moIdLst[i]));
					result = sysMoInfoMapper.insertMoTemplateOfMoDevice(beanTemp);
				}
			}
		} else {
			delResult = sysMoInfoMapper.delMoTemplateOfMoDevice(Integer.parseInt(bean.getMoIDs()));
			if(delResult>=0){
				bean.setMoID(Integer.parseInt(bean.getMoIDs()));
				result = sysMoInfoMapper.insertMoTemplateOfMoDevice(bean);
			}
			
		}
		if (result > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<MODeviceObj> listUsedDevice(Page<MOKPIThresholdBean> page) {
		return sysMoInfoMapper.getUsedDevice(page);
	}
}
