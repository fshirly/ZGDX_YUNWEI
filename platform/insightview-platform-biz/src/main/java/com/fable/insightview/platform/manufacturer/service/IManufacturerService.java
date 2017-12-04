package com.fable.insightview.platform.manufacturer.service;

import java.util.List;

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.manufacturer.entity.ManufacturerInfoBean;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;
/**
 * @Description:厂商service接口
 * @author zhurt
 * @date 2014-7-9
 */
public interface IManufacturerService {

	/**
	 *@Description:查询所有厂商信息
	 *@returnType List<ManufacturerInfoBean>
	 */
	List<ManufacturerInfoBean> queryAllManufacturerInfo();

	/**
	 *@Description:查询所有厂商信息
	 *@param manufacturerInfoBean
	 *@param flexiGridPageInfo
	 *@returnType List<ManufacturerInfoBean>
	 */
	List<ManufacturerInfoBean> getManufacturerInfoBeanByConditions(
			ManufacturerInfoBean manufacturerInfoBean,	FlexiGridPageInfo flexiGridPageInfo);

	/**
	 *@Description:查询所有厂商信息
	 *@param paramName
	 *@param paramValue
	 *@returnType ManufacturerInfoBean
	 */
	ManufacturerInfoBean getManufacturerInfoBeanByConditions(String paramName,String paramValue);

	/**
	 *@Description:新增厂商
	 *@param manufacturerInfoBean 
	 *@returnType void
	 */
	void addManufacturer(ManufacturerInfoBean manufacturerInfoBean);

	/**
	 *@Description:删除厂商
	 *@param manufacturerInfoBean 
	 *@returnType void
	 */
	void delManufacturerInfoById(ManufacturerInfoBean manufacturerInfoBean);

	/**
	 *@Description:批量删除
	 *@param resManuFacturerId 
	 *@returnType void
	 */
	void delBatchInfo(String resManuFacturerId);

	/**
	 *@Description:修改厂商
	 *@param manufacturerInfoBean 
	 *@returnType void
	 */
	void updateManufacturer(ManufacturerInfoBean manufacturerInfoBean);

	/**
	 *@Description:记录总数据条数
	 *@param manufacturerInfoBean
	 *@returnType int
	 */
	int getTotalCount(ManufacturerInfoBean manufacturerInfoBean);
	
	/**
	 * 验证厂商名称
	 */
	 public boolean  checkName(ManufacturerInfoBean manufacturerInfoBean);
	 
	 List<ManufacturerInfoBean> getManuFacInfoByConditions(
	 ManufacturerInfoBean manufacturerInfoBean);

	 public ManufacturerInfoBean getManufacturerInfoById(Integer id);
	 
	 /**
	 * 根据厂商id获得资源
	 */
	 boolean isUsedByRes(int resManuFacturerId);
	 
	 /**
	 * 根据id获得厂商信息
	 */
	 ManufacturerInfoBean getManufacturerInfoByID(int resManuFacturerId);
}
