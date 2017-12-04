package nl.justobjects.pushlet.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.entity.AlarmNode;

public class AlarmLevelFilter extends FilterHandler {

	public static Logger logger = LoggerFactory.getLogger(AlarmLevelFilter.class);

	@Override
	public boolean handleAlarmFilter(FilterParamObject filter, AlarmNode alarm) {
		if (filter.getAlarmLevel().size() > 0) {
			if (filter.getAlarmLevel().contains(alarm.getAlarmLevel())) {
				if (getSuccessor() != null) {
					logger.info("告警级别满足条件!");
					return getSuccessor().handleAlarmFilter(filter, alarm);
				}
			}
			return false;
		}
		logger.info("告警级别未设置,进行下一步匹配!");
		return getSuccessor().handleAlarmFilter(filter, alarm);
	}
}