/**
 * 
 */
package com.fable.insightview.platform.listview.entity;


/**
 * @author Administrator
 * 
 */
public class ListviewDataVoBean {

	private ListviewBean basic;

	private ListviewConditionBean[] condition;

	private ListviewFieldLabelBean[] cols;

	public ListviewBean getBasic() {
		return basic;
	}

	public void setBasic(ListviewBean basic) {
		this.basic = basic;
	}

	public ListviewConditionBean[] getCondition() {
		return condition;
	}

	public void setCondition(ListviewConditionBean[] condition) {
		this.condition = condition;
	}

	public ListviewFieldLabelBean[] getCols() {
		return cols;
	}

	public void setCols(ListviewFieldLabelBean[] cols) {
		this.cols = cols;
	}

}
