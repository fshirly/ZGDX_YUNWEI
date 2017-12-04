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

import com.fable.insightview.permission.form.SysMenuModuleForm;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.entity.SysMenuModuleBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.service.ISysMenuModuleService;
import com.fable.insightview.platform.service.ISysRoleService;

/**
 * 系统功能菜单
 * 
 * @author 武林
 * 
 */
@Controller
@RequestMapping("/permissionSysMenuModule")
public class SysMenuModuleController {

	@Autowired
	ISysMenuModuleService sysMenuModuleService;
	@Autowired
	ISysRoleService sysRoleService;
	@Autowired
	private HttpServletRequest request;
	private final Logger logger = LoggerFactory.getLogger(SysMenuModuleController.class);
	@RequestMapping("/updateSysMenu")
	@ResponseBody
	public boolean updateSysMenu(SysMenuModuleBean sysMenuModule)
			throws Exception {
		return sysMenuModuleService.updateSysUser(sysMenuModule);
	}

	@RequestMapping("/findSysMenuTreeVal")
	@ResponseBody
	public Map<String, Object> findSysMenuTreeVal(
			SysMenuModuleBean sysMenuModule) throws Exception {
		List<SysMenuModuleBean> menuLst = sysMenuModuleService
				.getSysMenuTreeVal();
		String menuRoleId = sysRoleService.queryMenuIdByRole(sysMenuModule);
		String menuLstJson = JsonUtil.toString(menuLst);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		result.put("menuRoleId", menuRoleId);
		return result;
	}
	

	/**
	 * 获得角色管理的菜单树
	 * @param sysMenuModule
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSysMenuTreeVal")
	@ResponseBody
	public Map<String, Object> getSysMenuTreeVal(
			SysMenuModuleBean sysMenuModule) throws Exception {
		List<SysMenuModuleBean> menuLst = sysMenuModuleService
				.getSysMenuTreeVal();
		String menuRoleId = sysRoleService.queryMenuIdByRole(sysMenuModule);
		if(menuRoleId!=""){
			if(menuRoleId.substring(menuRoleId.length()-1, menuRoleId.length()).equals(",")){
				menuRoleId = menuRoleId.substring(0,menuRoleId.length()-1);
			}
		}
		String menuLstJson = JsonUtil.toString(menuLst);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		result.put("menuRoleId", menuRoleId);
		return result;
	}
	
	@RequestMapping("/getParentMenus")
	@ResponseBody
	public String getParentMenus(
			SysMenuModuleBean sysMenuModule) throws Exception {
		return sysMenuModuleService.getparentMenuIDsByMenuID(sysMenuModule.getMenuId());
	}

	@RequestMapping("/findSysMenu")
	@ResponseBody
	public SysMenuModuleBean findSysUser(SysMenuModuleBean sysMenuModule)
			throws Exception {
		List<SysMenuModuleBean> menuLst = sysMenuModuleService
				.getSysMenuByConditions("menuId", sysMenuModule.getMenuId()
						+ "");
		return menuLst.get(0);

	}

	@RequestMapping("/findAllSysMenu")
	@ResponseBody
	public SysMenuModuleBean findAllSysMenu(SysMenuModuleBean sysMenuModule)
			throws Exception {
		List<SysMenuModuleBean> menuLst = sysMenuModuleService
				.getSysMenuByConditions("", "");
		return menuLst.get(0);

	}

	@RequestMapping("/toSysMenuModuleList")
	public ModelAndView toPermissionDepartmentList(String navigationBar) {
		ModelAndView mv = new ModelAndView("permission/sysmenumodule_list");
		mv.addObject("navigationBar", navigationBar);
		return mv ;
	}

	@RequestMapping("/addSysMenuModule")
	@ResponseBody
	public boolean addSysMenuModule(SysMenuModuleBean sysMenuModule)
			throws Exception {
		return sysMenuModuleService.addSysMenuModule(sysMenuModule);
	}

	@RequestMapping("/delSysMenuModule")
	@ResponseBody
	public boolean delSysMenuModule(int menuId)
			throws Exception {
		
		boolean flag=false;
		boolean isleaf=sysMenuModuleService.getSysRoleMenusByMenuID(menuId);
		if(isleaf==true){
			flag=sysMenuModuleService.delSysMenuModuleById(menuId);
		}
		return flag;
		
	}
	/**
	 * 批量删除
	 * @param sysMenuModule
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/batchdelSysMenuModule")
	@ResponseBody
	public boolean batchdelSysMenuModule(SysMenuModuleBean sysMenuModule)
			throws Exception {
		boolean flag=false;
		int i=0;
		String ids = request.getParameter("ids");
		logger.info("被删除菜单ID = "+ids);
		String[] splitIds = ids.split(",");
		for (String strId : splitIds) {
			Integer id = Integer.parseInt(strId);
			boolean isleaf=sysMenuModuleService.getSysRoleMenusByMenuID(id);
			if(isleaf==true){
				i++;
			}
		}
		if(i==splitIds.length){
			for (String strId : splitIds) {
				Integer id = Integer.parseInt(strId);
				flag=sysMenuModuleService.delSysMenuModuleById(id);
			}
		}
		return flag;
	
	}

	/**
	 * 菜单列表
	 * 
	 * @param sysMenuModuleForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listSysMenuModule")
	@ResponseBody
	public Map<String, Object> listSysMenuModule(
			SysMenuModuleForm sysMenuModuleForm) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);

		// 过滤条件值
		SysMenuModuleBean sysMenuModule = new SysMenuModuleBean();
		sysMenuModule.setMenuNameFilter(sysMenuModuleForm.getMenuNameFilter());
		sysMenuModule
				.setMenuLevelFilter(sysMenuModuleForm.getMenuLevelFilter());
		sysMenuModule.setParentMenuIDFilter(sysMenuModuleForm
				.getParentMenuIDFilter());
		sysMenuModule.setParID(sysMenuModuleForm.getParID());

		// 获取数据集合
		List<SysMenuModuleBean> menuLst = sysMenuModuleService
				.getSysMenuModuleByConditions(sysMenuModule, flexiGridPageInfo);
		// 获取总记录数
		int total = sysMenuModuleService.getTotalCount(sysMenuModule);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", menuLst);
		return result;
	}
	/**
	 * 查找节点ID
	 * @param menuName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public SysMenuModuleBean searchTreeNodes(String menuName)throws Exception{
		return sysMenuModuleService.getIdBymenuName(menuName);
	}
}