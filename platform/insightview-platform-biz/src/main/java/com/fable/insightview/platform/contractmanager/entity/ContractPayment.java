package com.fable.insightview.platform.contractmanager.entity;

import java.util.Date;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 *合同付款
 */
public class ContractPayment {
   @NumberGenerator(name="PaymentID")
   private Integer paymentID;
   private Integer contractID;
   private Date planPayTime;
   private Double planPayAmount;
   private Date paymentTime;
   private Double amount;
   private String handler;
   private String desc;
   private String certificateUrl;
public Integer getPaymentID() {
	return paymentID;
}
public void setPaymentID(Integer paymentID) {
	this.paymentID = paymentID;
}
public Integer getContractID() {
	return contractID;
}
public void setContractID(Integer contractID) {
	this.contractID = contractID;
}
public Date getPlanPayTime() {
	return planPayTime;
}
public void setPlanPayTime(Date planPayTime) {
	this.planPayTime = planPayTime;
}
public Double getPlanPayAmount() {
	return planPayAmount;
}
public void setPlanPayAmount(Double planPayAmount) {
	this.planPayAmount = planPayAmount;
}
public Date getPaymentTime() {
	return paymentTime;
}
public void setPaymentTime(Date paymentTime) {
	this.paymentTime = paymentTime;
}
public Double getAmount() {
	return amount;
}
public void setAmount(Double amount) {
	this.amount = amount;
}
public String getHandler() {
	return handler;
}
public void setHandler(String handler) {
	this.handler = handler;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public String getCertificateUrl() {
	return certificateUrl;
}
public void setCertificateUrl(String certificateUrl) {
	this.certificateUrl = certificateUrl;
}
   
}
