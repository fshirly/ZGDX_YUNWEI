package com.fable.insightview.platform.notifypolicycfg.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 通知策略模板
 * 
 */
public class NotifyPolicyContentBean {
	@NumberGenerator(name = "pfNotifyPolicyContentPK")
	private Integer id;
	private Integer policyId;
	private Integer notifyType;
	private String name;
	private String content;
	private Integer voiceMessageType;
	private Integer typeId;
	private Integer voiceId;
	private String voiceName;
	private String voicePath;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public Integer getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getVoiceMessageType() {
		return voiceMessageType;
	}

	public void setVoiceMessageType(Integer voiceMessageType) {
		this.voiceMessageType = voiceMessageType;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getVoiceId() {
		return voiceId;
	}

	public void setVoiceId(Integer voiceId) {
		this.voiceId = voiceId;
	}

	public String getVoiceName() {
		return voiceName;
	}

	public void setVoiceName(String voiceName) {
		this.voiceName = voiceName;
	}

	public String getVoicePath() {
		return voicePath;
	}

	public void setVoicePath(String voicePath) {
		this.voicePath = voicePath;
	}
	
}
