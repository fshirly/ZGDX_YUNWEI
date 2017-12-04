package com.fable.insightview.platform.positionManagement.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.positionManagement.Service.PositionManagementService;
import com.fable.insightview.platform.positionManagement.enitity.PositionManagement;
import com.fable.insightview.platform.positionManagement.enitity.PostUserManage;
import com.fable.insightview.platform.positionManagement.mapper.PositionManagementMapper;
@Service
public class PositionManagementServiceImpl implements PositionManagementService{
	@Autowired
	private PositionManagementMapper positionManagementMapper;
	
	@Override
	public List<PositionManagement> getPositionManagementList(Page<PositionManagement> page) {
		return positionManagementMapper.getPositionManagementList(page);
	}
	
	@Override
	public List<PositionManagement> queryPositionManagementList(Page<PositionManagement> page){
		return positionManagementMapper.queryPositionManagementList(page);
		
	}
	
	@Override
	public Integer insertPositionPersonal(PositionManagement vo){
		return positionManagementMapper.insertPositionPersonal(vo);
	}
	
	@Override
	public List<PositionManagement> getPositionMessage(Page<PositionManagement> Page){
		return positionManagementMapper.getPositionMessage(Page);
	}
	
	@Override
	public boolean updatePositionPersonal(PositionManagement positionManagement){
		return positionManagementMapper.updatePositionPersonal(positionManagement);
	}
	
	@Override
	public boolean deletePositionPersonal(PositionManagement positionManagement){
		return positionManagementMapper.deletePositionPersonal(positionManagement);
	}
	
	@Override
	public List<PositionManagement> initPositionPersonalTabList(Page<PositionManagement> page) {
		return positionManagementMapper.initPositionPersonalTabList(page);
	}
	
	@Override
	public boolean deletePositionPersonalMessage(PositionManagement positionManagement){
		return positionManagementMapper.deletePositionPersonalMessage(positionManagement);
	}
	
	@Override
	public List<PositionManagement> initPositionMessageAddPersonalTable(Page<PositionManagement> page) {
		return positionManagementMapper.initPositionMessageAddPersonalTable(page);
	}
	
	@Override
	public boolean insertPersonalToPosition(PostUserManage postUserManage){
		return positionManagementMapper.insertPersonalToPosition(postUserManage);
	}
	
	@Override
	public boolean delIsertPostUserMsg(PostUserManage postUserManage){
		return positionManagementMapper.delIsertPostUserMsg(postUserManage);
	}
	
	@Override
	public List<PositionManagement> getPostPersonalNumList(PositionManagement vo) {
		return positionManagementMapper.getPostPersonalNumList(vo);
	}
	
	@Override
	public String getOrganizationName(Integer organizationID){
		return positionManagementMapper.getOrganizationName(organizationID);
		
	}
	
	@Override
	public Integer getPersonalNum(Integer postID){
		return positionManagementMapper.getPersonalNum(postID);
		
	}
}
