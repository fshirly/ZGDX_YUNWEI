package com.fable.insightview.platform.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.fable.insightview.platform.common.service.ISecurityUserService;
import com.fable.insightview.platform.common.util.SysStaticValue;

/**
 * 加载资源与权限的对应关系－－ 资源权限获取器：用来取得访问某个URL或者方法所需要的权限，接口为SecurityMetadataSource
 * SecurityMetadataSource包括MethodSecurityMetadataSource和FilterInvocationSecurityMetadataSource
 * ，分别对应方法和URL资源。
 * 
 * @author 汪朝  2013-9-30
 * 
 */
@Service("fableSecurityMetadataSource")
public class FableSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private ISecurityUserService securityUserService;
	

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	/**
	 * 加载所有资源与权限的关系
	 */
	@PostConstruct
	private void loadResourceDefine() {
		this.securityUserService.updateResourceMap();
	}

	/**
	 * 返回所请求资源所需要的权限
	 * 
	 * @param urlObject
	 *            此参数封装了访问的URL(请求的资源)
	 */
	public Collection<ConfigAttribute> getAttributes(Object urlObject) throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) urlObject).getRequestUrl();
		// 处理一下,把?后的去掉
		if (requestUrl.indexOf("?") != -1)
			requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
		if (SysStaticValue.getResourceMap() == null) {
			loadResourceDefine();
		}
		//返回的所有权限资源
		Collection<ConfigAttribute> all = new ArrayList<ConfigAttribute>();
		PathMatcher matcher = new AntPathMatcher();

		for (String url : SysStaticValue.getResourceMap().keySet()) {
			// 如果有匹配的URL时
			if (matcher.match(url, requestUrl)) {
				all.addAll(SysStaticValue.getResourceMap().get(url)) ;
			}
		}
//		if(all.isEmpty()){
//			throw new AccessDeniedException(" 没有权限访问！");
//		}
		// 如果这个URL没有配置相关的权限时,如果这里返回NULL或者一个空的COLLECTION,则表示直接认证成功
		return all;
		// throw new AccessDeniedException(" 没有权限访问！");

	}
}
