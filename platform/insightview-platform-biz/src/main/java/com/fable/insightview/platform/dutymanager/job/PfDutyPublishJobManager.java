package com.fable.insightview.platform.dutymanager.job;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.support.QuartzManager;
import com.fable.insightview.platform.sysconf.service.SysConfigService;

@Service
public class PfDutyPublishJobManager {

	@Autowired
	private SysConfigService configService;
	
	@PostConstruct
	public void addJob() {
		String time = configService.getConfParamValue(1004, "DutyLogPublishTime");
		if (StringUtils.isEmpty(time)) {
			time = "7:30";
		}
		String[] times = time.split(":");
		try {
			if (times.length == 2 && Integer.parseInt(times[1]) >= 0 && Integer.parseInt(times[0]) >= 0) {
				QuartzManager.addJob("PfDutyPublishJob", PfDutyPublishJob.class, "0 " + times[1] + " " + times[0] + " * * ?");
			}
		} catch (Exception e) {
			QuartzManager.addJob("PfDutyPublishJob", PfDutyPublishJob.class, "0 30 7 * * * ?");
		}
		//QuartzManager.addJob("PfDutyPublishJob", PfDutyPublishJob.class, "0 */3 * * * ?");
	}
}
