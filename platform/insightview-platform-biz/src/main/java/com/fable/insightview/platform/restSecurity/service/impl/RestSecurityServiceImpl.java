package com.fable.insightview.platform.restSecurity.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fable.insightview.platform.common.util.Cast;
import com.fable.insightview.platform.common.util.Tool;
import com.fable.insightview.platform.restSecurity.entity.MethodBean;
import com.fable.insightview.platform.restSecurity.entity.ModuleRestDto;
import com.fable.insightview.platform.restSecurity.entity.RestBean;
import com.fable.insightview.platform.restSecurity.mapper.MethodBeanMapper;
import com.fable.insightview.platform.restSecurity.service.RestSecurityService;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
//import com.wordnik.swagger.annotations.Api;
//import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author fangang
 * Rest接口权限Service实现类
 */
@Service
public class RestSecurityServiceImpl implements RestSecurityService {
	
	@Autowired
	private MethodBeanMapper methodBeanMapper;
	
//	@Autowired
//	private MethodRoleBeanMapper methodRoleBeanMapper;
//	
//	@Autowired
//	private UserRoleBeanMapper userRoleBeanMapper;
//	
//	@Autowired
//	private RequestUtil requestUtil;

	@Override
	public List<RestBean> getRestData() throws Exception {
		List<RestBean> list = new ArrayList<RestBean>();
		
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(Component.class));
		
		String basePackage = "com.fable.**.*";
		Set<BeanDefinition> components = provider.findCandidateComponents(basePackage);
		
		String urlCls = ""; //类地址
		String urlMtd = ""; //方法地址
		
		for(BeanDefinition component : components) {
			if(component instanceof AnnotatedBeanDefinition) {
				System.out.printf("ClassName: %s\n", component.getBeanClassName());
				
				//通过反射获取
				try {
					final Class<?> clazz = Class.forName(component.getBeanClassName());
					Annotation cc = clazz.getAnnotation(Controller.class);
					
					//只扫描@Controller的类
					if(cc != null) {
						RestBean clsRestBean = new RestBean();
						clsRestBean.setType("2");
						String clsName = component.getBeanClassName();
						clsRestBean.setName(clsName);
						clsRestBean.setAliasName(clsName.substring(clsName.lastIndexOf(".") + 1));
						clsRestBean.setId(clsName);
						clsRestBean.setPid("-1");
						clsRestBean.setNote("类说明");
						
						//获取类的@Api
//						Api api = clazz.getAnnotation(Api.class);
//						if(api != null) {
//							if(StringUtil.isNotEmpty(api.description())) {
//								clsRestBean.setNote(api.description());
//							}
//						}
						
						//获取类的@RequestMapping
						Annotation rm = clazz.getAnnotation(RequestMapping.class);
						if(rm != null) {
							urlCls = ((RequestMapping)rm).value()[0];
						}
						
						//遍历方法
						final Method[] declaredMethods = clazz.getDeclaredMethods();
						for(final Method method : declaredMethods) {
							RestBean methodRestBean = new RestBean();
							methodRestBean.setType("3");
							String methodName = method.getName();
							methodRestBean.setName(clsName + "." + methodName);
							methodRestBean.setAliasName(methodName);
							methodRestBean.setId(clsName + "." + methodName);
							methodRestBean.setPid(clsRestBean.getId());
							
							//获取方法的@RequestMapping注解
							RequestMapping rm11 = method.getAnnotation(RequestMapping.class);
							if(rm11 != null) {
								urlMtd = rm11.value()[0];
								if(!urlCls.endsWith("/") && !urlMtd.startsWith("/")) {
									methodRestBean.setUrl(urlCls + "/" + urlMtd);
								} else {
									methodRestBean.setUrl(urlCls + urlMtd);
								}
							}else{
								continue;
							}
							
//							ApiOperation ao = (ApiOperation)method.getAnnotation(ApiOperation.class);
//							if(ao != null) {
//								if(StringUtil.isNotEmpty(ao.notes())) {
//									methodRestBean.setNote(ao.notes());
//								} else if(StringUtil.isNotEmpty(ao.value())) {
//									methodRestBean.setNote(ao.value());
//								}
//							}
							
							list.add(methodRestBean);
						}
						list.add(clsRestBean);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	@Override
	public String[] queryRestByModuleId(String moduleId) throws Exception {
		return methodBeanMapper.queryRestByModuleId(moduleId);
	}
	
	@Override
	public void saveRest(String moduleId, MethodBean[] methodBeans) throws Exception {
		Set<String> data1 = new HashSet<String>(); //原始数据集合
		Set<String> data2 = new HashSet<String>(); //当前数据集合
		Set<String> result = new HashSet<String>();
		
		Map<String, MethodBean> mapMethod = Maps.uniqueIndex(Arrays.asList(methodBeans), new Function<MethodBean, String>() {
	          public String apply(MethodBean from) {
	            return from.getMethodName();
	    }});
		
		String[] oriRestNames = queryRestByModuleId(moduleId); //数据库模块关联的Rest
		data1.addAll(Arrays.asList(oriRestNames));
		for(MethodBean methodBean : methodBeans) {
			data2.add(methodBean.getMethodName());
		}
		
		//1、计算原始数据集合-当前数据集合，并删除
		result.clear();
        result.addAll(data1);
        result.removeAll(data2);
        //System.out.println("差集1：" + result);
		if(!result.isEmpty()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("moduleId", moduleId);
			map.put("list", result.toArray());
			methodBeanMapper.batchDeleteByModuleId(map);
		}
        
        //2、计算当前数据集合-原始数据集合，并插入
        result.clear();
        result.addAll(data2);
        result.removeAll(data1);
        //System.out.println("差集2：" + result);
        for(String restName : result) {
            //System.out.println(restName);
            MethodBean methodBean = new MethodBean();
            methodBean.setModuleId(moduleId);
            methodBean.setMethodName(restName);
            methodBean.setUrl(mapMethod.get(restName).getUrl());
            //requestUtil.boToUserbo(methodBean);  //TODO
            methodBean.setId(Cast.guid2Str(Tool.newGuid()));
            methodBeanMapper.insert(methodBean);
        }
	}
	
	@Override
	public List<ModuleRestDto> queryModuleRestList() throws Exception {
		return methodBeanMapper.queryModuleRest();
	}

//	@Override
//	public String[] getRestByRoleId(String roleId) throws Exception {
//		return methodRoleBeanMapper.queryRestByRoleId(roleId);
//	}
//
//	@Override
//	public void saveRoleRest(String roleId, String[] restNames) throws Exception {
//		Set<String> data1 = new HashSet<String>(); //原始数据集合
//		Set<String> data2 = new HashSet<String>(); //当前数据集合
//		Set<String> result = new HashSet<String>();
//		
//		String[] oriRestNames = getRestByRoleId(roleId); //数据库模块关联的Rest
//		data1.addAll(Arrays.asList(oriRestNames));
//		data2.addAll(Arrays.asList(restNames));
//		
//		//1、计算原始数据集合-当前数据集合，并删除
//		result.clear();
//        result.addAll(data1);
//        result.removeAll(data2);
//        //System.out.println("差集1：" + result);
//		if(!result.isEmpty()) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("roleId", roleId);
//			map.put("list", result.toArray());
//			methodRoleBeanMapper.batchDeleteByRoleId(map);
//		}
//        
//        //2、计算当前数据集合-原始数据集合，并插入
//        result.clear();
//        result.addAll(data2);
//        result.removeAll(data1);
//        //System.out.println("差集2：" + result);
//        for(String restName : result) {
//            //System.out.println(restName);
//            MethodRoleBean methodRoleBean = new MethodRoleBean();
//            methodRoleBean.setRoleId(roleId);
//            methodRoleBean.setMethodName(restName);
//            requestUtil.boToUserbo(methodRoleBean);
//            methodRoleBean.setId(Cast.guid2Str(Tool.newGuid()));
//            methodRoleBeanMapper.insert(methodRoleBean);
//        }
//	}
//	
//	@Override
//	public boolean isRight(String userId, String methodName) throws Exception {
//		
//		//获取角色列表
//		String[] roleIds = userRoleBeanMapper.queryGrantedRoleByUserId(userId);
//		if(roleIds.length == 0) return false;
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("methodName", methodName);
//		map.put("list", roleIds);
//		String[] result = methodRoleBeanMapper.queryRest(map);
//		
//		return result == null || result.length == 0 ? false : true;
//	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			RestSecurityService rss = new RestSecurityServiceImpl();
			List<RestBean> list = rss.getRestData();
			for(RestBean rb : list) {
				System.out.println(PropertyUtils.describe(rb));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
