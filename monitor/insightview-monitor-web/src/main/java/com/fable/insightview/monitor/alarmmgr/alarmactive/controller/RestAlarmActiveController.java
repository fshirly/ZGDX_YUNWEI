package com.fable.insightview.monitor.alarmmgr.alarmactive.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.monitor.alarmdispatcher.core.SyncAlarmOperate;
import com.fable.insightview.monitor.alarmmgr.alarmactive.mapper.AlarmActiveMapper;
import com.fable.insightview.monitor.alarmmgr.alarmhistory.mapper.AlarmHistoryMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmHistoryInfo;
import com.fable.insightview.monitor.entity.AlarmNode;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import org.apache.logging.log4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Vpn网络频道rest接口
 * @author liy
 * @Date 2016-7-5
 */
@Controller
@RequestMapping("rest/monitor/alarmActive_app")
public class RestAlarmActiveController {
	private final static Logger logger = LoggerFactory.getLogger(RestAlarmActiveController.class);
	@Autowired
	private AlarmActiveMapper alarmActiveMapper;
	@Autowired
	private AlarmHistoryMapper alarmHistoryMapper;
	/**
	 * 批量处理确认告警
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/doBathAlarmActiveConfirm_app")
	@ResponseBody
	public synchronized boolean doBathAlarmActiveConfirm(HttpServletRequest request) {
		logger.error("进入接口了");
		AlarmNode vo=new AlarmNode();
		vo.setConfirmTime(new Timestamp(new Date().getTime()));
		String id = request.getParameter("id");
		String confirmInfo=request.getParameter("confirmInfo");
		logger.error("id:"+",confirmInfo:"+confirmInfo);
		try {
			if (id != "" && id != null) {
				logger.error("获取到id,id值为:"+id);
				vo.setMoName("(" + id + ")");
				alarmActiveMapper.bathAlarmActiveConfirm(vo);
				SyncAlarmOperate.getInstance().sendAlarmMessage(id);// 传送告警消息
			}else{
				logger.error("没有获取id值"+id);
			}
		} catch (Exception e) {
			logger.error("确认告警失败,告警Id为:"+id+"确认信息为:"+vo.getConfirmInfo());
			return false;
		}
		return true;
	}
	/**
	 * 批量处理告警清除
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping("/doBathClearAlarmActive_app")
	@ResponseBody
	public synchronized boolean doBathClearAlarmActive(HttpServletRequest request) {
		logger.error("进入清除接口了");
		AlarmNode vo=new AlarmNode();
		String id = request.getParameter("id");
		String cleanInfo=request.getParameter("cleanInfo");
		vo.setCleanInfo(cleanInfo);
		logger.error("id:"+",cleanInfo:"+cleanInfo);
		// 查询活动告警表中是否存在告警信息
		String newId = alarmActiveMapper.queryAlarmIdsByIds(id + "");
		if (newId != "" && newId != null) {
			/* 告警清除后,将该告警信息插入历史告警表中 */
			try {
				clearAlarmInfo(vo,newId);
			} catch (Exception e) {
				logger.error("历史告警插入异常：" + e.getMessage(), newId);
				return false;
			}
		}else{
			logger.error("没有获取id值"+id);
		}
		return true;
	}
	/**
	 * 告警清除后,将该告警信息插入历史告警表中
	 * 
	 * @param vo
	 * @param userVo
	 * @param id
	 */
	private void clearAlarmInfo(AlarmNode vo, String id) {
		AlarmHistoryInfo historyVo = new AlarmHistoryInfo();
		historyVo.setMoName(id);
		historyVo.setCleanInfo(vo.getCleanInfo());
		historyVo.setCleanTime(new Timestamp(new Date().getTime()));// 当时时间
		// historyVo.setAlarmStatus(5);// 表示人工清除
		historyVo.setAlarmOperateStatus(24);// 表示人工清除
		alarmHistoryMapper.copyActiveClearInfo(historyVo);// 插入历史告警表
		alarmActiveMapper.deleteAlarmActive(id);// 删除活动告警表
		SyncAlarmOperate.getInstance().sendAlarmMessage(id);// 传送告警消息
	}
}
