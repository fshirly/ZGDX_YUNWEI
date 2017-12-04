package com.fable.insightview.platform.tasklog.job;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.platform.core.util.BeanLoader;
import com.fable.insightview.platform.support.QuartzManager;
import com.fable.insightview.platform.sysconf.service.SysConfigService;
import com.fable.insightview.platform.tasklog.service.TaskLogService;

/**
 * 生成项目任务告知短信及修改定时任务时间
 */
public class PfTaskLogJob implements Job {

	private static SysConfigService configService;
	private static TaskLogService taskService;
	private static String quartz = null;
	private final Logger LOG = LoggerFactory.getLogger(PfTaskLogJob.class);

	static {
		configService = (SysConfigService) BeanLoader.getBean("platform.sysConfigServiceImpl");
		taskService = (TaskLogService) BeanLoader.getBean("platform.taskLogServiceImpl");
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Map<String, String> project = configService.getConfByTypeID(204);
		String time = project.get("TaskLogTime");/* 处理时间 */
		String enable = project.get("TaskLogEnable");/* 是否处理 */
		if (!"true".equalsIgnoreCase(enable) || StringUtils.isEmpty(time)) {
			return;
		}
		taskService.generaterTaskLogs(new Date());/* 创建任务日志信息 */
		String[] times = time.split(":");
		try {
			if (times.length == 2 && Integer.parseInt(times[1]) >= 0 && Integer.parseInt(times[0]) >= 0) {
				if (!time.equalsIgnoreCase(quartz) && null != quartz) {/* 上一次生成项目管理短信时间 */
					QuartzManager.modifyJobTime("PfTaskLogJob", "0 " + times[1] + " " + times[0] + " * * ?");/* 修改定时任务时间 */
				}
				quartz = time;
			}
		} catch (Exception e) {
			LOG.error("任务日志：设置定时任务的启动时间错误,解析异常{}", e);
			QuartzManager.modifyJobTime("PfTaskLogJob", "0 0 8 * * ?");/* 修改定时任务时间 */
		}
	}

}
