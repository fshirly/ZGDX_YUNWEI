package com.fable.insightview.monitor.environmentmonitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.discover.service.IMObjectResSynService;
import com.fable.insightview.monitor.environmentmonitor.entity.MOReader;
import com.fable.insightview.monitor.environmentmonitor.entity.MOTag;
import com.fable.insightview.monitor.environmentmonitor.entity.MOZoneManagerBean;
import com.fable.insightview.monitor.environmentmonitor.entity.TagKPINameDef;
import com.fable.insightview.monitor.environmentmonitor.service.IEnvMonitorService;
import com.fable.insightview.platform.page.Page;
import com.fable.insightview.platform.common.helper.RestHepler;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.sysinit.SystemParamInit;
/**
 * @Description: 设备列表
 * @author wsp
 * @Date 2014-11-11
 */
@Controller
@RequestMapping("/monitor/envManager")
public class EnvMonitorManagerController {
	
	@Autowired
	IEnvMonitorService envService;

	@Autowired
	IMObjectResSynService mobjectResSyn;
	
	private final Logger logger = LoggerFactory
			.getLogger(EnvMonitorManagerController.class);
	
	@RequestMapping("/toZoneManagerList")
	@ResponseBody
	public ModelAndView toZoneManagerList(HttpServletRequest request,ModelMap map) {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("id", request.getParameter("id"));
		map.put("mOClassID", request.getParameter("mOClassID"));
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/zoneManager_list");
	}
	
	/**
	 * 获取ZoneManager页面显示数据
	 * @param request
	 */
	@RequestMapping("/listZoneManager")
	@ResponseBody
	public Map<String, Object> listZoneManager(HttpServletRequest request){
		
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOZoneManagerBean> page = new Page<MOZoneManagerBean>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("ipAddress",request.getParameter("ipAddress"));
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOZoneManagerBean> list =envService.queryZoneManagerList(page);
			// 查询总记录数
			int totalCount = page.getTotalRecord();
			// 设置至前台显示
			result.put("total", totalCount);
			result.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束...获取页面显示数据");
		
		return result;
	}
	
	@RequestMapping("/toRoomList")
	@ResponseBody
	public ModelAndView toRoomList(HttpServletRequest request,ModelMap map) {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/room_list");
	}
	
	/**
	 * 获取阅读器页面显示数据
	 * @param request
	 */
	@RequestMapping("/listRoom")
	@ResponseBody
	public Map<String, Object> listRoom(HttpServletRequest request){
		
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOReader> page = new Page<MOReader>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(!"".equals(request.getParameter("parentID")) && request.getParameter("parentID") != null){
			paramMap.put("parentID", request.getParameter("parentID"));
		}
		paramMap.put("readerLabel", request.getParameter("readerName"));
		paramMap.put("iPAddress",request.getParameter("iPAddress"));
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOReader> list =envService.queryList(page);
			// 查询总记录数
			int totalCount = page.getTotalRecord();
			// 设置至前台显示
			result.put("total", totalCount);
			result.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束...获取页面显示数据");
		
		return result;
	}
	
	@RequestMapping("/toTagList")
	@ResponseBody
	public ModelAndView toTagList(HttpServletRequest request,ModelMap map) {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		Integer moClassId=Integer.parseInt(request.getParameter("moClassId"));
		map.put("moClassId", moClassId);
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/tag_list");
	}
	
	/**
	 * 获取标签页面显示数据
	 * @param request
	 */
	@RequestMapping("/listTag")
	@ResponseBody
	public Map<String, Object> listTag(HttpServletRequest request){
		
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOTag> page = new Page<MOTag>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Integer moClassId=Integer.parseInt(request.getParameter("moClassId"));
		if(moClassId==52){
			moClassId=null;
		}
		paramMap.put("moClassId",moClassId );
		paramMap.put("iPAddress",request.getParameter("iPAddress"));
		paramMap.put("classLable",request.getParameter("classLable"));
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOTag> list =envService.queryTagList(page);
			// 查询总记录数
			int totalCount = page.getTotalRecord();
			// 设置至前台显示
			result.put("total", totalCount);
			result.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束...获取页面显示数据");
		
		return result;
	}
	
	@SuppressWarnings("all")
	@RequestMapping("/showCmdb")
	public ModelAndView showCmdb(HttpServletRequest request, ModelMap map) {
		
		Integer moClassID = Integer.parseInt(request.getParameter("moClassId"));
		String moids = request.getParameter("moids");
		String TypeName = this.requestHanlder(moClassID, "type");
			// [{"id":4,"name":"附属资源-机柜U位"}]
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (!"[]".equals(TypeName) && TypeName !=null) {
			JSONArray jsonArray = JSONArray.fromObject(TypeName);
			List<Map<String, Object>> mapListJson = (List) jsonArray;
			map.put("typeLst", mapListJson);
			
			Integer id = (Integer)mapListJson.get(0).get("id");
			String assetName = this.requestHanlder(id, "asset");
			
			JSONObject jsonObj = JSONObject.fromObject(assetName);
			map.put("assetLst", jsonObj.get("assetTypeList").toString());
			map.put("isAsset",jsonObj.get("isAsset").toString());
		}
		map.put("moClassID", moClassID);
		map.put("moids", moids);
		return new ModelAndView("monitor/device/envCmdb");
	}
	
	/**
	 * 获取设备页面显示数据
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/listAssetName")
	@ResponseBody
	public Map<String, Object> listAssetName(HttpServletRequest request) {
		logger.info("开始...获取页面显示数据");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Integer id = Integer.parseInt(request.getParameter("typeId"));
			String assetName = this.requestHanlder(id, "asset");
			JSONObject jsonArray = JSONObject.fromObject(assetName);
			// {"assetTypeList":[{"id":11037,"name":"阅读器"}],"isAsset":1}
			result.put("assetNameLstJson", jsonArray.get("assetTypeList").toString());
			result.put("isAsset",jsonArray.get("isAsset").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return result;
	}
	
	private String requestHanlder(Integer moClassID, String type) {
		String path = "";
		if ("type".equals(type)) {
			path = "/rest/cmdb/restypedef/queryResTypeDefineByMoTypeId";
		}else if("asset".equals(type)) {
			path = "/rest/cmdb/restypedef/queryAssetTypeIdByTypeDefineId";
		}

		try {
			String url = SystemParamInit.getValueByKey("rest.resSychron.url");
			logger.info("Request parameter url = " + url);
			String username = SystemParamInit.getValueByKey("rest.username");
			String password = SystemParamInit.getValueByKey("rest.password");

			// 拼接获取单板接口URL
			StringBuffer basePath = new StringBuffer();
			basePath.append(url);
			basePath.append(path);
			// 设置请求头信息
			HttpHeaders requestHeaders = RestHepler.createHeaders(username,
					password);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(
					moClassID, requestHeaders);
			RestTemplate rest = new RestTemplate();
			// 参数1：请求地址 ，参数2：请求方式，参数3：请求头信息，参数4：相应类
			ResponseEntity<String> rssResponse = rest.exchange(basePath
					.toString(), HttpMethod.POST, requestEntity, String.class);
			if (null != rssResponse) {
				return rssResponse.getBody();
			}
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * * 加载CMDB配置信息
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("/doCmdbInfo")
	@ResponseBody
	public boolean doCmdbInfo(HttpServletRequest request){
		
		logger.info("开始...加载CMBD"); 
		boolean flag = true;
		Integer moClassID = Integer.parseInt(request.getParameter("moClassID"));
		Integer resTypeID = Integer.parseInt(request.getParameter("typeID"));
		String moids = request.getParameter("moids");
		String assetTypeId = request.getParameter("assetTypeId");
		logger.info("moClassID=" + moClassID + " typeID=" + resTypeID+ " moids=" + moids+" assetTypeId="+assetTypeId);
		try {
			new Thread(new synchronRes(moClassID, resTypeID, moids,"",assetTypeId)).start(); 
			
		} catch (Exception e) {
			logger.error("CMDB配置异常：" + e.getMessage());
			return false;
		}
		return flag;
	}
	
	class synchronRes implements Runnable {
		int moClassID;
		int resTypeID;
		String moids;
		String resids;
		String assetTypeId;
		
		public synchronRes(int moClassID,int resTypeID,String moids,String resids,String assetTypeId) {
			this.moClassID = moClassID; 
			this.resTypeID = resTypeID;
			this.moids = moids;
			this.resids=resids;
			this.assetTypeId = assetTypeId;
		}

		public void run() { 
			mobjectResSyn.synchronRes(moClassID, resTypeID, moids,"",assetTypeId); 
		}
	}
	
	/**
	 * 获得阅读器的信息
	 */
	@RequestMapping("findMOReaderInfo")
	@ResponseBody
	public MOReader findMOReaderInfo(MOReader moReader){
		return envService.getMoReaderByMOID(moReader.getMoID());
	}
	
	/**
	 * 获得标签的信息
	 */
	@RequestMapping("findMOTagInfo")
	@ResponseBody
	public MOTag findMOTagInfo(MOTag moTag){
		return envService.getMOTagBYMOID(moTag.getMoID());
	}
	
	@RequestMapping("/toReaderList")
	@ResponseBody
	public ModelAndView toReaderList(HttpServletRequest request,ModelMap map) {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		map.put("parentID", request.getParameter("parentID"));
		return new ModelAndView("monitor/component/reader_list");
	}
	
	
	@RequestMapping("/toTemperatureTagList")
	@ResponseBody
	public ModelAndView toTemperatureTagList(HttpServletRequest request,ModelMap map) {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		Integer moClassId=Integer.parseInt(request.getParameter("moClassId"));
		map.put("moClassId", moClassId);
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/temperatureTag_list");
	}
	
	/**
	 * 获取标签页面显示数据
	 * @param request
	 */
	@RequestMapping("/listTemperatureTag")
	@ResponseBody
	public Map<String, Object> listTemperatureTag(HttpServletRequest request){
		
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOTag> page = new Page<MOTag>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Integer moClassId=Integer.parseInt(request.getParameter("moClassId"));
		paramMap.put("moClassId",moClassId );
		paramMap.put("tagID",request.getParameter("tagID"));
		paramMap.put("lowBattery", TagKPINameDef.LOWBATTERY);
		paramMap.put("temperature", TagKPINameDef.TEMPERATURE);
		paramMap.put("messageLossRate", TagKPINameDef.MESSAGELOSSRATE);
		paramMap.put("sensorDisconnected", TagKPINameDef.SENSORDISCONNECTED);
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOTag> list =envService.queryTemperatureTagList(page);
			// 查询总记录数
			int totalCount = page.getTotalRecord();
			// 设置至前台显示
			result.put("total", totalCount);
			result.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	//水带
	@RequestMapping("/toWaterHoseTagList")
	@ResponseBody
	public ModelAndView toWaterHoseTagList(HttpServletRequest request,ModelMap map) {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		Integer moClassId=Integer.parseInt(request.getParameter("moClassId"));
		map.put("moClassId", moClassId);
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/waterHoseTag_list");
	}
	
	/**
	 * 获取标签页面显示数据
	 * @param request
	 */
	@RequestMapping("/listWaterHoseTag")
	@ResponseBody
	public Map<String, Object> listWaterHoseTag(HttpServletRequest request){
		
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOTag> page = new Page<MOTag>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Integer moClassId=Integer.parseInt(request.getParameter("moClassId"));
		paramMap.put("moClassId",moClassId );
		paramMap.put("tagID",request.getParameter("tagID"));
		paramMap.put("fluidDetected", TagKPINameDef.FLUIDDETECTED);
		paramMap.put("tamper", TagKPINameDef.TAMPER);
		paramMap.put("lowBattery", TagKPINameDef.LOWBATTERY);
		paramMap.put("sensorDisconnected", TagKPINameDef.SENSORDISCONNECTED);
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOTag> list =envService.queryWaterHoseTagList(page);
			// 查询总记录数
			int totalCount = page.getTotalRecord();
			// 设置至前台显示
			result.put("total", totalCount);
			result.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	
	//温湿度
	@RequestMapping("/toTemperatureHumidityTagList")
	@ResponseBody
	public ModelAndView toTemperatureHumidityTagList(HttpServletRequest request,ModelMap map) {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		Integer moClassId=Integer.parseInt(request.getParameter("moClassId"));
		map.put("moClassId", moClassId);
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/temperatureHumidityTag_list");
	}
	
	/**
	 * 获取标签页面显示数据
	 * @param request
	 */
	@RequestMapping("/listTemperatureHumidityTag")
	@ResponseBody
	public Map<String, Object> listTemperatureHumidityTag(HttpServletRequest request){
		
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOTag> page = new Page<MOTag>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Integer moClassId=Integer.parseInt(request.getParameter("moClassId"));
		paramMap.put("moClassId",moClassId );
		paramMap.put("tagID",request.getParameter("tagID"));
		paramMap.put("dewPoint", TagKPINameDef.DEWPOINT);
		paramMap.put("humidity", TagKPINameDef.HUMIDITY);
		paramMap.put("lowBattery", TagKPINameDef.LOWBATTERY);
		paramMap.put("messageLossRate", TagKPINameDef.MESSAGELOSSRATE);
		paramMap.put("temperature", TagKPINameDef.TEMPERATURE);
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOTag> list =envService.queryTemperatureHumidityTagList(page);
			// 查询总记录数
			int totalCount = page.getTotalRecord();
			// 设置至前台显示
			result.put("total", totalCount);
			result.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	
	//门磁感应
	@RequestMapping("/toDoorMagneticTagList")
	@ResponseBody
	public ModelAndView toDoorMagneticTagList(HttpServletRequest request,ModelMap map) {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		Integer moClassId=Integer.parseInt(request.getParameter("moClassId"));
		map.put("moClassId", moClassId);
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/doorMagneticTag_list");
	}
	
	/**
	 * 获取标签页面显示数据
	 * @param request
	 */
	@RequestMapping("/listDoorMagneticTag")
	@ResponseBody
	public Map<String, Object> listDoorMagneticTag(HttpServletRequest request){
		
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOTag> page = new Page<MOTag>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Integer moClassId=Integer.parseInt(request.getParameter("moClassId"));
		paramMap.put("moClassId",moClassId );
		paramMap.put("tagID",request.getParameter("tagID"));
		paramMap.put("tamper", TagKPINameDef.TAMPER);
		paramMap.put("motion", TagKPINameDef.MOTION);
		paramMap.put("lowBattery", TagKPINameDef.LOWBATTERY);
		paramMap.put("doorOpen", TagKPINameDef.DOOROPEN);
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOTag> list =envService.queryDoorMagneticTagList(page);
			// 查询总记录数
			int totalCount = page.getTotalRecord();
			// 设置至前台显示
			result.put("total", totalCount);
			result.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束...获取页面显示数据");
		return result;
	}
	
	
	//干节点
	@RequestMapping("/toDryContactTagList")
	@ResponseBody
	public ModelAndView toDryContactTagList(HttpServletRequest request,ModelMap map) {
		map.put("flag", request.getParameter("flag"));// 隐藏字段标志
		Integer moClassId=Integer.parseInt(request.getParameter("moClassId"));
		map.put("moClassId", moClassId);
		map.put("navigationBar",request.getParameter("navigationBar"));
		return new ModelAndView("monitor/device/dryContactTag_list");
	}
	
	/**
	 * 获取标签页面显示数据
	 * @param request
	 */
	@RequestMapping("/listDryContactTag")
	@ResponseBody
	public Map<String, Object> listDryContactTag(HttpServletRequest request){
		
		logger.info("开始...获取页面显示数据");
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Page<MOTag> page = new Page<MOTag>();
		// 设置分页参数
		page.setPageNo(flexiGridPageInfo.getPage());
		page.setPageSize(flexiGridPageInfo.getRp());
		// 设置查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Integer moClassId=Integer.parseInt(request.getParameter("moClassId"));
		paramMap.put("moClassId",moClassId );
		paramMap.put("tagID",request.getParameter("tagID"));
		paramMap.put("dryContactOpen", TagKPINameDef.DRYCONTACTOPEN);
		paramMap.put("motion", TagKPINameDef.MOTION);
		paramMap.put("lowBattery", TagKPINameDef.LOWBATTERY);
		paramMap.put("tamper", TagKPINameDef.TAMPER);
		page.setParams(paramMap);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 查询分页数据
			List<MOTag> list =envService.queryDryContactTagList(page);
			// 查询总记录数
			int totalCount = page.getTotalRecord();
			// 设置至前台显示
			result.put("total", totalCount);
			result.put("rows", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("结束...获取页面显示数据");
		return result;
	}
}
