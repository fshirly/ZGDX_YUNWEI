package com.fable.insightview.monitor.middleware.websphere.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;


@Alias("webMoudleInfosBean")
public class WebMoudleInfosBean {
	private Integer moId;
	private String warName;
	private String servletName;
	private String status;
	private long totalRequests;
	private long concurrentRequests;
	private long numErrors;
	private long responseTime;
	private String uri;
	private Date collectTime;
	private String formatTime;
	public WebMoudleInfosBean() {
	}
	public Integer getMoId() {
		return moId;
	}
	public void setMoId(Integer moId) {
		this.moId = moId;
	}
	public String getWarName() {
		return warName;
	}
	public void setWarName(String warName) {
		this.warName = warName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTotalRequests() {
		return totalRequests;
	}
	public void setTotalRequests(long totalRequests) {
		this.totalRequests = totalRequests;
	}
	public long getConcurrentRequests() {
		return concurrentRequests;
	}
	public void setConcurrentRequests(long concurrentRequests) {
		this.concurrentRequests = concurrentRequests;
	}
	public long getNumErrors() {
		return numErrors;
	}
	public void setNumErrors(long numErrors) {
		this.numErrors = numErrors;
	}
	public long getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getFormatTime() {
		return formatTime;
	}
	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}
	public String getServletName() {
		return servletName;
	}
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}
	
}
