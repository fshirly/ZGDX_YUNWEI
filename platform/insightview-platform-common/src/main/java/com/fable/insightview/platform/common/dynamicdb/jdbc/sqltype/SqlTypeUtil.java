package com.fable.insightview.platform.common.dynamicdb.jdbc.sqltype;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.common.util.CTD;
import com.thoughtworks.xstream.XStream;

/**
 * 类型映射工具类
 * 处理java类型与数据库类型的转换
 * @author 郑自辉
 *
 */
public class SqlTypeUtil {

	private static Map<String, String> sqlMap = new HashMap<String, String>();
	
	private static Map<String, String> dataTypeMap = new HashMap<String, String>();
	
	static
	{
		XStream xstream = new XStream();
		xstream.alias("sqltype", SqlType.class);
		xstream.addImplicitCollection(SqlType.class,"types", "type", Type.class);
		xstream.addImplicitCollection(SqlType.class, "dataTypes", "datatype", Type.class);
		//这边会根据真实数据库类型自动加载相应的配置文件
		String databaseType = CTD.judgeDateBase();
		InputStream in = SqlTypeUtil.class.getResourceAsStream(databaseType + "_type.xml");
		SqlType sqlType = (SqlType) xstream.fromXML(in);
		if (null != sqlType)
		{
			List<Type> types = sqlType.getTypes();
			if (null != types && !types.isEmpty())
			{
				for (Type type : types)
				{
					sqlMap.put(type.getJava(), type.getSql());
				}
			}
			List<Type> dataTypes = sqlType.getDataTypes();
			if (null != dataTypes && !dataTypes.isEmpty()) {
				for (Type type : dataTypes) {
					dataTypeMap.put(type.getJava(), type.getSql());
				}
			}
		}
	}
	
	public static String sqlType(String java)
	{
		return sqlMap.get(java);
	}
	
	public static String dataType(String javaType) {
		return dataTypeMap.get(javaType);
	}
}
