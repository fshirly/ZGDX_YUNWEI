package com.fable.insightview.monitor.portal.service;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.topo.entity.TopoBean;


public interface IEditInfoService {
	List<MODevice> getDeviceName(Map map);

	Integer getMoClassIDBymoid(Integer moid);

	List<TopoBean> getAllTopo();
}
