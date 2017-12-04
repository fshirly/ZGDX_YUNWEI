package com.fable.insightview.monitor.channel;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fable.insightview.monitor.alarmdispatcher.core.MessageResolverHolder;
import com.fable.insightview.monitor.messTool.constants.MessageTopic;
import com.fable.insightview.monitor.util.Results;

@WebServlet("/channel/confirm")
public class ChannelServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**  定义结果result中mOType为111*/
	private static final int mOType = 111;
	
	/** VPN通道访问端口, 缓存请求时间点*/
	private static ConcurrentMap<String, Long> requestTimes = new ConcurrentHashMap<String, Long>(1000);
	
	private static Long period = null;
	private static final String SPLIT = "|";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (period == null) {
			DecimalFormat df = new DecimalFormat("######0");
			period = Integer.parseInt(df.format(Integer.parseInt(req.getParameter("period")) * 1.5)) * 60 * 1000L;
		}
		String vpn = req.getParameter("conn");
		int serverPort = req.getServerPort();
		requestTimes.put(vpn + SPLIT + serverPort, System.currentTimeMillis());
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		new Thread(new ChannelMonitor()).start();
	}
	
	/**
	 * 单独启动线程依据采集周期【1.5倍计算】和与上次缓存的访问时间比较，进行告警产生
	 */
	class ChannelMonitor implements Runnable{

		@Override
		public void run() {
			
			final List<Object> ls = new ArrayList<Object>();
			
			while (true) {
				for (String key : requestTimes.keySet()) {
					if (period != null && ((System.currentTimeMillis() - requestTimes.get(key)) > period.longValue())) {
						// TODO:产生告警信息推送到kafka
						ls.add(results(key));
					}
				}
				if (!ls.isEmpty()) {
//					MessageResolverHolder.getInstance().produce(MessageTopic.performance_alerm, ls);
					ls.clear();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		private Results results(String info) {
			final String[] infos = info.split(SPLIT);
			Results results = new Results();
			results.setMOType(mOType);
			results.setStrIP(infos[0].split(":")[0]);
			List<Map<String, String>> datas = new ArrayList<Map<String,String>>();
			datas.add(new HashMap<String, String>(){
				private static final long serialVersionUID = 1L;

			{
				put("status", 1+"");//通道不可用状态
				put("vpn", infos[0]);
				put("port", infos[1]);
				put("time", new Date().getTime()+"");
			}});
			results.setData(datas);
			return results;
		}
	}
	
}
