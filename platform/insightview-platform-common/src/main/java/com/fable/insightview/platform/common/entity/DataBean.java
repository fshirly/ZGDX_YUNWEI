/**
 * 
 */
package com.fable.insightview.platform.common.entity;

import java.util.List;

/**
 * @author zhouwei
 * 数据封装对象
 * @param <T> BO对象
 */
//@ApiModel(value = "数据封装对象")
public class DataBean<T> {
	
	//@ApiModelProperty(value = "数据封装对象内容")
	private List<T> data;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
