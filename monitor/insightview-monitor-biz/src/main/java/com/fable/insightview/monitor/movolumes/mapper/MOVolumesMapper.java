package com.fable.insightview.monitor.movolumes.mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
 

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.movolumes.entity.MOVolumesBean;
import com.fable.insightview.platform.page.Page;

public interface MOVolumesMapper {
	List<MOVolumesBean> selectMOVolumes(Page<MOVolumesBean> page);
	
	LinkedList<MOVolumesBean> synchronMOVolumes(Page<MOVolumesBean> page);

	MOVolumesBean getVolumesById(int moID);

	int getByDeviceMOIDAndMOID(MOVolumesBean bean);
	
	List<MOVolumesBean> queryList(Page<MOVolumesBean> page);
	
	int updateResId(Map map);
	
	List<Integer> getResIDByDeviceId(@Param("deviceMoId")int deviceMoId);
	int updateDiskIfCollect(@Param("moID")int moID,@Param("isCollect") int isCollect);
	void deleteSnapshotDeviceDevice(@Param("moID") int moID);
}
