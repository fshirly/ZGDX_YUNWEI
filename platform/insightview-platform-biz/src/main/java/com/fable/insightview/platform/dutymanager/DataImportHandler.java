package com.fable.insightview.platform.dutymanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 读取Excel文件的标题及内容数据
 */
public abstract class DataImportHandler {

	private HSSFWorkbook wb;
	
	public DataImportHandler (HSSFWorkbook wb) {
		this.wb = wb;
	}

	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param InputStream
	 * @return String 表头内容的数组
	 */
	@SuppressWarnings("deprecation")
	public String[] readExcelTitle() {
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			title[i] = getCellValue(row.getCell((short) i));
		}
		return title;
	}
	
	/**
	 * 获取实际导入的记录数（不包含空行）
	 * @param columns
	 * @return
	 */
	public int getRecordCount(int columns) {
		int count = 0;
		Map<Integer, Object> content = null;
		HSSFSheet sheet = wb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		for (int i = 1; i <= rowNum; i++) {
			content = getRowData(sheet.getRow(i), columns);
			if (content.isEmpty()) {
				continue;
			}
			count++;
		}
		return count;
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public List<Map<Integer, Object>> readExcelContent(int columns) {
		List<Map<Integer, Object>> contents = new ArrayList<Map<Integer, Object>>();
		Map<Integer, Object> content = null;
		HSSFSheet sheet = wb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		for (int i = 1; i <= rowNum; i++) {
			content = getRowData(sheet.getRow(i), columns);
			/*if (content.isEmpty()) {
				continue;
			}*/
			contents.add(content);
		}
		return contents;
	}
	
	/**
	 * 获取一行数据内容
	 * @param row
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Map<Integer, Object> getRowData (HSSFRow row, int columns) {
		Map<Integer, Object> content = new HashMap<Integer, Object>();
		String value = null;
		if (row == null) {
			return content;
		}
        int j = 0;
        while (j < columns) {
        	value = getCellValue(row.getCell(j)).trim();
        	if (StringUtils.isNotEmpty(value)) {
        		if (j == columns - 3 || j == columns - 1) {
        			content.put(j, value);
        		} else if (j == 0) {
        			content.put(j, new String[]{value});
        		} else {
        			content.put(j, value.split(","));
        		}
        	}
            j++;
        }
       return content;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	public abstract String getCellValue(HSSFCell cell);

}
