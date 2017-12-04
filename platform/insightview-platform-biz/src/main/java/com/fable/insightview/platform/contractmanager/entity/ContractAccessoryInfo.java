package com.fable.insightview.platform.contractmanager.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class ContractAccessoryInfo {
  @NumberGenerator(name="ID")
  private Integer id;
  private Integer contractID;
  private String fileName;
  private String url;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getContractID() {
	return contractID;
}
public void setContractID(Integer contractID) {
	this.contractID = contractID;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
}
