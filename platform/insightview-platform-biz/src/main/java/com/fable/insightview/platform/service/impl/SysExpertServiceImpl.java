package com.fable.insightview.platform.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fable.insightview.platform.dao.ISysExpertDao;
import com.fable.insightview.platform.entity.SysExpertBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.service.ISysExpertService;

@Service("sysExpertService")
public class SysExpertServiceImpl implements ISysExpertService{

	@Autowired ISysExpertDao sysExportDao;
	 
	@Override
	public List<SysExpertBean> getExpertByConditions(
			SysExpertBean sysExpertBean, FlexiGridPageInfo flexiGridPageInfo) {
		return sysExportDao.getExpertByConditions(sysExpertBean, flexiGridPageInfo);
	}

	@Override
	public int getTotalCount(SysExpertBean sysExpertBean) {
		// TODO Auto-generated method stub
		return sysExportDao.getTotalCount(sysExpertBean);
	}

	@Override
	public List<SysExpertBean> getExpertListInCurrentStep(
			List<Integer> expertIdList) {
		// TODO Auto-generated method stub
		if(expertIdList.size()!=0){
			String ids="";
			for (int i = 0; i < expertIdList.size()-1; i++) {
				ids=ids+expertIdList.get(i)+",";
			}
			ids+=expertIdList.get(expertIdList.size()-1);
			return sysExportDao.getSysExpertsInCurrentStep(ids);
		}else{
			return sysExportDao.getSysExpertsInCurrentStep(null);
		}
	}

//	@Override
//	public String getExpertExistNames(Integer participateStep,
//			Integer projectStepId, String ids) {
//		String[] splitIds = ids.split(",");
//		List<String> idsList = new ArrayList<String>();
//		for (String id : splitIds) {
//			idsList.add(id);
//		}
//		String expertNames="";
//		for (int i = 0; i < idsList.size(); i++) {
//			SysExpertBean sysExpertBean=sysExportDao.getExpertByCondition(participateStep,projectStepId,Integer.parseInt(idsList.get(i)));
//			if(null!=sysExpertBean){
//				expertNames=expertNames+"["+sysExpertBean.getName()+"]";
//			}
//		}
//		return expertNames;
//	}

	@Override
	public List<SysExpertBean> getExpertByConditions(
			SysExpertBean sysExpertBean, FlexiGridPageInfo flexiGridPageInfo,
			List<Integer> expertIdList) {
		if(expertIdList.size()!=0){
			String ids="(";
			for (int i = 0; i < expertIdList.size()-1; i++) {
				ids=ids+expertIdList.get(i)+",";
			}
			ids+=expertIdList.get(expertIdList.size()-1)+")";
			return sysExportDao.getExpertByConditions(sysExpertBean, flexiGridPageInfo,ids);
		}
		return sysExportDao.getExpertByConditions(sysExpertBean, flexiGridPageInfo);
	}

	@Override
	public int getTotalExceptExist(SysExpertBean sysExpertBean,
			List<Integer> expertIdList) {
		if(expertIdList.size()!=0){
			String ids="(";
			for (int i = 0; i < expertIdList.size()-1; i++) {
				ids=ids+expertIdList.get(i)+",";
			}
			ids+=expertIdList.get(expertIdList.size()-1)+")";
			return sysExportDao.getTotalExceptExist(sysExpertBean,ids);
		}
		return sysExportDao.getTotalCount(sysExpertBean);
	}

}
