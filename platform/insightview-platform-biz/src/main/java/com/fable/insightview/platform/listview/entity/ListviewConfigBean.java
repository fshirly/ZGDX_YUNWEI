package com.fable.insightview.platform.listview.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fable.insightview.platform.common.entity.ColumnBean;

/**
 * ListView配置转换表
 * 
 * @author zhouwei
 */
//@ApiModel(value = "ListView配置转换表")
public class ListviewConfigBean {
	/**
	 * 主键
	 */
	//@ApiModelProperty(value = "主键")
	private String id;

	/**
	 * 名称（英文）
	 */
	//@ApiModelProperty(value = "名称（英文）")
	private String name;

	/**
	 * 页记录数
	 */
	//@ApiModelProperty(value = "页记录数")
	private BigDecimal pageSize;

	/**
	 * 分页可选范围
	 */
	//@ApiModelProperty(value = "分页可选范围")
	private String pageSizeList;

	/**
	 * 条件列数
	 */
	//@ApiModelProperty(value = " 条件列数")
	private BigDecimal colCount;

	/**
	 * 是否允许导出
	 */
	//@ApiModelProperty(value = "是否允许导出")
	private String isExport;

	/**
	 * 是否自动加载
	 */
	//@ApiModelProperty(value = "是否自动加载")
	private String isAutoBind;

	/**
	 * 列
	 */
	//@ApiModelProperty(value = "列")
	private List<ColumnBean> cols;

	/**
	 * 条件
	 */
	//@ApiModelProperty(value = "条件")
	private List<ListviewConditionDtoBean> condition;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPageSize() {
		return pageSize;
	}

	public void setPageSize(BigDecimal pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageSizeList() {
		return pageSizeList;
	}

	public void setPageSizeList(String pageSizeList) {
		this.pageSizeList = pageSizeList;
	}

	public BigDecimal getColCount() {
		return colCount;
	}

	public void setColCount(BigDecimal colCount) {
		this.colCount = colCount;
	}

	public String getIsExport() {
		return isExport;
	}

	public void setIsExport(String isExport) {
		this.isExport = isExport;
	}

	public String getIsAutoBind() {
		return isAutoBind;
	}

	public void setIsAutoBind(String isAutoBind) {
		this.isAutoBind = isAutoBind;
	}

	public List<ColumnBean> getCols() {
		return cols;
	}

	public void setCols(List<ColumnBean> cols) {
		this.cols = cols;
	}

	public List<ListviewConditionDtoBean> getCondition() {
		return condition;
	}

	public void setCondition(List<ListviewConditionDtoBean> condition) {
		this.condition = condition;
	}

}