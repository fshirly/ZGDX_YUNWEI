package com.fable.insightview.platform.common.excel.config;

public class ConfigConstant {
	
	/*
	 * model �Ĳ��ұ��  id
	 */
	public static final String MODEL_ID = "id";
	
	/*
	 * ��Excel �� Model ת����Ŀ�� javabean class
	 */
	public static final String MODEL_CLASS="class";
	
	/*
	 * excel ���ж�Ӧ��javabean�е� javabean�������
	 */
	public static final String PROPERTY_NAME = "name";
	
	/*
	 * excel�еı�����е����� (  ȡֵ��ʱ�������javabean���ԺͶ�Ӧ������ȡֵ)
	 */
	public static final String PROPERTY_CLOUMN="column";
	
	/*
	 * excel ���ж�Ӧ��javabean�е� excel�б������  (ȡֵ��ʱ��,������ javabean���Ժ�Excel�еı����Ӧȡֵ)
	 */
	public static final String PROPERTY_EXCEL_TITLE_NAME = "excelTitleName";
	
	/*
	 * excel ���Ƿ����Ϊ�ա���ֵ��Y������Ϊ�ա�N��������Ϊ��
	 */
	public static final String PROPERTY_ISNULL="isNull";
	
	/*
	 * Model д�뵽Excelʱ��ÿ�еĿ�ȡ�
	 */
	public static final String PROPERTY_COLUMN_WIDTH="columnWidth";
	/*
	 * excel�е������Ҫ��ת�����������
	 */
	public static final String PROPERTY_DATA_TYPE = "dataType";
	
	/*
	 * excel�е������󳤶�
	 */
	public static final String PROPERTY_MAX_LENGTH="maxLength";
	
	/*
	 * ��excel��û�������,��Ҫϵͳ��javabean�����е�ĳһ��ֵ����һ����̬�����ֵ(ָ����JavaBean���������ֵ������ͳһ����Ĺ̶�ֵ).����Щ����,��������Ĭ��ֵ
	 * 
	 * ���ֵ����Ϊ fixity = "yes" ,Ĭ��Ϊ no.
	 */
	public static final String PROPERTY_FIXITY="fixity";
	
	/*
	 * �ڵ���excelʱ,��Щֵ�ڴ���ϵͳʱ,ʹ�õ�ֵ��Ҫת��
	 * ��:һ���������޵������б�����{����(Y),����(C),����(D)}
	 * ϵͳ�д����ֻ���� Y,C,D֮���ֵ,����Excel��ֵȴ������,����,���������ֵ,��Ҫת��.
	 * ת����ʽ:ȡ��Excel�еľ���ֵ,���ϱ�������ǰ�,�Ӵ����Map��ȡֵ.�� ȡֵ����  C = Map.get("bgqx����"); 
	 * 
	 */
	public static final String PROPERTY_CODE_TABLE_NAME="codeTableName";
	/*
	 * ���ֵΪ��,���õ�Ĭ��ֵ
	 */
	public static final String PROPERTY_DEFAULT="default";
	
	/*
	 * 比较属性
	 */
	public static final String PROPERTY_GREATER_THAN="greaterThan";
}
