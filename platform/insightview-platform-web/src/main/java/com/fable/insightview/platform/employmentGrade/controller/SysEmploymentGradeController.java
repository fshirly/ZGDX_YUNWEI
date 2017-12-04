package com.fable.insightview.platform.employmentGrade.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.permission.form.EmploymentGradeForm;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.dictionary.entity.ConstantItemDef;
import com.fable.insightview.platform.employmentGrade.entity.SysEmploymentGradeBean;
import com.fable.insightview.platform.employmentGrade.service.ISysEmploymentGradeService;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.EmploymentGradeBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.service.IEmploymentGradeService;
import com.fable.insightview.platform.service.IOrganizationService;

@Controller
@RequestMapping("/platform/sysEmploymentGrade")
public class SysEmploymentGradeController {
	private final Logger logger = LoggerFactory.getLogger(SysEmploymentGradeController.class);
	@Autowired
	private HttpServletRequest request;
	@Autowired
	IOrganizationService organizationService;
	@Autowired
	ISysEmploymentGradeService empGradeService;

	/**
	 * 职务等级管理页面弹出
	 * @return
	 */
	@RequestMapping("/employmentGradeView")
	public ModelAndView toEmploymentGradeList(String navigationBar) {
		logger.info("strat..........进入职务等级管理页面");
		ModelAndView mv = new ModelAndView();
		mv.addObject("navigationBar", navigationBar);
		mv.setViewName("platform/sysEmploymentGrade/sysEmploymentGradeView");
		return mv;
	}

	/**
	 * 加载职务等级列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/loadEmploymentGradeList")
	@ResponseBody
	public Map<String, Object> loadEmploymentGradeList(SysEmploymentGradeBean empGrade) {
		logger.info("加载列表数据。。。。。。。。start");
		Map<String, Object> result = new HashMap<String, Object>();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);

		Map<String, Object> empGradeListMap = this.empGradeService
				.getEmploymentGradeList(empGrade, flexiGridPageInfo);
		List<SysEmploymentGradeBean> empGradeList = (List<SysEmploymentGradeBean>) empGradeListMap
				.get("empGradeList");
		int count = (Integer) empGradeListMap.get("total");

		result.put("rows", empGradeList);
		result.put("total", count);

		return result;
	}

	/**
	 * 初始化单位组织树
	 */
	@RequestMapping("/findOrganizationTree")
	@ResponseBody
	public Map<String, Object> findOrganizationLst(OrganizationBean organizationBean) throws Exception {
		List<OrganizationBean> organizationLst = organizationService.getOrganizationBeanByConditions("", "");
		String organizationLstJson = JsonUtil.toString(organizationLst);
		logger.info("json====="+organizationLstJson);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("organizationLstJson", organizationLstJson);
		return result;
	}

	/**
	 * 验证职务等级名称的唯一性
	 * @param empGrade
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkGradeName")
	@ResponseBody
	public boolean checkGradeName(SysEmploymentGradeBean empGrade){
		try {
			return empGradeService.checkEmpGradeByOrgID(empGrade);
		} catch (Exception e) {
			logger.error("验证名称异常："+e.getMessage(),empGrade.getGradeID());
			return false;
		}

	}

	/**
	 * 添加职务等级
	 * @param empGrade
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addEmploymentGrade")
	@ResponseBody
	public boolean addDepartment(SysEmploymentGradeBean empGrade) {
		logger.info("新增职务等级。。。start");
		logger.info("新增的职务ID为："+ empGrade.getGradeID() + "  职务名称为：" + empGrade.getGradeName());
		try {
			return empGradeService.addEmploymentGrade(empGrade);
		} catch (Exception e) {
			logger.error("新增异常："+e.getMessage(),empGrade.getGradeID());
			return false;
		}
	}

	/**
	 * 更新职务等级
	 * @param empGrade
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateEmploymentGrade")
	@ResponseBody
	public boolean updateDepartment(SysEmploymentGradeBean empGrade){
		System.out.println("ID====" + empGrade.getGradeID() + ",name======"
				+ empGrade.getGradeName());
		logger.info("更新职务等级。。。start");
		logger.info("更新的职务ID为："+ empGrade.getGradeID() + "  职务名称为：" + empGrade.getGradeName());
		try {
			return empGradeService.updateEmploymentGrade(empGrade);
		} catch (Exception e) {
			logger.error("更新异常："+e.getMessage());
			return false;
		}
	}

	/**
	 * 初始化编辑、查看页面
	 * @param empGrade
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findEmploymentGrade")
	@ResponseBody
	public SysEmploymentGradeBean findDepartment(SysEmploymentGradeBean empGrade)
			throws Exception {
		logger.info("初始化。。。。。。。start");
		List<SysEmploymentGradeBean> empGradeList = empGradeService
				.getEmpGradeByConditions("gradeID", empGrade.getGradeID() + "");
		String orgName = organizationService.getOrganizationBeanByConditions("organizationID", empGradeList.get(0).getOrganizationID()+"").get(0).getOrganizationName();
		empGradeList.get(0).setOrganizationName(orgName);
		return empGradeList.get(0);
	}
	
	/**
	 * 删除职务等级
	 * @param gradeID
	 * @param organizationID
	 * @return
	 */
	@RequestMapping("/delEmpGrade")
	@ResponseBody
	public boolean delEmpGrade(int gradeID, int organizationID){
		logger.info("单行删除。。。。。。start");
		logger.info("删除的职务ID："+gradeID+"  单位ID："+organizationID);
		int count = empGradeService.getEmploymentByGradeID(gradeID, organizationID);
		logger.info("验证职务是否被单位员工使用 "+count);
		if(count <= 0){
			try {
				return empGradeService.deleteEmplomentGrade(gradeID);
			} catch (Exception e) {
				logger.error("删除异常："+e.getMessage());
				return false;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除职务等级
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delEmpGrades")
	@ResponseBody
	public Map<String, Object> delEmpGrades(HttpServletRequest request){
		logger.info("批量删除。。。。。。start");
		boolean flag=false;
		boolean rs = false;
		String ids = request.getParameter("ids");
		String[] splitIds = ids.split(",");
		String gradeName = "";
		/* 能够被删的ID数组 */
		List<Integer> delEmpGradeId = new ArrayList<Integer>();
		/* 不能被删保留的ID数组 */
		List<Integer> reserveEmpGradeId = new ArrayList<Integer>();
		for (String strId : splitIds) {
			String[] deptIdAndOrgID = strId.split("_");
			for (int i = 1; i < deptIdAndOrgID.length; i++) {
				int gradeID = Integer.parseInt(deptIdAndOrgID[0]);
				int organizationID = Integer.parseInt(deptIdAndOrgID[1]);
				logger.info("验证职务是够被单位员工使用");
				int count = empGradeService.getEmploymentByGradeID(gradeID, organizationID);
				if(count > 0){
					reserveEmpGradeId.add(gradeID);
					logger.info("不能够被删而保留的Id：" + gradeID);
				}else{
					delEmpGradeId.add(gradeID);
					logger.info("能够被删的Id：" + gradeID);
				}
			}
		}
			logger.info("删除能够被删的职务。。。。。。。。start");
			 for (int i = 0; i < delEmpGradeId.size(); i++) {
				try {
					empGradeService.deleteEmplomentGrade(delEmpGradeId.get(i));
				} catch (Exception e) {
					logger.error("删除异常："+e.getMessage());
				}
			}
			 
			if (reserveEmpGradeId.size() > 0) {
				 flag = false;
				 String gName ="";
				 for (int i = 0; i < reserveEmpGradeId.size(); i++) {
					gName = empGradeService.getEmpGradeByConditions("gradeID", ""+reserveEmpGradeId.get(i)).get(0).getGradeName();
					gradeName = gradeName+gName+",";
				 }
				 gradeName = gradeName.substring(0, gradeName.length()-1);
				 logger.info("不能删除的职务等级名称："+gradeName);
			}else{
				flag = true;
			}
		Map<String, Object> result = new HashMap<String, Object>();
		gradeName = " 【 "+gradeName+" 】 ";
		result.put("flag", flag);
		result.put("gradeName", gradeName);
		return result;
	}
	
	/**
	 * 树定位
	 * @param organizationName
	 * @return
	 */
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public SysEmploymentGradeBean searchTreeNodes(String organizationName){
		logger.info("树定位。。。。。start");
		logger.info("定位的输入值为："+organizationName);
		return empGradeService.getEmpploymentByOrgName(organizationName);
		
	}
	
	/**
	 * 查询组织(单位)下的所有职务列表
	 * @return
	 */
	@RequestMapping("/querygrads")
	@ResponseBody
	public List<SysEmploymentGradeBean> queryGrads (Integer orgId) {
		if (orgId == null) {
			return new ArrayList<SysEmploymentGradeBean>();
		}
		try {
			return empGradeService.getEmpGradeByConditions("OrganizationID", orgId.toString());
		} catch (Exception e) {
			logger.error("查询组织(单位)下的所有职务列表失败：" + e);
			return new ArrayList<SysEmploymentGradeBean>();
		}
	}
}
