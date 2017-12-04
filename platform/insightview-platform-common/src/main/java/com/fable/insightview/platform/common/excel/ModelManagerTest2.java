//package com.fable.insightview.platform.common.excel;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import junit.framework.TestCase;
//
//public class ModelManagerTest2 extends TestCase {
//	// form ����
//	List formModelList = new ArrayList();
//
//	// map ����
//	List mapModelList = new ArrayList();
//	
//	// map���ɼ�
//	List mapScoreList = new ArrayList();
//	
//
//	protected void setUp() throws Exception {
//		super.setUp();
//		formModelList = StudentUtils.getStudentFormList();
//		mapModelList = StudentUtils.getStudentMapList();
//		mapScoreList = StudentUtils.getScoreMapList();
//	}
//
//	// �������ļ���������ı���ͷ�� ����form list ���
//	public void testFormConfig() {
//		String fileName = "D:\\text\\student\\student_FormConfig.xls";
//		ModelManager mm = new ModelManager(fileName, "importStudentInfo",
//				formModelList);
//		mm.getMte().setHeader("�߶�һ��(2008)��ѧ����");
//		mm.getMte().setRowHeight(500);
//		mm.getExcelFile();
//	}
//
//	// �ı����в��Ż���ȫ���б���������֣�����form list ���
//	public void testFormConfig_title() {
//		Map map = new HashMap();
//		map.put("className", "�༶���_�ı�");
//		map.put("classCode", "�༶���_�ı�");
//		map.put("studentName", "����_�ı�");
//		map.put("sex", "�Ա�_�ı�");
//		map.put("sort", "����_�ı�");
//		String fileName = "D:\\text\\student\\student_FormConfig_title.xls";
//		ModelManager mm = new ModelManager(fileName, "importStudentInfo",
//				formModelList);
//		mm.getMte().setHeader("�߶�һ��(2008)��ѧ����");
//		mm.getMte().setRowHeight(500);
//		mm.getMte().setDynamicTitleMap(map);
//		mm.getExcelFile();
//	}
//
//	// �������ļ���������ı���ͷ�� ����map list ���
//	public void testMapConfig() {
//		String fileName = "D:\\text\\student\\student_MapConfig.xls";
//		ModelManager mm = new ModelManager(fileName, "importStudentInfo",
//				mapModelList);
//		mm.getMte().setHeader("�߶�һ��(2008)��ѧ����");
//		mm.getMte().setRowHeight(500);
//
//		mm.getExcelFile();
//	}
//
//	// �ı��б���������֣�����Map list ���
//	public void testMapConfig_title() {
//		Map map = new HashMap();
//		map.put("className", "�༶���_�ı�");
//		map.put("classCode", "�༶���_�ı�");
//		map.put("studentName", "����_�ı�");
//		map.put("sex", "�Ա�_�ı�");
//		map.put("sort", "����_�ı�");
//
//		String fileName = "D:\\text\\student\\student_MapConfig_title.xls";
//		ModelManager mm = new ModelManager(fileName, "importStudentInfo",
//				mapModelList);
//		mm.getMte().setHeader("�߶�һ��(2008)��ѧ����");
//		mm.getMte().setRowHeight(500);
//
//		mm.getMte().setDynamicTitleMap(map);
//
//		mm.getExcelFile();
//	}
//
//	// ����������������� form list ���
//	public void testFormColumn() {
//		Map map = new HashMap();
//		map.put("className", "�༶���_����");
//		map.put("classCode", "�༶���_����");
//		map.put("studentName", "����_����");
//		map.put("sex", "�Ա�_����");
//		map.put("sort", "����_����");
//
//		String fileName = "D:\\text\\student\\student_FormColumn.xls";
//		ModelManager mm = new ModelManager(fileName, "importStudentInfo",
//				formModelList);
//		mm.getMte().setHeader("�߶�һ��(2008)��ѧ����");
//		mm.getMte().setRowHeight(500);
//		mm.getMte().setDynamicTitle(true);
//		mm.getMte().setDynamicTitleMap(map);
//
//		mm.getExcelFile();
//	}
//
//	// ����������������� map list ���
//	public void testMapColumn() {
//		Map map = new HashMap();
//		map.put("className", "�༶���_����");
//		map.put("classCode", "�༶���_����");
//		map.put("studentName", "����_����");
//		map.put("sex", "�Ա�_����");
//		map.put("sort", "����_����");
//
//		String fileName = "D:\\text\\student\\student_MapColumn.xls";
//		ModelManager mm = new ModelManager(fileName, "importStudentInfo",
//				mapModelList);
//		mm.getMte().setHeader("�߶�һ��(2008)��ѧ����");
//		mm.getMte().setRowHeight(500);
//
//		mm.getMte().setDynamicTitle(true);
//		mm.getMte().setDynamicTitleMap(map);
//
//		mm.getExcelFile();
//	}
//
//	// ����ģ�壬�������ļ�������form list ���
//	public void testFormTemplate() {
//		Map paramMap = new HashMap();
//		paramMap.put("Year", "2007");
//		paramMap.put("Name", "����");
//		paramMap.put("Date", "2008-1-23");
//
//		String fileName = "D:\\text\\student\\student_FormTemplate.xls";
//		ModelManager mm = new ModelManager(fileName, "importStudentInfo",
//				formModelList);
//		mm.getMte().setHeader("�߶�һ��(2008)��ѧ����");
//		mm.getMte().setRowHeight(400);
//
//		mm.getMte().setTemplateParam(
//				"D:\\text\\studentModelTemplate.xls", 3,
//				paramMap);
//		mm.getExcelFile();
//	}
//
//	// ����ģ�壬�������ļ�������map list ���
//	public void testMapTemplate() {
//		Map paramMap = new HashMap();
//		paramMap.put("Year", "2007");
//		paramMap.put("Name", "����");
//		paramMap.put("Date", "2008-1-23");
//
//		String fileName = "D:\\text\\student\\student_MapTemplate.xls";
//		ModelManager mm = new ModelManager(fileName, "importStudentInfo",
//				mapModelList);
//		mm.getMte().setHeader("�߶�һ��(2008)��ѧ����");
//		mm.getMte().setRowHeight(500);
//
//		mm.getMte().setTemplateParam(
//				"D:\\text\\studentModelTemplate.xls", 3,
//				paramMap);
//		mm.getExcelFile();
//	}
//
//	// �� Sheet ���
//	public void testMultipleSheet() {
//
//		// modeltoexcel_Multiple.xls��Ϊģ���ļ�����ʱ���ļ�������ڣ��������㹻���sheet
//		String fileName = "D:\\text\\student\\student_Multiple.xls";
//		ModelManager mm = null;
//		mm = new ModelManager(fileName, "importStudentInfo", formModelList);
//		mm.getMte().setHeader("�߶�һ��(2008)��ѧ����");
//		mm.getMte().setRowHeight(500);
//		mm.getMte().setSheet(0, "��һ");
//		mm.getExcelFile();	
//		
//		Map map = new HashMap();
//		map.put("className", "�༶���_�ı�");
//		map.put("classCode", "�༶���_�ı�");
//		map.put("studentName", "����_�ı�");
//		map.put("sex", "�Ա�_�ı�");
//		map.put("sort", "����_�ı�");
//		mm.setModelName_List("importStudentInfo", formModelList);
//		mm.getMte().setHeader("�߶�һ��(2008)��ѧ����");
//		mm.getMte().setRowHeight(500);
//		mm.getMte().setDynamicTitleMap(map);
//		mm.getMte().setSheet(1, "�ڶ�");
//		mm.getExcelFile();	
//		
//		Map paramMap = new HashMap();
//		paramMap.put("Year", "2007");
//		paramMap.put("Name", "����");
//		paramMap.put("Date", "2008-1-23");
//		mm.setModelName_List("importStudentInfo", mapModelList);
//		mm.getMte().setHeader("�߶�һ��(2008)��ѧ����");
//		mm.getMte().setRowHeight(500);
//		mm.getMte().setTemplateParam("D:\\text\\student\\student_Multiple.xls", 3,
//				paramMap);
//		mm.getMte().setSheet(2, "����");
//		mm.getExcelFile();
//
//	}
//
//	// ���ô�ÿ�У��ڼ��п�ʼ����
//	public void testRowColumn() {
//		ModelManager mm = new ModelManager(
//				"D:\\text\\student\\student_rowcolumn.xls",
//				"importStudentInfo", mapModelList);
//		mm.getMte().setHeader("�߶�һ��(2008)��ѧ����");
//		mm.getMte().setRowHeight(500);
//
//		Map paramMap = new HashMap();
//		paramMap.put("Year", "2007");
//		paramMap.put("Name", "����");
//		paramMap.put("Date", "2008-1-23");
//		mm.getMte().setTemplateParam(
//				"D:\\text\\studentModelTemplate2.xls", 3,
//				1, paramMap, false);
//		mm.getExcelFile();
//	}
//
//	// ���ÿ�ʼ�У���ʼ�в�������ε���
//	public void testMultipleRowColumn() {
//		ModelManager mm = new ModelManager(
//				"D:\\text\\student\\student_Multiplerowcolumn.xls",
//				"importStudentInfo", formModelList);		
//		mm.getMte().setRowHeight(400);
//
//		Map paramMap = new HashMap();
//		paramMap.put("Year", "2007");
//		paramMap.put("Name", "����");
//		paramMap.put("Date", "2008-1-23");
//		mm.getMte().setTemplateParam(
//				"D:\\text\\studentModelTemplate3.xls", 3,
//				1, paramMap, false);
//		mm.getExcelFile();
//		
//		//�ڶ��β���
//		mm.setModelName_List("importStudentInfo_score", mapScoreList);
//		mm.getMte().setTemplateParam(
//				"D:\\text\\\\student\\student_Multiplerowcolumn.xls", 3,12,
//				paramMap,false);		
//		mm.getExcelFile();
//	}
//	protected void tearDown() throws Exception {
//		super.tearDown();
//	}
//}
