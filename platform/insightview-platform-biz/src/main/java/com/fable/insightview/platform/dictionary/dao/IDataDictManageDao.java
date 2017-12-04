package com.fable.insightview.platform.dictionary.dao;

import java.util.List;

import com.fable.insightview.platform.dictionary.entity.ConstantItemDef;
import com.fable.insightview.platform.dictionary.entity.ConstantTypeDef;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

public interface IDataDictManageDao extends GenericDao<ConstantTypeDef>{
	public List<ConstantTypeDef> getConstantTypeTreeVal();
	public List<ConstantItemDef> getConstantItemList(ConstantItemDef constantItemDef,FlexiGridPageInfo flexiGridPageInfo);
	public int getConstantTtemListCount(ConstantItemDef constantItemDef);
	public boolean addConstantItem(ConstantItemDef constantItemDef);
	public int getMaxConstItemId(ConstantItemDef constantItemDef);
	public List<ConstantTypeDef> getConstantTypeList(ConstantTypeDef constantTypeDef,FlexiGridPageInfo flexiGridPageInfo);
	public List<ConstantTypeDef> getConstantTypeList(ConstantTypeDef constantTypeDef,FlexiGridPageInfo flexiGridPageInfo,String type);
	public int getConstantTypeListCount(ConstantTypeDef constantTypeDef);
	public int getConstantTypeListCount(ConstantTypeDef constantTypeDef,String type);
	public boolean isLeafType(int constantTypeId);
	public boolean isLeafItem(int constantTypeId);
	public List<ConstantTypeDef> getConstantTypeName(int constantTypeId);
	public List<ConstantTypeDef> getParentTypeName(int parentTypeID);
	public boolean addDataDictType(ConstantTypeDef constantTypeDef);
	public boolean deleteConstantItemById(int id);
	public boolean deleteConstantTypeById(int constantTypeId);
	
	public boolean updateDataDictItem(ConstantItemDef constantItemDef);
	public List<ConstantItemDef> getConstantItem(int id);
	
	public boolean updateDataDictType(ConstantTypeDef constantTypeDef);
	public List<ConstantTypeDef> getConstantType(int constantTypeId);
	
	public int getLevel(int constantTypeId);
	
	public ConstantTypeDef getTypeIdByTypeName(String typeName);
	public boolean checkItemId(ConstantItemDef constantItemDef);
	public boolean deleteItemByConstantTypeId(int constantTypeId);
	
	public boolean checkDataDictItemName(ConstantItemDef constantItemDef);
	
	public boolean checkDataDictItemAlias(ConstantItemDef constantItemDef);
	public List<ConstantItemDef> findDictDataByDictTypeId(String constantTypeId);
	
	public List<ConstantTypeDef> getByTypeNameAndId(ConstantTypeDef constantTypeDef);
}
