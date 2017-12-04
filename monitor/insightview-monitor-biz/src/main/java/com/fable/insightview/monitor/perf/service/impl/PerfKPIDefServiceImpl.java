package com.fable.insightview.monitor.perf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.perf.entity.PerfKPIDefBean;
import com.fable.insightview.monitor.perf.entity.SysKPIOfMOClassBean;
import com.fable.insightview.monitor.perf.mapper.PerfKPIDefMapper;
import com.fable.insightview.monitor.perf.mapper.SysKPIOfMOClassMapper;
import com.fable.insightview.monitor.perf.service.IPerfKPIDefService;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.page.Page;

@Service
public class PerfKPIDefServiceImpl implements IPerfKPIDefService {
	private final Logger logger = LoggerFactory.getLogger(PerfKPIDefServiceImpl.class);
	private static final int SITE_CLASSID = 90;
	private static final int CONDITION_CLASSID = 96;// 空调
	private static final int UPS_CLASSID = 73;// 空调
	private static final int ROOMMONITOR_CLASSID = 97;// 机房监控
	//根监测对象类型id
	private static final int MO = 1;
	@Autowired
	PerfKPIDefMapper perfKPIDefMapper;
	@Autowired 
	SysKPIOfMOClassMapper kpiOfMOClassMapper;
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	@Override
	public List<PerfKPIDefBean> getPerfKPIDefList(Page<PerfKPIDefBean> page) {
		return perfKPIDefMapper.getPerfKPIDefList(page);
	}

	@Override
	public boolean delPerKPIDef(List<Integer> kpiIDs) {
		return perfKPIDefMapper.delPerKPIDef(kpiIDs);
	}

	@Override
	public boolean checkBeforeDel(Integer kpiID) {
		int kPIExprCount = perfKPIDefMapper.getKPIExprByKPIID(kpiID);
		int thresholdCount = perfKPIDefMapper.getThresholdByKPIID(kpiID);
		int mobjectKPICount = perfKPIDefMapper.getMObjectKPIDefByKPIID(kpiID);
		int kpiOfClassCount = kpiOfMOClassMapper.getCountByKPIID(kpiID);
		if (kPIExprCount <= 0 && thresholdCount <= 0 && mobjectKPICount <= 0 && kpiOfClassCount <= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public PerfKPIDefBean getPerfKPIDefById(int kpiID) {
		return perfKPIDefMapper.getPerfKPIDefById(kpiID);
	}

	@Override
	public Map<String, Object> checkPerKPIName(String flag,
			PerfKPIDefBean perfKPIDefBean) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean checkResult = false;
		int isNameOrEName = 0;
		if ("add".equals(flag)) {
			int namecount = perfKPIDefMapper.getKPIByName(perfKPIDefBean);
			int enNameCount = perfKPIDefMapper.getKPIByEnName(perfKPIDefBean);
			if (namecount <= 0 && enNameCount <= 0) {
				checkResult = true;
			} else if (namecount > 0 && enNameCount <= 0) {
				// 指标名称已存在
				isNameOrEName = 0;
				checkResult = false;
			} else if (namecount <= 0 && enNameCount > 0) {
				// 指标英文名已存在
				isNameOrEName = 1;
				checkResult = false;
			} else {
				// 指标名称和英文名已存在
				isNameOrEName = 2;
				checkResult = false;
			}
		} else {
			int namecount = perfKPIDefMapper.getKPIByNameAndID(perfKPIDefBean);
			int enNameCount = perfKPIDefMapper
					.getKPIByEnNameAndID(perfKPIDefBean);
			if (namecount <= 0 && enNameCount <= 0) {
				checkResult = true;
			} else if (namecount > 0 && enNameCount <= 0) {
				// 指标名称已存在
				isNameOrEName = 0;
				checkResult = false;
			} else if (namecount <= 0 && enNameCount > 0) {
				// 指标英文名已存在
				isNameOrEName = 1;
				checkResult = false;
			} else {
				// 指标名称和英文名已存在
				isNameOrEName = 2;
				checkResult = false;
			}
		}
		result.put("checkResult", checkResult);
		result.put("isNameOrEName", isNameOrEName);
		return result;
	}

	@Override
	public boolean addPerfKPIDef(PerfKPIDefBean perfKPIDefBean) {
		try {
			perfKPIDefMapper.insertPerfKPIDef(perfKPIDefBean);
			return true;
		} catch (Exception e) {
			logger.error("新增指标异常：" + e);
			return false;
		}
	}

	@Override
	public boolean updatePerfKPIDef(PerfKPIDefBean perfKPIDefBean) {
		try {
			perfKPIDefMapper.updatePerfKPIDef(perfKPIDefBean);
			return true;
		} catch (Exception e) {
			logger.error("更新指标异常：" + e);
			return false;
		}
	}

	@Override
	public List<PerfKPIDefBean> getAllCategory() {
		return perfKPIDefMapper.getAllCategory();
	}

	@Override
	public boolean isExistKPIOfMOClass(SysKPIOfMOClassBean bean) {
		List<SysKPIOfMOClassBean> kpiOfClassLst = kpiOfMOClassMapper.getByKPIAndClass(bean);
		if(kpiOfClassLst.size() > 0){
			return false;
		}
		return true;
	}

	@Override
	public boolean addKPIOfMOClass(SysKPIOfMOClassBean bean) {
		try {
			kpiOfMOClassMapper.insertKPIOfMOClass(bean);
			return true;
		} catch (Exception e) {
			logger.error("新增指标与对象关系异常："+e);
		}
		return false;
	}

	@Override
	public boolean delKPIOfMOClass(int id) {
		logger.info("删除指标与对象类型关系，关系id为：" + id);
		try {
			kpiOfMOClassMapper.delKPIOfMOClass(id);
			return true;
		} catch (Exception e) {
			logger.error("删除指标与对象类型关系异常：" + e);
		}
		return false;
	}

	@Override
	public List<SysKPIOfMOClassBean> getKPIOfMOClassList(
			Page<SysKPIOfMOClassBean> page) {
		return kpiOfMOClassMapper.getKPIOfMOClassList(page);
	}

	@Override
	public List<Integer> getAllParentClassID(String classID) {
		List<Integer> parentIdLst = new ArrayList<Integer>();
		String ids = classID;
		while(true){
			List<Integer> idLst = mobjectInfoMapper.getParentIDByChildIds(ids);
			ids = "";
			if(idLst.size() > 0){
				for (int i = 0; i < idLst.size(); i++) {
//					System.out.println(idLst.get(i));
					parentIdLst.add(idLst.get(i));
					ids += idLst.get(i) + "," ;
				}
				ids = ids.substring(0, ids.lastIndexOf(","));
				if(parentIdLst.contains(MO)){
					break;
				}
			}else{
				break;
			}
		}
		if(Integer.parseInt(classID) == SITE_CLASSID){
			List<MObjectDefBean> moLst = mobjectInfoMapper.getByParentIDs(classID, SITE_CLASSID);
			for (int i = 0; i < moLst.size(); i++) {
				parentIdLst.add(moLst.get(i).getClassId());
			}
		}
		// 空调(若为空调时，配置采集指标的父类型放开，即展开该父类下所有的类型)
		if(Integer.parseInt(classID) == CONDITION_CLASSID){
			List<Integer> idLst = mobjectInfoMapper.getParentIDByChildIds(classID);
			String parentID = "";
			if(idLst.size()>0){
				for (int i = 0; i < idLst.size(); i++) {
					parentIdLst.add(idLst.get(i));
					parentID = String.valueOf(idLst.get(i));
				}
			}
			List<MObjectDefBean> moLst = mobjectInfoMapper.getByParentClassIDs (parentID, CONDITION_CLASSID);
			for (int i = 0; i < moLst.size(); i++) {
				parentIdLst.add(moLst.get(i).getClassId());
			}
		}
		//ups(若为ups时，配置采集指标的父类型放开，即展开该父类下所有的类型)
		if(Integer.parseInt(classID) == UPS_CLASSID){
			List<Integer> idLst = mobjectInfoMapper.getParentIDByChildIds(classID);
			String parentID = "";
			if(idLst.size()>0){
				for (int i = 0; i < idLst.size(); i++) {
					parentIdLst.add(idLst.get(i));
					parentID = String.valueOf(idLst.get(i));
				}
			}
			List<MObjectDefBean> moLst = mobjectInfoMapper.getByParentClassIDs (parentID, UPS_CLASSID);
			for (int i = 0; i < moLst.size(); i++) {
				parentIdLst.add(moLst.get(i).getClassId());
			}
		}
		// 机房监控(若为指标类型为机房监控时，配置采集指标的父类型放开，即展示下面机房监控下面所有的类型)
		if(Integer.parseInt(classID) == ROOMMONITOR_CLASSID){
			List<MObjectDefBean> moLst = mobjectInfoMapper.getByParentClassIDs(classID, ROOMMONITOR_CLASSID);
			for (int i = 0; i < moLst.size(); i++) {
				parentIdLst.add(moLst.get(i).getClassId());
			}
		}
		return parentIdLst;
	}

	@Override
	public Map<String, Object> initParentTree(List<Integer> classIds) {
		List<MObjectDefBean> objectDefLst = mobjectInfoMapper.getByClassIDLst(classIds);
		for (int i = 0; i < objectDefLst.size(); i++) {
			MObjectDefBean bean = objectDefLst.get(i);
			if (bean.getParentClassId() == null) {
				bean.setParentClassId(0);
			}
		}
		String menuLstJson = JsonUtil.toString(objectDefLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

}
