package com.fable.insightview.platform.common.excel.file.impl;

/**
 * @author wul
 * 
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.fable.insightview.platform.common.excel.config.RuturnConfig;
import com.fable.insightview.platform.common.excel.config.RuturnPropertyParam;
import com.fable.insightview.platform.common.excel.file.ExcelToModel;
import com.fable.insightview.platform.common.excel.util.DateUtils;
import com.fable.insightview.platform.common.excel.util.ValidateColumn;
import com.fable.insightview.platform.common.exception.NonConformanceException;
import com.fable.insightview.platform.common.exception.NotInDataBaseException;
import com.fable.insightview.platform.common.service.IResAssetExcelService;
import com.fable.insightview.platform.common.util.DateUtil;
import com.fable.insightview.platform.core.util.BeanLoader;

public class ExcelToModelImpl implements ExcelToModel {
	
    private File excelFile = null;

    private RuturnConfig excelConfig = null;

    private Map valueMap = null;

    private List fixityList = null;

    // �����list
    private List modelList = new ArrayList();

    // ��֤�ɹ���List
    private List successModelList = new ArrayList();

    // ��֤ʧ�ܵ�List
    private List errorList = new ArrayList();

    // ��ͷ��֤List
    private List messageList = null;
    
    Map<String, Object> codeTableMap = new HashMap<String, Object>();

    // �Ƿ��ж�ȡ�����������б����ظ�ʱ�����԰�Щ����������
    /*
     * isUserColumn = trueʱ���������ļ������Ƶ��кŶ�ȡ
     */
    private boolean isUseColumn = false;

    // ��ȡ�����Ƿ�ִ�У�ִ����ɺ󣬻����modelList,successModelList,errorList��ݡ�

    private boolean isRead = false;

    // ֧�ִ��б����Excel�ļ�ʱ�����������ݿ�ʼ��������Ч������������ļ������Ƶ�excel��ͷ��ʵ��excel�ļ����ĵڼ�����
    private int startTitleRow = 0;

    private List cellColumn = null;
    
    //֧�ִ���sheet ֵ��ָȡ�ڼ���sheet�ڵ�ֵ�����޸�ʱ�䡡2008��1��24
    // intSheet��sheet���� Excel�еڼ���
    // strSheet��sheet ��Excel�е����
    private int intSheet=0;
    
    private String strSheet=null;
    
    //Ϊexcel�ļ��У������ظ�������
    private List excelTitleList = new ArrayList();
    

    public ExcelToModelImpl(File excelFile, RuturnConfig excelConfig, Map valueMap) {
        this.excelConfig = excelConfig;
        this.excelFile = excelFile;
        this.valueMap = valueMap;
        // this.getExcelToModelList();
    }

    /*
     * ���������ļ���,ָ��ȡ�̶�ֵ��������,��ȡ���ŵ�List��.����Ϊ��
     */
    private void setFixity() {
        List fixityList = new ArrayList();
        Map pmap = this.excelConfig.getPropertyMap();
        for (Iterator it = pmap.values().iterator(); it.hasNext();) {
            RuturnPropertyParam propertyBean = (RuturnPropertyParam) it.next();
            if (propertyBean.getFixity().toUpperCase().trim().equals("YES")) {
                fixityList.add(propertyBean);
            }
        }
        this.fixityList = fixityList;
    }

    private void getExcelToModelListByTitle() throws NonConformanceException, NotInDataBaseException {

        // List modelList = new ArrayList();
        //System.out.println("Excel�ļ���ʼ��ȡ����" + (startTitleRow + 1));
        try {
            Workbook book;

            book = Workbook.getWorkbook(this.excelFile);
            
            //Sheet sheet = book.getSheet(0);
            //edit 2008-1-24
            Sheet sheet;
            if(StringUtils.isNotBlank(this.getStrSheet())){
                sheet = book.getSheet(this.getStrSheet());
            }else{
                sheet = book.getSheet(this.getIntSheet());
            }           
             
            //System.out.println("JXL version : " + book.getVersion());
            // editBook = Workbook.createWorkbook(file, book);

             //System.out.println("rows = " + sheet.getRows() + " cols ="+
            // sheet.getColumns());

            // ����������еı��������������10�����У���ֹͣȡ��
            int intContinueCount = 0;

            for (int i = startTitleRow + 1; i < sheet.getRows(); i++) {

                int intcount = getColumnByValue(sheet.getRow(i));
                if (intcount < 1) {
                    intContinueCount++;
                    if (intContinueCount < 11) {
                        continue;
                    } else {
                        break;
                    }
                } else {
                    intContinueCount = 0;
                }

                Object obj = this.getModelClass(excelConfig.getClassName());

                // ����֧��Map,д������ֵ��һ��˽�з��������?
                // BeanWrapper bw = new BeanWrapperImpl(obj);

                // �����־
                boolean flag = false;
                // ������Ϣ
                String strMessage = "";
                Map<String, String> compareMap = new HashMap<String, String>();
                //被比较的name属性名称
                String beCompareName = "";
                //比较的name属性名称
                String compareToName = "";
                // ��excelÿһ�е�ֵ����ȡֵ.
                for (int j = 0; j < sheet.getColumns(); j++) {

                     //System.out.println("i = " + i + " j =" + j);
                    // ȡ��Excel��ͷ
                    //String excelTitleName = sheet.getCell(j, startTitleRow).getContents().trim();
                	String excelTitleName = (String) this.excelTitleList.get(j);
                    
                    
                    // ȡ��Excelֵ
                    String value = sheet.getCell(j, i).getContents().trim();
                    //					
                    // ȡ����������
                    RuturnPropertyParam propertyBean = (RuturnPropertyParam) excelConfig.getPropertyMap().get(excelTitleName);

                    //System.out.println("i = " + i + " j =" + j + " value ="+
                    // value + " title = " + excelTitleName);

                    // �д����־
                    boolean columnFlag = false;
                    if (propertyBean != null) {
                        // �ж��Ƿ�ǿգ��ǿձ�־ ,ȡֵΪ��ʱ����Ϊfalse
                        boolean nullFlag = true;

                        if (propertyBean.getIsNull().equals("N")) {
                            if (value.length() < 1) {
                                // �ǿձ�־
                                nullFlag = false;
                                flag = true;
                                // Modified By ZhengZhen at 2015年1月20日 上午10:01:33
								// Begin .... 
								strMessage = strMessage + "第 " + i + " 行：字段【" + excelTitleName + "】不能为空";
								throw new NonConformanceException(strMessage);
								// End ......
                            }
                        }
                        // ���ȡֵ����
                        if (nullFlag) {
                            // �����ж�
                            int intLength = 0;
                            if (StringUtils.isNotBlank(propertyBean.getMaxLength()) && (NumberUtils.isNumber(propertyBean.getMaxLength()))) {
                                intLength = Integer.parseInt(propertyBean.getMaxLength());
                            }
                            // �����������󳤶ȣ����ұ߽�ȡָ�����ַ��
                            if (intLength > 0 && value.length() > intLength) {
                                value = value.substring(0, intLength);
                            }
                            // �ж�������� ֧�� String ,Integer,Date
                            if (propertyBean.getDataType().indexOf("Date") > -1) {
                            	
                                String[] strTemp = propertyBean.getDataType().split(" ");
                                String pattern = "";

                                if (strTemp.length > 1) {
                                    pattern = strTemp[1];
                                }
                                Cell strCell = sheet.getCell(j, i);
                                if(value.length() > 0){
                                	if (strCell.getType() == CellType.DATE) {
                                		DateCell dateCell = (DateCell) strCell;
                                		Date date = dateCell.getDate();
                                		value = DateUtils.getFormatDate(date, pattern);
                                		//System.out.println("DateFormat = " + dateCell.getDateFormat());
                                		//System.out.println("date = " + DateUtils.getFormatDate(date, ""));
                                	} else {
                                		value = null;
                                	}
                                }
                                if (value == null) {
                                    flag = true;
                                    columnFlag = true;
                                    strMessage = strMessage + "第 " + i + " 行： 字段【" + excelTitleName + "】日期格式不对";
                                    throw new NonConformanceException(strMessage);
                                }
                            }
                            if (propertyBean.getDataType().equals("Integer") && value.length() > 0) {
                            	if (!NumberUtils.isNumber(value)) {
                            		flag = true;
                            		columnFlag = true;
                            		strMessage = strMessage + "第 " + i + " 行： 字段【" + excelTitleName + "】应为整数";
                            		throw new NonConformanceException(strMessage);
                            	}
                            }
                        }

                        // //�����ж�,�Ƿ���Ҫ Text/Value ֵת��.
                        if (propertyBean.getCodeTableName().length() > 1 && value.length() > 0) {
                        	// Added By ZhengZhen at 2015年1月20日 上午9:30:51
							// Begin .... 
							List<String> codeTableList = (List<String>) codeTableMap.get(propertyBean.getCodeTableName());
							boolean codeNameFalg = false;
							if (null == codeTableList) {
								IResAssetExcelService resAssetExcelService = (IResAssetExcelService) BeanLoader
										.getBean("resAssetExcelService");
								codeTableList = resAssetExcelService
										.initCodeTableList(
												propertyBean.getCodeTableName(),
												propertyBean.getName());
								codeTableMap.put(
										propertyBean.getCodeTableName(),
										codeTableList);
							}
							for (int k = 0; k < codeTableList.size(); k++) {
								if (codeTableList.get(k).equals(value)) {
									codeNameFalg = true;
									break;
								}
							}
							if (!codeNameFalg) {
								strMessage = strMessage + "第 " + i + " 行：【"
										+ excelTitleName + "】“" + value
										+ "”在数据库中不存在";
								throw new NotInDataBaseException(strMessage);
							}
							// ...... End
                        }
                        // ����Ĭ��ֵ value ����ȡֵ�����ж��Ƿ������Ի�ֵ
                        if (value == null || value.length() < 1) {
                            value = propertyBean.getDefaultValue();
                        }
                        
                        // Added By ZhengZhen at 2015年1月20日 下午5:32:14
						// Begin .... 
						//比较属性的map
						compareMap.put(propertyBean.getExcelTitleName(), value);
						if (propertyBean.getGreaterThan().length() > 0) {
							beCompareName = propertyBean.getGreaterThan();
							compareToName = propertyBean.getExcelTitleName();
							if (propertyBean.getDataType().indexOf("Date") > -1) {
								compareMap.put("compareType", "Date");
							}
							if (propertyBean.getDataType().equals("Integer")) {
								compareMap.put("compareType", "Integer");
							}
						}
						// ...... End
						if (!columnFlag) {
                            // ����֧��Map
                            // bw.setPropertyValue(propertyBean.getName(),
                            // value);
                            this.setPropertyValue(obj, propertyBean.getName(), value);
                            // //System.out.println(propertyBean.getName()+ " = "+
                            // bw.getPropertyValue(propertyBean.getName()));
                        }
                    }

                }
                
                // Added By ZhengZhen at 2015年1月20日 下午5:31:55
				// Begin .... 
				//比较属性的处理
				String type = compareMap.get("compareType");
				String compareToDate = compareMap.get(compareToName);
				String beCompareDate = compareMap.get(beCompareName);
				if ("Date".equals(type)) {
					if ((DateUtil.string2Date3(compareToDate)).before(DateUtil
							.string2Date3(beCompareDate))) {
						throw new NonConformanceException("第 " + i + " 行：【"
								+ compareToName + "】应大于【" + beCompareName + "】");
					}
				}else if ("Integer".equals(type)) {
					if (Integer.parseInt(compareToDate) < Integer.parseInt(beCompareDate)) {
						throw new NonConformanceException("第 " + i + " 行：【"
								+ compareToName + "】应大于【" + beCompareName + "】");
					}
				}
				// ...... End
				/*
                 * ���ô����־����������Ϣ
                 */
                if (flag) {
                    String propertyFlag = (String) excelConfig.getFlagMap().get("name");
                    String propertyMessage = (String) excelConfig.getMessageMap().get("name");
                    // ����֧��Map
                    // bw.setPropertyValue(propertyFlag, "1");
                    if (StringUtils.isNotBlank(propertyFlag)) {
                        this.setPropertyValue(obj, propertyFlag, "1");
                    }

                    // ����֧��Map
                    // bw.setPropertyValue(propertyMessage, strMessage);
                    if (StringUtils.isNotBlank(propertyMessage)) {
                        this.setPropertyValue(obj, propertyMessage, strMessage);
                    }

                } else {
                    String propertyFlag = (String) excelConfig.getFlagMap().get("name");
                    String propertyMessage = (String) excelConfig.getMessageMap().get("name");
                    // ����֧��Map
                    // bw.setPropertyValue(propertyFlag, "0");

                    if (StringUtils.isNotBlank(propertyFlag)) {
                        this.setPropertyValue(obj, propertyFlag, "0");
                    }

                    // ֧��Mapʱ������Map��ÿ�����Զ����ڡ�
                    if (StringUtils.isNotBlank(propertyMessage)) {
                        this.setPropertyValue(obj, propertyMessage, "");
                    }

                }
                /*
                 * ���������ļ���ָ����Ҫ����Ϊ�̶�ֵ�����ԣ����������еĹ̶�ֵ����ݴ����Map��ȡ��������ȡ��Ĭ��ֵ
                 */
                for (Iterator it = this.fixityList.iterator(); it.hasNext();) {
                    RuturnPropertyParam propertyBean = (RuturnPropertyParam) it.next();
                    Object value = this.valueMap.get(propertyBean.getName());
                    if (value == null || value.toString().length() < 1) {
                        value = propertyBean.getDefaultValue();
                    }
                    // ����֧��Map
                    // bw.setPropertyValue(propertyBean.getName(), value);
                    this.setPropertyValue(obj, propertyBean.getName(), value);
                }
                this.modelList.add(obj);
                if (flag) {
                    this.errorList.add(obj);
                } else {
                    this.successModelList.add(obj);
                }

            }
            book.close();
        } catch (BiffException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


	private void setPropertyValue(Object obj, String property, Object value) {
        if (obj instanceof Map) {
            ((Map) obj).put(property, value);
        } else {
            BeanWrapper bw = new BeanWrapperImpl(obj);
            bw.setPropertyValue(property, value);
        }
    }

 
    private Object getModelClass(String className) {
        Object obj = null;
        try {
            obj = Class.forName(className).newInstance();
            //System.out.println("init Class = " + className);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return obj;
    }

    /*
     * ��֤Excel��ͷ��ʽ�Ƿ���ȷ (non-Javadoc)
     * 
     * @see com.javayjm.excel.file.ExcelToModel#validateExcelFormat()
     */
    public boolean validateExcelFormat() {
        // TODO Auto-generated method stub
        boolean boolResult = true;
        List titleList = this.getExcelTitle();
        
        /*
         * ��excel�������?�ѳ�����ͬ���мӱ�� ���������������� ���� ��������_1
         */
        List tempList = new ArrayList();
        for(int i=0;i<titleList.size();i++){
        	String temp = ValidateColumn.columnValidate(tempList,titleList.get(i).toString());
        	tempList.add(temp);
        }
        
        this.messageList = new ArrayList();
        Map pmap = this.excelConfig.getPropertyMap();
        for (Iterator it = pmap.keySet().iterator(); it.hasNext();) {
            String title = (String) it.next();
            // //System.out.println("tilte = " + title);
            if (!tempList.contains(title)) {
                boolResult = false;
                this.messageList.add("Excel�в����� " + title + " �У�");
                //System.out.println("Excel�в����� " + title + " ��!");
            }
        }
        /*
         * ������֤ͨ��ģ��Ѿ��������excel����
         */
        setExcelTitleList(tempList);
        
        return boolResult;
    }

    private List getExcelTitle() {
        this.setFixity();
        List titleList = new ArrayList();
        try {
            Workbook book;

            book = Workbook.getWorkbook(this.excelFile);
            //Sheet sheet = book.getSheet(0);
//          edit 2008-1-24            
            Sheet sheet;
            if(StringUtils.isNotBlank(this.getStrSheet())){
                sheet = book.getSheet(this.getStrSheet());
            }else{
                sheet = book.getSheet(this.getIntSheet());
            }    
            // ��֤��ͷ��ʼ������������ͷ��Ӧ����Ч����

            for (int i = 0; i < sheet.getRows(); i++) {
                // ��֤һ����ֵ������
                int intcount = this.getColumnByValue(sheet.getRow(i));
                // ������ - �̶�ֵ���� = �������Ƶ�����
                int intNotNullColumn = this.excelConfig.getPropertyMap().size() - fixityList.size();
                if (intNotNullColumn <= intcount) {
                    // boolean bool = this.validateColumnTitle(sheet.getRow(i),
                    // this.excelConfig.getPropertyMap());
                    // //��� bool = true ��һ������ͷ,��������ͷ����������Ч��
                    // if(bool){
                    // startTitleRow = i;
                    // this.setValidateCellColumn(sheet.getRow(i),this.excelConfig.getPropertyMap());
                    // break;
                    // }
                    // ��֤ʱ��ֻҪ���ֵĵ�һ�е���ֵ������������ļ������Ƶ��������Ϊ����ͷ
                    startTitleRow = i;
                    break;
                }
            }

            for (int j = 0; j < sheet.getColumns(); j++) {
                String title = sheet.getCell(j, startTitleRow).getContents().trim();
                titleList.add(title);
            }
            book.close();
        } catch (BiffException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return titleList;
    }

    public Map getConfigTitle() {
        // TODO Auto-generated method stub
        Map titleMap = new HashMap();
        Map pmap = this.excelConfig.getPropertyMap();
        for (Iterator it = pmap.keySet().iterator(); it.hasNext();) {
            String configTitle = (String) it.next();
            RuturnPropertyParam propertyBean = (RuturnPropertyParam) excelConfig.getPropertyMap().get(configTitle);
            titleMap.put(propertyBean.getName(), configTitle);
        }
        return titleMap;
    }

    public List getModelList() throws NonConformanceException, NotInDataBaseException {
        return this.getExcelToList(0);
    }

    public List getErrorModelList() throws NonConformanceException, NotInDataBaseException {
        // TODO Auto-generated method stub
        return this.getExcelToList(2);
    }

    public List getSuccessModelList() throws NonConformanceException, NotInDataBaseException {
        // TODO Auto-generated method stub
        return this.getExcelToList(1);
    }

    private List getExcelToList(int intFlag) throws NonConformanceException, NotInDataBaseException {
        if (!isRead) {
            this.isRead = true;
            getExcelToModelList();
        }
        if (intFlag == 0) {
            return this.modelList;
        } else if (intFlag == 1) {
            return this.successModelList;
        } else if (intFlag == 2) {
            return this.errorList;
        } else {
            return new ArrayList();
        }
    }

    private void getExcelToModelList() throws com.fable.insightview.platform.common.exception.NonConformanceException, NotInDataBaseException {
        if (isUseColumn) {
            getExcelToModelListByColumn();
        } else {
            getExcelToModelListByTitle();
        }
    }

    private void getExcelToModelListByColumn() {
        this.setFixity();
        // List modelList = new ArrayList();
        try {
            Workbook book;

            book = Workbook.getWorkbook(this.excelFile);
            //Sheet sheet = book.getSheet(0);
//          edit 2008-1-24
            Sheet sheet;
            if(StringUtils.isNotBlank(this.getStrSheet())){
                sheet = book.getSheet(this.getStrSheet());
            }else{
                sheet = book.getSheet(this.getIntSheet());
            }    
            
            //System.out.println("JXL version : " + book.getVersion());
            // editBook = Workbook.createWorkbook(file, book);

            // //System.out.println("rows = " + sheet.getRows() + " cols ="+
            // sheet.getColumns());
            for (int i = 1; i < sheet.getRows(); i++) {
                Object obj = this.getModelClass(excelConfig.getClassName());

                // ����֧��Map,д������ֵ��һ��˽�з��������?
                // BeanWrapper bw = new BeanWrapperImpl(obj);

                // �����־
                boolean flag = false;
                // ������Ϣ
                String strMessage = "";

                // ��excelÿһ�е�ֵ����ȡֵ.
                for (int j = 0; j < sheet.getColumns(); j++) {

                    // //System.out.println("i = " + i + " j =" + j);
                    // ȡ��Excel��ͷ
                    String excelTitleName = sheet.getCell(j, 0).getContents().trim();
                    // ȡ��Excelֵ
                    String value = sheet.getCell(j, i).getContents().trim();
                    //          
                    // ȡ����������
                    // RuturnPropertyParam propertyBean = (RuturnPropertyParam)
                    // excelConfig.getPropertyMap().get(excelTitleName);

                    /*
                     * �������кŶ�ȡExcel�ļ���ֻ�޸�������ļ��д��룬ע������һ�д��롣
                     */
                    String keycolumn = String.valueOf(j + 1);
                    RuturnPropertyParam propertyBean = (RuturnPropertyParam) excelConfig.getColumnMap().get(keycolumn);
                    //System.out.println("column = " + keycolumn);
                    // //System.out.println("i = " + i + " j =" + j + " value ="+
                    // value + " title = " + excelTitleName);

                    // �д����־
                    boolean columnFlag = false;
                    if (propertyBean != null) {
                        // //System.out.println("propertyName = " +
                        // propertyBean.getName());
                        // �ж��Ƿ�ǿգ��ǿձ�־ ,ȡֵΪ��ʱ����Ϊfalse
                        boolean nullFlag = true;

                        if (propertyBean.getIsNull().equals("N")) {
                            if (value.length() < 1) {
                                // �ǿձ�־
                                nullFlag = false;
                                flag = true;
                                strMessage = strMessage + ";�У�" + excelTitleName + "-Ϊ�գ�";
                            }
                        }
                        // ���ȡֵ����
                        if (nullFlag) {
                            // �����ж�
                            int intLength = 0;
                            if (StringUtils.isNotBlank(propertyBean.getMaxLength()) && (NumberUtils.isNumber(propertyBean.getMaxLength()))) {
                                intLength = Integer.parseInt(propertyBean.getMaxLength());
                            }
                            // �����������󳤶ȣ����ұ߽�ȡָ�����ַ��
                            if (intLength > 0 && value.length() > intLength) {
                                value = value.substring(0, intLength);
                            }
                            // �ж�������� ֧�� String ,Integer,Date
                            if (propertyBean.getDataType().indexOf("Date") > -1) {
                                String[] strTemp = propertyBean.getDataType().split(" ");
                                String pattern = "";

                                if (strTemp.length > 1) {
                                    pattern = strTemp[1];
                                }
                                // boolean isDate = this.isValidDate(value,
                                // pattern);
                                Cell strCell = sheet.getCell(j, i);
                                if (strCell.getType() == CellType.DATE) {
                                    DateCell dateCell = (DateCell) strCell;
                                    Date date = dateCell.getDate();
                                    value = DateUtils.getFormatDate(date, pattern);
                                    //System.out.println("DateFormat = " + dateCell.getDateFormat());
                                    //System.out.println("date = " + DateUtils.getFormatDate(date, ""));
                                } else {
                                    value = null;
                                }
                                if (value == null) {
                                    flag = true;
                                    columnFlag = true;
                                    strMessage = strMessage + "�У�" + excelTitleName + "-���ڸ�ʽ����ȷ��";
                                    //System.out.println("�У�" + excelTitleName + "-���ڸ�ʽ����ȷ��");
                                }
                                // boolean isDate = DateUtils.isValidDate(value,
                                // pattern);
                                // if (!isDate) {
                                // flag = true;
                                // columnFlag = true;
                                // strMessage = strMessage + "�У�"
                                // + excelTitleName + "-���ڸ�ʽ����ȷ��";
                                // //System.out.println("�У�" + excelTitleName
                                // + "-���ڸ�ʽ����ȷ��");
                                // }
                                // value = DateUtils.getFormatDate(value,
                                // pattern);
                            }
                            if (propertyBean.getDataType().equals("Integer")) {
                                if (!NumberUtils.isNumber(value)) {
                                    flag = true;
                                    columnFlag = true;
                                    strMessage = strMessage + "�У�" + excelTitleName + "-�������֣�";
                                    //System.out.println("�У�" + excelTitleName + "-�������֣�");
                                }
                            }
                        }

                        // //�����ж�,�Ƿ���Ҫ Text/Value ֵת��.
                        if (propertyBean.getCodeTableName().length() > 1) {

                            String valueKey = propertyBean.getCodeTableName().trim() + value;
                            // //System.out.println("valueKey = " + valueKey);
                            Object obj1 = this.valueMap.get(valueKey);
                            if (obj1 == null) {
                                value = "";
                            } else {
                                value = obj1.toString();
                            }
                        }
                        // ����Ĭ��ֵ value ����ȡֵ�����ж��Ƿ������Ի�ֵ
                        if (value == null || value.length() < 1) {
                            value = propertyBean.getDefaultValue();
                        }
                        if (!columnFlag) {
                            // ����֧��Map
                            // bw.setPropertyValue(propertyBean.getName(),
                            // value);
                            this.setPropertyValue(obj, propertyBean.getName(), value);
                            // //System.out.println(propertyBean.getName()+ " = "+
                            // bw.getPropertyValue(propertyBean.getName()));
                        }
                    }

                }
                /*
                 * ���ô����־����������Ϣ
                 */
                if (flag) {
                    String propertyFlag = (String) excelConfig.getFlagMap().get("name");
                    String propertyMessage = (String) excelConfig.getMessageMap().get("name");
                    // ����֧��Map
                    // bw.setPropertyValue(propertyFlag, "1");
                    this.setPropertyValue(obj, propertyFlag, "1");

                    //System.out.println(propertyMessage + " = " + strMessage);
                    // ����֧��Map
                    // bw.setPropertyValue(propertyMessage, strMessage);
                    this.setPropertyValue(obj, propertyMessage, strMessage);
                } else {
                    String propertyFlag = (String) excelConfig.getFlagMap().get("name");
                    String propertyMessage = (String) excelConfig.getMessageMap().get("name");
                    // ����֧��Map
                    // bw.setPropertyValue(propertyFlag, "0");
                    this.setPropertyValue(obj, propertyFlag, "0");
                    // ֧��Mapʱ������Map��ÿ�����Զ����ڡ�
                    this.setPropertyValue(obj, propertyMessage, "");

                }
                /*
                 * ���������ļ���ָ����Ҫ����Ϊ�̶�ֵ�����ԣ����������еĹ̶�ֵ����ݴ����Map��ȡ��������ȡ��Ĭ��ֵ
                 */
                for (Iterator it = this.fixityList.iterator(); it.hasNext();) {
                    RuturnPropertyParam propertyBean = (RuturnPropertyParam) it.next();
                    Object value = this.valueMap.get(propertyBean.getName());
                    if (value == null || value.toString().length() < 1) {
                        value = propertyBean.getDefaultValue();
                    }
                    // ����֧��Map
                    // bw.setPropertyValue(propertyBean.getName(), value);
                    this.setPropertyValue(obj, propertyBean.getName(), value);
                }
                this.modelList.add(obj);
                if (flag) {
                    this.errorList.add(obj);
                } else {
                    this.successModelList.add(obj);
                }

            }
            book.close();
        } catch (BiffException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isUseColumn() {
        return isUseColumn;
    }

    public void setUseColumn(boolean isUseColumn) {
        this.isUseColumn = isUseColumn;
    }

    // ȡ�õ�ǰ����ֵ������
    private int getColumnByValue(Cell[] rowCell) {
        int column = rowCell.length;
        int intResult = column;
        for (int i = 0; i < column; i++) {
            String str = rowCell[i].getContents();
            if (StringUtils.isBlank(str)) {
                intResult--;
            }
        }
        return intResult;
    }

    // ��֤��ǰ��ֵ���д��ڵ��������ļ�����Ҫ���б��ֵ�Ƿ�����ͷ��
    private boolean validateColumnTitle(Cell[] rowCell, Map columnMap) {
        int column = rowCell.length;
        List list = this.getColumnTitle(rowCell);
        boolean bool = true;
        for (Iterator it = columnMap.keySet().iterator(); it.hasNext();) {
            String str = (String) it.next();
            if (!list.contains(str)) {
                bool = false;
            }
        }
        return bool;
    }

    private List getColumnTitle(Cell[] rowCell) {
        List list = new ArrayList();
        int column = rowCell.length;
        for (int i = 0; i < column; i++) {
            String str = rowCell[i].getContents().toString().trim();
            if (StringUtils.isNotBlank(str)) {
                list.add(str);
            }
        }
        return list;
    }

    // ���������ļ��У�ָ������ͷ��ʵ��Excel�ļ��г��ֵ�����
    private void setValidateCellColumn(Cell[] rowCell, Map columnMap) {
        this.cellColumn = new ArrayList();
        int column = rowCell.length;
        for (int i = 0; i < column; i++) {
            String str = rowCell[i].getContents().toString();
            if (StringUtils.isNotBlank(str)) {
                if (columnMap.get(str) != null) {
                    this.cellColumn.add(String.valueOf(i));
                }
            }
        }
    }

    public int getStartTitleRow() {
        // TODO Auto-generated method stub
        return startTitleRow;
    }

    public int getIntSheet() {
        return intSheet;
    }

    public void setSheet(int intSheet) {
        this.strSheet = null;
        this.intSheet = intSheet;
    }

    public String getStrSheet() {        
        return strSheet;
    }

    public void setSheet(String strSheet) {
        this.intSheet = 0;
        this.strSheet = strSheet;
    }

	private void setExcelTitleList(List excelTitleList) {
		this.excelTitleList = excelTitleList;
	}
    
    
    
}
