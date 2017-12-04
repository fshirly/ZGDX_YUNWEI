package com.fable.insightview.platform.core.portal;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

public class TemplateFactory {

	private static Map<String, String> templateCache = new HashedMap();
	
	public static String getTemplate(String templateName){
		return templateCache.get(templateName);
	}

	public static void setTemplateCache(Map<String, String> templateCache) {
		TemplateFactory.templateCache = templateCache;
	}
}
