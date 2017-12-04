package com.fable.insightview.platform.formdesign.vo;

import java.util.List;
import java.util.Map;

/**
 * 流程表单的VO类
 * 
 * @author Maowei
 * 
 */
public class ProcessFormVO {

	private Integer id;
	private String formName;
	private Integer layout;
	private String businessNodeId;
	private String businessId;
	private String businessType;
	private List<Map<String, String>> newAttrs;
	private List<Map<String, String>> updateAttrs;
	private List<Map<String, String>> deleteAttrs;
	private List<Map<String, String>> buttons;
	private Map<String, String> positionAttrs;

	public Map<String, String> getPositionAttrs() {
		return positionAttrs;
	}

	public void setPositionAttrs(Map<String, String> positionAttrs) {
		this.positionAttrs = positionAttrs;
	}

	public List<Map<String, String>> getButtons() {
		return buttons;
	}

	public void setButtons(List<Map<String, String>> buttons) {
		this.buttons = buttons;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

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

	public String getBusinessNodeId() {
		return businessNodeId;
	}

	public void setBusinessNodeId(String businessNodeId) {
		this.businessNodeId = businessNodeId;
	}

	public List<Map<String, String>> getNewAttrs() {
		return newAttrs;
	}

	public void setNewAttrs(List<Map<String, String>> newAttrs) {
		this.newAttrs = newAttrs;
	}

	public List<Map<String, String>> getUpdateAttrs() {
		return updateAttrs;
	}

	public void setUpdateAttrs(List<Map<String, String>> updateAttrs) {
		this.updateAttrs = updateAttrs;
	}

	public List<Map<String, String>> getDeleteAttrs() {
		return deleteAttrs;
	}

	public void setDeleteAttrs(List<Map<String, String>> deleteAttrs) {
		this.deleteAttrs = deleteAttrs;
	}

}
