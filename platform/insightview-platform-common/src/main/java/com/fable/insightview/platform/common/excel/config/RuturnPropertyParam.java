package com.fable.insightview.platform.common.excel.config;

public class RuturnPropertyParam {
	private String name = null;
	private String column = null;
	private String excelTitleName = null;
	private String isNull=null;
	private String columnWidth=null;
	private String dataType = null;
	private String maxLength = null;
	private String fixity = null;
	private String codeTableName = null;
	private String defaultValue = null;
	private String greaterThan = null;
	
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getExcelTitleName() {
		return excelTitleName;
	}
	public void setExcelTitleName(String excelTitleName) {
		this.excelTitleName = excelTitleName;
	}
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCodeTableName() {
		return codeTableName;
	}
	public void setCodeTableName(String codeTableName) {
		this.codeTableName = codeTableName;
	}
	public String getFixity() {
		return fixity;
	}
	public void setFixity(String fixity) {
		this.fixity = fixity;
	}
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public String getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}
	public String getGreaterThan() {
		return greaterThan;
	}
	public void setGreaterThan(String greaterThan) {
		this.greaterThan = greaterThan;
	}
	
}
