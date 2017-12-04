package com.fable.insightview.platform.common.dynamicdb;
/**
 * 字段属性
 * @author 郑自辉
 *
 */
public class Column {

	/**
	 * 字段名称
	 */
	private String columnName;
	
	/**
	 * 字段显示文本
	 */
	private String attributeName;
	
	/*
	 * 字段类型
	 */
	private String dataType;
	
	/**
	 * 是否显示在列表页
	 */
	private Integer isTableDisplay;
	
	/**
	 * 表名称
	 */
	private String tableName;
	
	/*
	 * 字段ID
	 */
	private Integer attributeId;
	
	/*
	 * 字段初始值
	 */
	private Integer dataInitValue;
	
	/*
	 * 字段控件类型
	 */
	private String widgetValue;
	
	public Column()
	{
		
	}
	
	public Column(String columnName,int isTableDisplay)
	{
		this.columnName = columnName;
		this.isTableDisplay = isTableDisplay;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getIsTableDisplay() {
		return isTableDisplay;
	}

	public void setIsTableDisplay(Integer isTableDisplay) {
		this.isTableDisplay = isTableDisplay;
	}
	
		public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}

	public Integer getDataInitValue() {
		return dataInitValue;
	}

	public void setDataInitValue(Integer dataInitValue) {
		this.dataInitValue = dataInitValue;
	}

	public String getWidgetValue() {
		return widgetValue;
	}

	public void setWidgetValue(String widgetValue) {
		this.widgetValue = widgetValue;
	}
	
}
