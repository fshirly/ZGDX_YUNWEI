package com.fable.insightview.platform.formdesign.vo;

/**
 * 流程表单列表的VO
 * 
 * @author Maowei
 * 
 */
public class ProcessFormLstVO {
	
	private Integer id;
	private String formName;
	private Integer layout;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public Integer getLayout() {
		return layout;
	}

	public void setLayout(Integer layout) {
		this.layout = layout;
	}

}
