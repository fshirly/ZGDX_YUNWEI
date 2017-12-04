package com.fable.insightview.monitor.molist.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.alarmmgr.entity.MOKPIThresholdBean;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.molist.entity.ManufacturerInfoBean;
import com.fable.insightview.monitor.molist.entity.ResCategoryDefineBean;
import com.fable.insightview.monitor.molist.entity.SysMOManufacturerResDefBean;
import com.fable.insightview.monitor.molist.entity.SysMoInfoBean;
import com.fable.insightview.monitor.molist.entity.SysMoTemplateIntervalBean;
import com.fable.insightview.monitor.molist.entity.SysMoTypeOfMoClassBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTemplateUsedBean;
import com.fable.insightview.monitor.molist.entity.SysMonitorsTypeBean;
import com.fable.insightview.monitor.perf.entity.SysMonitorsOfMOClassBean;
import com.fable.insightview.platform.page.Page;

public interface ISysMoService {
	//获取监测器类型列表
	public List<SysMonitorsTypeBean> queryMonitorTypes(Page page);
	//新增监测器类型
	public int insertMonitorType(SysMonitorsTypeBean monitorTypeBean);
	//修改监测器类型
	public int updateMonitorType(SysMonitorsTypeBean monitorTypeBean);
	//根据类型名称查询
	public SysMonitorsTypeBean getMonitorTypeByName(String monitorTypeName);
	//根据类型标识查询
	public SysMonitorsTypeBean getMonitorTypeById(Integer monitorTypeId);
	//根据类型标识查询SysMonitorsTypeOfMOClass
	public Integer delMoClassByMonitorTypeId(Integer monitorTypeId);
	//根据类型标识查询SysMOList
	public Integer getMonitorsByMonitorTypeId(Integer monitorTypeId);
	//根据类型标识删除对象
	public Integer delByMonitorTypeId(Integer monitorTypeId);
	//获取所有监测器类型
	public List<SysMonitorsTypeBean> getAllMonitorTypes();
	//获取监测器列表
	public List<SysMoInfoBean> queryMoInfos(Page<SysMoInfoBean> page);
	//获取所有厂商
	public List<ManufacturerInfoBean> queryAllManufacturer();
	//根据厂商获取型号
	public List<ResCategoryDefineBean> queryResCategoryByManuId(Integer resManufacturerID);
	public List<ResCategoryDefineBean> queryAllResCategory();
	//新增监测器
	public int insertMoInfo(SysMoInfoBean sysMoInfoBean);
	//新增监测器与厂商型号关系
	public int insertMoManuCateRelation(SysMOManufacturerResDefBean relationBean);
	//新增监测器与监测对象关系
	public int insertMoClassRelation(SysMonitorsOfMOClassBean moClassRelationBean);
	//获取监测器与厂商型号关系列表
	public List<SysMOManufacturerResDefBean> getMoManuCateByMid(Integer mid);
	//根据标识删除监测器与厂商型号关系
	public Integer delManuCateById(Integer id);
	//根据mid获取信息
	public SysMoInfoBean getMoInfoByMid(Integer mid);
	//根据mid修改监测器基本信息
	public int updateMoInfo(SysMoInfoBean sysMoInfoBean);
	//根据mid获取信息
	public SysMoInfoBean getMoClassRelationInfoByMid(Integer mid);
	//修改监测器与监测对象关系
	public int updateMoClassRelation(SysMonitorsOfMOClassBean moClassRelationBean);
	//获取模板列表
	public List<SysMonitorsTemplateBean> queryMoTemplates(Page<SysMonitorsTemplateBean> page);
	//新增模板
	public int insertMoTemplateInfo(SysMonitorsTemplateBean templateBean);
	//新增模板与监测器关系
	public int insertMoTempInterval(SysMoTemplateIntervalBean tempIntervalBean);
	//根据moClassId获取监测器信息
	public List<String> getMoList(int moClassID);
	//根据templateID获取监测器信息
	public List<String> getMoListByTemplateId(int templateID);
	//根据templateID获取模板信息
	public SysMonitorsTemplateBean getTemplateByID(int templateID);
	//修改模板
	public int updateMoTemplateInfo(SysMonitorsTemplateBean templateBean);
	//根据模板ID删除模板与监测器关系
	public int delIntervalByTemplateID(int templateID);
	//根据模板ID删除模板
	public int delTemplateByID(int templateID);
	//根据moClassID获取监测类型列表
	public List<SysMoTypeOfMoClassBean> queryTypeOfClassByMoClassID(Page<SysMoTypeOfMoClassBean> page);
	//根据moClassID获取监测类型
	public int queryTypesByMoClassID(int moClassID);
	//新增类型适用范围
	public int insertMoTypeOfMoClass(SysMoTypeOfMoClassBean bean);
	//删除类型适用范围
	public int delMoTypeOfMoClass(int id);
	//根据标识删除监测器
	public Integer delMoInfoByMid(Integer mid);
	//根据标识删除监测器与厂商关系
	public Integer delMoOfManuCateByMid(Integer mid);
	//根据标识删除监测器与对象关系
	public Integer delMoOfClassByMid(Integer mid);
	//根据标识删除监测器与模板关系
	public Integer delMoOfTemplateByMid(Integer mid);
	//根据mid判断监测器是否关联性能任务
	public int queryMoOfPerfTask(int mid);
	//获取根据moClassID获取模板信息
	public List<SysMonitorsTemplateBean> queryMoTemplatesByClassID(Integer moClassID);
	//根据templateID获取监测器类型信息
	public List<String> getMoTypeListByTemplateId(int templateID);
	//新增设备与模板关系
	public int insertMoTemplateOfMoDevice(SysMonitorsTemplateUsedBean bean);
	//根据moClassID判断模板信息是否已经存在（一个监测对象只能有一个模板）
	public int isExistTemplate(Integer moClassID);
	//根据设备ID删除设备与模板关系
	public int delMoTemplateOfMoDevice(Integer moID);
	//根据moID获取设备已使用模板
	public SysMonitorsTemplateUsedBean getUsedTemplateByMoID(Integer moID);
	//根据TemplateID判断模板是否被套用
	public int queryUsedTemplateByTempId(int templateID);
	
	public int getUsedTemplateByTypeId(int monitorTypeID);
	
	public int getCountByMoClass(String moClass);
	
	public int getCountByTemplateName(String templateName);
	
	//根据模板ID删除模板Interval
	public int delTempIntervalByID(int templateID);
	
	public List<SysMoTemplateIntervalBean> queryIntervalByTypeId(int monitorTypeID);
	
	//删除监测器与监测对象关系
	public int delMoClassRelation(SysMonitorsOfMOClassBean moClassRelationBean);
	
	//获得适用范围
	public Map<String, Object> initPortalTree();
	
	public int isUsedByTemplate(Map<String, Object> paramMap);
	
	public boolean addMoTemplateOfMoDevice(SysMonitorsTemplateUsedBean bean);
	
	/**
	 * 绑定模板的设备列表
	 */
	List<MODeviceObj> listUsedDevice(Page<MOKPIThresholdBean> page);
	
}
