package com.fable.insightview.guzhangrizManager.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.GuzhangrizManager.entity.GuzhangFile;
import com.fable.insightview.platform.GuzhangrizManager.entity.GuzhangrizManager;
import com.fable.insightview.platform.GuzhangrizManager.service.GuzhangrizManagerService;
import com.fable.insightview.platform.backup.service.DataBackupService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("guzhangrizManager")
public class GuzhangrizManagerController {
	@Autowired
	private GuzhangrizManagerService guzhangrizManagerService;
	
	@Autowired
	private DataBackupService backupService;
	
	
	private final static Logger logger = LoggerFactory.getLogger(GuzhangrizManagerService.class);
	/*
	 * 跳转页面
	 */
	@RequestMapping("/guzhangrizManager_list")
    public ModelAndView toguzhangrizManager_listList(String navigationBar) {
		return new ModelAndView("platform/guzhangrz/guzhangrz").addObject("navigationBar", navigationBar);
	}
	@RequestMapping("/guzrizManager_list")
    public ModelAndView toguzrizManager_list(String navigationBar) {
		return new ModelAndView("platform/guzhangrz/guzrz").addObject("navigationBar", navigationBar);
	}
	 /*
	  * 备份信息查看
	  */
	@RequestMapping("/guzhangrizManagerInfo_list")
	@ResponseBody
	public Map<String,Object> getContractManagerInfo_list(String tablename,HttpServletRequest request){
		//获取当前每页行数和当前页数
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		Page<GuzhangrizManager> page = new Page<GuzhangrizManager>();
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tablename", tablename);
		page.setParams(paramMap);
		// 查询分页数据
		List<GuzhangrizManager> list = guzhangrizManagerService.queryguzhangrizManagerList(page);
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	/**
	 * 故障日志
	 */
	@RequestMapping("/gzManagerInfo_list")
	@ResponseBody
	public List<GuzhangFile> gzManagerInfo_list(HttpServletRequest request){
		List<GuzhangFile> list=guzhangrizManagerService.getTomcatLogs_Info();
		return list;
	}
	public  String constFileName(String fileName) {
		return fileName.substring(0, fileName.indexOf(".") - 24) + fileName.substring(fileName.indexOf("."));
	}
	/**
	 * 故障日志
	 */
	@RequestMapping("/gzManagerInfo_download")
	public void downLoad(String fileName,HttpServletRequest request,HttpServletResponse response){
		String tomcatdir=System.getProperty("catalina.home")+File.separator+"logs";
		 //获得请求文件名  
        String filename = request.getParameter("fileName");  
        System.out.println(filename);  
          
        //设置文件MIME类型  
        response.setContentType(request.getServletContext().getMimeType(filename));  
        //设置Content-Disposition  
        response.setHeader("Content-Disposition", "attachment;filename="+filename);  
        //读取目标文件，通过response将目标文件写到客户端  
        //获取目标文件的绝对路径  
        String fullFileName = tomcatdir+File.separator+filename;  
        //System.out.println(fullFileName);  
        //读取文件  
        InputStream in = null;
		try {
			in = new FileInputStream(fullFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        OutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
          
        //写文件  
        int b;  
        try {
			while((b=in.read())!= -1){  
			    out.write(b);  
			}  
			  
			in.close();  
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	/**
	 * 数据恢复
	 */
	@RequestMapping("/dataRestore")
	@ResponseBody
	public Map<String,Object> dataRestore(GuzhangrizManager manager){
		manager = guzhangrizManagerService.queryBackupRecodeById(manager.getId());
		Map<String,Object> resMap = backupService.restoreDate(manager);
		return resMap;
	}
	
	
}
