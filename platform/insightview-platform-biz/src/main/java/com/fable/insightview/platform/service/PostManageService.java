package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.mybatis.entity.STPostManage;

public interface PostManageService {
	
	List<STPostManage> queryAllPost(Integer deptId);
	
	List<STPostManage> queryAvailPost(Integer userId);
	
	List<STPostManage> queryAddedPost(Integer userId);
	
	void deleteAllPost(Integer userId);
	
	void insertPostUserManage(Integer userId, Integer postId);

}
