package com.fable.insightview.monitor.mobject.entity;

public class MObjectRelationBean {

	private int relationID;
	
	private int classID;
	
	private int  parentClassID;
	
	private int relationType;
	
	private String relationPath;

	public int getRelationID() {
		return relationID;
	}

	public void setRelationID(int relationID) {
		this.relationID = relationID;
	}

	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
		this.classID = classID;
	}

	public int getParentClassID() {
		return parentClassID;
	}

	public void setParentClassID(int parentClassID) {
		this.parentClassID = parentClassID;
	}

	public int getRelationType() {
		return relationType;
	}

	public void setRelationType(int relationType) {
		this.relationType = relationType;
	}

	public String getRelationPath() {
		return relationPath;
	}

	public void setRelationPath(String relationPath) {
		this.relationPath = relationPath;
	}
	
	
}
