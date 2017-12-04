package com.fable.insightview.platform.common.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import com.fable.insightview.platform.common.entity.ResAssetExcelBean;
import com.fable.insightview.platform.common.exception.NotInDataBaseException;
import com.fable.insightview.platform.common.exception.NonConformanceException;
import com.fable.insightview.platform.common.util.FileUploadUtil;

/**
 * @author wul
 * 
 */

public class ExcelManagerTest extends TestCase {
	private static Map codeMap = new HashMap();

	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * FileUploadUtil获取导入数据源并且转换成LIST
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws NonConformanceException 
	 * @throws NotInDataBaseException 
	 * @throws Exception
	 */
	public List<ResAssetExcelBean> importVal(HttpServletRequest request) throws IOException, NonConformanceException, NotInDataBaseException {
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		File uploadFile = fileUploadUtil.mkFile(request);
		String fileName = uploadFile.getCanonicalPath().replace("\\",
				"\\" + "\\");

		ExcelManager test = new ExcelManager(fileName, "importResAsset",
				codeMap);
		test.getEtm().setSheet(0);
		List modelList = test.getModelList();

		List<ResAssetExcelBean> stuLst = new ArrayList<ResAssetExcelBean>();
		for (int i = 0; i < modelList.size(); i++) {
			Object obj = modelList.get(i);
			ResAssetExcelBean st = (ResAssetExcelBean) obj;
			stuLst.add(st);
		}
		test.getSucessExcelFile(modelList);
		return stuLst;
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
