package com.fable.insightview.platform.dictionary.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dictionary.dao.IDataDictManageDao;
import com.fable.insightview.platform.dictionary.entity.ConstantItemDef;
import com.fable.insightview.platform.dictionary.entity.ConstantTypeDef;
import com.fable.insightview.platform.dictionary.service.IDataDictManageService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.service.DictService;

@Service("dataDictManageService")
public class DataDictManageServiceImpl implements IDataDictManageService{
	@Autowired
	private IDataDictManageDao dataDictManageDao;
	@Autowired
	private DictService dictService;

	@Override
	public List<ConstantTypeDef> getConstantTypeTreeVal() {
		return dataDictManageDao.getConstantTypeTreeVal();
	}

	@Override
	public Map<String, Object> getConstantItemList(ConstantItemDef constantItemDef,FlexiGridPageInfo flexiGridPageInfo) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<ConstantItemDef> constantItemList = new ArrayList<ConstantItemDef>();
		constantItemList = this.dataDictManageDao.getConstantItemList(constantItemDef, flexiGridPageInfo);
		int count = this.dataDictManageDao.getConstantTtemListCount(constantItemDef);
		result.put("constantItemList", constantItemList);
		result.put("total", count);
		return result;
	}

	@Override
	public boolean addDataDictItem(ConstantItemDef constantItemDef) {
		dictService.clear();
		return dataDictManageDao.addConstantItem(constantItemDef);
	}

	@Override
	public int getMaxConstItemId(ConstantItemDef constantItemDef) {
		return dataDictManageDao.getMaxConstItemId(constantItemDef)+1;
	}

	@Override
	public Map<String, Object> getConstantTypeList(
			ConstantTypeDef constantTypeDef, FlexiGridPageInfo flexiGridPageInfo) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<ConstantTypeDef> constantTypeList = new ArrayList<ConstantTypeDef>();
		constantTypeList = this.dataDictManageDao.getConstantTypeList(constantTypeDef, flexiGridPageInfo);
		int count = this.dataDictManageDao.getConstantTypeListCount(constantTypeDef);
		result.put("constantTypeList", constantTypeList);
		result.put("total", count);
		return result;
	}

	@Override
	public boolean isLeaf(int constantTypeId) {
		boolean flag1=this.dataDictManageDao.isLeafType(constantTypeId);
		boolean flag2=this.dataDictManageDao.isLeafItem(constantTypeId);
		if(flag1==true && flag2==true){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean deleteConstantTypeById(int constantTypeId) {
		try {
			dataDictManageDao.deleteConstantTypeById(constantTypeId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<ConstantTypeDef> getConstantTypeName(int constantTypeId) {
		return this.dataDictManageDao.getConstantTypeName(constantTypeId);
	}

	@Override
	public boolean addDataDictType(ConstantTypeDef constantTypeDef) {
		return this.dataDictManageDao.addDataDictType(constantTypeDef);
	}

	@Override
	public List<ConstantTypeDef> getParentTypeName(int parentTypeID) {
		return this.dataDictManageDao.getParentTypeName(parentTypeID);
	}

	@Override
	public boolean deleteConstantItemById(int id) {
		try {
			dictService.clear();
			dataDictManageDao.deleteConstantItemById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

	@Override
	public List<ConstantItemDef> getConstantItem(int id) {
		return this.dataDictManageDao.getConstantItem(id);
	}

	@Override
	public List<ConstantTypeDef> getConstantType(int constantTypeId) {
		return this.dataDictManageDao.getConstantType(constantTypeId);
	}

	@Override
	public boolean updateDataDictItem(ConstantItemDef constantItemDef) {
		dictService.clear();
		return this.dataDictManageDao.updateDataDictItem(constantItemDef);
	}

	@Override
	public boolean updateDataDictType(ConstantTypeDef constantTypeDef) {
		return this.dataDictManageDao.updateDataDictType(constantTypeDef);
	}

	@Override
	public boolean isLeafType(int constantTypeId) {
		return this.dataDictManageDao.isLeafItem(constantTypeId);
	}

	@Override
	public ConstantTypeDef getTypeIdByTypeName(String typeName) {
		return this.dataDictManageDao.getTypeIdByTypeName(typeName);
	}

	@Override
	public Map<String, Object> getConstantTypeList(
			ConstantTypeDef constantTypeDef,
			FlexiGridPageInfo flexiGridPageInfo, String type) {
		Map<String,Object> result = new HashMap<String,Object>();
		List<ConstantTypeDef> constantTypeList = new ArrayList<ConstantTypeDef>();
		constantTypeList = this.dataDictManageDao.getConstantTypeList(constantTypeDef, flexiGridPageInfo,type);
		int count = this.dataDictManageDao.getConstantTypeListCount(constantTypeDef,type);
		result.put("constantTypeList", constantTypeList);
		result.put("total", count);
		return result;
	}

	@Override
	public boolean checkItemId(ConstantItemDef constantItemDef) {
		return this.dataDictManageDao.checkItemId(constantItemDef);
	}

	@Override
	public boolean deleteItemByConstantTypeId(int constantTypeId) {
		return this.dataDictManageDao.deleteItemByConstantTypeId(constantTypeId);
	}

	@Override
	public boolean checkDataDictItemName(ConstantItemDef constantItemDef) {
		return this.dataDictManageDao.checkDataDictItemName(constantItemDef);
	}

	@Override
	public boolean checkDataDictItemAlias(ConstantItemDef constantItemDef) {
		return this.dataDictManageDao.checkDataDictItemAlias(constantItemDef);
	}

	@Override
	public List<ConstantItemDef> findDictDataByDictTypeId(String constantTypeId) {
		return this.dataDictManageDao.findDictDataByDictTypeId(constantTypeId);
	}

	@Override
	public boolean checkTypeName(ConstantTypeDef constantTypeDef) {
		List<ConstantTypeDef> typelist = this.dataDictManageDao.getByTypeNameAndId(constantTypeDef);
		if(typelist.size() > 0){
			return false;
		}
		return true;
	}
	
	
}
