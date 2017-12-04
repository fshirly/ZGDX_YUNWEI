/**
 * 
 */
package com.fable.insightview.platform.listview.entity;

import com.fable.insightview.platform.dataobject.entity.DataObjectBean;

/**
 * @author zhouwei 用于listview中导入导出用
 *
 */
//@ApiModel(value = "listview组合dataObject")
public class ListviewDataObjectBean {

	/**
	 * listview对象
	 */
	//@ApiModelProperty(value = "listview对象")
	ListviewDataBean listview;
	
	/**
	 * 数据对象
	 */
	//@ApiModelProperty(value = "数据对象")
	DataObjectBean dataObject;

	public ListviewDataBean getListview() {
		return listview;
	}

	public void setListview(ListviewDataBean listview) {
		this.listview = listview;
	}

	public DataObjectBean getDataObject() {
		return dataObject;
	}

	public void setDataObject(DataObjectBean dataObject) {
		this.dataObject = dataObject;
	}

}
