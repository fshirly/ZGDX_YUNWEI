package com.fable.insightview.monitor.perfTask;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.fable.insightview.monitor.dispatcher.Dispatcher;
import com.fable.insightview.monitor.dispatcher.constants.TaskType;
import com.fable.insightview.monitor.dispatcher.facade.ManageFacade;
import com.fable.insightview.monitor.dispatcher.notify.TaskDispatchNotification;
import com.fable.insightview.monitor.dispatcher.server.TaskDispatcherServer;

public class PerfTaskNotify {
	public void notifyDisPatch() {
		Dispatcher dispatcher = Dispatcher.getInstance();
		ManageFacade facade = dispatcher.getManageFacade();

		List<TaskDispatcherServer> servers = facade
				.listActiveServersOf(TaskDispatcherServer.class);
		if (servers.size() > 0) {
			String topic = "TaskDispatch";
			TaskDispatchNotification message = new TaskDispatchNotification();
			message.setDispatchTaskType(TaskType.TASK_COLLECT);
			facade.sendNotification(servers.get(0).getIp(), topic, message);
		}
	}

	public void portAlarmBlackListDisPatch() {
		Dispatcher dispatcher = Dispatcher.getInstance();
		ManageFacade facade = dispatcher.getManageFacade();

		List<TaskDispatcherServer> servers = facade
				.listActiveServersOf(TaskDispatcherServer.class);
		if (servers.size() > 0) {
			String topic = "TaskDispatch";
			TaskDispatchNotification message = new TaskDispatchNotification();
//			message.setDispatchTaskType(TaskType.TASK_BLACK_LIST);
			facade.sendNotification(servers.get(0).getIp(), topic, message);
		}
	}
	
	public static Timestamp getCurrDate() {
		Date d = new Date();
		Timestamp ts = new Timestamp(d.getTime());
		return ts;
	}

	public static Timestamp getDate(Object oTime) {
		long ll = Long.parseLong(oTime.toString());
		Timestamp ts = new Timestamp(ll);
		return ts;
	}

}
