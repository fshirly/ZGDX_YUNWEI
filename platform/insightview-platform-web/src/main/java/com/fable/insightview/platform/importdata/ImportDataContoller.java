package com.fable.insightview.platform.importdata;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.importdata.DataImportor;
import com.fable.insightview.platform.importdata.ImportResult;
import com.fable.insightview.platform.importdata.ValidResult;
import com.fable.insightview.platform.importdata.resolver.DataResolverFactory;
import com.fable.insightview.platform.importdata.resolver.InputStreamResolver;

@Controller
@RequestMapping("/platform/dataImportor")
public class ImportDataContoller {
	
	String resultInfo = "";
	String importType = "";
	@RequestMapping("/dataImportForm")
	public ModelAndView dataImportForm(HttpServletRequest request, HttpServletResponse response) {
		String importType = request.getParameter("importType");
		request.setAttribute("importType", importType);
		return new ModelAndView("common/dataimportForm");  
	}
	
	@RequestMapping("/doImportData")
	public String doImportData(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		String resultView = "permission/sysuser_list";
		ImportResult importResult = null;
		StringBuilder sb = new StringBuilder("");
		String beanName = request.getParameter("beanName");
		importType = beanName;
		if("deptImportor".equals(beanName)){
			resultView = "permission/department_list";
		}
		if (!StringUtils.isEmpty(beanName)) {
			DataImportor importor = (DataImportor) BeanLoader.getBean(beanName);
			if (importor != null) {
				
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(1024 * 1024);
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(1024 * 1024 * 100);
				
				FileItemIterator it = upload.getItemIterator(request);
				while (it.hasNext()) {
					FileItemStream item = it.next();
					if(!item.isFormField()){
						
						((InputStreamResolver)importor.getResolver()).setInputStream(item.openStream());
						importResult = importor.importHandle();
						
						List<ValidResult> vaildResultList = importResult.getVaildResultList();
						List<String> processResultList = importResult.getProcessResultList();
						if(processResultList != null){
							for (int i = 0; i < processResultList.size(); i++) {
								sb.append(processResultList.get(i));
								sb.append("<br>\r\n");
							}
						}
						
						if(vaildResultList != null){
							for(ValidResult validResult : vaildResultList){
								sb.append(getErrorInfo(validResult.getLineNum(), validResult.getConstraintSet()));
								sb.append("<br>\r\n");
							}
						}
						
					}
				}
				DataResolverFactory dataResfactory = DataResolverFactory.getInstance();
				dataResfactory.pubMappingClass = null;
			}
		}
		if(importResult!=null){
			if(sb.length() > 0 && importResult.getSuccessNum() == 0){
				resultInfo =  "<font color='red'>数据异常，请检查：</font><br>" + sb.toString();
			}else{
					if (sb.length() > 0 && importResult.getSuccessNum() > 0) {
						String info = "共提交" + importResult.getTotalNum()+"条，成功"+importResult.getSuccessNum()+"条，失败" + importResult.getFailureNum() + "条";
						String errorInfo = "<br><font color='red'>数据异常，请检查：</font><br>" + sb.toString();
						resultInfo = info + errorInfo;
					} else {
						String info = importResult.isSuccess()?"导入成功！":"<font color='red'>导入失败！</font>";
						info += "<br>共提交" + importResult.getTotalNum()+"条，成功"+importResult.getSuccessNum()+"条，失败" + importResult.getFailureNum() + "条";
						resultInfo = info;
					}
				
			}
		}
		return "/common/importResult";
	}
	
	private StringBuilder getErrorInfo(int rowNum, Set<ConstraintViolation> set){
		StringBuilder sb = new StringBuilder();
		sb.append("第");
		sb.append(rowNum);
		sb.append("行：");
		
		Iterator<ConstraintViolation> iterator = set.iterator();
		while(iterator.hasNext()){
			ConstraintViolation constraintViolation = iterator.next();
			sb.append(constraintViolation.getMessage());
			sb.append("//");
		}
		return sb;
	}
	
	@RequestMapping("/toResultInfo")
	public ModelAndView toResultInfo(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		request.setAttribute("resultInfo", resultInfo);
		request.setAttribute("importType", importType);
		return new ModelAndView("/common/importResult");
	}
	
	
}
