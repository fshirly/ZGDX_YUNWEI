package com.fable.insightview.monitor.machineRoom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.monitor.machineRoom.entity.MachineRoomBean;
import com.fable.insightview.monitor.machineRoom.entity.MoiemBean;
import com.fable.insightview.monitor.util.CurwarnBean;

public interface MachineRoomMapper {

	List<MachineRoomBean> queryInfo();
	/***
	 * 查询告警信息
	 * @return
	 */
	List<CurwarnBean> queryAlarmList();
	
	List<MoiemBean> queryDeviceInfo();
	
	List<MoiemBean> MOiemList(@Param("idRtDevice") String idRtDevice);
	
	int updateMOiemInfo(MoiemBean  moiemBean);
	
	/***
	 * 新增
	 * @param tempInsertList
	 * @return
	 */
	int insertMOiemInfo(List<MoiemBean>  tempInsertList);
}
