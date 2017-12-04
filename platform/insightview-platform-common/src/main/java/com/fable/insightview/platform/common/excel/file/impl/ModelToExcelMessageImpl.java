package com.fable.insightview.platform.common.excel.file.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.fable.insightview.platform.common.excel.config.ExcelConfigFactory;
import com.fable.insightview.platform.common.excel.config.RuturnConfig;
import com.fable.insightview.platform.common.excel.file.ModelToExcelMessage;
import com.fable.insightview.platform.common.excel.model.DeptModel;

/**
 * @author wul
 * 
 */
public class ModelToExcelMessageImpl implements ModelToExcelMessage {
	private List modelList;

	private File excelFile;

	private RuturnConfig excelConfig = null;

	private int startTitleRow = 0;

	private int intSheet = 0;

	public ModelToExcelMessageImpl() {

	}

	public ModelToExcelMessageImpl(File excelFile, RuturnConfig excelConfig,
			List modelList) {
		this.excelFile = excelFile;
		this.excelConfig = excelConfig;
		this.modelList = modelList;
	}

	public File getErrorFile() {
		// TODO Auto-generated method stub
		getExcelFile(1);
		return excelFile;
	}

	public File getFile() {
		// TODO Auto-generated method stub
		getExcelFile(-1);
		return excelFile;
	}

	public File getSuccessFile() {
		// TODO Auto-generated method stub
		getExcelFile(0);
		return excelFile;
	}

	private void getExcelFile(int flag) {
		try {
			Workbook book = Workbook.getWorkbook(this.excelFile);
			// Sheet sheet = book.getSheet(0);

			WritableWorkbook wbook = Workbook.createWorkbook(this.excelFile,
					book);

			WritableSheet wsheet = wbook.getSheet(intSheet);

			int intModelSize = modelList.size();
			String propertyName = (String) excelConfig.getMessageMap().get(
					"name");
			String flagName = (String) excelConfig.getFlagMap().get("name");

			int intColumn = wsheet.getColumns();
			// ���ô�����Ϣ�п�
			wsheet.setColumnView(intColumn, 60);

			boolean isMap = false;
			if (modelList.size() > 0) {
				Object tmpObj = modelList.get(0);
				if (tmpObj instanceof Map) {
					isMap = true;
				}
			}

			for (int i = startTitleRow; i < wsheet.getRows(); i++) {
				//System.out.println("Cell[" + i + "][" + intColumn + "]");
				// for (int j = 0; j < sheet.getColumns(); j++) {
				// Label labelC = new Label(j, i, sheet.getCell(j, i)
				// .getContents().trim());
				//
				// wsheet.addCell(labelC);
				// }
				// �����һ��д���������

				if (i > startTitleRow) {

					String strMessage = "";
					if (intModelSize > i - 1 - startTitleRow) {
						if (isMap) {
							strMessage = (String) ((Map) modelList.get(i - 1
									- startTitleRow)).get(propertyName);
						} else {
							BeanWrapper bw = new BeanWrapperImpl(
									modelList.get(i - 1 - startTitleRow));
							strMessage = (String) bw
									.getPropertyValue(propertyName);
						}

					}
					Label labelC = new Label(intColumn, i, strMessage,
							getDefaultNormolFormat());
					wsheet.addCell(labelC);
				} else {
					String excelTitleName = (String) excelConfig
							.getMessageMap().get("excelTitleName");
					Label labelC = new Label(intColumn, i, excelTitleName,
							getDefaultTitleFormat());
					wsheet.addCell(labelC);

				}

			}
			if (flag > -1) {
				// for (int i = startTitleRow + 1; i < wsheet.getRows(); i++) {
				for (int i = wsheet.getRows() - 1; i >= startTitleRow + 1; i--) {
					// ֻҪ��ȷ����
					if (intModelSize >= i - startTitleRow) {
						String strFlag = "";
						if (isMap) {
							strFlag = (String) ((Map) modelList.get(i - 1
									- startTitleRow)).get(flagName);
						} else {
							BeanWrapper bw = new BeanWrapperImpl(
									modelList.get(i - 1 - startTitleRow));
							strFlag = (String) bw.getPropertyValue(flagName);
						}

						// ֻҪ������ݣ�strFlag����0��Ϊ��ȷ���
						//System.out.println("i=" + i + "  flag =" + flag
						//+ " strFlag =" + strFlag);
						if ((flag == 1) && strFlag.equals("0")) {
							wsheet.removeRow(i);
						}
						// ֻҪ��ȷ��ݣ�strFlag����1��Ϊ�������
						if ((flag == 0) && strFlag.equals("1")) {
							wsheet.removeRow(i);
						}
					}
				}
			}
			wbook.write();
			wbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		List list = new ArrayList();
		DeptModel dept = new DeptModel();
		dept.setFlag("0");
		dept.setMessage("00000");
		DeptModel dept1 = new DeptModel();
		dept1.setFlag("1");
		dept1.setMessage("111111");
		list.add(dept);
		list.add(dept1);

		ModelToExcelMessageImpl mte = new ModelToExcelMessageImpl(new File(
				"D:\\text\\test.xls"), ExcelConfigFactory
				.createExcelConfigManger().getModel("deptModel", ""), list);
		mte.getFile();
		// mte.getErrorFile();
		// mte.getSuccessFile();

	}

	public void setStartTitleRow(int startTitleRow) {
		// TODO Auto-generated method stub
		this.startTitleRow = startTitleRow;
	}

	private WritableCellFormat getDefaultTitleFormat() {
		// ���壺TIMES,��СΪ14��,���壬б��,���»���
		WritableFont font = new WritableFont(WritableFont.TIMES, 14,
				WritableFont.BOLD, true, UnderlineStyle.SINGLE);
		try {
			font.setColour(Colour.RED);// ��ɫ����
		} catch (WriteException e1) {
			// TODO �Զ���� catch ��
			e1.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);

		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		} catch (WriteException e) {
			// TODO �Զ���� catch ��
			e.printStackTrace();
		}
		return format;
	}

	private WritableCellFormat getDefaultNormolFormat() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 12);
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			// format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setAlignment(jxl.format.Alignment.LEFT);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		} catch (WriteException e) {
			// TODO �Զ���� catch ��
			e.printStackTrace();
		}
		return format;
	}

	public void setIntSheet(int intSheet) {
		this.intSheet = intSheet;
	}
}
