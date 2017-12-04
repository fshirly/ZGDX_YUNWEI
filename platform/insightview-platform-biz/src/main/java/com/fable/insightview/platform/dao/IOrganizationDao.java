package com.fable.insightview.platform.dao;

import java.util.List;

import com.fable.insightview.platform.employmentGrade.entity.SysEmploymentGradeBean;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserGroupBean;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.managedDomain.entity.ManagedDomain;
/**
 * @Description:   单位信息Dao 
 */
public interface IOrganizationDao extends GenericDao<OrganizationBean> {
	List<OrganizationBean> getOrganizationBeanByConditions(String paramName,
			String paramValue);
	
	/* 更新单位组织 */
	boolean updateOrganizationBean(OrganizationBean organizationBean);

	/* 数据总记录数 */
	int getTotalCount(OrganizationBean organizationBean);

	/* 新增单位组织 */
	boolean addOrganization(OrganizationBean organizationBean);

	/* 删除单位组织 */
	boolean delOrganizationById(OrganizationBean organizationBean);

	/* 查询单位组织 */
	List<OrganizationBean> getOrganizationByConditions(OrganizationBean organizationBean,FlexiGridPageInfo flexiGridPageInfo);

	/* 根据上级组织查询单位组织 */
	List<OrganizationBean> findByParentId(int parentId);
	
	/* 条件查询单位组织 */
	List<OrganizationBean> getOrganizationByConditions(String paramName,String paramValue);
	
	/* 根据组织Id获得用户组 */
	List<SysUserGroupBean> getSysUserGroupByOrganizationID(OrganizationBean organizationBean);
	
	/* 根据组织Id获得单位员工 */
	List<SysEmploymentBean> getSysEmployByOrganizationID(OrganizationBean organizationBean);
	
	/* 根据组织Id获得职务级别 */
	List<SysEmploymentGradeBean> getSysEmployGradeByOrganizationID(OrganizationBean organizationBean);

	/*  获得组织树 */
	List<OrganizationBean> getOrganizationTreeVal();
	
	/*  获得组织树 */
	List<OrganizationBean> getOrganizationTree(String deptId);
	
	/* 根据单位Id获得上级部门名称 */
	List<OrganizationBean> getParentnameByIds(List<Integer> ids);
	
	/* 获得子单位的Id */
	public List<Integer> getChildIdByOrgIDList(List<Integer> organizationIDList);
	
	/* 根据单位Id获得与所有下属单位*/
	public List<Integer> getAllOrgId(int organizationID);
	
	/* 根据单位名称获得单位Id */
	public List<Integer> getOrganizationIDByOrgName(String organizatioName);
	
	/* 验证单位名称的唯一性 */
	public boolean checkOrganizationName(OrganizationBean organizationBean);
	
	/* 验证单位编码的唯一性 */
	public boolean checkOrganizationCode(OrganizationBean organizationBean);
	
	/* 根据单位Id获得管理域 */
	public List<ManagedDomain> getSysManagedDomainByOrganizationID(OrganizationBean organizationBean);
	
	/* 根据单位Id获得部门 */
	public List<DepartmentBean> getDepartmentByOrganizationID(OrganizationBean organizationBean);
	
	/* 根据单位名称获得单位 */
	public OrganizationBean getOrganizationBeanByOrgName(String organizationName);

	/*获取该组织下用户Accounts集合*/
	List<Integer> getUserIdsByOrgId(Integer branchId);

	/*获取供应商用户所在供应商服务的单位列表*/
	List<OrganizationBean> listOrganizationByUserId(int currentUserId);
	
	/**
	 * 根据用户Id查询所属单位信息
	 * @param userId
	 * @return
	 */
	List<OrganizationBean> getOrgByUserId(int userId);

	OrganizationBean getOrganizationByCode(String organizationCode);
}
