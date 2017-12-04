package com.fable.insightview.platform.backup.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jdbc工具包,数据恢复操作;
 * @author duy 20170812
 *
 */
public class JdbcUtils {
	
	public static Logger logger = LoggerFactory.getLogger(JdbcUtils.class);
    private static Connection connection;  
    private static PreparedStatement pstmt;  
    private static ResultSet resultSet;
    
    private JdbcUtils() {}
    
    /** 
     * 获得数据库的连接 
     * @return 
     */  
    public static synchronized Connection getConnection(DbInfo dbInfo) {
        try {  
        	Class.forName(dbInfo.getDriver());
            connection = DriverManager.getConnection(dbInfo.getUrl(), dbInfo.getUserName(), dbInfo.getPassword());
            logger.info("mysql数据库连接成功");
        } catch (Exception e) {  
        	logger.error("mysql数据库连接成功,请检查数据库连接");
            e.printStackTrace();  
        }  
        return connection;
    }
    
    
    /** 
     * 释放数据库连接 
     */  
    public static void releaseConn(){  
    	try{
    		if (resultSet != null) {
    			resultSet.close();
    		}
    		if(connection != null) {
    			connection.close();
    		}
    		if (pstmt != null) {
    			pstmt.close();
    		}
        }catch(SQLException e){  
            e.printStackTrace();  
        }   
    }
    
    /**
	 * 查询表中的数据和字段
	 * @param tableName
	 * @return
	 */
	public static ArrayList<HashMap<String, String>> getTableData(String sql,DbInfo dbInfo) {
		HashMap<String, String> hashMap = null;
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		try {
			pstmt = JdbcUtils.getConnection(dbInfo).prepareStatement(sql);
			resultSet = pstmt.executeQuery();
			// 使用元数据获取一个表字段的总数
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int coulum = rsmd.getColumnCount();
			while (resultSet.next()) {
				hashMap = new HashMap<String, String>();
				for (int i = 0; i < coulum; i++) {
					String columName = rsmd.getColumnName(i + 1);
					hashMap.put(columName, resultSet.getString(i + 1));
				}
				result.add(hashMap);
			}
		} catch (SQLException e) {
			logger.error("sql查询解析出错",e);
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConn();
		}
		return result;
	}
	
	
	/**
	 * 解析数据，还原表数据;
	 * @param al
	 * @param tableName
	 * @return
	 */
	public static Map<String,Object> insertDataToSql(ArrayList<HashMap<String, String>> al,
			String tableName,DbInfo dbInfo) {
		Map<String,Object> resMap = new HashMap<String,Object>();
		connection = JdbcUtils.getConnection(dbInfo);
		boolean flag = false;
		int successTol = 0; //成功记录数
		try {
			for (int i = 0; i < al.size(); i++) {
				String fieldSql = "";
				String valueSql = "";
				HashMap<String, String> data = (HashMap<String, String>) al.get(i);
				Iterator<String> it = data.keySet().iterator();
				while (it.hasNext()) {
					String field = it.next().toString();
					String value = data.get(field);
					if(value == null){
						continue;
					}
					fieldSql += field + ",";
					valueSql += "'" + value + "',";
				}
				// 去除最后字段的最后一个,符号，去除值的最后两个',符号
				fieldSql = fieldSql.substring(0, fieldSql.length() - 1);
				valueSql = valueSql.substring(0, valueSql.length() - 1);
				String sql = "insert into " + tableName + "(" + fieldSql + ") "
						+ " values(" + valueSql + ");";
				try {
					connection.prepareStatement(sql).execute();
				} catch (Exception e) {
					successTol--;
					logger.error("插入数据异常,主键重复异常，不做处理!");
				}
				successTol++;
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.releaseConn();
		}
		resMap.put("success", flag);
		resMap.put("successTol", successTol);
		return resMap;
	}
	
	
	
    public static void main(String[] args) {
    	/*String sql = "SELECT * FROM PerfServCPU";
    	DbInfo backup = new DbInfo();
    	backup.setUserName("root");
    	backup.setPassword("123456");
    	backup.setUrl("jdbc:mysql://localhost:3306/sc_0811_bak");
    	ArrayList<HashMap<String, String>> al = getTableData(sql,backup);
    	backup.setUrl("jdbc:mysql://localhost:3306/sc_0811");
    	Map<String,Object> res = insertDataToSql(al, "PerfServCPU_TEST",backup);*/
	}
}
