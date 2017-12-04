package com.fable.insightview.platform.dictionary.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.dictionary.entity.ConstantItemDef;
import com.fable.insightview.platform.dictionary.entity.ConstantTypeDef;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface IDataDictManageService {
	public List<ConstantTypeDef> getConstantTypeTreeVal();

	public Map<String, Object> getConstantItemList(
			ConstantItemDef constantItemDef, FlexiGridPageInfo flexiGridPageInfo);

	public boolean addDataDictItem(ConstantItemDef constantItemDef);

	public int getMaxConstItemId(ConstantItemDef constantItemDef);

	public Map<String, Object> getConstantTypeList(
			ConstantTypeDef constantTypeDef, FlexiGridPageInfo flexiGridPageInfo);

	public Map<String, Object> getConstantTypeList(
			ConstantTypeDef constantTypeDef,
			FlexiGridPageInfo flexiGridPageInfo, String type);

	public boolean isLeaf(int constantTypeId);

	public boolean isLeafType(int constantTypeId);

	public boolean deleteConstantTypeById(int constantTypeId);

	public List<ConstantTypeDef> getConstantTypeName(int constantTypeId);

	public List<ConstantTypeDef> getParentTypeName(int parentTypeID);

	public boolean addDataDictType(ConstantTypeDef constantTypeDef);

	public boolean deleteConstantItemById(int id);

	public boolean updateDataDictItem(ConstantItemDef constantItemDef);

	public List<ConstantItemDef> getConstantItem(int id);

	public boolean updateDataDictType(ConstantTypeDef constantTypeDef);

	public List<ConstantTypeDef> getConstantType(int constantTypeId);

	public ConstantTypeDef getTypeIdByTypeName(String typeName);

	public boolean checkItemId(ConstantItemDef constantItemDef);

	public boolean deleteItemByConstantTypeId(int constantTypeId);
	
	public boolean checkDataDictItemName(ConstantItemDef constantItemDef);
	
	public boolean checkDataDictItemAlias(ConstantItemDef constantItemDef);

	/**
	 * 根据数据字典类型获取数据字典项列表
	 * @param constantTypeId
	 * @return
	 */
	public List<ConstantItemDef> findDictDataByDictTypeId(String constantTypeId);
	
	/**
	 * 校验字典类别名的唯一性
	 * @param constantTypeDef
	 * @return
	 */
	boolean checkTypeName(ConstantTypeDef constantTypeDef);
}
