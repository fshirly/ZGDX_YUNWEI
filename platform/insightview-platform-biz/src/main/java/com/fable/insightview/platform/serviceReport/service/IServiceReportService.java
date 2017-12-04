package com.fable.insightview.platform.serviceReport.service;

import java.util.List;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.serviceReport.entity.ServiceReport;

public interface IServiceReportService {

	/**
	 * 分页按条件查询服务报告列表
	 * @param page
	 * @return
	 */
	List<ServiceReport> getServiceReportByConditions(Page<ServiceReport> page);

	/**
	 * 查询总条数
	 * @param serviceReport
	 * @return
	 */
	int getTotalCount(ServiceReport serviceReport);

	/**
	 * 服务报告新增
	 * @param serviceReport
	 */
	void newServiceReport(ServiceReport serviceReport);

	/**
	 * 根据id查看服务报告信息
	 * @param parseInt
	 * @return
	 */
	ServiceReport getServiceReportById(int id);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	boolean delServiceReport(Integer id);

	/**
	 * 批量删除
	 * @param splitIds
	 */
	void delByServiceReportIds(String[] splitIds);

}
