package com.fable.insightview.platform.importdata.resolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.fable.insightview.platform.importdata.resolver.metadata.FieldMetaData;
import com.fable.insightview.platform.importdata.resolver.metadata.MetaDataFactory;

public class TextResolver<T> extends InputStreamResolver<T> {

	protected String charsetName = "UTF-8";
	
	protected String targetText = "";// 需要解析的文本
	
	protected int startRow = 1;

	protected int endRow = 0; 

	protected String lineSeparator = "[\r\n]+";// 行分隔符

	protected String columnSeparator = "\\|";// 列分隔符

	private Map<String, Integer> nameIndexMap = new HashMap<String, Integer>();// 列名和列索引
																				// 映射关系
	public List<T> resolve() {

		List<T> ls = new ArrayList<T>();
		try {
			
			List<String[]> dataList = new ArrayList<String[]>();
			if (!StringUtils.isEmpty(targetText)) {
				String[] lines = targetText.split(lineSeparator);
				for (int i = 0; i < lines.length; i++) {
					String[] columns = lines[i].split(columnSeparator);
					dataList.add(columns);
				} 
			}
			
			//建立列名和列索引的映射关系
			initNameIndexMap(dataList.get(0));

			int end =  endRow>0?endRow:dataList.size();
			for (int i = startRow; i < end; i++) {
				Map<String, String> map = new HashMap<String, String>();

				Map<String, FieldMetaData> fieldMetaDataMap = MetaDataFactory.getInstance().getHierarchyFieldMetaDataByClazz(this.mappingClass);
				Iterator<String> iterator = fieldMetaDataMap.keySet().iterator();
				while (iterator.hasNext()) {
					String fieldName = iterator.next();
					FieldMetaData fieldMetaData = fieldMetaDataMap.get(fieldName);

					int index = 0;
					String name = fieldMetaData.getMappingName(fieldName);
					if (!StringUtils.isEmpty(name)) {
						index = nameIndexMap.get(name);
					} else {
						index = fieldMetaData.getMappingIndex();
					}
					map.put(fieldName, dataList.get(i)[index]);
				}
				T target = mappingClass.newInstance();
				BeanUtils.copyProperties(target, map);
				ls.add(target);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}
	
	@Override
	public void setInputStream(InputStream inputStream) {
		super.setInputStream(inputStream);
		targetText = getTargetText(this.getInputStream());
	}

	/**
	 * 建立列名和列索引的映射关系
	 * 
	 * @param columns
	 */
	private void initNameIndexMap(String[] columns) {
		nameIndexMap.clear();
		Map<String, FieldMetaData> fieldMetaDataMap = MetaDataFactory.getInstance().getHierarchyFieldMetaDataByClazz(this.mappingClass);
		if (fieldMetaDataMap.size() > 0) {
			FieldMetaData fieldMetaData = fieldMetaDataMap.values().iterator().next();
			if(fieldMetaData.hasMappingName()){
				for (int i = 0; i < columns.length; i++) {
					nameIndexMap.put(columns[i].trim(), i);
				}
			}
		}
	}
	
	private String getTargetText(InputStream is){
		StringBuilder sb = new StringBuilder(); 
		if(is != null){
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, charsetName));
				String line = null; 	
				while ((line = reader.readLine()) != null) {    
	                sb.append(line.trim() + "\n");    
	            }
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {    
	            try {    
	                is.close();    
	            } catch (IOException e) {    
	                e.printStackTrace();    
	            }    
	        }
		}
		return sb.toString();
	}

	public final String getColumnSeparator() {
		return columnSeparator;
	}

	public final void setColumnSeparator(String columnSeparator) {
		this.columnSeparator = columnSeparator;
	}

	public final int getEndRow() {
		return endRow;
	}

	public final void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public final String getLineSeparator() {
		return lineSeparator;
	}

	public final void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}

	public final int getStartRow() {
		return startRow;
	}

	public final void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public final String getTargetText() {
		return targetText;
	}

	public final void setTargetText(String targetText) {
		this.targetText = targetText;
	}

}
