package com.fable.insightview.platform.common.bpm.activiti.model;

/**
 * 人工任务处理权限
 * @author xue.antai
 *
 */
public class IdentityLinkRS {
	public static final String ID_LINK_TYPE_START = "starter";
	public static final String ID_LINK_TYPE_PARTICIPANT = "participant";
	public static final String ID_LINK_TYPE_CANDIDATE = "candidate";
	private String userId;
	private String groupId;
	private String type;
	private String url;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
