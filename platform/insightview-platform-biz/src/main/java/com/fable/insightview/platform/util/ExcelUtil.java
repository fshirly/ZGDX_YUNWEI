package com.fable.insightview.platform.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fable.insightview.platform.common.entity.ColumnBean;
import com.fable.insightview.platform.common.util.Cast;
import com.fable.insightview.platform.common.util.StringUtil;
import com.fable.insightview.platform.util.exception.BusinessException;

/**
 * 
 * @author zhouwei
 * 
 */
public class ExcelUtil {

	private static HashMap<Workbook, CellStyle> headStyleMap = new HashMap<Workbook, CellStyle>();

	private static HashMap<Workbook, CellStyle> bodyStyleMap = new HashMap<Workbook, CellStyle>();

	private static int maxWidth = 100;

	public static File dataToExcel(List<ColumnBean> cols,
			List<HashMap<String, Object>> dataList, String title , String fileName , String suffix) {

		Workbook wb = null;
		try {
			if(StringUtil.equalsIgnoreCase(suffix , ".xlsx")){
				wb = new XSSFWorkbook(); // 创建2007excel版本的Workbook
			}else{
				wb = new HSSFWorkbook(); // 创建2003excel版本的Workbook
			}
			Sheet sheet0 =  wb.createSheet(); // 创建sheet页
			Row row = null;// 行
			Cell cell = null;// 单元值

			if ((title != null) && (!title.equals(""))) {// 给excel赋标题
				row = sheet0.getRow(0);// 获取行
				if (row == null) {
					row = sheet0.createRow((int) 0);
				}
				row.setHeightInPoints(40);

				cell = row.getCell((int) 0);// 获得单元格
				if (cell == null) {
					cell = row.createCell((int) 0);
				}
				cell.setCellValue(title);// 赋值
			}

			row = sheet0.getRow(1);// 获列题行
			if (row == null) {
				row = sheet0.createRow((int) 1);
			}
			row.setHeightInPoints(40);

			int q = 0;
			for (ColumnBean col : cols) {

				cell = row.getCell((int) q);// 获得单元格
				if (cell == null) {
					cell = row.createCell((int) q);
				}
				cell.setCellValue(col.getName());// 赋值
				q = q + 1;// 显示列梯增

				// 设置单元格样式
				cell.setCellStyle(getHeadStyle(wb));
			}

			int r0 = 2;// 第2行数开始
			Iterator<HashMap<String, Object>> it = dataList.iterator();
			while (it.hasNext()) {/* 获取每行数据 */

				Map<String, Object> cdata = (Map<String, Object>) it.next();/* 实际数据 */

				row = sheet0.getRow(r0);// 获取行
				if (row == null) {
					row = sheet0.createRow((int) r0);
				}
				int l0 = 0;// 实际显示列
				for (ColumnBean col : cols) {
					cell = row.getCell(l0);// 获得单元格
					if (cell == null) {
						cell = row.createCell((int) l0);
					}
					cell.setCellValue(Cast.toString(cdata.get(col.getId())));// 根据列字段获取列数据并付给cel
					// 设置单元格样式
					cell.setCellStyle(getBodyStyle(wb));
					l0 = l0 + 1;
				}
				r0 = r0 + 1;

				if(wb instanceof HSSFWorkbook){//2003格式导出上限
					if (r0 > 65535)
						break;
				}
				
			}

			for (int i = 0; i < cols.size(); i++) {
				sheet0.autoSizeColumn((int) i);// 自动适应宽度
				int columnWidth = sheet0.getColumnWidth(i);
				if(columnWidth > maxWidth * 256){
					sheet0.setColumnWidth(i,maxWidth * 256);
				}
			}

		} catch (Exception ex) {
			throw new BusinessException("00300013", ex, null);
		} finally {
			clearHeadStyle(wb);
			clearBodyStyle(wb);
		}

		File file = writeToFile(wb, fileName);

		return file;
	}

	/**
	 * 获得表头样式
	 * 
	 * @param wb
	 * @return
	 */
	private static CellStyle getHeadStyle(Workbook wb) {
		CellStyle style = headStyleMap.get(wb);
		if (style == null) {
			style = createHeadStyle(wb);
			headStyleMap.put(wb, style);
		}
		return style;
	}

	/**
	 * 清空表头样式
	 */
	private static void clearHeadStyle(Workbook wb) {
		headStyleMap.remove(wb);
	}

	/**
	 * 获得主体样式
	 * 
	 * @param wb
	 * @return
	 */
	private static CellStyle getBodyStyle(Workbook wb) {
		CellStyle style = bodyStyleMap.get(wb);
		if (style == null) {
			style = createBodyStyle(wb);
			bodyStyleMap.put(wb, style);
		}
		return style;
	}

	/**
	 * 清空主体样式
	 */
	private static void clearBodyStyle(Workbook wb) {
		bodyStyleMap.remove(wb);
	}

	/**
	 * 
	 * 创建表头样式
	 * 
	 * @param wb
	 * @return
	 */
	private static CellStyle createHeadStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// 设置居中
		style.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		// 设置Excel中的边框(表头的边框)
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 设置字体
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 16); // 字体高度
		font.setFontName(" 宋体 "); // 字体
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		return style;
	}

	/**
	 * 
	 * 创建主体样式
	 * 
	 * @param wb
	 * @return
	 */
	private static CellStyle createBodyStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		// 设置居中
		style.setAlignment(CellStyle.ALIGN_GENERAL);// 水平居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		// 设置Excel中的边框(表头的边框)
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setWrapText(true);
		// 设置字体
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 12); // 字体高度
		font.setFontName(" 宋体 "); // 字体
		style.setFont(font);
		return style;
	}
	
	
	/**
	 * 将excel写入临时文件
	 * 
	 * @param wb
	 * @return
	 */
	private static File writeToFile(Workbook wb , String fileName) {
		File file = new File(fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			wb.write(fos);
			fos.flush();
		} catch (IOException e) {
			throw new BusinessException("00300013", e, null);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
		return file;
	}
}
