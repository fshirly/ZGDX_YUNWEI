package com.fable.insightview.platform.common.excel;

/**
 * @author wul
 * 
 */
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.common.excel.config.ExcelConfigFactory;
import com.fable.insightview.platform.common.excel.config.ExcelConfigManager;
import com.fable.insightview.platform.common.excel.file.ExcelToModel;
import com.fable.insightview.platform.common.excel.file.ModelToExcelMessage;
import com.fable.insightview.platform.common.excel.file.impl.ExcelToModelImpl;
import com.fable.insightview.platform.common.excel.file.impl.ModelToExcelMessageImpl;
import com.fable.insightview.platform.common.exception.NotInDataBaseException;
import com.fable.insightview.platform.common.exception.NonConformanceException;

public class ExcelManager {
	private File excelFile = null;

	private String modelName = "";

	private Map valueMap = null;

	private ExcelToModel etm = null;

	private ModelToExcelMessage mtem = null;

	private ExcelConfigManager configManager = null;

	public ExcelManager(String fileName, String modelName, Map valueMap) {
		this(new File(fileName), modelName, valueMap);
	}

	public ExcelManager(String fileName, String modelName) {
		this(new File(fileName), modelName, new HashMap());
	}

	public ExcelManager(File fileName, String modelName, Map valueMap) {
		this.excelFile = fileName;
		this.modelName = modelName;
		this.valueMap = valueMap;
		configManager = ExcelConfigFactory.createExcelConfigManger();
	}

	/**
	 * ȡ�ô�Excelת����Model�ġ�List.
	 * @throws NonConformanceException 
	 * @throws NotInDataBaseException 
	 */
	public List getModelList() throws NonConformanceException, NotInDataBaseException {
		if (isValidateExcelFormat()) {
			// modelList = etm.getModelList();
			return etm.getModelList();
		}
		return null;
	}

	/**
	 * ��֤Xml�ļ����Ƶ�Excel�����ڡ�Excel�ļ����Ƿ���
	 */
	public boolean isValidateExcelFormat() {
		if (etm == null) {
			etm = new ExcelToModelImpl(this.excelFile, configManager.getModel(
					modelName, ""), this.valueMap);
		}
		return etm.validateExcelFormat();
	}

	/**
	 * ȡ��Xml�ļ������ƵĶ�ӦExcel�ļ��еı���
	 */
	public Map getConfigTitle() {
		if (etm == null) {
			etm = new ExcelToModelImpl(this.excelFile, configManager.getModel(
					modelName, ""), this.valueMap);
		}
		return etm.getConfigTitle();
	}

	/**
	 * ȡ�ô�Excelת����Model�Ĳ�����֤ͨ��� List.
	 * @throws NonConformanceException 
	 * @throws NotInDataBaseException 
	 */
	public List getSucessModelList() throws NonConformanceException, NotInDataBaseException {
		if (isValidateExcelFormat()) {
			return etm.getSuccessModelList();
		}
		return null;
	}

	/**
	 * ȡ�ô�Excelת����Model�Ĳ�����֤δͨ��� List.
	 * @throws NonConformanceException 
	 * @throws NotInDataBaseException 
	 */
	public List getErrorModelList() throws NonConformanceException, NotInDataBaseException {
		if (isValidateExcelFormat()) {
			return etm.getErrorModelList();
		}
		return null;
	}

	/**
     * 
     */
	private File getFile(File excelFile, List modelList, String strFlag) {
		if (mtem == null) {
			if (excelFile == null)
				mtem = new ModelToExcelMessageImpl(this.excelFile,
						configManager.getModel(modelName, ""), modelList);
			else
				mtem = new ModelToExcelMessageImpl(excelFile,
						configManager.getModel(modelName, ""), modelList);
		}
		mtem.setStartTitleRow(etm.getStartTitleRow());
		mtem.setIntSheet(etm.getIntSheet());
		if ("-1".equals(strFlag)) {
			return mtem.getFile();
		} else if ("0".equals(strFlag)) {
			return mtem.getSuccessFile();
		} else {
			return mtem.getErrorFile();
		}
	}

	/**
	 * ��ת��֮���modelList����֤δͨ�����Ϣ����д��Excel�ļ��У�������ȫ������ʱ�ļ��ļ�¼
	 * 
	 * @param excelFile
	 *            File
	 * @param modelList
	 *            List
	 * @return File
	 */
	public File getExcelFile(File excelFile, List modelList) {
		return this.getFile(excelFile, modelList, "-1");
	}

	/**
	 * ��ת��֮���modelList����֤δͨ�����Ϣ����д��Excel�ļ��У�������ȫ������ʱ�ļ�����֤ͨ��ļ�¼
	 * 
	 * @param excelFile
	 *            File
	 * @param modelList
	 *            List
	 * @return File
	 */
	public File getSucessExcelFile(File excelFile, List modelList) {
		return this.getFile(excelFile, modelList, "0");
	}

	/**
	 * ��ת��֮���modelList����֤δͨ�����Ϣ����д��Excel�ļ��У�������ȫ������ʱ�ļ�����֤δͨ��ļ�¼
	 * 
	 * @param excelFile
	 *            File
	 * @param modelList
	 *            List
	 * @return File
	 */
	public File getErrorExcelFile(File excelFile, List modelList) {
		return this.getFile(excelFile, modelList, "1");
	}

	/**
	 * ��ת��֮���modelList����֤δͨ�����Ϣ����д��Excel�ļ��У�������ȫ������ʱ�ļ��ļ�¼
	 * 
	 * @param modelList
	 *            List
	 * @return File
	 */
	public File getExcelFile(List modelList) {
		return this.getFile(null, modelList, "-1");
	}

	/**
	 * ��ת��֮���modelList����֤δͨ�����Ϣ����д��Excel�ļ��У�������ȫ������ʱ�ļ�����֤ͨ��ļ�¼
	 * 
	 * @param modelList
	 *            List
	 * @return File
	 */
	public File getSucessExcelFile(List modelList) {
		return this.getFile(null, modelList, "0");
	}

	/**
	 * ��ת��֮���modelList����֤δͨ�����Ϣ����д��Excel�ļ��У�������ȫ������ʱ�ļ�����֤δͨ��ļ�¼
	 * 
	 * @param modelList
	 *            List
	 * @return File
	 */
	public File getErrorExcelFile(List modelList) {
		return this.getFile(null, modelList, "1");
	}

	/**
	 * ȡ�ò���Excel�ļ���ʵ��
	 */
	public ExcelToModel getEtm() {
		if (etm == null) {
			etm = new ExcelToModelImpl(this.excelFile, configManager.getModel(
					modelName, ""), this.valueMap);
		}
		return etm;
	}

}
