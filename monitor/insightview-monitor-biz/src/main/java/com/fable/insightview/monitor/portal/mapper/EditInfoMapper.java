package com.fable.insightview.monitor.portal.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.monitor.host.entity.MODevice;

public interface EditInfoMapper {
	List<MODevice> getDeviceName(Map map);

	Integer getMoClassIDBymoid(Integer moid);
}
