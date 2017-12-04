package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.deleteMonitorObject.entity.ServiceBean;
import com.fable.insightview.monitor.deleteMonitorObject.mapper.DeviceServiceMapper;
import com.fable.insightview.monitor.deleteMonitorObject.service.DeviceService;
@Service
public class DevcieSeriveImpl implements DeviceService {

	@Autowired
	DeviceServiceMapper deviceServiceMapper;

	@Override
	public List<ServiceBean> queryDBServices(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return deviceServiceMapper.queryServices(map);
	}

	@Override
	public List<Integer> queryVMID(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return deviceServiceMapper.queryVMID(map);
	}

	@Override
	public List<ServiceBean> queryZoneMagerServie(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return deviceServiceMapper.queryZoneMagerServie(map);
	}

	@Override
	public List<ServiceBean> querydeviceIP(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return deviceServiceMapper.querydeviceIP(map);
	}

	@Override
	public List<String> queryVMIP(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return deviceServiceMapper.queryVMIP(map);
	}

	@Override
	public List<ServiceBean> queryVhostServices(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return deviceServiceMapper.queryVhostServices(map);
	}

	@Override
	public List<ServiceBean> queryVmServices(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return deviceServiceMapper.queryVmServices(map);
	}


}
