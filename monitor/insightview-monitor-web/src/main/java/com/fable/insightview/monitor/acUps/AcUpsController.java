package com.fable.insightview.monitor.acUps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.monitor.AcUPS.entity.AirConditionBean;
import com.fable.insightview.monitor.AcUPS.entity.PerfRoomConditionsBean;
import com.fable.insightview.monitor.AcUPS.entity.perfEastUpsBean;
import com.fable.insightview.monitor.AcUPS.entity.perfInvtUpsBean;
import com.fable.insightview.monitor.AcUPS.service.IAcConditionService;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.perf.mapper.PerfTaskInfoMapper;
import com.fable.insightview.monitor.perf.service.IPerfTaskService;
import com.fable.insightview.monitor.util.ConfigParameterCommon;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/monitor/acUPS")
public class AcUpsController {
	
	@Autowired
	IAcConditionService  acConditionService;
	@Autowired 
	IPerfTaskService perfTaskService;
	@Autowired
	IOracleService orclService;
	@Autowired 
	PerfTaskInfoMapper perfTaskInfoMapper;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger logger = LoggerFactory .getLogger(AcUpsController.class);
	// 空调moclassID
	private static final  int CONDITION =96;
	// UPS moclassID
	private  static final int UPS=73;
	// 易事特厂商ID
	private static final String EASTNEMANUFACTURERID = "75" ;
	// 英威腾厂商ID
	private static final String INVTMANUFACTURERID = "76" ;
	/***
	 * 跳转页面
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAcUPS")
	public ModelAndView toAcUpsList(ModelMap map,HttpServletRequest request){
		map.put("navigationBar",request.getParameter("navigationBar"));
		String moClassID= request.getParameter("MOClassID");
		request.setAttribute("MOClassID", moClassID);
		return new ModelAndView("monitor/acUPS/acUPS_list");
	}

	
	/***
	 * 数据展示
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/acUPSList")
	@ResponseBody
	public Map<String, Object> queryAcUPSlist(AirConditionBean vo,
			HttpServletRequest request) throws Exception {
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<AirConditionBean> page = new Page<AirConditionBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ResManufacturerName", request.getParameter("txtAcditionName"));
		String type= request.getParameter("txtAcditionType");
		if(type !=null && !type.equals("")){
			if(Integer.parseInt(type) == CONDITION){
				paramMap.put("MOClassID", type);
			}else if (Integer.parseInt(type) == UPS){
				paramMap.put("MOClassID", type);
			}
		}else{
			paramMap.put("MOClassID", request.getParameter("MOClassID"));
		}
		page.setParams(paramMap);
		
		// 查询分页数据
		List<AirConditionBean> list = acConditionService.queryAcConditionList(page);
		
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	/***
	 * 跳转新增页面
	 * @return
	 */
	@RequestMapping("/doAddAcUPS")
	public ModelAndView toShowAcUPSAdd() {
		return new ModelAndView("monitor/acUPS/acUPS_add");
	}
	
	/***
	 * 新增
	 * @param condition
	 * @return
	 */
	@RequestMapping("/addCondition")
	@ResponseBody
	public boolean addCondition(AirConditionBean condition,HttpServletRequest request){
		boolean flag= false;
		int templateID = Integer.parseInt(request.getParameter("templateID"));
		String moTypeLstJson = request.getParameter("moTypeLstJson");
		  flag= acConditionService.insertData(condition);
		if(flag){
			return  acConditionService.addSitePerfTask(condition,templateID,moTypeLstJson);
		}
		return flag;
	}
	
	/**
	 * 更新
	 * @param condition
	 * @return
	 */
	@RequestMapping("/updateCondition")
	@ResponseBody
	public boolean updateCondition(AirConditionBean condition,HttpServletRequest request){
		int templateID = Integer.parseInt(request.getParameter("templateID"));
		String moTypeLstJson = request.getParameter("moTypeLstJson");
		return acConditionService.updateData(condition,templateID,moTypeLstJson);
	}
	
	/**
	 * 跳转至编辑界面
	 */
	@RequestMapping("/toShowAcditionModify")
	public ModelAndView toShowAcditionModify(HttpServletRequest request,ModelMap map) {
		String moID = request.getParameter("moID");
		String moclassID = request.getParameter("moClassID");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("MOID",moID);
		AirConditionBean   airCondition =acConditionService.queryAcditionByMOID(paramMap);
		int templateID = 0;
		// 空调
		if(Integer.parseInt(moclassID) == CONDITION){
			templateID= perfTaskService.getTemplateID(Integer.parseInt(moID), CONDITION);
		}else if(Integer.parseInt(moclassID) == UPS){
			// ups
			templateID = perfTaskService.getTemplateID(Integer.parseInt(moID), UPS);
		}
		airCondition.setTemplateID(templateID);
		map.put("airCon",airCondition);
		return new ModelAndView("monitor/acUPS/acUPS_modify");
	}
	/***
	 * 测试连接
	 * @param webSite
	 * @return
	 */
	@RequestMapping("/testCondition")
	@ResponseBody
	public JSONObject testCondition(ConfigParameterCommon snmp,HttpServletRequest request){
		String MOClassID = request.getParameter("MOClassID");
		return acConditionService.getTestResult(snmp,MOClassID);
	}
	
	/**
	 * 验证SNMP认证中改设备IP是否存在
	 * 
	 */
	@RequestMapping("/checkSNMPCommunity")
	@ResponseBody
	public boolean checkSNMPCommunity(AirConditionBean condition) {
		return acConditionService.checkbeforeAdd(condition);
	}
	
	/**
	 * 跳转到设备详情页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toperfRoom")
	public ModelAndView toperfRoomInfo(HttpServletRequest request,ModelMap map) {
		String moid = request.getParameter("MOID");
		String moclassID = request.getParameter("moClassID");
		String neManufacturerID = request.getParameter("neManufacturerID");
		ModelAndView model = new ModelAndView();
		if(Integer.parseInt(moclassID)==CONDITION){
			PerfRoomConditionsBean  roomBean = acConditionService.queryperfRoomInfo(Integer.parseInt(moid));
			map.put("moid", moid);
			map.put("room", roomBean);
			model.setViewName("monitor/acUPS/perfRoom");
		}else if(Integer.parseInt(moclassID) == UPS){
			// 易事特
			if(neManufacturerID.equals(EASTNEMANUFACTURERID)){
				perfEastUpsBean  upsBean = acConditionService.getUpsInfo(Integer.parseInt(moid));
				map.put("room", upsBean);
				model.setViewName("monitor/acUPS/perfUps");
			}else if(neManufacturerID.equals(INVTMANUFACTURERID)){
				// 英威腾
				perfInvtUpsBean  InvtUps = acConditionService.getInvtUpsInfo(Integer.parseInt(moid));
				map.put("invt", InvtUps);
				model.setViewName("monitor/acUPS/perfInvtUps");
			}
		}
		return model;
	}
}
