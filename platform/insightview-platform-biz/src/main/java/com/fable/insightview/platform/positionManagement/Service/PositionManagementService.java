package com.fable.insightview.platform.positionManagement.Service;

import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.positionManagement.enitity.PositionManagement;
import com.fable.insightview.platform.positionManagement.enitity.PostUserManage;

public interface PositionManagementService {

	List<PositionManagement> getPositionManagementList(Page<PositionManagement> page);
	List<PositionManagement> queryPositionManagementList(Page<PositionManagement> page);
	public Integer insertPositionPersonal(PositionManagement vo);
	List<PositionManagement> getPositionMessage(Page<PositionManagement> Page);
	public boolean updatePositionPersonal(PositionManagement positionManagement);
	public boolean deletePositionPersonal(PositionManagement positionManagement);
	List<PositionManagement> initPositionPersonalTabList(Page<PositionManagement> page);
	public boolean deletePositionPersonalMessage(PositionManagement positionManagement);
	List<PositionManagement> initPositionMessageAddPersonalTable(Page<PositionManagement> page);
	public boolean insertPersonalToPosition(PostUserManage postUserManage);
	public boolean delIsertPostUserMsg(PostUserManage postUserManage);
	List<PositionManagement> getPostPersonalNumList(PositionManagement vo);
	public String getOrganizationName(Integer organizationID);
	Integer getPersonalNum(Integer postID);

}
