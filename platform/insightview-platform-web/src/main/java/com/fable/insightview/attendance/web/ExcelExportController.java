package com.fable.insightview.attendance.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.platform.attendance.entity.HisAttRecordInfoVo;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.entity.export.ExcelToBeanDTO;

/**
 * Excel导出
 * @author xue.antai
 *
 */
public abstract class ExcelExportController {
	List<T> expResource = null;
	
	private final Logger logger = LoggerFactory.getLogger(ExcelExportController.class);
	
	/**
	 * 导出文件
	 */
	public void exportVal(HttpServletResponse response,ExcelToBeanDTO<T> export, List<T> expResources) {
		export.setExpResource(expResources);

		this.refExportBean(export);
		this.exportExcel(response, export);
	}

	@SuppressWarnings("rawtypes")
	public ExcelToBeanDTO refExportBean(ExcelToBeanDTO exportBean) {
		exportBean.setColnameArr(exportBean.getColNameArrStr().split(","));
		exportBean.setTitleArr(exportBean.getColTitleArrStr().split(","));
		return exportBean;
	}

	// 导出
	@SuppressWarnings("rawtypes")
	public void exportExcel(HttpServletResponse response,ExcelToBeanDTO exportEntity) {
		
		logger.info("信息导出前处理。。。");
		// 导出的文件名称
		String fileName = exportEntity.getExlName();

		// 设置response头信息
		response.setContentType("octets/stream");
		response.setCharacterEncoding("gbk");
		try {
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "iso8859-1"));

			OutputStream out = response.getOutputStream();
			// ExportExcelByPOI exportExcelByPOI = new ExportExcelByPOI();
			this.exportExcel(exportEntity, out);

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * 通用的POI导出数据方法
	 * 
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 */
	@SuppressWarnings( { "rawtypes", "unused" })
	public void exportExcel(ExcelToBeanDTO exportEntity, OutputStream out) {
		logger.info("开始导出。。。");
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet();
		XSSFCell cell = null;
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		
		// font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		
		// 生成并设置另一个样式
		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(CellStyle.BORDER_THIN);
		style2.setBorderLeft(CellStyle.BORDER_THIN);
		style2.setBorderRight(CellStyle.BORDER_THIN);
		style2.setBorderTop(CellStyle.BORDER_THIN);
		style2.setAlignment(CellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		// 生成另一个字体
		XSSFFont font2 = workbook.createFont();
		
		// font2.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		XSSFDrawing patriarch = sheet.createDrawingPatriarch();
		
		// 定义注释的大小和位置,详见文档
		XSSFComment comment = patriarch
				.createCellComment((new XSSFClientAnchor(0, 0, 0, 0, (short) 4,2, (short) 6, 5)));
		
		// 设置注释内容
		comment.setString(new XSSFRichTextString("签到统计详情"));
		
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("xat");
		String[] headers = exportEntity.getTitleArr();
		
		List<String[]> signTimeSlots = null;
		boolean hasSignTimeSlot = false;
		int signTimeIndex = 0;
		for(int j = 0;j < headers.length;j++) {
			if("签到时间段".equals(headers[j])) {
				hasSignTimeSlot = true;
				signTimeIndex = j;
			}
		}
		int cellNum = headers.length + (hasSignTimeSlot ? exportEntity.getPeriodCount()-1 : 0);
		// 生成表格标题行
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cellNum-1));
		XSSFRow row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue(exportEntity.getAttPlanName());
		cell.setCellStyle(style2);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		// 生成表头
		row = sheet.createRow(1);
		if(hasSignTimeSlot) {
			sheet.addMergedRegion(new CellRangeAddress(1, 1, signTimeIndex, signTimeIndex+exportEntity.getPeriodCount()-1));
		}
		XSSFRichTextString text = null;
		for(int hi = 0,hri = 0;hi < cellNum;hi++) {
			if(hasSignTimeSlot) {
				if(hi>=signTimeIndex && hi<=(signTimeIndex+exportEntity.getPeriodCount()-1)) {
					text = new XSSFRichTextString(headers[hri]);
					cell = row.createCell(hi);
					cell.setCellStyle(style);
					cell.setCellValue(text);
					continue;
				}
				else if(hi>(signTimeIndex+exportEntity.getPeriodCount()-1)) {
					hri++;
					text = new XSSFRichTextString(headers[hri]);
					cell = row.createCell(hi);
					cell.setCellStyle(style);
					cell.setCellValue(text);
				}
				else {
					text = new XSSFRichTextString(headers[hri]);
					cell = row.createCell(hi);
					cell.setCellStyle(style);
					cell.setCellValue(text);
					hri++;
				}
			}
			else {
				text = new XSSFRichTextString(headers[hi]);
				cell = row.createCell(hi);
				cell.setCellStyle(style);
				cell.setCellValue(text);
			}
		}
		// 生成内容
		try {
			String jsonResource = JsonUtil.toString(exportEntity.getExpResource());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode content = mapper.readTree(jsonResource);
			Iterator it = content.iterator();
			JsonNode item = null;
			String columnName = null;
			String columnVal = "";
			// 签到时间段列下标
			int ci = 0;
			for(int ri=0,rn=2;ri<exportEntity.getExpResource().size();ri++) {
				item = (JsonNode) it.next();
				if(!hasSignTimeSlot) {
					row = sheet.createRow(rn);
					for(ci=0;ci<cellNum;ci++) {
						cell = sheet.getRow(rn).createCell(ci);
						columnName = exportEntity.getColnameArr()[ci];
						columnVal = item.get(columnName).toString();
						this.creatCell(columnVal, cell, workbook, style2);
					}
					rn++;
				}
				else {
					row = sheet.createRow(rn);
					row = sheet.createRow(rn+1);
					// 签到时间段指针
					int sti = 0;
					ci = 0;
					for(int cri=0;ci<cellNum;ci++) {
						if(ci>=signTimeIndex && ci<=signTimeIndex+exportEntity.getPeriodCount()-1) {
							signTimeSlots = ((HisAttRecordInfoVo)exportEntity.getExpResource().get(ri)).getSignTimeSlot();
							cell = sheet.getRow(rn).createCell(ci);
							if(sti>signTimeSlots.size()-1) {
								columnVal = "";
							}
							else {
								columnVal = signTimeSlots.get(sti)[0];
							}
							this.creatCell(columnVal, cell, workbook, style2);
							cell = sheet.getRow(rn+1).createCell(ci);
							if(sti>signTimeSlots.size()-1) {
								columnVal = "";
							}
							else {
								columnVal = signTimeSlots.get(sti)[1];
							}
							this.creatCell(columnVal, cell, workbook, style2);
							sti++;
							continue;
						}
						else if(ci>signTimeIndex+exportEntity.getPeriodCount()-1) {
							cri++;
							sheet.addMergedRegion(new CellRangeAddress(rn, rn+1, ci, ci));
							cell = sheet.getRow(rn).createCell(ci);
							columnName = exportEntity.getColnameArr()[cri];
							columnVal = item.get(columnName).toString();
							this.creatCell(columnVal, cell, workbook, style2);
						}
						else {
							sheet.addMergedRegion(new CellRangeAddress(rn, rn+1, ci, ci));
							cell = sheet.getRow(rn).createCell(ci);
							columnName = exportEntity.getColnameArr()[cri];
							columnVal = item.get(columnName).toString();
							this.creatCell(columnVal, cell, workbook, style2);
							cri++;
						}
					}
					
					rn=rn+2;
				}
			}
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出异常！",e.getMessage());
		}
		

	}

	/**
	 * 单元格赋值
	 * @param columnVal
	 * @param cell
	 * @param workbook
	 * @param style
	 * @return
	 */
	private XSSFCell creatCell(String columnVal, XSSFCell cell,XSSFWorkbook workbook, XSSFCellStyle style) {
		Object value = null;
		if (null != columnVal && !"null".equals(columnVal)) {
			value = columnVal;
		} else {
			value = "";
		}
		String textValue = value.toString();
		textValue = textValue.replace("\"", "");
		textValue = textValue.replace("[", "");
		textValue = textValue.replace("]", "");
		if (null == value) {
			value = "-";
		}
		
		XSSFRichTextString richString = new XSSFRichTextString(textValue);
		XSSFFont font3 = workbook.createFont();
		font3.setColor(HSSFColor.BLUE.index);
		richString.applyFont(font3);
		cell.setCellValue(richString);

		value = textValue;
		cell.setCellValue(value.toString());
		cell.setCellStyle(style);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		return cell;
	}
}
