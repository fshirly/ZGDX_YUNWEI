package com.fable.insightview.platform.service;

import java.util.List;

import com.fable.insightview.platform.entity.ItsmEventTypeDict;

public interface ItsmEventTypeDictService {

	public List<ItsmEventTypeDict> getEventTypeItems(Integer id);
	
}
