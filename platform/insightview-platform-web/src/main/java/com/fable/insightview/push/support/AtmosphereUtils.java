package com.fable.insightview.push.support;

import java.util.concurrent.CountDownLatch;

import javax.servlet.http.HttpServletRequest;

import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Meteor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 推送工具类
 * 主要实现原始请求到AtmosphereResource的转换
 * 推迟响应
 * 
 * @author 郑自辉
 *
 */
public final class AtmosphereUtils {


    public static final Logger LOG = LoggerFactory.getLogger(AtmosphereUtils.class);

    public static AtmosphereResource getAtmosphereResource(HttpServletRequest request) {
        return getMeteor(request).getAtmosphereResource();
    }

    public static Meteor getMeteor(HttpServletRequest request) {
        return Meteor.build(request);
    }

    public static void suspend(final AtmosphereResource resource) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        resource.addEventListener(new AtmosphereResourceEventListenerAdapter() {
            @Override
            public void onSuspend(AtmosphereResourceEvent event) {
                countDownLatch.countDown();
                resource.removeEventListener(this);
            }
        });
        
        AtmosphereRequest request = resource.getRequest();
        if(request.getHeader("negotiating") == null) {
            resource.resumeOnBroadcast(resource.transport() == AtmosphereResource.TRANSPORT.LONG_POLLING).suspend();
        }
        else {
        	resource.suspend();
        }
        
        /**
         * 兼容Long-Polling
         */
        if (resource.isSuspended()) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                LOG.error("Interrupted while trying to suspend resource {}", resource);
            }
        }
    }
}
