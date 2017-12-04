package com.fable.insightview.monitor.mostorage.mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.mostorage.entity.MOStorageBean;
import com.fable.insightview.platform.page.Page;

public interface MOStorageMapper {
	List<MOStorageBean> selectMOStorage(Page<MOStorageBean> page);
	
	LinkedList<MOStorageBean> synchronMOStorage(Page<MOStorageBean> page);

	List<MOStorageBean> queryList(Page<MOStorageBean> page);
	
	int updateResId(Map map);
	
	List<Integer> getResIDByDeviceId(@Param("deviceMoId")int deviceMoId);
}
