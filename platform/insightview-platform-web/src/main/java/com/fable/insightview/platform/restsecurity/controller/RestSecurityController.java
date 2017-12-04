package com.fable.insightview.platform.restsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.platform.common.entity.MessageBean;
import com.fable.insightview.platform.common.entity.TreeDataBean;
import com.fable.insightview.platform.restSecurity.entity.MethodBean;
import com.fable.insightview.platform.restSecurity.entity.ModuleRestDto;
import com.fable.insightview.platform.restSecurity.entity.RestBean;
import com.fable.insightview.platform.restSecurity.service.RestSecurityService;

//import com.wordnik.swagger.annotations.ApiOperation;
//import com.wordnik.swagger.annotations.ApiParam;

/**
 * @author fangang
 * Rest接口权限控制器处理类
 */
@Controller
@RequestMapping("sys")
public class RestSecurityController {

//	@RequestMapping(value = "/restSecurity/list/html", method = RequestMethod.GET)
//    //@ApiOperation(value = "Rest接口权限管理页面", notes = "前往Rest接口权限管理页面")
//    public String gotoIndexPage() {
//        return "platform/system/restSecurityManage";
//    }
	
	@RequestMapping(value = "/restSecurity/moduleRest/html", method = RequestMethod.GET)
    //@ApiOperation(value = "模块关联Rest接口页面", notes = "前往模块关联Rest接口页面")
    public String gotoRestPage() {
        return "platform/system/moduleRest";
    }
	
//	@RequestMapping(value = "/restSecurity/roleRest/html", method = RequestMethod.GET)
//    //@ApiOperation(value = "角色Rest接口分配页面", notes = "前往角色Rest接口分配页面")
//    public String gotoRoleRestPage() {
//        return "platform/system/roleRest";
//    }
	
	@RequestMapping(value = "/restSecurity/getRestData", method = RequestMethod.GET)
    //@ApiOperation(value = "获取Rest接口数据", notes = "获取Rest接口数据")
    public @ResponseBody
    TreeDataBean<RestBean> getRestData() throws Exception
    {
        TreeDataBean<RestBean> restData = new TreeDataBean<RestBean>();
        List<RestBean> list = restSecurityService.getRestData();
        restData.setData(list);
        
        return restData;
    }
	
	@RequestMapping(value = "/restSecurity/queryRestByModuleId/{moduleId}", method = RequestMethod.GET)
    //@ApiOperation(value = "获取模块关联的Rest接口名称", notes = "获取模块关联的Rest接口名称")
    public @ResponseBody String[] queryRestByModuleId(
            //@ApiParam(required = true, value = "模块ID") 
    		@PathVariable("moduleId") String moduleId)
            throws Exception
    {
        //System.out.println("moduleId: " + moduleId);
        return restSecurityService.queryRestByModuleId(moduleId);
    }
	
	@RequestMapping(value = "/restSecurity/saveRest/{moduleId}", method = RequestMethod.POST)
    //@ApiOperation(value = "保存模块关联的Rest接口", notes = "保存模块关联的Rest接口")
    public @ResponseBody MessageBean saveRest(
    		//@ApiParam(required = true, value = "模块ID") 
    		@PathVariable("moduleId") String moduleId,
    		//@ApiParam(required = true, value = "Rest接口列表") 
    		@RequestBody MethodBean[] methodBeans) throws Exception {
		//System.out.println("moduleId: " + moduleId);
		//System.out.println(PropertyUtils.describe(methodBeans));
    	restSecurityService.saveRest(moduleId, methodBeans);
    	MessageBean mes = new MessageBean();
        mes.setMessage("保存成功！");
        return mes;
    }
	
	@RequestMapping(value = "/restSecurity/getModuleRestData", method = RequestMethod.GET)
	//@ApiOperation(value="获取模块Rest接口数据", notes="获取模块Rest接口数据")
	public @ResponseBody TreeDataBean<ModuleRestDto> queryModuleRestList() throws Exception {
		TreeDataBean<ModuleRestDto> moduleRestData = new TreeDataBean<ModuleRestDto>();
		List<ModuleRestDto> list = restSecurityService.queryModuleRestList();
		moduleRestData.setData(list);
		
		return moduleRestData;
	}
	
//	@RequestMapping(value = "/restSecurity/getRoleRest/{roleId}", method = RequestMethod.GET)
//    //@ApiOperation(value = "获取角色关联的Rest接口", notes = "获取角色关联的Rest接口")
//    public @ResponseBody String[] getRoleRest(
//    		//@ApiParam(required = true, value = "角色ID") 
//    		@PathVariable("roleId") String roleId) throws Exception {
//		//System.out.println("roleId: " + roleId);
//		return restSecurityService.getRestByRoleId(roleId);
//    }
//	
//	@RequestMapping(value = "/restSecurity/saveRoleRest/{roleId}", method = RequestMethod.POST)
//    //@ApiOperation(value = "保存角色关联的Rest接口", notes = "保存角色关联的Rest接口")
//    public @ResponseBody MessageBean saveRoleRest(
//    		//@ApiParam(required = true, value = "角色ID") 
//    		@PathVariable("roleId") String roleId,
//    		//@ApiParam(required = true, value = "Rest接口名称列表") 
//    		@RequestBody String[] restNames) throws Exception {
//		//System.out.println("roleId: " + roleId + "restNames: " + restNames);
//    	restSecurityService.saveRoleRest(roleId, restNames);
//    	MessageBean mes = new MessageBean();
//        mes.setMessage("保存成功！");
//        return mes;
//    }
	
	@Autowired
	private RestSecurityService restSecurityService;
}
