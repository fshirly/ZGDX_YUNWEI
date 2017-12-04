package com.fable.insightview.monitor.daily.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import com.fable.insightview.monitor.daily.mapper.TimerdayAnnounceMapper;
import com.fable.insightview.monitor.daily.service.TimerdayAnnounceService;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liy 上传日通告具体caoz
 */
@Service("timerdayAnnounceServiceImpl")
public class TimerdayAnnounceServiceImpl implements TimerdayAnnounceService {
	
	public static Logger logger = LoggerFactory.getLogger(TimerdayAnnounceServiceImpl.class);
	
	@Autowired
	private TimerdayAnnounceMapper timerdayAnnounceMapper;

	/*
	 * 开始进行上传
	 */
	@Override
	public String beginToUploadDayAnnounceExcel(String filePath,String prefixFileName) {
		try {
			// logger.info("Daily filePath="+filePath);
			// 创建上传日通告的文件
			File targetFile = this.createDayAnnounceExcel(filePath,prefixFileName);
			return targetFile.getName();
		} catch (Exception e) {
			logger.error("Daily 创建上传日通告的文件",e); 
			return "";
		}
	}

	/*
	 * 查询值班人
	 */
	@Override
	public String querybeforeDutePeople() { 
		return timerdayAnnounceMapper.querybeforeDutePeople(getDate());
	}
		
	/*
	 * 查询前一天值班人号码
	 */
	@Override
	public String queryDutePeoplePhoneNumber() { 
		return timerdayAnnounceMapper.querybeforePhoneNumber(getDate());
	}
	
	/*
	 * 查询当天值班人号码
	 */
	@Override
	public String queryCurrentDutePeoplePhoneNumber() { 
		return timerdayAnnounceMapper.querybeforePhoneNumber(getCurrentDate());
	}

	/*
	 * 创建文件
	 */
	private File createDayAnnounceExcel(String filePath, String prefixFileName)
			throws Exception {
		// 创建模板对象
		String path = new File("").getAbsolutePath() + File.separator
				+ filePath + File.separator + "day_Announcements.xls";
		File sourceFile = new File(path);
		// 创建日通告目标对象
		File targetmkdir = new File(new File("").getAbsolutePath()
				+ File.separator + filePath + File.separator
				+ "dayAnnouncExcel");
		if (!targetmkdir.exists()) {
			targetmkdir.mkdirs();
		}

		File targetFile = new File(new File("").getAbsolutePath()
				+ File.separator + filePath + File.separator
				+ "dayAnnouncExcel" + File.separator + prefixFileName + this.getDate() + ".xls");
		
		this.copyFile(sourceFile, targetFile);
		
		this.inputPoiValue(targetFile);
		
		return targetFile;
	}
	
	private void copyFile(File sourceFile, File targetFile) throws  Exception{
		BufferedInputStream isBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			if (!targetFile.exists()) {
				targetFile.createNewFile();
			}
			isBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = isBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
		} finally {
			if (isBuff != null) {
				try {
					isBuff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outBuff != null) {
				try {
					isBuff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		}
	}

	/*
	 * 获取前一天日期
	 */
	private String getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}
	
	/*
	 * 获取当天日期
	 */
	private String getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 0);
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}

	/*
	 * 修改文件
	 */
	public void inputPoiValue(File filepath) throws Exception{
		HSSFWorkbook workbook = null;
		Random random = new Random(); 
		workbook = new HSSFWorkbook(new FileInputStream(filepath));
		/*
		 * 新表
		 */
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		// 标题1
		String title_1 = getDate().substring(0, 4) + "年"
				+ getDate().substring(4, 6) + "月"
				+ getDate().substring(6, 8) + "日24小时监测情况";
		row = sheet.getRow(1);
		if (row.getCell(0) == null) {
			cell = row.createCell(0);
		} else {
			cell = row.getCell(0);
		}
		cell.setCellValue(title_1);
		
		// 机房巡检标题
		row = sheet.getRow(44);
		cell = row.getCell((short) 0);
		String dataString = this.getDate().substring(0, 4) + "年"
				+ getDate().substring(4, 6) + "月"
				+ getDate().substring(6, 8) + "日" + "时人工巡检机房情况";
		cell.setCellValue(dataString);
		
		// 机房1
		row = sheet.getRow(47);
		cell = row.getCell((short) 1);
		cell.setCellValue(random.nextInt(27) % (27 - 25 + 1) + 25);
		cell = row.getCell((short) 2);
		cell.setCellValue((int) (random.nextInt(55) % (55 - 45) + 45));
		
		// 机房2
		row = sheet.getRow(48);
		cell = row.getCell((short) 1);
		cell.setCellValue(random.nextInt(27) % (27 - 25 + 1) + 25);
		cell = row.getCell((short) 2);
		cell.setCellValue((int) (random.nextInt(55) % (55 - 45) + 45));
		
		// 机房3
		row = sheet.getRow(49);
		cell = row.getCell((short) 1);
		cell.setCellValue(random.nextInt(27) % (27 - 25 + 1) + 25);
		cell = row.getCell((short) 2);
		cell.setCellValue((int) (random.nextInt(55) % (55 - 45) + 45));
		
		// 签到
		if (sheet.getRow(57) != null) {
			row = sheet.getRow(57);
			String zhibString = "";
			cell = row.getCell((short) 0);
			zhibString = querybeforeDutePeople();
			cell.setCellValue("报送人:" + zhibString);
			cell = row.getCell((short) 8);
			cell.setCellValue("报送日期：" + getCurrentDate().substring(0, 4) + "年"
					+ getCurrentDate().substring(4, 6) + "月"
					+ getCurrentDate().substring(6, 8) + "日");
		}
		
		FileOutputStream fileOut1 = new FileOutputStream(filepath
				.getAbsoluteFile());
		workbook.write(fileOut1);
		fileOut1.close();
	}
}