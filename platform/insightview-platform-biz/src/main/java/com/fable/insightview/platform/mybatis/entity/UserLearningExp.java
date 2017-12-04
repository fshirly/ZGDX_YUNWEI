package com.fable.insightview.platform.mybatis.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * @author Li jiuwei
 * @date 2015年4月2日 下午4:44:19
 */
@Alias("UserLearningExp")
public class UserLearningExp {
	@NumberGenerator(name = "UserLearningExpPK")
	private Integer learningExpId;
	private Integer resumeID;
	private String startTime;
	private String endTime;
	private String eduAndMajor;
	private String graduationInfo;
	private String reference;

	public Integer getLearningExpId() {
		return learningExpId;
	}

	public void setLearningExpId(Integer learningExpId) {
		this.learningExpId = learningExpId;
	}

	public Integer getResumeID() {
		return resumeID;
	}

	public void setResumeID(Integer resumeID) {
		this.resumeID = resumeID;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEduAndMajor() {
		return eduAndMajor;
	}

	public void setEduAndMajor(String eduAndMajor) {
		this.eduAndMajor = eduAndMajor;
	}

	public String getGraduationInfo() {
		return graduationInfo;
	}

	public void setGraduationInfo(String graduationInfo) {
		this.graduationInfo = graduationInfo;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
