package com.fable.insightview.monitor.SToneU;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.SToneU.entity.MODeviceSt;
import com.fable.insightview.monitor.SToneU.entity.PerfStoneuBean;
import com.fable.insightview.monitor.SToneU.service.StoneuService;
import com.fable.insightview.monitor.host.entity.HostComm;
import com.fable.insightview.monitor.host.entity.KPINameDef;
import com.fable.insightview.monitor.perf.service.IPerfTaskService;
import com.fable.insightview.monitor.util.ConfigParameterCommon;
import com.fable.insightview.monitor.website.service.IWebSiteService;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.page.Page;

@Controller
@RequestMapping("/monitor/stoneu")
public class SToneUController {

	private static final Logger log = LoggerFactory.getLogger(SToneUController.class);
	private static final String STONEUINFO =".1.3.6.1.4.1.4320";
	public final static int JOBSTONEU = 98;
	@Autowired
	StoneuService stoneuService;
	@Autowired 
	IPerfTaskService perfTaskService;
	@Autowired
	IWebSiteService websiteService;
	/**
	 * 跳转页面
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryStoneu")
	public ModelAndView toStoneuList(ModelMap map,HttpServletRequest request){
		map.put("navigationBar",request.getParameter("navigationBar"));
		map.put("moclassID", request.getParameter("moclassID"));
		return new ModelAndView("monitor/stoneu/stoneu_list");
	}
	
	/**
	 * 查询斯特纽机房信息
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/stonuList")
	@ResponseBody
	public Map<String, Object> querystonuList(MODeviceSt vo,HttpServletRequest request) throws Exception {
		log.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MODeviceSt> page = new Page<MODeviceSt>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("moName", request.getParameter("moName"));
		paramMap.put("MOClassID", request.getParameter("MOClassID"));
		paramMap.put("MID", JOBSTONEU);
		page.setParams(paramMap);
		
		// 查询分页数据
		List<MODeviceSt> list = stoneuService.getStoneuList(page);
		getStoneuInfo(list);
		// 查询总记录数
		int totalCount = page.getTotalRecord();
		Map<String, Object> result = new HashMap<String, Object>();
		// 设置至前台显示
		result.put("total", totalCount);
		result.put("rows", list);
		log.info("结束...获取页面显示数据");
		return result;
	}
	
	/**
	 * 跳转到新增页面
	 * @return
	 */
	@RequestMapping("/toAdd")
	public ModelAndView toShowAcUPSAdd(HttpServletRequest request,ModelMap map) {
		map.put("moclassID", request.getParameter("moClassID"));
		return new ModelAndView("monitor/stoneu/stoneu_add");
	}
	
	/**
	 * 新增
	 * @param condition
	 * @param request
	 * @return
	 */
	@RequestMapping("/doAdd")
	@ResponseBody
	public boolean addCondition(MODeviceSt stone,HttpServletRequest request){
		int templateID = Integer.parseInt(request.getParameter("templateID"));
		String moTypeLstJson = request.getParameter("moTypeLstJson");
		
		return stoneuService.addDoBatch(stone,templateID,moTypeLstJson);
	}
	
	/**
	 * 跳转到更新页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toUpdate")
	public ModelAndView toModify(HttpServletRequest request,ModelMap map) {
		String moID = request.getParameter("moID");
		String moclassID = request.getParameter("moClassID");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("MOID",moID);
		MODeviceSt stoneu = stoneuService.queryByMOID(paramMap);
		int	templateID = perfTaskService.getTemplateID(Integer.parseInt(moID), Integer.parseInt(moclassID));
		stoneu.setTemplateID(templateID);
		map.put("stoneu",stoneu);
		return new ModelAndView("monitor/stoneu/stoneu_modify");
	}
	
	
	
	/**
	 * 更新
	 * @param condition
	 * @param request
	 * @return
	 */
	@RequestMapping("/doUpdate")
	@ResponseBody
	public boolean updateCondition(MODeviceSt stone,HttpServletRequest request){
		int templateID = Integer.parseInt(request.getParameter("templateID"));
		String moTypeLstJson = request.getParameter("moTypeLstJson");
		return stoneuService.updateInfo(stone,templateID,moTypeLstJson);
	}
	
	/**
	 * 校验该对象是否已存在
	 * @param request
	 * @return
	 */
	@RequestMapping("/toCheck")
	@ResponseBody
	public boolean checkMOdevice(HttpServletRequest request){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deviceIP", request.getParameter("deviceIP"));
		paramMap.put("MOID", request.getParameter("MOID"));
		int i = stoneuService.checkMOdevice(paramMap);
		return i>0?true:false;
	}
	/**
	 * 测试连接
	 * @param snmp
	 * @param request
	 * @return
	 */
	@RequestMapping("/test")
	@ResponseBody
	public int test(ConfigParameterCommon snmp,HttpServletRequest request){
		boolean flag = false;
		int  manufacturerID=0;
		try {
			flag = stoneuService.getTestResult(snmp);
		} catch (Exception e) {
			log.error("获取测试结果失败");
			return manufacturerID;
		}
		if(flag){
			manufacturerID = stoneuService.getResManufacturerID(STONEUINFO);
		}
		return manufacturerID;
	}
	
	/**
	 * 跳转到性能页面
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/perfInfo")
	public ModelAndView toGetPerfInfo(HttpServletRequest request,ModelMap map) {
		String deviceIP = request.getParameter("deviceIp");
		List<PerfStoneuBean> perfList =  stoneuService.perfList(deviceIP);
		Map<String,PerfStoneuBean> tempHumMap = new TreeMap<String,PerfStoneuBean>();
		Map<String,PerfStoneuBean> somkeMap = new TreeMap<String,PerfStoneuBean>();
		Map<String,PerfStoneuBean> soundLightMap = new TreeMap<String,PerfStoneuBean>();
		Map<String,PerfStoneuBean> contatMap = new TreeMap<String,PerfStoneuBean>();
		for (PerfStoneuBean perfStoneuBean : perfList) {
			if(perfStoneuBean.getMoClassID()==99){
				tempHumMap.put(perfStoneuBean.getMoName(), perfStoneuBean);
			}else if(perfStoneuBean.getMoClassID() ==106){
				somkeMap.put(perfStoneuBean.getMoName(), perfStoneuBean);
			}else if(perfStoneuBean.getMoClassID() ==107){
				soundLightMap.put(perfStoneuBean.getMoName(), perfStoneuBean);
			}else if(perfStoneuBean.getMoClassID() ==108){
				contatMap.put(perfStoneuBean.getMoName(), perfStoneuBean);
			}
		}
		request.setAttribute("tempHumMap", tempHumMap);
		request.setAttribute("somkeMap", somkeMap);
		request.setAttribute("soundLightMap", soundLightMap);
		request.setAttribute("contatMap", contatMap);
		return new ModelAndView("monitor/stoneu/perfInfo_list");
	}
	
	/**
	 * 可持续时间
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<MODeviceSt> getStoneuInfo(List<MODeviceSt> list) throws Exception{
		int period=1;
		long curr=0;
		long currTime=stoneuService.getDateNow().getTime();
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				MODeviceSt siteBean = list.get(i);
				DateFormat  fmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date updateAlarmTime = fmt.parse(siteBean.getUpdateAlarmTime());
				Date collectTime =null;
				if(siteBean.getCollectTime() !=null){
					collectTime=fmt.parse(siteBean.getCollectTime());
				}
				if(siteBean.getDoIntervals()==null || "".equals(siteBean.getDoIntervals())){
					period=siteBean.getDefDoIntervals()*websiteService.getConfParamValue()*60000;
				}else{
					period=siteBean.getDoIntervals()*websiteService.getConfParamValue()*1000;
				}
				if (collectTime != null) {
					curr=currTime-collectTime.getTime();
					if(curr<=period){
						if (siteBean.getOperStatus().equals(1)) {
							siteBean.setOperaTip("UP");
							siteBean.setOperstatusdetail("up.png");
						} else if (siteBean.getOperStatus().equals(2)) {
							siteBean.setOperaTip("DOWN");
							siteBean.setOperstatusdetail("down.png");
						} 
						if (updateAlarmTime != null) {
							String durationTime = HostComm.getDurationToTime(currTime-updateAlarmTime.getTime());
							siteBean.setDurationTime(durationTime);
						}else{
							siteBean.setDurationTime("");
						}
					}else{
						siteBean.setOperaTip("未知");
						siteBean.setOperstatusdetail("unknown.png");
						String durationTime = HostComm.getDurationToTime(currTime-collectTime.getTime());
						siteBean.setDurationTime(durationTime);
						
					}	
				}else{
						siteBean.setOperaTip("未知");
						siteBean.setOperstatusdetail("unknown.png");
						siteBean.setDurationTime("");
					}
			}
		}
		
		return list;
		
	}
}
