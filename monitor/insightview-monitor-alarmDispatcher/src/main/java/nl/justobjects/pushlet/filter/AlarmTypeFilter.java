package nl.justobjects.pushlet.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.entity.AlarmNode;

public class AlarmTypeFilter extends FilterHandler {
	
	public static Logger logger = LoggerFactory.getLogger(AlarmTypeFilter.class);
	
	@Override
	public boolean handleAlarmFilter(FilterParamObject filter, AlarmNode alarm) {
		if (filter.getAlarmType().size() > 0) {
			if (filter.getAlarmType().contains(alarm.getAlarmType())) {
				if (getSuccessor() != null) {
					logger.info("告警类型满足条件!");
					return getSuccessor().handleAlarmFilter(filter, alarm);
				}
			}
			return false;
		}  
		logger.info("告警类型未设置,进行下一步匹配!");
		return getSuccessor().handleAlarmFilter(filter, alarm); 
	}
}