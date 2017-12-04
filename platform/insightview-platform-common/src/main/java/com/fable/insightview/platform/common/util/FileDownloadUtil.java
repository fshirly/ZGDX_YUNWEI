package com.fable.insightview.platform.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fable.insightview.platform.sysinit.SystemParamInit;
import com.oreilly.servlet.ServletUtils;

public class FileDownloadUtil {
	public static String constFileName(String fileName) {
		return fileName.substring(0, fileName.indexOf(".") - 24) + fileName.substring(fileName.indexOf("."));
	}

	/**
	 * 下载文件接口
	 * 
	 * @param request
	 * @param response
	 * @author wul
	 * @return
	 */
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) {

		String fileDirStr = request.getParameter("fileDir").replace("/", "\\\\");
		// 获取文件名称
		int bIndex = fileDirStr.indexOf("\\", 2);
		String fileName = fileDirStr.substring(bIndex + 2, fileDirStr.length());
		fileDirStr = fileDirStr.replace("\\", "/");
		try {
			/* 封装请求路径 */
			URL url = new URL(SystemParamInit.getValueByKey("fileServerURL") + fileDirStr);
			URI uri = new URI(url.getProtocol(), url.getHost() + ":" + url.getPort(), url.getPath(), url.getQuery(), null);
			// 构造Http请求
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(uri);

			// 执行请求
			HttpResponse responses = client.execute(post);

			// 获得请求文件输入流
			InputStream is = responses.getEntity().getContent();

			// 生命字节码
			int read = 0;
			byte[] bytes = new byte[1024];

			String PROCESSPATH_JILIANG = fileName;
			String path = request.getSession().getServletContext().getRealPath(PROCESSPATH_JILIANG);

			// 将输入流持久化写入本地文件流
			FileOutputStream fo = new FileOutputStream(path);
			// OutputStream os = response.getOutputStream();
			while ((read = is.read(bytes)) != -1) {
				fo.write(bytes, 0, read);
			}
			// 转换文件名称Byte，支持中文文件名
			fileName = new String(fileName.getBytes("utf8"), "utf-8");
			fileName = constFileName(fileName);
			fileName = URLEncoder.encode(fileName, "UTF-8");
			fileName = fileName.replace("+", " ");
			// 设置response响应头
			// 把响应设置为二进制流 ，* = application/octet-stream 可下载任何类型的文件
			// setContentType浏览器会开启下载框
			response.setContentType("application/octet-stream;charset=UTF-8");
			// 设置编码
			request.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

			ServletOutputStream out = null;
			try {
				out = response.getOutputStream();
				// 输出到页面下载框
				ServletUtils.returnFile(path, out);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != out) {
					out.close();
				}
				fo.close();
				File file = new File(path);
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param fileDir
	 */
	public static String downloadFile(String fileDir, String directory) {

		String fileDirStr = fileDir.replace("/", "\\\\");
		// 获取文件名称
		int bIndex = fileDirStr.indexOf("\\", 2);
		String fileName = fileDirStr.substring(bIndex + 2, fileDirStr.length());
		fileDirStr = fileDirStr.replace("\\", "/");
		try {
			/* 封装请求路径 */
			URL url = new URL(SystemParamInit.getValueByKey("fileServerURL") + fileDirStr);
			URI uri = new URI(url.getProtocol(), url.getHost() + ":" + url.getPort(), url.getPath(), url.getQuery(), null);
			// 构造Http请求
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(uri);
			// 执行请求
			HttpResponse responses = client.execute(post);
			// 获得请求文件输入流
			InputStream is = responses.getEntity().getContent();
			// 生命字节码
			int read = 0;
			byte[] bytes = new byte[1024];
			fileName = constFileName(fileName);
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext().getRealPath(directory + fileName);
			// 将输入流持久化写入本地文件流
			FileOutputStream fo = new FileOutputStream(path);
			while ((read = is.read(bytes)) != -1) {
				fo.write(bytes, 0, read);
			}
			fo.close();
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
