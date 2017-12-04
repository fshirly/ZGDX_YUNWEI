package com.fable.insightview.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.mybatis.entity.PersonalInfo;
import com.fable.insightview.platform.mybatis.entity.STUserCertificate;
import com.fable.insightview.platform.mybatis.mapper.PersonalInfoMapper;
import com.fable.insightview.platform.service.PersonalInfoService;

@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {
	
	@Autowired
	PersonalInfoMapper personalInfoMapper;
	
	public void updatePersonalInfo(PersonalInfo personalInfo) {
		personalInfoMapper.updatePersonalInfo(personalInfo);
	}

	public STUserCertificate getUserCertificate(Integer certificateId) {
		return personalInfoMapper.getUserCertificate(certificateId);
	}
	
	public List<STUserCertificate> queryUserCertificate(Integer userId) {
		return personalInfoMapper.queryUserCertificate(userId);
	}

	public void insertUserCertificate(STUserCertificate userCertificate) {
		personalInfoMapper.insertUserCertificate(userCertificate);
	}

	public void updateUserCertificate(STUserCertificate userCertificate) {
		personalInfoMapper.updateUserCertificate(userCertificate);
	}

	public void deleteUserCertificate(Integer certificateId) {
		personalInfoMapper.deleteUserCertificate(certificateId);
	}
}
