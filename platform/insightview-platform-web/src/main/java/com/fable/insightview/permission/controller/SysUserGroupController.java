package com.fable.insightview.permission.controller;

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

import com.fable.insightview.permission.form.SysUserGroupForm;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.entity.SysRoleBean;
import com.fable.insightview.platform.entity.SysUserGroupBean;
import com.fable.insightview.platform.entity.SysUserGroupRolesBean;
import com.fable.insightview.platform.entity.SysUserInGroupsBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.service.ISysRoleService;
import com.fable.insightview.platform.service.ISysUserGroupService;
import com.fable.insightview.platform.service.ISysUserInGroupsService;

/**
 * 用户组
 * 
 * @author 汪朝
 * 
 */
@Controller
@RequestMapping("/permissionSysUserGroup")
public class SysUserGroupController {
	private final Logger logger = LoggerFactory.getLogger(SysUserGroupController.class);
	@Autowired
	ISysUserGroupService sysUserGroupService;
	@Autowired
	ISysRoleService sysRoleService;
	@Autowired
	ISysUserInGroupsService sysUserInGroupsService;
	@Autowired
	private HttpServletRequest request;

	/*
	 * 添加用户
	 */
	@RequestMapping("/addSysUserInGroups")
	@ResponseBody
	public String addSysUserInGroups(SysUserInGroupsBean sysUserInGroupsBean) {
		logger.info("添加用户。。。。。。。");
		logger.info("用户ids:"+sysUserInGroupsBean.getUserIds()+"用户组id==============="+sysUserInGroupsBean.getGroupId());
		boolean rtlVal = sysUserInGroupsService
				.addSysUserInGroupsBean(sysUserInGroupsBean);
		return rtlVal + "";
	}

	/**
	 * 将用户从用户组中移除
	 */
	@RequestMapping("/removeSysUserInGroups")
	@ResponseBody
	public String removeSysUserInGroups(SysUserInGroupsBean sysUserInGroupsBean) {
		logger.info("移除用户。。。。。。。");
		logger.info("用户ids:"+sysUserInGroupsBean.getUserIds()+"用户组id==============="+sysUserInGroupsBean.getGroupId());
		boolean rtlVal = sysUserInGroupsService
				.removeSysUserInGroupsBean(sysUserInGroupsBean);
		return rtlVal + "";
	}

	
	
	@RequestMapping("/updateGroupRole")
	@ResponseBody
	public boolean updateGroupRole(SysUserGroupRolesBean groupRole)
			throws Exception {
		SysUserGroupRolesBean groupRoleTemp = new SysUserGroupRolesBean();
		groupRoleTemp.setGroupID(groupRole.getGroupID());
		logger.info("删除本身已有的角色");
		boolean delInfo = sysUserGroupService.delGroupRoleByCond(groupRole);
		logger.info("插入新配置的角色");
		String roleIds = groupRole.getRoleIdArr();
		if(roleIds!=""){
			boolean addInfo = sysUserGroupService.insertGroupRole(groupRole);
		}
		return true;

	}

	@RequestMapping("/checkUserGroup")
	@ResponseBody
	public boolean checkUserGroup(SysUserGroupBean groupBean) throws Exception {
		return sysUserGroupService.checkUserGroupByConditions(groupBean);
	}

	/*
	 * 加载角色
	 */
	@RequestMapping("/findGroupRole")
	@ResponseBody
	public Map<String, Object> findGroupRole(SysUserGroupRolesBean groupRole)
			throws Exception {
		List<SysUserGroupRolesBean> groupRoleLstTemp = sysUserGroupService
				.getSysUserGroupRole("groupId", groupRole.getGroupID() + "");
		List<SysRoleBean> roleLst = sysRoleService.getSysRoleBeanByConditions(
				"roleId", "", groupRoleLstTemp);
		String roleLstJson = JsonUtil.toString(roleLst);

		List<SysRoleBean> groupRoleLst = sysRoleService
				.getSysRoleBeanByConditions("roleId", "-1", groupRoleLstTemp);

		String groupRoleLstJson = JsonUtil.toString(groupRoleLst);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("roleLstJson", roleLstJson);
		/*已有的角色*/
		result.put("groupRoleLstJson", groupRoleLstJson);
		return result;

	}

	@RequestMapping("/findSysUserGroup")
	@ResponseBody
	public SysUserGroupBean findSysUserGroup(SysUserGroupBean sysUserGroupBean)
			throws Exception {
		List<SysUserGroupBean> userGroupLst = sysUserGroupService
				.getSysUserGroupByConditions("groupID",
						sysUserGroupBean.getGroupID() + "");
		return userGroupLst.get(0);

	}

	@RequestMapping("/updateSysUserGroup")
	@ResponseBody
	public boolean updateSysUserGroup(SysUserGroupBean sysUserGroupBean)
			throws Exception {
		return sysUserGroupService.updateSysUserGroup(sysUserGroupBean);
	}

	@RequestMapping("/toSysUserGroupList")
	public ModelAndView toSysUserGroupList(String navigationBar) {
		ModelAndView mv = new ModelAndView("permission/sysusergroup_list");
		mv.addObject("navigationBar", navigationBar);
		return mv ;
	}

	@RequestMapping("/addSysUserGroup")
	@ResponseBody
	public boolean addSysUserGroup(SysUserGroupBean sysUserGroupBean)
			throws Exception {
		logger.info("新增用户组。。。。。。。start");
		return sysUserGroupService.addSysUserGroup(sysUserGroupBean);
	}

	@RequestMapping("/delSysUserGroup")
	@ResponseBody
	public boolean delSysUserGroup(SysUserGroupForm sysUserGroupForm)
			throws Exception {
		SysUserGroupBean sysUserGroupBean = new SysUserGroupBean();
		sysUserGroupBean.setGroupID(sysUserGroupForm.getGroupID());
		return sysUserGroupService.delSysUserGroupById(sysUserGroupBean);
	}
	
	/**
	 * 删除正在被使用的用户组
	 */
	@RequestMapping("/doAfterDel")
	@ResponseBody
	public boolean doAfterDel(SysUserGroupForm sysUserGroupForm)
			throws Exception {
		SysUserGroupBean sysUserGroupBean = new SysUserGroupBean();
		sysUserGroupBean.setGroupID(sysUserGroupForm.getGroupID());
		return sysUserGroupService.delGroupAndReleationById(sysUserGroupBean);
	}
	
	@RequestMapping("/delSysUserGroups")
	@ResponseBody
	public Map<String, Object> delSysUserGroups(HttpServletRequest request) throws Exception{
		boolean flag = false;
		boolean isUse = true ; 
		boolean isSystem = true;
		boolean rs = false;
		String groupName = "";
		String sysGroupName = "" ;
		String reserveIds = "";
		String delIds = "";
		String groupIds = request.getParameter("groupIds");
		String[] spilitIds = groupIds.split(",");
		for (String splitId : spilitIds) {
			Integer groupId = Integer.parseInt(splitId);
			
			//非系统自定义用户组
			if(groupId >= 10000){
				SysUserGroupBean sysUserGroupBean = new SysUserGroupBean();
				sysUserGroupBean.setGroupID(groupId);
				rs = sysUserGroupService.delSysUserGroupById(sysUserGroupBean);
				String gName = "";
				if(rs == false){
					gName = sysUserGroupService.getSysUserGroupByConditions("groupID", groupId+"").get(0).getGroupName();
					groupName = groupName+gName+",";
					reserveIds = reserveIds +groupId+",";
				}else{
					delIds = delIds + +groupId+",";
				}
			}else{
				isSystem = false;
				sysGroupName = sysGroupName+sysUserGroupService.getSysUserGroupByConditions("groupID", groupId+"").get(0).getGroupName()+",";
			}
		}
		
		//系统自定义用户组名称
		if (!"".equals(sysGroupName)) {
			sysGroupName = sysGroupName.substring(0, sysGroupName.lastIndexOf(","));
		}
		sysGroupName = "【 " + sysGroupName + "】";
		
		//删除可以删除的角色
		try {
			sysUserGroupService.delGroupAndReleationByIds(delIds);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		
		//获得正在使用的
		Map<String, Object> result = new HashMap<String, Object>();
		if(groupName.length() > 0){
			groupName = groupName.substring(0, groupName.lastIndexOf(","));
			reserveIds = reserveIds.substring(0, reserveIds.lastIndexOf(","));
			isUse = false;
		}else{
			isUse = true;
		}
		groupName = " 【 "+groupName+" 】 ";
		result.put("flag", flag);
		result.put("groupName", groupName);
		result.put("reserveIds", reserveIds);
		result.put("isUse", isUse);
		result.put("isSystem", isSystem);
		result.put("sysGroupName", sysGroupName);
		return result;
		
	}

	/**
	 * 批量删除正在被使用的用户组
	 */
	@RequestMapping("/doAfterBatchDel")
	@ResponseBody
	public boolean doAfterBatchDel(HttpServletRequest request)
			throws Exception {
		String groupIDs = request.getParameter("groupIDs");
		return sysUserGroupService.delGroupAndReleationByIds(groupIDs);
	}
	
	/*
	 * 页面加载表格数据
	 */
	@RequestMapping("/listSysUserGroup")
	@ResponseBody
	public Map<String, Object> listSysUserGroup(
			SysUserGroupBean sysUserGroupBean) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);

		List<SysUserGroupBean> s = sysUserGroupService
				.getSysUserGroupByConditions(sysUserGroupBean,
						flexiGridPageInfo);

		// 获取总记录数
		int total = sysUserGroupService.getTotalCount(sysUserGroupBean);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", s);
		return result;
	}
	
	/**
	 * 打开详情页面
	 * @param sysUserForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowDetail")
	@ResponseBody
	public ModelAndView toShowDetail(HttpServletRequest request){
		String groupID=request.getParameter("groupID");
		request.setAttribute("groupID", groupID);
		return new ModelAndView("permission/sysusergroup_detail");
	}
	
	/**
	 * 打开配置用户页面
	 * @param sysUserForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowUserConfig")
	@ResponseBody
	public ModelAndView toShowUserConfig(HttpServletRequest request){
		String groupID=request.getParameter("groupID");
		request.setAttribute("groupID", groupID);
		String organizationID=request.getParameter("organizationID");
		request.setAttribute("organizationID", organizationID);
		return new ModelAndView("permission/sysusergroup_userconfig");
	}
	
	@RequestMapping("toDivAllotRolePop")
	public ModelAndView toDivAllotRolePop(Integer groupID){
		ModelAndView mv = new ModelAndView("permission/sysusergroup_role");
		mv.addObject("groupID", groupID);
		return mv;
	}
	
}
