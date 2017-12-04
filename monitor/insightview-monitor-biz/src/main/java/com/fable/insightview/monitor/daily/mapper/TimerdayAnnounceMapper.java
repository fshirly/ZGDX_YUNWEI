package com.fable.insightview.monitor.daily.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TimerdayAnnounceMapper {
	/*
	 * 查询昨日值班人
	 */
	@Select("select sys.UserName as zhibr from PfUsersOfDuty pfu,SysUserInfo sys  where sys.userID=pfu.UserId and pfu.ID "
			+ " in(select max(pfu.ID) from PfDuty pfd, PfUsersOfDuty pfu where pfd.ID=pfu.DutyId and pfu.UserType=1 and pfd.DutyDate=#{date})")
	public String querybeforeDutePeople(@Param("date") String date);

	// /*
	// * 短信发送
	// */
	// @Insert("insert into SmsNotificationTask(ID,PhoneNumber,Message,SendedTimes,MaxTimes,STATUS,LastUpdateTime,ExpectSendTime) values (#{id},#{phoneNumber},#{message},'0','3','1',#{lastUpdateTime},#{expectSendTime}) ")
	// public void insertsendDuanx(SmsNotificationTaskBean smsNotificationTask);

	/*
	 * 昨日值班人短信
	 */
	@Select("select sys.MobilePhone as mobilePhone from PfUsersOfDuty pfu,SysUserInfo sys  where sys.userID=pfu.UserId and pfu.ID "
			+ " in(select max(pfu.ID) from PfDuty pfd, PfUsersOfDuty pfu where pfd.ID=pfu.DutyId and pfu.UserType=1 and pfd.DutyDate=#{date})")
	public String querybeforePhoneNumber(@Param("date") String date);
}