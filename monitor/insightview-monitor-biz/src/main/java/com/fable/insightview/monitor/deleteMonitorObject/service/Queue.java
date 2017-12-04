package com.fable.insightview.monitor.deleteMonitorObject.service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Queue {
	public static final int QUEUE_SIZE =100000;
	public static BlockingQueue<Map<DeleteMonitorObject,Map<String, Object>>> resultQueue = new ArrayBlockingQueue<Map<DeleteMonitorObject,Map<String, Object>>>(QUEUE_SIZE);
	
	public static void writeQueue(Map<DeleteMonitorObject, Map<String, Object>> result) throws InterruptedException {
		while (resultQueue.size() >= QUEUE_SIZE) {
			Thread.sleep(100);
		}
		resultQueue.put(result);
	}
}
