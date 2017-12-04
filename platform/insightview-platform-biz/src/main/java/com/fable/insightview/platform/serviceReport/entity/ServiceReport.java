package com.fable.insightview.platform.serviceReport.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

@Alias("serviceReport")
public class ServiceReport {

	@NumberGenerator(name = "platformServiceReportPK")
	private Integer serviceReportID;
	
	private String name;
	
	private Date reportTime;
	
	private Integer reporter;
	
	private String summary;
	
	private String reportFile;

	private Date startTime;
	
	private Date endTime;
	
	private String reporterName;
	
	private String strReportTime;
	
	public Integer getServiceReportID() {
		return serviceReportID;
	}

	public void setServiceReportID(Integer serviceReportID) {
		this.serviceReportID = serviceReportID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public Integer getReporter() {
		return reporter;
	}

	public void setReporter(Integer reporter) {
		this.reporter = reporter;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getReportFile() {
		return reportFile;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public String getStrReportTime() {
		return strReportTime;
	}

	public void setStrReportTime(String strReportTime) {
		this.strReportTime = strReportTime;
	}
	
}
