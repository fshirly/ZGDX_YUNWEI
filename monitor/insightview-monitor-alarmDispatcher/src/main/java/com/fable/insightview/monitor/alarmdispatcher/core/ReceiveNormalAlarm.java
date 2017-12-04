package com.fable.insightview.monitor.alarmdispatcher.core;
 
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.alarmdispatcher.utils.AlarmObjTransfer;
import com.fable.insightview.monitor.entity.AlarmNode; 
import com.fable.insightview.monitor.interfaces.MessageHandler;
import com.fable.insightview.monitor.messTool.MessageResolver;
import com.fable.insightview.monitor.messTool.constants.MessageTopic;
import com.fable.insightview.platform.core.util.BeanLoader; 
import com.fable.insightview.platform.event.DefaultEventDispatcher;

/**
 * 接收正常告警线程
 */
public class ReceiveNormalAlarm  implements MessageHandler, Runnable {

	public static Logger logger = LoggerFactory.getLogger(ReceiveNormalAlarm.class);
	
	private DefaultEventDispatcher alarmEventDispatcher = (DefaultEventDispatcher) BeanLoader
			.getBean("alarmEventDispatcher");
	
	MessageResolver mt = null;

	public ReceiveNormalAlarm(MessageResolver mt) {
		this.mt = mt;
	}

	private void AlarmConsumeProcess() { 
		mt.consumeQueue(MessageTopic.alarm,this);
	}

	@Override
	public void handleMessage(Object mess) {
		
		logger.info("get normal alarm !!");
		
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("Current Time:"+time.format(new java.util.Date()));
		
		if (mess instanceof AlarmNode) {
			AlarmNode node = (AlarmNode)mess;
			logger.info("current queue size="+ReceiveAlarmThread.queue.size());
			logger.info("New Alarm");
			logger.info("NormalAlarmID:" + node.getAlarmID());
			logger.info("NormalSourceMOID:" + node.getSourceMOID()); 
			logger.info("NormalStatusName:"+node.getStatusName());
			logger.info("NormalAlarmTypeName:"+node.getAlarmTypeName());
			logger.info("NormalStatus:" + node.getAlarmStatus());
			logger.info("NormalOperateStatus:" + node.getAlarmOperateStatus());
			logger.info("NormalAlarmDefineID:" + node.getAlarmDefineID());
			logger.info("NormalStart:" + node.getStartTime());
			logger.info("NormalLast:" + node.getLastTime());
			logger.info("NormalRepeatCount:" + node.getRepeatCount());
			logger.info("NormalUpgradeCount:" + node.getUpgradeCount());
			logger.info("NormalAlarmTitle:" + node.getAlarmTitle());
			logger.info("NormalAlarmContent:" + node.getAlarmContent());
			
			// 放入队列
			try {
				ReceiveAlarmThread.queue.put(mess);
			} catch (Exception e) {
				logger.error("告警对象存入队列出错,空间不足", e);
			}
			
			// 将告警对象派发给告警统计和告警通知
			logger.info("\n 开始分发正常告警....");
			alarmEventDispatcher.dispatchEvent(AlarmObjTransfer.transfer(node));
		}
	}

	@Override
	public void run() {
		logger.info("\n 开始启动正常告警监听线程 !!"); 
		AlarmConsumeProcess();
	}
}