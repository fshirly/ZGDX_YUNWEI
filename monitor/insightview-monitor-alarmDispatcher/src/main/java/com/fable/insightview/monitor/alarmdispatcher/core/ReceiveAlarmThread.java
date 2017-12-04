package com.fable.insightview.monitor.alarmdispatcher.core;

import java.io.IOException;
import java.util.Map;
import java.util.Properties; //import java.util.Queue;
import java.util.concurrent.BlockingQueue; //import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import nl.justobjects.pushlet.util.Sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.alarmdispatcher.daily.DailyJob;
import com.fable.insightview.monitor.alarmdispatcher.daily.OperateTool;
import com.fable.insightview.monitor.messTool.MessageResolver;
import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.support.QuartzManager;
import com.fable.insightview.platform.sysconf.service.SysConfigService;

/**
 * 从jafka接收告警线程
 * 
 */
public class ReceiveAlarmThread implements Runnable {

	public static Logger logger = LoggerFactory
			.getLogger(ReceiveAlarmThread.class);

	private static final String PROPERTIES_FILE = "zookeeper.properties";

	MessageResolver mt = null;

	// 告警队列
	// public static Queue<Object> queue;
	//
	// static {
	// queue = new ConcurrentLinkedQueue<Object>();
	// }
	public static BlockingQueue<Object> queue;

	static {
		queue = new LinkedBlockingQueue<Object>();
	}

	public ReceiveAlarmThread() {
		logger.info("alarm dispatcher init...");
		try {
			mt = MessageResolver.getInstance();
			mt.setProperty(getProperties());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("alarm dispatcher init", e);
		}
	}

	public static Properties getProperties() throws IOException {
		Properties ZKPrpperty = new Properties();
		try {
			ZKPrpperty = Sys.loadPropertiesResource(PROPERTIES_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ZKPrpperty;
	}

	// 常规告警topic
	// class noralAlarm implements MessageHandler, Runnable {
	// private void AlarmConsumeProcess() {
	// mt.alarmConsume(this);
	// }
	//
	// @Override
	// public void handleMessage(Object mess) {
	// System.out.println(mess);
	// if(mess instanceof AlarmNode) {
	// queue.offer(mess);
	// }
	// }
	//
	// @Override
	// public void run() {
	// //System.out.println("启动正常告警监听线程");
	// while (true) {
	// try {
	// noralAlarm ac = new noralAlarm();
	// ac.AlarmConsumeProcess();
	// Thread.sleep(100);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }

	// 操作告警topic
	// class operateAlarm implements MessageHandler, Runnable {
	// private void AlarmOperateConsumeProcess() {
	// mt.alarmOperateConsume(this);
	// }
	//
	// @Override
	// public void handleMessage(Object mess) {
	// System.out.println(mess);
	// if(mess instanceof AlarmNode) {
	// queue.offer(mess);
	// }
	// }
	//
	// @Override
	// public void run() {
	// //System.out.println("启动更新告警监听线程");
	// operateAlarm operate = new operateAlarm();
	// while (true) {
	// try {
	// operate.AlarmOperateConsumeProcess();
	// Thread.sleep(100);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }

	@Override
	public void run() {
		new Thread(new ReceiveOperateAlarm(mt)).start();
		new Thread(new ReceiveNormalAlarm(mt)).start();
	 
		// 增加日报定时任务
		logger.info("daily job init!");
		try{
			SysConfigService sysConfigService = (SysConfigService) BeanLoader
					.getBean("platform.sysConfigServiceImpl");
			// type
			Map<String, String> initParam = sysConfigService.getConfByTypeID(200);
			String dayAnnouncementsEnable = initParam.get("dayAnnouncementsEnable");
			String dayAnnouncementsTime = initParam.get("dayAnnouncementsTime");
			String phoneNumber = initParam.get("dayAnnouncementsMsgPhone"); 
			OperateTool dailyTool = new OperateTool();
			
			if (dayAnnouncementsEnable != null && dayAnnouncementsEnable.equals("true")) {
				boolean sendFlag = false;
				if (dayAnnouncementsTime != null && !dayAnnouncementsTime.equals("")) {
					if (dayAnnouncementsTime.indexOf(":") > -1) {
						String hour = dayAnnouncementsTime.substring(0, dayAnnouncementsTime.indexOf(":"));
						String min = dayAnnouncementsTime.substring(dayAnnouncementsTime.indexOf(":") + 1,dayAnnouncementsTime.length());
						if (dailyTool.isNumeric(hour)) {
							if (hour != null && min != null && dailyTool.isNumeric(min) && dailyTool.isNumeric(min)
									&& (24 > Integer.parseInt(hour) && Integer.parseInt(hour) >= 0)
									&& (59 >= Integer.parseInt(min) && Integer.parseInt(min) >= 0)) {
								if (min.equals("00")) {
									min = "0";
								}
								dayAnnouncementsTime = "0 "+ Integer.parseInt(min.trim()) + " "+ Integer.parseInt(hour.trim()) + " * * ?";
								sendFlag = true;
							}
						}
					}
				}
				
				if (sendFlag) {
					logger.info("Daily dayAnnouncementsTime="+dayAnnouncementsTime);
					QuartzManager.addJob("Daily", DailyJob.class,dayAnnouncementsTime);
				} else {
					logger.error("Daily 自动上传日报功能,由于时间配置错误,将不会进行自动上报,请联系管理员!");
					dailyTool.sendSms(200, phoneNumber,"自动上传日报功能,由于时间配置错误,将不会进行自动上报,请联系管理员!");
				} 
			}
		} catch(Exception e){
			logger.error("Daily init error!",e);
		} 
	}
}