package com.fable.insightview.platform.core.dao.idgenerator.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.transaction.PlatformTransactionManager;

import com.fable.insightview.platform.core.dao.idgenerator.IdentifierGenerator;
import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;
import com.fable.insightview.platform.core.dao.idgenerator.StringGenerator;
import com.fable.insightview.platform.core.util.BeanLoader;

public class IDGeneratorFactory {

	private Map<Class, FieldGenerator> idGenCache = new ConcurrentHashMap<Class, FieldGenerator>();

	private static IDGeneratorFactory idGeneratorFactory;

	private DataSource dataSource = (DataSource) BeanLoader
			.getBean("dataSource");

	private PlatformTransactionManager txManager = (PlatformTransactionManager) BeanLoader
			.getBean("txManager");

	/** 产生序列化的表名 */
	private String sequenceTable = "SysKeyGenerator";

	private IDGeneratorFactory() {
	};

	public static IDGeneratorFactory getInstance() {
		if (idGeneratorFactory == null) {
			idGeneratorFactory = new IDGeneratorFactory();
		}
		return idGeneratorFactory;
	}

	public FieldGenerator getFieldGenerator(Class clazz) {
		getGenerator(clazz);
		return idGenCache.get(clazz);
	}

	public IdentifierGenerator getGenerator(Class clazz) {
		FieldGenerator fieldGenerator = idGenCache.get(clazz);
		if(fieldGenerator != null && fieldGenerator.gen != null){
			return fieldGenerator.gen;
		}
		
		IdentifierGenerator gen = null;
		for (Field field : clazz.getDeclaredFields()) {
			Annotation annotation = field
					.getAnnotation(NumberGenerator.class);
			if (annotation != null) {
				gen = new NumberGeneratorImpl(annotation, dataSource,
						txManager, sequenceTable);
				idGenCache.put(clazz, new FieldGenerator(field, gen));
				break;
			} else {
				annotation = field.getAnnotation(StringGenerator.class);
				if (annotation != null) {
					gen = new StringGeneratorImpl(annotation, dataSource,
							txManager, sequenceTable);
					idGenCache.put(clazz, new FieldGenerator(field, gen));
					break;
				}
			}
		}
		return gen;
	}

	public class FieldGenerator {
		public Field field;
		public IdentifierGenerator gen;
		
		public FieldGenerator(Field field, IdentifierGenerator gen) {
			this.field = field;
			this.gen = gen;
		}
	}

}
