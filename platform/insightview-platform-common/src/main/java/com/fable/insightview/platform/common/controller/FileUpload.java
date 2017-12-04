package com.fable.insightview.platform.common.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.platform.common.util.FileDownloadUtil;
import com.fable.insightview.platform.common.util.FileUploadUtil;
import com.fable.insightview.platform.sysinit.SystemParamInit;

@Controller
@RequestMapping("/commonFileUpload")
public class FileUpload { 

	/**
	 * 文件上传统一入口方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @author wul
	 */
	@RequestMapping(value = "/uploadFile",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadFile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		String fileMsg = fileUploadUtil.uploadFile(request, response);
		return fileMsg;
	}

	@RequestMapping(value = "/CosDownload",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public void CosDownload(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FileDownloadUtil fd = new FileDownloadUtil();
		fd.downloadFile(request, response);
	}
	
	@RequestMapping("/getFileServerPath")
	@ResponseBody
	public String getFileServerPath(){
		String fileServerPath = SystemParamInit.getValueByKey("fileServerURL");
		return fileServerPath.substring(0,fileServerPath.length()-1);
	}
	
	@RequestMapping(value = "/delFile",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delFile(HttpServletRequest request,HttpServletResponse response){
		FileUploadUtil fu = new FileUploadUtil();
		String fileName = request.getParameter("fileDir");
		String fileMsg = fu.delFile(request,response,fileName);
		return fileMsg;
	}
	
	/**
	 * 获取文件的大小
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getFileSize",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getFileSize(HttpServletRequest request,HttpServletResponse response){
		FileUploadUtil fu = new FileUploadUtil();
		String fileName = request.getParameter("fileDir");
		String fileMsg = fu.getFileSize(request,response,fileName);
		return fileMsg;
	}
	
	/**
	 * 上传大文件
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/uploadMaxFile",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadMaxFile(HttpServletRequest request,HttpServletResponse response) {
		FileUploadUtil util = new FileUploadUtil();
		String fileMsg = util.uploadMaxFile(request, response);
		return fileMsg;
	}
	
}
