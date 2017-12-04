package com.fable.insightview.monitor.molist.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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

public interface SysMoInfoMapper {
	
	public List<SysMonitorsTypeBean> queryMonitorTypes(Page<SysMonitorsTypeBean> page);
	
	public int insertMonitorType(SysMonitorsTypeBean monitorTypeBean);
	
	public int updateMonitorType(SysMonitorsTypeBean monitorTypeBean);
	
	public SysMonitorsTypeBean getMonitorTypeByName(String monitorTypeName);
	
	public SysMonitorsTypeBean getMonitorTypeById(Integer monitorTypeID);

	public Integer delMoClassByMonitorTypeId(Integer monitorTypeID);

	public Integer getMonitorsByMonitorTypeId(Integer monitorTypeID);
	
	public Integer delByMonitorTypeId(Integer monitorTypeID);
	
	public List<SysMonitorsTypeBean> getAllMonitorTypes();
	
	public List<SysMoInfoBean> queryMoInfos(Page<SysMoInfoBean> page);
	
	public List<ManufacturerInfoBean> queryAllManufacturer();
	
	public List<ResCategoryDefineBean> queryResCategoryByManuId(Integer resManufacturerID);
	
	public List<ResCategoryDefineBean> queryAllResCategory();
	
	public int insertMoInfo(SysMoInfoBean sysMoInfoBean);
	
	public List<SysMOManufacturerResDefBean> getMoManuCateByMid(Integer mid);
	
	public int insertMoManuCateRelation(SysMOManufacturerResDefBean relationBean);
	
	public int insertMoClassRelation(SysMonitorsOfMOClassBean moClassRelationBean);
	
	public Integer delManuCateById(Integer id);
	
	public SysMoInfoBean getMoInfoByMid(Integer mid);
	
	public int updateMoInfo(SysMoInfoBean sysMoInfoBean);
	
	public SysMoInfoBean getMoClassRelationInfoByMid(Integer mid);
	
	public int updateMoClassRelation(SysMonitorsOfMOClassBean moClassRelationBean);
	
	public List<SysMonitorsTemplateBean> queryMoTemplates(Page<SysMonitorsTemplateBean> page);
	
	public int insertMoTemplateInfo(SysMonitorsTemplateBean templateBean);

	public int insertMoTempInterval(SysMoTemplateIntervalBean tempIntervalBean);
	
	public List<String> getMoList(int moClassID);
	
	public List<String> getMoListByTemplateId(int templateID);
	
	public SysMonitorsTemplateBean getTemplateByID(int templateID);
	
	public int updateMoTemplateInfo(SysMonitorsTemplateBean templateBean);
	
	public int delIntervalByTemplateID(int templateID);
	
	public int delTemplateByID(int templateID);
	
	public List<SysMoTypeOfMoClassBean> queryTypeOfClassByMoClassID(Page<SysMoTypeOfMoClassBean> page);
	
	public int queryTypesByMoClassID(int moClassID);
	
	public int insertMoTypeOfMoClass(SysMoTypeOfMoClassBean bean);
	
	public int delMoTypeOfMoClass(int id);
	
	public Integer delMoInfoByMid(Integer mid);
	
	public Integer delMoOfManuCateByMid(Integer mid);
	
	public Integer delMoOfClassByMid(Integer mid);
	
	public Integer delMoOfTemplateByMid(Integer mid);
	
	public int queryMoOfPerfTask(int mid);
	
	public List<SysMonitorsTemplateBean> queryMoTemplatesByClassID(Integer moClassID);
	
	public List<String> getMoTypeListByTemplateId(int templateID);
	
	public int insertMoTemplateOfMoDevice(SysMonitorsTemplateUsedBean bean);
	
	public int isExistTemplate(Integer moClassID);
	
	public int delMoTemplateOfMoDevice(Integer moID);
	
	public SysMonitorsTemplateUsedBean getUsedTemplateByMoID(Integer moID);
	
	public int queryUsedTemplateByTempId(int templateID);
	
	public int getUsedTemplateByTypeId(int monitorTypeID);
	
	public SysMonitorsTemplateUsedBean getTemplateByMoIDAndMOClassID(@Param("moID")Integer moID,@Param("moClassID")Integer moClassID);
	
	public List<SysMoInfoBean> getMoByManuAndCategory(@Param("moId")Integer moId);
	
	public List<SysMoInfoBean> getMoByManufacturer(@Param("moId")Integer moId);
	
	public List<SysMoInfoBean> getMoByManuAndCategoryForRoom(@Param("ResManufacturerID")Integer ResManufacturerID);
	
	public List<SysMoInfoBean> getMoByMoClassId(@Param("moClassId")Integer moClassId);
	
	public int getCountByMoClass(String moClass);
	
	public int getCountByTemplateName(String templateName);
	
	public int delTempIntervalByID(int templateID);
	
	public List<SysMoTemplateIntervalBean> queryIntervalByTypeId(int monitorTypeID);
	
	public int delMoClassRelation(SysMonitorsOfMOClassBean moClassRelationBean);
	
	public int isUsedByTemplate(Map<String, Object> paramMap);
	
	public List<MODeviceObj> getUsedDevice(Page<MOKPIThresholdBean> page);
}
