package com.fable.insightview.platform.entity;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
/**
  * 
  * @TABLE_NAME: SysUserInGroups
  * @Description: 
  * @author: wul
  * @Create at: Tue Dec 03 14:09:32 CST 2013
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SysUserInGroups")
public class SysUserInGroupsBean extends com.fable.insightview.platform.itsm.core.entity.Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sysuseringroups_gen")
	@TableGenerator(initialValue=10001, name = "sysuseringroups_gen", table = "SysKeyGenerator", pkColumnName = "GenName", valueColumnName = "GenValue", pkColumnValue = "SysUserInGroupsPK", allocationSize = 1)
	@Column(name = "ID")
	private int id;

	@Column(name = "UserId")
	private int userId;

	@Column(name = "GroupId")
	private int groupId;

	@Transient
	private String userIds;

	
	public String getUserIds() {
		return userIds;
	}


	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}


	public SysUserInGroupsBean (){
		super();
	}
	
	
	public SysUserInGroupsBean(int userId, int groupId) {
		super();
		this.userId = userId;
		this.groupId = groupId;
	}


	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
}

