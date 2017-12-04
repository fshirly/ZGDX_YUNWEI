package com.fable.insightview.platform.module.controller;

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
import com.fable.insightview.platform.module.entity.ModuleBean;
import com.fable.insightview.platform.module.service.ModuleService;


/**
 * @author fangang
 * 模块管理控制器处理类
 */
@Controller
@RequestMapping("/sys")
public class RestModuleController
{
	@RequestMapping(value = "/module/list/html", method = RequestMethod.GET)
    //@ApiOperation(value = "模块管理页面", notes = "前往模块管理页面")
    public String gotoListPage()
    {
        return "platform/system/moduleManage";
    }
	
	@RequestMapping(value = "/module/create/html", method = RequestMethod.GET)
    //@ApiOperation(value = "新增模块页面", notes = "前往新增模块页面")
    public String gotoCreatePage()
    {
        return "platform/system/moduleEdit";
    }
    
    @RequestMapping(value = "/module/update/html", method = RequestMethod.GET)
    //@ApiOperation(value = "修改模块页面", notes = "前往修改模块页面")
    public String gotoUpdatePage()
    {
        return "platform/system/moduleEdit";
    }
	
    @RequestMapping(value = "/module" , method = RequestMethod.GET)
    //@ApiOperation(value = "获取所有模块", notes = "获取所有模块")
    public @ResponseBody
    TreeDataBean<ModuleBean> getModules() throws Exception
    {
        TreeDataBean<ModuleBean> dictionaryData = new TreeDataBean<ModuleBean>();
        List<ModuleBean> list = moduleService.queryModuleList();
        dictionaryData.setData(list);
        
        return dictionaryData;
    }
    
    @RequestMapping(value = "/module/saveData", method = RequestMethod.PUT)
    //@ApiOperation(value = "保存模块列表数据", notes = "保存模块列表数据")
    public @ResponseBody MessageBean saveModuleData(
    		//@ApiParam(required = true, value = "模块列表") 
    		@RequestBody ModuleBean[] moduleData) throws Exception {
		//System.out.println(JSONArray.fromObject(moduleData));
		
    	moduleService.saveModuleData(moduleData);
		MessageBean mes = new MessageBean();
    	mes.setMessage("保存成功！");
        return mes;
    }
    
    @RequestMapping(value = "/module/{moduleId}", method = RequestMethod.DELETE)
    //@ApiOperation(value = "删除模块节点", notes = "删除模块节点")
    public @ResponseBody MessageBean deleteModule(
            //@ApiParam(required = true, value = "模块节点ID") 
            @PathVariable("moduleId") String moduleId)
            throws Exception
    {
        //System.out.println("moduleId: " + moduleId);
        moduleService.deleteModule(moduleId);
        MessageBean mes = new MessageBean();
    	mes.setMessage("删除成功！");
        return mes;
    }
    
    @RequestMapping(value = "/module/{moduleId}", method = RequestMethod.GET)
    //@ApiOperation(value = "获取模块节点", notes = "获取模块节点")
    public @ResponseBody
    ModuleBean getModule(
            //@ApiParam(required = true, value = "模块节点ID")
    		@PathVariable("moduleId") String moduleId)
            throws Exception
    {
        //System.out.println("moduleId: " + moduleId);
        return moduleService.getModule(moduleId);
    }
    
    @RequestMapping(value = "/module", method = RequestMethod.POST)
    //@ApiOperation(value = "创建模块节点", notes = "创建模块节点")
    public @ResponseBody MessageBean createModule(
            //@ApiParam(required = true, value = "模块节点") 
            @RequestBody ModuleBean moduleBean)
            throws Exception
    {
        //System.out.println(PropertyUtils.describe(moduleBean));
        moduleService.addModule(moduleBean);
        MessageBean mes = new MessageBean();
        //返回模块id至页面
        mes.setMessage(moduleBean.getId());
    	return mes;
    }
    
    @RequestMapping(value = "/module", method = RequestMethod.PUT)
    //@ApiOperation(value = "更新模块节点", notes = "更新模块节点")
    public @ResponseBody MessageBean updateModule(
            //@ApiParam(required = true, value = "模块节点") 
            @RequestBody ModuleBean moduleBean)
            throws Exception
    {
    	//System.out.println(PropertyUtils.describe(moduleBean));
    	moduleService.updateModule(moduleBean);
        MessageBean mes = new MessageBean();
    	mes.setMessage("保存成功！");
    	return mes;
    }
    
    @Autowired
	private ModuleService moduleService;
}
