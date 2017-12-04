/**
 * 
 */
package com.fable.insightview.platform.listview.entity;

import java.util.List;

/**
 * @author Administrator
 * 
 */
public class ListviewDataBean {

	private ListviewBean basic;

	private List<ListviewConditionBean> condition;

	private List<ListviewFieldLabelBean> cols;

	public ListviewBean getBasic() {
		return basic;
	}

	public void setBasic(ListviewBean basic) {
		this.basic = basic;
	}

	public List<ListviewConditionBean> getCondition() {
		return condition;
	}

	public void setCondition(List<ListviewConditionBean> condition) {
		this.condition = condition;
	}

	public List<ListviewFieldLabelBean> getCols() {
		return cols;
	}

	public void setCols(List<ListviewFieldLabelBean> cols) {
		this.cols = cols;
	}

}
