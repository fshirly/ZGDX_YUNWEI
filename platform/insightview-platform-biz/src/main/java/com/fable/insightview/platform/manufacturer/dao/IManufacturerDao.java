package com.fable.insightview.platform.manufacturer.dao;

import java.util.List;

import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.manufacturer.entity.ManufacturerInfoBean;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;
/**
 * 
 * @Description:厂商Dao接口
 * @author zhurt
 * @date 2014-7-9
 */
public interface IManufacturerDao extends GenericDao<ManufacturerInfoBean> {

	/**
	 * 查询所有厂商信息
	 */
	List<ManufacturerInfoBean> queryAllManufacturerInfo();

	/**
	 * 查询所有厂商信息
	 */
	List<ManufacturerInfoBean> getManufacturerInfoBeanByConditions(
			ManufacturerInfoBean manufacturerInfoBean,FlexiGridPageInfo flexiGridPageInfo);

	/**
	 * 查询所有厂商信息
	 */
	ManufacturerInfoBean getManufacturerInfoBeanByConditions(String paramName,String paramValue);

	/**
	 * 新增
	 */
	void addManufacturerInfo(ManufacturerInfoBean manufacturerInfoBean);

	/**
	 *删除
	 */
	void delManufacturerInfoById(ManufacturerInfoBean manufacturerInfoBean);

	/**
	 *批量删除
	 */
	void delBatchInfo(String resManuFacturerId);

	/**
	 *更新修改
	 */
	void updateManufacturer(ManufacturerInfoBean manufacturerInfoBean);

	/**
	 * 记录总数据条数
	 */
	int getTotalCount(ManufacturerInfoBean manufacturerInfoBean);
	
	/**
	 * 验证厂商名称
	 */
	public boolean checkName(ManufacturerInfoBean manufacturerInfoBean);

	List<ManufacturerInfoBean> getManuFacInfoByConditions(
			ManufacturerInfoBean manufacturerInfoBean);
	
	/**
	 * 根据厂商id获得资源
	 */
	int getResCategoryByManuFacturerId(int resManuFacturerId); 
	
	/**
	 * 根据id获得厂商信息
	 */
	ManufacturerInfoBean getManufacturerInfoByID(int resManuFacturerId);
}
