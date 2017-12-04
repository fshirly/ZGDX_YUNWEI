package com.fable.insightview.monitor.mobject.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.entity.MObjectRelationBean;
import com.fable.insightview.platform.page.Page;

public interface MobjectInfoMapper {
	List<MObjectDefBean> getMObjectForEdit();

	List<MObjectDefBean> getAllMObject();

	List<MObjectDefBean> getAllMObject2();

	List<MObjectDefBean> queryMObjectRelation();

	List<MObjectDefBean> queryMObjectRelation2();

	// 只显示第二级
	List<MObjectDefBean> queryMObjectBySecondLevel(
			@Param("ClassId") String ClassId);

	List<MObjectDefBean> getMessageByTreeName(Map<String, String> map);

	List<MObjectDefBean> getMessageByTreeName2(Map<String, String> map);

	List<MObjectDefBean> selectMobject(Page<MObjectDefBean> page);

	MObjectDefBean getMobjectById(int classId);

	List<MObjectDefBean> getMObject();

	List<MObjectDefBean> getTreeIdByTreeName(String classLable);

	int isLeaf(int classId);

	List<MObjectDefBean> getByParentID(@Param("sourceClassId") int sourceClassId);

	List<MObjectDefBean> getresNameByParentID(Map map);

	List<Integer> getChildIdByParentIDs(@Param("parentIDs") String parentIDs);

	List<MObjectDefBean> getByParentIDs(@Param("parentIDs") String parentIDs,
			@Param("sourceClassId") int sourceClassId);
	
	List<MObjectDefBean> getByParentClassIDs(@Param("parentIDs") String parentIDs,
			@Param("sourceClassId") int sourceClassId);

	MObjectDefBean getByClassName(@Param("className") String className);

	List<MObjectDefBean> getMObject2();

	List<MObjectDefBean> getMObjectByEdit();
	
	List<MObjectRelationBean> queryParentClassID(String parentClassId);
	
	List<MObjectDefBean> getByClassIDLst(List<Integer> classIDs);
	
	List<Integer> getParentIDByChildIds(@Param("classIDs") String classIDs);

	List<MObjectDefBean> getAllResMObject();
}
