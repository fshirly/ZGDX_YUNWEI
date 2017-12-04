package com.fable.insightview.platform.common.excel;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.common.excel.config.ExcelConfigFactory;
import com.fable.insightview.platform.common.excel.config.ExcelConfigManager;
import com.fable.insightview.platform.common.excel.file.ModelToExcel;
import com.fable.insightview.platform.common.excel.file.impl.ModelToExcelImpl;

/**
 * @author wul
 * 
 */

public class ModelManager {
  private File excelFile = null;

  private OutputStream excelOutputStream = null;
  
  private List modelList = null;

  private String modelName = "";

  private ModelToExcel mte = null;
  
  private ExcelConfigManager configManager = null;

  public ModelManager(String fileName, String modelName, List modelList) {
    this(new File(fileName), modelName, modelList);
  }
  public ModelManager(File fileName, String modelName, List modelList) {
    this.excelFile = fileName;
    this.modelName = modelName;
    this.modelList = modelList;
    configManager = ExcelConfigFactory.createExcelConfigManger();
  }
  public ModelManager(OutputStream excelOutputStream, String modelName, List modelList) {
      this.excelOutputStream = excelOutputStream;
      this.modelName = modelName;
      this.modelList = modelList;
      configManager = ExcelConfigFactory.createExcelConfigManger();
  }
  public void setModelName_List(String modelName,List modelList){
      this.mte = null;
      this.modelName = modelName;
      this.modelList = modelList;
  }
  public File getExcelFile(){
    if(mte==null){
      //mte = new ModelToExcelImpl(excelFile, configManager.getModel(modelName, ""), modelList);
        if(this.excelFile!=null){
            mte = new ModelToExcelImpl(excelFile, configManager.getModel(modelName, ""), modelList);
        }else{
            mte = new ModelToExcelImpl(this.excelOutputStream, configManager.getModel(modelName, ""), modelList);
        }
    }
    return mte.getExcelfile();
  }
  public ModelToExcel getMte() {
    if(mte==null){
      //mte = new ModelToExcelImpl(excelFile, configManager.getModel(modelName, ""), modelList);
        if(this.excelFile!=null){
            mte = new ModelToExcelImpl(excelFile, configManager.getModel(modelName, ""), modelList);
        }else{
            mte = new ModelToExcelImpl(this.excelOutputStream, configManager.getModel(modelName, ""), modelList);
        }  
    }
    return mte;
  }
  public static void main(String[] args) {
    List modelList = new ArrayList();
    //��form������
//    for(int i=0;i<68;i++){
//      DeptModel dept = new DeptModel();
//      dept.setDeptName("�ܲ�");
//      dept.setDeptCode("A10" + i);
//      dept.setDeptNo(i);
//      dept.setSendFileName("���Ϸ�");
//      modelList.add(dept);
//    }
    //map ����
    for(int i=0;i<68;i++){
        Map m = new HashMap();
        m.put("deptName", "�ܲ�");
        m.put("deptCode", "A10"+i);
        m.put("deptNo", i);
        m.put("sendFileName", "�й��̷�");
        modelList.add(m);
    }
    Map map = new HashMap();
    map.put("deptName", "�������1");
    map.put("deptCode", "���ű��1");
    map.put("deptNo", "����1");  
    
    ModelManager mm = new ModelManager("D:\\text\\modeltoexcel_rowcolumn.xls","deptModel",modelList);
    mm.getMte().setHeader("���ŷ��ļ�ƣ�2007��");
    mm.getMte().setRowHeight(500);
    
    //Ĭ�ϰ������ļ�����������dynamicTitleMap��property,exceltitlename�� ֻ�ǰ�javabean��Ӧ�����Ըı��������
    //���isDynamicTitle��= true�����������dynamicTitleMap��������������ֻ�ǰ�����ʾ���������ļ����á�
    //isDynamicTitle = true��Ҫ��֤��dynamicTitleMap��size������0,���������õ�������javabean�д��ڵ��б������0   
    //mm.getMte().setDynamicTitle(true);
    //mm.getMte().setDynamicTitleMap(map);
    
    
    // �ļ�����ʱ��֧��ģ�嵼��
    /*
     * isTemplate = true ,�������ģ���ļ���ʽ����� templateFile ģ���ļ��� startRow ��ʼ���������
     * paramMap ģ���еĲ���map.put("Name","����")��ģ������һ����Ԫ��Ϊ �Ʊ��� #Name# ,���ʱΪ���Ʊ��� ����
     */
    Map paramMap = new HashMap();
    paramMap.put("Name", "����");
    paramMap.put("Date", "2008-1-23");
    
    //mm.getMte().setTemplateParam("D:\\text\\deptModelTemplate.xls", 3, paramMap);
    mm.getMte().setTemplateParam("D:\\text\\studentModelTemplate.xls", 3,2, paramMap,false);
    mm.getExcelFile();
  }
  
}
