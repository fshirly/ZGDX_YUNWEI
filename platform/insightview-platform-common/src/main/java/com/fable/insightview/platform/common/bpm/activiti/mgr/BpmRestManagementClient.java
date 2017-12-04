package com.fable.insightview.platform.common.bpm.activiti.mgr;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fable.insightview.platform.common.dao.ISecurityUserDao;
import com.fable.insightview.platform.common.dao.mapper.SecurityUserMapper;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.service.ISecurityUserService;

/**
 * 
 * 流程服务Rest API，包括用户鉴权和流程操作
 * 
 * @author xue.antai
 * 
 */
public class BpmRestManagementClient {
	private Logger logger = LoggerFactory.getLogger(BpmRestManagementClient.class);
	private HttpHeaders requestHeaders;

	private RestTemplate restTemplate;
	
	private Properties processResourcesBundle;

	private Properties systemParamBundle;
	
	private HttpClient httpClient;

	private String userName;
	
	private String password;
	
	private Integer localUserId;
	
	private ISecurityUserDao securityUserDao;
	
	private SecurityUserMapper securityUserMapper;
	
	public void setLocalUserId(Integer localUserId) {
		this.localUserId = localUserId;
	}
	
	public BpmRestManagementClient(Integer localUserId) throws IOException{
		InputStream in1 = this.getClass().getResourceAsStream("/processActivitiResources.properties");
		InputStream in2 = this.getClass().getResourceAsStream("/systemParam.properties");
		processResourcesBundle = new Properties();
		processResourcesBundle.load(in1);
		
		systemParamBundle = new Properties();
		systemParamBundle.load(in2);
		
//		userName = systemParamBundle.getProperty("process.user");
//		password = systemParamBundle.getProperty("process.password");
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		// 正常登录用户认证
		SecurityUserInfoBean curUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
		if(null == curUser) {
			// 操作人账号
			this.securityUserDao = (ISecurityUserDao) WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("platform.securityUserDao");
//			curUser = this.securityUserDao.getBaseInfoByUserId(localUserId);
			this.securityUserMapper = (SecurityUserMapper) WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("securityUserMapper");
			curUser = this.securityUserMapper.getBaseInfoByUserId(localUserId);
		}
		else if(1==localUserId) {
			// 公共账号
			this.securityUserDao = (ISecurityUserDao) WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("platform.securityUserDao");
//			curUser = this.securityUserDao.getBaseInfoByUserId(localUserId);
			this.securityUserMapper = (SecurityUserMapper) WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()).getBean("securityUserMapper");
			curUser = this.securityUserMapper.getBaseInfoByUserId(localUserId);
		}
		
		userName = curUser.getId().toString();
		password = curUser.getPassword();
		requestHeaders = createHeaders(userName, password);
		restTemplate = getRestTemplate();
	}
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	private RestTemplate getRestTemplate() {
		if(null == this.httpClient) {
			this.httpClient = getHttpClient();
		}
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return new RestTemplate(factory);
	}

	private HttpClient getHttpClient() {
		this.httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		return this.httpClient;
	}

	private HttpHeaders createHeaders(final String username,
			final String password) {
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64.encode(auth.getBytes());
		String authHeader = "Basic " + new String(encodedAuth);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authHeader);
		headers.set("Content-Type", "application/json; charset=utf-8");
		logger.info("Authorization:" + authHeader);
		return headers;
	}
	
	/**
	 * 获取接口地址
	 * @param prop
	 * @param params
	 * @return
	 */
	protected String urlForm(String prop, String... params) {
		String msg = "";
		
		if(params.length == 0) {
			msg = processResourcesBundle.getProperty(prop);
		} else {
			msg = MessageFormat.format(processResourcesBundle.getProperty(prop), (Object[]) params);
		}
		String url =  "http://" + systemParamBundle.getProperty("process.host")
				+ processResourcesBundle.getProperty("process.urlContext") + msg;
		return url;
	}
	
	/**
	 * 无参数调用服务接口
	 * @param url 接口地址
	 * @param enumRest 请求方式
	 * @return 返回文本类型内容
	 * @throws Exception
	 */
	protected String getDataFromRestService(String url,
			EnumActivitiRestType enumRest) throws Exception {
		return this.getDataFromRestService(url, enumRest, new HashMap<String, Object>());
	}
	
	/**
	 * 根据请求方式调用服务接口
	 * @param url 接口地址
	 * @param enumRest 请求方式
	 * @param parameters 请求参数，仅在RestType为POST和PUT时有效
	 * @return 请求结果返回的文本类型内容
	 * @throws Exception
	 */
	protected String getDataFromRestService(String url,
			EnumActivitiRestType enumRest, Map<String, Object> parameters)
			throws Exception {
		HttpEntity<Object> requestEntity = null;
		ResponseEntity<String> ret = null;
		if (EnumActivitiRestType.GET.restType.equals(enumRest.getRestType())) {
			requestEntity = new HttpEntity<Object>(requestHeaders);
			ret = restTemplate.exchange(url,HttpMethod.GET,requestEntity,String.class);
			logger.info("response:" + ret.getBody());
			return ret.getBody();
		} else if (EnumActivitiRestType.POST.restType.equals(enumRest.getRestType())) {
			requestEntity = new HttpEntity<Object>(parameters, requestHeaders);
			ret = restTemplate.exchange(url,HttpMethod.POST,requestEntity,String.class);
			logger.info("response:" + ret.getBody());
			return ret.getBody();
		}
		else if(EnumActivitiRestType.PUT.restType.equals(enumRest.getRestType())) {
			requestEntity = new HttpEntity<Object>(parameters, requestHeaders);
			ret = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
			logger.info("response:" + ret.getBody());
			return ret.getBody();
		}
		else if(EnumActivitiRestType.DELETE.restType.equals(enumRest.getRestType())) {
			requestEntity = new HttpEntity<Object>(requestHeaders);
			ret = restTemplate.exchange(url, HttpMethod.DELETE,requestEntity, String.class);
			logger.info("response:" + ret.getBody());
			return ret.getBody();
		}

		return null;
	}
	
	/**
	 * 调用Rest接口返回字节流，用于图片和二进制文件处理
	 * @param url 服务接口地址
	 * @param enumRest 调用方式
	 * @return 请求结果的字节流
	 * @throws Exception
	 */
	// TODO
	protected InputStream getBytesFromRestService(String url,
			EnumActivitiRestType enumRest) throws Exception {
		ResponseEntity<InputStream> retObj = null;
		if(EnumActivitiRestType.GET.restType.equals(enumRest.getRestType())) {
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestHeaders);
			retObj = this.restTemplate.exchange(url, HttpMethod.GET, requestEntity, InputStream.class, new HashMap());
		}
		return retObj.getBody();
	}
	
	/**
	 * 格式化变量参数 未考虑变量范围和变量类型为binary和serializable的情况
	 * @param params 
	 * 
	 * name	是	变量名称。
	 * value	否	变量值。写入变量时，如果没有设置value，会认为值是null。
	 * valueUrl	否	当读取变量的类型为binary或serializable时，这个属性会指向获取原始二进制数据的URL。
	 * scope	否	变量的范围。如果为'local'，变量会对应到请求的资源。如果为'global'，变量会定义到请求资源的上级（或上级树的任何上级）。当写入变量，没有设置scope时，假设使用global。
	 * type	否	变量类型。参考下面的表格对类型的描述。当写入变量，没有设置类型时，会根据请求的JSON属性来推断它的类型，限制为string，double，integer和boolean。如果不确定会用到的类型，建议还是要设置一个类型
	 * @return
	 */
	protected List getDataParam(Map<String, Object> params) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> m = null;
		if(null == params || params.isEmpty()) {
			throw new IllegalArgumentException("params cannot be null");
		}
		Set<String> keys = params.keySet();
		String type = null;
		for(String key : keys) {
			m = new HashMap<String, Object>();
			m.put(ConstantsBpm.DATA_FORMAT_VARIABLES_NAME, key);
			m.put(ConstantsBpm.DATA_FORMAT_VARIABLES_VALUE, params.get(key));
			type = this.getVarType(params.get(key));
			m.put(ConstantsBpm.DATA_FORMAT_VARIABLES_SCOPE, "local");
//			m.put(Constants.DATA_FORMAT_VARIABLES_TYPE, scope);
			list.add(m);
		}
		return list;
	}
	
	private String getVarType(Object obj) {
		String type = null;
		if(obj instanceof String ) {
			type = "string";
		}
		else if(obj instanceof Integer) {
			type = "integer";
		}
		else if(obj instanceof Short) {
			type = "short";
		}
		else if(obj instanceof Long) {
			type = "long";
		}
		else if(obj instanceof Double) {
			type = "double";
		}
		else if(obj instanceof Boolean) {
			type = "boolean";
		}
		else if(obj instanceof java.util.Date) {
			type = "date";
		}
		// TODO 基础类型之外的变量？？
		else {
			type = null;
		}
		return type;
	}
	
}