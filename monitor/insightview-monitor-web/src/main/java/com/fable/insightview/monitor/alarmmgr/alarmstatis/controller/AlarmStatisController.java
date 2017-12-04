package com.fable.insightview.monitor.alarmmgr.alarmstatis.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmLevelInfo;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmTypeInfo;
import com.fable.insightview.monitor.discover.mapper.MODeviceMapper;
import com.fable.insightview.monitor.entity.AlarmNode;

/**
 * @Description:   告警统计控制器
 * @author         zhengxh
 * @Date           2014-7-28
 */
@Controller
@RequestMapping("/monitor/alarmStatis")
public class AlarmStatisController {
	
	@Autowired
	private AlarmActiveMapper alarmActiveMapper;
	
	@Autowired
	MODeviceMapper moDeviceMapper;
	
	private final static Logger logger = LoggerFactory.getLogger(AlarmStatisController.class);	

	/**
	 * 菜单页面跳转
	 */
	@RequestMapping("/toAlarmStatisList")
	public ModelAndView toAlarmActiveList(AlarmNode vo,ModelMap map,String navigationBar)throws Exception {
		logger.debug("查询参数："+vo.getTimeBegin()+vo.getTimeEnd()+vo.getAlarmStatus()+vo.getViewPic());
		/*统计数据－按级别 */
		List<AlarmLevelInfo> levelLstNum= alarmActiveMapper.queryLevelNumByCondition(vo);
		/*统计数据－按类型 */
		List<AlarmTypeInfo> typeLstNum= alarmActiveMapper.queryTypeNumByCondition(vo);
		
		map.put("levelLstNum", levelLstNum);
		map.put("typeLstNum", typeLstNum);
		//json数据拼接
		StringBuffer levelJson = new StringBuffer();
		StringBuffer levelStr = new StringBuffer();
		StringBuffer levelVal = new StringBuffer();
		StringBuffer typeJson = new StringBuffer();
		StringBuffer typeStr = new StringBuffer();
		StringBuffer typeVal = new StringBuffer();
		StringBuffer levelColor = new StringBuffer(); 
		
		for(int i=0;i<levelLstNum.size();i++){
			AlarmLevelInfo levelVo = levelLstNum.get(i);
			levelStr.append(",'"+levelVo.getAlarmLevelName()+"'");//拼接名称
			levelVal.append(","+levelVo.getTotalNum());//拼接值
			levelJson.append(",{value:"+levelVo.getTotalNum()+",name:'"+levelVo.getAlarmLevelName()+"'}");//拼接json数据
			levelColor.append(",'"+levelVo.getLevelColor()+"'");//拼接颜色
		}
		
		for(int i=0;i<typeLstNum.size();i++){
			AlarmTypeInfo typeVo = typeLstNum.get(i);
			typeStr.append(",'"+typeVo.getAlarmTypeName()+"'");//拼接名称
			typeVal.append(","+typeVo.getTotalNum());//拼接值
			typeJson.append(",{value:"+typeVo.getTotalNum()+",name:'"+typeVo.getAlarmTypeName()+"'}");//拼接json数据
		}
		//删除首个字符
		levelJson.deleteCharAt(0);
		levelStr.deleteCharAt(0);
		levelVal.deleteCharAt(0);
		typeJson.deleteCharAt(0);
		typeStr.deleteCharAt(0);
		typeVal.deleteCharAt(0);
		levelColor.deleteCharAt(0);
		
		map.put("levelLstNumJson",levelJson.toString());
		map.put("levelName",levelStr.toString());
		map.put("levelVal",levelVal.toString());
		map.put("typeLstNumJson", typeJson.toString());
		map.put("typeName",typeStr.toString());
		map.put("typeVal",typeVal.toString());
		map.put("alarmVo", vo);
		map.put("levelColor", levelColor.toString());
		map.put("navigationBar", navigationBar);
		return new ModelAndView("monitor/alarmMgr/alarmstatis/alarmStatis_list");
	}
	
	@RequestMapping("/doStatisAlarm")
	public void doStatisAlarm(){
		logger.info("统计告警数量并更新告警级别");
		try {
			//清空告警统计表
			alarmActiveMapper.clearMOActiveAlarmStInfo();
			
			synchronized (this) {
				alarmActiveMapper.alarmDeviceStatisBySourceMOID();
				alarmActiveMapper.alarmZoomStatisByMOID();
			}
			//变更告警级别暂不处理
//			moDeviceMapper.updateModeviceAllAlarmLevel();
//			moDeviceMapper.updateMiddleWareAllAlarmLevel();
//			moDeviceMapper.updateDBAllAlarmLevel();
		} catch (Exception e) {
			logger.error("告警统计异常信息：{}", e.getMessage());
		}		
	}
}