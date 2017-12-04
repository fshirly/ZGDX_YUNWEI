package com.fable.insightview.platform.provider.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;
import com.fable.insightview.platform.provider.service.IProviderService;

/**
 * 供应商—控制器
 * @author zhurt
 */
@Controller
@RequestMapping("/platform/provider")
public class ProviderController {
	
	@Autowired
	private IProviderService  providerService;
	
	@Autowired
	private HttpServletRequest request;
	
	private final Logger logger = LoggerFactory.getLogger(ProviderController.class);
	
	/**
	 * 菜单页跳转
	 * @return
	 */
	@RequestMapping("/toProviderList")
	public ModelAndView toProviderList() {
		logger.info("访问页面provider_list.jsp");
		return new ModelAndView("platform/provider/provider_list");
	}
	
	/**
	 * 判断供应商名是否唯一
	 * @param providerInfoBean
	 * @return
	 */
	@RequestMapping("/checkNameUnique")
	@ResponseBody
	public boolean checkNameUnique(ProviderInfoBean providerInfoBean) {
		logger.info("执行供应商名唯一性验证checkNameUnique()方法，输入的 'providerName'值为: "+providerInfoBean.getProviderName());
		try {
			//获得providerName
			providerInfoBean = providerService.getProviderInfoBeanByConditions("providerName", providerInfoBean.getProviderName()+""); 
		} catch (Exception e) {
			logger.error("验证异常："+e.getMessage(),providerInfoBean);
		}
		return (null == providerInfoBean) ? true:false;
	}
	
	/**
	 * 查询所有供应商信息
	 * @param providerInfoBean
	 * @return
	 */
	@RequestMapping("/findProvider")
	@ResponseBody
	public ProviderInfoBean findProvider(ProviderInfoBean providerInfoBean) {
		logger.info("执行findProvider()方法，根据providerId获取供应商信息，providerId的值为： "+providerInfoBean.getProviderId());
		try {
			providerInfoBean =providerService.getProviderInfoBeanByConditions("providerId", providerInfoBean.getProviderId()+"");
		} catch (Exception e) {
			logger.error("异常："+e.getMessage(),providerInfoBean);
		}	
		logger.info("findProvider()方法执行结束");
		System.out.println("************URL:"+providerInfoBean.getuRL());
		return providerInfoBean; 		
	}


	/**
	 * 增加供应商
	 * @param providerInfoBean
	 * @return
	 */
	@RequestMapping("/addProvider")
	@ResponseBody
	public boolean addProvider(ProviderInfoBean providerInfoBean) {
		logger.info("执行addProvider()方法，新增供应商信息");
		try {
			providerService.addProviderInfo(providerInfoBean);	
		} catch (Exception e) {
			logger.error("插入异常："+e.getMessage(),providerInfoBean);
			return false;
		}
		logger.info("addProvider()方法执行结束");
		return true;
	}
 
	/**
	 * 删除供应商
	 * @param providerInfoBean
	 * @return
	 */
	@RequestMapping("/delProvider")
	@ResponseBody
	public Map<String , Object> delProvider(ProviderInfoBean providerInfoBean) {
		ProviderInfoBean provider = new ProviderInfoBean();
		provider.setProviderId(providerInfoBean.getProviderId());
		boolean flag = false;
		
		try {
				  providerService.delProviderInfoById(providerInfoBean);
				  flag = true;
			} catch (Exception e) {
				  logger.error("删除异常："+e.getMessage(),providerInfoBean.getProviderId());
				  flag = false;
			}
		
		Map<String ,Object> result = new HashMap<String,Object>();
		result.put("flag", flag);
		return result;
	}
	
	/**
	 * 批量删除供应商
	 * @param providerInfoBean
	 * @return
	 */
	@RequestMapping("/delBatchProvider")
	@ResponseBody
	public Map<String ,Object> delBatchProvider(HttpServletRequest request) {
		logger.info("执行批量删除delBatchProvider()方法，根据providerId删除供应商,providerId的值为： ");
		boolean flag  = false;
		boolean rs = false;
		String providerIds = request.getParameter("providerIds");
		String[] splitIds = providerIds.split(","); 
		String userName ="";
		String proName="";
		Set checkProName = new HashSet();
		int recordsize =0;
		
		/* 能够被删的ID数组 */
		List<ProviderInfoBean> delDept = new ArrayList<ProviderInfoBean>();
		/* 不能被删保留的ID数组 */
		List<ProviderInfoBean> reserveDept = new ArrayList<ProviderInfoBean>();
		
		for(String strId : splitIds){
			Integer providerId = Integer.parseInt(strId);
			ProviderInfoBean providerInfoBean = new ProviderInfoBean();
			providerInfoBean.setProviderId(providerId);
			
			boolean checkBefore = providerService.checkBeforeDel(providerInfoBean);
			if(checkBefore){
				delDept.add(providerInfoBean);
				System.out.println("能够被删的Id：" +providerInfoBean.getProviderId());
			}else{
				reserveDept.add(providerInfoBean);
				System.out.println("不能够被删而保留的Id：" +providerInfoBean.getProviderId());
			}
		}
		
		for(int i=0; i< delDept.size();i++){
			try {
				providerService.delProviderInfoById(delDept.get(i));
				flag = true;
			} catch (Exception e) {
				logger.error("删除异常："+e.getMessage());
				
			}
		}
		
		if (reserveDept.size() > 0) {
			 flag = false;
			 for (int i = 0; i < reserveDept.size(); i++) {
				List<SysUserInfoBean> sysProviderUserList = providerService.getProUserByProviderID(reserveDept.get(i));
				//String uname = sysProviderUserList.get(0).getUserName();
				int listsize = sysProviderUserList.size();
				recordsize = recordsize+listsize;
				System.out.println("list:"+recordsize);
				
				//获得引用供应商的员工信息
				for(int j=0; j < sysProviderUserList.size();j++){
					String uname = sysProviderUserList.get(j).getUserName();
					userName = userName+uname+",";
				}
				
				//获得被引用的供应商信息
				for(int k=0; k < sysProviderUserList.size(); k++){
//					checkProName = sysProviderUserList.get(k).getProviderName();
					String pname = sysProviderUserList.get(k).getProviderName();
//					checkProName.add(pname);
					proName = proName+pname+",";
				}
				userName = userName.substring(0,userName.lastIndexOf(","));
				//String pName = checkProName.toString();
				//System.out.println("**&*&*&:"+pName);
				proName = proName.substring(0,proName.lastIndexOf(","));
			 }
		}else{
			flag = true;
		}
		
		Map<String ,Object> result = new HashMap<String,Object>();
		userName = "【"+userName+"】";
		proName = "【"+proName+"】";
		result.put("flag", flag);
		result.put("userName", userName);
		result.put("proName", proName);
		return result;
	}
	
	/**
	 * 修改/更新信息
	 * @param providerInfoBean
	 * @return
	 */
	@RequestMapping("/updateProvider")
	@ResponseBody
	public boolean updateProvider(ProviderInfoBean providerInfoBean) {
		logger.info("执行updateProvider()方法，根据providerId修改供应商,providerId的值为: "+providerInfoBean.getProviderId());
		try {
			providerService.updateProvider(providerInfoBean);
		} catch (Exception e) {
			logger.error("修改异常："+e.getMessage(),providerInfoBean);
			return false;
		}
		logger.info("updateProvider()方法执行结束");
		return true;
	}	  
	
	/**
	 * 加载页面，获取页面显示数据
	 * @param providerInfoBean
	 * @return
	 */
	@RequestMapping("/listProvider")
	@ResponseBody
	public Map<String,Object> listProvider(ProviderInfoBean providerInfoBean) {
		logger.info("执行listProvider()方法，获取页面加载所需信息");
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder
					.getRequestAttributes()).getRequest();

			FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
					.getFlexiGridPageInfo(request);

			List<ProviderInfoBean> providerList = providerService
					.getProviderInfoBeanByConditions(providerInfoBean,flexiGridPageInfo);
	
			// 获取总记录数
			int total = providerService.getTotalCount(providerInfoBean);
			logger.info("...获得数据库中记录总数："+total);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("total", total);
			result.put("rows", providerList);
			
			logger.info("listProvider()方法执行结束");
			return result;
	}
	
	/**
	 * 加载供应商树
	 * @return
	 */
	@RequestMapping("/findProvideTreeVal")
	@ResponseBody
	public Map<String, Object> findProvideTreeVal() {
		List<ProviderInfoBean> menuLst=providerService.findProvideTreeVal();
		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}
	
	/**
	 * 加载用户所属单位的关联供应商
	 * @return
	 */
	@RequestMapping("/queryProsByUserId")
	@ResponseBody
	public List<ProviderInfoBean> queryPros( Integer userId) {
		if(userId==null||"".equals(userId)){
			SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
			userId = Integer.parseInt(sysUser.getId().toString());
		}
		return providerService.queryProviderListByOragWithUser(userId);
	}
	
}
