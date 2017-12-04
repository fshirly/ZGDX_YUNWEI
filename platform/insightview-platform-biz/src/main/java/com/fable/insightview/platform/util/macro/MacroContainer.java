/**
 * 
 */
package com.fable.insightview.platform.util.macro;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.fable.insightview.platform.common.util.StringUtil;

/**
 * @author zhouwei
 *
 */
@Component
public class MacroContainer implements ApplicationContextAware , InitializingBean {
	
	private ApplicationContext applicationContext;
	
	private static HashMap<String , Macro> container = new HashMap<String , Macro>();
	
	public static Macro getMacro(String key){
		if(StringUtil.isEmpty(key)){
			return null;
		}
		return container.get(StringUtil.trimToEmpty(key).toLowerCase());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String,Macro> macros = applicationContext.getBeansOfType(Macro.class);
		for(Macro macro : macros.values()){
			if(StringUtil.isEmpty(macro.getKey())){
				continue;
			}
			container.put(StringUtil.trimToEmpty(macro.getKey()).toLowerCase(), macro);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
