package nl.justobjects.pushlet.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.alarmmgr.alarmview.mapper.AlarmViewMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmViewFilterInfo;
import com.fable.insightview.monitor.discover.entity.MODeviceObj;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.environmentmonitor.entity.MOTag;
import com.fable.insightview.monitor.environmentmonitor.mapper.EnvMonitorMapper;
import com.fable.insightview.platform.core.util.BeanLoader;

/**
 * 加载告警过滤条件
 */
public class LoadAlarmFilter {
	
	private static final Logger log = LoggerFactory.getLogger(LoadAlarmFilter.class);

	private AlarmViewMapper alarmViewMapper = (AlarmViewMapper) BeanLoader
			.getBean("alarmViewMapper");

	private MODeviceMapper moDeviceMapper = (MODeviceMapper) BeanLoader
			.getBean("MODeviceMapper");

	private EnvMonitorMapper envMonitorService = (EnvMonitorMapper) BeanLoader
			.getBean("envMonitorMapper");

	public FilterParamObject loadFilter(String userID,String viewCfgID) { 
		try{
			FilterParamObject filter = new FilterParamObject();
			Map<String,Object> params = new HashMap<String,Object>();
			
			params.put("userID", userID);
			if (viewCfgID != null && !viewCfgID.equals("0")) {
				params.put("viewFilter", " and tb.ViewCfgID=" + viewCfgID);
			} else {
				params.put("viewFilter", " and tb.UserDefault=1");
			}
			  
			List<AlarmViewFilterInfo> filterInfo = alarmViewMapper
					.queryDefaultFilterByUser(params);
			if (filterInfo.size() == 0) {
				params.put("userID", 0); 
				params.put("viewFilter", " and tb.UserDefault=1");
				filterInfo = alarmViewMapper.queryDefaultFilterByUser(params);
			}
			
			for (AlarmViewFilterInfo viewFilter : filterInfo) {
				if (viewFilter.getFilterKey().equals("AlarmLevel")) {
					filter.getAlarmLevel().add(viewFilter.getFilterKeyValue());
				} else if (viewFilter.getFilterKey().equals("AlarmType")) {
					filter.getAlarmType().add(viewFilter.getFilterKeyValue());
				} else if (viewFilter.getFilterKey().equals("AlarmSourceMOID")) {
					filter.getAlarmSourceObj().add(viewFilter.getFilterKeyValue());
				} else if (viewFilter.getFilterKey().equals("AlarmDefineID")) {
					filter.getAlarmEvent().add(viewFilter.getFilterKeyValue());
				}
			}
			return filter;
		}catch(Exception e){
			log.info("query filter error:",e);
		}
		return null;
	}
	
	/**
	 * 根据resID找对应的MOID过滤符合条件的告警
	 * @param resIds
	 * @return
	 */
	public FilterParamObject load3DRoomFilter(String resIds) {
		try {
			FilterParamObject filter = new FilterParamObject();
			Map<String, Object> params = new HashMap<String, Object>();
			if (resIds != null && resIds.compareTo("") > 0) {
				params.put("resId", resIds);
				List<MODeviceObj> molist = moDeviceMapper.getMODeviceByResId(params);
				for (MODeviceObj mo : molist) {
					filter.getRoomFilter().put(mo.getMoid(), mo.getResid());
				}

				List<MOTag> taglist = envMonitorService.queryRelationByResID(params);
				for (MOTag mo : taglist) {
					filter.getRoomFilter().put(mo.getMoID(), mo.getResID());
				}
			}
			return filter;
		} catch (Exception e) {
			log.info("query filter error:", e);
		}
		return null;
	}
	
	/**
	 * 根据MOID过滤符合条件的告警
	 * @param resIds
	 * @return
	 */
	public FilterParamObject loadTopoFilter(String moids) {
		FilterParamObject filter = new FilterParamObject(); 
		if (moids != null && moids.compareTo("") > 0) { 
			filter.setTopoFilter(moids);
		}
		return filter;
	}
}