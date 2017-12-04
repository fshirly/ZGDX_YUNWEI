package com.fable.insightview.monitor.alarmdispatcher.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

import com.fable.insightview.monitor.alarmmgr.entity.AlarmDispatchDetail;
import com.fable.insightview.platform.common.helper.RestHepler;
import com.fable.insightview.platform.sysinit.SystemParamInit;

public class InvokeRestInterface {

	public static Logger logger = LoggerFactory
			.getLogger(AlarmObjTransfer.class);
	
	private static int Send_Failed_Flag = 2;
	
	public static AlarmDispatchDetail invokeParty(JSONObject jsondata) {
		
		AlarmDispatchDetail detail = new AlarmDispatchDetail();
		
		try {
			String path = "/rest/itsm/alarm/alarmDispatch";
			String username = SystemParamInit.getValueByKey("rest.username");
			String password = SystemParamInit.getValueByKey("rest.password");
			
			// 拼接获取CMDB接口URL
			StringBuffer basePath = new StringBuffer();
			String url = SystemParamInit.getValueByKey("rest.resSychron.url");
			basePath.append(url);
			basePath.append(path); 
			logger.debug("调用接口地址:" + basePath.toString());
			logger.debug("jsonObject:"+jsondata.toJSONString());
			
			// 设置请求头信息
			HttpHeaders requestHeaders = RestHepler.createHeaders(username,password);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(jsondata,requestHeaders);
			RestTemplate rest = new RestTemplate();
			
			// 参数1：请求地址 ，参数2：请求方式，参数3：请求头信息，参数4：相应类
			ResponseEntity<String> response = rest.exchange(basePath
					.toString(), HttpMethod.POST, requestEntity, String.class);
			if (null != response) {
				String result = response.getBody(); 
				JSONObject responseRecord = JSONObject.parseObject(result);
				detail.setWorkOrderId(strUtil(responseRecord.get("workOrderId"))); 
				detail.setDispatchStatus(strUtil(responseRecord.get("executeFlag")));
				Object descr = responseRecord.get("resultDescr");
				if (descr != null) {
					detail.setResultDescr(descr.toString());
				}
			} else {  
				detail.setWorkOrderId(null); 
				detail.setDispatchStatus(Send_Failed_Flag);
				detail.setResultDescr(""); 
			}
		} catch (Exception e) {
			detail.setWorkOrderId(null); 
			detail.setDispatchStatus(Send_Failed_Flag);
			detail.setResultDescr(""); 
			logger.error("告警派发至CMDB,调用接口同步错误!", e);
		}
		return detail;
	}
	
	/**
	 * 黄振骁新增： 修改手工派发失败重发。
	 * 
	 * @param jsondata
	 * @return
	 */
	public static AlarmDispatchDetail invokeParty2(JSONObject jsondata) {
		AlarmDispatchDetail detail = new AlarmDispatchDetail();
		try {
			// 1、修改派发路径
			String path = "/rest/itsm/alarm/alarmOrderAllot";
			String username = SystemParamInit.getValueByKey("rest.username");
			String password = SystemParamInit.getValueByKey("rest.password");
			
			// 拼接获取CMDB接口URL
			StringBuffer basePath = new StringBuffer();
			String url = SystemParamInit.getValueByKey("rest.resSychron.url");
			basePath.append(url);
			basePath.append(path); 
			logger.debug("调用接口地址:" + basePath.toString());
			logger.debug("jsonObject:"+jsondata.toJSONString());
			
			// 设置请求头信息
			HttpHeaders requestHeaders = RestHepler.createHeaders(username,password);
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(jsondata,requestHeaders);
			RestTemplate rest = new RestTemplate();
			
			// 参数1：请求地址 ，参数2：请求方式，参数3：请求头信息，参数4：相应类
			ResponseEntity<String> response = rest.exchange(basePath.toString(), HttpMethod.POST, requestEntity, String.class);
			if (null != response) {
				String result = response.getBody(); 
				JSONObject responseRecord = JSONObject.parseObject(result);
				detail.setWorkOrderId(strUtil(responseRecord.get("workOrderId"))); 
				detail.setDispatchStatus(strUtil(responseRecord.get("executeFlag")));
				Object descr = responseRecord.get("resultDescr");
				if (descr != null) {
					detail.setResultDescr(descr.toString());
				}
			} else {  
				detail.setWorkOrderId(null); 
				detail.setDispatchStatus(Send_Failed_Flag);
				detail.setResultDescr(""); 
			}
		} catch (Exception e) {
			detail.setWorkOrderId(null); 
			detail.setDispatchStatus(Send_Failed_Flag);
			detail.setResultDescr(""); 
			logger.error("告警派发至CMDB,调用接口同步错误!", e);
		}
		return detail;
	}
	
	public static int strUtil(Object obj) {
		if (obj == null || obj.equals("")) {
			return 0;
		}
		return Integer.parseInt(obj.toString().trim());
	}
}