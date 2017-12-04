package com.fable.insightview.monitor.momemories.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.momemories.entity.MOMemoriesBean;
import com.fable.insightview.platform.page.Page;

public interface MOMemoriesMapper {
	List<MOMemoriesBean> selectMOMemories(Page<MOMemoriesBean> page);

	MOMemoriesBean getMOMemoriesById(int moID);

	int getByDeviceMOIDAndMOID(MOMemoriesBean bean);
	
	List<MOMemoriesBean> queryList(Page<MOMemoriesBean> page);
		
	List<MOMemoriesBean> synchronMOMemoryToRes(Page<MOMemoriesBean> page);
	
	int updateResId(Map map);
	
	List<Integer> getResIDByDeviceId(@Param("deviceMoId")int deviceMoId);
}
