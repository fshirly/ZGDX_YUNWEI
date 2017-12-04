package com.fable.insightview.monitor.alarmdispatcher.daily;

/**
 * @Description: 
 *
 * @Title: QuartzJob.java
 * @Package com.joyce.quartz
 * @Copyright: Copyright (c) 2014
 *
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import com.fable.insightview.monitor.daily.service.TimerdayAnnounceService;
import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.support.QuartzManager;
import com.fable.insightview.platform.sysconf.service.SysConfigService;

/**
 * @Description: 任务执行类
 * 
 */
public class DailyJob implements Job {

	public static Logger logger = LoggerFactory.getLogger(DailyJob.class);

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String filepath = "";
		String dayAnnouncementsEnable = "";
		String dayAnnouncementsTime = "";
		String successMsg = "";
		String failMsg = "";
		String beforeDatePhoneNumber = "";
		String currentDatePhoneNumber = "";
		
		String loginUrl = "";
		String userId = "";
		String userPass= "";
		String actionMethod = "";
		String fileUpLoadUrl = "";
		String filePrefixName = "";  
		String fileName ="";
		// success flag
		boolean executeFlag = false;
		// date format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		OperateTool dailyTool = new OperateTool();

		try {
			// 增加随机数
			Random random = new Random();
			Thread.sleep(random.nextInt(20) * 60 *1000);
		  
			//get bean 
			TimerdayAnnounceService timerdayAnnounceService = (TimerdayAnnounceService) BeanLoader
					.getBean("timerdayAnnounceServiceImpl");
			SysConfigService sysConfigService = (SysConfigService) BeanLoader
					.getBean("platform.sysConfigServiceImpl");
			// type
			Map<String, String> initParam = sysConfigService.getConfByTypeID(200);
			dayAnnouncementsEnable = initParam.get("dayAnnouncementsEnable");
			dayAnnouncementsTime = initParam.get("dayAnnouncementsTime");
			successMsg = initParam.get("dayAnnouncementsMsgSuccess");
			failMsg = initParam.get("dayAnnouncementsMsgFail");
//			phoneNumber = initParam.get("dayAnnouncementsMsgPhone");
			 
			filePrefixName = initParam.get("dayPrefixFileName"); 
			loginUrl = initParam.get("loginUrl");
			userId = initParam.get("userId");
			userPass= initParam.get("userPass");
			actionMethod = initParam.get("actionMethod");
			fileUpLoadUrl = initParam.get("fileUpLoadUrl"); 
			filepath = initParam.get("filePath");
			
			beforeDatePhoneNumber = timerdayAnnounceService.queryDutePeoplePhoneNumber();
			currentDatePhoneNumber = timerdayAnnounceService.queryCurrentDutePeoplePhoneNumber();
			
			if (dayAnnouncementsEnable != null && dayAnnouncementsEnable.equals("true")) {
				// 生成文件是否成功 
				fileName = timerdayAnnounceService.beginToUploadDayAnnounceExcel(filepath,filePrefixName);
				if (fileName != null && !fileName.equals("")) {
					executeFlag = true;
				}
				
				if (executeFlag) {
					Uploadaily upload = new Uploadaily(loginUrl,userId,userPass,actionMethod,fileUpLoadUrl,filepath,filePrefixName);
					try {
						// 上传是否成功
						if (upload.login()) {
							// 执行结束时,发短信通知 
							if (beforeDatePhoneNumber != null && !beforeDatePhoneNumber.equals("")) {
								dailyTool.sendSms(200, beforeDatePhoneNumber,
										successMsg.replace("{FileName}", fileName));
							}

							if (currentDatePhoneNumber != null && !currentDatePhoneNumber.equals("")) {
								dailyTool.sendSms(200, currentDatePhoneNumber,
										successMsg.replace("{FileName}", fileName));
							}
							
							logger.warn("Daily:"+ dateFormat.format(new Date())
												+ " phoneNumber:" + beforeDatePhoneNumber
												+ " msg:"+successMsg.replace("{FileName}", fileName));
						} else {
							logger.error("Daily file upload failed");
							executeFlag = false;
						}
					} catch (Exception e) {
						logger.error("Daily file upload error:",e); 
						executeFlag = false;
					}
				}
				
			} else {
				executeFlag = true;
				logger.warn("Daily phoneNumber:" + beforeDatePhoneNumber
						+ " msg:自动上传日报功能,根据配置将不再进行自动上报,如有疑问请联系管理员!");
				// del
				QuartzManager.removeJob("Daily");
				// 删除任务时,发短信通知
				if (beforeDatePhoneNumber != null && !beforeDatePhoneNumber.equals("")) {
					dailyTool.sendSms(200, beforeDatePhoneNumber, "自动上传日报功能,根据配置将不再进行自动上报,如有疑问请联系管理员!");
				}
				if (currentDatePhoneNumber != null && !currentDatePhoneNumber.equals("")) {
					dailyTool.sendSms(200, currentDatePhoneNumber, "自动上传日报功能,根据配置将不再进行自动上报,如有疑问请联系管理员!"); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			executeFlag = false;
			logger.error("Daily自动上传日报失败",e);
		}

		if (!executeFlag) {
			// 任务失败时,发短信通知
			if (beforeDatePhoneNumber != null && !beforeDatePhoneNumber.equals("")) {
				dailyTool.sendSms(200, beforeDatePhoneNumber, failMsg.replace("{FileName}", fileName));
			}
			if (currentDatePhoneNumber != null && !currentDatePhoneNumber.equals("")) {
				dailyTool.sendSms(200, currentDatePhoneNumber, failMsg.replace("{FileName}", fileName)); 
			}
			logger.warn("Daily:" + dateFormat.format(new Date())
								 + " phoneNumber:" + beforeDatePhoneNumber+" msg"+failMsg.replace("{FileName}", fileName));
		}
		
		// 下一次执行时间
		if (dayAnnouncementsTime != null && !dayAnnouncementsTime.equals("")) {
			if (dayAnnouncementsTime.indexOf(":") > -1) {
				String hour = dayAnnouncementsTime.substring(0,dayAnnouncementsTime.indexOf(":"));
				String min = dayAnnouncementsTime.substring(
						dayAnnouncementsTime.indexOf(":") + 1,dayAnnouncementsTime.length());
				if (dailyTool.isNumeric(hour)) {
					if (hour != null && min != null
							&& dailyTool.isNumeric(min) && dailyTool.isNumeric(min)
							&& (24 > Integer.parseInt(hour) && Integer.parseInt(hour) >= 0)
							&& (59 >= Integer.parseInt(min) && Integer.parseInt(min) >= 0)) {
						if (min.equals("00")) {
							min = "0";
						} 
						dayAnnouncementsTime = "0 " + Integer.parseInt(min.trim()) + " " + Integer.parseInt(hour.trim()) + " * * ?";
					}
				}
			}
			// 重新放入执行时间
			QuartzManager.modifyJobTime("Daily",dayAnnouncementsTime); 
		}
	}
}