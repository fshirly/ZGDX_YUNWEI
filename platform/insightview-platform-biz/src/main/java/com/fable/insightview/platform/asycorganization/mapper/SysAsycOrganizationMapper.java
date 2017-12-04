package com.fable.insightview.platform.asycorganization.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.asycorganization.entity.SysAsycDepartMent;
import com.fable.insightview.platform.asycorganization.entity.SysAsycOrganization;

public interface SysAsycOrganizationMapper {
  public List<SysAsycOrganization>  findSyncOrganizationList(Map<String,Object> params);
  public SysAsycDepartMent  quaryDeptInfos(Map<String,Object> map);
  public SysAsycOrganization quaryOrganizationInfo(Map<String,Object> map);
  public int insertDepartmentInfo(SysAsycDepartMent sysAsycDepartMent);
  public int updateDepartmentInfo(SysAsycDepartMent sysAsycDepartMent);
  public List<SysAsycOrganization> findUserInsertSyncOrganizationList(Map<String,Object> params);
  public List<SysAsycDepartMent>  findUserInsertSyncDeptList(Map<String,Object> params);
  public SysAsycOrganization findUserAddSyncOrganizationList(Map<String,Object> params);
}
