package com.fable.insightview.monitor.machineRoom.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DBUtil {
	private static final Logger logger = LoggerFactory .getLogger(DBUtil.class);
	private DBUtil(){}
	
	public static String url = null;
	public static String driver = null;
	public static String username = null;
	public static String password = null;
	public static String  Switch = null;
		
	static {
		Properties p = new Properties();
		try {
			p.load(DBUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
			Switch= p.getProperty("yz.jdbc.driverSwitch");
			// 判断扬州数据库配置文件是否开启
			if(null !=Switch && Switch.equals("on")){
				url = p.getProperty("yz.jdbc.url");
				driver = p.getProperty("yz.jdbc.driverClassName");
				username = p.getProperty("yz.jdbc.username");
				password = p.getProperty("yz.jdbc.password");
				if(null !=driver){
					Class.forName(driver);
				}else{
					logger.info("获取数据库driverClassName失败，请检查数据库配置是否正确");
				}
			}else{
				logger.info("扬州数据库连接没有开启========");
			}
		} catch (Exception e) {
			logger.error("获取数据库连接失败",e);
		}
	}
    
   
    
    /********************************************************************************************/
    /**
	 * @return
	 */
	public static Connection getConnection() {
		Connection con = null;
		try {
			if(null !=url && null !=username && null !=password){
				con = DriverManager.getConnection(url, username, password);
			}
		} catch (SQLException e) {
			logger.error("the method DBUtil.getConnection() occurs exception:\n" + e.getMessage());
		}
		return con;
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int update(String sql, Object... params) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			if (null != params){
				for (int i = 0; i < params.length; i++) {
					setParam(i + 1, params[i], ps);
				}
			}
			return ps.executeUpdate();
		} catch (Exception e) {
			logger.error("the method DBUtil.update() occurs exception:\n" + e.getMessage());
		} finally {
			close(null, ps, con);
		}
		return 0;
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String, String>> query(String sql, Object... params) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if(null ==Switch || !Switch.equals("on")){
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("Switch", "the yz.jdbc.driverSwitch is off");
			list.add(errorMap);
			return list;
		}
		logger.debug("进行查询事件=====开始");
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			
			if(null == con){
				logger.info("获取扬州机房数据库连接失败，请检查数据库配置文件================");
				Map<String, String> errorMap = new HashMap<String, String>();
				errorMap.put("error", "the connection is error");
				list.add(errorMap);
				return list;
			}
			
			ps = con.prepareStatement(sql);
			if (null != params){
				for (int i = 0; i < params.length; i++) {
					setParam(i + 1, params[i], ps);
				}
			}
			rs = ps.executeQuery();
			List<String> cols = getResultSetColumns(rs);
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < cols.size(); i++) {
					String col = cols.get(i);
					map.put(col, rs.getString(col));
				}
				list.add(map);
			}
		} catch (Exception e) {
			logger.error("the method DBUtil.query() occurs exception:\n" + e.getMessage());
		} finally {
			close(rs, ps, con);
		}
		logger.debug("查询事件===================结束");
		return list;
	}

	/**
	 * @param index
	 * @param data
	 * @param ps
	 * @throws SQLException
	 */
	public static void setParam(int index, Object data, PreparedStatement ps) throws SQLException {
		if (data == null) {
			ps.setNull(index, Types.VARCHAR);
			return;
		}
		if (data instanceof String) {
			ps.setString(index, (String) data);
			return;
		}
		if (data instanceof Date) {
			ps.setDate(index, (Date) data);
			return;
		}
		ps.setObject(index, data);
	}

	/**
	 * @param rs
	 * @param ps
	 * @param con
	 */
	public static void close(ResultSet rs, PreparedStatement ps, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("the method DBUtil.rsClose() occurs exception:\n" + e.getMessage());
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				logger.error("the method DBUtil.psClose() occurs exception:\n" + e.getMessage());
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				logger.error("the method DBUtil.conClose() occurs exception:\n" + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static List<String> getResultSetColumns(ResultSet rs) throws Exception {
		List<String> list = new ArrayList<String>();
		ResultSetMetaData md = rs.getMetaData();
		int c = md.getColumnCount();
		for (int i = 0; i < c; i++) {
			list.add(md.getColumnLabel(i + 1));
		}
		return list;
	}
	
}
