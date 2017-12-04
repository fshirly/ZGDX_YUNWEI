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

import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.importdata.AbstractDataImportor;
import com.fable.insightview.platform.importdata.DataType;
import com.fable.insightview.platform.importdata.ImportResult;
import com.fable.insightview.platform.importdata.entity.DepartmentImportEntity;
import com.fable.insightview.platform.importdata.resolver.DataResolver;
import com.fable.insightview.platform.importdata.resolver.DataResolverFactory;
import com.fable.insightview.platform.importdata.resolver.ExcelResolver;
import com.fable.insightview.platform.service.IDepartmentService;
import com.fable.insightview.platform.service.IOrganizationService;

public class DepartmentImportor extends AbstractDataImportor<DepartmentImportEntity> {

	@Autowired
	IDepartmentService departmentService;
	@Autowired
	IOrganizationService  organizationService;
	
	private final Logger logger = LoggerFactory.getLogger(DepartmentImportor.class);
			
	@Override
	protected boolean logicHandle(List<DepartmentImportEntity> data,ImportResult<DepartmentImportEntity> result) {
		
		logger.info("开始部门导入数据逻辑处理。。。");
		DepartmentBean deptBean = new DepartmentBean();
		boolean flag = false;
		List<String> resultList = new ArrayList<String>();
		List<DepartmentBean> beans = departmentService.getDepartmentBeanByConditions("", "");
		List<String> deptCodeList = new ArrayList<String>();
		Map<String,Integer> deptMap = new HashMap<String,Integer>();
		Map<String,Integer> orgMap = new HashMap<String,Integer>();
		
		for (int i = 0; i < beans.size(); i++) {
			deptMap.put(beans.get(i).getDeptCode(), beans.get(i).getDeptId());
			if(beans.get(i).getDeptCode() != null && !"".equals(beans.get(i).getDeptCode())){
				deptCodeList.add(beans.get(i).getDeptCode());
			}
		}
		
		List<OrganizationBean> orgBeans = organizationService.getOrganizationBeanByConditions("", "");
		for (int i = 0; i < orgBeans.size(); i++) {
			orgMap.put(orgBeans.get(i).getOrganizationName(), orgBeans.get(i).getOrganizationID());
		}
		
		int sucessNum = 0;
		for (int i = 0; i < data.size(); i++) {
			try {
				if(!"".equals(data.get(i).getDeptCode()) && deptCodeList.size()>0){
					if(deptCodeList.indexOf(data.get(i).getDeptCode())>=0){
						resultList.add("第"+(i+2)+"行部门编码"+data.get(i).getDeptCode()+"在数据库中已经存在！");
						result.setProcessResultList(resultList);
						continue;
					}
				}
				if(orgMap.get(data.get(i).getOrganizationName()) == null){
					resultList.add("第"+(i+2)+"行所属单位"+data.get(i).getOrganizationName()+"在数据库中不存在！");
					result.setProcessResultList(resultList);
					continue;
				}
				if(!"".equals(data.get(i).getParentDeptCode())){
					if (deptCodeList.indexOf(data.get(i).getParentDeptCode()) < 0) {
						resultList.add("第"+(i+2)+"行上级部门编码"+data.get(i).getParentDeptCode()+"在数据库中不存在！");
						result.setProcessResultList(resultList);
						continue;
					} else {
						deptBean.setParentDeptID(deptMap.get(data.get(i).getParentDeptCode()));
					}
					
				} else {
					deptBean.setParentDeptID(0);
				}
				deptBean.setDeptName(data.get(i).getDeptName());
				deptBean.setOrganizationID(Integer.valueOf(orgMap.get(data.get(i)
						.getOrganizationName())));
				
					
				deptBean.setDescr(data.get(i).getDescr());
				OrganizationBean orgBean = organizationService.findOrganizationById(Integer.valueOf(orgMap.get(data.get(i)
						.getOrganizationName())));
				deptBean.setOrganizationBean(orgBean);
				deptBean.setHeadOfDept(0);
				deptBean.setDeptCode(data.get(i).getDeptCode());
				flag = departmentService.addDepartment(deptBean);
				if(flag == true) {
					deptMap.put(deptBean.getDeptCode(), deptBean.getDeptId());
					deptCodeList.add(deptBean.getDeptCode());
					sucessNum ++ ;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("部门导入异常！",e.getMessage());
				flag = false;
			}
		}
		result.setFailureNum(data.size()-sucessNum);
		return flag;
	}

	@Override
	public DataResolver<DepartmentImportEntity> getResolver() {
		
		ExcelResolver<DepartmentImportEntity> dataResolver = (ExcelResolver) DataResolverFactory
				.getInstance().getDataResolver(DataType.EXCEL,
						DepartmentImportEntity.class);
		return dataResolver;
	}

	public static Timestamp getCurrDate() {
		Date d = new Date();
		Timestamp ts = new Timestamp(d.getTime());
		return ts;
	}

}
