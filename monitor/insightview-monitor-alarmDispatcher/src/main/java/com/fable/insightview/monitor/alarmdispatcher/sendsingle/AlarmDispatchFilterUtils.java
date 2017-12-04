package com.fable.insightview.monitor.alarmdispatcher.sendsingle;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.entity.AlarmDispatchFilter;
import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.entity.AlarmDispatchFilterDef;
import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.entity.AlarmDispatchFilterDefExample;
import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.entity.AlarmDispatchFilterExample;
import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.mapper.AlarmDispatchFilterDefMapper;
import com.fable.insightview.monitor.alarmmgr.alarmDispatchFilter.mapper.AlarmDispatchFilterMapper;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.platform.core.util.BeanLoader;

/**
 * 自定告警派单规则内存缓存
 * 
 * @author huangzx
 * @date 2015-05-27
 * 
 */
// @SuppressWarnings("all")
public class AlarmDispatchFilterUtils {
	public static Logger log = LoggerFactory.getLogger(AlarmDispatchFilterUtils.class);

	private static AlarmDispatchFilterUtils alarmDispatchFilterUtils = null;

	private AlarmLevelHandler alarmLevelHandler;

	private AlarmTypeHandler alarmTypeHandler;

	private AlarmDefineHandler alarmDefineHandler;

	private AlarmSourceHandler alarmSourceHandler;

	private static AlarmDispatchFilterMapper adfm = (AlarmDispatchFilterMapper) BeanLoader.getBean("alarmDispatchFilterMapper");

	private static AlarmDispatchFilterDefMapper def = (AlarmDispatchFilterDefMapper) BeanLoader.getBean("alarmDispatchFilterDefMapper");

	private AlarmDispatchFilterUtils() {
		alarmLevelHandler = new AlarmLevelHandler();
		alarmTypeHandler = new AlarmTypeHandler();
		alarmDefineHandler = new AlarmDefineHandler();
		alarmSourceHandler = new AlarmSourceHandler();

		alarmLevelHandler.setSuccessor(alarmTypeHandler);
		alarmTypeHandler.setSuccessor(alarmDefineHandler);
		alarmDefineHandler.setSuccessor(alarmSourceHandler);

		buildAlarmDispatchFilter();

	}

	public static AlarmDispatchFilterUtils getInstance() {
		if (alarmDispatchFilterUtils == null) {
			alarmDispatchFilterUtils = new AlarmDispatchFilterUtils();
		}
		return alarmDispatchFilterUtils;
	}

	public void test() {
		AlarmDispatchFilterDefExample example = new AlarmDispatchFilterDefExample();
		example.createCriteria().andIsDefaultEqualTo((short) 1);
		List<AlarmDispatchFilterDef> temp = def.selectByExample(example);
		log.error("[TEST] 获取启用的派发规则.");
		log.error("[TEST] 已经启用的规则数量: " + temp.size());
		
		AlarmDispatchFilterExample e2 = new AlarmDispatchFilterExample();
		e2.createCriteria().andFilterIdEqualTo(temp.get(0).getId());
		List<AlarmDispatchFilter> list = adfm.selectByExample(e2);
		log.error("[TEST] 获取派发规则详细.");
		log.error("[TEST] 已经启用的规则数量: " + list.size());
	}

	public void buildAlarmDispatchFilter() {
		alarmLevelHandler.getKeys().clear();
		alarmTypeHandler.getKeys().clear();
		alarmDefineHandler.getKeys().clear();
		alarmSourceHandler.getKeys().clear();

		// 获取启用规则
		AlarmDispatchFilterDefExample example = new AlarmDispatchFilterDefExample();
		example.createCriteria().andIsDefaultEqualTo((short) 1);
		List<AlarmDispatchFilterDef> temp = def.selectByExample(example);
		if (null == def) {
			log.error("[ERROR]: AlarmDispathFilterUtils:　AlarmDispatchFilterDefMapper　是null ");
			def = (AlarmDispatchFilterDefMapper) BeanLoader.getBean("alarmDispatchFilterDefMapper");
		}
		if (null == temp) {
			log.error("[ERROR]: AlarmDispathFilterUtils:　temp是null ");
			return;
		}
		if (temp.size() == 0) {
			log.error("[ERROR]: AlarmDispathFilterUtils:　获取派单定义规则的size为0");
			return;
		}
		// 查找规则下面的过滤条件
		AlarmDispatchFilterExample e2 = new AlarmDispatchFilterExample();
		e2.createCriteria().andFilterIdEqualTo(temp.get(0).getId());
		List<AlarmDispatchFilter> list = adfm.selectByExample(e2);
		//
		for (AlarmDispatchFilter adf : list) {
			if ("AlarmLevel".equals(adf.getFilterKey())) {
				alarmLevelHandler.getKeys().add(adf.getFilterKeyValue());
			} else if ("AlarmType".equals(adf.getFilterKey())) {
				alarmTypeHandler.getKeys().add(adf.getFilterKeyValue());
			} else if ("AlarmDefineID".equals(adf.getFilterKey())) {
				alarmDefineHandler.getKeys().add(adf.getFilterKeyValue());
			} else if ("AlarmSourceMOID".equals(adf.getFilterKey())) {
				alarmSourceHandler.getKeys().add(adf.getFilterKeyValue());
			}
		}
	}

	/**
	 * 根据告警派发规则判断告警是否派发
	 * 
	 * @param alarmNode
	 * @return
	 */
	public boolean isDispatch(AlarmNode a) {
		int sum = alarmLevelHandler.getKeys().size();
		sum += alarmTypeHandler.getKeys().size();
		sum += alarmDefineHandler.getKeys().size();
		sum += alarmSourceHandler.getKeys().size();
		// 没有验证规则直接转发
		if (0 == sum) {
			return false;
		}
		return alarmLevelHandler.handleRequest(a);
	}
}
