package com.fable.insightview.platform.positionManagement.enitity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class PostUserManage {
	@NumberGenerator(name = "ID")
	private Integer ID;
	private Integer postID;
	private Integer userID;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer ID) {
		this.ID = ID;
	}
	public Integer getPostID() {
		return postID;
	}
	public void setPostID(Integer postID) {
		this.postID = postID;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
}
