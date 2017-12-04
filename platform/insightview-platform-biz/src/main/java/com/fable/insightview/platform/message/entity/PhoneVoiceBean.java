package com.fable.insightview.platform.message.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 电话语音
 *
 */
public class PhoneVoiceBean {
	@NumberGenerator(name="PhoneVoicePK")
	private Integer id;
	private String name;
	private String path;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
