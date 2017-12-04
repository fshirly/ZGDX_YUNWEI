//package com.fable.insightview.monitor.daily.service;
//
//import java.util.Calendar;
//import java.util.TimerTask;
//
//import javax.servlet.ServletContext;
//
//import com.fable.insightview.platform.core.util.BeanLoader;
//
///**
// * @author liy 定时器执行
// */
//public class TimerdayAnnounceTask extends TimerTask {
//	/**
//	 * 指定执行时间区间,准确时间已系统启动时间为准
//	 */
//	private static final int TASK_TIMER = 15;
//	
//	private ServletContext context = null;
//	
//	private boolean isRunning = false;
//	
//	private TimerdayAnnounceService timerdayAnnounceService;
//
//	public TimerdayAnnounceTask(ServletContext servletContext) {
//		this.context = servletContext;
//	}
//
//	@Override
//	public void run() {
//		Calendar calendar = Calendar.getInstance();
//		if (!isRunning) {
//			if (TASK_TIMER == calendar.get(Calendar.HOUR_OF_DAY)) {
//				context.log("开始执行上传任务");
//				isRunning = true;
//				String filepath = context.getRealPath("/");
//				timerdayAnnounceService = (TimerdayAnnounceService) BeanLoader.getBean("timerdayAnnounceServiceImpl");
//				timerdayAnnounceService.beginToUploadDayAnnounceExcel(filepath);
//				isRunning = true;
//				context.log("上传任务执行结束");
//
//			} else {
//				context.log("开始执行上传任务");
//			}
//		} else {
//			context.log("上一次任务还未结束");
//		} 
//	} 
//}