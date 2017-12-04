package com.fable.insightview.monitor.alarmdispatcher.daily;
 
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar; 
import java.util.Properties; 

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 


/**
 * httpclient 模拟登陆及上传
 */
public class Uploadaily {

	private HttpClient client;

	static Properties prop = new Properties();
  
	private String loginUrl = "";

	private String userId = "";

	private String userPass = "";

	private String fileUpLoadUrl = "";

	private String filePath = "";
	
	private String filePrefixName ="";
		
	private String actionMethod = "";
	
	//获取以当前类为参数的日志对象    
    public static Logger logger = LoggerFactory.getLogger(Uploadaily.class);
    
	/**
	 * Default Constructor
	 */
	public Uploadaily(String loginUrl, String userId, String userPass,
			String actionMethod, String fileUpLoadUrl, String filePath,
			String filePrefixName) {
		this.client = new HttpClient();
		this.client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		try {
			this.loginUrl = loginUrl;
			this.userId = userId;
			this.userPass = userPass;
			this.fileUpLoadUrl = fileUpLoadUrl;
			this.filePath = filePath;
			this.actionMethod = actionMethod;
			this.filePrefixName = filePrefixName;
//			this.filePrefixName = new String(filePrefixName.getBytes("ISO-8859-1"), "gbk");
		} catch (Exception e) {
			logger.error("Daily upload init error!", e);
		}
	}

	/**
	 * 登录
	 * @param url 请求URL
	 * @param username 用户名
	 * @param password 密码
	 * @throws HttpException
	 * @throws IOException
	 */
	public boolean login() throws HttpException, IOException {
		logger.info("Daily ... start login ...");
		UTF8PostMethod method = new UTF8PostMethod(loginUrl); 
		try {
			// user 
			NameValuePair name = new NameValuePair("userId", userId);
			// pass
			NameValuePair pass = new NameValuePair("userPass", userPass);
			// action为登陆跳转方法
			NameValuePair action = new NameValuePair("action", actionMethod);
			// set
			method.setRequestBody(new NameValuePair[] { name, pass, action });
  
			int statuscode = client.executeMethod(method);
			if (statuscode == HttpStatus.SC_OK) {
				Cookie[] cookies = client.getState().getCookies();
				for (int i = 0; i < cookies.length; i++) {
					// System.out.println("cookie = " + cookies[i].toString());
				}
			} else if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY)
					|| (statuscode == HttpStatus.SC_MOVED_PERMANENTLY)
					|| (statuscode == HttpStatus.SC_SEE_OTHER)
					|| (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {

				// 读取新的URL地址
				Header header = method.getResponseHeader("location");
				if (header != null) {
					String newuri = header.getValue();
					if ((newuri == null) || (newuri.equals(""))) {
						newuri = "/";
					}
					newuri += "/"+actionMethod+"?userId="+userId+"&userPass="+userPass; 
					UTF8PostMethod redirect = new UTF8PostMethod(newuri);
					client.executeMethod(redirect);

					// 打印结果页面
					//String response = new String(redirect
					//		.getResponseBodyAsString().getBytes("UTF-8"));
					// 打印返回的信息
					// System.out.println("Redirect response:"+response);
					redirect.releaseConnection();
			  
					// 上传文件
					return uploadfile();

				} else {
					throw new HttpException("Invalid redirect"); 
				}
			} else {
				throw new HttpException("登录失败，错误代码：" + statuscode);
			}
		} finally {			
			method.releaseConnection();
		}
		return false;
	}

	/**
	 * Test file upload
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public boolean uploadfile() throws IOException {
		logger.info("Daily ... start upload file ...");
		// post type
		// test
		// fileUpLoadUrl = "www.baidu.com";
		MultipartPostMethod multipartPost = new MultipartPostMethod(
				fileUpLoadUrl);
		multipartPost.setDoAuthentication(false);
		multipartPost.setRequestHeader("Content-Type","text/html; charset=GB18030");  
		client.setConnectionTimeout(8000); 
   
		// get file list where the path has
		File file = new File(new File("").getAbsolutePath()
				+ File.separator + filePath + File.separator
				+ "dayAnnouncExcel");
		// get the folder list
		File[] array = file.listFiles(); 
		boolean isExistFile = false; 
		filePrefixName += getDate() + ".xls";
		
		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile() && filePrefixName != null && filePrefixName.equals(array[i].getName())) { 
				multipartPost.addParameter("uf", array[i]);
				isExistFile = true;
			} else if (array[i].isDirectory()) {
				// System.out.println("can't execute directory!");
			}
		}
		 
		if (isExistFile) {
			multipartPost.addParameter("type", "04");
			multipartPost.addParameter("typeValue", "运行服务管理日通告");
			// config timeout
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			
			int statuscode = client.executeMethod(multipartPost);
			//	if (statuscode == HttpStatus.SC_OK) {
			//	//System.out.println("Upload complete, response = " + multipartPost.getResponseBodyAsString());
			//	} else {
			//	//System.out.println("Upload failed, response = " + HttpStatus.getStatusText(statuscode));
			//	}
			if (statuscode == HttpStatus.SC_OK) {
				//System.out.println("statusLine = " + multipartPost.getStatusLine());
				Cookie[] cookies = client.getState().getCookies();
				for (int i = 0; i < cookies.length; i++) {
					//System.out.println("cookie = " + cookies[i].toString());
				}
				multipartPost.releaseConnection();
				return true;
			} else {
				multipartPost.releaseConnection();
				return false;
				//throw new HttpException("upload error, response = " + HttpStatus.getStatusText(statuscode));
			}
		}
		logger.error("Daily upload file doesn't exist!");
		return false;
	}

	/**
	 * 抓取一个页面
	 * 
	 * @param url 页面URL
	 * @throws HttpException
	 * @throws IOException
	 */
	public void getPage(String url) throws HttpException, IOException {
		GetMethod method = new GetMethod(url);
		try {
			int status = client.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				System.out.println(method.getResponseBodyAsString());
			}
		} finally {
			method.releaseConnection();
		}
	}
 
	public String getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}
}

/**
 * 重写PostMethod以解决UTF-8编码问题
 * 
 */
class UTF8PostMethod extends PostMethod {
	public UTF8PostMethod(String url) {
		super(url);
	}

	@Override
	public String getRequestCharSet() {
		return "UTF-8";
	}
}