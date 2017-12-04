package com.fable.insightview.platform.dutymanager;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DutyData extends DataImportHandler {

	private double min = 0.0;
	private double max = 0.0;
	private String minD = null;
	private String maxD = null;

	public DutyData(HSSFWorkbook wb) {
		super(wb);
	}

	@Override
	public String getCellValue(HSSFCell cell) {
		String value = "";
		if (cell == null) {
			return value;
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			value = String.valueOf(cell.getNumericCellValue());
			value = value.substring(0, value.length()-2);
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue());
				value = sdf.format(date);
				if (cell.getNumericCellValue() < min || min == 0.0) {
					min = cell.getNumericCellValue();
					minD = value;
				}
				if (cell.getNumericCellValue() > max || max == 0.0) {
					max = cell.getNumericCellValue();
					maxD = value;
				}
			}
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
		}
		return value;
	}

	public String getMinD() {
		return minD;
	}

	public String getMaxD() {
		return maxD;
	}

}
