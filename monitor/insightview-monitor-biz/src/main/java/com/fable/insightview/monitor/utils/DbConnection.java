package com.fable.insightview.monitor.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.monitor.machineRoom.service.DBUtil;

public class DbConnection {
	private static byte[] lock = new byte[0];	//控制moid的获取
	public static String url = null;
	public static String driver = null;
	public static String username = null;
	public static String password = null;
	private static final Logger log = LoggerFactory.getLogger(DbConnection.class);
	static {
		Properties p = new Properties();
		try {
			p.load(DBUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
				url = p.getProperty("jdbc.url");
				driver = p.getProperty("jdbc.driverClassName");
				username = p.getProperty("jdbc.username");
				password = p.getProperty("jdbc.password");
					Class.forName(driver);
		} catch (Exception e) {
			log.error("获取数据库连接失败",e);
		}
	}
	public static int getMOID(int len) {
		return getID("MO", len);
	}

	public static  int getID(String tableName, int len) {
		Connection conn = null;
		CallableStatement proc = null;
		int ID = -1;

		if(len <= 0)
			return -1;

		try {
			synchronized(lock) {
				conn = getConnection();
				proc = conn.prepareCall("{ call getTableSequence(?,?,?) }");
				proc.setString(1, tableName);
				proc.setInt(2, len);
				proc.registerOutParameter(3, Types.INTEGER);

				proc.execute();

				ID = proc.getInt(3);
			}
		} catch (Exception e) {
			log.error("获取表"+tableName+"ID异常. ", e);
		} finally {
			try {
				if(proc != null)
					proc.close();
				if (conn != null)
					conn.close();
			} catch(Exception e) {

			}
		}
		return ID;
	}
	
	public static  Connection getConnection() {
		Connection con = null;
		try {
			if(null !=url && null !=username && null !=password){
				con = DriverManager.getConnection(url, username, password);
			}
		} catch (SQLException e) {
			log.error("the method getConnection() occurs exception:\n" + e.getMessage());
		}
		return con;
	}
}
