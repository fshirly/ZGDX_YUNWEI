package com.fable.insightview.platform.listview.entity;

import java.util.Map;

/**
 * ListView请求参数表
 * 
 * @author zhouwei
 */
//@ApiModel(value = "ListView请求参数表")
public class ListviewRequestParamsBean {
	/**
	 * 名称
	 */
	//@ApiModelProperty(value = "listview名称（英文）")
	private String listviewName;

	/**
	 * 初始参数
	 */
	//@ApiModelProperty(value = "初始参数")
	private Map<String, String> initParams;

	public String getListviewName() {
		return listviewName;
	}

	public void setListviewName(String listviewName) {
		this.listviewName = listviewName;
	}

	public Map<String, String> getInitParams() {
		return initParams;
	}

	public void setInitParams(Map<String, String> initParams) {
		this.initParams = initParams;
	}

}