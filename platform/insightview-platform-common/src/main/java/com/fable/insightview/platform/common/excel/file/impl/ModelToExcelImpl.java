package com.fable.insightview.platform.common.excel.file.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.CellType;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.fable.insightview.platform.common.excel.config.ExcelConfigFactory;
import com.fable.insightview.platform.common.excel.config.ExcelConfigManager;
import com.fable.insightview.platform.common.excel.config.RuturnConfig;
import com.fable.insightview.platform.common.excel.config.RuturnPropertyParam;
import com.fable.insightview.platform.common.excel.file.ModelToExcel;
import com.fable.insightview.platform.common.excel.model.DeptModel;

/**
 * @author wul
 * 
 */

public class ModelToExcelImpl implements ModelToExcel {
	private List modelList = null;

	private File excelFile = null;

	private OutputStream excelOutputStream = null;

	private RuturnConfig excelConfig = null;

	private WritableCellFormat headerFormat;

	private WritableCellFormat titleFormat;

	private WritableCellFormat normolFormat;

	private String header;

	private int intHeight = -1;

	// Ĭ�ϰ������ļ�����������dynamicTitleMap��property,exceltitlename��
	// ֻ�ǰ�javabean��Ӧ�����Ըı��������
	// ��� isDynamicTitle = true ���������dynamicTitleMap ������������ֻ������ʾ���������ļ����á�
	// isDynamicTitle = true Ҫ��֤��dynamicTitleMap size
	// ����0,���������õ�������javabean�д��ڵ��б������0
	private boolean isDynamicTitle = false;

	private Map dynamicTitleMap = new HashMap();

	// �ļ�����ʱ��֧��ģ�嵼��
	/*
	 * isTemplate = true ,�������ģ���ļ���ʽ������� templateFile��ģ���ļ��� startRow����ʼ���������
	 * paramMap��ģ���еĲ���map.put("Name","����")��ģ������һ����Ԫ��Ϊ�����Ʊ��ˡ�#Name#
	 * ,���ʱΪ���Ʊ��ˡ�����
	 */
	private boolean isTemplate = false;

	private String templateFile = null;

	private int startRow = 0;

	private Map paramMap = new HashMap();

	private int startColumn = 0;

	private boolean isInsertRow = true;

	// ֧�ֶ�Sheet���
	private String sheetName = "Sheet1";
	private int sheetNum = 0;

	public ModelToExcelImpl() {

	}

	public ModelToExcelImpl(File excelFile, RuturnConfig excelConfig,
			List modelList) {
		this.excelOutputStream = null;
		this.excelFile = excelFile;
		this.excelConfig = excelConfig;
		this.modelList = modelList;
	}

	public ModelToExcelImpl(OutputStream excelOutputStream,
			RuturnConfig excelConfig, List modelList) {
		this.excelFile = null;
		this.excelOutputStream = excelOutputStream;
		this.excelConfig = excelConfig;
		this.modelList = modelList;
	}

	private void initFormat() {
		if (this.headerFormat == null) {
			this.headerFormat = this.getDefaultHeaderFormat();
		}
		if (this.titleFormat == null) {
			this.titleFormat = this.getDefaultTitleFormat();
		}
		if (this.normolFormat == null) {
			this.normolFormat = this.getDefaultNormolFormat();
		}
	}

	public File getExcelfile() {
		initFormat();
		if (isDynamicTitle) {
			return getExcelfileByDynamicTitle(); // ��̬����ı�ͷ����
		} else if (isTemplate) { // ģ�巽ʽ
			return getExcelfileByTemplate();
		} else {
			return getExcelfileByConfig(); // �����ļ����У��ɸı��ͷ
		}
	}

	public File getExcelfileByTemplate() {
		try {
			if (this.excelFile != null && !excelFile.exists()) {
				this.excelFile.createNewFile();
			}
			// ����ģ�����ݵ������ļ���
			Workbook book;
			WritableWorkbook wbook;
			if (this.excelFile != null) {
				// this.copyFile(this.templateFile,
				// this.excelFile.getAbsolutePath());
				// book = Workbook.getWorkbook(this.excelFile);
				book = Workbook.getWorkbook(new File(this.templateFile));
				wbook = Workbook.createWorkbook(this.excelFile, book);
			} else {
				// this.copyFile(this.templateFile, this.excelOutputStream);
				book = Workbook.getWorkbook(new File(this.templateFile));
				wbook = Workbook.createWorkbook(this.excelOutputStream, book);
			}
			WritableSheet wsheet;
			if (wbook.getSheets().length > this.sheetNum) {
				wsheet = wbook.getSheet(this.sheetNum);
			} else {
				//System.out.println("ERROR:��ģ���ļ��в����� Sheet(" + this.sheetNum
						//+ ")");
				return null;
			}

			int rows = wsheet.getRows();
			int columns = wsheet.getColumns();

			// �滻ģ���� # #��֮������ݡ�
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					WritableCell wc = wsheet.getWritableCell(j, i);
					String strCell = wc.getContents().toString();
					//System.out.println("strCell = " + strCell);
					if (StringUtils.isNotBlank(strCell)
							&& strCell.indexOf("#") >= 0) {
						strCell = this.getParamValue(strCell);
						if (wc.getType() == CellType.LABEL) {
							Label l = (Label) wc;
							l.setString(strCell);
						}
					}
				}
			}
			// �У�������ȡֵ����ȡmodelList.size()��+ startRow
			columns = excelConfig.getColumnMap().size();
			rows = modelList.size() + startRow;

			String[] propertyName = new String[columns];
			String[] propertyDataType = new String[columns];
			for (int j = 0; j < columns; j++) {
				RuturnPropertyParam propertyBean = (RuturnPropertyParam) excelConfig
						.getColumnMap().get(String.valueOf(j + 1));
				// ÿ�� Model�ж�Ӧ������
				propertyName[j] = propertyBean.getName();
				propertyDataType[j] = propertyBean.getDataType();
			}
			// �жϵ�ǰ list �洢���� from ���� Map

			boolean isMap = false;
			if (modelList.size() > 0) {
				Object tmpObj = modelList.get(0);
				if (tmpObj instanceof Map) {
					isMap = true;
				}
			}

			for (int i = startRow; i < rows; i++) {
				// ÿһ�β�������Ҫ����һ��
				if (this.isInsertRow) {
					wsheet.insertRow(i);
				}
				Object modelObj = modelList.get(i - startRow);
				BeanWrapper bw = null;
				if (isMap != true) {
					bw = new BeanWrapperImpl(modelObj);
				}

				for (int j = 0; j < columns; j++) {
					String strCell = "";

					Object obj;
					if (isMap == true) {
						obj = ((Map) modelObj).get(propertyName[j]);
					} else {
						obj = bw.getPropertyValue(propertyName[j]);
					}

					if (obj != null) {
						strCell = obj.toString().trim();
					}

					Label cellNormol = new Label(this.startColumn + j, i,
							strCell, this.getNormolFormat());

					if (NumberUtils.isNumber(strCell)
							&& (propertyDataType[j].indexOf("Integer") >= 0)) {
						Number ncellNormol;
						if (strCell.indexOf(".") >= 0) {
							ncellNormol = new Number(this.startColumn + j, i,
									Double.parseDouble(strCell),
									this.getNormolFormat());
						} else {
							ncellNormol = new Number(this.startColumn + j, i,
									Long.parseLong(strCell),
									this.getNormolFormat());
						}
						wsheet.addCell(ncellNormol);
					} else {
						wsheet.addCell(cellNormol);
					}
				}

				if (intHeight > 0) {
					wsheet.setRowView(i, intHeight);
				}
			}

			book.close();
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
		return excelFile;
	}

	public File getExcelfileByDynamicTitle() {
		try {
			/*
			 * ��֧�ֶ�ҳ�Ĺ���У�����ÿ������������ÿ�ζ�Ҫ���½�����Workbook.createWorkbook(this.excelFile
			 * ); Ҫ�Ӵ�������ļ��Ļ��Ͻ����޸� Workbook book =
			 * Workbook.getWorkbook(this.excelFile); wbook =
			 * Workbook.createWorkbook(this.excelFile,book);
			 * ���Ƕ��ڸոս������ļ���this.excelFile.createNewFile();
			 * ��ִ��Workbook.getWorkbook(this.excelFile); ʱ���? �������� isNewFileFlag
			 * = false;
			 */
			boolean isNewFileFlag = false;
			if (this.excelFile != null && !excelFile.exists()) {
				this.excelFile.createNewFile();
				isNewFileFlag = true;
			}
			if (this.dynamicTitleMap == null) {
				//System.out.println("Error:δ���ö�̬�У�");
				return null;
			}
			WritableWorkbook wbook;
			if (this.excelFile != null) {
				if (isNewFileFlag) {
					wbook = Workbook.createWorkbook(this.excelFile);
				} else {
					Workbook book = Workbook.getWorkbook(this.excelFile);
					wbook = Workbook.createWorkbook(this.excelFile, book);
				}
			} else {
				wbook = Workbook.createWorkbook(this.excelOutputStream);
			}

			if (wbook.getSheets().length > this.sheetNum) {
				wbook.removeSheet(this.sheetNum);
			}
			WritableSheet wsheet = wbook.createSheet(this.sheetName,
					this.sheetNum);
			//System.out.println(this.sheetName + this.sheetNum);

			int columns = this.dynamicTitleMap.size();
			// ���ϱ�ͷ2�У�����1��
			int rows = modelList.size() + 3;
			//System.out.println("columns = " + columns);

			// ����Excel��ͷ
			wsheet.mergeCells(0, 0, columns - 1, 1);
			Label cellHeader = new Label(0, 0, this.getHeader(),
					this.getHeaderFormat());
			wsheet.addCell(cellHeader);

			if (intHeight > 0) {
				wsheet.setRowView(0, intHeight);
				wsheet.setRowView(1, intHeight);
			}

			// String[] propertyName = new String[columns];
			String[] propertyDataType = new String[columns];
			for (int j = 0; j < columns; j++) {
				RuturnPropertyParam propertyBean = (RuturnPropertyParam) excelConfig
						.getColumnMap().get(String.valueOf(j + 1));
				// ÿ�� Model�ж�Ӧ������
				propertyDataType[j] = propertyBean.getDataType();
			}

			String[][] title = this.getDynamicTitle();
			String[] propertyName = title[0];
			String[] columnWidth = title[1];
			if (StringUtils.isBlank(propertyName[propertyName.length - 1])) {
				//System.out.println("ERROR : ��̬��ͷ����ֵδ�������ļ���������ȫ��");
			}

			// ����Excel����
			for (int j = 0; j < propertyName.length; j++) {
				String excelTitleName = (String) this.dynamicTitleMap
						.get(propertyName[j]);
				Label cellTitle = new Label(j, 2, excelTitleName,
						this.getTitleFormat());
				wsheet.addCell(cellTitle);
				if (StringUtils.isNotBlank(columnWidth[j])) {
					wsheet.setColumnView(j, Integer.parseInt(columnWidth[j]));
				}
			}
			if (intHeight > 0) {
				wsheet.setRowView(2, intHeight);
			}

			// �жϵ�ǰ list �洢���� from ���� Map

			boolean isMap = false;
			if (modelList.size() > 0) {
				Object tmpObj = modelList.get(0);
				if (tmpObj instanceof Map) {
					isMap = true;
				}
			}
			for (int i = 3; i < rows; i++) {
				// BeanWrapper bw = new BeanWrapperImpl(modelList.get(i - 3));
				Object modelObj = modelList.get(i - 3);
				BeanWrapper bw = null;
				if (isMap != true) {
					bw = new BeanWrapperImpl(modelObj);
				}
				for (int j = 0; j < columns; j++) {

					String strCell = "";
					// Object obj = null;
					// if (StringUtils.isNotBlank(propertyName[j])) {
					// obj = bw.getPropertyValue(propertyName[j]);
					// }
					Object obj;
					if (isMap == true) {
						obj = ((Map) modelObj).get(propertyName[j]);
					} else {
						obj = bw.getPropertyValue(propertyName[j]);
					}
					if (obj != null) {
						strCell = obj.toString().trim();
					}

					Label cellNormol = new Label(j, i, strCell,
							this.getNormolFormat());
					// wsheet.addCell(cellNormol);
					if (NumberUtils.isNumber(strCell)
							&& (propertyDataType[j].indexOf("Integer") >= 0)) {
						Number ncellNormol;
						if (strCell.indexOf(".") >= 0) {
							ncellNormol = new Number(this.startColumn + j, i,
									Double.parseDouble(strCell),
									this.getNormolFormat());
						} else {
							ncellNormol = new Number(this.startColumn + j, i,
									Long.parseLong(strCell),
									this.getNormolFormat());
						}
						wsheet.addCell(ncellNormol);
					} else {
						wsheet.addCell(cellNormol);
					}
				}
				if (intHeight > 0) {
					wsheet.setRowView(i, intHeight);
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
		return excelFile;
	}

	public File getExcelfileByConfig() {
		// TODO Auto-generated method stub

		try {

			/*
			 * ��֧�ֶ�ҳ�Ĺ���У�����ÿ������������ÿ�ζ�Ҫ���½�����Workbook.createWorkbook(this.excelFile
			 * ); Ҫ�Ӵ�������ļ��Ļ��Ͻ����޸� Workbook book =
			 * Workbook.getWorkbook(this.excelFile); wbook =
			 * Workbook.createWorkbook(this.excelFile,book);
			 * ���Ƕ��ڸոս������ļ���this.excelFile.createNewFile();
			 * ��ִ��Workbook.getWorkbook(this.excelFile); ʱ���? �������� isNewFileFlag
			 * = false;
			 */

			boolean isNewFileFlag = false;
			if (this.excelFile != null && !excelFile.exists()) {
				this.excelFile.createNewFile();
				isNewFileFlag = true;
			}

			// WritableWorkbook wbook = Workbook.createWorkbook(this.excelFile);
			WritableWorkbook wbook;
			if (this.excelFile != null) {
				if (isNewFileFlag) {
					wbook = Workbook.createWorkbook(this.excelFile);
				} else {
					Workbook book = Workbook.getWorkbook(this.excelFile);
					wbook = Workbook.createWorkbook(this.excelFile, book);
				}

			} else {
				wbook = Workbook.createWorkbook(this.excelOutputStream);
			}

			if (wbook.getSheets().length > this.sheetNum) {
				wbook.removeSheet(this.sheetNum);
			}
			WritableSheet wsheet = wbook.createSheet(this.sheetName,
					this.sheetNum);
			// //System.out.println(this.sheetName + this.sheetNum);

			int columns = excelConfig.getColumnMap().size();
			// ���ϱ�ͷ2�У�����1��
			int rows = modelList.size() + 3;
			//System.out.println("columns = " + columns);
			// ����Excel��ͷ
			wsheet.mergeCells(0, 0, columns - 1, 1);
			Label cellHeader = new Label(0, 0, this.getHeader(),
					this.getHeaderFormat());
			wsheet.addCell(cellHeader);

			if (intHeight > 0) {
				wsheet.setRowView(0, intHeight);
				wsheet.setRowView(1, intHeight);
			}

			String[] propertyName = new String[columns];
			String[] propertyDataType = new String[columns];
			// ����Excel����
			for (int j = 0; j < columns; j++) {
				RuturnPropertyParam propertyBean = (RuturnPropertyParam) excelConfig
						.getColumnMap().get(String.valueOf(j + 1));
				String excelTitleName = propertyBean.getExcelTitleName();
				String name = propertyBean.getName().trim();

				// ���̬��ͷ���м�¼����ֵ��ȡ��̬����ı�ͷ��ֵ�����Ǵ����ֵ����Ϊ�գ����Ϊ�գ�ȡ�����ļ���ֵ
				if (this.dynamicTitleMap.containsKey(name)) {
					String strTitle = (String) this.dynamicTitleMap.get(name);
					if (StringUtils.isNotBlank(strTitle)) {
						excelTitleName = strTitle;
					}
				}
				Label cellTitle = new Label(j, 2, excelTitleName,
						this.getTitleFormat());
				wsheet.addCell(cellTitle);
				// ÿ�� Model�ж�Ӧ������
				propertyName[j] = propertyBean.getName();
				propertyDataType[j] = propertyBean.getDataType();
				// ����ÿ�еĿ��
				if (StringUtils.isNotBlank(propertyBean.getColumnWidth())) {
					wsheet.setColumnView(j,
							Integer.parseInt(propertyBean.getColumnWidth()));
				}
			}

			if (intHeight > 0) {
				wsheet.setRowView(2, intHeight);
			}
			// �жϵ�ǰ list �洢���� from ���� Map

			boolean isMap = false;
			if (modelList.size() > 0) {
				Object tmpObj = modelList.get(0);
				if (tmpObj instanceof Map) {
					isMap = true;
				}
			}
			for (int i = 3; i < rows; i++) {
				// BeanWrapper bw = new BeanWrapperImpl(modelList.get(i - 3));
				Object modelObj = modelList.get(i - 3);
				BeanWrapper bw = null;
				if (isMap != true) {
					bw = new BeanWrapperImpl(modelObj);
				}
				for (int j = 0; j < columns; j++) {

					String strCell = "";
					// Object obj = bw.getPropertyValue(propertyName[j]);
					Object obj;
					if (isMap == true) {
						obj = ((Map) modelObj).get(propertyName[j]);
					} else {
						obj = bw.getPropertyValue(propertyName[j]);
					}
					if (obj != null) {
						strCell = obj.toString().trim();
					}
					Label cellNormol = new Label(j, i, strCell,
							this.getNormolFormat());
					// wsheet.addCell(cellNormol);
					if (NumberUtils.isNumber(strCell)
							&& (propertyDataType[j].indexOf("Integer") >= 0)) {
						Number ncellNormol;
						if (strCell.indexOf(".") >= 0) {
							ncellNormol = new Number(this.startColumn + j, i,
									Double.parseDouble(strCell),
									this.getNormolFormat());
						} else {
							ncellNormol = new Number(this.startColumn + j, i,
									Long.parseLong(strCell),
									this.getNormolFormat());
						}
						wsheet.addCell(ncellNormol);
					} else {
						wsheet.addCell(cellNormol);
					}
				}
				if (intHeight > 0) {
					wsheet.setRowView(i, intHeight);
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
		return excelFile;
	}

	private String[][] getDynamicTitle() {
		int intColumn = excelConfig.getColumnMap().size();
		String[][] propertyName = new String[2][this.dynamicTitleMap.size()];

		int j = -1;
		for (int i = 0; i < intColumn; i++) {
			RuturnPropertyParam propertyBean = (RuturnPropertyParam) excelConfig
					.getColumnMap().get(String.valueOf(i + 1));
			String name = propertyBean.getName().trim();
			String columnWidth = propertyBean.getColumnWidth();
			if (this.dynamicTitleMap.containsKey(name)) {
				j++;
				propertyName[0][j] = name;
				propertyName[1][j] = columnWidth;

			}
		}

		// ��������ж�j��ֵ�����ж϶�̬�����map���������Ƿ���ȷ��
		if (j != this.dynamicTitleMap.size()) {
			//System.out.println("----��̬��ͷ����ֵδ�������ļ������ƣ�---");
		}
		return propertyName;
	}

	public void setHeaderCellFormat(WritableCellFormat headerFormat) {
		// TODO Auto-generated method stub
		this.headerFormat = headerFormat;
	}

	public void setNormolCellFormat(WritableCellFormat normolFormat) {
		// TODO Auto-generated method stub
		this.normolFormat = normolFormat;
	}

	public void setRowHeight(int intHeight) {
		// TODO Auto-generated method stub
		this.intHeight = intHeight;
	}

	public void setTitleCellFormat(WritableCellFormat titleFormat) {
		// TODO Auto-generated method stub
		this.titleFormat = titleFormat;
	}

	public void setHeader(String header) {
		// TODO Auto-generated method stub
		this.header = header;
	}

	public String getHeader() {
		return header;
	}

	public WritableCellFormat getHeaderFormat() {
		return headerFormat;
	}

	public int getIntHeight() {
		return intHeight;
	}

	public WritableCellFormat getNormolFormat() {
		return normolFormat;
	}

	public WritableCellFormat getTitleFormat() {
		return titleFormat;
	}

	private WritableCellFormat getDefaultHeaderFormat() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 24,
				WritableFont.BOLD);// ��������
		try {
			font.setColour(Colour.BLUE);// ��ɫ����
		} catch (WriteException e1) {
			// TODO �Զ���� catch ��
			e1.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// ���Ҿ���
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// ���¾���
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// ��ɫ�߿�
			// format.setBackground(Colour.YELLOW);// ��ɫ����
			format.setBackground(Colour.GRAY_50);

		} catch (WriteException e) {
			// TODO �Զ���� catch ��
			e.printStackTrace();
		}
		return format;
	}

	private WritableCellFormat getDefaultTitleFormat() {
		// ���壺TIMES,��СΪ14��,���壬б��,���»���
		WritableFont font = new WritableFont(WritableFont.TIMES, 14,
				WritableFont.BOLD, true, UnderlineStyle.SINGLE);
		try {
			font.setColour(Colour.BLUE);// ��ɫ����
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
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		} catch (WriteException e) {
			// TODO �Զ���� catch ��
			e.printStackTrace();
		}
		return format;
	}

	private void copyFile(String src, String dest) throws IOException {
		FileInputStream in = new FileInputStream(src);
		File file = new File(dest);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
			for (int i = 0; i < c; i++)
				out.write(buffer[i]);
		}
		in.close();
		out.close();
	}

	private String getParamValue(String strTemp) {
		String str = strTemp;
		Object[] keyarr = this.paramMap.keySet().toArray();
		for (int i = 0; i < keyarr.length; i++) {
			String strkey = "#" + keyarr[i].toString() + "#";
			str = StringUtils.replace(str, strkey, paramMap.get(keyarr[i])
					.toString());
		}
		return str;
	}

	public Map getDynamicTitleMap() {
		return dynamicTitleMap;
	}

	public void setDynamicTitleMap(Map dynamicTitleMap) {
		this.dynamicTitleMap = dynamicTitleMap;
	}

	public boolean isDynamicTitle() {
		return isDynamicTitle;
	}

	public void setDynamicTitle(boolean isDynamicTitle) {
		this.isDynamicTitle = isDynamicTitle;
	}

	public void setTemplateParam(String templateFile, int startRow, Map paramMap) {
		this.setTemplateParam(true, templateFile, startRow, paramMap);
	}

	public void setTemplateParam(boolean isTemplate, String templateFile,
			int startRow, Map paramMap) {
		this.setTemplateParam(isTemplate, templateFile, startRow, 0, paramMap,
				true);
	}

	// edit YJM��2008-03-29
	public void setTemplateParam(String templateFile, int startRow,
			int startColumn, Map paramMap, boolean isInsertRow) {
		this.setTemplateParam(true, templateFile, startRow, startColumn,
				paramMap, isInsertRow);
	}

	public void setTemplateParam(boolean isTemplate, String templateFile,
			int startRow, int startColumn, Map paramMap, boolean isInsertRow) {
		this.isTemplate = isTemplate;
		this.templateFile = templateFile;
		this.startRow = startRow;
		this.paramMap = paramMap;
		this.startColumn = startColumn;
		this.isInsertRow = isInsertRow;
	}

	public Map getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public boolean isTemplate() {
		return isTemplate;
	}

	public void setTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}

	public void setSheet(int sheetNum, String sheetName) {
		// TODO Auto-generated method stub
		this.sheetName = sheetName;
		this.sheetNum = sheetNum;
	}

	public static void main(String[] args) {
		List modelList = new ArrayList();
		for (int i = 0; i < 65; i++) {
			DeptModel dept = new DeptModel();
			dept.setDeptName("�ܲ�");
			dept.setDeptCode("A10" + i);
			dept.setDeptNo(i);
			dept.setSendFileName("���Ϸ�");
			modelList.add(dept);
		}
		// Colour[] color = Colour.getAllColours();
		// for(int i=0;i<color.length;i++){
		// //System.out.println(color[i].getDescription() + " = " +
		// color[i].getDefaultRGB().getRed() + " "
		// +color[i].getDefaultRGB().getGreen() +" " +
		// color[i].getDefaultRGB().getBlue());
		// }
		Map map = new HashMap();
		map.put("deptName", "�������1");
		map.put("deptCode", "���ű��1");
		map.put("deptNo", "����1");

		ExcelConfigManager configManager = ExcelConfigFactory
				.createExcelConfigManger();
		File excelfile = new File("E:\\workspace\\modeltoexcel.xls");
		ModelToExcelImpl mte = new ModelToExcelImpl(excelfile,
				configManager.getModel("deptModel", ""), modelList);
		mte.setHeader("���ŷ��ļ�ƣ�2007��");
		mte.setRowHeight(500);
		mte.setDynamicTitle(true);
		mte.setDynamicTitleMap(map);
		mte.getExcelfile();

	}

}
