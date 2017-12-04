package com.fable.insightview.platform.serviceReport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.serviceReport.entity.ServiceReport;
import com.fable.insightview.platform.serviceReport.mapper.ServiceReportMapper;
import com.fable.insightview.platform.serviceReport.service.IServiceReportService;

@Service("serviceReportService")
public class ServiceReportServiceImpl implements IServiceReportService{

	@Autowired
	ServiceReportMapper serviceReportMapper;
	
	@Override
	public List<ServiceReport> getServiceReportByConditions(
			Page<ServiceReport> page) {
		return serviceReportMapper.getServiceReportByConditions(page);
	}

	@Override
	public int getTotalCount(ServiceReport serviceReport) {
		return serviceReportMapper.getTotalCount(serviceReport);
	}

	@Override
	public void newServiceReport(ServiceReport serviceReport) {
		if(null != serviceReport.getServiceReportID()) {
			serviceReportMapper.updateServiceReport(serviceReport);
		} else {
			serviceReportMapper.insertServiceReport(serviceReport);
		}
		
	}

	@Override
	public ServiceReport getServiceReportById(int id) {
		return serviceReportMapper.getServiceReportById(id);
	}

	@Override
	public boolean delServiceReport(Integer id) {
		serviceReportMapper.delMaterialApply(id);
		return true;
	}

	@Override
	public void delByServiceReportIds(String[] splitIds) {
		for (String id : splitIds) {
			this.delServiceReport(Integer.valueOf(id));
		}
		
	}

}
