package com.fable.insightview.permission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.service.AdapterDelDeptService;
import com.fable.insightview.platform.common.util.CommonUtil;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.common.util.StringUtil;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.OrgDeptProviderTreeBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.service.IDepartmentService;
import com.fable.insightview.platform.service.IOrganizationService;

/**
 * 部门管理
 * 
 * @author 汪朝
 * 
 */
@Controller
@RequestMapping("/permissionDepartment")
public class DepartmentController {
	private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	@Autowired
	IDepartmentService departmentService;
	@Autowired
	IOrganizationService  organizationService;
	
	@Autowired
	private HttpServletRequest request;
	
	
	@RequestMapping("/initTree")
	@ResponseBody
	public String initTree() throws Exception{
		List<Map<String,String>> listMaps= new ArrayList<Map<String,String>>();
		logger.info("获得组织数据");
		List<OrganizationBean> orgList = organizationService.getOrganizationTreeVal();
		List<Integer> orgIds = departmentService.getExistIds(true);
		for (OrganizationBean org : orgList) {
			Map<String,String> map = new HashMap<String,String>();
			int orginId = org.getOrganizationID();
			map.put("id", orginId+"");
			map.put("isOrg", "true");
			map.put("isChecked", "true");
			map.put("text", org.getOrganizationName());
			if (orgIds.contains(orginId)) {
				map.put("state", "closed");
				map.put("iconCls", "icon-folder");
			} else {
				map.put("state", "open");
				map.put("iconCls", "icon-file");
			}
			listMaps.add(map);
		}
		return JsonUtil.listMap2Json(listMaps);
	}
	
	@RequestMapping("/initDepartment")
	@ResponseBody
	public String initDepartment(String pid,String orgId) throws Exception{
		List<Map<String,String>> listMaps= new ArrayList<Map<String,String>>();
		List<DepartmentBean> deptList = null;
		if (StringUtil.isNotEmpty(orgId)) {
			deptList = departmentService.getDepartments("organizationBean", orgId);
		}
		if (StringUtil.isNotEmpty(pid)) {
			deptList = departmentService.getDepartmentByConditions("parentDeptID",pid);
		}
		List<Integer> parendIds = departmentService.getExistIds(false);
		for (DepartmentBean dept : deptList) {
			Map<String,String> map = new HashMap<String,String>();
			int depId = dept.getDeptId();
			map.put("id", depId+"");
			map.put("iconCls", "icon-ok");
			map.put("text", dept.getDeptName());
			if (parendIds.contains(depId)) {
				map.put("state", "closed");
				map.put("iconCls", "icon-folder");
			} else {
				map.put("state", "open");
				map.put("iconCls", "icon-file");
			}
			listMaps.add(map);
		}
		return JsonUtil.listMap2Json(listMaps);
	}
	
	/**
	 * 初始化单位部门树
	 */
	@RequestMapping("/findOrgAndDeptVal")
	@ResponseBody
	public Map<String, Object> findOrgAndDept() throws Exception{
		logger.info("初始化单位部门树........start");
		List<OrgDeptProviderTreeBean> TreeMenuList = new ArrayList<OrgDeptProviderTreeBean>();
		logger.info("获得单位数据");
		List<OrganizationBean> orgList = organizationService.getOrganizationTreeVal();
		for (int i = 0; i < orgList.size(); i++) {
			OrgDeptProviderTreeBean odp = new OrgDeptProviderTreeBean();
			odp.setId("O"+orgList.get(i).getOrganizationID());
			logger.info("单位组织拼接后的ID ："+odp.getId());
			odp.setParentId("0");
			odp.setName(orgList.get(i).getOrganizationName());
			TreeMenuList.add(odp);
		}
		logger.info("获得部门数据");
		List<DepartmentBean> deptList = departmentService.getDepartmentTreeVal();
		for (int i = 0; i < deptList.size(); i++) {
			OrgDeptProviderTreeBean odp = new OrgDeptProviderTreeBean();
			odp.setId("D"+deptList.get(i).getDeptId());
			odp.setName(deptList.get(i).getDeptName());
			if(deptList.get(i).getParentDeptID() == 0){
				odp.setParentId("O"+deptList.get(i).getOrganizationBean().getOrganizationID());
			}else{
				odp.setParentId("D"+deptList.get(i).getParentDeptID());
			}
			TreeMenuList.add(odp);
		}
		String menuLstJson = JsonUtil.toString(TreeMenuList);
		logger.info("menuLstJson======="+menuLstJson);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}
	
	
	/**
	 * 初始化本单位部门树
	 */
	@RequestMapping("/findSelfOrgAndDept")
	@ResponseBody
	public Map<String, Object> findSelfOrgAndDept(String deptId) throws Exception{
		logger.info("初始化单位部门树........start");
		List<OrgDeptProviderTreeBean> TreeMenuList = new ArrayList<OrgDeptProviderTreeBean>();
		logger.info("获得单位数据");
		List<OrganizationBean> orgList = organizationService.getOrganizationTree(deptId);
		for (int i = 0; i < orgList.size(); i++) {
			OrgDeptProviderTreeBean odp = new OrgDeptProviderTreeBean();
			odp.setId("O"+orgList.get(i).getOrganizationID());
			logger.info("单位组织拼接后的ID ："+odp.getId());
			odp.setParentId("0");
			odp.setName(orgList.get(i).getOrganizationName());
			TreeMenuList.add(odp);
		}
		logger.info("获得部门数据");
		List<DepartmentBean> deptList = departmentService.getDepartmentTreeSelf(deptId);
		for (int i = 0; i < deptList.size(); i++) {
			OrgDeptProviderTreeBean odp = new OrgDeptProviderTreeBean();
			odp.setId("D"+deptList.get(i).getDeptId());
			odp.setName(deptList.get(i).getDeptName());
			if(deptList.get(i).getParentDeptID() == 0){
				if(null != deptList.get(i).getOrganizationID()){
					odp.setParentId("O"+deptList.get(i).getOrganizationID());
				}
			}else{
				odp.setParentId("D"+deptList.get(i).getParentDeptID());
			}
			TreeMenuList.add(odp);
		}
		String menuLstJson = JsonUtil.toString(TreeMenuList);
		logger.info("menuLstJson======="+menuLstJson);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}
	
	/**
	 * 初始化部门树
	 * @param departmentBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDeptLst")
	@ResponseBody
	public Map<String, Object> findDepartmentLst(DepartmentBean departmentBean)
			throws Exception {
		logger.info("初始化部门树........start");
		List<DepartmentBean> deptLst = departmentService
				.getDepartmentBeanByConditions("", "");
		String deptLstJson = JsonUtil.toString(deptLst);
		logger.info("deptLstJson======="+deptLstJson);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("deptLstJson", deptLstJson);
		return result;

	}

	/**
	 * 初始化查看编辑页面
	 * @param departmentBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDepartment")
	@ResponseBody
	public DepartmentBean findDepartment(DepartmentBean departmentBean)	throws Exception {
		logger.info("初始化查看编辑页面........start");
		logger.info("初始化的Id为："+departmentBean.getDeptId());
		List<DepartmentBean> deptLst = departmentService
				.getDepartmentBeanByConditions("deptId",
						departmentBean.getDeptId() + "");
		String organizationName = deptLst.get(0).getOrganizationBean().getOrganizationName();
		deptLst.get(0).setOrganizationName(organizationName);
		return deptLst.get(0);
	}
	
	/**
	 * 根据userId查询单位组织
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author maow
	 */
	@RequestMapping("/findDepartmentByUserID")
	@ResponseBody
	public DepartmentBean findDepartmentByUserID(Integer id) throws Exception {
		List<DepartmentBean> deptLst = departmentService.getDepartmentByUserId(id);
		if (!deptLst.isEmpty() && deptLst.size() > 0) {
			return deptLst.get(0);
		}
		return null;
	}
	
	/**
	 * 更新部门
	 * @param departmentBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateDepartment")
	@ResponseBody
	public boolean updateDepartment(DepartmentBean departmentBean){
		logger.info("strat..........更新部门");
		boolean flag = false;
		try {
			flag = departmentService.updateSysUser(departmentBean);
		} catch (Exception e) {
			logger.error("更新异常："+e.getMessage());
			flag = false;
		}
		return flag;
	}

	/**
	 * 部门管理页面弹出
	 * @return
	 */
	@RequestMapping("/toDepartmentList")
	public ModelAndView toPermissionDepartmentList(String navigationBar) {
		logger.info("strat..........进入部门管理管理页面");
		ModelAndView mv = new ModelAndView("permission/department_list");
		mv.addObject("navigationBar", navigationBar);
		return mv ;
	}

	/**
	 * 新增部门
	 * @param DepartmentBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addDepartment")
	@ResponseBody
	public boolean addDepartment(DepartmentBean DepartmentBean) {
		logger.info("strat..........新增部门");
		boolean flag = false;
		try {
			flag = departmentService.addDepartment(DepartmentBean);
		} catch (Exception e) {
			logger.error("新增异常："+e.getMessage(),DepartmentBean.getDeptId());
			flag =  false;
		}
		return flag;
	}

	/**
	 * 批量删除
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delDepartments")
	@ResponseBody
	public Map<String, Object> delDepartments(HttpServletRequest request){
		logger.info("批量删除部门。。。start");
		boolean flag=false;
		String deptIds = request.getParameter("deptIds");
		String[] splitIds = deptIds.split(",");
		String deptName = "";
		/* 能够被删的ID数组 */
		List<DepartmentBean> delDept = new ArrayList<DepartmentBean>();
		/* 不能被删保留的ID数组 */
		List<DepartmentBean> reserveDept = new ArrayList<DepartmentBean>();
		for (String strId : splitIds) {
			Integer deptId =  Integer.parseInt(strId);
			DepartmentBean departmentBean = new DepartmentBean();
			departmentBean.setDeptId(deptId);
			boolean checkRs = departmentService.checkBeforeDel(departmentBean);
			if(checkRs){
				delDept.add(departmentBean);
				logger.info("能够被删的Id：" + departmentBean.getDeptId());
			}else{
				reserveDept.add(departmentBean);
				logger.info("不能够被删而保留的Id：" + departmentBean.getDeptId());
			}
		}
		logger.info("删除能够被删的职务。。。。。。。。start");
		for (int i = 0; i < delDept.size(); i++) {
			try {
				departmentService.delDepartmentById(delDept.get(i));
			} catch (Exception e) {
				logger.error("删除异常："+e.getMessage());
			}
		}
		
		if (reserveDept.size() > 0) {
			 flag = false;
			 String dName ="";
			 for (int i = 0; i < reserveDept.size(); i++) {
				dName = departmentService.getDepartmentBeanByConditions("deptId", reserveDept.get(i).getDeptId()+"").get(0).getDeptName();
				deptName = deptName+dName+",";
			 }
			 deptName = deptName.substring(0, deptName.lastIndexOf(","));
			 logger.info("不能删除的职务等级名称："+deptName);
		}else{
			flag = true;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		deptName = " 【 "+deptName+" 】 ";
		result.put("flag", flag);
		result.put("deptName", deptName);
		return result;
	}
	
	/**
	 * 删除部门
	 * @param departmentBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delDepartment")
	@ResponseBody
	public Map<String, Object> delDepartment(DepartmentBean departmentBean){
		//Added By Zhang Ya at 2014年12月11日 下午5:26:56
		// Begin .... 
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, AdapterDelDeptService> map = CommonUtil.getOtherSysyemServiceImpl(AdapterDelDeptService.class);
		if(map != null && !map.isEmpty()){
			for(AdapterDelDeptService adapter : map.values()){
				if(!StringUtils.isEmpty(adapter.isDeptIdUsed(departmentBean.getDeptId()))){
					result.put("result", adapter.isDeptIdUsed(departmentBean.getDeptId()));
				} 
		// ...... End
				else{
					boolean checkRS = departmentService.checkBeforeDel(departmentBean);
					if(checkRS){
						try {
							departmentService.delDepartmentById(departmentBean);
							result.put("result", "DelSuccess");
						} catch (Exception e) {
							result.put("result", "删除部门异常!");
						}
					} else{
						result.put("result", "部门有子部门或员工，删除失败！");
					}
				}
			}
		} else {
			boolean checkRS = departmentService.checkBeforeDel(departmentBean);
			if(checkRS){
				try {
					departmentService.delDepartmentById(departmentBean);
					result.put("result", "DelSuccess");
				} catch (Exception e) {
					result.put("result", "删除部门异常!");
				}
			}else{
				result.put("result", "部门有子部门或员工或者被子网使用，删除失败！");
			}
		}
		return result;
	}

	/**
	 * 加载部门列表
	 * @param departmentBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listDepartment")
	@ResponseBody
	public Map<String, Object> listDepartment(DepartmentBean departmentBean)throws Exception {
		logger.info("加载列表数据。。。。。。。。start");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		List<DepartmentBean> s = departmentService.getDepartmentByConditions(
				departmentBean, flexiGridPageInfo);
		// 获取总记录数
		int total = departmentService.getTotalCount(departmentBean);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", s);
		return result;
	}
	
	/**
	 * 验证部门名称
	 * @param departmentBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkDeptName")
	@ResponseBody
	public boolean checkDeptName(DepartmentBean departmentBean){
		logger.info("验证部门名称。。。。。。。。start");
		boolean flag= false; 
		try {
			flag = departmentService.checkDeptName(departmentBean);
		} catch (Exception e) {
			logger.error("验证部门异常："+e.getMessage(),departmentBean);
			flag =  false;
		}
		return flag;
	}
	
	/**
	 * 验证上级部门与所属组织是否匹配
	 * @param departmentBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkOrgAndParentDept")
	@ResponseBody
	public boolean checkOrgAndParentDept(DepartmentBean departmentBean) {
		logger.info("验证上级部门与所属组织是否匹配。。。。。。。。start");
		try {
			return departmentService.getDeptByOrgIDAndParentDeptID(departmentBean);
		} catch (Exception e) {
			logger.error("验证名称异常："+e.getMessage());
			return false;
		}
	}
	
	/**
	 * 初始化部门领导树
	 * @param departmentBean
	 * @return
	 */
	@RequestMapping("/findHeadList")
	@ResponseBody
	 public Map<String, Object> findHead(DepartmentBean departmentBean){
		logger.info("初始化部门领导树。。。。。。。。start");
		List<SysEmploymentBean> empList = departmentService.getEmploymentListByOrgID(departmentBean);
		String headLstJson = JsonUtil.toString(empList);
		logger.info("headLstJson==="+headLstJson);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("headLstJson", headLstJson);
		return result;
	}
	
	/**
	 * 树定位
	 * @param deptName
	 * @return
	 */
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public DepartmentBean searchTreeNodes(String deptName){
		logger.info("树定位。。。。。。。。start");
		return departmentService.getDepartmentByDeptName(deptName);
		
	}
	
	@RequestMapping("/checkDeptCode")
	@ResponseBody
	public boolean checkDeptCode(HttpServletRequest request,DepartmentBean departmentBean){
		String flag = request.getParameter("flag");
		return departmentService.checkDeptCode(flag, departmentBean);
		
	}


	@RequestMapping("/findDeptByOrgId")
	@ResponseBody
	 public List<DepartmentBean> findDeptByOrgId(HttpServletRequest request){
		String organizationId = request.getParameter("organizationId");
		if(organizationId ==null || "".equals(organizationId)){
			SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
			Integer orgId1 = departmentService.getOrgByUserId(Integer.parseInt(sysUser.getId().toString()));
			if(orgId1!=-1){
				organizationId = String.valueOf(orgId1);
			}
		}
		if (organizationId == null || "".equals(organizationId)) {
			return new ArrayList<DepartmentBean>();
		}
		List<DepartmentBean> deptLst =  departmentService
				.getDepartmentByOrgId(Integer.parseInt(organizationId));
		return deptLst;
	}
	
	@RequestMapping("/findDeptList")
	@ResponseBody
	 public List<DepartmentBean> findDeptList(HttpServletRequest request){
		List<DepartmentBean> deptLst = departmentService
				.getDepartmentBeanByConditions(null, null);
		return deptLst;
	}
}
