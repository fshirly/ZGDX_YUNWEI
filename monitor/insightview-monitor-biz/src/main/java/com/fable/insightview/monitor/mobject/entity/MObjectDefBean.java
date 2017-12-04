package com.fable.insightview.monitor.mobject.entity;

import org.apache.ibatis.type.Alias;


@Alias("mobjectBean")
public class MObjectDefBean {
	private Integer classId;
	private String className;
	private String classLable;
	private String descr;
	private String attributeTableName;
	private Integer parentClassId;
	private Integer relationType;
	private Integer showOrder;

	private Integer newParentID;
	private Integer relationID;
	private String relationPath;

	private Integer childCount;
	public MObjectDefBean() {
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassLable() {
		return classLable;
	}

	public void setClassLable(String classLable) {
		this.classLable = classLable;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getAttributeTableName() {
		return attributeTableName;
	}

	public void setAttributeTableName(String attributeTableName) {
		this.attributeTableName = attributeTableName;
	}

	public Integer getParentClassId() {
		return parentClassId;
	}

	public void setParentClassId(Integer parentClassId) {
		this.parentClassId = parentClassId;
	}

	public Integer getRelationType() {
		return relationType;
	}

	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}

	public Integer getNewParentID() {
		return newParentID;
	}

	public void setNewParentID(Integer newParentID) {
		this.newParentID = newParentID;
	}

	public Integer getRelationID() {
		return relationID;
	}

	public void setRelationID(Integer relationID) {
		this.relationID = relationID;
	}

	public String getRelationPath() {
		return relationPath;
	}

	public void setRelationPath(String relationPath) {
		this.relationPath = relationPath;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

}
