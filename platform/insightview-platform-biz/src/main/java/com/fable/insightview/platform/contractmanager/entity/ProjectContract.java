package com.fable.insightview.platform.contractmanager.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class ProjectContract {
  @NumberGenerator(name="ID")
   private Integer id;
   private Integer projectId;//所属项目
   private Date    signTime;//签订时间
   private String contractNo;//合同编号
   private Date validTimeBegin;//合同有效期_开始
   private Date validTimeEnd;//合同有效期_结束
   private Byte contractType;//合同类型
   private BigDecimal moneyAmount;//合同金额
   private String responserName;//负责人
   private String owner;//甲方
   private String partyB;//乙方
   private Double cashDeposit;//保证金
   private String contractSummary;//合同摘要
   private String title;//合同标题
   private String fileCachearrat;//文件缓存记录
   /*
    * 非数据库字段
    */
   private Date nextPayDate;
   private Double nextPayment;
   private String projecttitle;
   private String typeneir;
   private Integer remainder;
   private Double nextAmount;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getProjectId() {
	return projectId;
}
public void setProjectId(Integer projectId) {
	this.projectId = projectId;
}
public Date getSignTime() {
	return signTime;
}
public void setSignTime(Date signTime) {
	this.signTime = signTime;
}
public String getContractNo() {
	return contractNo;
}
public void setContractNo(String contractNo) {
	this.contractNo = contractNo;
}
public Date getValidTimeBegin() {
	return validTimeBegin;
}
public void setValidTimeBegin(Date validTimeBegin) {
	this.validTimeBegin = validTimeBegin;
}
public Date getValidTimeEnd() {
	return validTimeEnd;
}
public void setValidTimeEnd(Date validTimeEnd) {
	this.validTimeEnd = validTimeEnd;
}
public Byte getContractType() {
	return contractType;
}
public void setContractType(Byte contractType) {
	this.contractType = contractType;
}
public BigDecimal getMoneyAmount() {
	return moneyAmount;
}
public void setMoneyAmount(BigDecimal moneyAmount) {
	this.moneyAmount = moneyAmount;
}
public String getResponserName() {
	return responserName;
}
public void setResponserName(String responserName) {
	this.responserName = responserName;
}
public String getOwner() {
	return owner;
}
public void setOwner(String owner) {
	this.owner = owner;
}
public String getPartyB() {
	return partyB;
}
public void setPartyB(String partyB) {
	this.partyB = partyB;
}
public Double getCashDeposit() {
	return cashDeposit;
}
public void setCashDeposit(Double cashDeposit) {
	this.cashDeposit = cashDeposit;
}

public String getContractSummary() {
	return contractSummary;
}
public void setContractSummary(String contractSummary) {
	this.contractSummary = contractSummary;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getFileCachearrat() {
	return fileCachearrat;
}
public void setFileCachearrat(String fileCachearrat) {
	this.fileCachearrat = fileCachearrat;
}
public Date getNextPayDate() {
	return nextPayDate;
}
public void setNextPayDate(Date nextPayDate) {
	this.nextPayDate = nextPayDate;
}
public Double getNextPayment() {
	return nextPayment;
}
public void setNextPayment(Double nextPayment) {
	this.nextPayment = nextPayment;
}
public String getProjecttitle() {
	return projecttitle;
}
public void setProjecttitle(String projecttitle) {
	this.projecttitle = projecttitle;
}
public String getTypeneir() {
	return typeneir;
}
public void setTypeneir(String typeneir) {
	this.typeneir = typeneir;
}
public Integer getRemainder() {
	return remainder;
}
public void setRemainder(Integer remainder) {
	this.remainder = remainder;
}
public Double getNextAmount() {
	return nextAmount;
}
public void setNextAmount(Double nextAmount) {
	this.nextAmount = nextAmount;
}
   
}
