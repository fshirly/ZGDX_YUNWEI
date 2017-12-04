package com.fable.insightview.platform.common.excel.config.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fable.insightview.platform.common.excel.config.ConfigConstant;
import com.fable.insightview.platform.common.excel.config.ExcelConfigManager;
import com.fable.insightview.platform.common.excel.config.RuturnConfig;
import com.fable.insightview.platform.common.excel.config.RuturnPropertyParam;
import com.fable.insightview.platform.common.excel.util.ValidateColumn;

public class ExcelConfigManagerImpl implements ExcelConfigManager {

    private String configName = "ImportExcelToModel.xml";

    private SAXReader saxReader;

    private Document doc;

    private Element root;

    public ExcelConfigManagerImpl() {
        String str = this.getClass().getResource("/").getPath();
        // this.getClass().getResourceAsStream("/");

        //System.out.println(str + configName);

        // InputStream in = ClassLoader.getSystemResourceAsStream(configName);

        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(configName);

        saxReader = new SAXReader();

        try {
            // Reader reader = Resources.getResourceAsReader(configName);

            // doc = saxReader.read(new File(str + configName));
            
            //saxReader.read(in)֧�֡�UTF-8
            doc = saxReader.read(in);
            saxReader.setValidation(true);

            // saxReader.setContentHandler(this);
            // XMLErrorHandler error = new XMLErrorHandler();
            // saxReader.setErrorHandler(error);
            //    
            // saxReader.setFeature("http://xml.org/sax/features/namespaces",true);
            // saxReader.setFeature("http://xml.org/sax/features/validation",true);
            // saxReader.setFeature("http://apache.org/xml/features/validation/schema",
            // true);
            // saxReader.setFeature("http://apache.org/xml/features/validation/schema-full-checking",true);
            //saxReader.read(reader); ��֧�� UTF-8
           
            // doc = saxReader.read(reader);

        } catch (DocumentException e) {
            // TODO �Զ���� catch ��
            e.printStackTrace();
            // } catch (IOException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        root = doc.getRootElement();
    }

    public Element getModelElement(String modelName) {
        //System.out.println("modelName = " + modelName + "-----------");
        List list = root.elements();
        Element model = null;
        Element returnModel = null;
        for (Iterator it = list.iterator(); it.hasNext();) {
            model = (Element) it.next();
            //System.out.println(model.attributeValue("id"));
            if (model.attributeValue("id").equals(modelName)) {
                returnModel = model;
                break;
            }
        }
        if(returnModel==null){
            //System.out.println("����������modelName : " + modelName + " �������ļ��в����ڣ�");
        }
        return returnModel;
    }

    public RuturnConfig getModel(String modelName, String flag) {
        Element model = this.getModelElement(modelName);
        RuturnConfig result = new RuturnConfig();
        if (model != null) {
            result.setClassName(model.attributeValue(ConfigConstant.MODEL_CLASS));
            // result.setPropertyMap(result,model);
            this.setPropertyMap(result, model);
        }
        return result;
    }

    private void setPropertyMap(RuturnConfig result, Element model) {
        Map<String, RuturnPropertyParam> propertyMap = new HashMap<String, RuturnPropertyParam>();
        Map<String, RuturnPropertyParam> columnMap = new HashMap<String, RuturnPropertyParam>();
        List list = model.elements();
        Element property = null;

        for (Iterator it = list.iterator(); it.hasNext();) {
            property = (Element) it.next();
            // //System.out.println("Name = " + property.getName());
            if (property.getName().equals("property")) {
                RuturnPropertyParam modelProperty = new RuturnPropertyParam();
                modelProperty.setName(property.attributeValue(ConfigConstant.PROPERTY_NAME));
                modelProperty.setColumn(property.attributeValue(ConfigConstant.PROPERTY_CLOUMN));
                modelProperty.setExcelTitleName(property.attributeValue(ConfigConstant.PROPERTY_EXCEL_TITLE_NAME));
                modelProperty.setIsNull(property.attributeValue(ConfigConstant.PROPERTY_ISNULL));
                modelProperty.setColumnWidth(property.attributeValue(ConfigConstant.PROPERTY_COLUMN_WIDTH));
                modelProperty.setDataType(property.attributeValue(ConfigConstant.PROPERTY_DATA_TYPE));
                modelProperty.setMaxLength(property.attributeValue(ConfigConstant.PROPERTY_MAX_LENGTH));

                modelProperty.setFixity(property.attributeValue(ConfigConstant.PROPERTY_FIXITY));
                modelProperty.setCodeTableName(property.attributeValue(ConfigConstant.PROPERTY_CODE_TABLE_NAME));
                modelProperty.setDefaultValue(property.attributeValue(ConfigConstant.PROPERTY_DEFAULT));
                modelProperty.setGreaterThan(property.attributeValue(ConfigConstant.PROPERTY_GREATER_THAN));
                //System.out.println(property.attributeValue("name"));
                
                String excelTitle = ValidateColumn.configValidate(propertyMap,modelProperty.getExcelTitleName());
                propertyMap.put(excelTitle, modelProperty);
                
                //System.out.println("column = " + modelProperty.getColumn());
                columnMap.put(modelProperty.getColumn(), modelProperty);
            }
            if (property.getName().equals("flag")) {
                Map flagMap = new HashMap();
                flagMap.put(ConfigConstant.PROPERTY_NAME, property.attributeValue(ConfigConstant.PROPERTY_NAME));
                result.setFlagMap(flagMap);
            }
            if (property.getName().equals("message")) {
                Map messageMap = new HashMap();
                messageMap.put(ConfigConstant.PROPERTY_NAME, property.attributeValue(ConfigConstant.PROPERTY_NAME));
                messageMap.put(ConfigConstant.PROPERTY_EXCEL_TITLE_NAME, property.attributeValue(ConfigConstant.PROPERTY_EXCEL_TITLE_NAME));
                result.setMessageMap(messageMap);
            }
        }
        result.setPropertyMap(propertyMap);
        result.setColumnMap(columnMap);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ExcelConfigManagerImpl test = new ExcelConfigManagerImpl();
        test.getModel("deptModel", "");
        // //System.out.println(model.attributeValue("class"));

    }
}
