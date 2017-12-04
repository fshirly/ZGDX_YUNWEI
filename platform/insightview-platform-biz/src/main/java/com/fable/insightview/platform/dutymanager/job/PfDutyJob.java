package com.fable.insightview.platform.dutymanager.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.dutymanager.duty.service.IDutyService;
import com.fable.insightview.platform.message.service.IPfMessageService;
import com.fable.insightview.platform.notifypolicycfg.service.INotifyPolicyCfgService;
import com.fable.insightview.platform.support.QuartzManager;
import com.fable.insightview.platform.sysconf.service.SysConfigService;

public class PfDutyJob implements Job {

	private static SysConfigService configService;
	private static IPfMessageService msgService;
	private static IDutyService dutyService;
	private static INotifyPolicyCfgService notifyPolicyCfgService;
	private static String quartz = null;
	private final Logger LOG = LoggerFactory.getLogger(PfDutyJob.class);

	static {
		configService = (SysConfigService) BeanLoader.getBean("platform.sysConfigServiceImpl");
		dutyService = (IDutyService) BeanLoader.getBean("platform.dutyServiceImpl");
		msgService = (IPfMessageService) BeanLoader.getBean("platform.pfMessageServiceImpl");
		notifyPolicyCfgService = (INotifyPolicyCfgService) BeanLoader.getBean("notifyPolicyCfgService");
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		LOG.info("值班短信通知JOB执行---------------START,时间：" + new Date());
		try {
			Map<String, String> duty = configService.getConfByTypeID(205);
			String time = duty.get("DutyMsgTime");
			String enable = duty.get("DutyMsgEnable");
			if (!"true".equalsIgnoreCase(enable)) {
				return;
			}
			List<Map<String, Object>> orders = dutyService.findOrdersOfDutyByDate(getTomorrow());
			for(Map<String, Object> order : orders) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("policyId", notifyPolicyCfgService.findPolicyIdForDuty());
				param.put("smsContentName", notifyPolicyCfgService.findNameByNotifyType(1));
				param.put("emailContentName", notifyPolicyCfgService.findNameByNotifyType(2));
				param.put("phoneContentName", notifyPolicyCfgService.findNameByNotifyType(3));
				param.put("emailTitle", "值班通知");
				Integer orderId = Integer.valueOf(order.get("orderId").toString());
				String leaderName = order.get("LeaderName").toString();
				param.put("user1", leaderName);
				String orderName = order.get("Title").toString();
				param.put("duty", orderName);
				List<String> dutyNames = dutyService.findAllDutyersByOrderId(orderId);
				int index = 2;
				for(String dutyName : dutyNames) {
					param.put("user"+index, dutyName);
					index++;
				}
				param.put("date", new SimpleDateFormat("yyyy年MM月dd日").format(getTomorrow()));
				notifyPolicyCfgService.notify(param, orderId);
			}
			String[] times = time.split(":");
			if (times.length == 2 && Integer.parseInt(times[1]) >= 0 && Integer.parseInt(times[0]) >= 0) {
				if (!time.equalsIgnoreCase(quartz)) {
					QuartzManager.modifyJobTime("PfDutyJob", "0 " + times[1] + " " + times[0] + " * * ?");
				}
				quartz = time;
			}
		} catch (Exception e) {
			LOG.error("值班管理：设置定时任务的启动时间错误,解析异常{}", e);
			QuartzManager.modifyJobTime("PfDutyJob", "0 0 17 * * ?");
		}
		LOG.info("值班短信通知JOB执行---------------END,时间：" + new Date());
	}
	
	private Date getTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);
		Date tomorrow = calendar.getTime();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			tomorrow = sd.parse(sd.format(tomorrow));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return tomorrow;
	}

	/*@Override
	@Deprecated
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		LOG.info("值班短信通知JOB执行---------------START,时间：" + new Date());
		try {
			Map<String, String> duty = configService.getConfByTypeID(205);
			String time = duty.get("DutyMsgTime"); 处理时间 
			String enable = duty.get("DutyMsgEnable"); 是否处理 
			if (!"true".equalsIgnoreCase(enable)) {
				return;
			}
			LOG.info("值班短信通知JOB执行-查询短信信息,并向SmsNotificationTask表中新增短信信息-----START");
			List<PfMessage> msgs = dutyService.queryMsg(); 创建值班人员通知短信 
			LOG.info("值班短信通知JOB执行-查询短信信息,MSGS:" + new Gson().toJson(msgs));
			for (PfMessage msg : msgs) {
				msgService.insert(msg);
			}
			LOG.info("值班短信通知JOB执行-查询短信信息,并向SmsNotificationTask表中新增短信信息-----END");
			String[] times = time.split(":");
			if (times.length == 2 && Integer.parseInt(times[1]) >= 0 && Integer.parseInt(times[0]) >= 0) {
				if (!time.equalsIgnoreCase(quartz)) { 上一次生成短信时间 
					QuartzManager.modifyJobTime("PfDutyJob", "0 " + times[1] + " " + times[0] + " * * ?"); 修改定时任务时间 
				}
				quartz = time;
			}
		} catch (Exception e) {
			LOG.error("值班管理：设置定时任务的启动时间错误,解析异常{}", e);
			QuartzManager.modifyJobTime("PfDutyJob", "0 0 17 * * ?"); 修改定时任务时间 
		}
		LOG.info("值班短信通知JOB执行---------------END,时间：" + new Date());
	}*/

}
