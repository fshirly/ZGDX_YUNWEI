package com.fable.insightview.platform.importdata.resolver.metadata;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fable.insightview.platform.importdata.resolver.Mapping;

public class FieldMetaData {
	
	private String fieldName;
	
	private Mapping mapping;
	
	private Processor processor;
	
	//key=父层级fieldname  value=映射列名
	private Map<String, String> nameMapping = new HashMap<String, String>();
	
	public boolean hasMappingName() {
		if(hasMapping() && (nameMapping.size() >0 || !StringUtils.isEmpty(mapping.columnName()))){
			return true;
		}
		return false;
	}
	
	public boolean hasMapping() {
		return mapping != null;
	}
	
	public int getMappingIndex(){
		return mapping.columnIdex();
	}

	/**
	 * 取映射name，解决一个类映射多列的情况
	 * 
	 * @param hierarchyFieldName  具有层级的fieldname
	 * @return
	 */
	public String getMappingName(String hierarchyFieldName) {
		String parentHierarchyName = hierarchyFieldName;
		int i = hierarchyFieldName.lastIndexOf(".");
		if(i > 0){
			parentHierarchyName = hierarchyFieldName.substring(0, i);
		}
		
		if(!StringUtils.isEmpty(mapping.columnName())){
			return mapping.columnName();
		}else{
			if(nameMapping.size() > 0){
				return nameMapping.get(parentHierarchyName);
			}
			return null;
		}
	}
	
	private void initNameMapping(){
		if(!StringUtils.isEmpty(mapping.columnName())){
			String[] multiName = mapping.columnName().split(",");
			if(multiName != null && multiName.length > 0){
				for(String name : multiName){
					String[] m = name.split(":");
					if(m.length > 1){
						nameMapping.put(m[0], m[1]);
					}
				}
			}
		}
	}
	
	public final Map<String, String> getNameMapping() {
		return nameMapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
		initNameMapping();
	}

	public Processor getProcessor() {
		return processor;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
