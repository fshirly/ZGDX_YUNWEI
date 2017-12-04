package com.fable.insightview.platform.common.dynamicdb.hibernate;

import static com.fable.insightview.platform.common.dynamicdb.hibernate.DynamicDBConstants.hbmFileDir;
import static com.fable.insightview.platform.common.dynamicdb.hibernate.DynamicDBConstants.templateHbmFileName;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Iterator;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.Property;
import org.hibernate.type.Type;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * 更新动态模型映射文件
 * 在添加字段和删除字段时需要调用里面的方法
 * 同时还提供根据表名生成对应的映射文件
 * @author 郑自辉
 *
 */
public class DynamicDBMappingMgr {

	/**
	 * 更新映射文件
	 * @param dbMgr
	 */
	public static void updateClassMapping(String tableName,Component customProperties) {
		try {
			String file = hbmFileDir + tableName + ".hbm.xml";
			Document document = DynamicDBXMLUtil.loadDocument(file);
			NodeList componentTags = document
					.getElementsByTagName("dynamic-component");
			Node node = componentTags.item(0);
			DynamicDBXMLUtil.removeChildren(node);
			
			@SuppressWarnings("unchecked")
			Iterator<Property> propertyIterator = customProperties.getPropertyIterator();
			while (propertyIterator.hasNext()) {
				Property property = (Property) propertyIterator.next();
				Element element = createPropertyElement(document, property);
				node.appendChild(element);
			}

			DynamicDBXMLUtil.saveDocument(document, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据动态属性创建映射文件的相应元素
	 * @param document
	 * @param property
	 * @return
	 */
	private static Element createPropertyElement(Document document,
			Property property) {
		Element element = document.createElement("property");
		Type type = property.getType();

		element.setAttribute("name", property.getName());
		element.setAttribute("column", ((Column) property.getColumnIterator()
				.next()).getName());
//		element.setAttribute("type", type.getReturnedClass().getName());
		element.setAttribute("type", type.getName());
		element.setAttribute("not-null", String.valueOf(false));

		return element;
	}
	
	/**
	 * 根据模板生成相应的动态模型映射文件
	 * @param tableName
	 */
	private static void copyHbmFromTbl(String tableName) { 
    	File srcFile = new File(hbmFileDir + templateHbmFileName);
        if (tableName == null) {  
            return;
        } else {  
            try {  
                @SuppressWarnings("resource")
				FileChannel fcin = new FileInputStream(srcFile).getChannel();  
                @SuppressWarnings("resource")
				FileChannel fcout = new FileOutputStream(new File(hbmFileDir,  
						tableName + ".hbm.xml")).getChannel();
                fcin.transferTo(0, fcin.size(), fcout);  
                fcin.close();  
                fcout.close();  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        } 
    } 
	
	/**
	 * 生成对应表的动态映射
	 * @param tableName
	 */
	public static void createHbmFile(String tableName)
	{
		copyHbmFromTbl(tableName);
		
		/**
		 * 设置entity-name属性
		 */
		try {
			String hbmFile = hbmFileDir + tableName + ".hbm.xml";
			Document document = DynamicDBXMLUtil.loadDocument(hbmFile);
			NodeList classTags = document
					.getElementsByTagName("class");
			Node classNode = classTags.item(0);
			Element classE = (Element)classNode;
			classE.setAttribute("entity-name", tableName);
			
			DynamicDBXMLUtil.saveDocument(document, hbmFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
