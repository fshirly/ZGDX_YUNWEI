package com.fable.insightview.monitor.machineRoom.service;

import java.util.List;

import com.fable.insightview.monitor.machineRoom.entity.MachineRoomBean;

public interface MachineRoomService {
	/***
	 * 查询机房设备详情
	 * @return
	 */
	List<MachineRoomBean> queryInfo();
	
	 boolean queryDeviceInfo();
}
