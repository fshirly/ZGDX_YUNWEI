package com.fable.insightview.platform.mybatis.mapper;

import java.util.List;

import com.fable.insightview.platform.mybatis.entity.STPostManage;
import com.fable.insightview.platform.mybatis.entity.STPostUserManage;

public interface STPostManageMapper {

	List<STPostManage> queryAllPost(Integer deptId);
	
	List<STPostManage> queryAvailPost(Integer userId);
	
	List<STPostManage> queryAddedPost(Integer userId);
	
	void deleteAllPost(Integer userId);
	
	void insertPostUserManage(STPostUserManage obj);
	
}