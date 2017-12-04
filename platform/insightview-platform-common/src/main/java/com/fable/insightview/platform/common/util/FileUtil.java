package com.fable.insightview.platform.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fable.insightview.platform.core.exception.BusinessException;

/**
 * @author Zhangh
 * @version 2014年8月28日 下午2:25:59
 */
public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	private static final Format format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 当前的classpath的绝对URI路径 eg:
	 * 
	 * @return
	 */
	public static String getAppRootPath() {
		URL root = FileUtil.class.getResource("/");
		try {
			return Paths.get(root.toURI()).toString();
		} catch (URISyntaxException e) {
			logger.error("", e);
			return root.getPath().substring(1);
		}
	}

	/**
	 * 创建文件目录 dir.mkdirs()
	 * 
	 * @param destDirName
	 * @return
	 */
	public static boolean createDirs(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			return false;
		}
		if (!destDirName.endsWith(File.separator))
			destDirName = destDirName + File.separator;
		// 创建单个目录
		if (dir.mkdirs()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	/*
	 * public boolean deleteDirectory(String sPath) {
	 * //如果sPath不以文件分隔符结尾，自动添加文件分隔符 if (!sPath.endsWith(File.separator)) { sPath
	 * = sPath + File.separator; } File dirFile = new File(sPath);
	 * //如果dir对应的文件不存在，或者不是一个目录，则退出 if (!dirFile.exists() ||
	 * !dirFile.isDirectory()) { return false; } Boolean flag = true;
	 * //删除文件夹下的所有文件(包括子目录) File[] files = dirFile.listFiles(); for (int i = 0;
	 * i < files.length; i++) { //删除子文件 if (files[i].isFile()) { flag =
	 * deleteFile(files[i].getAbsolutePath()); if (!flag) break; } //删除子目录 else
	 * { flag = deleteDirectory(files[i].getAbsolutePath()); if (!flag) break; }
	 * } if (!flag) return false; //删除当前目录 if (dirFile.delete()) { return true;
	 * } else { return false; } }
	 */

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	/*
	 * public boolean deleteFile(String sPath) { Boolean flag = false; File file
	 * = new File(sPath); // 路径为文件且不为空则进行删除 if (file.isFile() && file.exists())
	 * { file.delete(); flag = true; } return flag; }
	 */

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 * 
	 * @param delpath
	 *            String
	 * @return boolean
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean deleteFile(String delpath) throws Exception {
		try {

			File file = new File(delpath);
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "\\" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
					} else if (delfile.isDirectory()) {
						deleteFile(delpath + "\\" + filelist[i]);
					}
				}
				file.delete();
			}
			return true;
		} catch (FileNotFoundException e) {
			logger.error("" + e);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 重命名文件夹名称
	 * 
	 * @param delpath
	 * @param newName
	 * @return
	 * @throws Exception
	 */
	public boolean editfile(String delpath, File newName) throws Exception {
		File file = new File(delpath);
		// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
		if (file.isDirectory()) {
			file.renameTo(newName);
		}
		return true;
	}

	/**
	 * 输出某个文件夹下的所有文件夹和文件路径
	 * 
	 * @param filepath
	 *            String
	 * @return boolean
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean readfile(String filepath)
			throws FileNotFoundException, IOException {
		try {

			File file = new File(filepath);
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时（即文件夹下有子文件时），返回 true
			if (!file.isDirectory()) {

			} else if (file.isDirectory()) {
				// 得到目录中的文件和目录
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {

					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
					}
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 文件下载 解决中文乱码
	 * 
	 * @param request
	 * @param response
	 * @param filePath
	 * @param fileName
	 * @throws IOException
	 */
	public void download(HttpServletRequest request,
			HttpServletResponse response, String filePath, String fileName)
			throws IOException {
		// String encodeType = getFilecharset(new File(filePath+fileName));
		String encodeType = getFilecharset(new File(filePath));
		response.setContentType("APPLICATION/OCTET-STREAM");

		// 解决中文乱码问题
		// fileName=new String(fileName.getBytes(encodeType),"ISO-8859-1");

		// 创建file对象
		// File file=new File(filePath+fileName);
		File file = new File(filePath);

		// 设置response的编码方式
		response.setContentType("application/x-msdownload");

		// 写明要下载的文件的大小
		response.setContentLength((int) file.length());

		// 解决中文乱码
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(encodeType), "ISO-8859-1"));

		// 读出文件到i/o流
		FileInputStream fis = new FileInputStream(file);
		@SuppressWarnings("resource")
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[1024];// 相当于我们的缓存

		long k = 0;// 该值用于计算当前实际下载了多少字节

		// 从response对象中得到输出流,准备下载
		OutputStream myout = response.getOutputStream();

		// 开始循环下载
		while (k < file.length()) {

			int j = buff.read(b, 0, 1024);
			k += j;

			// 将b中的数据写到客户端的内存
			myout.write(b, 0, j);

		}

		// 将写入到客户端的内存的数据,刷新到磁盘
		myout.flush();
		fis.close();
		buff.close();
		myout.close();
	}

	/**
	 * 删除单个文件
	 * 
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file) {
		return file.delete();
	}

	/**
	 * 获取文件编码
	 * 
	 * @param sourceFile
	 * @return
	 */
	@SuppressWarnings({ "resource", "unused" })
	private static String getFilecharset(File sourceFile) {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(sourceFile));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				return charset; // 文件编码为 ANSI
			} else if (first3Bytes[0] == (byte) 0xFF
					&& first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE"; // 文件编码为 Unicode
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE
					&& first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE"; // 文件编码为 Unicode big endian
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF
					&& first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8"; // 文件编码为 UTF-8
				checked = true;
			}
			bis.reset();
			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
							// (0x80
							// - 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
			}
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charset;
	}

	/**
	 * 根据目录获取子文件
	 * 
	 * @param path
	 * @return datagrid json
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getFilesByDir(String path) {
		File dir = new File(path);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		File[] fs = dir.listFiles();
		List list = new ArrayList();
		String s = "";
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isFile()) {
				cal.setTimeInMillis(fs[i].lastModified());
				s = "{\"id\":\""
						+ fs[i].getAbsolutePath().replace("\\", "\\\\")
						+ "\",\"fileName\":\"" + fs[i].getName() + "\","
						+ "\"updateTime\":\"" + formatter.format(cal.getTime())
						+ "\"," + "\"fileSize\":\""
						+ (fs[i].length() / 1024 + 1) + " KB" + "\"}";
				list.add(s);
			}
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
				.fromObject(list);
		s = "{\"total\":" + list.size() + "," + "\"rows\":"
				+ jsonArray.toString() + "}";
		return s;
	}

	/**
	 * 解压Zip文件到指定目录
	 * 
	 * @param unZipFileName
	 * @param outputDirectory
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void upzipFile(String unZipFileName, String outputDirectory)
			throws IOException {
		String charSet = getFilecharset(new File(unZipFileName));
		// 创建输出文件夹对象
		File outDirFile = new File(outputDirectory);
		outDirFile.mkdirs();

		// 打开压缩文件文件夹
		ZipFile zipFile = new ZipFile(unZipFileName, charSet);
		for (Enumeration entries = zipFile.getEntries(); entries
				.hasMoreElements();) {
			ZipEntry ze = (ZipEntry) entries.nextElement();
			File file = new File(outDirFile, ze.getName());
			if (ze.isDirectory()) {// 如果是目录创建目录
				file.mkdirs();
			} else {
				File parent = file.getParentFile();
				if (parent != null && !parent.exists()) {
					parent.mkdirs();
				}
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				InputStream is = zipFile.getInputStream(ze);
				inStream2outStream(is, fos);
				fos.close();
				is.close();
			}
		}
		zipFile.close();
		new File(unZipFileName).delete();
	}

	/**
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	public static void inStream2outStream(InputStream is, OutputStream os)
			throws IOException {
		BufferedInputStream bis = new BufferedInputStream(is);
		// BufferedOutputStream bos = new BufferedOutputStream(os);
		int bytesRead = 0;
		for (byte[] buffer = new byte[1024]; ((bytesRead = bis.read(buffer, 0,
				1024)) != -1);) {
			os.write(buffer, 0, bytesRead); // 将流写入
		}
	}

	/**
	 * 取根目录下的所有文件的相对路径
	 * 
	 * @param baseFolder
	 * @return
	 */
	public static List<String> getAllRetFilesByFolder(String baseFolder) {
		File file = new File(baseFolder);
		List<String> allRelFilePath = new ArrayList<String>();
		parseListFiles(file, allRelFilePath, baseFolder);
		return allRelFilePath;
	}

	private static void parseListFiles(File file, List<String> allRelFilePath,
			String rootPath) {
		if (!file.exists())
			return;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File item : files) {
				parseListFiles(item, allRelFilePath, rootPath);
			}
		} else {
			String retPath = file.getPath().substring(rootPath.length() + 1);
			allRelFilePath.add(retPath);
		}
	}

	public static String getBaseFileFolder(String configFolder) {
		String path;
		Path configFolderPath = Paths.get(configFolder);
		if (configFolderPath.isAbsolute()) {
			path = configFolder;
		} else {
			String basePath = FileUtil.getAppRootPath();
			path = basePath + File.separator + configFolder;
		}
		logger.info("baseFilePath:" + path);
		return path;
	}

	/**
	 * 将数据以Excel格式输出到页面
	 * 
	 * @param titles
	 *            表头
	 * @param dataAryList
	 *            表内容
	 * @author nimj 2015/11/18
	 */
	public static void exportExcel(String[] titles, List<Object[]> dataAryList,
			String fileName, HttpServletResponse response) throws Exception {
		if (ArrayUtils.isEmpty(titles) || dataAryList == null
				|| StringUtils.isEmpty(fileName) || response == null) {
			throw new BusinessException("00000014");
		}
		OutputStream output = null;
		// 创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet();
		// 在sheet中添加表头（第0行）
		HSSFRow row = sheet.createRow(0);
		// 创建居中单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 设置表头
		HSSFCell cell = null;
		for (int i = 0, cols = titles.length; i < cols; i++) {
			cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(style);
		}

		// 写入实体数据
		Object[] dataAry = null;
		Object data = null;
		for (int i = 0, size = dataAryList.size(); i < size; i++) {
			row = sheet.createRow(i + 1);
			dataAry = dataAryList.get(i);
			for (int j = 0, cols = dataAry.length; j < cols; j++) {
				data = dataAry[j];
				if (data instanceof Date) {
					row.createCell(j).setCellValue(format.format(data));
				} else {
					row.createCell(j).setCellValue(String.valueOf(data));
				}
			}
		}
		for (int i = 0, cols = titles.length; i < cols; i++) {
			sheet.autoSizeColumn(i);// 列宽度自适应
		}
		// 将文件输出到页面
		response.setContentType("octets/stream");
		try {
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
			output = new BufferedOutputStream(response.getOutputStream());
			wb.write(output);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		}

	}
}
