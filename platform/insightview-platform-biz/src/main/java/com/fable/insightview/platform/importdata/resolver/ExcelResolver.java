package com.fable.insightview.platform.importdata.resolver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;

import com.fable.insightview.platform.importdata.resolver.metadata.FieldMetaData;
import com.fable.insightview.platform.importdata.resolver.metadata.MetaDataFactory;

public class ExcelResolver<T> extends InputStreamResolver<T> {

	private int sheetIndex = 0;

	private String sheetName;

	private int startRow = 1;

	private int endRow = 0;

	public List<T> resolve() {
		
		List<T> ls = new ArrayList<T>();
		
		try {
			Workbook wb = WorkbookFactory.create(this.getInputStream());

			Sheet st;
			if (!StringUtils.isEmpty(sheetName)) {
				st = wb.getSheet(sheetName);
			} else {
				st = wb.getSheetAt(sheetIndex);
			}
			
			int rows = st.getPhysicalNumberOfRows();// 得到工作簿的行数
			if (rows > 1) {
				if(endRow <= 0){
					endRow = rows;
				}
				for (int i = startRow; i < endRow; i++) {
					Row r = st.getRow(i);
					if(r != null){
						T target = mappingClass.newInstance();
						BeanUtils.copyProperties(target, this.rowToMap(r));
						ls.add(target);
					}
				}
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {    
            try {    
            	this.getInputStream().close();    
            } catch (IOException e) {    
                e.printStackTrace();    
            }    
        }
		return ls;
	}

	private Map<String, String> rowToMap(Row r) {

		Map<String, String> map = new HashMap<String, String>();
		
		Map<String, FieldMetaData> fieldMetaDataMap = MetaDataFactory.getInstance().getHierarchyFieldMetaDataByClazz(this.mappingClass);
		Iterator<String> iterator = fieldMetaDataMap.keySet().iterator();
		while (iterator.hasNext()) {
			String fieldName = iterator.next();
			FieldMetaData fieldMetaData = fieldMetaDataMap.get(fieldName);

			String columnName;
			int columnIdex;
			
			if (fieldMetaData.hasMapping()) {
				columnName = fieldMetaData.getMappingName(fieldName);
				if (!StringUtils.isEmpty(columnName)) {
					columnIdex = CellReference.convertColStringToIndex(columnName);
				} else {
					columnIdex = fieldMetaData.getMappingIndex();
				}

				String value = "";
				if(r.getCell(columnIdex) != null){
					r.getCell(columnIdex).setCellType(Cell.CELL_TYPE_STRING);
					value = r.getCell(columnIdex).getStringCellValue().trim();
				}
				map.put(fieldName, value);
			}
		}
		return map;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
}
