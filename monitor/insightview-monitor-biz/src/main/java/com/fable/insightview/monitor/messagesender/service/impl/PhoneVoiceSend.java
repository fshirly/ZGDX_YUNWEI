package com.fable.insightview.monitor.messagesender.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fable.insightview.platform.message.entity.PhoneNotificationTaskBean;
import com.fable.insightview.platform.sysconf.service.SysConfigService;


/**
 * 电话语音发送
 * 
 */
@Component("phoneVoiceSend")
public class PhoneVoiceSend {
	private static final Logger logger = LoggerFactory.getLogger(PhoneVoiceSend.class);
	private static final int TYPE_PHONEVOICE = 210;
	@Autowired
	SysConfigService sysConfigService;
	/**
	 * 创建数据库连接
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 */
	public Connection getConnection(String url,String user,String password) {  
        try {  
        	logger.info("创建数据库连接....");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
            return DriverManager.getConnection(url, user, password);  
        } catch (Exception e) {  
            logger.error("创建数据库连接失败！",e);
            return null;
        }  
    }      
	public boolean send(PhoneNotificationTaskBean notificationTaskBean){
		//获得系统配置中SQLServer信息
		Map<String, String> configMap = sysConfigService.getConfByTypeID(TYPE_PHONEVOICE);
		String databaseName = configMap.get("databaseName");
		String url = configMap.get("url");
		String user = configMap.get("user");
		String password = configMap.get("password");
		url += databaseName;
		logger.info("url的值为：{} ,  数据库名称为：{}",url,databaseName);
		logger.info("用户名为：{} , 密码为：{}",user,password);
        Connection  conn = this.getConnection(url, user, password);
        PreparedStatement ps = null;
        if(conn != null){
        	try {
				String sql = "INSERT INTO SendQueue(TaskID,PhoneNum1,SendType,MaxTimes,Timing,VMode,VSource,STimes,State,SeqID) VALUES(?,?,?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, notificationTaskBean.getId());
				ps.setString(2, notificationTaskBean.getPhoneNumber());
				ps.setInt(3, 0);
				ps.setInt(4, notificationTaskBean.getMaxTimes());
				ps.setInt(5, 0);
				ps.setInt(6, notificationTaskBean.getVoiceMessageType());
				ps.setString(7, notificationTaskBean.getMessage());
				ps.setInt(8, 0);
				ps.setInt(9, 0);
				ps.setString(10, notificationTaskBean.getId().toString());
				ps.executeUpdate();
				return true;
			} catch (SQLException e) {
				logger.error("插入表SendQueue异常：",e);
			} finally{
				try {
					if(ps!=null){
						ps.close();
					}
					if(conn!=null){
						conn.close();
					}
				} catch (Exception e) {
					logger.error("关闭连接异常：",e);
				}
			}
        }
        return false;
	}
}
