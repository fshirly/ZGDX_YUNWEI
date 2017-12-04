package com.fable.insightview.push.support;

import javax.servlet.ServletContext;

import org.atmosphere.cpr.AtmosphereFramework;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtmosphereContext {

	@Autowired
	private ServletContext servletContext;
	
	private AtmosphereFramework framework;
	
	public Broadcaster getBroadCaster(String topic) {
		BroadcasterFactory factory = framework.getBroadcasterFactory();
		return factory.lookup(topic, true);
	}
	
	public Broadcaster getBroadCaster(AtmosphereResource resource,String topic) {
		framework = resource.getAtmosphereConfig().framework();
		BroadcasterFactory factory = framework.getBroadcasterFactory();
		return factory.lookup(topic, true);
	}
}
