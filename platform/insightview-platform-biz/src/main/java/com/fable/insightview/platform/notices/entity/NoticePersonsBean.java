package com.fable.insightview.platform.notices.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class NoticePersonsBean {

	@NumberGenerator(name = "PfNoticePersonsKey", begin = 10000, allocationSize = 1)
	private int id;
	private int noticeId;
	private int userId;
	private int status = 1;
	private Date confirmTime;
	private String confirmDes;
	private String file;
	private int isNote = 1;

	private String userName;
	private String filePath;
	private NotificationsBean notifications;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getConfirmDes() {
		return confirmDes;
	}

	public void setConfirmDes(String confirmDes) {
		this.confirmDes = confirmDes;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public NotificationsBean getNotifications() {
		return notifications;
	}

	public void setNotifications(NotificationsBean notifications) {
		this.notifications = notifications;
	}

	public int getIsNote() {
		return isNote;
	}

	public void setIsNote(int isNote) {
		this.isNote = isNote;
	}

	public String getFormateConfigeTime() {
		if (null == this.confirmTime) {
			return "";
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.confirmTime);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
