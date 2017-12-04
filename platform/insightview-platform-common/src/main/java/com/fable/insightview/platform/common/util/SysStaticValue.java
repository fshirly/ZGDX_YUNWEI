package com.fable.insightview.platform.common.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;

public class SysStaticValue {

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public static Map<String, Collection<ConfigAttribute>> getResourceMap() {
		return resourceMap;
	}

	public static void setResourceMap(
			Map<String, Collection<ConfigAttribute>> resourceMap) {
		SysStaticValue.resourceMap = resourceMap;
	}
}
