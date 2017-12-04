package com.fable.insightview.platform.manufacturer.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.manufacturer.dao.IManufacturerDao;
import com.fable.insightview.platform.manufacturer.entity.ManufacturerInfoBean;
import com.fable.insightview.platform.manufacturer.service.IManufacturerService;
/**
 * 
 * @Description:厂商service实现
 * @author zhurt
 * @date 2014-7-9
 */
@Service("manufacturerService")
public class ManufacturerServiceImpl implements IManufacturerService {

	@Autowired
	private IManufacturerDao manufacturerDao;
	@Override
	public void addManufacturer(ManufacturerInfoBean manufacturerInfoBean) {
				// 添加创建时间
		Date createTime = new Date();
		System.out.println("******"+createTime);
		manufacturerInfoBean.setCreateTime(createTime);
		manufacturerDao.addManufacturerInfo(manufacturerInfoBean);
	}

	@Override
	public void delManufacturerInfoById(ManufacturerInfoBean manufacturerInfoBean) {
		manufacturerDao.delManufacturerInfoById(manufacturerInfoBean);
	}

	@Override
	public List<ManufacturerInfoBean> getManufacturerInfoBeanByConditions(
			ManufacturerInfoBean manufacturerInfoBean,FlexiGridPageInfo flexiGridPageInfo) {
		return manufacturerDao.getManufacturerInfoBeanByConditions(
				manufacturerInfoBean, flexiGridPageInfo);
	}

	@Override
	public ManufacturerInfoBean getManufacturerInfoBeanByConditions(String paramName, String paramValue) {
		return manufacturerDao.getManufacturerInfoBeanByConditions(paramName,paramValue);
	}

	@Override
	public List<ManufacturerInfoBean> queryAllManufacturerInfo() {
		return manufacturerDao.queryAllManufacturerInfo();
	}

	@Override
	public int getTotalCount(ManufacturerInfoBean manufacturerInfoBean) {
		return manufacturerDao.getTotalCount(manufacturerInfoBean);
	}

	@Override
	public void updateManufacturer(ManufacturerInfoBean manufacturerInfoBean) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 添加最后修改时间
		manufacturerInfoBean.setLastModifyTime(new Date());
		manufacturerDao.updateManufacturer(manufacturerInfoBean);
	}

	@Override
	public void delBatchInfo(String resManuFacturerId) {
		manufacturerDao.delBatchInfo(resManuFacturerId);
	}

	@Override
	public boolean checkName(ManufacturerInfoBean manufacturerInfoBean) {
		return manufacturerDao.checkName(manufacturerInfoBean);
	}

	public List<ManufacturerInfoBean> getManuFacInfoByConditions(
		ManufacturerInfoBean manufacturerInfoBean) {
		// TODO Auto-generated method stub
		return manufacturerDao
				.getManuFacInfoByConditions(manufacturerInfoBean);
	}
	
	@Override
	public ManufacturerInfoBean getManufacturerInfoById(Integer id) {
		// TODO Auto-generated method stub
		return manufacturerDao.getById(id);
	}

	@Override
	public boolean isUsedByRes (int resManuFacturerId) {
		int count = manufacturerDao.getResCategoryByManuFacturerId(resManuFacturerId);
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public ManufacturerInfoBean getManufacturerInfoByID(int resManuFacturerId) {
		return manufacturerDao.getManufacturerInfoByID(resManuFacturerId);
	}
}
