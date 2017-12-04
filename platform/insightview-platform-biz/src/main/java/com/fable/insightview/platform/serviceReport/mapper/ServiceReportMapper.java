package com.fable.insightview.platform.serviceReport.mapper;

import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.serviceReport.entity.ServiceReport;

public interface ServiceReportMapper {

	/**
	 * 分页查询服务报告列表
	 * @param page
	 * @return
	 */
	List<ServiceReport> getServiceReportByConditions(Page<ServiceReport> page);

	/**
	 * 查询总记录数
	 * @param serviceReport
	 * @return
	 */
	int getTotalCount(ServiceReport serviceReport);

	/**
	 * 服务报告新增
	 * @param serviceReport
	 */
	void insertServiceReport(ServiceReport serviceReport);

	/**
	 * 根据id查询服务报告信息
	 * @param id
	 * @return
	 */
	ServiceReport getServiceReportById(int id);

	/**
	 * 修改
	 * @param serviceReport
	 */
	void updateServiceReport(ServiceReport serviceReport);

	/**
	 * 删除
	 * @param id
	 */
	void delMaterialApply(Integer id);

}
