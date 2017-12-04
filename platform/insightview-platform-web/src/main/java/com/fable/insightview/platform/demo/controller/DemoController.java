package com.fable.insightview.platform.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.demo.entity.TestUser;
import com.fable.insightview.platform.demo.service.ITestUserService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;

@Controller
@RequestMapping("/platform/demoUser")
public class DemoController {
	@Autowired
	ITestUserService testUserService;
	@RequestMapping("/toUserInfos")
	public ModelAndView toUserInfos() { 
		return new ModelAndView("platform/demo/testsysuser_list");
	}
		
	@RequestMapping("/checkSysUser")
	@ResponseBody
	public boolean checkSysUser(TestUser userInfoBean)throws Exception{
		List<TestUser> userlist=testUserService.getUser(userInfoBean.getUseraccount());
			
		if (null == userlist || userlist.size()<=0) {
			return false;
		} else {
			return true;
		}
	}
	
	@RequestMapping("/addUser")
	@ResponseBody
	public void addUser(TestUser user)throws Exception{
		testUserService.addUser(user);
	}
	
	@RequestMapping("/listUsers")
	@ResponseBody
	public Map<String, Object> listUsers(TestUser user)throws Exception{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		System.out.println("param sort="+request.getParameter("sort"));
		System.out.println("param order="+request.getParameter("order"));
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		
		Page<TestUser> page=new Page<TestUser>(); 
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("useraccount", user.getUseraccount());
		paramMap.put("username", user.getUsername());
		paramMap.put("usertype", user.getUsertype());
		paramMap.put("isautolock", user.getIsautolock()); 
		paramMap.put("sort", request.getParameter("sort"));
		paramMap.put("order", request.getParameter("order"));
		page.setParams(paramMap);
		List<TestUser> users=
			testUserService.getAllUsersByConditions(page);
		int total=testUserService.getTotalCount(user);
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("total", total);
		result.put("rows", users);
		return result;
	}
	
	@RequestMapping("/updateUser")
	@ResponseBody
	public boolean updateUser(TestUser user)throws Exception{
		return testUserService.updateUser(user);
	}
	
	@RequestMapping("/delUser")
	@ResponseBody
	public boolean delUser(TestUser user)throws Exception{
		return testUserService.delUser(user);
	}
	
	@RequestMapping("/findUser")
	@ResponseBody
	public TestUser findUser(TestUser user) throws Exception{
		TestUser testUser=testUserService.getUserByUserId(user.getUserid());
		return testUser;
	}
}