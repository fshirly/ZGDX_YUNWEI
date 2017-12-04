package com.fable.insightview.monitor.alarmmgr.alarmnotify.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.monitor.alarmmgr.alarmnotify.service.IAlarmNotifyCfgService;
import com.fable.insightview.monitor.alarmmgr.alarmnotifytousers.mapper.AlarmNotifyToUsersMapper;
import com.fable.insightview.monitor.alarmmgr.entity.AlarmNotifyToUsersBean;
import com.fable.insightview.platform.dao.ISysUserDao;
import com.fable.insightview.platform.dutymanager.duty.service.IDutyService;
import com.fable.insightview.platform.entity.SysUserInfoBean;

@Service
public class AlarmNotifyCfgServiceImpl implements IAlarmNotifyCfgService {
	@Autowired
	AlarmNotifyToUsersMapper alarmNotifyToUsersMapper;
	@Autowired
	ISysUserDao sysUserDao;
	@Autowired
	IDutyService dutyService;
	@Override
	public List<AlarmNotifyToUsersBean> getAllNotifyToUsersByID(int policyID) {
		List<AlarmNotifyToUsersBean> userLst = alarmNotifyToUsersMapper.getNotifyToUsersByID(policyID);
		List<Integer> userIds = new ArrayList<Integer>();
		for (int i = 0; i < userLst.size(); i++) {
			if(userLst.get(i).getUserID() != null){
				userIds.add(userLst.get(i).getUserID());
			}
		}
		for (int i = 0; i < userLst.size(); i++) {
			if(userLst.get(i).getUserID() != null){
				int userId = userLst.get(i).getUserID();
				//如果是值班负责人
				if(userId == -100){
					Date currentTime = new Date();
					//获得当前时间的值班负责人
					String dutierId = dutyService.getPointDuyter(currentTime);
					if(!"".equals(dutierId) && dutierId != null && !"null".equals(dutierId) ){
						if(!userIds.contains(Integer.parseInt(dutierId))){
							List<SysUserInfoBean> list = sysUserDao.getSysUserByConditions("userID", dutierId);
							SysUserInfoBean sysUserBeanTemp = list.get(0);
							userLst.get(i).setUserName(sysUserBeanTemp.getUserName());
							userLst.get(i).setEmail(sysUserBeanTemp.getEmail());
							userLst.get(i).setMobileCode(sysUserBeanTemp.getMobilePhone());
						}
						//如果配置的通知用户中包含值班负责人，则去掉值班负责人
						else{
							userLst.remove(i);
						}
					}
				}
			}
		}
		return userLst;
	}

}
