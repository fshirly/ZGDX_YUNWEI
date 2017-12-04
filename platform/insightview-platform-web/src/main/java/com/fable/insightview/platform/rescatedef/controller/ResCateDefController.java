package com.fable.insightview.platform.rescatedef.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
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

import com.fable.insightview.platform.common.util.FileUploadUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.manufacturer.entity.ManufacturerInfoBean;
import com.fable.insightview.platform.manufacturer.service.IManufacturerService;
import com.fable.insightview.platform.rescatedef.entity.ResCateDefInfo;
import com.fable.insightview.platform.rescatedef.service.IResCateDefService;
import com.fable.insightview.platform.restypedef.entity.ResTypeDefineInfo;
import com.fable.insightview.platform.restypedef.service.IResTypeDefService;

/**
 * @Description:   产品目录控制器 
 * @author         郑小辉
 * @Date           2014-6-25 下午03:59:36 
 */
@Controller
@RequestMapping("/platform/resCateDefine")
public class ResCateDefController {
	@Autowired
	private IResCateDefService resCateDefService;
	
	@Autowired
	private IManufacturerService  manufacturerService;
	
	@Autowired
	private IResTypeDefService resTypeDefService;
	
	private final Logger logger = LoggerFactory.getLogger(ResCateDefController.class);


	/**
	 * 菜单页面跳转
	 * @return
	 */
	@RequestMapping("/toResCateDefineList")
	public ModelAndView toResCateDefineList(String navigationBar) {	
		return new ModelAndView("platform/rescatedef/rescatedef_list").addObject("navigationBar", navigationBar);
	}
	
	/**
	 * 获取页面显示数据
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listResCateDef")
	@ResponseBody
	public Map<String, Object> listResCateDef(ResCateDefInfo vo, HttpServletRequest request)throws Exception{
		logger.info("开始...获取页面显示数据");
		logger.debug("param 产品目录名称[resCategoryName]={}",vo.getResCategoryName());
		//获取分页对象
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		//获取排序参数
		flexiGridPageInfo.setSort(request.getParameter("sort"));
		flexiGridPageInfo.setOrder(request.getParameter("order"));
		//执行分页方法
		flexiGridPageInfo = resCateDefService.queryPage(vo, flexiGridPageInfo);
		Map<String,Object> result = new HashMap<String,Object>();
		//设置
		result.put("total", flexiGridPageInfo.getTotal() );
		result.put("rows", flexiGridPageInfo.getRows());
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	/**
	 * 删除信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delResCateDefine")
	@ResponseBody
	public Map<String,Object> delResCateDefine(ResCateDefInfo vo){
		logger.info("开始...删除ResCateDefInfo");
		logger.debug("delete by param resCategoryID={}",vo.getResCategoryID());
		Map<String,Object> result = new HashMap<String,Object>();
		String flag = "1"  ;//表示正常删除状态
		String id = resCateDefService.getCanDelId(vo.getResCategoryID()+"");
		try {			
			if(id.equals(vo.getResCategoryID()+"")){//判断是否有外键关系，此时无
				resCateDefService.deleteInfo(vo);				
			}else{
				flag = "2";//表示有外键关系
			}		
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage(),vo);
			flag = "3";
		}
		logger.info("结束...删除ResCateDefInfo");
		result.put("flag", flag);
		return result ;
	}
	
	/**
	 * 增加信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addResCateDefine")
	@ResponseBody
	public boolean addResCateDefine(ResCateDefInfo vo){
		logger.info("开始...增加ResCateDefInfo");
		vo.setCreateTime(new Date());
		try {
			resCateDefService.addInfo(vo);
		} catch (Exception e) {
			logger.error("插入异常："+e.getMessage(),vo);
			return false;
		}
		logger.info("结束...增加ResCateDefInfo");
		return true ;
	}
	
	/**
	 * 查找对象信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findResCateDefine")
	@ResponseBody
	public ResCateDefInfo findResCateDefine(ResCateDefInfo vo) throws Exception{
		logger.info("开始...查询ResCateDefInfo");
		logger.debug("find by param resCategoryID={}",vo.getResCategoryID());
		vo = resCateDefService.getInfoById(vo.getResCategoryID());
		if(0 !=vo.getResManufacturerID()){
			ManufacturerInfoBean mfInfo = manufacturerService.getManufacturerInfoBeanByConditions("resManufacturerId",vo.getResManufacturerID()+"");
			if(null !=mfInfo){
				vo.setResManufacturerName(mfInfo.getResManuFacturerName());
			}
		}
		if(0 !=vo.getResTypeID()){
			ResTypeDefineInfo resVo = resTypeDefService.getInfoById(vo.getResTypeID());
			if(null !=resVo){
				vo.setResTypeName(resVo.getResTypeName());
			}
		}
		logger.info("结束...查询ResCateDefInfo");
		return vo;
	}
	
	/**
	 * 更新信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateResCateDefine")
	@ResponseBody
	public boolean updateResCateDefine(ResCateDefInfo vo){
		logger.info("开始...更新ResCateDefInfo");
		logger.debug("update by param resCategoryID={}",vo.getResCategoryID());
		vo.setLastModifyTime(new Date());
		try {
			resCateDefService.updateInfo(vo);
		} catch (Exception e) {
			logger.error("修改异常："+e.getMessage(),vo);
			return false;
		}
		logger.info("结束...更新ResCateDefInfo");
		return true ;
	}
	
	/**
	 * 厂商信息：菜单页面跳转
	 * @return
	 */
	@RequestMapping("/toSelectMfList")
	public ModelAndView toSelectMfList() {
		return new ModelAndView("platform/rescatedef/selectMfList");
	}
	
	/**
	 * 产品目录：菜单页面跳转
	 * @return
	 */
	@RequestMapping("/toSelectRtList")
	public ModelAndView toSelectRtList() {	
		return new ModelAndView("platform/rescatedef/selectRtList");
	}
	
	/**
	 * 批量删除信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delBathResCateDefine")
	@ResponseBody
	public Map<String,Object> delBathSnmpPen(String id){
		logger.info("开始...批量删除ResCateDefInfo");
		logger.debug("deleteBath by param resCategoryID={}",id);
		Map<String,Object> result = new HashMap<String,Object>();
		String flag = "1"  ;//表示正常删除状态
		String canDelId = resCateDefService.getCanDelId(id);//判断是否有外键关系，此时无
		try {
			if(id.equals(canDelId)){
				resCateDefService.deleteBathInfo(id);
			}else{
				flag = "2";//表示有外键关系
			}
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage(),id);
			flag = "3";
		}
		result.put("flag", flag);
		logger.info("结束...批量删除ResCateDefInfo");		
		return result ;
	}
	
	/**
	 * 图标上传
	 * @param request
	 * @param response
	 */
	@RequestMapping("/resCatefileUpload")
	@ResponseBody
	public void resCatefileUpload(HttpServletRequest request,HttpServletResponse response){
		logger.info("开始...上传图标");		
		try {
			FileUploadUtil fileUploadUtil = new FileUploadUtil();
			String fileMsg = fileUploadUtil.uploadFile(request, response);

			System.out.println(fileMsg);
			PrintWriter out = response.getWriter();
			out.write(fileMsg);
		} catch (Exception e) {
			logger.error("上传图标异常："+e.getMessage());
		}
		logger.info("结束...上传图标");		
	}	
}