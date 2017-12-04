package com.fable.insightview.platform.message.mapper;

import java.util.List;

import com.fable.insightview.platform.message.entity.PfMessage;

public interface PfMessageMapper {

	/**
	 * 新增短信提醒信息
	 * @param msg
	 */
	void insert(PfMessage msg);
	
	/**
	 * 批量新增短信提醒信息
	 * @param msg
	 */
	void batchInsert(List<PfMessage> msgs);
}
