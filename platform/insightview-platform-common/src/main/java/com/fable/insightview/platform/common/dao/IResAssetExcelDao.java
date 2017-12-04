package com.fable.insightview.platform.common.dao;

import java.util.List;

import com.fable.insightview.platform.common.entity.ResAssetClassChange;
import com.fable.insightview.platform.common.entity.ResAssetExcelBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface IResAssetExcelDao extends GenericDao<ResAssetExcelBean> {
	
	// 删除导入临时表
	void delResAssetTemp();
	
	// 统计导入数据
	int queryCount();

	/* 更新资产类型和资源类型 */
	void updateConfigItem(ResAssetClassChange resAssetClassChange);

	/* 查询类型数据 */
	List<String> configurationItemLst();

	/* 新增数据 */
	void addResAssetExcel(ResAssetExcelBean resAssetExcelBean);

	/*获取资产编号为空的集合*/
	List<ResAssetExcelBean> getSerialNoIsNullList();

	/*更新资产编号*/
	void updateSerialNo(String assetName, String serialNo);

	List<String> initCodeTableList(String codeTableName, String name);

	/*获取临时表集合*/
	List<ResAssetExcelBean> getAllList();
}
