package com.fable.insightview.platform.mybatis.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * @author Li jiuwei
 * @date 2015年4月2日 下午4:44:37
 */
@Alias("UserWorkingExp")
public class UserWorkingExp {
	@NumberGenerator(name = "UserWorkingExpPK")
	private Integer workingExpId;
	private Integer resumeID;
	private String startTime;
	private String endTime;
	private String unitAndPost;
	private String reference;

	public Integer getWorkingExpId() {
		return workingExpId;
	}

	public void setWorkingExpId(Integer workingExpId) {
		this.workingExpId = workingExpId;
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

	public String getUnitAndPost() {
		return unitAndPost;
	}

	public void setUnitAndPost(String unitAndPost) {
		this.unitAndPost = unitAndPost;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
