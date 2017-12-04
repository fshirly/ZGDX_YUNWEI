package com.fable.insightview.platform.mybatis.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * @author Li jiuwei
 * @date 2015年4月2日 下午4:44:54
 */
@Alias("STUserCertificate")
public class STUserCertificate {
	@NumberGenerator(name = "STUserCertificatePK")
	private Integer certificateId;
	private Integer userId;
	private String certificateNo;
	private String certificateName;
	private Date dateOfIssue;
	private Date effectiveTime;
	private String accessoryUrl;
	private String description;

	public Integer getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(Integer certificateId) {
		this.certificateId = certificateId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public Date getDateOfIssue() {
		return dateOfIssue == null ? null : new YYYYMMDDDate(dateOfIssue);
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public Date getEffectiveTime() {
		return effectiveTime == null ? null : new YYYYMMDDDate(effectiveTime);
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getAccessoryUrl() {
		return accessoryUrl;
	}

	public void setAccessoryUrl(String accessoryUrl) {
		this.accessoryUrl = accessoryUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
