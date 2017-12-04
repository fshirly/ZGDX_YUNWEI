//package com.fable.insightview.platform.common.excel;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class StudentUtils {
//	 
//	public static List getStudentFormList(){
//		Map codeMap = new HashMap();
//		codeMap.put("sex��", "M");
//		codeMap.put("sexŮ", "F");
//		ExcelManager test = new ExcelManager(
//				"D:\\text\\student.xls",
//				"importStudentInfo", codeMap);
//		
//		List modelList = test.getModelList();
//		return modelList;
//	}
//	
//	public static List getStudentMapList(){
//		Map codeMap = new HashMap();
//		codeMap.put("sex��", "M");
//		codeMap.put("sexŮ", "F");
//		ExcelManager test = new ExcelManager(
//				"D:\\text\\student.xls",
//				"importStudentInfo_map", codeMap);
//		test.getEtm().setSheet(1);
//		List modelList = test.getModelList();
//		return modelList;
//	}
//	public static List getScoreMapList(){
//		Map codeMap = new HashMap();
//		codeMap.put("sex��", "M");
//		codeMap.put("sexŮ", "F");
//		ExcelManager test = new ExcelManager(
//				"D:\\text\\student.xls",
//				"importStudentInfo_score", codeMap);
//		test.getEtm().setSheet(4);
//		List modelList = test.getModelList();
//		return modelList;
//	}
//}
