package com.fable.insightview.platform.positionmanagement.controller;

//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
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

import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.positionManagement.Service.PositionManagementService;
import com.fable.insightview.platform.positionManagement.enitity.PositionManagement;
import com.fable.insightview.platform.positionManagement.enitity.PostUserManage;
/**
 * 岗位管理
 * @author zhaoyp
 * @version
 */

@Controller
@RequestMapping("/platform")
public class PositionManagementController {	
	private final Logger logger = LoggerFactory.getLogger(PositionManagementController.class);
	@Autowired
	private PositionManagementService positionManagementService;
	@RequestMapping("/positionManagement")
	public ModelAndView positionManagement(String navigationBar) {
		ModelAndView mv = new ModelAndView("platform/positionManagement/positionManagement");
		mv.addObject("navigationBar", navigationBar);
		return mv;
	}	
	
	@RequestMapping("/positionManagementPopPage")
	public ModelAndView positionManagementPopPage(PositionManagement vo,HttpServletRequest request){
		//默认弹出页面地址是新增页面
		Map<String, Object> params = new HashMap<String, Object>();
		Page<PositionManagement> page=new Page<PositionManagement>(); 
		ModelAndView mv = new ModelAndView();
		String popUrl = "";
		String id = request.getParameter("id");
		String pageCondition = request.getParameter("condition");
		if(pageCondition.equals("edit")){//编辑页面
			popUrl = "platform/positionManagement/positionManagementAddDlgtoEdit";
			params.put("postID",id);
			page.setParams(params);
			List<PositionManagement> list = positionManagementService.getPositionMessage(page);
			mv.addObject("postName", list.get(0).getPostName());
			mv.addObject("postID", list.get(0).getPostID());
			mv.addObject("organizationName", list.get(0).getOrganizationName());
			mv.addObject("organizationID", list.get(0).getOrganizationID());
			mv.addObject("isImportant", list.get(0).getIsImportant());
			mv.addObject("postDivision", list.get(0).getPostDivision());
		}else if(pageCondition.equals("look")){//查看页面
			popUrl = "platform/positionManagement/positionManagementAddDlgtoLook";
			params.put("postID",id);
			page.setParams(params);
			List<PositionManagement> list = positionManagementService.getPositionMessage(page);
			mv.addObject("postName", list.get(0).getPostName());
			mv.addObject("postID", list.get(0).getPostID());
			mv.addObject("organizationName", list.get(0).getOrganizationName());
			mv.addObject("organizationID", list.get(0).getOrganizationID());
			mv.addObject("isImportant", list.get(0).getIsImportant());
			mv.addObject("postDivision", list.get(0).getPostDivision());
		}else if(pageCondition.equals("addPersonal")){//增加人员页面
			popUrl = "platform/positionManagement/positionManagementAddPersonalDlg";
		}else{
			popUrl = "platform/positionManagement/positionManagementAddDlg";
			String organizationID = request.getParameter("organizationID");
			String organizationName = positionManagementService.getOrganizationName(Integer.parseInt(organizationID));
			mv.addObject("organizationID", organizationID);
			mv.addObject("organizationName", organizationName);
		}
		mv.setViewName(popUrl);
		mv.addObject("id",id);
		mv.addObject("pageCondition",pageCondition);
		return mv;
	}
	
	/**
	 * 加载岗位管理列表
	 * 
	 * @return
	 */
	
	@RequestMapping("/positionManagementList")
	@ResponseBody
	public Map<String, Object> positionManagementList(PositionManagement vo,PostUserManage postUserManage,HttpServletRequest request) {
		logger.info("开始...获取页面显示数据");		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Page<PositionManagement> page = new Page<PositionManagement>(); 
		//设置分页参数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		params.put("organizationID", vo.getOrganizationID());
		params.put("postName",vo.getPostName());
		params.put("isImportant", vo.getIsImportant());
		page.setParams(params);
		System.out.println(params);
		//设置查询参数
		System.out.println(page);
		List<PositionManagement> list = positionManagementService.getPositionManagementList(page);
		List<PositionManagement> PersonalNumList = positionManagementService.getPostPersonalNumList(vo);
		//查询总记录数
		for(int i=0;i < PersonalNumList.size();i++){
			Integer postID = PersonalNumList.get(i).getPostID();
			Integer PersonalNum = PersonalNumList.get(i).getPostPersonalNum();
			for(int j=0;j < list.size();j++){
				Integer PostID = list.get(j).getPostID();
				if(PostID.equals(postID)){
					list.get(j).setPostPersonalNum(PersonalNum);
				}
			}
		}
		int totalCount = page.getTotalRecord();
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}

	/**
	 *
	 * 根据条件查询岗位信息
	 *
	 * @return
	 */
	@RequestMapping("/queryPositionManagementList")
	@ResponseBody
	public Map<String, Object> queryPositionManagementList(){
		return null;
	}
	
	/**
	 *
	 *新增岗位人员
	 *
	 *@return
	 */
	@RequestMapping("/addNew")
	@ResponseBody
	public Integer addNew(PositionManagement vo){
		try{
			positionManagementService.insertPositionPersonal(vo);
	        Integer postID = vo.getPostID();
			return postID;	
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 *
	 *编辑修改岗位信息
	 *
	 *@return
	 */
	@RequestMapping("/toUpdate")
	@ResponseBody
	public boolean toUpdate(PositionManagement positionManagement, HttpServletRequest request){
		try{
			positionManagement.setPostID(Integer.parseInt(request.getParameter("id")));
			positionManagementService.updatePositionPersonal(positionManagement);
			return true;	
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 *
	 *单条数据删除
	 *
	 *@return
	 */
	@RequestMapping("/doDelete")
	@ResponseBody
	public boolean doDelete(PositionManagement positionManagement,PostUserManage  postUserManage,HttpServletRequest request){
		try{
			positionManagement.setPostID(Integer.parseInt(request.getParameter("id")));
			positionManagementService.deletePositionPersonal(positionManagement);
			postUserManage.setPostID(Integer.parseInt(request.getParameter("id")));
			positionManagementService.delIsertPostUserMsg(postUserManage);
			return true;	
		}catch (Exception e) {
			return false;
		}
	}
	
	/**
	 *
	 *批量删除
	 *
	 *@return
	 */
	@RequestMapping("/doBatchDel")
	@ResponseBody
	public Boolean doBatchDel(PositionManagement positionManagement,PostUserManage  postUserManage, HttpServletRequest request){
			String postIDs = request.getParameter("postIDs");
			String[] splitIds = postIDs.split(",");
			boolean flag = true;
			List<Integer> delPostId = new ArrayList<Integer>();
			for(String StrId : splitIds){
				Integer postID = Integer.parseInt(StrId);
				delPostId.add(postID);
			}
			if(delPostId.size() > 0){
				for(int i=0; i < delPostId.size();i++){
					Integer postID = delPostId.get(i);
					positionManagement.setPostID(postID);
					postUserManage.setPostID(postID);
					Integer personalNum = positionManagementService.getPersonalNum(postID);
					if(personalNum==0){
						positionManagementService.deletePositionPersonal(positionManagement);
						positionManagementService.delIsertPostUserMsg(postUserManage);
					}else{
						flag = false; 
					}
				}
			}
			return flag;
	}
	/**
	 *初始化树 
	 */
	@RequestMapping("/initOrganizationTreeVal")
	@ResponseBody
	public Map<String, Object> initOrganizationTreeVal() throws Exception{
		return null;
	}
	
	/**
	 * 
	 *加载岗位人员信息表
	 *
	 */
	@RequestMapping("/initPositionPersonalTab")
	@ResponseBody
	public Map<String,Object> initPositionPersonalTab(PositionManagement positionManagement, HttpServletRequest request){
		try{
			Map<String, Object> result = new HashMap<String, Object>();
			Map<String, Object> params = new HashMap<String, Object>();
			Page<PositionManagement> page = new Page<PositionManagement>(); 
			//设置分页参数
			FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
			// 设置分页参数
			page.setPageNo(flexiGridPageInfo.getPage());
			page.setPageSize(flexiGridPageInfo.getRp());
			params.put("postID",positionManagement.getPostID());
			page.setParams(params);
			//设置查询参数
			List<PositionManagement> list = positionManagementService.initPositionPersonalTabList(page);
			//查询总记录数
			int totalCount = page.getTotalRecord();
			result.put("total", totalCount);
			result.put("rows", list);
			logger.info("结束...获取页面显示数据");
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * 
	 *删除岗位人员信息
	 *
	 */
	@RequestMapping("/deletePositionPersonalMessage")
	@ResponseBody
	public Boolean deletePositionPersonalMessage(PositionManagement positionManagement, HttpServletRequest request){
		try{
			positionManagement.setID(Integer.parseInt(request.getParameter("id")));
			positionManagementService.deletePositionPersonalMessage(positionManagement);
			return true;	
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 
	 *加载岗位人员信息表
	 *
	 */
	@RequestMapping("/initPositionMessageAddPersonalTable")
	@ResponseBody
	public Map<String,Object> initPositionMessageAddPersonalTable(PositionManagement positionManagement, HttpServletRequest request){
		try{
			Integer organizationID = Integer.parseInt(request.getParameter("organizationID"));
			Map<String, Object> result = new HashMap<String, Object>();
			Map<String, Object> params = new HashMap<String, Object>();
			Page<PositionManagement> page = new Page<PositionManagement>(); 
			//设置分页参数
			FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
			// 设置分页参数
			page.setPageNo(flexiGridPageInfo.getPage());
			page.setPageSize(flexiGridPageInfo.getRp());
			params.put("employeeCode",positionManagement.getEmployeeCode());
			params.put("userName",positionManagement.getUserName());
			params.put("organizationID",organizationID);
			page.setParams(params);
			//设置查询参数
			List<PositionManagement> list = positionManagementService.initPositionMessageAddPersonalTable(page);
			//查询总记录数
			int totalCount = page.getTotalRecord();
			result.put("total", totalCount);
			result.put("rows", list);
			logger.info("结束...获取页面显示数据");
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 *
	 *添加人员到岗位
	 *
	 *@return
	 */
	@RequestMapping("/addPersonalToPosition")
	@ResponseBody
	public Boolean addPersonalToPosition(PostUserManage postUserManage, HttpServletRequest request){
		try{
			String userIDs = request.getParameter("userIDs");
			String[] splitIds = userIDs.split(",");
			postUserManage.setPostID(Integer.parseInt(request.getParameter("postID")));
			List<Integer> addUserId = new ArrayList<Integer>();
			for(String StrId : splitIds){
				Integer userID = Integer.parseInt(StrId);
				addUserId.add(userID);
			}
			if(addUserId.size() > 0){
				for(int i=0; i < addUserId.size();i++){
					Integer userID = addUserId.get(i);
					postUserManage.setUserID(userID);
					positionManagementService.insertPersonalToPosition(postUserManage);
				}
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 *
	 *删除新增的人员信息
	 *
	 *@return
	 */
	@RequestMapping("/delIsertPostMsg")
	@ResponseBody
	public Boolean delIsertPostMsg(PostUserManage postUserManage, PositionManagement positionManagement,HttpServletRequest request){
		try{
			postUserManage.setPostID(Integer.parseInt(request.getParameter("postID")));
			positionManagementService.delIsertPostUserMsg(postUserManage);
			positionManagement.setPostID(Integer.parseInt(request.getParameter("postID")));
			positionManagementService.deletePositionPersonal(positionManagement);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

