package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.mybatis.entity.PersonalInfo;
import com.fable.insightview.platform.mybatis.entity.STUserCertificate;

public interface PersonalInfoService {
	
	void updatePersonalInfo(PersonalInfo personalInfo);
	
	STUserCertificate getUserCertificate(Integer certificateId);

	List<STUserCertificate> queryUserCertificate(Integer userId);

	void insertUserCertificate(STUserCertificate userCertificate);

	void updateUserCertificate(STUserCertificate userCertificate);

	void deleteUserCertificate(Integer certificateId);

}
