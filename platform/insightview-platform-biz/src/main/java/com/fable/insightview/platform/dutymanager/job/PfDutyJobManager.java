package com.fable.insightview.platform.dutymanager.job;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.support.QuartzManager;
import com.fable.insightview.platform.sysconf.service.SysConfigService;

@Service
public class PfDutyJobManager {

	@Autowired
	private SysConfigService configService;

	/**
	 * 增加定时任务
	 */
	@PostConstruct
	public void addJob() {
		String time = configService.getConfParamValue(205, "DutyMsgTime"); //处理时间 
		if (StringUtils.isEmpty(time)) {
			time = "17:00"; //默认启动时间 
		}
		String[] times = time.split(":");
		try {
			if (times.length == 2 && Integer.parseInt(times[1]) >= 0 && Integer.parseInt(times[0]) >= 0) {
				QuartzManager.addJob("PfDutyJob", PfDutyJob.class, "0 " + times[1] + " " + times[0] + " * * ?");
			}
		} catch (Exception e) {
			QuartzManager.addJob("PfDutyJob", PfDutyJob.class, "0 0 17 * * ?");
		}
		//QuartzManager.addJob("PfDutyJob", PfDutyJob.class, "0 */3 * * * ?");
	}
}
