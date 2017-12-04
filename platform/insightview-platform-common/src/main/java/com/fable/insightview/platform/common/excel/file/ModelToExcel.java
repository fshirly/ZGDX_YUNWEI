package com.fable.insightview.platform.common.excel.file;

import java.io.File;
import java.util.Map;

import jxl.write.WritableCellFormat;

/**
 * @author wul
 * 
 */

public interface ModelToExcel {
    public void setHeader(String header);

    public void setHeaderCellFormat(WritableCellFormat headerFormat);

    public void setTitleCellFormat(WritableCellFormat titleFormat);

    public void setNormolCellFormat(WritableCellFormat normolFormat);

    public void setRowHeight(int intHeight);

    // Ĭ�ϰ������ļ�����������dynamicTitleMap��property,exceltitlename��
    // ֻ�ǰ�javabean��Ӧ�����Ըı��������
    // ��� isDynamicTitle = true ���������dynamicTitleMap ������������ֻ������ʾ���������ļ����á�
    // isDynamicTitle = true Ҫ��֤��dynamicTitleMap size
    // ����0,���������õ�������javabean�д��ڵ��б������0

    public void setDynamicTitle(boolean isDynamicTitle);

    public void setDynamicTitleMap(Map dynamicTitleMap);

    public File getExcelfile();

    // �ļ�����ʱ��֧��ģ�嵼��
    /*
     * isTemplate = true ,�������ģ���ļ���ʽ����� templateFile ģ���ļ��� startRow ��ʼ���������
     * paramMap ģ���еĲ���map.put("Name","����")��ģ������һ����Ԫ��Ϊ �Ʊ��� #Name# ,���ʱΪ���Ʊ��� ����
     */

    public void setTemplateParam(String templateFile, int startRow, Map paramMap);

    public void setTemplateParam(boolean isTemplate, String templateFile, int startRow, Map paramMap);

    public void setParamMap(Map paramMap);

    public void setStartRow(int startRow);

    public void setTemplateFile(String templateFile);

    public void setTemplate(boolean isTemplate);
    //edit yjm 2008-03-28 
    public void setTemplateParam(String templateFile,int startRow,int startColumn,Map paramMap,boolean isInsertRow);
    public void setTemplateParam(boolean isTemplate,String templateFile,int startRow,int startColumn,Map paramMap,boolean isInsertRow);
    
    //֧�ֶ� Sheet�����
    public void setSheet(int sheetNum,String sheetName);
    

}
