package com.fable.insightview.monitor.alarmdispatcher.distribute;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import com.fable.insightview.monitor.alarmdispatcher.core.ReceiveAlarmThread; 
import com.fable.insightview.monitor.alarmdispatcher.sendsingle.AlarmDispatchFilterUtils;
import com.fable.insightview.monitor.alarmdispatcher.utils.AlarmSplit;
import com.fable.insightview.monitor.entity.AlarmNode;   

import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;
import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.core.SessionManager;
import nl.justobjects.pushlet.filter.AlarmEventFilter;
import nl.justobjects.pushlet.filter.AlarmLevelFilter;
import nl.justobjects.pushlet.filter.AlarmSourceObjFilter;
import nl.justobjects.pushlet.filter.AlarmTypeFilter;
import nl.justobjects.pushlet.filter.FilterHandler;

/**
 * 实时告警推送 
 * @author Administrator
 */
public class NotifyPushletServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static Logger logger = LoggerFactory.getLogger(NotifyPushletServlet.class);
		
	static public class NotifyEventPullSource extends EventPullSource {
		// 初始化过滤条件
		static FilterHandler alarmLevel = new AlarmLevelFilter();
		static FilterHandler alarmType = new AlarmTypeFilter();
		static FilterHandler alarmSourceObj = new AlarmSourceObjFilter();
		static FilterHandler alarmEvent = new AlarmEventFilter(); 
		static Properties prop = new Properties();
		static {
			alarmLevel.setSuccessor(alarmType);
			alarmType.setSuccessor(alarmSourceObj);
			alarmSourceObj.setSuccessor(alarmEvent); 
			try {
				prop.load(Resources.getResourceAsStream("zookeeper.properties"));
			} catch (IOException e) {
				System.out.println(e);
				logger.error("get properties error:", e);
			}
		}
		
		private boolean isRun = true;
		
		private boolean isSuccessed = true;
		
		@Override
		protected long getSleepTime() { 
			//默认为10,加长刷新时间至1000
			return 500;
		}

		@Override
		protected Event pullEvent() {
			
			// topic
			Event event = Event.createDataEvent("/alarmNotify");
				
			while (isRun) {
				try {
					//取出告警
					AlarmNode alarm = (AlarmNode) ReceiveAlarmThread.queue.take(); 
					logger.info("take current queue size="+ReceiveAlarmThread.queue.size());
					// dispatch thread 
					if (alarm.getAlarmOperateStatus() == 21
							&& prop.getProperty("alarm.dispatch") != null
							&& prop.getProperty("alarm.dispatch") .equals("true")) {
						logger.info("告警派发至CMDB alarmID=" + alarm.getAlarmID()
								+ " alarmStatus=" + alarm.getAlarmStatus());
						// 原先的方式
						// if
						// ("1,4,5,7,8".indexOf(String.valueOf(alarm.getAlarmStatus()))
						// > -1) {
						// new Thread(new AlarmDispatchThread(alarm)).start();
						// }
						// 根据库里的告警派单规则判断是否派单
						logger.info("[INFO]: 告警id： " + alarm.getAlarmID());
						logger.info("[INFO]: 告警level id： " + alarm.getAlarmLevel());
						logger.info("[INFO]: 告警level name： " + alarm.getAlarmLevelName());
						logger.info("[INFO]: 告警type： " + alarm.getAlarmType());
						logger.info("[INFO]: 告警type name： " + alarm.getAlarmTypeName());
						logger.info("[INFO]: 告警define id： " + alarm.getAlarmDefineID());
						logger.info("[INFO]: 告警source id： " + alarm.getSourceMOID());
						logger.info("[INFO]: 告警source name： " + alarm.getSourceMOName());
						logger.info("[INFO]: 告警source ip： " + alarm.getSourceMOIPAddress());
						
						boolean isAlarm = AlarmDispatchFilterUtils.getInstance().isDispatch(alarm);
						logger.info("[INFO]: 判断是否自动派单: " + isAlarm);
						if (isAlarm) {
							Date date = new Date();
							Timestamp dispatchTime = new Timestamp(date.getTime());
							alarm.setDispatchUser("inner"); 
							alarm.setDispatchTime(dispatchTime);
							alarm.setAlarmOperateStatus(23);
							alarm.setOperateStatusName("已派发");
							new Thread(new AlarmDispatchThread(alarm)).start();
						}
					}
					
					// 获取在线用户
					Session[] sessions = SessionManager.getInstance().getSessions();
					logger.info("当前在线用户数:" + sessions.length);
					if (sessions.length > 0) {
						//告警JSON转换
						AlarmSplit.splitStr(event, alarm);
						
						// 在线用户遍历
						for (int i = 0; i < sessions.length; i++) {
							isSuccessed = false;  
							// 判断是否满足TOPO
							if (sessions[i].getFilter().getTopoFilter() != null
									&& sessions[i].getFilter().getTopoFilter()
									.indexOf(String.valueOf(alarm.getSourceMOID())) > -1) { 
									// 设备告警
								AlarmSplit.spitTopoJson(event, alarm); 
								isSuccessed = true;
							}
							// 判断是否满足3D机房
							else if (sessions[i].getFilter().getRoomFilter() != null
									&& (sessions[i].getFilter().getRoomFilter()
											.containsKey(alarm.getSourceMOID()) || sessions[i]
											.getFilter().getRoomFilter()
											.containsKey(alarm.getMoid()))) {
									if (alarm.getSourceMOClassID() == 45) {
										// 电子标签告警
										AlarmSplit.spit3dRoomJson(event, alarm,
											Integer.parseInt(sessions[i].getFilter()
													.getRoomFilter().get(alarm.getMoid()).toString()));
									} else {
										// 设备告警
										AlarmSplit.spit3dRoomJson(event,alarm,
											Integer.parseInt(sessions[i]
													.getFilter()
													.getRoomFilter()
													.get(alarm.getSourceMOID()).toString()));
									}
								isSuccessed = true;							
							}
							// 判断是否满足实时告警列表
							else if (alarmLevel.handleAlarmFilter(sessions[i].getFilter(), alarm)) {
								isSuccessed = true; 
							}
							
							if (isSuccessed) {
								// logger.error("满足推送条件!!!!");
								// 推送消息 
								// logger.info("实时告警列表条件满足,开始推送");
								logger.info("event.get(alarmdetail1):"+event.getField("alarmdetail1"));
								logger.info("event.get(alarmdetail2):"+event.getField("alarmdetail2"));
								logger.info("event.get(alarmdetail3):"+event.getField("alarmdetail3"));
								Dispatcher.getInstance().unicast(event,sessions[i].getId()); 
							}
						}
					}
				}
				catch (Exception e) {
					logger.error("从队列中取告警对象处理出错", e);
				}
				return event;
			}
			return event;
		}
	}
}