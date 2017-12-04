package nl.justobjects.pushlet.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.entity.AlarmNode;

public class AlarmEventFilter extends FilterHandler {

	public static Logger logger = LoggerFactory
			.getLogger(AlarmEventFilter.class);

	@Override
	public boolean handleAlarmFilter(FilterParamObject filter, AlarmNode alarm) {
		if (filter.getAlarmEvent().size() > 0) {
			if (filter.getAlarmEvent().contains(alarm.getAlarmDefineID())) {
				//if (getSuccessor() != null) {
					logger.info("AlarmEvent满足条件!");
					return true;
				//}
			} 
			return false;
		}

		if (filter.getAlarmLevel().size() > 0
				|| filter.getAlarmType().size() > 0
				|| filter.getAlarmSourceObj().size() > 0) {
			return true;
		} else {
			logger.info("所有条件不满足!");
			return false;
		} 
	}
}