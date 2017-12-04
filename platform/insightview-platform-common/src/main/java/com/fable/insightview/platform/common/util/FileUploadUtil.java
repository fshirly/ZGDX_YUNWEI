package com.fable.insightview.platform.common.util;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fable.insightview.platform.sysinit.SystemParamInit;
import com.oreilly.servlet.MultipartRequest;

public class FileUploadUtil {
	
	/**
	 * 重写generateContentType，去掉charset的设置，浏览器不设置charset，这里的post请求也要去掉charset的设置
	 * @author Administrator
	 *
	 */
	public class CustomMultipartEntity extends MultipartEntity {
		
		public CustomMultipartEntity(
	            HttpMultipartMode mode,
	            String boundary,
	            Charset charset){
			super(mode,boundary,charset);
		}
		
		@Override
		protected String generateContentType(
	            final String boundary,
	            final Charset charset) {
	        StringBuilder buffer = new StringBuilder();
	        buffer.append("multipart/form-data; boundary=");
	        buffer.append(boundary);
//	        if (charset != null) {
//	            buffer.append("; charset=");
//	            buffer.append(charset.name());
//	        }
	        return buffer.toString();
	    }
	}

	/**
	 * 上传文件接口
	 * 
	 * @param request
	 * @param response
	 * @author wul
	 * @return
	 */

	public String uploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		String strUtil = "";
		try {
			File uploadFile = this.mkFile(request);
			String fileDir = this.construFolderName();

			// 构造上传文件
			MultipartEntity mpEntity = new CustomMultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,null,Charset.forName("utf-8"));
			ContentBody cbFile = new FileBody(uploadFile);
			mpEntity.addPart("userfile", cbFile);

			// 构造Http请求
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(SystemParamInit.getValueByKey("fileServerPath")
					+ fileDir);
			post.setEntity(mpEntity);
			// 执行请求
			HttpResponse responses = client.execute(post);

			// 接收请求返回值
			strUtil =EntityUtils.toString(responses.getEntity(), "utf-8");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strUtil;
	}
	
	public String uploadFile(String file) {
		String strUtil = "";
		try {
			File uploadFile = new File(file);
			String fileDir = this.construFolderName();

			// 构造上传文件
			MultipartEntity mpEntity = new CustomMultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,null,Charset.forName("utf-8"));
			ContentBody cbFile = new FileBody(uploadFile);
			mpEntity.addPart("userfile", cbFile);

			// 构造Http请求
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(SystemParamInit.getValueByKey("fileServerPath")
					+ fileDir);
			post.setEntity(mpEntity);
			// 执行请求
			HttpResponse responses = client.execute(post);

			// 接收请求返回值
			strUtil =EntityUtils.toString(responses.getEntity(), "utf-8");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strUtil;
	}
	
	/**
	 * 删除文件接口
	 * 
	 * @param request
	 * @param response
	 * @author nlb
	 * @return
	 */
	
	public String delFile(HttpServletRequest request,
			HttpServletResponse response,String fileName) {
		String strUtil = "";
		try {
			request.setCharacterEncoding("UTF-8");
			// 构造Http请求
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(SystemParamInit.getValueByKey("fileDeleteServerPath")
					+ URLEncoder.encode(fileName,"utf-8"));
//			post.setEntity(mpEntity);
			// 执行请求
			HttpResponse responses = client.execute(post);
			
			// 接收请求返回值
			strUtil =EntityUtils.toString(responses.getEntity(), "utf-8");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strUtil;
	}
	
	public String getFileSize(HttpServletRequest request,
			HttpServletResponse response,String fileName) {
		String strUtil = "";
		if(!"".equals(fileName)) {
			fileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.lastIndexOf("."));
		}
		try {
			request.setCharacterEncoding("UTF-8");
			// 构造Http请求
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(SystemParamInit.getValueByKey("getFileSizeServerPath")
					+ URLEncoder.encode(fileName,"utf-8"));
//			post.setEntity(mpEntity);
			// 执行请求
			HttpResponse responses = client.execute(post);
			
			// 接收请求返回值
			strUtil =EntityUtils.toString(responses.getEntity(), "utf-8");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strUtil;
	}
	
	public String uploadMaxFile(HttpServletRequest request,
			HttpServletResponse response) {
		String strUtil = "";
		try {
			File uploadFile = this.mkMaxFile(request);
			String fileDir = this.construFolderName();
			uploadFile.getName();

			// 构造上传文件
			MultipartEntity mpEntity = new CustomMultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,null,Charset.forName("utf-8"));
			ContentBody cbFile = new FileBody(uploadFile);
			mpEntity.addPart("userfile", cbFile);

			// 构造Http请求
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(SystemParamInit.getValueByKey("fileMaxServerPath")
					+ fileDir);
			post.setEntity(mpEntity);
			// 执行请求
			HttpResponse responses = client.execute(post);

			// 接收请求返回值
			strUtil =EntityUtils.toString(responses.getEntity(), "utf-8");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strUtil;
	}

	/**
	 * 生成文件保存路径 生成规则 年-月
	 * 
	 * @author wul
	 * @return
	 */
	public static String construFolderName() {
		Calendar now = Calendar.getInstance();
		String fileDirStr = now.get(Calendar.YEAR) + "-"
				+ (now.get(Calendar.MONTH) + 1);
		return fileDirStr;
	}

	/**
	 * 生成文件接口
	 * 
	 * @author wul
	 * @param request
	 * @param response
	 * @return
	 */
	public File mkFile(HttpServletRequest request) {
		// 获取文件在文档服务器中的保存路径
		String fileDirStr = this.construFolderName();

		// 确定文件的保存位置
		File fileDir = new File(request.getServletContext().getRealPath(
				"/FileDir/" + fileDirStr));
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		// 设置上传文件的大小，超过这个大小 将抛出IOException异常，默认大小是10M。
		int inmaxPostSize = 10 * 1024 * 1024;
		MultipartRequest multirequest = null;

		try {
			// MultipartRequest()有8种构造函数！
			multirequest = new MultipartRequest(request,
					fileDir.getAbsolutePath(), inmaxPostSize, "utf-8"); // GBK中文编码模式上传文件

			Enumeration<String> filedFileNames = multirequest.getFileNames();
			String filedName = null;
			if (null != filedFileNames) {
				while (filedFileNames.hasMoreElements()) {

					filedName = filedFileNames.nextElement();// 文件文本框的名称
					// 获取该文件框中上传的文件，即对应到上传到服务器中的文件	
					File uploadFile = multirequest.getFile(filedName);
					return uploadFile;
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 生成文件接口
	 * 
	 * @author wul
	 * @param request
	 * @param response
	 * @return
	 */
	public File mkMaxFile(HttpServletRequest request) {
		// 获取文件在文档服务器中的保存路径
		String fileDirStr = this.construFolderName();

		// 确定文件的保存位置
		File fileDir = new File(request.getServletContext().getRealPath(
				"/FileDir/" + fileDirStr));
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		// 设置上传文件的大小
		int inmaxPostSize = 500 * 1024 * 1024;
		MultipartRequest multirequest = null;
		try {
			// MultipartRequest()有8种构造函数！
			multirequest = new MultipartRequest(request,
					fileDir.getAbsolutePath(), inmaxPostSize, "utf-8"); // GBK中文编码模式上传文件

			Enumeration<String> filedFileNames = multirequest.getFileNames();
			String filedName = null;
			if (null != filedFileNames) {
				while (filedFileNames.hasMoreElements()) {

					filedName = filedFileNames.nextElement();// 文件文本框的名称
					// 获取该文件框中上传的文件，即对应到上传到服务器中的文件	
					File uploadFile = multirequest.getFile(filedName);
					return uploadFile;
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
