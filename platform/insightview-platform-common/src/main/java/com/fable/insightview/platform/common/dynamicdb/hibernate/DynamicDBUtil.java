package com.fable.insightview.platform.common.dynamicdb.hibernate;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Mappings;
import org.hibernate.mapping.PersistentClass;

import static com.fable.insightview.platform.common.dynamicdb.hibernate.DynamicDBConstants.*;
/**
 * 动态模型数据库连接实现
 * 目前我们的动态模型使用独立的SessionFactory
 * 目的是为了防止对已有的Spring管理的静态映射的SessionFactory
 * 造成影响，也就是说，目前项目中有两个SessionFactory，一个处理
 * 之前的静态映射，由Spring管理，一个处理需要的动态映射，由我们自己管理
 * @author 郑自辉
 *
 */
public class DynamicDBUtil {
	
	private Configuration cfg;
	
	private SessionFactory sessionFactory;
	
	private Session session;
	
	private static DynamicDBUtil instance;

	private DynamicDBUtil()
	{
		
	}
	
	public static DynamicDBUtil getInstance()
	{
		if (null == instance)
		{
			instance = new DynamicDBUtil();
		}
		return instance;
	}
	
	private synchronized Configuration getConfiguration()
	{
		if (null == cfg)
		{
			cfg = new Configuration();
			File hbm = new File(hbmFileDir);
			File[] hbmFiles = hbm.listFiles();
			if (null != hbmFiles && hbmFiles.length > 0)
			{
				for (File hbmFile : hbmFiles)
				{
					if (!templateHbmFileName.equals(hbmFile.getName()))
					{
						cfg.addFile(hbmFile);
					}
				}
			}
			cfg = cfg.configure();
		}
		return cfg;
	}
	
	private synchronized SessionFactory getSessionFactory()
	{
		if (null == sessionFactory)
		{
			sessionFactory = getConfiguration().buildSessionFactory();
		}
		return sessionFactory;
	}
	
	public synchronized Session getCurrentSession()
	{
		if (null == session || !session.isOpen())
		{
			session = getSessionFactory().openSession();
		}
		return session;
	}
	
	public synchronized void rebuildSessionFactory()
	{
		if (null != cfg)
		{
			cfg = null;
		}
		//重新构建sessionFactgory
		if (null != sessionFactory)
		{
			sessionFactory = null;
		}
		sessionFactory = getConfiguration().buildSessionFactory();
	}
	
	public PersistentClass getClassMapping(String entityName)
	{
		return getConfiguration().getClassMapping(entityName);
	}
	
	public Mappings getMappings()
	{
		return getConfiguration().createMappings();
	}
}
