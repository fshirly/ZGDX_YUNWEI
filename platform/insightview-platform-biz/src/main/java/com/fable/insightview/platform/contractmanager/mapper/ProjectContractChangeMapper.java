package com.fable.insightview.platform.contractmanager.mapper;
import java.util.List;

import com.fable.insightview.platform.contractmanager.entity.ProjectContractChange;
import com.fable.insightview.platform.page.Page;
public interface ProjectContractChangeMapper {
   /*
    * 变更信息list
    */
	public List<ProjectContractChange> queryProjectContractChangeList(Page page);
   /*
    * 新增变更信息
    */
	public void insertProjectContractChangeInfo(ProjectContractChange projectContractChange);
	/*
	 * 修改变更信息
	 */
	public void updateProjectContractChangeInfo(ProjectContractChange projectContractChange);
	/*
	 * 查询详细变更信息
	 */
	public ProjectContractChange queryProjectContractChangeInfo(Integer id);
	/*
	 * 删除变更信息
	 */
	public void deleteProjectContractChangeInfo(Integer id);
}
