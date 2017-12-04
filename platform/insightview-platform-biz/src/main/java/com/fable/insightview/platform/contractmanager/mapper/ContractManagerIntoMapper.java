package com.fable.insightview.platform.contractmanager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fable.insightview.platform.contractmanager.entity.ComboxBean;
import com.fable.insightview.platform.contractmanager.entity.ProjectContract;
import com.fable.insightview.platform.page.Page;

public interface ContractManagerIntoMapper {
	/*
	 * 关联项目下拉框
	 */
	List<ComboxBean> queryProjectCombox();
	/*
	 * 合同类型下来框
	 */
	List<ComboxBean> queryContractTypeCombox();
	/*
	 * 添加合同信息
	 */
	void insertContractInfo(ProjectContract projectContract);
	/*
	 * 显示合同信息
	 */
	List<ProjectContract> queryContractList(Page page);
	/*
	 * 合同信息详情(编辑)
	 */
	ProjectContract queryContractInfo(Integer contractid);
	/*
	 * 修改合同信息
	 */
	void updateContractInfo(ProjectContract projectContract);
	/*
	 * 合同信息详情
	 */
	ProjectContract queryContractInfoDetail(Integer contractid);
	/*
	 * 删除合同信息
	 */
	void deleteProjectContractInfo(Integer contractid);
	
	/**
	 * 根據項目id查詢已經關聯的合同列表
	 * @param page
	 * @param projectId
	 * @return
	 */
	List<ProjectContract> queryContractListByProjectId(Page<ProjectContract> page);
	
	/**
	 * 排除已经与项目关联的合同
	 * @param paramMap 
	 * @param page
	 * @param projectId
	 * @return
	 */
	List<ProjectContract> queryContractExculdeProjectId(Page<ProjectContract> page);
	
	/**
	 * 项目记录删除之后更新合同外键
	 * @param projectId
	 */
	void updateCompactProjectId(Integer projectID);
	
	/**
	 * 统计项目合同模糊查询总条数
	 * @param paramMap
	 * @param projectId
	 * @return
	 */
	int selectCountExculdeProjectId(@Param(value = "params") Map<String, Object> paramMap, @Param(value = "projectId") Integer projectId);
	
	/**
	 * 统计已经关联资源的项目
	 * @param page
	 * @return
	 */
	int getCountContractByProjectId(@Param(value = "projectId") Integer projectId);
}