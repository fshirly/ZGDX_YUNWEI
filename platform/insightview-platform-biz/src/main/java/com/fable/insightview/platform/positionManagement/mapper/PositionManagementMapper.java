package com.fable.insightview.platform.positionManagement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.positionManagement.enitity.PositionManagement;
import com.fable.insightview.platform.positionManagement.enitity.PostUserManage;

public interface PositionManagementMapper {
	public List<PositionManagement> getPositionManagementList(Page<PositionManagement> page);
	public List<PositionManagement> queryPositionManagementList(Page<PositionManagement> page);
	public Integer insertPositionPersonal(PositionManagement vo);
	public List<PositionManagement> getPositionMessage(Page<PositionManagement> page);
	public boolean updatePositionPersonal(PositionManagement positionManagement);
	public boolean deletePositionPersonal(PositionManagement positionManagement);
	public List<PositionManagement> initPositionPersonalTabList(Page<PositionManagement> page);
	public boolean deletePositionPersonalMessage(PositionManagement positionManagement);
	public List<PositionManagement> initPositionMessageAddPersonalTable(Page<PositionManagement> page);
	public boolean insertPersonalToPosition(PostUserManage postUserManage);
	public boolean delIsertPostUserMsg(PostUserManage postUserManage);
	public List<PositionManagement> getPostPersonalNumList(PositionManagement vo);
	public String getOrganizationName(@Param("organizationID")Integer organizationID);
	public Integer getPersonalNum(Integer postID);
	}