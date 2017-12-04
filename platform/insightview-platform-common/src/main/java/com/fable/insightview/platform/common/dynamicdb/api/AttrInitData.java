package com.fable.insightview.platform.common.dynamicdb.api;

/**
 * 属性初始化数据
 * @author 郑自辉
 *
 */
public class AttrInitData {

	/**
	 * 值
	 */
	private int value;
	
	/**
	 * 显示文本
	 */
	private String labelText;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getLabelText() {
		return labelText;
	}

	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}
}
