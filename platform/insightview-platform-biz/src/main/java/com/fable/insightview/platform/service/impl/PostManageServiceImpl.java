package com.fable.insightview.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.mybatis.entity.STPostManage;
import com.fable.insightview.platform.mybatis.entity.STPostUserManage;
import com.fable.insightview.platform.mybatis.mapper.STPostManageMapper;
import com.fable.insightview.platform.service.PostManageService;

/**
 * @author Li jiuwei
 * @date   2015年4月1日 下午3:09:01
 */
@Service
public class PostManageServiceImpl implements PostManageService {

	@Autowired
	STPostManageMapper postManageMapper;
	
	/* (non-Javadoc)
	 * @see com.fable.insightview.platform.service.PostManageService#queryAvailPost()
	 */
	@Override
	public List<STPostManage> queryAllPost(Integer deptId) {
		return postManageMapper.queryAllPost(deptId);
	}
	
	/* (non-Javadoc)
	 * @see com.fable.insightview.platform.service.PostManageService#queryAvailPost()
	 */
	@Override
	public List<STPostManage> queryAvailPost(Integer userId) {
		return postManageMapper.queryAvailPost(userId);
	}

	/* (non-Javadoc)
	 * @see com.fable.insightview.platform.service.PostManageService#queryAddedPost()
	 */
	@Override
	public List<STPostManage> queryAddedPost(Integer userId) {
		return postManageMapper.queryAddedPost(userId);
	}

	/* (non-Javadoc)
	 * @see com.fable.insightview.platform.service.PostManageService#deleteAllPost(java.lang.Integer)
	 */
	@Override
	public void deleteAllPost(Integer userId) {
		postManageMapper.deleteAllPost(userId);
	}

	/* (non-Javadoc)
	 * @see com.fable.insightview.platform.service.PostManageService#insertPost(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void insertPostUserManage(Integer userId, Integer postId) {
		STPostUserManage obj = new STPostUserManage();
		obj.setUserID(userId);
		obj.setPostID(postId);
		postManageMapper.insertPostUserManage(obj);
	}

}
