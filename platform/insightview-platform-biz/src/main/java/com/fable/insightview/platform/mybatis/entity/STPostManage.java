package com.fable.insightview.platform.mybatis.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;


@Alias("STPostManage")
public class STPostManage {

	@NumberGenerator(name = "STPostManagePK")
	private Integer postID;
	
	private String postName;
	
	private Integer organizationID;
	
	private Integer isImportant;
	
	private String postDivision;

	public Integer getPostID() {
		return postID;
	}

	public void setPostID(Integer postID) {
		this.postID = postID;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public Integer getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Integer organizationID) {
		this.organizationID = organizationID;
	}

	public Integer getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(Integer isImportant) {
		this.isImportant = isImportant;
	}

	public String getPostDivision() {
		return postDivision;
	}

	public void setPostDivision(String postDivision) {
		this.postDivision = postDivision;
	}

}
