package com.fable.insightview.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.mybatis.entity.STUserResume;
import com.fable.insightview.platform.mybatis.entity.UserLearningExp;
import com.fable.insightview.platform.mybatis.entity.UserProjectExp;
import com.fable.insightview.platform.mybatis.entity.UserTrainingExp;
import com.fable.insightview.platform.mybatis.entity.UserWorkingExp;
import com.fable.insightview.platform.mybatis.mapper.STUserResumeMapper;
import com.fable.insightview.platform.service.UserResumeService;

/**
 * @author Li jiuwei
 * @date   2015年4月1日 下午3:09:01
 */
@Service
public class UserResumeServiceImpl implements UserResumeService {

	@Autowired
	STUserResumeMapper userResumeMapper;
	
	public STUserResume getResumeByUserId(Integer userId) {
		return userResumeMapper.getResumeByUserId(userId);
	}

	public STUserResume getUserInfoByUserId(Integer userId) {
		return userResumeMapper.getUserInfoByUserId(userId);
	}
	
	public void insertEmtpyResume(STUserResume resume) {
		userResumeMapper.insertEmtpyResume(resume);
	}

	public void updateResume(STUserResume resume) {
		userResumeMapper.updateResume(resume);
	}

	public void updateUserInfo(STUserResume resume) {
		userResumeMapper.updateUserInfo(resume);
	}
	
	
	public UserLearningExp getUserLearningExp(Integer learningExpId) {
		return userResumeMapper.getUserLearningExp(learningExpId);
	}
	
	public List<UserLearningExp> queryUserLearningExp(Integer resumeID) {
		return userResumeMapper.queryUserLearningExp(resumeID);
	}
	
	public void insertUserLearningExp(UserLearningExp userLearningExp) {
		userResumeMapper.insertUserLearningExp(userLearningExp);
	}
	
	public void updateUserLearningExp(UserLearningExp userLearningExp) {
		userResumeMapper.updateUserLearningExp(userLearningExp);
	}
	
	public void deleteUserLearningExp(Integer learningExpId) {
		userResumeMapper.deleteUserLearningExp(learningExpId);
	}
	
	
	public UserTrainingExp getUserTrainingExp(Integer trainingExpId) {
		return userResumeMapper.getUserTrainingExp(trainingExpId);
	}

	public List<UserTrainingExp> queryUserTrainingExp(Integer resumeID) {
		return userResumeMapper.queryUserTrainingExp(resumeID);
	}

	public void insertUserTrainingExp(UserTrainingExp userTrainingExp) {
		userResumeMapper.insertUserTrainingExp(userTrainingExp);
	}

	public void updateUserTrainingExp(UserTrainingExp userTrainingExp) {
		userResumeMapper.updateUserTrainingExp(userTrainingExp);
	}

	public void deleteUserTrainingExp(Integer trainingExpId) {
		userResumeMapper.deleteUserTrainingExp(trainingExpId);
	}

	
	public UserWorkingExp getUserWorkingExp(Integer workingExpId) {
		return userResumeMapper.getUserWorkingExp(workingExpId);
	}

	public List<UserWorkingExp> queryUserWorkingExp(Integer resumeID) {
		return userResumeMapper.queryUserWorkingExp(resumeID);
	}

	public void insertUserWorkingExp(UserWorkingExp userWorkingExp) {
		userResumeMapper.insertUserWorkingExp(userWorkingExp);
	}

	public void updateUserWorkingExp(UserWorkingExp userWorkingExp) {
		userResumeMapper.updateUserWorkingExp(userWorkingExp);
	}

	public void deleteUserWorkingExp(Integer workingExpId) {
		userResumeMapper.deleteUserWorkingExp(workingExpId);
	}
	
	
	public UserProjectExp getUserProjectExp(Integer projectExpId) {
		return userResumeMapper.getUserProjectExp(projectExpId);
	}

	public List<UserProjectExp> queryUserProjectExp(Integer resumeID) {
		return userResumeMapper.queryUserProjectExp(resumeID);
	}

	public void insertUserProjectExp(UserProjectExp userProjectExp) {
		userResumeMapper.insertUserProjectExp(userProjectExp);
	}

	public void updateUserProjectExp(UserProjectExp userProjectExp) {
		userResumeMapper.updateUserProjectExp(userProjectExp);
	}

	public void deleteUserProjectExp(Integer projectExpId) {
		userResumeMapper.deleteUserProjectExp(projectExpId);
	}

}
