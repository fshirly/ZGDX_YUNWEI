package com.fable.insightview.monitor.mocpus.mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.mocpus.entity.MOCPUsBean;
import com.fable.insightview.platform.page.Page;

public interface MOCPUsMapper {
	
	List<MOCPUsBean> selectMOCPUs(Page<MOCPUsBean> page);
	
	LinkedList<MOCPUsBean> synchronMOCPUsToRes(Page<MOCPUsBean> page);

	MOCPUsBean getMOCPUsId(int moID);

	int getByDeviceMOIDAndMOID(MOCPUsBean bean);
	
	List<MOCPUsBean> queryList(Page<MOCPUsBean> page);
	
	int updateResId(Map map);
	
	List<Integer> getResIDByDeviceId(@Param("deviceMoId")int deviceMoId);
}
