package com.fable.insightview.monitor.deleteMonitorObject.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.deleteMonitorObject.service.DeleteMonitorObject;
import com.fable.insightview.monitor.deleteMonitorObject.service.Queue;

public class DeletePerfAlarm {
	private final Logger logger = LoggerFactory.getLogger(DeletePerfAlarm.class);
	Map<DeleteMonitorObject, Map<String, Object>> result = null;
	public void deletePerfAndAlarm(){
		new Thread(){
			public void run(){
				while(true){
					try {
						result = Queue.resultQueue.take();
						/***
						 * 循环遍历删除对应的性能表数据、告警数据
						 */
						for (DeleteMonitorObject monitor : result.keySet()) {
							monitor.deletePerfAndAlarmData(result.get(monitor));
						}
					} catch (InterruptedException e) {
						logger.error("删除设备对象性能数据及告警数据线程消费失败,{}",e.getMessage());
					}
				}
			}
		}.start();
	}
}
