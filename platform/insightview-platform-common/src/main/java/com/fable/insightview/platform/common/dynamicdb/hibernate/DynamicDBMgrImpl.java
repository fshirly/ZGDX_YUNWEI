package com.fable.insightview.platform.common.dynamicdb.hibernate;

import static com.fable.insightview.platform.common.dynamicdb.hibernate.DynamicDBConstants.hbmFileDir;

import java.io.File;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Mappings;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.SimpleValue;

import com.fable.insightview.platform.common.dynamicdb.api.IDynamicDB;
/**
 * 动态模型的具体实现
 * 主要实现动态创建表、增加表字段、删除表字段和删除表结构
 * @author 郑自辉
 *
 */
public class DynamicDBMgrImpl extends DynamicDBImpl implements IDynamicDB{

	/**
	 * 新增表结构
	 */
	public void createTable(String tableName) {
		/**
		 * 根据表名称首先创建动态映射文件，采用动态模型
		 * 文件名称为表名
		 */
		DynamicDBMappingMgr.createHbmFile(tableName);
		
		/**
		 * 生成动态映射文件之后动态加载映射，重新构建SessionFactory
		 */
		DynamicDBUtil.getInstance().rebuildSessionFactory();
	}

	public void addColumn(String tableName, String columName,
			String columnType) {
		/**
		 * 创建自定义字段
		 */
		Mappings mappings = DynamicDBUtil.getInstance().getMappings();
		SimpleValue simpleVal = new SimpleValue(mappings);
		simpleVal.addColumn(new Column(columName));
		simpleVal.setTypeName(columnType);
		PersistentClass pClass = getPersistentClass(tableName);
		simpleVal.setTable(pClass.getTable());
		
		Property property = new Property();
		property.setName(columName);
		property.setValue(simpleVal);
		getCustomProperties(tableName).addProperty(property);
		
		/**
		 * 根据表名更新映射文件
		 * 具体为dynamic-component子元素插入<property>
		 */
		updateMapping(tableName);
		
		/**
		 * 更新映射文件后动态加载，重新构建SessionFactory
		 */
		DynamicDBUtil.getInstance().rebuildSessionFactory();
	}

	public void removeColumn(String tableName, String colomnName,
			boolean isLogic) {
		@SuppressWarnings("unchecked")
		Iterator<Property> propertyIterator = getCustomProperties(tableName).getPropertyIterator();
        while (propertyIterator.hasNext()) {
            Property property = (Property) propertyIterator.next();
            if (property.getName().equals(colomnName)) {
                propertyIterator.remove();
                updateMapping(tableName);
                break;
            }
        }
        
        /**
		 * Hibernate根据映射文件重新构建SessionFactory并不会删除
		 * 原来的字段，所以需要我们手动删除字段
		 * 注意：如果要保留原来的字段作为历史数据，则不需要执行下面的代码
		 * 只需要删除字段表中的记录即可
		 */
        if (!isLogic)
        {
        	Session session = DynamicDBUtil.getInstance().getCurrentSession();
            String dropSql = "ALTER TABLE " + tableName +" DROP COLUMN " + colomnName;
            Query query = session.createSQLQuery(dropSql);
            query.executeUpdate();
            session.close();
        }
        
        /**
		 * 更新映射文件后动态加载，重新构建SessionFactory
		 */
		DynamicDBUtil.getInstance().rebuildSessionFactory();
	}

	public void dropTable(String tableName) {
		/**
		 * 首先删除该表相应的映射文件
		 */
		File hbmFile = new File(hbmFileDir + tableName + ".hbm.xml");
		if (hbmFile.exists())
		{
			hbmFile.delete();
		}
		
		/**
		 * 物理删除该表结构
		 */
		Session session = DynamicDBUtil.getInstance().getCurrentSession();
        String dropSql = "DROP TABLE " + tableName;
        Query query = session.createSQLQuery(dropSql);
        query.executeUpdate();
        session.close();
		
		/**
		 * 更新映射文件后动态加载，重新构建SessionFactory
		 */
		DynamicDBUtil.getInstance().rebuildSessionFactory();
	}
	
	/**
	 * 更新映射文件
	 */
	private synchronized void updateMapping(String tableName)
	{
		DynamicDBMappingMgr.updateClassMapping(tableName,getCustomProperties(tableName));
	}

	@Override
	public void createTable(String tableName, String primaryKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addColumn(String tableName, String columnName, String javaType,
			int length) {
		// TODO Auto-generated method stub
		
	}
}
