package com.fable.insightview.permission.controller;

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

import com.fable.insightview.platform.entity.SysRoleBean;
import com.fable.insightview.platform.entity.SysRoleMenusBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.service.ISysRoleService;

/**
 * 兴趣爱好
 * 
 * @author 汪朝
 * 
 */
@Controller
@RequestMapping("/permissionSysRole")
public class SysRoleController {
	private final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

	@Autowired
	ISysRoleService sysRoleService;
	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/addSysRoleMenu")
	@ResponseBody
	public boolean addSysRoleMenu(SysRoleMenusBean sysRoleMenusBean)
			throws Exception {
		return sysRoleService.addSysRoleMenu(sysRoleMenusBean);
	}

	@RequestMapping("/updateSysRole")
	@ResponseBody
	public boolean updateSysRole(SysRoleBean sysRoleBean) throws Exception {
		return sysRoleService.updateSysRole(sysRoleBean);
	}

	@RequestMapping("/findSysRole")
	@ResponseBody
	public SysRoleBean findSysRole(SysRoleBean sysRoleBean) throws Exception {
		List<SysRoleBean> roleLst = sysRoleService.getSysRoleBeanByConditions(
				"roleId", sysRoleBean.getRoleId() + "");
		return roleLst.get(0);

	}

	@RequestMapping("/toSysRoleList")
	public ModelAndView toSysRoleList(String navigationBar) {
		ModelAndView mv = new ModelAndView("permission/sysrole_list");
		mv.addObject("navigationBar", navigationBar);
		return mv ;
	}

	@RequestMapping("/addSysRole")
	@ResponseBody
	public boolean addSysRole(SysRoleBean SysRoleBean) throws Exception {
		return sysRoleService.addSysRole(SysRoleBean);
	}

	@RequestMapping("/delSysRole")
	@ResponseBody
	public boolean delSysRole(SysRoleBean sysRoleBean) throws Exception {
		logger.info("删除角色。。。start");
		boolean checkRS =sysRoleService.checkBeforeDel(sysRoleBean);
		if(checkRS){
			return sysRoleService.delSysRoleById(sysRoleBean);
		}else{
			return false;
		}
	}

	@RequestMapping("/listSysRole")
	@ResponseBody
	public Map<String, Object> listSysRole(SysRoleBean sysRoleBean)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);

		List<SysRoleBean> s = sysRoleService.getSysRoleByConditions(
				sysRoleBean, flexiGridPageInfo);
		int total = sysRoleService.getTotalCount(sysRoleBean);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", s);
		return result;
	}
	
	/**
	 * 验证角色名称的唯一性
	 * 
	 * @author hanl
	 */
	@RequestMapping("/checkRoleName")
	@ResponseBody
	public boolean checkRoleName(SysRoleBean sysRoleBean) throws Exception {
		return sysRoleService.checkRoleName(sysRoleBean);
	}
	
	/**
	 * 批量删除
	 * 
	 * @author hanl
	 */
	@RequestMapping("/delSysRoles")
	@ResponseBody
	public Map<String, Object> delSysRoles(HttpServletRequest request) throws Exception {
		logger.info("strat........批量删除");
		boolean flag = false ; 
		boolean isUse = true ; 
		boolean isSystem = true;
		String roleIds = request.getParameter("roleIds");
		String[] splitIds = roleIds.split(",");
		String roleName = "";
		String sysRoleName = "" ;
		
		/* 能够被删的ID数组 */
		List<SysRoleBean> delRole = new ArrayList<SysRoleBean>();
		/* 不能被删保留的ID数组 */
		List<SysRoleBean> reserveRole = new ArrayList<SysRoleBean>();
		for (String strId : splitIds) {
			int roleId = Integer.parseInt(strId);
			
			//非系统自定义角色
			if(roleId>=10000){
				SysRoleBean role = new SysRoleBean();
				role.setRoleId(roleId);
				boolean checkRS =sysRoleService.checkBeforeDel(role);
				if(checkRS){
					delRole.add(role);
				}else{
					reserveRole.add(role);
				}
			}else{
				isSystem = false;
				sysRoleName = sysRoleName+sysRoleService.getSysRoleBeanByConditions("roleId",strId).get(0).getRoleName()+",";
			}
		}
		
		//系统自定义角色名称
		if (!"".equals(sysRoleName)) {
			sysRoleName = sysRoleName.substring(0, sysRoleName.lastIndexOf(","));
		}
		sysRoleName = "【 " + sysRoleName + "】";
		
		//删除可以删除的角色
		try {
			for (int i = 0; i < delRole.size(); i++) {
				sysRoleService.delSysRoleById(delRole.get(i));
			}
			flag = true;
		} catch (Exception e) {
			flag= false;
		}
		
		//获得正在使用的角色
		if (reserveRole.size() > 0) {
			isUse = false;
			 String rName ="";
			 for (int i = 0; i < reserveRole.size(); i++) {
				rName = sysRoleService.getSysRoleBeanByConditions("roleId",reserveRole.get(i).getRoleId()+"").get(0).getRoleName();
				roleName = roleName+rName+",";
			 }
			 roleName = roleName.substring(0, roleName.lastIndexOf(","));
			 roleName = " 【 "+roleName+" 】 ";
		}else{
			isUse = true;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		logger.info("flag=-=="+flag);
		result.put("flag", flag);
		result.put("isUse", isUse);
		result.put("roleName", roleName);
		result.put("isSystem", isSystem);
		result.put("sysRoleName", sysRoleName);
		return result;
	}
}
