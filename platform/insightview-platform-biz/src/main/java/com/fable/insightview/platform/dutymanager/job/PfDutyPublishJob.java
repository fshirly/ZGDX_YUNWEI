package com.fable.insightview.platform.dutymanager.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fable.insightview.platform.common.helper.RestHepler;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.dutymanager.duty.service.IDutyService;
import com.fable.insightview.platform.sysconf.service.SysConfigService;
import com.fable.insightview.platform.sysinit.SystemParamInit;

public class PfDutyPublishJob implements Job {

	private static IDutyService dutyService;
	private static SysConfigService sysConfigService;
	private final Logger LOGGER = LoggerFactory.getLogger(PfDutyPublishJob.class);
	
	static {
		dutyService = (IDutyService) BeanLoader.getBean("dutyServiceImpl");
		sysConfigService = (SysConfigService) BeanLoader.getBean("sysConfigServiceImpl");
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Date now = new Date();
		/*try {
			now = new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-22");
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DATE, -1);
		Date yesterday = calendar.getTime();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			yesterday = sd.parse(sd.format(yesterday));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		List<Map<String, Object>> dutyOrders = dutyService.findOrdersOfDutyByDate(yesterday);
		for(Map<String, Object> order : dutyOrders) {
			Map<String, Object> param = new HashMap<String, Object>();
			String leaderName = String.valueOf(order.get("LeaderName"));
			String dutyName = String.valueOf(order.get("DutyName"));
			Integer dutyId = Integer.valueOf(order.get("ID").toString());
			String reserveNames = String.valueOf(dutyService.query(dutyId).get("reserveNames"));
			if(reserveNames == null || "null".equals(reserveNames)) {
				reserveNames = "";
			}
			String title = new SimpleDateFormat("yyyy-MM-dd").format(yesterday)+" 信息中心服务台值班日志";
			if(dutyOrders.size() > 1) {
				title += "("+order.get("Title")+")";
			}
			param.put("leaderName", leaderName);
			param.put("dutyName", dutyName);
			param.put("reserveNames", reserveNames);
			param.put("title", title);
			//TODO 告警数和工单数
			String beginPoint = order.get("BeginPoint").toString();
			String endPoint = order.get("EndPoint").toString();
			String intervalDays = order.get("IntervalDays").toString();
			String dutyDate = order.get("DutyDate").toString();
			Date startTime = null;
			Date endTime = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				startTime = sdf.parse(dutyDate+" "+beginPoint+":00");
				if("0".equals(intervalDays)) {
					endTime = sdf.parse(dutyDate+" "+endPoint+":00");
				} else {
					endTime = sdf.parse(dutyDate+" 23:59:59");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Map<String, Object> dateParam = new HashMap<String, Object>();
			dateParam.put("startTime", startTime);
			dateParam.put("endTime", endTime);
			Map<String, Object> result = dutyService.findAlarmInfoByDate(dateParam);
			param.put("alarmCount", result.get("urgencyAlarmCount"));
			param.put("alarmAlreadyDoneCount", result.get("urgencyAlarmAlreadyDoneCount"));
			param.put("workOrderDoneCount", result.get("workOrderCount"));
			String text = generateDutyHtml2(param);
			this.sendResource(text, title);
			LOGGER.info("值班日志：已生成主题为"+title+"的值班日志，值班班次为"+order.get("Title"));
		}
		
	}
	
	private String sendResource(String text, String title) {
		String username = SystemParamInit.getValueByKey("rest.username");
		String password = SystemParamInit.getValueByKey("rest.password");
		try {
			HttpHeaders requestHeaders = RestHepler.createHeaders(username, password);
			requestHeaders.set("Accept-Charset", "utf-8");
			requestHeaders.set("Content-Type" ,"application/json;charset=UTF-8");
			RestTemplate rest = new RestTemplate();

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("title", title);
			param.put("launchtime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			param.put("type", 2);
			param.put("mid", sysConfigService.getConfParamValue(1004 ,"DutyLogPublishMid"));
			param.put("code" ,"0");
			param.put("text", text);
			HttpEntity<String> httpEntity = new HttpEntity<String>(JsonUtil.map2Json(param), requestHeaders);
			String sendUrl = sysConfigService.getConfParamValue(209, "PublishRestUrl");
			
			ResponseEntity<String> rssResponse = rest.exchange(sendUrl, HttpMethod.POST, httpEntity, String.class);
			if (null != rssResponse) {
				String result = rssResponse.getBody();
				if(result.indexOf("success") > -1){
				}
				return result;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Deprecated
	private String generateDutyHtml(Map<String, Object> param) {
		Date now = new Date();
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		sb.append("<p style='text-align: center;hyphenate: auto;font-size: 18pt;'>");
		sb.append("<span>"+param.get("title")+"</span></p>");
		sb.append("<p style='text-align: start;hyphenate: auto;font-size: 12pt;text-align: center;color: #333;'>");
		sb.append("<span>发布时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now)+"</span></p>");
		sb.append("<div style='position: relative;margin: 15px auto;width: 100%;background-color: #fff;border: 1px solid #ddd;border-radius: 4px'>");
		sb.append("<table style='text-align: center;margin: 0 auto;color: #333;width: 100%;'>");
		sb.append("<tr><td colspan='3'><p style='text-align: left;'><span>值班信息</span></p></td>");
		sb.append("<tr><td><p><span>值班信息</span></p></td><td><p><span>带班领导</span></p></td><td><p><span>当日值班员</span></p></td></tr>");
		sb.append("<tr><td><p><span>值班信息</span></p></td><td><p><span>"+param.get("leaderName")+"</span></p></td><td><p><span>"+param.get("dutyName")+"</span></p></td></tr>");
		sb.append("<tr><td><p><span>备勤人员</span></p></td><td colspan='2'><p><span>"+param.get("reserveNames")+"</span></p></td></tr></table>");
		sb.append("<table style='text-align: center;margin: 0 auto;color: #333;width: 100%;'>");
		sb.append("<tr><td colspan='2'><p style='text-align: left;'><span>信息查询服务</span></p></td></tr>");
		sb.append("<tr><td><p><span>项目</span></p></td><td><p><span>相关情况</span></p></td></tr>");
		sb.append("<tr><td><p><span>服务请求解决数</span></p></td><td><p><span>"+random.nextInt(16)+"</span></p></td></tr>");
		sb.append("<tr><td><p><span>接听服务请求电话数</span></p></td><td><p><span>"+random.nextInt(16)+"</span></p></td></tr>");
		sb.append("<tr><td><p><span>公安部电话查岗情况</span></p></td><td><p><span>0</span></p></td></tr></table>");
		sb.append("<table style='text-align: center;margin: 0 auto;color: #333;width: 100%;'>");
		sb.append("<tr><td colspan='2'><p style='text-align: left;'><span>事件处理和报告</span></p></td></tr>");
		sb.append("<tr><td><p><span>项目</span></p></td><td><p><span>相关情况</span></p></td></tr>");
		sb.append("<tr><td><p><span>报送《公安信息通信网运行服务管理情况表》</span></p></td><td><p><span>已报</span></p></td></tr>");
		sb.append("<tr><td><p><span>发布每日巡检通告（5个）</span></p></td><td><p><span>已发</span></p></td></tr>");
		sb.append("<tr><td><p><span>收到各地中断报告和请示数</span></p></td><td><p><span>"+random.nextInt(4)+"</span></p></td></tr>");
		sb.append("<tr><td><p><span>向公安部报送中断报告和请示数</span></p></td><td><p><span>0</span></p></td></tr></table>");
		sb.append("<table style='text-align: center;margin: 0 auto;color: #333;width: 100%;'>");
		sb.append("<tr><td colspan='4'><p style='text-align: left;'><span>资源运行状态检测</span></p></td></tr>");
		sb.append("<tr><td colspan='3'><p><span>项目</span></p></td><td><p><span>相关情况</span></p></td></tr>");
		sb.append("<tr><td rowspan='2'><p><span>运维系统告警处理情况</span></p></td><td colspan='2'><p><span>紧急告警数</span></p></td><td><p><span>"+param.get("alarmCount")+"</span></p></td></tr>");
		sb.append("<tr><td colspan='2'><p><span>紧急告警解决数</span></p></td><td><p><span>"+param.get("alarmAlreadyDoneCount")+"</span></p></td></tr>");
		sb.append("<tr><td rowspan='4'><p><span>机房物理巡检</span></p></td><td rowspan='2'><p><span>上午</span></p></td><td><p><span>时间</span></p></td><td><p><span>8:30</span></p></td></tr>");
		sb.append("<tr><td><p><span>情况</span></p></td><td><p><span>正常</span></p></td></tr>");
		sb.append("<tr><td rowspan='2'><p><span>下午</span></p></td><td><p><span>时间</span></p></td><td><p><span>20:30</span></p></td></tr>");
		sb.append("<tr><td><p><span>情况</span></p></td><td><p><span>正常</span></p></td></tr></table>");
		sb.append("<table style='text-align: center;margin: 0 auto;color: #333;width: 100%;'>");
		sb.append("<tr><td colspan='2'><p style='text-align: left;'><span>运维工单完成情况</span></p></td></tr>");
		sb.append("<tr><td><p><span>项目</span></p></td><td><p><span>完成情况</span></p></td></tr>");
		sb.append("<tr><td><p><span>运维工单完成数</span></p></td><td><p><span>"+param.get("workOrderDoneCount")+"</span></p></td></tr></table></div>");
		return sb.toString();
	}
	
	private String generateDutyHtml2(Map<String, Object> param) {
		Date now = new Date();
		Random random = new Random();
		String html = "<meta charset='utf-8'><p style='text-align: center;hyphenate: auto;font-size: 20px;'><span>"+param.get("title")+"</span></p><p style='text-align: start;hyphenate: auto;font-size: 14px;text-align: center;color: #999;'><span>发布时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now)+"</span></p><table style='width:100%; height:auto;margin: 0 auto; text-align:center'><tr><td colspan='3'  style='color:#000; font-size: 16px; font-weight: bold; border:0; padding: 15px 15px 3px 10px;'><p style='text-align: left;'>值班信息</p></td></tr><tr><td style='width:36%; background:#f4f4f4;'><span>值班信息</span></td><td style='background:#f4f4f4;'><span>带班领导</span></td><td style='background:#f4f4f4;'><span>当日值班员</span></td></tr><tr><td><span>值班信息</span></td><td><p><span>"+param.get("leaderName")+"</span></p></td><td><p><span>"+param.get("dutyName")+"</span></p></td></tr><tr><td><span>备勤人员</span></td><td colspan='2'><p><span>"+param.get("reserveNames")+"</span></p></td></tr></table><table style='width:100%; height:auto;margin: 0 auto; text-align:center'><tr><td colspan='2' style='color:#000; font-size: 16px; font-weight: bold; border:0; padding:15px 10px 3px 10px;'><p style='text-align: left;'><span>信息查询服务</span></p></td></tr><tr><td style='width:65.5%; background:#f4f4f4;'><span>项目</span></td><td style='background:#f4f4f4;'><span>相关情况</span></td></tr><tr><td><span>服务请求解决数</span></td><td><p><span>"+random.nextInt(16)+"</span></p></td></tr><tr><td><span>接听服务请求电话数</span></td><td><p><span>"+random.nextInt(16)+"</span></p></td></tr><tr><td><span>公安部电话查岗情况</span></td><td><p><span>0</span></p></td></tr></table><table style='width:100%; height:auto;margin: 0 auto; text-align:center'><tr><td colspan='2' style='color:#000; font-size: 16px; font-weight: bold; border:0; padding: 15px 15px 3px 10px;'><p style='text-align: left;'><span>事件处理和报告</span></p></td></tr><tr><td style='width:65.5%; background:#f4f4f4;'><span>项目</span></td><td style='background:#f4f4f4;'><span>相关情况</span></td></tr><tr><td><span>报送《公安信息通信网运行服务管理情况表》</span></td><td><p><span>已报</span></p></td></tr><tr><td><span>发布每日巡检通告（5个）</span></td><td><p><span>已发</span></p></td></tr><tr><td><span>收到各地中断报告和请示数</span></td><td><p><span>"+random.nextInt(4)+"</span></p></td></tr><tr><td><span>向公安部报送中断报告和请示数</span></td><td><p><span>0</span></p></td></tr></table><table style='width:100%; height:auto;margin: 0 auto; text-align:center'><tr><td colspan='4' style='color:#000; font-size: 16px; font-weight: bold; border:0; padding: 15px 15px 3px 10px;'><p style='text-align: left;'><span>资源运行状态检测</span></p></td></tr><tr><td colspan='3' style='width:65.5%; background:#f4f4f4;'><span>项目</span></td><td style='background:#f4f4f4;'><span>相关情况</span></td></tr><tr><td rowspan='2'><span>运维系统告警处理情况</span></td><td colspan='2'><p><span>紧急告警数</span></p></td><td><p><span>"+param.get("alarmCount")+"</span></p></td></tr><tr><td colspan='2'><p><span>紧急告警解决数</span></p></td><td><p><span>"+param.get("alarmAlreadyDoneCount")+"</span></p></td></tr><tr><td rowspan='4'><span>机房物理巡检</span></td><td rowspan='2'><p><span>上午</span></p></td><td><p><span>时间</span></p></td><td><p><span>8:30</span></p></td></tr><tr><td><p><span>情况</span></p></td><td><p><span>正常</span></p></td></tr><tr><td rowspan='2'><p><span>下午</span></p></td><td><p><span>时间</span></p></td><td><p><span>20:30</span></p></td></tr><tr><td><p><span>情况</span></p></td><td><p><span>正常</span></p></td></tr></table><table style='width:100%; height:auto;margin: 0 auto; text-align:center'><tr><td colspan='2' style='color:#000; font-size: 16px; font-weight: bold; border:0; padding: 15px 15px 3px 10px;'><p style='text-align: left;'><span>运维工单完成情况</span></p></td></tr><tr><td style='width:65.5%; background:#f4f4f4;'><span>项目</span></td><td style='background:#f4f4f4;'>"
					  +"<span>相关情况</span></td></tr><tr><td><span>运维工单完成数</span></td><td><p><span>"+param.get("workOrderDoneCount")+"</span></p></td></tr></table>";
		return html;
	}
	
}
