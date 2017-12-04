package com.fable.insightview.monitor.machineRoom.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fable.insightview.monitor.dispatcher.constants.DispatcherConstants;
import com.fable.insightview.monitor.machineRoom.mapper.MachineRoomMapper;
import com.fable.insightview.monitor.messTool.MessageResolver;
import com.fable.insightview.monitor.messTool.constants.MessageTopic;
import com.fable.insightview.monitor.util.CurwarnBean;

public class CurwarnTrack {
	@Autowired
	MachineRoomMapper roomMapper;
	@Autowired
	MachineRoomService  roomService;
	
	private static final Logger logger = LoggerFactory .getLogger(CurwarnTrack.class);
	MessageResolver mt = null;
	boolean flag =true;
	/***
	 * 获取zookeeper连接信息
	 */
	public void getProperties(){
		Properties proterties = new Properties(); 
        Properties prop = new Properties();
        try {    
            prop.load(Resources.getResourceAsStream("zookeeper.properties")); 
            proterties.setProperty(DispatcherConstants.ZOOKEEPER_CONNECT_KEY,prop.getProperty("zk.connect"));
        	mt = MessageResolver.getInstance();
			mt.setProperty(prop);
        } catch (IOException e) {   
            e.printStackTrace();  
            logger.error("ZookeeperRegister init error",e);
        }
	}
	
	public void alarmTrack() {
		boolean info =false;
		if(flag){
			info = roomService.queryDeviceInfo();
			flag =false;
		}
			getProperties();
		if(info){
			ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(1);
			stpe.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() { 
					logger.info("轮询机房告警通知=====开始");
					final List<Object> ls = new ArrayList<Object>();
					List<CurwarnBean> list= new ArrayList<CurwarnBean>();
					List<Map<String,String>> resultList = DBUtil.query("select WarnLevel,StartTime,Content from rtcurwarn", null);
					for (Map<String, String> map : resultList) {
						CurwarnBean curWar = new CurwarnBean();
						curWar.setWarnLevel(Integer.parseInt(map.get("WarnLevel")));
						curWar.setStartTime(  Timestamp.valueOf(map.get("StartTime")));
						curWar.setContent(map.get("Content"));
						list.add(curWar);
					}
					if(list !=null && list.size()>0){
						ls.addAll(list);
						mt.produce(MessageTopic.YZ_room, ls);
						ls.clear();
					}
				}
			}, 300, 60, TimeUnit.SECONDS);
			
		}
		
	}
	  
}
