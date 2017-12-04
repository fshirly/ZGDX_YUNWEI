package com.fable.insightview.permission.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.entity.export.SysUserExportBean;
import com.fable.insightview.platform.service.ISysUserService;

@Controller
@RequestMapping("/platform/sysuserExport")
public class SysUserExportController {

	@Autowired
	ISysUserService sysUserService;
	private final Logger logger = LoggerFactory.getLogger(SysUserExportController.class);

	/**
	 * 导出文件
	 */
	@RequestMapping("/exportVal")
	public void exportVal(HttpServletResponse response,SysUserExportBean<SysUserInfoBean> export, SysUserInfoBean user) {
		SysUserInfoBean u = export.getSysUserBean();
		List<SysUserInfoBean> sysUserLst = null;
		if("".equals(u.getUserAccount()) && "".equals(u.getMobilePhone()) && "".equals(u.getUserName()) && u.getStatus() == -1 && u.getUserType() == -1 && u.getIsAutoLock() == -1) {
			user.setUserName("");
			user.setUserAccount("");
			user.setMobilePhone("");
			user.setIsAutoLock(-1);
			user.setStatus(-1);
			user.setUserType(-1);
			sysUserLst = sysUserService.getSysUserByCondition(user);
		} else {
			sysUserLst = sysUserService.querySysUser(u);
		}
		export.setExpResource(sysUserLst);

		this.refExportBean(export);
		this.exportExcel(response, export);
	}

	@SuppressWarnings("rawtypes")
	public SysUserExportBean refExportBean(SysUserExportBean exportBean) {
		
		exportBean.setColnameArr(exportBean.getColNameArrStr().split(","));
		exportBean.setTitleArr(exportBean.getColTitleArrStr().split(","));
		return exportBean;
		
	}

	// 导出
	@SuppressWarnings("rawtypes")
	public void exportExcel(HttpServletResponse response,
			SysUserExportBean exportEntity) {

		logger.info("用户信息导出前处理。。。");
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
	public void exportExcel(SysUserExportBean exportEntity, OutputStream out)
			throws Exception {

		logger.info("用户开始导出。。。");
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet();
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
		comment.setString(new XSSFRichTextString("可以在POI中添加注释！"));
		
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");

		String[] headers = exportEntity.getTitleArr();
		
		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 遍历数据集产生shuj
		try {
			String jsonResource = JsonUtil.toString(exportEntity.getExpResource());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode info = mapper.readTree(jsonResource);
			int index = 0;
			for (Iterator iter = info.iterator(); iter.hasNext();) {
				++index;
				// 创建一行
				row = sheet.createRow(index);
				XSSFCell cell = null;
				Object value = null;
				JsonNode item = (JsonNode) iter.next();
				for (int i = 0; i < exportEntity.getColnameArr().length; i++) {
					String columnName = exportEntity.getColnameArr()[i];
					String columnVal = "";
					if (columnName.indexOf(".") < 0) {
						columnVal = item.get(columnName).toString();
						
						// 用户类型
						if ("userType".equals(columnName)) {
							// UserType：0:管理员,1企业内IT部门用户,2:企业业务部门用户,3:外部供应商用户
							if (columnVal == "0") {
								columnVal = "IT部门用户";
							} else if (columnVal == "1") {
								columnVal = "业务部门用户";
							} else {
								columnVal = "外部供应商用户";
							}
						}
						
						// 状态
						if ("status".equals(columnName)) {
							if (columnVal == "0") {
								columnVal = "锁定";
							} else {
								columnVal = "正常";
							}
						}
						
						// 是否自动锁定
						if ("isAutoLock".equals(columnName)) {
							if (columnVal == "0") {
								columnVal = "是";
							} else {
								columnVal = "否";
							}
						}
						
						// 时间处理
						if ("lockedTime".equals(columnName)) {
							if (null != columnVal && !"".equals(columnVal)
									&& !"null".equals(columnVal)) {
								long l = Long.valueOf(columnVal).longValue();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								columnVal = sdf.format(new Date(l));
							}
						}
					} else {
						columnVal = item.get(columnName.split("\\.")[0]).get(
								columnName.split("\\.")[1]).toString();
					}
					cell = row.createCell(i);
					if (null != columnVal && !"null".equals(columnVal)) {
						value = columnVal;
					} else {
						value = "";
					}
					String textValue = value.toString();
					textValue = textValue.replace("\"", "");
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
					cell.setCellStyle(style2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户导出异常！", e.getMessage());
		}

		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

}
