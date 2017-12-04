package com.fable.insightview.monitor.portal.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.fable.insightview.monitor.database.entity.MODBMSServerBean;
import com.fable.insightview.monitor.database.service.IOracleService;
import com.fable.insightview.monitor.host.entity.MODevice;
import com.fable.insightview.monitor.middleware.tomcat.entity.MOMiddleWareJMXBean;
import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareService;
import com.fable.insightview.monitor.mobject.entity.MObjectDefBean;
import com.fable.insightview.monitor.mobject.mapper.MobjectInfoMapper;
import com.fable.insightview.monitor.portal.entity.WidgetInfoBean;
import com.fable.insightview.monitor.portal.mapper.WidgetInfoMapper;
import com.fable.insightview.monitor.portal.service.IEditInfoService;
import com.fable.insightview.monitor.topo.entity.TopoBean;
import com.fable.insightview.platform.common.helper.RestHepler;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.sysinit.SystemParamInit;

@Controller
@RequestMapping("/monitor/editManager")
public class EditInfoController {

	private final Logger logger = LoggerFactory
			.getLogger(EditInfoController.class);

	@Autowired
	IEditInfoService editService;
	@Autowired
	WidgetInfoMapper widgetInfoMapper;
	@Autowired
	MobjectInfoMapper mobjectInfoMapper;
	@Autowired
	IMiddlewareService imiddlewareService;
	@Autowired
	IOracleService orclService;

	/**
	 * 查询设备列表
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toDeviceEdit")
	public ModelAndView toDeviceEdit(HttpServletRequest request, ModelMap map) {
		// widgetId=1&widgetTitle=最近告警&url="/monitor/gaugeChartManage/findChartIfUsage?moID={moID};moClass={moClass}"
		String widgetId = request.getParameter("widgetId");
		String widgetTitle = request.getParameter("widgetTitle");
		String widgetName = request.getParameter("widgetName");
		String url = request.getParameter("url");
		// 映射关系
		Map<String, Integer> mapping = new HashMap<String, Integer>();
		List<MObjectDefBean> mobLst = mobjectInfoMapper.getMObjectForEdit();
		if (mobLst != null) {
			for (int i = 0; i < mobLst.size(); i++) {
				mapping.put(mobLst.get(i).getClassName(), mobLst.get(i)
						.getClassId());
			}
		}
		try {
			widgetTitle = java.net.URLDecoder.decode(widgetTitle, "UTF-8");
			widgetName = java.net.URLDecoder.decode(widgetName, "UTF-8");
			WidgetInfoBean widBean = this.widgetInfoMapper
					.getWidgetByWidgetId(widgetId);
			// 获取filter
			String filter = widBean.getWidgetFilter();
			StringBuilder idList = new StringBuilder();
			for (String f : filter.split("\\s*,\\s*", -1)) {// 字符串用逗号分隔成数组，逗号两边可以有空格
				if (mapping.containsKey(f)) {
					idList.append(mapping.get(f)).append(",");
				}
			}

			if (idList.length() == 0) {
				idList.append(7);// 结果为空的处理 查询 物理主机
			} else {
				idList.setLength(idList.length() - 1);// 删除最后一个逗号
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("moClassID", idList);
			List<MODevice> deviceLst = editService.getDeviceName(params);
			map.put("deviceLst", deviceLst);
			map.put("widgetTitle", widgetTitle);
			map.put("widgetName", widgetName);
			map.put("widgetId", widgetId);
			map.put("url", url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("monitor/portaledit/deviceEdit");
	}

	/**
	 * 获取moClassID
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMoClassIDBymoid")
	@ResponseBody
	public Map<String, Object> getMoClassIDBymoid(HttpServletRequest request) {
		logger.info("开始...获取页面显示数据");
		Map<String, Object> result = new HashMap<String, Object>();
		Integer moClassID = null;
		try {
			Integer moid = Integer.parseInt(request.getParameter("moid"));
			if (moid != null) {
				moClassID = this.editService.getMoClassIDBymoid(moid);
			}
			Map<Integer, String> mapping = new HashMap<Integer, String>();
			mapping.put(7, "host");
			mapping.put(8, "vhost");
			mapping.put(9, "vm");
			mapping.put(5, "router");
			mapping.put(6, "switcher");
			result.put("moClassID", mapping.get(moClassID));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * topo编辑界面
	 */
	@RequestMapping("/toTopoEdit")
	@ResponseBody
	public ModelAndView toTopoEdit(HttpServletRequest request, ModelMap map) {
		String widgetId = request.getParameter("widgetId");
		String widgetTitle = request.getParameter("widgetTitle");
		String widgetName = request.getParameter("widgetName");
		try {
			widgetTitle = java.net.URLDecoder.decode(widgetTitle, "UTF-8");
			widgetName = java.net.URLDecoder.decode(widgetName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = request.getParameter("url");
		String[] urlStr = url.split("\\?");
		String[] topoIDStr = urlStr[1].split("\\=");
		String topoId = topoIDStr[1];
		List<TopoBean> topoLst = editService.getAllTopo();
		map.put("topoLst", topoLst);
		map.put("topoId", topoId);
		map.put("widgetTitle", widgetTitle);
		map.put("widgetName", widgetName);
		map.put("widgetId", widgetId);
		map.put("url", url);
		return new ModelAndView("monitor/portaledit/topoEdit");
	}

	/**
	 * 中间件 数据库 配置编辑
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/toMiddleDBEdit")
	@ResponseBody
	public ModelAndView toMiddleDBEdit(HttpServletRequest request, ModelMap map) {
		String widgetId = request.getParameter("widgetId");
		String widgetTitle=request.getParameter("widgetTitle");
		String widgetName = request.getParameter("widgetName");
		String url = request.getParameter("url");
		try {
			widgetTitle = java.net.URLDecoder.decode(widgetTitle, "UTF-8");
			widgetName = java.net.URLDecoder.decode(widgetName, "UTF-8");
			url = java.net.URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		WidgetInfoBean widBean = this.widgetInfoMapper
				.getWidgetByWidgetId(widgetId);
		// 获取filter
		String filter = widBean.getWidgetFilter();
		String moID = getStr(url,"str");
		MODBMSServerBean moBean = new MODBMSServerBean();
		MOMiddleWareJMXBean miBean = new MOMiddleWareJMXBean();
		MObjectDefBean mob = mobjectInfoMapper.getByClassName(filter);
		if ((url != null || "".equals(url)) && !"{moID}".equals(moID)) {

			if (moID != null
					&& (filter.contains("Oracle") || filter.contains("Mysql")
							|| filter.contains("DB2_DB") || filter
							.contains("DB2_INSTANCE"))) {
				moBean = orclService.selectMoDbmsByPrimaryKey(Integer
						.parseInt(moID));
				if (moBean != null) {
					map.put("moClassId", moBean.getMoClassId());
					map.put("ipt_deviceIp", moBean.getIp());
					map.put("ipt_deviceId", moBean.getMoid());
					map.put("portType", filter);
				}
			} else if (moID != null
					&& (filter.contains("Tomcat")
							|| filter.contains("Websphere") || filter
							.contains("Weblogic"))) {
				miBean = imiddlewareService.selectMoMidByPrimaryKey(Integer
						.parseInt(moID));
				if (miBean != null) {
					map.put("moClassId", miBean.getMoClassId());
					map.put("ipt_deviceIp", miBean.getIp());
					map.put("ipt_deviceId", miBean.getMoId());
					map.put("portType", filter);
				}
			}
		} else {
			if (filter.contains("Oracle") || filter.contains("Mysql")
					|| filter.contains("DB2_DB")
					|| filter.contains("DB2_INSTANCE")) {
				List<MODBMSServerBean> moLst = orclService.queryAll();
				if (moLst != null && moLst.size() != 0) {
					map.put("moClassId", moLst.get(0).getMoClassId());
					map.put("ipt_deviceIp", moLst.get(0).getIp());
					map.put("ipt_deviceId", moLst.get(0).getMoid());
				} else {
					map.put("moClassId", mob.getClassId());
					map.put("ipt_deviceIp", "");
					map.put("ipt_deviceId", "");
				}

			} else if (filter.contains("Tomcat")
					|| filter.contains("Websphere")
					|| filter.contains("Weblogic")) {
				List<MOMiddleWareJMXBean> moLst = imiddlewareService.queryAll();
				if (moLst != null && moLst.size() != 0) {
					map.put("moClassId", moLst.get(0).getMoClassId());// 当查询有数据时
					// 取第一条
					map.put("ipt_deviceIp", moLst.get(0).getIp());
					map.put("ipt_deviceId", moLst.get(0).getMoId());
				} else {
					map.put("moClassId", mob.getClassId()); // 当查询没有数据时 根据 过滤条件
					// 隐藏 classID
					map.put("ipt_deviceIp", "");
					map.put("ipt_deviceId", "");
				}

			}

			map.put("portType", filter);
		}
		map.put("widgetTitle", widgetTitle);
		map.put("widgetName", widgetName);
		map.put("widgetId", widgetId);
		map.put("url", url);
		return new ModelAndView("monitor/portaledit/middleDBEdit");
	}

	/**
	 * 跳转到3d编辑页面 liujinbing 2015-02-10
	 */
	@RequestMapping("/toRoom3dEdit")
	public @ResponseBody
	ModelAndView toRoom3dEdit(HttpServletRequest request, ModelMap map) {
		String widgetId = request.getParameter("widgetId");
		String widgetTitle = request.getParameter("widgetTitle");
		String widgetName = request.getParameter("widgetName");
		String url = request.getParameter("url");

		try {
			widgetTitle = java.net.URLDecoder.decode(widgetTitle, "UTF-8");
			widgetName = java.net.URLDecoder.decode(widgetName, "UTF-8");

			String restUrl = SystemParamInit.getValueByKey("rest.room3d.url");
			String username = SystemParamInit.getValueByKey("rest.username");
			String password = SystemParamInit.getValueByKey("rest.password");

			// 拼接获取单板接口URL
			StringBuffer basePath = new StringBuffer();
			basePath.append(restUrl);
			basePath.append("/rest/cmdb/3d/room/all");

			// 设置请求头信息
			HttpHeaders requestHeaders = RestHepler.createHeaders(username,
					password);

			HttpEntity<Object> requestEntity = new HttpEntity<Object>(null,
					requestHeaders);
			RestTemplate rest = new RestTemplate();

			// 参数1：请求地址 ，参数2：请求方式，参数3：请求头信息，参数4：相应类
			ResponseEntity<String> rssResponse = rest.exchange(basePath
					.toString(), HttpMethod.POST, requestEntity, String.class);

			List<Map<String, Object>> roomList = null;

			if (null != rssResponse) {
				String datas = openFilter(request, rssResponse.getBody());
				roomList = JsonUtil.toList(datas);
			}
			
			
			String roomId = null;
			String viewType = null;
			String drivceType = null;
			
			if (null != url && !"".equals(url))
			{
				String str = java.net.URLDecoder.decode(url);
				str = str.replace("{", "").replace("}", "");
				String [] strs = str.split(";");
				if (strs.length >= 3)
				{
					roomId = strs[0].substring(strs[0].indexOf("=") + 1).trim();
					roomId = "roomId".equals(roomId) ? "0" : roomId;
					viewType = strs[1].substring(strs[1].indexOf("=") + 1).trim();
					drivceType = strs[2].substring(strs[2].indexOf("=") + 1).trim();
				}
			}
			
			
			map.put("roomList", roomList);
			map.put("widgetTitle", widgetTitle);
			map.put("widgetName", widgetName);
			map.put("widgetId", widgetId);
			map.put("url", url);
			map.put("roomId", roomId);
			map.put("viewType", viewType);
			map.put("drivceType", drivceType);

		} catch (RestClientException e) {
			logger.error("The request failed.", e);
		} catch (Exception e) {
			logger.error("The request failed.", e);
		}

		return new ModelAndView("monitor/portaledit/room3dEdit");
	}

	/**
	 * 过滤打开机房列表数据 liujinbing 2015-02-10
	 * @param request 请求对象
	 * @param roomData 机房列表数据
	 * @return 过滤后数据
	 */
	public String openFilter(HttpServletRequest request, String roomData) {
		// 将String转化为JSONArray
		JSONArray oldJsonArr = JSONArray.fromObject(roomData);

		// 获取本地临时目录下文件
		List<File> fileList = new ArrayList<File>();
		String file_real_path = request.getRealPath("room3d/temp");
		listDirectory(new File(file_real_path), fileList);

		if (null == fileList || fileList.isEmpty()) {
			return roomData;
		}

		JSONArray newJsonArr = new JSONArray();
		Iterator<JSONObject> it = oldJsonArr.iterator();

		while (it.hasNext()) {
			JSONObject oldJsonObj = it.next();

			for (File file : fileList) {
				if (null != oldJsonObj && null != file
						&& null != file.getName()
						&& file.getName().indexOf(".") != -1) {
					String name = file.getName().split("\\.")[0];
					String ciId = String.valueOf(oldJsonObj.getInt("ciId"));

					if (null != name && null != ciId
							&& name.trim().equals(ciId.trim())) {
						JSONObject newJsonObj = new JSONObject();
						newJsonObj.put("ciId", oldJsonObj.getInt("ciId"));
						newJsonObj.put("name", oldJsonObj.getString("name"));
						newJsonArr.add(newJsonObj);
						break;
					}
				}
			}
		}

		return newJsonArr.toString();
	}

	/**
	 * 循环遍历文件 liujinbing 2015-02-10
	 * @param path  文件路径
	 * @param fileList 文件列表
	 */
	private void listDirectory(File path, List<File> fileList) {

		if (!path.exists()) {
			logger.error("Could not find the file.");
		} else {
			if (path.isFile()) {
				fileList.add(path);
			} else {
				File[] files = path.listFiles();
				for (File file : files) {
					listDirectory(file, fileList);
				}
			}
		}
	}
	@RequestMapping("/toAlarmEdit")
	public ModelAndView toAlarmEdit(HttpServletRequest request, ModelMap map) {
		String widgetId = request.getParameter("widgetId");
		String widgetTitle = request.getParameter("widgetTitle");
		String widgetName = request.getParameter("widgetName");
		String url = request.getParameter("url");
		String moClass="";
		String num="10";
		List<MObjectDefBean> mobLst = mobjectInfoMapper.getMObjectByEdit();
		try {
			widgetTitle = java.net.URLDecoder.decode(widgetTitle, "UTF-8");
			widgetName = java.net.URLDecoder.decode(widgetName, "UTF-8");
			url = java.net.URLDecoder.decode(url, "UTF-8");
			if(!"".equals(url)|| url!=null){
				num=url.split(";")[1].split("=")[1];
			}
			moClass= getStr(url,"moClass");
			map.put("mobLst", mobLst);
			map.put("widgetTitle", widgetTitle);
			map.put("widgetName", widgetName);
			map.put("widgetId", widgetId);
			map.put("url", url);
			map.put("moClass", moClass);
			map.put("num", num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("monitor/portaledit/alarmEdit");
	}

	@RequestMapping("/toTopNEdit")
	public ModelAndView toTopNEdit(HttpServletRequest request, ModelMap map) {
		// widgetId=1&widgetTitle=最近告警&url="/monitor/gaugeChartManage/findChartIfUsage?moID={moID};moClass={moClass}"
		String widgetId = request.getParameter("widgetId");
		String widgetTitle = request.getParameter("widgetTitle");
		String widgetName = request.getParameter("widgetName");
		String url = request.getParameter("url");
		String num="10";
		String orderby="desc";
		try {
			widgetTitle = java.net.URLDecoder.decode(widgetTitle, "UTF-8");
			widgetName = java.net.URLDecoder.decode(widgetName, "UTF-8");
			url = java.net.URLDecoder.decode(url, "UTF-8");
			if(!"".equals(url)|| url!=null){
				num=url.split(";")[1].split("=")[1];
			}
			if(!"".equals(url)|| url!=null){
				if(!url.contains("topOrder")){
					orderby="desc";
				}else{
					orderby=url.split(";")[2].split("=")[1];
				}
			}
			map.put("widgetTitle", widgetTitle);
			map.put("widgetName", widgetName);
			map.put("widgetId", widgetId);
			map.put("num", num);
			map.put("orderby", orderby);
			map.put("url", url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("monitor/portaledit/topNEdit");
	}
	
	@RequestMapping("/toAllSnapshotEdit")
	public ModelAndView toAllSnapshotEdit(HttpServletRequest request, ModelMap map) {
		String widgetId = request.getParameter("widgetId");
		String widgetTitle = request.getParameter("widgetTitle");
		String widgetName = request.getParameter("widgetName");
		String url = request.getParameter("url");
		String moClass="";
		List<MObjectDefBean> mobLst = mobjectInfoMapper.getMObjectByEdit();
		try {
			widgetTitle = java.net.URLDecoder.decode(widgetTitle, "UTF-8");
			widgetName = java.net.URLDecoder.decode(widgetName, "UTF-8");
			url = java.net.URLDecoder.decode(url, "UTF-8");
			moClass= getStr(url,"moClass");
			map.put("mobLst", mobLst);
			map.put("widgetTitle", widgetTitle);
			map.put("widgetName", widgetName);
			map.put("widgetId", widgetId);
			map.put("url", url);
			map.put("moClass", moClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("monitor/portaledit/allSnapshotEdit");
	}
	
	/**
	 * 截取字符串
	 * @param url
	 * @return
	 */
	public static String getStr(String url,String type) {
		String subStr = "";
		if("moClass".equals(type)){
			if (!"".equals(url) && url != null) {
				String str = url.substring(url.indexOf("?"), url.length());

				if (str.contains("?") && !str.contains(";")) {
					subStr = str.substring(str.indexOf("=") + 1, str.length());

				} else if (str.contains("?") && str.contains(";")) {
					subStr = str.substring(str.indexOf("=") + 1, str.indexOf(";"));
				}
			}
		}else{
			if (!"".equals(url) && url != null) {
				String str = url.substring(url.indexOf("?"), url.length());
	
				if (str.contains("?") && !str.contains("&")) {
					subStr = str.substring(str.indexOf("=") + 1, str.length());
	
				} else if (str.contains("?") && str.contains("&")) {
					subStr = str.substring(str.indexOf("=") + 1, str.indexOf("&"));
				}
			}
		}
		return subStr;
	}

}
