package com.fable.insightview.platform.support;

import java.io.File;
import java.lang.reflect.Field; 
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile; 

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

public class SqlSessionFactoryBeanExt extends SqlSessionFactoryBean {

	private SqlSessionFactory sqlSessionFactory;

	public void setValue(String name, Object value) {
		try {
			Field field = SqlSessionFactoryBean.class.getDeclaredField(name);
			field.setAccessible(true);
			field.set(this, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SqlSessionFactory getObject() throws Exception {
		if (this.sqlSessionFactory == null) {
			sqlSessionFactory = buildSqlSessionFactory();
			setValue("sqlSessionFactory", sqlSessionFactory);
		}
		return this.sqlSessionFactory;
	}

	public void afterPropertiesSet() throws Exception {
	}

	private static final String ROOT_PATH_SPLIT = ",";
	private static final String[] PATH_REPLACE_ARRAY = { "]" };

	Logger logger = LoggerFactory.getLogger(getClass()); 
 
	public void setTypeAliasesPackage(String typeAliasesPackage) { 
		StringBuffer typeAliasesPackageStringBuffer = new StringBuffer();
		try {
			typeAliasesPackageStringBuffer.append(getResources());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if ("".equals(typeAliasesPackageStringBuffer.toString())) {
			throw new RuntimeException("mybatis typeAliasesPackage *.biz.jar 路径扫描错误");
		}

		typeAliasesPackage = replaceResult(
				typeAliasesPackageStringBuffer.toString()).replace(File.separator, ".");
 		super.setTypeAliasesPackage(typeAliasesPackage); 
	}
	
	/**
	 * scran sub package
	 * @return
	 */
	private String getResources() {
		try {
			StringBuffer resourcePathStringBuffer = new StringBuffer();
			String filepath = new File(Thread.currentThread()
					.getContextClassLoader().getResource("").toURI()).getPath();
			filepath = filepath.replaceAll("\\\\", "/");
			filepath += "/../lib";
			File file = new File(filepath);
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					if (f.getName().startsWith("insightview")
							&& f.getName().endsWith(".jar")
							&& f.getName().indexOf("biz") > -1) {
						java.util.jar.JarFile rr = new JarFile(f);
						Enumeration<JarEntry> entrys = rr.entries();
						while (entrys.hasMoreElements()) {
							JarEntry jar = entrys.nextElement();
							if (jar.getName().startsWith("com/fable/insightview/")
									&& jar.getName().endsWith("entity/")) {
								resourcePathStringBuffer.append(
										jar.getName().replaceAll("//", "."))
										.append(ROOT_PATH_SPLIT);
							}
						}
					}
				}
			}
			return resourcePathStringBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private String replaceResult(String resultStr) {
		for (String replaceStr : PATH_REPLACE_ARRAY) {
			resultStr = resultStr.replace(replaceStr, "");
		}
		return resultStr;
	}
}