package com.fable.insightview.platform.common.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.fable.insightview.platform.common.excel.model.DeptModel;

/*��
 * ����˾��
 * �ļ���ModelManagerTest.java
 * �����ߣ�YJM - yinjingmin
 * �ʡ��䣺yinjmin@163.com
 * �汾�ţ�1.0
 * ʱ���䣺2008-3-8 ����01:36:42
 */

public class ModelManagerTest extends TestCase {
    //form������
    List formModelList = new ArrayList(); 
    //map ����
    List mapModelList =  new ArrayList();
    
    protected void setUp() throws Exception {
        super.setUp();        
        //��form������
        for(int i=0;i<68;i++){
          DeptModel dept = new DeptModel();
          dept.setDeptName("�ܲ�");
          dept.setDeptCode("A10" + i);
          dept.setDeptNo(i);
          dept.setReceiveFileName("12345678");
          dept.setSendFileName("���Ϸ�");
          formModelList.add(dept);
        }
        //map ����
        for(int i=0;i<68;i++){
            Map m = new HashMap();
            m.put("deptName", "�ܲ�");
            m.put("deptCode", "A10"+i);
            m.put("receiveFileName", "1234567");
            m.put("deptNo", i);
            m.put("sendFileName", "�й��̷�");
            mapModelList.add(m);
        }
    }
    //�������ļ���������ı���ͷ��������form��list ���
    public void testFormConfig(){
        String fileName = "D:\\text\\modeltoexcel_FormConfig.xls";
        ModelManager mm = null;
        try {
            mm = new ModelManager(new FileOutputStream(new File(fileName)),"deptModel",formModelList);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
        mm.getMte().setRowHeight(500);
        mm.getExcelFile();
    }
    //�ı����в��Ż���ȫ���б���������֣�����form list ���
    public void testFormConfig_title(){
        Map map = new HashMap();
        map.put("deptName", "�������_�ı�");
        map.put("deptCode", "���ű��_�ı�");
        map.put("deptNo", "����_�ı�"); 
        String fileName = "D:\\text\\modeltoexcel_FormConfig_title.xls";
        ModelManager mm = new ModelManager(fileName,"deptModel",formModelList);
        mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
        mm.getMte().setRowHeight(500);
        mm.getMte().setDynamicTitleMap(map);
        mm.getExcelFile();
    }
    //�������ļ���������ı���ͷ��������map��list ���
    public void testMapConfig(){
        String fileName = "D:\\text\\modeltoexcel_MapConfig.xls";
        ModelManager mm = new ModelManager(fileName,"deptModel",mapModelList);
        mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
        mm.getMte().setRowHeight(500);
        
        mm.getExcelFile();
    }
//  �ı��б���������֣�����Map list ���
    public void testMapConfig_title(){
        Map map = new HashMap();
        map.put("deptName", "�������_�ı�");
        map.put("deptCode", "���ű��_�ı�");
        map.put("deptNo", "����_�ı�"); 
        
        String fileName ="D:\\text\\modeltoexcel_MapConfig_title.xls";
        ModelManager mm = new ModelManager(fileName,"deptModel",mapModelList);
        mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
        mm.getMte().setRowHeight(500);
        
        mm.getMte().setDynamicTitleMap(map);
        
        mm.getExcelFile();
    }
    
    //����������������� form list ���
    public void testFormColumn(){
        Map map = new HashMap();
        map.put("deptName", "�������_�ı�");
        map.put("deptCode", "���ű��_�ı�");
        map.put("deptNo", "����_�ı�");  
        
        String fileName ="D:\\text\\modeltoexcel_FormColumn.xls";
        ModelManager mm = new ModelManager(fileName,"deptModel",formModelList);
        mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
        mm.getMte().setRowHeight(500);
        
        mm.getMte().setDynamicTitle(true);
        mm.getMte().setDynamicTitleMap(map);
        
        mm.getExcelFile();
    }
    //����������������� map list ���
    public void testMapColumn(){
        Map map = new HashMap();
        map.put("deptName", "�������_�ı�");
        map.put("deptCode", "���ű��_�ı�");
        map.put("deptNo", "����_�ı�");  
        
        String fileName ="D:\\text\\modeltoexcel_MapColumn.xls";
        ModelManager mm = new ModelManager(fileName,"deptModel",mapModelList);
        mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
        mm.getMte().setRowHeight(500);
        
        mm.getMte().setDynamicTitle(true);
        mm.getMte().setDynamicTitleMap(map);
        
        mm.getExcelFile();
    }
    //����ģ�壬�������ļ�������form list ���
    public void testFormTemplate(){
        Map paramMap = new HashMap();
        paramMap.put("Name", "����");
        paramMap.put("Date", "2008-1-23");
        
        String fileName ="D:\\text\\modeltoexcel_FormTemplate.xls";
        ModelManager mm = new ModelManager(fileName,"deptModel",formModelList);
        mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
        mm.getMte().setRowHeight(500);
        
        mm.getMte().setTemplateParam("D:\\text\\deptModelTemplate.xls", 3, paramMap);        
        mm.getExcelFile();
    }
    //����ģ�壬�������ļ�������map list ���
    public void testMapTemplate(){
        Map paramMap = new HashMap();
        paramMap.put("Name", "����");
        paramMap.put("Date", "2008-1-23");
        
        String fileName ="D:\\text\\modeltoexcel_MapTemplate.xls";
        ModelManager mm = new ModelManager(fileName,"deptModel",mapModelList);
        mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
        mm.getMte().setRowHeight(500);
        
        mm.getMte().setTemplateParam("D:\\text\\deptModelTemplate.xls", 3, paramMap);        
        mm.getExcelFile();
    }
    
    //�� Sheet ���
    public void testMultiple(){
        
        //modeltoexcel_Multiple.xls��Ϊģ���ļ�����ʱ���ļ�������ڣ��������㹻���sheet
        String fileName = "D:\\text\\modeltoexcel_Multiple.xls";
        ModelManager mm = null;
        
        mm = new ModelManager(fileName,"deptModel",formModelList);
        
        mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
        mm.getMte().setRowHeight(500);
        mm.getMte().setSheet(0, "��һ");
        mm.getExcelFile();
        
        
//      String fileName = "D:\\text\\modeltoexcel_FormConfig_title.xls";
        //ModelManager mm = new ModelManager(fileName,"deptModel",formModelList);
        Map map = new HashMap();
        map.put("deptName", "�������_�ı�");
        map.put("deptCode", "���ű��_�ı�");
        map.put("deptNo", "����_�ı�"); 
        
        mm.setModelName_List("deptModel",formModelList);
        mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
        mm.getMte().setRowHeight(500);
        mm.getMte().setDynamicTitleMap(map);
        
        mm.getMte().setSheet(1, "�ڶ�");
        mm.getExcelFile();
        
        //ModelManager mm = new ModelManager(fileName,"deptModel",mapModelList);
        Map paramMap = new HashMap();
        paramMap.put("Name", "����");
        paramMap.put("Date", "2008-1-23");
        mm.setModelName_List("deptModel",mapModelList);
        mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
        mm.getMte().setRowHeight(500);
        
        mm.getMte().setTemplateParam("D:\\text\\modeltoexcel_Multiple.xls", 3, paramMap); 
        mm.getMte().setSheet(3, "");
        mm.getExcelFile();
        
    }
    //���ô�ÿ�У��ڼ��п�ʼ����
    public void testRowColumn(){
        ModelManager mm = new ModelManager("D:\\text\\modeltoexcel_rowcolumn.xls","deptModel",mapModelList);
        mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
        mm.getMte().setRowHeight(500);
        
        Map paramMap = new HashMap();
        paramMap.put("Name", "����");
        paramMap.put("Date", "2008-1-23");
        
        //mm.getMte().setTemplateParam("D:\\text\\deptModelTemplate.xls", 3, paramMap);
        mm.getMte().setTemplateParam("D:\\text\\deptModelTemplate.xls", 3,2, paramMap,false);
        mm.getExcelFile();
    }
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
