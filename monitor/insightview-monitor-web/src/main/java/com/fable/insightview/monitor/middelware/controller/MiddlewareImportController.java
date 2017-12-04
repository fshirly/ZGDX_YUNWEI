package com.fable.insightview.monitor.middelware.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fable.insightview.monitor.middleware.tomcat.service.IMiddlewareSyncService;
import com.fable.insightview.platform.common.helper.RestHepler;
import com.fable.insightview.platform.sysinit.SystemParamInit;

@Controller
@RequestMapping("/middleware/middlewareImport")
@SuppressWarnings("all")
public class MiddlewareImportController {
	private static final Logger logger = LoggerFactory.getLogger(MiddlewareImportController.class);
	final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private IMiddlewareSyncService iMiddlewareSyncService;

	// MOMiddleWareJVMBean

	@ResponseBody
	@RequestMapping(value = "/execute", produces = "application/json; charset=utf-8")
	public String execute() {
		String[] models = { "MOMiddleWareJMX", "MOMiddleWareJVM", "MOMiddleWareMemory", "MOMiddleWareJTA", "MOMiddleWareJMS", "MOMiddleWareJdbcDS", "MOMiddleWareJdbcPool", "MOMiddleClassLoad", "MOMiddleWareThreadPool", "MOMiddleEJBModule", "MOMiddleWareWebModule", "MOMiddleWareJ2eeApp" };
		logger.info("....start execute synchron mobject to CMDB....");
		try {
			logger.info("....begin synchronized middleware.....");
			logger.info("....end synchronized middleware.....");
			return sendMOMiddleWareJMX();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "ERROR";
		}
		// return "OK";
	}

	public String sendHeader() {
		List<Map> temp = iMiddlewareSyncService.queryByTableName("MOMiddleWareJTA");
		return JSONArray.fromObject(temp).toString();
	}

	public String sendMOMiddleWareJMX() {
		List<Map> temp = iMiddlewareSyncService.queryByTableName("MOMiddleWareJTA");
		for (Map map : temp) {
			// 资源类型
			map.put("resTypeId", "");
			// 资源ID
			map.put("resId", "");
		}
		JSONObject json = new JSONObject();
		json.put("transferor", "monitor");
		json.put("transfertime", df.format(new Date()));
		json.put("process", 1);
		json.put("batchid", 1);
		json.put("size", temp.size());
		JsonConfig conf = new JsonConfig();
		conf.registerDefaultValueProcessor(Date.class, new DefaultValueProcessor() {
			@Override
			public Object getDefaultValue(Class clazz) {
				System.out.println(clazz);
				return null;
			}
		});
		json.put("data", JSONArray.fromObject(temp, conf));
		return json.toString();
	}

	/**
	 * 同步设备
	 * 
	 */
	private void executeDevice() {
		logger.info("...... start sync device ......");
	}

	private void invokeParty(JSONObject jsondata) {
		try {
			String url = SystemParamInit.getValueByKey("rest.resSychron.url");
			String path = "/rest/cmdb/monitor/sync/bulk";
			String username = SystemParamInit.getValueByKey("rest.username");
			String password = SystemParamInit.getValueByKey("rest.password");
			// 拼接获取单板接口URL
			StringBuffer basePath = new StringBuffer();
			basePath.append(url);
			basePath.append(path);
			HttpHeaders requestHeaders = RestHepler.createHeaders(username, password);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(jsondata, requestHeaders);
			RestTemplate rest = new RestTemplate();
			// 参数1：请求地址 ，参数2：请求方式，参数3：请求头信息，参数4：相应类
			ResponseEntity<String> rssResponse = rest.exchange(basePath.toString(), HttpMethod.POST, requestEntity, String.class);
			if (null != rssResponse) {
				logger.info(rssResponse.getBody());
			}
		} catch (Exception e) {
			logger.error("invoke rest interface, error!");
		}
	}

	public static void main(String[] args) {

	}
}