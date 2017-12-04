package com.fable.insightview.platform.mybatis.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * @author Li jiuwei
 * @date 2015年4月2日 下午4:44:46
 */
@Alias("UserProjectExp")
public class UserProjectExp {
	@NumberGenerator(name = "UserProjectExpPK")
	private Integer projectExpId;
	private Integer resumeID;
	private String startTime;
	private String endTime;
	private String projectName;
	private String reference;

	public Integer getProjectExpId() {
		return projectExpId;
	}

	public void setProjectExpId(Integer projectExpId) {
		this.projectExpId = projectExpId;
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
