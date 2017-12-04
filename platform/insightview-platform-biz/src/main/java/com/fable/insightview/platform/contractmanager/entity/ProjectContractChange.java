package com.fable.insightview.platform.contractmanager.entity;

import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class ProjectContractChange {
  @NumberGenerator(name="Id")
  private Integer id;
  private Integer contractId;
  private String title;
  private Date changeTime;
  private String description;
  private String confirmManName;
  private String url;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getContractId() {
	return contractId;
}
public void setContractId(Integer contractId) {
	this.contractId = contractId;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public Date getChangeTime() {
	return changeTime;
}
public void setChangeTime(Date changeTime) {
	this.changeTime = changeTime;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getConfirmManName() {
	return confirmManName;
}
public void setConfirmManName(String confirmManName) {
	this.confirmManName = confirmManName;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
}
