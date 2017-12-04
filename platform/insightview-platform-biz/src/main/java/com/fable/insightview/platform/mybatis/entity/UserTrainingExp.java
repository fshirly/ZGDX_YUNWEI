package com.fable.insightview.platform.mybatis.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * @author Li jiuwei
 * @date 2015年4月2日 下午4:44:29
 */
@Alias("UserTrainingExp")
public class UserTrainingExp {
	@NumberGenerator(name = "UserTrainingExpPK")
	private Integer trainingExpId;
	private Integer resumeID;
	private String startTime;
	private String endTime;
	private String followUpWork;
	private String reference;
	public Integer getTrainingExpId() {
		return trainingExpId;
	}
	public void setTrainingExpId(Integer trainingExpId) {
		this.trainingExpId = trainingExpId;
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
	public String getFollowUpWork() {
		return followUpWork;
	}
	public void setFollowUpWork(String followUpWork) {
		this.followUpWork = followUpWork;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
}
