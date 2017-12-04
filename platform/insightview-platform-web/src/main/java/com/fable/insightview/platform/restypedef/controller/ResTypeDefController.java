package com.fable.insightview.platform.restypedef.controller;

import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.restypedef.entity.ResTypeDefineInfo;
import com.fable.insightview.platform.restypedef.service.IResTypeDefService;

/**
 * @Description:   产品类型控制器
 * @author         郑小辉
 * @Date           2014-6-20 上午09:58:37 
 */

@Controller
@RequestMapping("/platform/resTypeDefine")
public class ResTypeDefController {
	@Autowired
	private IResTypeDefService resTypeDefService;
	
	private final Logger logger = LoggerFactory.getLogger(ResTypeDefController.class);
	
	/**
	 * 菜单页面跳转
	 * @return
	 */
	@RequestMapping("/toResTypeDefineList")
	public ModelAndView toResTypeDefineList(String navigationBar) {	
		return new ModelAndView("platform/restypedef/restypedef_list").addObject("navigationBar", navigationBar);
	}
	
	/**
	 * 获取页面显示数据
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listResTypeDefine")
	@ResponseBody
	public Map<String, Object> listResTypeDefine(ResTypeDefineInfo vo, HttpServletRequest request)throws Exception{
		logger.info("开始...获取页面显示数据");
		logger.debug("param 类型名称[resTypeName]={}",vo.getResTypeName());
		//获取分页对象
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		//获取排序参数
		flexiGridPageInfo.setSort(request.getParameter("sort"));
		flexiGridPageInfo.setOrder(request.getParameter("order"));
		//执行分页方法
		flexiGridPageInfo = resTypeDefService.queryPage(vo, flexiGridPageInfo);
		Map<String,Object> result = new HashMap<String,Object>();
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
	@RequestMapping("/delResTypeDefine")
	@ResponseBody
	public boolean delResTypeDefine(ResTypeDefineInfo vo){
		logger.info("开始...删除ResTypeDefineInfo");
		logger.debug("delete by param resTypeID={}",vo.getResTypeID());
		try {
			resTypeDefService.deleteInfo(vo);
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage(),vo);
			return false;
		}
		logger.info("结束...删除ResTypeDefineInfo");
		return true ;
	}
	
	/**
	 * 增加信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addResTypeDefine")
	@ResponseBody
	public boolean addResTypeDefine(ResTypeDefineInfo vo){
		logger.info("开始...增加ResTypeDefineInfo");
		vo.setCreateTime(new Date());
		try {
			resTypeDefService.addInfo(vo);
		} catch (Exception e) {
			logger.error("插入异常："+e.getMessage(),vo);
			return false;
		}
		logger.info("结束...增加ResTypeDefineInfo");
		return true ;
	}
	
	/**
	 * 查找对象信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findResTypeDefine")
	@ResponseBody
	public ResTypeDefineInfo findResTypeDefine(ResTypeDefineInfo vo)throws Exception{
		logger.info("开始...查询SnmpDeviceSysOIDInfo");
		logger.debug("find by param resTypeID={}",vo.getResTypeID());
		vo = resTypeDefService.getInfoById(vo.getResTypeID());
		if(vo.getParentTypeId()!=-1){
			ResTypeDefineInfo resVo = resTypeDefService.getInfoById(vo.getParentTypeId());
			if(resVo != null){
				vo.setParentTypeName(resVo.getResTypeName());
			}	
		}
		logger.info("结束...查询SnmpDeviceSysOIDInfo");
		return vo;
	}
	

	/**
	 * 更新信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateResTypeDefine")
	@ResponseBody
	public boolean updateResTypeDefine(ResTypeDefineInfo vo){
		logger.info("开始...更新ResTypeDefineInfo");
		logger.debug("update by param resTypeID={}",vo.getResTypeID());
		vo.setLastModifyTime(new Date());		
		try {
			resTypeDefService.updateInfo(vo);
		} catch (Exception e) {
			logger.error("修改异常："+e.getMessage(),vo);
			return false;
		}
		logger.info("结束...更新ResTypeDefineInfo");
		return true ;
	}
	
	/**
	 * 树形初始化数据
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initTreeResTypeDefine")
	@ResponseBody
	public Map<String, Object> initTreeResTypeDefine(ResTypeDefineInfo vo) throws Exception {
		logger.info("开始...初始化树形数据");
		List<ResTypeDefineInfo> resTypeDefLst = resTypeDefService.getResTypeDefineTree("", "");
		String resTypeDefLstJson = JsonUtil.toString(resTypeDefLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("resTypeDefLstJson", resTypeDefLstJson);
		logger.info("结束...初始化树形数据");
		return result;
	}
	
	/**
	 * 批量删除信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delBathResTypeDefine")
	@ResponseBody
	public boolean delBathSnmpPen(String id){
		logger.info("开始...批量删除ResTypeDefineInfo");
		logger.debug("deleteBath by param resTypeID={}",id);
		try {
			resTypeDefService.deleteBathInfo(id);
		} catch (Exception e) {
			logger.error("删除异常："+e.getMessage(),id);
			return false;
		}
		logger.info("结束...批量删除ResTypeDefineInfo");
		return true ;
	}
}
