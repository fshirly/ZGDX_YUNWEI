package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.entity.ItsmEventTypeDict;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;

public interface ItsmEventTypeDictDao extends GenericDao<ItsmEventTypeDict>{

	public List<ItsmEventTypeDict> getEventTypeItems(Integer id);
	
}
