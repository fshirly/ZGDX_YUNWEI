package com.fable.insightview.platform.notices.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class NotificationsBean {

	@NumberGenerator(name = "PfNotificationsKey", begin = 10000, allocationSize = 1)
	private int id;
	private String title;
	private String noticeContent;
	private int creator;
	private Date createTime;
	private String file;
	private int isNote = 1;

	private String userName;
	private String userIds;
	private List<NoticePersonsBean> persons;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public List<NoticePersonsBean> getPersons() {
		return persons;
	}

	public void setPersons(List<NoticePersonsBean> persons) {
		this.persons = persons;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIsNote() {
		return isNote;
	}

	public void setIsNote(int isNote) {
		this.isNote = isNote;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getFormatCreateTime() {
		if (null == this.createTime) {
			return null;
		} else {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.createTime);
		}
	}

	public String getNotifiStatus() {
		if (null == this.persons || this.persons.size() == 0) {
			return "未确定";
		}
		if (this.persons.size() == 1) {/* 仅仅为一个信息提醒接收者 */
			if (this.persons.get(0).getStatus() == 1) {
				return "未确定";
			} else {
				return "已确定";
			}
		}
		boolean all = false, part = false;
		for (NoticePersonsBean person : this.persons) {
			if (person.getStatus() == 1) {/* 1表示消息还没有得到确认 */
				part = true;
			} else {
				all = true;
			}
			if (all && part) {
				return "部分确定";
			}
		}
		return all ? "已确定" : "未确定";
	}
}
