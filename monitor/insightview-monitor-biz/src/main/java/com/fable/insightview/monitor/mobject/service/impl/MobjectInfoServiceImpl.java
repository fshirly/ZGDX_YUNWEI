package com.fable.insightview.monitor.mobject.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.mobject.service.IMobjectInfoService;
import com.fable.insightview.platform.page.Page;

@Service
public class MobjectInfoServiceImpl implements IMobjectInfoService {
	
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	
	@Override
	public List<MObjectDefBean> getAllMObject() {
		return mobjectInfoMapper.getAllMObject();
	}

	@Override
	public List<MObjectDefBean> getAllMObject2() {
		return mobjectInfoMapper.getAllMObject2();
	}

	@Override
	public MObjectDefBean getByClassName(String className) {
		return mobjectInfoMapper.getByClassName(className);
	}

	@Override
	public List<MObjectDefBean> getByParentID(int sourceClassId) {
		return mobjectInfoMapper.getByParentID(sourceClassId);
	}

	@Override
	public List<MObjectDefBean> getByParentIDs(String parentIDs,
			int sourceClassId) {
		return mobjectInfoMapper.getByParentIDs(parentIDs, sourceClassId);
	}

	@Override
	public List<Integer> getChildIdByParentIDs(String parentIDs) {
		return mobjectInfoMapper.getChildIdByParentIDs(parentIDs);
	}

	@Override
	public List<MObjectDefBean> getMObject() {
		return mobjectInfoMapper.getMObject();
	}

	@Override
	public List<MObjectDefBean> getMObject2() {
		return mobjectInfoMapper.getMObject2();
	}

	@Override
	public List<MObjectDefBean> getMObjectForEdit() {
		return mobjectInfoMapper.getMObjectForEdit();
	}

	@Override
	public List<MObjectDefBean> getMessageByTreeName(Map<String, String> map) {
		return mobjectInfoMapper.getMessageByTreeName(map);
	}

	@Override
	public List<MObjectDefBean> getMessageByTreeName2(Map<String, String> map) {
		return mobjectInfoMapper.getMessageByTreeName2(map);
	}

	@Override
	public MObjectDefBean getMobjectById(int classId) {
		return mobjectInfoMapper.getMobjectById(classId);
	}

	@Override
	public List<MObjectDefBean> getTreeIdByTreeName(String classLable) {
		return mobjectInfoMapper.getTreeIdByTreeName(classLable);
	}

	@Override
	public List<MObjectDefBean> getresNameByParentID(Map map) {
		return mobjectInfoMapper.getresNameByParentID(map);
	}

	@Override
	public int isLeaf(int classId) {
		return mobjectInfoMapper.isLeaf(classId);
	}

	@Override
	public List<MObjectDefBean> queryMObjectBySecondLevel(String ClassId) {
		return mobjectInfoMapper.queryMObjectBySecondLevel(ClassId);
	}

	@Override
	public List<MObjectDefBean> queryMObjectRelation() {
		return mobjectInfoMapper.queryMObjectRelation();
	}

	@Override
	public List<MObjectDefBean> queryMObjectRelation2() {
		return mobjectInfoMapper.queryMObjectRelation2();
	}

	@Override
	public List<MObjectDefBean> selectMobject(Page<MObjectDefBean> page) {
		return mobjectInfoMapper.selectMobject(page);
	}

}
