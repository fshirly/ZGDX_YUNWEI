package com.fable.insightview.platform.mybatis.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * @author Li jiuwei
 * @date   2015年4月1日 上午10:37:12
 */
@Alias("STPostUserManage")
public class STPostUserManage {
	
	@NumberGenerator(name = "STPostUserManagePK")
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
