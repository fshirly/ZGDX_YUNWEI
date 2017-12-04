package com.fable.insightview.platform.core.jms;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.StreamMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * MQ消息的发布者
 * 
 * @author 刘金兵 2015-07-08
 * 
 */
public class JMSProvider {

	private static final Logger logger = LoggerFactory
			.getLogger(JMSProvider.class);

	private JmsTemplate jmsTemplate;

	private boolean pubSubDomain = true;

	/**
	 * 向默认队列发送text消息
	 * 
	 * @param topic
	 *            主题
	 * @param msg
	 *            消息
	 */
	public void sendMessage(final String topic, final String msg){
		if (null == topic || "".equals(topic)){return ;}
		if (null == msg || "".equals(msg)){return ;}
		jmsTemplate.setPubSubDomain(pubSubDomain);
		jmsTemplate.send(topic, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}

	/**
	 * 向默认队列发送map消息
	 * 
	 * @param topic
	 *            主题
	 * @param map
	 *            map集合
	 */
	public void sendMapMessage(final String topic,
			final Map<String, Object> maps) {
		if (null == topic || "".equals(topic)){return ;}
		if (null == maps || maps.isEmpty()){return;}
		jmsTemplate.setPubSubDomain(pubSubDomain);
		jmsTemplate.send(topic, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
				Iterator<String> it = maps.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					message.setObject(key, maps.get(key));
				}
				return message;
			}
		});
	}

	/**
	 * 向默认队列发送Object消息
	 * 
	 * @param topic
	 *            主题
	 * @param obj
	 *            实现序列化对象
	 */
	public void sendObjectMessage(final String topic, final Serializable obj) {
		if (null == topic || "".equals(topic)){return;}
		if (null == obj){return;}
		jmsTemplate.setPubSubDomain(pubSubDomain);
		jmsTemplate.send(topic, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(obj);
			}
		});
	}

	/**
	 * 向默认队列发送Bytes消息
	 * 
	 * @param topic
	 *            主题
	 * @param bt
	 *            byte数组
	 */
	public void sendBytesMessage(final String topic, final byte[] bt) {
		if (null == topic || "".equals(topic)){return;}
		if (null == bt){return;}
		jmsTemplate.setPubSubDomain(pubSubDomain);
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				BytesMessage message = session.createBytesMessage();
				message.writeBytes(bt);
				return message;
			}
		});
	}

	/**
	 * 向默认队列发送Stream消息
	 * @param topic 主题
	 * @param str 字符串
	 */
	public void sendStreamMessage(final String topic, final String str) {
		if (null == topic || "".equals(topic)){return;}
		if (null == str || "".equals(str)){return ;}
		jmsTemplate.setPubSubDomain(pubSubDomain);
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				StreamMessage message = session.createStreamMessage();
				message.writeString(str);
				return message;
			}
		});
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setPubSubDomain(boolean pubSubDomain) {
		this.pubSubDomain = pubSubDomain;
	}

}
