package com.fable.insightview.platform.importdata.importors;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fable.insightview.platform.dao.ISysEmploymentDao;
import com.fable.insightview.platform.dao.ISysUserDao;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.EmploymentGradeBean;
import com.fable.insightview.platform.entity.OrganizationalEntityBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.importdata.AbstractDataImportor;
import com.fable.insightview.platform.importdata.DataType;
import com.fable.insightview.platform.importdata.ImportResult;
import com.fable.insightview.platform.importdata.entity.SysUserImportEntity;
import com.fable.insightview.platform.importdata.resolver.DataResolver;
import com.fable.insightview.platform.importdata.resolver.DataResolverFactory;
import com.fable.insightview.platform.importdata.resolver.ExcelResolver;
import com.fable.insightview.platform.service.IDepartmentService;
import com.fable.insightview.platform.service.IEmploymentGradeService;
import com.fable.insightview.platform.service.ISysUserService;

public class UserImportor extends AbstractDataImportor<SysUserImportEntity> {
	
	@Autowired
	ISysUserService sysUserService;
	@Autowired
	protected ISysUserDao sysUserDao;
	@Autowired
	protected ISysEmploymentDao sysEmploymentDao;
	@Autowired
	IDepartmentService departmentService;
	@Autowired
	IEmploymentGradeService employmentGradeService;
	
	private final Logger logger = LoggerFactory.getLogger(UserImportor.class);
			
	@Override
	protected boolean logicHandle(List<SysUserImportEntity> data,ImportResult<SysUserImportEntity> result) {
		
		logger.info("开始用户导入逻辑处理。。。");
		List<String> resultList = new ArrayList<String>();
		List<String> orgEntityIdList = new ArrayList<String>();
		SysUserInfoBean userBean = new SysUserInfoBean();
		SysEmploymentBean empBean = new SysEmploymentBean();
		List<DepartmentBean> beans = departmentService.getDepartmentBeanByConditions("", "");
		List<OrganizationalEntityBean> orgBeans = sysUserService.getAllOrganizationalEntity();
		if (orgBeans.size() > 0) {
			for (int i = 0; i < orgBeans.size(); i++) {
				orgEntityIdList.add(orgBeans.get(i).getId());
			}
		}
		Map<String, Integer> deptMap = new HashMap<String, Integer>();
		
		for (int i = 0; i < beans.size(); i++) {
			if (beans.get(i).getDeptCode() != null && !"".equals(beans.get(i).getDeptCode())) {
				deptMap.put(beans.get(i).getDeptCode(), beans.get(i).getDeptId());
			}
		}
		
		logger.info("开始遍历导入的用户信息。。。");
		String emailReg = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		String phoneReg = "^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$";
		String telReg = "^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}$" ;
		int sucessNum = 0;
		for (int i = 0; i < data.size(); i++) {
			
			// UserType：0:管理员,1企业内IT部门用户,2:企业业务部门用户,3:外部供应商用户
			SysUserImportEntity importEntity = data.get(i);
			userBean.setUserAccount(importEntity.getUserAccount());
			List<SysUserInfoBean> userLst = sysUserService.getSysUserByConditions(
					"userAccount", userBean.getUserAccount());
//			int i=sysUserService.getOrganization(sysUserBean.getUserAccount());
			if ((null == userLst || userLst.size() <= 0)) {
				userBean.setUserName(importEntity.getUserName());
				if (importEntity.getMobilePhone().matches(phoneReg) == true) {
					userBean.setMobilePhone(importEntity.getMobilePhone());
				} else {
					resultList.add("第" + (i + 2) + "行请输入正确的手机格式，如18788888888！");
					result.setProcessResultList(resultList);
					continue;
				}
				if (importEntity.getTelephone().matches(telReg) == true) {
					userBean.setTelephone(importEntity.getTelephone());
				} else {
					resultList.add("第" + (i + 2) + "行请输入正确的手机或固话号码，如18788888888，02588888888，0522-88888888！");
					result.setProcessResultList(resultList);
					continue;
				}
				
				if (importEntity.getEmail().matches(emailReg) == true) {
					userBean.setEmail(importEntity.getEmail());
				} else {
					resultList.add("第" + (i + 2) + "行请输入正确的邮箱格式，如examplename@example.com！");
					result.setProcessResultList(resultList);
					continue;
				}
				
				
				/*
				 * if ("管理员".equals(importEntity.getUserType())) {
				 * userBean.setUserType(0); } else if
				 * ("企业内IT部门用户".equals(importEntity.getUserType())) {
				 * userBean.setUserType(1); } else if
				 * ("企业业务部门用户".equals(importEntity.getUserType())) {
				 * userBean.setUserType(2); } else { userBean.setUserType(3); }
				 */
				if ("企业内IT部门用户".equals(importEntity.getUserType())) {
					userBean.setUserType(1);
				} else if ("企业业务部门用户".equals(importEntity.getUserType())) {
					userBean.setUserType(2);
				} else {
					userBean.setUserType(3);
				}
				userBean.setCreateTime(this.getCurrDate());
				try {
					
					//设置默认密码为123456
					userBean.setUserPassword("123456");
					userBean.setStatus(1);
					userBean.setIsAutoLock(1);
					if (deptMap.get(importEntity.getDeptCode()) == null) {
						resultList.add("第" + (i + 2) + "行部门编码"
								+ importEntity.getDeptCode() + "在数据库中不存在！");
						result.setProcessResultList(resultList);
						continue;
					}
					Integer organizationId = departmentService.getDepartmentById(deptMap.get(importEntity
							.getDeptCode())).getOrganizationBean().getOrganizationID();
					List<EmploymentGradeBean> gradeBeans=employmentGradeService.getGradeInfo(organizationId);
					if (gradeBeans.size()==0) {
						resultList.add("第" + (i + 2) + "行用户所属部门单位"+importEntity.getGradeName()+"职位不存在！");
						result.setProcessResultList(resultList);
						continue;
					} else {
						Map<String,Integer> gradeMap = new HashMap<String,Integer>();
						for (int j = 0; j < gradeBeans.size(); j++) {
							gradeMap.put(gradeBeans.get(j).getGradeName(), gradeBeans.get(j).getGradeID());
						}
						if (gradeMap.get(importEntity.getGradeName()) == null) {
							resultList.add("第" + (i + 2) + "行用户所属部门单位"+importEntity.getGradeName()+"职位不存在！");
							result.setProcessResultList(resultList);
							continue;
						} else {
							boolean addUser = sysUserDao.addSysUser(userBean);
							if (addUser == true) {
								//入表OrganizationalEntity
								if (orgEntityIdList.contains(userBean.getUserAccount()) == false) {
									OrganizationalEntityBean orgEntityBean = new OrganizationalEntityBean();
									orgEntityBean.setDtype("User");
									orgEntityBean.setId(userBean.getUserAccount());
									boolean flagAdd = sysUserService.addOrganization(orgEntityBean);
									if (flagAdd == true) {
										orgEntityIdList.add(userBean.getUserAccount());
									}
								}
								
								//入员工表
								empBean.setDeptID(deptMap.get(importEntity.getDeptCode()));
								empBean.setEmployeeCode(importEntity.getEmployeeCode());
								empBean.setUserId(userBean.getUserID());
								empBean.setOrganizationID(departmentService.getDepartmentById(deptMap.get(importEntity
										.getDeptCode())).getOrganizationBean().getOrganizationID());
								empBean.setGradeID(gradeMap.get(importEntity.getGradeName()));
								boolean addSysE = sysEmploymentDao.addSysEmp(empBean);
								if (addSysE == true) {
									sucessNum ++ ;
								}
							}
						}
					}
				} catch (Exception e) {
					logger.error("用户导入异常！",e.getMessage());
					return false;
				}
			} else {
				resultList.add("第" + (i + 2) + "行用户名"
						+ importEntity.getUserAccount() + "在数据库中已经存在！");
				result.setProcessResultList(resultList);
				continue;
			}
			
		}
		result.setFailureNum(data.size()-sucessNum);
		return true;
	}

	@Override
	public DataResolver<SysUserImportEntity> getResolver() {
		
		//获取Excel数据解析器
		ExcelResolver<SysUserImportEntity> dataResolver = (ExcelResolver) DataResolverFactory
				.getInstance().getDataResolver(DataType.EXCEL,
						SysUserImportEntity.class);
		return dataResolver;
	}

	public static Timestamp getCurrDate() {
		
		Date d = new Date();
		Timestamp ts = new Timestamp(d.getTime());
		return ts;
	}

}
