package com.fable.insightview.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.ItsmEventTypeDictDao;
import com.fable.insightview.platform.entity.ItsmEventTypeDict;
import com.fable.insightview.platform.service.ItsmEventTypeDictService;

@Service("ItsmEventTypeDictService")
public class ItsmEventTypeDictServiceImpl implements ItsmEventTypeDictService{

	@Autowired
	protected ItsmEventTypeDictDao itsmEventTypeDictDao;
	
	
	public List<ItsmEventTypeDict> getEventTypeItems(Integer id) {
		return itsmEventTypeDictDao.getEventTypeItems(id);
	}

}
