package com.fable.insightview.platform.smstools.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class SMSServerOutBean {
	@NumberGenerator(name = "SMSServerOutPK")
	private Integer id;
	private String type;
	private String recipient;
	private String text;
	private String wap_url;
	private String wap_expiry_date;
	private String wap_signal;
	private String create_date;
	private String originator;
	private String encoding;
	private Integer status_report;
	private Integer flash_sms;
	private Integer src_port;
	private Integer dst_port;
	private String sent_date;
	private String ref_no;
	private Integer priority;
	private String status;
	private Integer errors;
	private String gateway_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getWap_url() {
		return wap_url;
	}

	public void setWap_url(String wapUrl) {
		wap_url = wapUrl;
	}

	public String getWap_expiry_date() {
		return wap_expiry_date;
	}

	public void setWap_expiry_date(String wapExpiryDate) {
		wap_expiry_date = wapExpiryDate;
	}

	public String getWap_signal() {
		return wap_signal;
	}

	public void setWap_signal(String wapSignal) {
		wap_signal = wapSignal;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String createDate) {
		create_date = createDate;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Integer getStatus_report() {
		return status_report;
	}

	public void setStatus_report(Integer statusReport) {
		status_report = statusReport;
	}

	public Integer getFlash_sms() {
		return flash_sms;
	}

	public void setFlash_sms(Integer flashSms) {
		flash_sms = flashSms;
	}

	public Integer getSrc_port() {
		return src_port;
	}

	public void setSrc_port(Integer srcPort) {
		src_port = srcPort;
	}

	public Integer getDst_port() {
		return dst_port;
	}

	public void setDst_port(Integer dstPort) {
		dst_port = dstPort;
	}

	public String getSent_date() {
		return sent_date;
	}

	public void setSent_date(String sentDate) {
		sent_date = sentDate;
	}

	public String getRef_no() {
		return ref_no;
	}

	public void setRef_no(String refNo) {
		ref_no = refNo;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getErrors() {
		return errors;
	}

	public void setErrors(Integer errors) {
		this.errors = errors;
	}

	public String getGateway_id() {
		return gateway_id;
	}

	public void setGateway_id(String gatewayId) {
		gateway_id = gatewayId;
	}

}
