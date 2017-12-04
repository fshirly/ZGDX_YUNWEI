package com.fable.insightview.platform.common.service;

import java.util.List;

import com.fable.insightview.platform.common.entity.ResAssetClassChange;
import com.fable.insightview.platform.common.entity.ResAssetExcelBean;

public interface IResAssetExcelService {
	// 删除导入临时表
	void delResAssetTemp();

	/* 统计导入数据 */
	int queryCount();

	/* 更新资产类型和资源类型 */
	void updateConfigItem(ResAssetClassChange resAssetClassChange);

	/* 查询类型数据 */
	List<ResAssetClassChange> configurationItemLst();

	/* 新增数据 */
	void addResAssetExcel(List<ResAssetExcelBean> resAssetExcelBean);

	/*初始化配置文件里配置的表的数据*/
	List<String> initCodeTableList(String codeTableName, String name);

}
