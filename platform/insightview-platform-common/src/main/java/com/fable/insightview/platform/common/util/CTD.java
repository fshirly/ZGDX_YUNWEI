package com.fable.insightview.platform.common.util;

import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.web.context.ContextLoader;

public class CTD {

	private static String connectType = null;
	
	static {
		if(null == connectType){
			AnnotationSessionFactoryBean configBean = (AnnotationSessionFactoryBean)(ContextLoader.getCurrentWebApplicationContext().getBean("&sessionFactory"));
			Configuration  configuration  = configBean.getConfiguration();
			String dataSource = configuration.getProperty("hibernate.dialect");
			if(dataSource.indexOf("MySQLDialect") > 0){
				connectType = "mysql";
			}else{
				connectType = "oracle";
			}
		}
	}
	
	
	/**
	 * 判断当前连接的数据库
	 * @return
	 * @deprecated
	 */
	public static String judgeDateBase(){
		if(null == connectType){
			AnnotationSessionFactoryBean configBean = (AnnotationSessionFactoryBean)(ContextLoader.getCurrentWebApplicationContext().getBean("&sessionFactory"));
			Configuration  configuration  = configBean.getConfiguration();
			String dataSource = configuration.getProperty("hibernate.dialect");
			if(dataSource.indexOf("MySQLDialect") > 0){
				connectType = "mysql";
			}else{
				connectType = "oracle";
			}
		}
		return connectType;
	}
	
	public static boolean isMySQL() {
		return "mysql".equals(connectType);
	}
	
	public static boolean isOracle() {
		return "oracle".equals(connectType);
	}
}
