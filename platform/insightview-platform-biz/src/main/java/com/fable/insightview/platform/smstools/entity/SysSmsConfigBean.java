package com.fable.insightview.platform.smstools.entity;

/**
 * 短信配置
 *
 */
public class SysSmsConfigBean {
	private Integer id;
	private String paramKey;
	private String paramValue;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParamKey() {
		return paramKey;
	}
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
}
