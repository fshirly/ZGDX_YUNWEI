package com.fable.insightview.platform.mybatis.mapper;

import java.util.List;

import com.fable.insightview.platform.mybatis.entity.STUserResume;
import com.fable.insightview.platform.mybatis.entity.UserLearningExp;
import com.fable.insightview.platform.mybatis.entity.UserProjectExp;
import com.fable.insightview.platform.mybatis.entity.UserTrainingExp;
import com.fable.insightview.platform.mybatis.entity.UserWorkingExp;

public interface STUserResumeMapper {

	STUserResume getResumeByUserId(Integer userId);
	
	STUserResume getUserInfoByUserId(Integer userId);
	
	void insertEmtpyResume(STUserResume resume);
	
	void updateResume(STUserResume resume);
	
	void updateUserInfo(STUserResume resume);
	
	
	UserLearningExp getUserLearningExp(Integer learningExpId);
	
	List<UserLearningExp> queryUserLearningExp(Integer resumeID);
	
	void insertUserLearningExp(UserLearningExp userLearningExp);
	
	void updateUserLearningExp(UserLearningExp userLearningExp);
	
	void deleteUserLearningExp(Integer learningExpId);
	
	
	UserTrainingExp getUserTrainingExp(Integer trainingExpId);

	List<UserTrainingExp> queryUserTrainingExp(Integer resumeID);

	void insertUserTrainingExp(UserTrainingExp userTrainingExp);

	void updateUserTrainingExp(UserTrainingExp userTrainingExp);

	void deleteUserTrainingExp(Integer trainingExpId);

	
	UserWorkingExp getUserWorkingExp(Integer workingExpId);

	List<UserWorkingExp> queryUserWorkingExp(Integer resumeID);

	void insertUserWorkingExp(UserWorkingExp userWorkingExp);

	void updateUserWorkingExp(UserWorkingExp userWorkingExp);

	void deleteUserWorkingExp(Integer workingExpId);

	
	UserProjectExp getUserProjectExp(Integer projectExpId);

	List<UserProjectExp> queryUserProjectExp(Integer resumeID);

	void insertUserProjectExp(UserProjectExp userProjectExp);

	void updateUserProjectExp(UserProjectExp userProjectExp);

	void deleteUserProjectExp(Integer projectExpId);
	
}