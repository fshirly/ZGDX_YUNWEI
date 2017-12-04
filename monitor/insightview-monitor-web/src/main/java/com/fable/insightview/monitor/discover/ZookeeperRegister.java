package com.fable.insightview.monitor.discover;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.alarmdispatcher.core.ReceiveAlarmThread;
import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.DispatcherConstants;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.CommonFacade;
import com.fable.insightview.monitor.dispatcher.facade.CommonFacadeImpl;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.manager.impl.ScriptManagerImpl;
import com.fable.insightview.monitor.dispatcher.manager.impl.ServerManagerImpl;
import com.fable.insightview.monitor.dispatcher.manager.impl.TaskManagerImpl;
import com.fable.insightview.monitor.dispatcher.server.Server;
import com.fable.insightview.monitor.dispatcher.server.WebAppServer;
import com.fable.insightview.monitor.interfaces.MessageHandler;
import com.fable.insightview.monitor.messTool.MessageResolver;
import com.fable.insightview.monitor.messTool.constants.MessageTopic;
import com.fable.insightview.monitor.messTool.entity.notify.DiscoveryProgressNotification;

/**
 * zookeeper发现进度及实时告警加载
 * 
 */
public class ZookeeperRegister {

	private static final Logger log = LoggerFactory.getLogger(ZookeeperRegister.class);

	public static ManageFacade manageFacade = null;
	MessageResolver mt = null;
	
	public static ConcurrentHashMap<Integer, LinkedList<DiscoveryProgressNotification>> concurrentMap = null; 
	
	public void init() {
		
		if (manageFacade == null) {			
			Properties proterties = new Properties(); 
	        Properties prop = new Properties();
	        try {    
	            prop.load(Resources.getResourceAsStream("zookeeper.properties")); 
	            proterties.setProperty(DispatcherConstants.ZOOKEEPER_CONNECT_KEY,prop.getProperty("zk.connect"));
	        	mt = MessageResolver.getInstance();
//	        	log.info("print pro info"+prop.getProperty("alarm.listener")+"  "+prop.getProperty("zk.connect"));
				mt.setProperty(prop);
	        } catch (IOException e) {   
	            e.printStackTrace();  
	            log.error("ZookeeperRegister init error",e);
	        }
	        
	        if(prop.getProperty("alarm.listener")!=null && prop.getProperty("alarm.listener").equals("true")){
				log.info("启动告警监听");
				new Thread(new ReceiveAlarmThread()).start();
	        }
			
	        log.info("启动设备发现进度监听");
			// 连接到zookeeper参数设置
	        concurrentMap = new ConcurrentHashMap<Integer, LinkedList<DiscoveryProgressNotification>>(); 
			Dispatcher dispatcher = Dispatcher.getInstance();
			dispatcher.setProterties(proterties);
			
			// 注册管理端
			manageFacade = dispatcher.getManageFacade();
			Server server = new WebAppServer();
			server.setProcessName("Tomcat");
			log.info("注册WebServer!");
			 /** 在同一个zookeeper上只能注册一个该类型的服务(非线程安全).
			 * 该方法会影响server的注册功能,当注册的server不符合条件时,该方法会尝试终止该java进程,
			 * 如果终止java进程的努力失败，那么会抛出一个RuntimeException
			 */
			manageFacade.regUniqueServer("WEB_APP_SERVER", TaskType.TASK_WEBAPP);
			manageFacade.register(server);
			
			
			new Thread() {
				public void run() {
					// 发现进度监听
					mt.consumeQueue(MessageTopic.discovery_progress,
						new MessageHandler() {
							LinkedList<DiscoveryProgressNotification> list = null;

							@Override
							public void handleMessage(Object mess) {
								log.info("mess class="+mess.getClass());
								if (mess instanceof DiscoveryProgressNotification) { 
									synchronized (concurrentMap) {
										try {
											list = new LinkedList<DiscoveryProgressNotification>();
											DiscoveryProgressNotification process = (DiscoveryProgressNotification) mess;
											int taskID = Integer.parseInt(process.getTaskId());
											if (concurrentMap.containsKey(taskID)) {
												list = concurrentMap.get(taskID);
											}
											list.add(process);
											concurrentMap.put(taskID, list);
										} catch (Exception e) {
											log.error("error", e);
										}
									}
								}	
							} 
						});
				}
			}.start();
			 
			// 进度通知
//			String topic = "TASK_DISCOVERY_PROGRESS";
			 
//			// 发现进度监听
//			manageFacade.addNotificationListener(topic, new Listener() {
//				LinkedList<DiscoveryProgressNotification> list = null;
//				@Override
//				public boolean onEvent(BaseEvent event) {
//					synchronized (concurrentMap) { 
//						try{
//							list = new LinkedList<DiscoveryProgressNotification>(); 
//							DiscoveryProgressNotification process = (DiscoveryProgressNotification) event
//									.getTarget();
//							int taskID = Integer.parseInt(process.getTaskId());
//							if (concurrentMap.containsKey(taskID)) {
//								list = concurrentMap.get(taskID);
//							} 
//							list.add(process);
//							concurrentMap.put(taskID, list);
//						}catch(Exception e){
//							log.error("error error");
//						}
//					}
//					return false;
//				}
//			}, 1);
		}
	} 

	public void cleanUp() {}

	public ManageFacade getInstance() {
		if (manageFacade == null) {
			log.error("注册WebServer服务失败");
		}
		return manageFacade;
	}

	public LinkedList<DiscoveryProgressNotification> getProcess(int taskID) {
		synchronized (concurrentMap) {
			LinkedList<DiscoveryProgressNotification> list = concurrentMap.get(taskID);
			concurrentMap.put(taskID,  new LinkedList<DiscoveryProgressNotification>());
			return list;
		}
	} 
}