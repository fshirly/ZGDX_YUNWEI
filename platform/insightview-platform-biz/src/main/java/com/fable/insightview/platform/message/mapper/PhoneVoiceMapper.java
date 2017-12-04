package com.fable.insightview.platform.message.mapper;

import java.util.List;

import com.fable.insightview.platform.message.entity.PhoneVoiceBean;

public interface PhoneVoiceMapper {
	/* 查询所有 */
	List<PhoneVoiceBean> queryAllPhoneVoice();

	/* 根据ID查询 */
	PhoneVoiceBean getPhoneVoiceByID(int id);
}
