package com.fable.insightview.platform.listview.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.common.entity.TreeDictionaryBean;

/**
 * ListView条件转换表
 * 
 * @author zhouwei
 */
//@ApiModel(value = "ListView条件转换表")
public class ListviewConditionDtoBean {

	/**
	 * 控件类型
	 */
	//@ApiModelProperty(value = "控件类型")
	private String controlType;

	/**
	 * 属性名称(字段名)
	 */
	//@ApiModelProperty(value = "属性名称(字段名)")
	private String propertyName;

	/**
	 * 显示标题
	 */
	//@ApiModelProperty(value = "显示标题")
	private String displayTitle;

	/**
	 * values
	 */
	//@ApiModelProperty(value = "值数组")
	private List<TreeDictionaryBean> values;
	
	/**
	 * value
	 */
	//@ApiModelProperty(value = "值")
	private String value;
	
	/**
	 * defaultValue
	 */
	//@ApiModelProperty(value = "默认值")
	private String defaultValue;

	/**
	 * URL
	 */
	//@ApiModelProperty(value = "请求URL")
	private String url;
	
	/**
	 * method
	 */
	//@ApiModelProperty(value = "请求方法")
	private String method = "get";
	
	/**
	 * params
	 */
	//@ApiModelProperty(value = "请求参数")
	private Map<String,Object> params = new HashMap<String,Object>();
	
	/**
	 * type
	 */
	//@ApiModelProperty(value = "数据字典类型")
	private String type;

	/**
	 * 下级ID propertyName
	 */
	//@ApiModelProperty(value = "下拉框下级Id")
	private String childId;

	/**
	 * 是否多选
	 */
	//@ApiModelProperty(value = "是否多选")
	private String multiple = "false";
	
	/**
	 * 单位机构opts
	 * @return
	 */
	private Map<String,Object> opts;

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getDisplayTitle() {
		return displayTitle;
	}

	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
	}


	public List<TreeDictionaryBean> getValues() {
		return values;
	}

	public void setValues(List<TreeDictionaryBean> values) {
		this.values = values;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Map<String, Object> getOpts() {
		return opts;
	}

	public void setOpts(Map<String, Object> opts) {
		this.opts = opts;
	}

}