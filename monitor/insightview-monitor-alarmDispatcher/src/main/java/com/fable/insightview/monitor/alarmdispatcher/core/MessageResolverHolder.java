package com.fable.insightview.monitor.alarmdispatcher.core;

import java.util.Properties;

import nl.justobjects.pushlet.util.Sys;

import com.fable.insightview.monitor.messTool.MessageResolver;

public class MessageResolverHolder {

	private static final String PROPERTIES_FILE = "zookeeper.properties";
	
	private static MessageResolver mt = MessageResolver.getInstance();
	
	static {
		mt.setProperty(getProperties());
	}
	
	private static Properties getProperties(){
		Properties p = new Properties();
		try {
			p = Sys.loadPropertiesResource(PROPERTIES_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public static MessageResolver getInstance() {
		return mt;
	}
}
