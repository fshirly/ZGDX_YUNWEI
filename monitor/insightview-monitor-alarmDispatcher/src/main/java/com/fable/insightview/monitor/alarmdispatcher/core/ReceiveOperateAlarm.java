package com.fable.insightview.monitor.alarmdispatcher.core;

import java.text.SimpleDateFormat; 
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import com.fable.insightview.monitor.alarmdispatcher.utils.AlarmObjTransfer; 
import com.fable.insightview.monitor.dispatcher.notify.DiscoveryProgressNotification;
import com.fable.insightview.monitor.entity.AlarmNode; 
import com.fable.insightview.monitor.interfaces.MessageHandler;
import com.fable.insightview.monitor.messTool.MessageResolver;
import com.fable.insightview.monitor.messTool.constants.MessageTopic;
import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.event.DefaultEventDispatcher;

public class ReceiveOperateAlarm  implements MessageHandler, Runnable {
	
	private DefaultEventDispatcher alarmEventDispatcher = (DefaultEventDispatcher) BeanLoader
			.getBean("alarmEventDispatcher");
	
	public static Logger logger = LoggerFactory.getLogger(ReceiveOperateAlarm.class);
	
	MessageResolver mt = null;

	public ReceiveOperateAlarm(MessageResolver mt) {
		this.mt = mt;
	}

	private void AlarmOperateConsumeProcess() { 
		mt.subscribe("alarmdispatcher",MessageTopic.alarmOperate,this); 
	}

	@Override
	public void handleMessage(Object mess) {
		logger.info("receive operate alarm !!!");
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("Current Time:"+time.format(new java.util.Date())); 
		if (mess instanceof AlarmNode) {
			AlarmNode node = (AlarmNode)mess;
			logger.info("Operate Alarm...");
			logger.info("current queue size="+ReceiveAlarmThread.queue.size());
			logger.info("OperateAlarmID:" + node.getAlarmID());
			logger.info("OperateSourceMOID:" + node.getSourceMOID());
			logger.info("AlarmStatus:" + node.getAlarmStatus());
			logger.info("AlarmStatus:" + node.getAlarmStatus());
			logger.info("OperateStatus:" + node.getAlarmOperateStatus());
			logger.info("OperateStatusName:"+node.getOperateStatusName());
			logger.info("OperateAlarmTypeName:"+node.getAlarmTypeName()); 
			logger.info("OperateAlarmDefineID:" + node.getAlarmDefineID());
			logger.info("OperateStart:" + node.getStartTime());
			logger.info("OperateLast:" + node.getLastTime());
			logger.info("OperateRepeatCount:" + node.getRepeatCount());
			logger.info("OperateUpgradeCount:" + node.getUpgradeCount()); 
			// 放入队列
			try {
				ReceiveAlarmThread.queue.put(mess);
			} catch (Exception e) {
				logger.error("告警对象存入队列出错,空间不足", e);
			}
		}
	}

	@Override
	public void run() {
		new Thread() {
			public void run() {
				// 发现进度监听
				mt.consumeQueue(MessageTopic.alarmOperate,new MessageHandler() {
					@Override
					public void handleMessage(Object mess) {
						if (mess instanceof AlarmNode) {							
							AlarmNode node = (AlarmNode)mess;
							logger.info("dispatchEvent Operate Alarm...");
							logger.info("dispatchEvent OperateAlarmID:" + node.getAlarmID());
							logger.info("dispatchEvent OperateSourceMOID:" + node.getSourceMOID());
							logger.info("dispatchEvent OperateAlarmStatus:" + node.getAlarmStatus());
							logger.info("dispatchEvent OperateStatus:" + node.getAlarmOperateStatus());
							logger.info("dispatchEvent OperateAlarmDefineID:" + node.getAlarmDefineID());
							logger.info("dispatchEvent OperateStart:" + node.getStartTime());
							logger.info("dispatchEvent OperateLast:" + node.getLastTime());
							logger.info("dispatchEvent OperateRepeatCount:" + node.getRepeatCount());
							logger.info("dispatchEvent OperateUpgradeCount:" + node.getUpgradeCount()); 
							alarmEventDispatcher.dispatchEvent(AlarmObjTransfer.transfer((AlarmNode)mess)); 
						}
					}
				});
			}
		}.start();
		
		logger.info("\n 开始启动操作告警监听线程 !!");
		AlarmOperateConsumeProcess();  
	}
}