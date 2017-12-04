package com.fable.insightview.platform.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.dao.IResAssetExcelDao;
import com.fable.insightview.platform.common.entity.ResAssetClassChange;
import com.fable.insightview.platform.common.entity.ResAssetExcelBean;
import com.fable.insightview.platform.common.service.IResAssetExcelService;

@Service("resAssetExcelService")
public class ResAssetExcelServiceImpl implements IResAssetExcelService {

	@Autowired
	IResAssetExcelDao resAssetExcelDao;

	public void delResAssetTemp() {
		resAssetExcelDao.delResAssetTemp();
	}

	public int queryCount() {
		return resAssetExcelDao.queryCount();
	}

	public void addResAssetExcel(List<ResAssetExcelBean> resAssetExcelLst) {
		// 遍历资产集合，将数据导入到临时表
		for (ResAssetExcelBean resAsset : resAssetExcelLst) {
			if (null == resAsset.getResAssetType()
					|| "" == resAsset.getResAssetType().trim()) {
				resAsset.setResAssetType("未填写");
			}
			resAssetExcelDao.addResAssetExcel(resAsset);
		}
	}

	public void updateConfigItem(ResAssetClassChange resAssetClassChange) {
		String eStr = resAssetClassChange.getExportItsmClass();
		String rStr = resAssetClassChange.getResAssetItsmClass();
		String cStr = resAssetClassChange.getResCiItsmClass();

		String[] eArr = eStr.split(",");
		String[] rArr = rStr.split(",");
		String[] cArr = cStr.split(",");

		for (int i = 0; i < eArr.length; i++) {
			ResAssetClassChange resChange = new ResAssetClassChange(eArr[i],
					rArr[i], cArr[i]);
			resAssetExcelDao.updateConfigItem(resChange);
		}

	}

	public List<ResAssetClassChange> configurationItemLst() {
		List<String> confItemLst = resAssetExcelDao.configurationItemLst();
		List<ResAssetClassChange> confBeanLst = new ArrayList<ResAssetClassChange>();
		for (String item : confItemLst) {
			ResAssetClassChange resAssetItem = new ResAssetClassChange(item);
			confBeanLst.add(resAssetItem);
		}
		return confBeanLst;
	}

	@Override
	public List<String> initCodeTableList(String codeTableName, String name) {
		// TODO Auto-generated method stub
		return resAssetExcelDao.initCodeTableList(codeTableName, name);
	}
}
