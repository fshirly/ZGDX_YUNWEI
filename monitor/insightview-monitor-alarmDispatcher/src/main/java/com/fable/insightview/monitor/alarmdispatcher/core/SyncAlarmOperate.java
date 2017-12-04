package com.fable.insightview.monitor.alarmdispatcher.core;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.justobjects.pushlet.util.Sys;

import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.monitor.messTool.MessageResolver;
import com.fable.insightview.monitor.messTool.constants.MessageTopic;
import com.fable.insightview.platform.core.util.BeanLoader;

/**
 * 同步操作告警 
 * web界面告警操作后,将告警同步给其它客户端
 */
public class SyncAlarmOperate {

	private static MessageResolver mt = null;
	
	private static SyncAlarmOperate syncAlarm = null;

	private static final String PROPERTIES_FILE = "zookeeper.properties";

	public static Logger logger = LoggerFactory.getLogger(SyncAlarmOperate.class);

	private static AlarmActiveMapper alarmActiveMapper = (AlarmActiveMapper) BeanLoader
			.getBean("alarmActiveMapper");

	private void init() {
		mt = MessageResolver.getInstance();
		try {
			mt.setProperty(getProperties());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SyncAlarmOperate getInstance() {
		if (syncAlarm == null) {
			syncAlarm = new SyncAlarmOperate();
		}
		return syncAlarm;
	}

	public void sendAlarmMessage(String alarmIDs) {
		new Thread(new synchron(alarmIDs)).start();
	}

	class synchron implements Runnable {
		
		String alarmIDs;

		public synchron(String alarmIDs) {
			this.alarmIDs = alarmIDs;
		}

		@Override
		public void run() {
			
			logger.info("同步操作告警信息给jafka alarmID:" + alarmIDs); 
			if (mt == null) {
				init();
			}

			synchronized (this) {
				try { 
					String[] array = alarmIDs.split(",");
					for (int i = 0; i < array.length; i++) {
						AlarmNode alarmInfo = alarmActiveMapper
								.getInfoById(Integer.parseInt(array[i]));
						if (alarmInfo == null) {
							alarmInfo = alarmActiveMapper
									.getHisInfoById(Integer.parseInt(array[i]));
						}
						// 操作类型说明
						// alarmStatus 1-新告警 2-自动清除 3-自动次数重复；4-自动次数重复,且级别升级 
						// alarmOperateStatus 1-未确认 2-已确认 3-已派发 4-人工清除
						if (alarmInfo == null) {
							continue;
						}
						mt.produce(MessageTopic.alarmOperate, alarmInfo);
					}
				} catch (Exception e) {
					logger.error("同步告警操作失败:", e);
				}
			}
		}
	}

	private static Properties getProperties() throws IOException {
		Properties ZKPrpperty = new Properties();
		try {
			ZKPrpperty = Sys.loadPropertiesResource(PROPERTIES_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ZKPrpperty;
	}
}