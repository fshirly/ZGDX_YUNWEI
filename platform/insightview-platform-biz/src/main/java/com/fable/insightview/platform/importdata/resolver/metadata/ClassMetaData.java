package com.fable.insightview.platform.importdata.resolver.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ClassMetaData {

	private Class clazz;
	
	private Processor processor;
	
	private List<FieldMetaData> fieldMetaDataList = new ArrayList<FieldMetaData>();
	
	// 子对象类型元数据 key=fieldName
	private Map<String, ClassMetaData> childClassMetaDataMap = new HashMap<String, ClassMetaData>();
	
	// 分层结构的属性元数据 key=fieldName
	private Map<String, FieldMetaData> hierarchyFieldMetaDataMap = new HashMap<String, FieldMetaData>();
	
	public final Class getClazz() {
		return clazz;
	}

	public final void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public final Map<String, FieldMetaData> getHierarchyFieldMetaDataMap() {
		if(hierarchyFieldMetaDataMap.size() <= 0){
			buildChildFieldMetaDataMap(null, this, hierarchyFieldMetaDataMap);
		}
		return hierarchyFieldMetaDataMap;
	}

	public final Processor getProcessor() {
		return processor;
	}

	public final void setProcessor(Processor processor) {
		this.processor = processor;
	}
	
	public final List<FieldMetaData> getFieldMetaDataList() {
		return fieldMetaDataList;
	}

	public final void setFieldMetaDataList(List<FieldMetaData> fieldMetaDataList) {
		this.fieldMetaDataList = fieldMetaDataList;
	}

	public final Map<String, ClassMetaData> getChildClassMetaDataMap() {
		return childClassMetaDataMap;
	}

	public final void setChildClassMetaDataMap(
			Map<String, ClassMetaData> childClassMetaDataMap) {
		this.childClassMetaDataMap = childClassMetaDataMap;
		buildChildFieldMetaDataMap(null, this, hierarchyFieldMetaDataMap);
	}

	private void buildChildFieldMetaDataMap(String propertyPath, ClassMetaData classMetaData, Map<String, FieldMetaData> map){
		String path = propertyPath==null?"":propertyPath + ".";
		for(FieldMetaData fieldMetaData : classMetaData.getFieldMetaDataList()){
			map.put(path + fieldMetaData.getFieldName(), fieldMetaData);
		}
		
		Iterator<String> iterator = classMetaData.getChildClassMetaDataMap().keySet().iterator();
		while(iterator.hasNext()){
			String fieldName = iterator.next();
			ClassMetaData childClassMetaData = classMetaData.getChildClassMetaDataMap().get(fieldName);
			buildChildFieldMetaDataMap(path + fieldName, childClassMetaData, map);
		}
	}
}
